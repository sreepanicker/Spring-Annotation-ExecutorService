/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher;



import com.sree.podcatcher.service.Podcast;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author sreejithu.panicker
 */
public class App {

    public static void main(String[] args) throws Exception {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Podcast podCast = appContext.getBean(Podcast.class);
        podCast.download();

    }
}
