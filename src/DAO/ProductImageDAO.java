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
import tmall.ProductImage;

public class ProductImageDAO {
   public static final String type_single = "type_single";
   public static final String type_detail = "type_detail";
   
   public int getTotal() {
	   int total=0;
	   try(Connection con=DBUtil.getConnection();Statement s=con.createStatement();) {	
		   String sql ="select count(*) from productimage";
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
   
   public void add(ProductImage p) {
	   String sql="INSERT INTO productimage values(null,?,?)";
	   try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);){
		   ps.setInt(1, p.getProduct().getId());
		   ps.setString(2, p.getType());
		   ps.execute();
		   ResultSet rs = ps.getGeneratedKeys();
		   if(rs.next()) {
			   int id = rs.getInt(1);
			   p.setId(id);
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   public void update(ProductImage p) {
	   String sql="UPDATE productimage SET pid = ?,type = ? where id = ?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, p.getProduct().getId());
		   ps.setString(2,p.getType());
		   ps.setInt(3, p.getId());
		   ps.execute();
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   public void delete(int id) {
	   String sql="DELETE from productimage where id =?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, id);
		   ps.execute();
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   public ProductImage get(int id) {
	   ProductImage p = null;
	   String sql="select * from productimage where id = ?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, id);
		   ResultSet rs=ps.executeQuery();
		   if(rs.next()) {
			   p=new ProductImage();
			   p.setId(id);
			   p.setType(rs.getString("type"));
			   Product product=new ProductDAO().get(rs.getInt("pid"));
			   p.setProduct(product);
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return p;
   }
   
   public List<ProductImage> list(Product p,String type,int start,int count){
	   List<ProductImage> images = new ArrayList<>();
	   String sql ="select * from productimage where pid = ? and type = ? order by id desc limit ?,?";
	   try(Connection con=DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql);){
		   ps.setInt(1, p.getId());
		   ps.setString(2, type);
		   ps.setInt(3, start);
		   ps.setInt(4, count);
		   ResultSet rs = ps.executeQuery();
		   while(rs.next()){
			   ProductImage image = new ProductImage();
			   image.setType(type);
			   image.setProduct(p);
			   image.setId(rs.getInt(1));
			   images.add(image);
		   }
	   
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return images;
   }
   
   public List<ProductImage> list(Product p ,String type){
	   return list(p,type,0,Short.MAX_VALUE);
   }
}
