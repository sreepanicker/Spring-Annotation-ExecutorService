/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sree.podcatcher.component;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author sreejithu.panicker This class uses stax parser
 */
@Component
@Scope("prototype")
public class XmlParser implements Parser, Callable<ArrayList<String>> {
    private Logger logger = Logger.getLogger("com.sree.podcatcher.service.Podcast");
    private URL url;

    @Override
    public void setUrl(String url) throws ParserException {
        try {
            this.url = new URL(url);
        } catch (Exception e) {
            throw new ParserException("Not able to parse the url provided by the configuration");
        }

    }

    /*Actual parser for reeading the content from the enclosed url.
    A Sample item in RSS Feed
    <item>
	<title>TNW 12: Best Interviews of 2017</title>
	<itunes:title>Best Interviews of 2017</itunes:title>
	<itunes:episodeType>full</itunes:episodeType>
	<pubDate>Thu, 28 Dec 2017 12:00:00 PST</pubDate>
		<itunes:episode>12</itunes:episode>
		<link>https://twit.tv/shows/tech-news-weekly/episodes/12</link>
	<comments>https://twit.tv/shows/tech-news-weekly/episodes/12</comments>
	<itunes:author>TWiT</itunes:author>
	<category>Technology</category>
	<category>Tech News</category>
	<category>News</category>
	<itunes:explicit>clean</itunes:explicit>
	<itunes:subtitle>Our favorite interviews from 2017.</itunes:subtitle>
	<itunes:keywords>News, biomason, KRACK, tesla, social, facebook, Twitter, russia, instagram</itunes:keywords>
	<guid isPermaLink="false">http://www.podtrac.com/pts/redirect.mp3/cdn.twit.tv/audio/tnw/tnw0012/tnw0012.mp3</guid>
	<itunes:duration>1:01:39</itunes:duration>
	<enclosure url="http://www.podtrac.com/pts/redirect.mp3/cdn.twit.tv/audio/tnw/tnw0012/tnw0012.mp3" length="29780345" type="audio/mpeg"/>	
    </item>
    */
    @Override
    public ArrayList<String> call() throws ParserException {
        
        logger.debug("Parseing URL :" +url);
        ArrayList<String> list = new ArrayList();
        XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader xmlSTreamReader = xmlFactory.createXMLStreamReader(url.openStream());
            //currently only reading 5 items
            int count =0;
            //reading current tag parsed by the parser
            int tag;
            while( xmlSTreamReader.hasNext()){
                tag = xmlSTreamReader.next();
                switch(tag){
                    case XMLStreamConstants.START_ELEMENT:
                        if (xmlSTreamReader.getLocalName().equalsIgnoreCase("enclosure")) {
                            for (int i = 0; i < xmlSTreamReader.getAttributeCount(); i++) {
                            if (xmlSTreamReader.getAttributeLocalName(i).equalsIgnoreCase("url")) {
                                list.add(xmlSTreamReader.getAttributeValue(i));
                                count++;
                            }
                        }
                        }
                    break;
                }
                if (count > 4) break;
            }
            
        } catch (XMLStreamException io) {
            throw new ParserException("Unable to read XML content from the Stream");
        } catch(Exception e){
             throw new ParserException("Unable to Process the Stream");
        }
        return list;
    }

}
