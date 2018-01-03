/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher.component;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author sreejithu.panicker
 */
@Component
@Scope("prototype")
public class FileDownLoad implements DownLoad {
    Logger logger = Logger.getLogger("com.sree.podcatcher.component.FileDownLoad");
    @Value("${downloadLocation}")
    private String location;
    private URL url;

    @Override
    public void setDownloadUrl(String url) throws DownLoadException {
        try {
            this.url = new URL(url);
        } catch (Exception e) {
            logger.error("Unable to set URL :", e);
            throw new DownLoadException("invaild URL Exception for download !");
        }
    }

    @Override
    public Boolean call() throws DownLoadException {
        boolean success = false;
        String fileName = url.toString().substring(url.toString().lastIndexOf("/") + 1);
        try {
            File file = new File(location, fileName);
            file.createNewFile();
            try (FileOutputStream fileoutput = new FileOutputStream(file); InputStream inputStream = url.openStream();) {
                int len;
                byte[] b = new byte[4096];
                Arrays.fill(b, (byte) 0);
                while ((len = inputStream.read(b, 0, 4096)) != 0) {
                    fileoutput.write(b, 0, len);
                    Arrays.fill(b, (byte) 0);
                }
            }
            success = true;
        } catch (Exception e) {
            logger.error("Unable process the file download :", e);
            throw new DownLoadException("Unavilable to download file and write file into file system");
        }
        return success;
    }

}
