/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher.service;

import com.sree.podcatcher.component.ConfigLoadException;
import com.sree.podcatcher.component.ConfigLoader;
import com.sree.podcatcher.component.DownLoad;
import com.sree.podcatcher.component.DownLoadException;
import com.sree.podcatcher.component.Parser;
import com.sree.podcatcher.component.ParserException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author sreejithu.panicker
 */
@Service
public class WebPodcast implements Podcast {

    private Logger logger = Logger.getLogger("com.sree.podcatcher.service.Podcast");
    @Autowired
    private ConfigLoader configLoader;
    //private Parser parser;
    @Autowired
    private ApplicationContext factory;

    @Override
    public boolean download() {
        boolean success = true;
        try {
            //loading all the urls fro the application.properties , you can change this to your own implementation. 
            ArrayList<String> urls = configLoader.load();
            ExecutorService executorServiceParser = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            ArrayList<Future<ArrayList<String>>> futureList = new ArrayList();
            urls.forEach((String str) -> {

                Parser parser;
                try {
                    logger.debug(str);
                    parser = factory.getBean(Parser.class);
                    parser.setUrl(str);
                    futureList.add(executorServiceParser.submit(parser));
                } catch (ParserException e) {
                    logger.error("unable to parse teh data :" + str + " " + e);
                }
            });
            ExecutorService executorServiceDownLoad = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            ArrayList<Future<Boolean>> futureDownLoad = new ArrayList();

            futureList.forEach((Future<ArrayList<String>> future) -> {
                try {
                    ArrayList<String> listofUrlsforEachLink = future.get();

                    listofUrlsforEachLink.forEach((str) -> {
                        try {
                            DownLoad download;
                            download = factory.getBean(DownLoad.class);
                            download.setDownloadUrl(str);
                            futureDownLoad.add(executorServiceDownLoad.submit(download));
                        } catch (DownLoadException e) {
                            logger.error("Unable to set URL " + e);
                        }
                    });
                } catch (Exception e) {
                    logger.error("Unable to read the future  object" + e);
                }
            });
            executorServiceParser.shutdown();
            futureDownLoad.forEach((future) -> {
                try {
                    future.get();
                } catch (Exception e) {
                    logger.error("Unable to read the future  object" + e);
                }
            });
            executorServiceDownLoad.shutdown();
        } catch (ConfigLoadException ex) {
            success = false;
        }
        return success;
    }
}
