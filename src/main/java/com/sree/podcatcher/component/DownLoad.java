/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher.component;

import java.util.concurrent.Callable;

/**
 *
 * @author sreejithu.panicker
 */
public interface DownLoad extends Callable<Boolean>{
    public void setDownloadUrl(String url) throws DownLoadException;
}
