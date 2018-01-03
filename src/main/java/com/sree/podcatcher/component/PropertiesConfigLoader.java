/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher.component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author sreejithu.panicker
 */
@Component
public class PropertiesConfigLoader implements ConfigLoader {

    @Value("${urlLocation}")
    private String[] urls;

    @Override
    public ArrayList<String> load() throws ConfigLoadException {

        List<String> list = Arrays.asList(urls);
        if (list.size() < 0) {
            throw new ConfigLoadException("invalid config information ");
        }
        return new ArrayList(list);
    }

}
