package com.igor.z.utils;

import com.igor.z.interfaces.INuGetCommandsWrapper;
import com.igor.z.interfaces.ISettingsReader;
import com.igor.z.modelAttributes.FeedItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NuGetCommandsWrapper implements INuGetCommandsWrapper {

    private String _nugetExecutablePath;
    private String _nugetConfigPath;
    private String _feedCommands = "sources";
    private String _add = "add";
    private String _push = "push";
    private String _remove = "remove";
    private String _list = "list";
    private String _name = "-name";
    private String _source = "-source";
    private String _config = "-configfile";
    private String _forceEnglish = "-ForceEnglishOutput";
    private String _verbosity = "-Verbosity";
    private String _detailed = "detailed";

    public NuGetCommandsWrapper(){
        ISettingsReader reader = new SettingsReader();
        _nugetConfigPath = reader.getNuGetConfigPath();
        _nugetExecutablePath = reader.getNuGetExecutablePath();
    }

    public NuGetCommandsWrapper(ISettingsReader reader) {
        _nugetConfigPath = reader.getNuGetConfigPath();
        _nugetExecutablePath = reader.getNuGetExecutablePath();
    }

    @Override
    public int createNewFeed(FeedItem feed, List<String> returnMessage) {
        ProcessBuilder pb = new ProcessBuilder(
                _nugetExecutablePath,
                _feedCommands, _add, _name, feed.getFeedName(),
                _source, feed.getFeedSource(), _config, _nugetConfigPath, _forceEnglish);
        int exitCode = safeExecutingNuGetCommand(returnMessage, pb);
        return exitCode;
    }

    private int safeExecutingNuGetCommand(List<String> returnMessage, ProcessBuilder pb) {
        int exitCode;
        try {
            exitCode = runNuGetCommand(returnMessage, pb);
        } catch (IOException e) {
            exitCode =-1;
            returnMessage.add("Error occurred in running nuget command");
        } catch (InterruptedException e) {
            exitCode =-1;
            returnMessage.add("Error occurred in running nuget command");
        }
        return exitCode;
    }

    @Override
    public int getFeedList(List<FeedItem> feedList, List<String> returnMessage) {
        returnMessage.clear();
        ProcessBuilder pb = new ProcessBuilder(
                _nugetExecutablePath,
                _feedCommands, _list, _config, _nugetConfigPath, _forceEnglish);
        int exitCode = safeExecutingNuGetCommand(returnMessage, pb);
        if (exitCode == 0){
            Pattern pattern = Pattern.compile("(?:\\s+\\d+\\.\\s+)(.+)(\\[.+)");
            Iterator<String> iterator = returnMessage.iterator();
            while (iterator.hasNext()){
                String line1 = iterator.next();
                Matcher matcher = pattern.matcher(line1);
                if (matcher.matches()) {
                    FeedItem item = new FeedItem();
                    item.setFeedName(matcher.group(1).trim());
                    item.setFeedSource(iterator.next().trim());
                    feedList.add(item);
                }
            }
        }
        return exitCode;
    }

    @Override
    public int removeFeed(FeedItem feed, List<String> returnMessage) {
        returnMessage.clear();
        ProcessBuilder pb = new ProcessBuilder(
                _nugetExecutablePath,
                _feedCommands, _remove, _name, "\""+ feed.getFeedName()+"\"", _config, _nugetConfigPath, _forceEnglish);
        int exitCode = safeExecutingNuGetCommand(returnMessage, pb);
        return exitCode;
    }

    @Override
    public int modifyFeed(FeedItem oldFeed, FeedItem newFeed, List<String> returnMessage) {
        returnMessage.clear();
        int exitCode1 = removeFeed(oldFeed, returnMessage);
        returnMessage.clear();
        int exitCode2 = createNewFeed(newFeed, returnMessage);
        if (exitCode1+exitCode2 != 0)
            createNewFeed(oldFeed, new ArrayList<>());
        return exitCode1+exitCode2;
    }

    @Override
    public int addPackageToFeed(String packagePath, String selectedFeedSource, List<String> returnMessage) {
        returnMessage.clear();
        ProcessBuilder pb = new ProcessBuilder(
                _nugetExecutablePath,
                _add, packagePath, _source, selectedFeedSource, _config, _nugetConfigPath, _forceEnglish);
        int exitCode = safeExecutingNuGetCommand(returnMessage, pb);
        return exitCode;
    }

    @Override
    public int pushPackage(String feedSource, String apiKey, String packagePath, List<String> returnMessage) {
        returnMessage.clear();
        ProcessBuilder pb = new ProcessBuilder(
                _nugetExecutablePath,
                _push, packagePath, apiKey, _source, feedSource, _forceEnglish);
        int exitCode = safeExecutingNuGetCommand(returnMessage, pb);
        return exitCode;
    }

    @Override
    public int searchPackage(String searchExp, List<PackageInfo> packageInfoList, List<String> returnMessage)  {
        returnMessage.clear();
        Pattern pattern = Pattern.compile("\\d+\\.\\d+\\.\\d+\\-*.*");
        String search = searchExp;
        boolean searchForVersion = false;
        if (pattern.matcher(searchExp.trim()).matches()){
            search = "";
            searchForVersion = true;
        }
        ProcessBuilder pb = new ProcessBuilder(
                _nugetExecutablePath,
                _list, search, _verbosity, _detailed, _config, _nugetConfigPath, _forceEnglish);
        int exitCode = safeExecutingNuGetCommand(returnMessage, pb);
        if (exitCode == 0){
            if(searchForVersion)
                packageInfoList.addAll(searchVersion(returnMessage, searchExp));
            else
                packageInfoList.addAll(parsePackageInfos(returnMessage));
        }
        return exitCode;
    }

    private List<PackageInfo> searchVersion(List<String> returnMessage, final String searchExp) {
        List<PackageInfo> allPackages = parsePackageInfos(returnMessage);
        return allPackages.stream().filter(pack -> pack.getVersion().equals(searchExp)).collect(Collectors.toList());
    }

    private List<PackageInfo> parsePackageInfos(List<String> resultMessage) {
        List<PackageInfo> packages = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+\\.\\d+\\.\\d+\\-*.*");
        ListIterator<String> iterator = resultMessage.listIterator();
        while (iterator.hasNext()){
            String version = iterator.next().trim();
            if (pattern.matcher(version).matches() && iterator.hasPrevious()){
                iterator.previous();
                String name = iterator.previous();
                packages.add(new PackageInfo(name, version));
                iterator.next();
                iterator.next();
                String nextVersion =  iterator.next().trim();
                StringBuilder descriptionBuilder = new StringBuilder();
                while (iterator.hasNext() && !pattern.matcher(nextVersion).matches()){
                    descriptionBuilder.append(nextVersion).append(" ");
                    nextVersion =  iterator.next().trim();
                }
                if (!iterator.hasNext()){
                    packages.get(packages.size() - 1).setDescription(descriptionBuilder.toString());
                    break;
                }
                iterator.previous();
                String nextName = iterator.previous().trim();
                descriptionBuilder.delete(descriptionBuilder.length() - nextName.length() - 1, descriptionBuilder.length()-1);
                packages.get(packages.size() - 1).setDescription(descriptionBuilder.toString());
            }
        }
        return packages;
    }

    private int runNuGetCommand(List<String> returnMessage, ProcessBuilder pb) throws IOException, InterruptedException {
        String line;
        Process p = pb.start();
        int exitCode = p.waitFor();
        if (exitCode == 0){
            BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = bri.readLine()) != null) {
                returnMessage.add(line);
            }
            bri.close();
        } else{
            BufferedReader bri = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = bri.readLine()) != null) {
                returnMessage.add(line);
            }
            bri.close();
        }
        return exitCode;
    }
}
