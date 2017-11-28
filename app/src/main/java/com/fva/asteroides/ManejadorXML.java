package com.fva.asteroides;

import org.xml.sax.SAXException;

import java.util.jar.Attributes;

/**
 * Created by DTIC-Dir on 28/11/2017.
 */

interface ManejadorXML {
    void startElement(String uri, String nombreLocal, String nombreCualif, Attributes atr) throws SAXException;
}
