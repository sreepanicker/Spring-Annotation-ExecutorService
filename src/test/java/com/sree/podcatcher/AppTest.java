/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher;

import com.sree.podcatcher.component.ComponentTest;
import com.sree.podcatcher.service.WebPodcastTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author sreejithu.panicker
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({WebPodcastTest.class,ComponentTest.class})
public class AppTest {
   
}
