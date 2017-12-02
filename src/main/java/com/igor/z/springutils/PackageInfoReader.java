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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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

    public List<NuGetPackageInfo> readStreamFromServer(InputStream stream) {
        List<NuGetPackageInfo> result = new ArrayList<>();
        try {
            Document doc = DomReader.readDomDocument(stream);
            result = readPackages(doc);
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<NuGetPackageInfo> readPackages(Document doc) {
        List<NuGetPackageInfo> result = new ArrayList<>();
        NodeList nList = doc.getElementsByTagName("feed").item(0).getChildNodes();
        for (int counter = 0; counter < nList.getLength(); counter++) {
            Node node = nList.item(counter);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            if (node.getNodeName().equals("entry")){
                NuGetPackageInfo info = readEntry(node);
                if (info != null)
                    result.add(info);
            }
        }
        return result;
    }

    private NuGetPackageInfo readEntry(Node root) {
        NodeList nList = root.getChildNodes();
        for (int counter = 0; counter < nList.getLength(); counter++) {
            Node node = nList.item(counter);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            if (node.getNodeName().equals("m:properties"))
                return readNodes(node);
        }
        return null;
    }

    private NuGetPackageInfo readNuspec(InputStream content){
        NuGetPackageInfo info = null;
        if (content != null){
            try {
                Document doc = DomReader.readDomDocument(content);
                Node root = doc.getElementsByTagName("metadata").item(0);
                info = readNodes(root);
            } catch (ParserConfigurationException | IOException e) {
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
        return info;
    }

    private NuGetPackageInfo readNodes(Node root) {
        NuGetPackageInfo result = new NuGetPackageInfo();
        NodeList nList = root.getChildNodes();
        for (int counter = 0; counter < nList.getLength(); counter++){
            Node node = nList.item(counter);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            switch (node.getNodeName())
            {
                case "id":
                case "d:Id":
                    result.setId(node.getTextContent());
                    break;
                case "version":
                case "d:Version":
                    result.setVersion(node.getTextContent());
                    break;
                case "authors":
                case "d:Authors":
                    result.setAuthors(node.getTextContent());
                    break;
                case "owners":
                case "d:Owners":
                    result.setOwners(node.getTextContent());
                    break;
                case "licenseUrl":
                case "d:LicenseUrl":
                    result.setLicenseUrl(node.getTextContent());
                    break;
                case "projectUrl":
                case "d:ProjectUrl":
                    result.setProjectUrl(node.getTextContent());
                    break;
                case "iconUrl":
                case "d:IconUrl":
                    result.setIconUrl(node.getTextContent());
                    break;
                case "requireLicenseAcceptance":
                case "d:RequireLicenseAcceptance":
                    result.setRequireLicenseAcceptance(node.getTextContent());
                    break;
                case "serviceable":
                case "d:Serviceable":
                    result.setServiceable(node.getTextContent());
                    break;
                case "developmentDependency":
                case "d:DevelopmentDependency":
                    result.setDevelopmentDependency(node.getTextContent());
                    break;
                case "description":
                case "d:Description":
                    result.setDescription(node.getTextContent());
                    break;
                case "summary":
                case "d:Summary":
                    result.setSummary(node.getTextContent());
                    break;
                case "releaseNotes":
                case "d:ReleaseNotes":
                    result.setReleaseNotes(node.getTextContent());
                    break;
                case "copyright":
                case "d:Copyright":
                    result.setCopyright(node.getTextContent());
                    break;
                case "language":
                case "d:Language":
                    result.setLanguage(node.getTextContent());
                    break;
                case "title":
                case "d:Title":
                    result.setTitle(node.getTextContent());
                    break;
                case "tags":
                case "d:Tags":
                    result.setTags(node.getTextContent());
                    break;
                case "dependencies":
                case "d:Dependencies":
                    result.setDependencies(node.getTextContent());
                    break;
                case "frameworkAssemblies":
                case "d:FrameworkAssemblies":
                    result.setFrameworkAssemblies(node.getTextContent());
                    break;
                case "references":
                case "d:References":
                    result.setReferences(node.getTextContent());
                    break;
            }
        }
        return result;
    }
}
