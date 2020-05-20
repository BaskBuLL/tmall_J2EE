package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Util.DBUtil;
import tmall.Category;

public class CategoryDAO {
   public int getTotal() {
	   int total=0;
	   try( Connection con=DBUtil.getConnection();Statement s=con.createStatement();) {
		String sql="select count(*) from category";
		ResultSet rs=s.executeQuery(sql);
		while(rs.next()) {
			total=rs.getInt(1);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return total;
   }
	
   public void add(Category category) {
		String sql ="insert into category values(null,?)";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {		
		ps.setString(1, category.getName());
		ps.execute();
		ResultSet rs=ps.getGeneratedKeys();
		if(rs.next()) {
			int id=rs.getInt(1);
			category.setId(id);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
   }
   
   public void delete(int id) {
	   String sql="delete from Category where id=?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);) {
	    ps.setInt(1, id);
	    ps.execute();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
   }
   
   public void update(Category category) {
	   String sql="update category SET name=? where id = ?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);) {
		ps.setString(1, category.getName());
		ps.setInt(2, category.getId());
		ps.execute();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
   }
   
   public Category get(int id) {
	   Category category =null;
	   String sql="select * from category where id = ?";
	   
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);) {
		ps.setInt(1, id);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			category = new Category();
			String name=rs.getString(2);
			category.setName(name);
			category.setId(id);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	   return category;
   }
   
   public List<Category> list(int start,int count){
	   List<Category> categories=new ArrayList<>();    
	   String sql="select * from category order by id desc limit ?,?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
     	ps.setInt(1, start);
		ps.setInt(2, count);
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			Category category= new Category();
			int id =rs.getInt(1);
			String name=rs.getString(2);
			category.setId(id);
			category.setName(name);
			categories.add(category);
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	   return categories;
   }
   
   public List<Category> list(){
	   return list(0,Short.MAX_VALUE);
   }
}
