/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author sreejithu.panicker
 */
@Configuration
@ComponentScan("com.sree.podcatcher")
@PropertySource({"application.properties"})
@EnableAspectJAutoProxy
public class AppConfig {

}
