package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Util.DBUtil;
import tmall.Product;
import tmall.Property;
import tmall.PropertyValue;

public class PropertyValueDAO {
   public int getTotal() {
	   int total = 0 ;
	   try(Connection con=DBUtil.getConnection();Statement s=con.createStatement();) {	
		   String sql ="select count(*) from propertyvalue";
		   ResultSet rs=s.executeQuery(sql);
		   while(rs.next()) {
			   total = rs.getInt(1);
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return total;
   }
   
   public void add(PropertyValue p) {
	   String sql ="INSERT INTO propertyvalue values(null,?,?,?)";
	   try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);){
		   ps.setInt(1, p.getProduct().getId());
		   ps.setInt(2, p.getProperty().getId());
		   ps.setString(3, p.getValue());
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
   
   public void update(PropertyValue p) {
	   String sql ="UPDATE propertyvalue set pid = ? ,ptid = ? ,value=? where id = ? ";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, p.getProduct().getId());
		   ps.setInt(2, p.getProperty().getId());
		   ps.setString(3, p.getValue());
		   ps.setInt(4, p.getId());
		   ps.execute();
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   public void delete(int id) {
	   String sql="DELETE from propertyvalue where id = ?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, id);
		   ps.execute();
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   public PropertyValue get(int id ){
	   PropertyValue pv=null;
	   String sql ="select * from propertyvalue where id =?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, id);
		   ResultSet rs = ps.executeQuery();
		   if(rs.next()) {
			   pv=new PropertyValue();
			   pv.setId(id);
			   Product product = new ProductDAO().get(rs.getInt("pid"));
			   Property property = new PropertyDAO().get(rs.getInt("ptid"));
			   String value = rs.getString("value");
			   pv.setValue(value);
			   pv.setProduct(product);
			   pv.setProperty(property);
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return pv;
   }
   
   public PropertyValue get(int ptid ,int pid) {
	   PropertyValue pv = null;
	   String sql ="select * from propertyvalue where ptid = ? and pid = ?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   
		   ps.setInt(1, ptid);
		   ps.setInt(2, pid);
		   ResultSet rs = ps.executeQuery();
		   if(rs.next()) {
			   pv=new PropertyValue();
			   Product product = new ProductDAO().get(ptid);
			   Property property = new PropertyDAO().get(ptid);
			   pv.setProduct(product);
			   pv.setProperty(property);
			   pv.setValue(rs.getString("value"));
			   pv.setId(rs.getInt(1));
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return pv;
   }
   
   public List<PropertyValue> list() {
       return list(0, Short.MAX_VALUE);
   }
   
   public List<PropertyValue> list(int start, int count){
	   String sql ="select * from propertyvalue  by id desc limit ?,?";
	   List<PropertyValue> pvs=new ArrayList<>();
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, start);
		   ps.setInt(2, count);
		   ResultSet rs=ps.executeQuery();   
		   while(rs.next()) {
			   PropertyValue pv = new PropertyValue();
			   int ptid = rs.getInt("ptid");
			   int pid = rs.getInt("pid");
			   Product product = new ProductDAO().get(pid);
			   Property property = new PropertyDAO().get(ptid);
			   pv.setProduct(product);
			   pv.setProperty(property);
			   pv.setId(rs.getInt(1));
			   pv.setValue(rs.getString("value"));
			   pvs.add(pv);
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return pvs;
   }
   
   public List<PropertyValue> list(int pid){
	   List<PropertyValue> pvs=new ArrayList<>();
	   String sql ="select * from propertyvalue where pid = ? order by ptid desc";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, pid);
		   ResultSet rs = ps.executeQuery();
		   while(rs.next()) {
			   PropertyValue pv = new PropertyValue();
			   int ptid = rs.getInt("ptid");
			   Property p = new PropertyDAO().get(ptid); 
			   pv.setProperty(p);
			   Product product =new ProductDAO().get(pid);
			   pv.setProduct(product);
			   pv.setId(rs.getInt(1));
			   pv.setValue(rs.getString("value"));
			   pvs.add(pv);
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return pvs;  
   }
   
   public void init(Product p) {
	   List<Property> pts = new PropertyDAO().list(p.getCategory().getId());
	   for(Property pt : pts) {
		   PropertyValue pv = get(pt.getId(),p.getId());
		   if(pv==null) {
			   pv= new PropertyValue();
			   pv.setProduct(p);
			   pv.setProperty(pt);
			   this.add(pv);//ProductValueDAO的实例
		   }
	   }
   }
}
