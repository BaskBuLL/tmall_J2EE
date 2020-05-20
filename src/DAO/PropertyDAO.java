package DAO;
/*
 * 从PropertyDAO开始使用自动关闭
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Util.DBUtil;
import tmall.Category;
import tmall.Property;

public class PropertyDAO {
   public int getTotal(int cid) {
	   int total=0;
	   //try()自动关闭
	   try(Connection con=DBUtil.getConnection();Statement s=con.createStatement();) {	
		String sql="select count(*) from property where cid="+cid;
		ResultSet rs =s.executeQuery(sql);
		while(rs.next()) {
			total=rs.getInt(1);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return total;
   }
   
   public void add(Property p) {
	   String sql="insert into property values(null,?,?)";
	   
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
		  ps.setInt(1, p.getCategory().getId()); //获取分类id cid
		  ps.setString(2, p.getName());
		  ps.execute();
		  ResultSet rs = ps.getGeneratedKeys();
		  if(rs.next()) {
			  int id =rs.getInt(1);
			  p.setId(id);
		  }
		  
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
   }
   
   public void update(Property p) {
	   String sql="UPDATE property set cid = ?,name = ? where id = ?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, p.getCategory().getId());
		   ps.setString(2, p.getName());
		   ps.setInt(3, p.getId());
		   ps.execute();
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   public void delete(int id) {
	   String sql="delete from property where id=?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, id);
		   ps.execute();
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   public Property get(int id) {
	   String sql="select * from property where id = ?";
	   Property property=null;
	   ResultSet rs=null;
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, id);
		   rs=ps.executeQuery();
		   if(rs.next()) {
			   property= new Property();
			   int cid = rs.getInt("cid");
			   String name =rs.getString("name");
			   Category c=new CategoryDAO().get(cid);
			   property.setCategory(c);
			   property.setName(name);
			   property.setId(id);
		   }
		   
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return property;
   }
   
   public List<Property> list(int cid,int start,int count){
	   String sql ="select * from property where cid =? order by id desc limit ?,?";
	   List<Property> properties=new ArrayList<>();
	   ResultSet rs=null;
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, cid);
		   ps.setInt(2, start);
		   ps.setInt(3, count);
		   rs=ps.executeQuery();
		   
		   while(rs.next()) {
			   Property p = new Property();
			   int id =rs.getInt(1);
			   String name=rs.getString("name");
			   p.setCategory(new CategoryDAO().get(cid));
			   p.setName(name);
			   p.setId(id);
			   properties.add(p);
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return properties;
   }
   
   public List<Property> list(int cid){
	   return list(cid,0,Short.MAX_VALUE);
   }
}
