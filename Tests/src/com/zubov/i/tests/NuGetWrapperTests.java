package com.zubov.i.tests;

import com.igor.z.interfaces.INuGetCommandsWrapper;
import com.igor.z.interfaces.ISettingsReader;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.utils.NuGetCommandsWrapper;
import com.igor.z.utils.PackageInfo;
import com.zubov.i.tests.utils.UnDisposableStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NuGetWrapperTests {

    private ISettingsReader mockReader;
    private INuGetCommandsWrapper wrapper;
    private UnDisposableStream content;
    private String folderPath;
    private String configCopy;
    private static final String PilotSDK = "Ascon.Pilot.SDK.0.0.0.0.nupkg";
    private static final String Log4Net = "log4net.2.0.3.nupkg";
    private static final String Unity = "Unity.2.1.505.2.nupkg";

    @Before
    public void setup() {
        mockReader = mock(ISettingsReader.class);
        ClassLoader classLoader = getClass().getClassLoader();
        URL conf = classLoader.getResource("resources/test.Config");
        URL exe = classLoader.getResource("resources/nuget.exe");
        File confFile = new File(conf.getPath().substring(1));
        Path source = confFile.toPath();
        configCopy = confFile.getParentFile().getPath()+"/copy.config";
        Path target = Paths.get(confFile.getParentFile().getPath()+"/copy.config");
        try {
            Files.copy(source, target, REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        when(mockReader.getNuGetConfigPath()).thenReturn(target.toString());
        when(mockReader.getNuGetExecutablePath()).thenReturn(exe.getPath().substring(1));
        File f = new File(exe.getPath().substring(1));
        folderPath = f.getParentFile().getPath();
        when(mockReader.getTmpUploadPath()).thenReturn(folderPath +"/tmp");
        wrapper = new NuGetCommandsWrapper(mockReader);
    }

    @After
    public void after() {
        wrapper = null;
        try {
            if (content != null)
                content.realClose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File f = new File(configCopy);
        if (f.exists())
            f.delete();
        Path directory = Paths.get(folderPath +"/source");
        if (!directory.toFile().exists())
            return;
        try {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldManipulateFeedListCorrect(){
        List<String> result = new ArrayList<>();
        List<FeedItem> feeds = new ArrayList<>();
        int res = wrapper.getFeedList(feeds, result);
        assertEquals(0, res);
        assertEquals(0, feeds.size());
        FeedItem item = new FeedItem();
        item.setFeedName("feed");
        item.setFeedSource("source");
        res = wrapper.createNewFeed(item, result);
        assertEquals(0, res);
        res = wrapper.getFeedList(feeds, result);
        assertEquals(0, res);
        assertEquals(1, feeds.size());
        assertEquals(item.getFeedName(), feeds.get(0).getFeedName());
        res = wrapper.removeFeed(item, result);
        assertEquals(0, res);
        feeds.clear();
        res = wrapper.getFeedList(feeds, result);
        assertEquals(0, res);
        assertEquals(0, feeds.size());
    }

    @Test
    public void shouldModifyFeed(){
        List<String> result = new ArrayList<>();
        List<FeedItem> feeds = new ArrayList<>();
        int res = wrapper.getFeedList(feeds, result);
        assertEquals(0, res);
        assertEquals(0, feeds.size());
        FeedItem item = new FeedItem();
        item.setFeedName("feed");
        item.setFeedSource("source");
        FeedItem newItem = new FeedItem();
        newItem.setFeedName("feed1");
        newItem.setFeedSource("source");
        FeedItem newItem2 = new FeedItem();
        newItem2.setFeedName("feed1");
        newItem2.setFeedSource("source1");
        res = wrapper.createNewFeed(item, result);
        assertEquals(0, res);
        res = wrapper.getFeedList(feeds, result);
        assertEquals(0, res);
        assertEquals(1, feeds.size());
        assertEquals(item.getFeedName(), feeds.get(0).getFeedName());
        feeds.clear();
        res = wrapper.modifyFeed(item, newItem, result);//change name
        assertEquals(0, res);
        res = wrapper.getFeedList(feeds, result);
        assertEquals(0, res);
        assertEquals(1, feeds.size());
        assertEquals(newItem.getFeedName(), feeds.get(0).getFeedName());
        feeds.clear();
        res = wrapper.modifyFeed(newItem, newItem2, result);//change source
        assertEquals(0, res);
        res = wrapper.getFeedList(feeds, result);
        assertEquals(0, res);
        assertEquals(1, feeds.size());
        assertEquals(newItem2.getFeedName(), feeds.get(0).getFeedName());
        feeds.clear();
        res = wrapper.removeFeed(newItem2, result);//delete
        assertEquals(0, res);
        res = wrapper.getFeedList(feeds, result);
        assertEquals(0, res);
        assertEquals(0, feeds.size());
    }

    @Test
    public void shouldAddPackage(){
        FeedItem item = new FeedItem();
        item.setFeedName("feed");
        item.setFeedSource("source");
        List<String> result = new ArrayList<>();
        int res = wrapper.createNewFeed(item, result);
        assertEquals(0, res);
        List<FeedItem> list = new ArrayList<>();
        res = wrapper.getFeedList(list, result);
        assertEquals(0, res);
        assertEquals(1, list.size());
        res = wrapper.addPackageToFeed(folderPath +"\\"+PilotSDK, list.get(0).getFeedSource(), result);
        assertEquals(0, res);
        res = wrapper.removeFeed(item, result);
        assertEquals(0, res);
    }

    @Test
    public void shouldAddMultiplePackageAndFind(){
        FeedItem item = new FeedItem();
        item.setFeedName("feed");
        item.setFeedSource("source");
        List<String> result = new ArrayList<>();
        int res = wrapper.createNewFeed(item, result);
        assertEquals(0, res);
        List<FeedItem> list = new ArrayList<>();
        res = wrapper.getFeedList(list, result);
        assertEquals(0, res);
        assertEquals(1, list.size());
        res = wrapper.addPackageToFeed(folderPath +"\\"+PilotSDK, list.get(0).getFeedSource(), result);
        assertEquals(0, res);
        res = wrapper.addPackageToFeed(folderPath +"\\"+Log4Net, list.get(0).getFeedSource(), result);
        assertEquals(0, res);
        List<PackageInfo> packages = new ArrayList<>();
        res = wrapper.searchPackage("", packages, result);
        assertEquals(0, res);
        assertEquals(2, packages.size());
        packages.clear();
        res = wrapper.searchPackage("Pilot", packages, result);
        assertEquals(0, res);
        assertEquals(1, packages.size());
        assertEquals("Ascon.Pilot.SDK", packages.get(0).getName());
        packages.clear();
        res = wrapper.searchPackage("2.0.3", packages, result);
        assertEquals(0, res);
        assertEquals(1, packages.size());
        assertEquals("log4net", packages.get(0).getName());
        assertEquals("2.0.3", packages.get(0).getVersion());
        //
        res = wrapper.removeFeed(item, result);
        assertEquals(0, res);
    }

}
