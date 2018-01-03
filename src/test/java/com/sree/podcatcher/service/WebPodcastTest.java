/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher.service;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author sreejithu.panicker
 */
public class WebPodcastTest {
    
    private Mockery mockery;
    
    @Before
    /*
    This metheod initialize the mockery object before each test.
    */
    public void Initialize(){
        mockery = new Mockery();                       
    }
    
    @Test()
       /*
    This is a mock test method to simulate the all the service calls 
    */
    public void downloadMock() {
        final Podcast podcast = mockery.mock(Podcast.class);
        mockery.checking(new Expectations(){{
            oneOf(podcast);
            returnValue(true);
        }});
        podcast.download();
        mockery.assertIsSatisfied();
    }
    @Test
    @Ignore
    public void download(){
        WebPodcast webPodcast = new WebPodcast();
        boolean status = webPodcast.download();
        assertTrue("Service throws exception, failure to call associated components!", status);
    }
}
