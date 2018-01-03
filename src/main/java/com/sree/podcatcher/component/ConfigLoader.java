/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher.component;

import java.util.ArrayList;

/**
 *
 * @author sreejithu.panicker
 */
public interface ConfigLoader {
    public ArrayList<String> load() throws ConfigLoadException;
}
