package com.igor.z.daos;

import com.igor.z.springutils.NuGetPackageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcPackageDao implements PackageDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public String insert(NuGetPackageInfo packageInfo, String feed) {
        String query = "insert into packages (id, version, authors, owners, licenseUrl, projectUrl, iconUrl, " +
                "requireLicenseAcceptance, serviceable, developmentDependency, description, summary, releaseNotes, " +
                "copyright, language, title, tags, dependencies, frameworkAssemblies, `references`, feedId) " +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, packageInfo.getId());
            ps.setString(2, packageInfo.getVersion());
            ps.setString(3, packageInfo.getAuthors());
            ps.setString(4, packageInfo.getOwners());
            ps.setString(5, packageInfo.getLicenseUrl());
            ps.setString(6, packageInfo.getProjectUrl());
            ps.setString(7, packageInfo.getIconUrl());
            ps.setString(8, packageInfo.getRequireLicenseAcceptance());
            ps.setString(9, packageInfo.getServiceable());
            ps.setString(10, packageInfo.getDevelopmentDependency());
            ps.setString(11, packageInfo.getDescription());
            ps.setString(12, packageInfo.getSummary());
            ps.setString(13, packageInfo.getReleaseNotes());
            ps.setString(14, packageInfo.getCopyright());
            ps.setString(15, packageInfo.getLanguage());
            ps.setString(16, packageInfo.getTitle());
            ps.setString(17, packageInfo.getTags());
            ps.setString(18, packageInfo.getDependencies());
            ps.setString(19, packageInfo.getFrameworkAssemblies());
            ps.setString(20, packageInfo.getReferences());
            ps.setString(21, feed);
            int out = ps.executeUpdate();
            if(out !=0){
                StringBuilder builder = new StringBuilder();
                builder.append("Package ").append(packageInfo.getId())
                        .append(" with version ").append(packageInfo.getVersion())
                        .append(" added successfully!");
                return builder.toString();
            } else {
                StringBuilder builder = new StringBuilder();
                builder.append("Package ").append(packageInfo.getId())
                        .append(" with version ").append(packageInfo.getVersion())
                        .append(" addition failed!");
                return builder.toString();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Package ").append(packageInfo.getId())
                .append(" with version ").append(packageInfo.getVersion())
                .append(" addition failed!");
        return builder.toString();
    }

    @Override
    public List<NuGetPackageInfo> findByAny(String searchExp) {
        String query = "SELECT * FROM packages WHERE MATCH (id, version, authors, owners, licenseUrl, projectUrl, " +
                "serviceable, developmentDependency, description, summary, releaseNotes, " +
                "title, tags, dependencies, frameworkAssemblies, `references`) " +
                "AGAINST (? IN NATURAL LANGUAGE MODE)";

        List<NuGetPackageInfo> packages = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, searchExp);
            rs = ps.executeQuery();
            packages = createListFromResponse(rs);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return packages;
    }

    @Override
    public List<NuGetPackageInfo> getAll() {
        String query = "SELECT * FROM packages";

        List<NuGetPackageInfo> packages = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            packages = createListFromResponse(rs);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return packages;
    }

    @Override
    public List<NuGetPackageInfo> getAllPackagesFromFeed(String feedSource) {
        String query = "SELECT * FROM packages WHERE feedId=?";
        List<NuGetPackageInfo> packages = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, feedSource);
            rs = ps.executeQuery();
            packages = createListFromResponse(rs);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return packages;
    }

    @Override
    public List<NuGetPackageInfo> findByAnyFromFeed(String feedSource, String searchExpression) {
        String query = "SELECT * FROM packages WHERE MATCH (id, version, authors, owners, licenseUrl, projectUrl, " +
                "serviceable, developmentDependency, description, summary, releaseNotes, " +
                "title, tags, dependencies, frameworkAssemblies, `references`) " +
                "AGAINST (? IN NATURAL LANGUAGE MODE) AND feedId = ?";

        List<NuGetPackageInfo> packages = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, searchExpression);
            ps.setString(2, feedSource);
            rs = ps.executeQuery();
            packages = createListFromResponse(rs);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return packages;
    }

    private List<NuGetPackageInfo> createListFromResponse(ResultSet rs) throws SQLException {
        List<NuGetPackageInfo> packages = new ArrayList<>();
        while (rs.next()){
            NuGetPackageInfo info = new NuGetPackageInfo();
            info.setId(rs.getString("id"));
            info.setVersion(rs.getString("version"));
            info.setAuthors(rs.getString("authors"));
            info.setOwners(rs.getString("owners"));
            info.setLicenseUrl(rs.getString("licenseUrl"));
            info.setProjectUrl(rs.getString("projectUrl"));
            info.setIconUrl(rs.getString("iconUrl"));
            info.setReferences(rs.getString("requireLicenseAcceptance"));
            info.setServiceable(rs.getString("serviceable"));
            info.setDevelopmentDependency(rs.getString("developmentDependency"));
            info.setDescription(rs.getString("description"));
            info.setSummary(rs.getString("summary"));
            info.setReleaseNotes(rs.getString("releaseNotes"));
            info.setCopyright(rs.getString("copyright"));
            info.setLanguage(rs.getString("language"));
            info.setTitle(rs.getString("title"));
            info.setTags(rs.getString("tags"));
            info.setDependencies(rs.getString("dependencies"));
            info.setFrameworkAssemblies(rs.getString("frameworkAssemblies"));
            info.setReferences(rs.getString("references"));
            packages.add(info);
        }
        return packages;
    }
}
