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
    public void insert(FeedItem feed) {
        String query = "insert into feeds (feedname, feedsource, apikey) values (?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, feed.getFeedName());
            ps.setString(2, feed.getFeedSource());
            ps.setString(3, feed.getApiKey());
            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Employee saved with id="+feed.getFeedName());
            }else System.out.println("Employee save failed with id="+feed.getFeedName());
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
    public void update(FeedItem feed) {
        String query = "update feeds set feedname=?, feedsource=?, apikey=? where feedname=?";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, feed.getFeedName());
            ps.setString(2, feed.getFeedSource());
            ps.setString(3, feed.getApiKey());
            ps.setString(4, feed.getFeedName());
            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Employee updated with id="+feed.getFeedName());
            }else System.out.println("No Employee found with id="+feed.getFeedName());
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
    }

    @Override
    public void deleteById(int id) {
        String query = "delete from feeds where id=?";
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int out = ps.executeUpdate();
            if(out !=0){
                System.out.println("Employee deleted with id="+id);
            }else System.out.println("No Employee found with id="+id);
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
