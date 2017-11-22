package com.igor.z.utils;

import com.igor.z.interfaces.ISettingsReader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class SettingsReader implements ISettingsReader {

    public String getNuGetExecutablePath(){
        InputStream content = FacesContext.getCurrentInstance().getExternalContext().
                getResourceAsStream("/res/settings.xml");
        if (content != null){
            try {
                Document doc = readDomDocument(content);
                String path = readPath("execNuGet", doc);
                return path;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    content.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String getNuGetConfigPath(){
        InputStream content = FacesContext.getCurrentInstance().getExternalContext().
                getResourceAsStream("/res/settings.xml");
        if (content != null){
            try {
                Document doc = readDomDocument(content);
                String path = readPath("configNuGet", doc);
                return path;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }finally {
                try {
                    content.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String getTmpUploadPath(){
        InputStream content = FacesContext.getCurrentInstance().getExternalContext().
                getResourceAsStream("/res/settings.xml");
        if (content != null){
            try {
                Document doc = readDomDocument(content);
                String path = readPath("uploadTmpFolder", doc);
                return path;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }finally {
                try {
                    content.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private String readPath(String nodeName, Document doc) {
        NodeList nList = doc.getElementsByTagName(nodeName);
        Node nNode = nList.item(0);
        return nNode.getAttributes().getNamedItem("path").getTextContent();
    }

    private Document readDomDocument(InputStream content) throws ParserConfigurationException, IOException,
            SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(content);
        doc.getDocumentElement().normalize();
        return doc;
    }
}
