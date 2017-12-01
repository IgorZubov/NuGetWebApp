package com.igor.z.daos;

import com.igor.z.modelAttributes.FeedItem;
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
public class JdbcFeedDao implements FeedDao{

    @Autowired
    private DataSource dataSource;

    @Override
    public String insert(FeedItem feed) {
        String query = "insert into feeds (feedname, feedsource, apikey) values (?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        Exception error = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, feed.getFeedName());
            ps.setString(2, feed.getFeedSource());
            ps.setString(3, feed.getApiKey());
            int out = ps.executeUpdate();
            if(out !=0){
                return "Feed " + feed.getFeedName() + " successfully added!";
            } else{
                return "Feed " + feed.getFeedName() + " was not added!";
            }
        }catch(SQLException e){
            error = e;
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "Error occurred: " + error.getMessage() + " !";
    }

    @Override
    public FeedItem findByFeedItemId(int feedId) {
        String query = "select feedname, feedsource, apikey, id from feeds where id = ?";
        FeedItem feed = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, feedId);
            rs = ps.executeQuery();
            if(rs.next()){
                feed = new FeedItem();
                feed.setFeedName(rs.getString("feedname"));
                feed.setFeedSource(rs.getString("feedsource"));
                feed.setApiKey(rs.getString("apikey"));
                feed.setId(feedId);
                System.out.println("Employee Found::"+feed);
            }else{
                System.out.println("No Employee found with id="+feedId);
            }
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
        return feed;
    }

    @Override
    public String update(FeedItem feed, int feedId) {
        String query = "update feeds set feedname=?, feedsource=?, apikey=? where id=?";
        Connection con = null;
        PreparedStatement ps = null;
        Exception error = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, feed.getFeedName());
            ps.setString(2, feed.getFeedSource());
            ps.setString(3, feed.getApiKey());
            ps.setInt(4, feedId);
            int out = ps.executeUpdate();
            if(out !=0){
                return "Feed successfully modified!";
            } else {
                return "Feed was not modified!";
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
        return "Error occurred: " + error.getMessage() + " !";
    }

    @Override
    public String deleteById(int id) {
        String query = "delete from feeds where id=?";
        Connection con = null;
        PreparedStatement ps = null;
        Exception error = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int out = ps.executeUpdate();
            if(out !=0){
                return "Feed successfully deleted!";
            } else {
                return "Feed was not!";
            }
        }catch(SQLException e){
            error = e;
            e.printStackTrace();
        }finally{
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "Error occurred: " + error.getMessage() + " !";
    }

    @Override
    public List<FeedItem> getAll() {
        String query = "select feedname, feedsource, apikey, id from feeds";
        List<FeedItem> feedItems = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                FeedItem feed = new FeedItem();
                feed.setFeedName(rs.getString("feedname"));
                feed.setFeedSource(rs.getString("feedsource"));
                feed.setApiKey(rs.getString("apikey"));
                feed.setId(rs.getInt("id"));
                feedItems.add(feed);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                assert rs != null;
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return feedItems;
    }
}
