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
import tmall.Category;
import tmall.Product;
import tmall.ProductImage;

public class ProductDAO {
	
    public int getTotal(int cid) {
       	int total = 0;
       	try(Connection con =DBUtil.getConnection();Statement s =con.createStatement();) {
			String sql ="select count(*) from product where cid =" + cid;
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
    
    public void add(Product p) {
    	String sql ="INSERT INTO product values(null,?,?,?,?,?,?,?)";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);){
    		ps.setString(1, p.getName());
    		ps.setString(2, p.getSubTitle());
    		ps.setFloat(3, p.getOriginalPrice());
    		ps.setFloat(4, p.getPromotePrice());
    		ps.setInt(5, p.getStock());
    		ps.setInt(6, p.getCategory().getId());
    		ps.setTimestamp(7, DateUtil.dateToTimestamp(p.getCreateDate()));	
    		ps.execute();
    		ResultSet rs=ps.getGeneratedKeys();
    		if(rs.next()) {
    			int id=rs.getInt(1);
    			p.setId(id);
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void update(Product p) {
    	String sql ="UPDATE product set name =?,subTitle = ?,originalPrice = ?,promotePrice = ?,stock = ?,cid = ?,createDate = ? where id =?";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setString(1, p.getName());
    		ps.setString(2, p.getSubTitle());
    		ps.setFloat(3, p.getOriginalPrice());
    		ps.setFloat(4, p.getPromotePrice());
    		ps.setInt(5, p.getStock());
    		ps.setInt(6, p.getCategory().getId());
    		ps.setTimestamp(7, DateUtil.dateToTimestamp(p.getCreateDate()));	
    		ps.setInt(8, p.getId());    		
    		ps.execute();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void delete(int id) {
    	String sql ="DELETE from product where id=?";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, id);
    		ps.execute();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    			
    }
    
    public Product get(int id) {
    	Product p = null;
    	String sql= "select * from product where id = ?";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, id);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			p=new Product();
    			String name = rs.getString("name");
    			String subTitle = rs.getString("subTitle");
    			float originalPrice = rs.getFloat("originalPrice");
    			float promotePrice = rs.getFloat("promotePrice");
    			int stock = rs.getInt("stock");
    			int cid = rs.getInt("cid");
    			Category c = new CategoryDAO().get(cid);
    			Date createDate =rs.getTimestamp("createDate");
    			p.setName(name);
    			p.setSubTitle(subTitle);
    			p.setOriginalPrice(originalPrice);
    			p.setPromotePrice(promotePrice);
    			p.setStock(stock);
    			p.setCategory(c);
    			p.setCreateDate(createDate);
    			p.setId(id);
    			setFirstProductImage(p);
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return p;
    }
    
    public List<Product> list(){
    	return list(0,Short.MAX_VALUE);
    }
    
    public List<Product> list(int start,int count){
    	List<Product> products = new ArrayList<>();
    	String sql = "select * from product order by id desc limit ?,?";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, start);
    		ps.setInt(2, count);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			Product p = new Product();
    			int id = rs.getInt(1);
    			String name = rs.getString("name");
    			String subTitle = rs.getString("subTitle");
    			float originalPrice = rs.getFloat("originalPrice");
    			float promotePrice = rs.getFloat("promotePrice");
    			int stock = rs.getInt("stock");    	
    			int cid = rs.getInt("cid");
    			Category c = new CategoryDAO().get(cid);
    			Date createDate = rs.getTimestamp("createDate");
    			p.setName(name);
    			p.setSubTitle(subTitle);
    			p.setOriginalPrice(originalPrice);
    			p.setPromotePrice(promotePrice);
    			p.setStock(stock);
    			p.setCategory(c);
    			p.setCreateDate(createDate);
    			p.setId(id);
    			setFirstProductImage(p);
    			products.add(p);
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return products;
    }
    
    public List<Product> list(int cid){
    	return list(cid,0,Short.MAX_VALUE);
    }
    
    public List<Product> list(int cid,int start, int count){
    	List<Product> products = new ArrayList<>();
    	String sql = "select * from product where cid =? order by id desc limit ?,?";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);){
    		ps.setInt(1, cid);
    		ps.setInt(2, start);
    		ps.setInt(3, count);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			Product p = new Product();
    			int id = rs.getInt(1);
    			String name = rs.getString("name");
    			String subTitle = rs.getString("subTitle");
    			float originalPrice = rs.getFloat("originalPrice");
    			float promotePrice = rs.getFloat("promotePrice");
    			int stock = rs.getInt("stock");    			
    			Category c = new CategoryDAO().get(cid);
    			Date createDate = rs.getTimestamp("createDate");
    			p.setName(name);
    			p.setSubTitle(subTitle);
    			p.setOriginalPrice(originalPrice);
    			p.setPromotePrice(promotePrice);
    			p.setStock(stock);
    			p.setCategory(c);
    			p.setCreateDate(createDate);
    			p.setId(id);
    			setFirstProductImage(p);
    			products.add(p);
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return products;
    }
    
    public void fill(Category c) {
    	List<Product> products=this.list(c.getId());
    	c.setProducts(products);
    }
    
    public void fill(List<Category> cs) {
    	for(Category c : cs) {
    		this.fill(c);
    	}
    }
    
    public void fillByRow(List<Category> cs) {
    	int productNumberEachRow = 8;
    	for(Category c : cs) {
    		List<Product> products = c.getProducts();
    		List<List<Product>> productsByRow = new ArrayList<>();
    		for(int i=0;i<products.size();i+=productNumberEachRow) {
    			int size =i+productNumberEachRow;
    			size = size>products.size()?products.size():size;
    			List<Product> productsOfEachRow = products.subList(i, size);
    			productsByRow.add(productsOfEachRow);
    		}
    		c.setProductsByRow(productsByRow);
    	}
    }
    
    public void setFirstProductImage(Product p) {
    	List<ProductImage> pis = new ProductImageDAO().list(p, ProductImageDAO.type_single);
    	if(!pis.isEmpty()){
    		p.setFirstProductImage(pis.get(0));
    	}
    }
    
    public void setSaleAndReviewNumber(Product p) {
    	int saleCount = new OrderItemDAO().getSaleCount(p.getId());
    	p.setSaleCount(saleCount);
    	
    	int reviewCount = new ReviewDAO().getCount(p.getId());
    	p.setReviewCount(reviewCount);
    	
    }
    
    public void setSaleAndReviewNumber(List<Product> ps) {
    	for(Product p : ps) {
    		setSaleAndReviewNumber(p);
    	}
    }
    
    public List<Product> search(String keyword,int start,int count){
    	List<Product> products = new ArrayList<>();
    	if(keyword==null||keyword.trim().length()==0) {
    		return products;
    	}
    	String sql ="select * from Product where name like ? limit ?,?";
    	try(Connection con =DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setString(1, "%"+keyword.trim()+"%");
    		ps.setInt(2, start);
    		ps.setInt(3, count);
    		ResultSet rs =ps.executeQuery();
    		while(rs.next()) {
    			Product p = new Product();
    			int id = rs.getInt(1);
    			int cid = rs.getInt("cid");
    			String name = rs.getString("name");
    			String subTitle = rs.getString("subTitle");
    			float originalPrice = rs.getFloat("originalPrice");
    			float promotePrice = rs.getFloat("promotePrice");
    			int stock = rs.getInt("stock");
    			Date createDate =rs.getTimestamp("createDate");
    			Category c = new CategoryDAO().get(cid);
    			
    			p.setId(id);
    			p.setCategory(c);
    			p.setName(name);
    			p.setSubTitle(subTitle);
    			p.setOriginalPrice(originalPrice);
    			p.setPromotePrice(promotePrice);
    			p.setStock(stock);
    			p.setCreateDate(createDate);
    			setFirstProductImage(p);
    			products.add(p);
    		}
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return products;
    }
}
