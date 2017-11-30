package com.igor.z.springutils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackageInfoReader {

    public NuGetPackageInfo readPackage(String packagePath){
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(packagePath);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            ZipEntry nuspec = null;
            while(entries.hasMoreElements()){
                ZipEntry next = entries.nextElement();
                if (next.getName().endsWith(".nuspec")){
                    nuspec = next;
                    break;
                }
            }
            return readNuspec(zipFile.getInputStream(nuspec));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (zipFile != null)
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    private NuGetPackageInfo readNuspec(InputStream content){
        NuGetPackageInfo info = null;
        if (content != null){
            try {
                Document doc = readDomDocument(content);
                info = readNodes(doc);
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
                return info;
            }
        }
        return info;
    }

    private NuGetPackageInfo readNodes(Document doc) {
        NuGetPackageInfo result = new NuGetPackageInfo();
        NodeList nList = doc.getElementsByTagName("metadata").item(0).getChildNodes();
        for (int counter = 0; counter < nList.getLength(); counter++){
            Node node = nList.item(counter);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            switch (node.getNodeName())
            {
                case "id":
                    result.setId(node.getTextContent());
                    break;
                case "version":
                    result.setVersion(node.getTextContent());
                    break;
                case "authors":
                    result.setAuthors(node.getTextContent());
                    break;
                case "owners":
                    result.setOwners(node.getTextContent());
                    break;
                case "licenseUrl":
                    result.setLicenseUrl(node.getTextContent());
                    break;
                case "projectUrl":
                    result.setProjectUrl(node.getTextContent());
                    break;
                case "iconUrl":
                    result.setIconUrl(node.getTextContent());
                    break;
                case "requireLicenseAcceptance":
                    result.setRequireLicenseAcceptance(node.getTextContent());
                    break;
                case "serviceable":
                    result.setServiceable(node.getTextContent());
                    break;
                case "developmentDependency":
                    result.setDevelopmentDependency(node.getTextContent());
                    break;
                case "description":
                    result.setDescription(node.getTextContent());
                    break;
                case "summary":
                    result.setSummary(node.getTextContent());
                    break;
                case "releaseNotes":
                    result.setReleaseNotes(node.getTextContent());
                    break;
                case "copyright":
                    result.setCopyright(node.getTextContent());
                    break;
                case "language":
                    result.setLanguage(node.getTextContent());
                    break;
                case "title":
                    result.setTitle(node.getTextContent());
                    break;
                case "tags":
                    result.setTags(node.getTextContent());
                    break;
                case "dependencies":
                    result.setDependencies(node.getTextContent());
                    break;
                case "frameworkAssemblies":
                    result.setFrameworkAssemblies(node.getTextContent());
                    break;
                case "references":
                    result.setReferences(node.getTextContent());
                    break;
            }
        }
        return result;
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
