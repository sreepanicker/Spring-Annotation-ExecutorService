/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher.component;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 *
 * @author sreejithu.panicker
 */
public interface Parser extends Callable<ArrayList<String>> {
    public void setUrl(String url) throws ParserException;
}
