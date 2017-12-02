package com.igor.z.nugetImplementations;

import com.igor.z.daos.FeedDao;
import com.igor.z.daos.PackageDao;
import com.igor.z.interfaces.INuGetCommandsWrapper;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.springutils.NuGetPackageInfo;
import com.igor.z.springutils.PackageInfoReader;
import com.igor.z.utils.NuGetCommandsWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class ServerNuget implements Nuget {

    private INuGetCommandsWrapper wrapper;

    public ServerNuget() {
        this.wrapper = new NuGetCommandsWrapper();
    }

    @Override
    public String addFeedSource(FeedDao feedDao, FeedItem feedItem) {
        if (remoteFeedIsAvailable(feedItem.getFeedSource()))
            return feedDao.insert(feedItem);
        return "Feed source is not available or misspelled!";
    }

    @Override
    public String modifyFeedSource(FeedDao feedDao, FeedItem feedItem, Integer id) {
        if (remoteFeedIsAvailable(feedItem.getFeedSource()))
            return feedDao.update(feedItem, id);
        return "Feed source is not available or misspelled!";
    }

    @Override
    public String deleteFeedSource(FeedDao feedDao, int id) {
        return feedDao.deleteById(id);
    }

    @Override
    public String addPackageToSource(String packagePath, FeedItem feed, PackageDao packageDao) {
        if (remoteFeedIsAvailable(feed.getFeedSource())){
            List<String> messages =  new ArrayList<>();
            if (wrapper.pushPackage(feed.getFeedSource(), feed.getApiKey(), packagePath, messages) == 0){
                PackageInfoReader reader = new PackageInfoReader();
                NuGetPackageInfo info = reader.readPackage(packagePath);
                return packageDao.insert(info, feed.getFeedSource());
            }
            return messages.size() > 1 ? messages.get(messages.size() - 1) : "Some error occurred!";
        }
        return "Feed source is not available or misspelled!";
    }

    @Override
    public List<NuGetPackageInfo> searchForPackagesInEveryFeed(String searchExp, PackageDao packageDao) {
        return packageDao.findByAny(searchExp);
    }

    @Override
    public List<NuGetPackageInfo> getAllPackages(PackageDao packageDao) {
        return packageDao.getAll();
    }

    @Override
    public List<NuGetPackageInfo> getAllPackagesFromFeed(String feedSource, PackageDao packageDao) {
        return packageDao.getAllPackagesFromFeed(feedSource);
    }

    @Override
    public List<NuGetPackageInfo> searchForPackagesInExactFeed(String feedSource, String searchExpression, PackageDao packageDao) {
        return packageDao.findByAnyFromFeed(feedSource, searchExpression);
    }

    @Override
    public String syncFeed(PackageDao packageDao, FeedItem feed) {
        InputStream stream = getStreamWithPackagesInfo(feed.getFeedSource() + "/Packages");
        if (stream != null){
            PackageInfoReader reader = new PackageInfoReader();
            List<NuGetPackageInfo> list = reader.readStreamFromServer(stream);
            if (list.size() > 0){
                packageDao.deleteAll();
                for(NuGetPackageInfo info : list){
                    packageDao.insert(info, feed.getFeedName());
                }
            }
            return list.size() + " packages were synchronised";
        }
        return "Synchronisation failed!";
    }

    @Override
    public List<FeedItem> getAllFeeds(FeedDao feedDao) {
        return feedDao.getAll();
    }

    @Override
    public FeedItem getFeedById(FeedDao feedDao, int feedId) {
        return feedDao.findByFeedItemId(feedId);
    }

    private boolean remoteFeedIsAvailable(String feedSource) {
        try {
            URL obj = new URL(feedSource);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            return  con.getResponseCode() == 200;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private InputStream getStreamWithPackagesInfo(String feedSource) {
        try {
            URL obj = new URL(feedSource);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            return con.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
