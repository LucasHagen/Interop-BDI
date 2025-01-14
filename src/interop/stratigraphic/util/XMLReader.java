/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interop.stratigraphic.util;

import interop.stratigraphic.model.StratigraphicDescription;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Luan
 */
public class XMLReader {

    public static List<StratigraphicDescription> readStratigraphicDescriptionXML(String pathFile) {

        List<StratigraphicDescription> descriptionsList = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            XMLHandler handler = new XMLHandler();

            saxParser.parse(pathFile, handler);

            descriptionsList = handler.getDescriptionsList();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        for(StratigraphicDescription description : descriptionsList)
            description.setFilePath(pathFile);

        return descriptionsList;
    }


}
