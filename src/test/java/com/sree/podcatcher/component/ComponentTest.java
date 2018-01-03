/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher.component;

import java.net.URL;
import java.util.ArrayList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sreejithu.panicker
 */
public class ComponentTest {

    private Mockery mockery;

    @Before
    public void Initialize() {
        mockery = new Mockery();
    }

    @Test
    public void loadPropertiesFileMock() {
        final ConfigLoader configLoader = mockery.mock(ConfigLoader.class);
        mockery.checking(new Expectations() {
            {
                oneOf(configLoader);
                will(returnValue(new ArrayList() {
                    {
                        add("http://feeds.twit.tv/tnt.xml");
                        add("http://feeds.feedburner.com/Whatthetechgfq");
                    }
                }));
            }
        });
        try {
            ArrayList<String> arrayList = configLoader.load();
            System.out.println(arrayList.size());
            arrayList.forEach((String str) -> {
                System.out.printf("Arraylist elements %s \n", str);
            });
        } catch (ConfigLoadException ex) {
            System.out.println(ex);
        }
        mockery.assertIsSatisfied();

    }

    @Test
    public void parserMock() {
        final Parser parser = mockery.mock(Parser.class);
        ArrayList<URL> url = new ArrayList();
        boolean sucess = true;
        mockery.checking(new Expectations() {
            {
                oneOf(parser);

            }
        });
        try {
            parser.setUrl("http://feeds.twit.tv/tnt.xml");
        } catch (ParserException e) {
            sucess = false;
        }
        mockery.assertIsSatisfied();
        assertTrue("Url validation failed !", sucess);
    }

    @Test
    public void parser() {
        Parser parser = new XmlParser();
        boolean success = false;
        ArrayList<String> arraylist = new ArrayList<>();
        try {
            parser.setUrl("http://feeds.twit.tv/tnt.xml");
            arraylist = parser.call();
            success = true;
        } catch (ParserException e) {
            success = false;
            System.out.println(e);
        } catch (Exception io) {
            success = false;
            System.out.println(io);
        }
        assertTrue("Parser failed to parse the XML url !", success);
        assertThat("Parser doen't retuen any item ", arraylist, not(empty()));
    }

    @Test
    public void DownLoadMock() {
        final DownLoad download = mockery.mock(DownLoad.class);
        ArrayList<URL> url = new ArrayList();
        Boolean success = true;
        try {
            mockery.checking(new Expectations() {
                {
                    oneOf(download);
                    will(returnValue(Boolean.TRUE));
                }
            });
            success = download.call();
        } catch (DownLoadException e) {
            System.out.println(e);
            success = false;
        } catch (Exception e) {
            System.out.println(e);
            success = false;
        }
        System.out.println("vaue of success !" + success);
        mockery.assertIsSatisfied();
        assertTrue("Url validation failed !", success);
    }

}
