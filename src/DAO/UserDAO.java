package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Util.DBUtil;
import tmall.User;

public class UserDAO {
   public int getTotal() {
	   int total=0;
		String sql ="select count(*) from user";
	   try( Connection con=DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);) {
		  ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			total=rs.getInt(1);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	   return total;
   }
   
   public void add(User user) {
	   ResultSet rs =null;
	   String sql="INSERT into user value (null,?,?)";
	   try( Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
		ps.setString(1, user.getName());
		ps.setString(2, user.getPassword());
		ps.execute();
		rs=ps.getGeneratedKeys();
		if(rs.next()) {
			int id=rs.getInt(1);
			user.setId(id);//传user的时候id
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
   
   public void update(User user) {
		Connection con=null;
		PreparedStatement ps=null;
	    try {
			con=DBUtil.getConnection();
			String sql="UPDATE user set name = ?,password = ? where id = ?";
			ps=con.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.release(ps, con);
		}
   }
   
   public void delete(int id) {
	   Connection con=null;
	   PreparedStatement ps=null;
	   
	   try {
		con=DBUtil.getConnection();
		String sql ="DELETE FROM user where id=?";
		ps=con.prepareStatement(sql);
		ps.setInt(1, id);
		ps.execute();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		DBUtil.release(ps, con);
	}
	   
   }
   
   public User get(int id) {
	   User user =null;
	   String sql="select * from user where id = ?";
	   try (Connection con=DBUtil.getConnection(); PreparedStatement ps=con.prepareStatement(sql); ){
		ps.setInt(1, id);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			user=new User();
			String name =rs.getString("name");
			String password=rs.getString("password");
			user.setId(id);
			user.setName(name);
			user.setPassword(password);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return user;
   }
   
   public List<User> list(int start,int count){
	   List<User> users=new ArrayList<>();
	   String sql ="select * from user order by id desc limit ?,?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);) {

		ps.setInt(1, start);
		ps.setInt(2, count);
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			User user = new User();
			int id =rs.getInt(1);
			String name=rs.getString("name");
			String password=rs.getString("password");
			user.setId(id);
			user.setName(name);
			user.setPassword(password);
			users.add(user);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	   return users;
   }
   
   public List<User> list(){
	   return list(0,Short.MAX_VALUE);
   }
   
   public User get(String name) {
	   User user = null;
	   ResultSet rs =null;
	   Connection con=null;
	   PreparedStatement ps=null;
	   try {
		con=DBUtil.getConnection();
		String sql="select * from user where name = ?";
		ps=con.prepareStatement(sql);
		ps.setString(1, name);
		rs=ps.executeQuery();
		if(rs.next()) {
			user=new User();
			int id = rs.getInt("id");
			String password=rs.getString("password");
			user.setId(id);
			user.setName(name);
			user.setPassword(password);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		DBUtil.release(rs, ps, con);
	}
	   return user;
   }
   
   public User get(String name,String password) {
	   User user =null;
	   String sql="select * from user where name = ?and password = ?";
	   try(Connection con=DBUtil.getConnection(); PreparedStatement ps=con.prepareStatement(sql);) {
		ps.setString(1, name);
		ps.setString(2, password);
		 ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			 user=new User();
			int id = rs.getInt(1);
			user.setId(id);
			user.setName(name);
			user.setPassword(password);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	   return user;
   }
   
   public boolean isExist(String name) {
	   User user = get(name);
	   return (user!=null);
   }
}
