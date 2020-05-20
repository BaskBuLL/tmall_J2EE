package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Util.DBUtil;
import Util.DateUtil;
import tmall.Product;
import tmall.Review;
import tmall.User;

public class ReviewDAO {
    
    public void add(Review r) {
    	String sql ="INSERT into review values(null,?,?,?,?)";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);){
    		ps.setString(1,r.getContent());
    		ps.setInt(2, r.getUser().getId());
    		ps.setInt(3, r.getProduct().getId());
    		ps.setTimestamp(4, DateUtil.dateToTimestamp(r.getCreateDate()));
    		ps.execute();
    		ResultSet rs = ps.getGeneratedKeys();
    		if(rs.next()) {
    			int id = rs.getInt(1);
    			r.setId(id);
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void update(Review r) {
    	String sql="update review set content = ? , uid =?,pid=?,createDate = ? where id =?";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);){
    		ps.setString(1, r.getContent());
    		ps.setInt(2, r.getUser().getId());
    		ps.setInt(3, r.getProduct().getId());
    		ps.setTimestamp(4, DateUtil.dateToTimestamp(r.getCreateDate()));
    		ps.setInt(5, r.getId());
    		ps.execute();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void delete(int id) {
    	String sql ="DELETE from review where id = ?";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, id);
    		ps.execute();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Review get(int id) {
    	Review r = null;
    	String sql ="select * from review where id = ?";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, id);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			r=new Review();
    			int pid = rs.getInt("pid");
    			int uid = rs.getInt("uid");
    			Product product = new ProductDAO().get(pid);
    			User user = new UserDAO().get(uid);
    			String content = rs.getString("content");
    			Date createDate = DateUtil.TimestampToDate(rs.getTimestamp("createDate"));
    			r.setContent(content);
    			r.setCreateDate(createDate);
    			r.setUser(user);
    			r.setProduct(product);
    			r.setId(id);
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return r;
    }
    
    public int getCount(int pid) {
    	int count = 0;
    	String sql ="select count(*) from review where pid = ?";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, pid);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			count=rs.getInt(1);
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return count;
    }
    
    public List<Review> list(int pid,int start,int count){
    	List<Review> reviews = new ArrayList<>();
    	String sql="select * from review where pid = ? order by id desc limit ?,?";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, pid);
    		ps.setInt(2,start);
    		ps.setInt(3, count);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			Review r = new Review();
    			int id = rs.getInt(1);
    			int uid = rs.getInt("uid");
    			Date createDate = DateUtil.TimestampToDate(rs.getTimestamp("createDate"));
    			String content = rs.getString("content");
    			Product product = new ProductDAO().get(pid);
    			User user = new UserDAO().get(uid);
    			r.setContent(content);
    			r.setProduct(product);
    			r.setCreateDate(createDate);
    			r.setUser(user);
    			r.setId(id);
    			reviews.add(r);
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  return reviews;
    }
    
    public List<Review> list(int pid){
    	return list(pid,0,Short.MAX_VALUE);
    }
    
    public boolean isExist(String content,int pid) {
    	String sql ="select * from Review where content = ? and pid =?";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setString(1, content);
    		ps.setInt(2, pid);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			return true;
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  return false;
    }
}
