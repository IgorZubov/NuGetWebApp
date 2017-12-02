package com.igor.z.utils;

import com.igor.z.interfaces.ISettingsReader;
import com.igor.z.nugetImplementations.NugetImplementation;
import com.igor.z.springutils.DomReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class SettingsReader implements ISettingsReader {

    public String getNuGetExecutablePath(){
        Resource resource = new ClassPathResource("/resources/settings.xml");
        InputStream content = null;
        try {
            content = resource.getInputStream();
            Document doc = DomReader.readDomDocument(content);
            return readValue("execNuGet", doc);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert content != null;
                content.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getNuGetConfigPath(){
        Resource resource = new ClassPathResource("/resources/settings.xml");
        InputStream content = null;
        try {
            content = resource.getInputStream();
            Document doc = DomReader.readDomDocument(content);
            return readValue("configNuGet", doc);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert content != null;
                content.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getTmpUploadPath(){
        Resource resource = new ClassPathResource("/resources/settings.xml");
        InputStream content = null;
        try {
            content = resource.getInputStream();
            Document doc = DomReader.readDomDocument(content);
            return readValue("uploadTmpFolder", doc);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (content != null)
                    content.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public NugetImplementation getNuGetImplementation() {
        Resource resource = new ClassPathResource("/resources/settings.xml");
        InputStream content = null;
        try {
            content = resource.getInputStream();
            Document doc = DomReader.readDomDocument(content);
            String path = readValue("nuGetImpl", doc);
            return NugetImplementation.valueOf(path);
        } catch (ParserConfigurationException | IllegalArgumentException | SAXException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (content != null)
                    content.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return NugetImplementation.EMPTY_NUGET;
    }

    private String readValue(String nodeName, Document doc) {
        NodeList nList = doc.getElementsByTagName(nodeName);
        Node nNode = nList.item(0);
        return nNode.getAttributes().getNamedItem("path").getTextContent();
    }
}
