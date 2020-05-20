package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Util.DBUtil;
import tmall.Order;
import tmall.OrderItem;
import tmall.Product;
import tmall.User;

public class OrderItemDAO {
	
    public int getTotal() {
        int total = 0;
        try(Connection con=DBUtil.getConnection();Statement s=con.createStatement();) {	
        	String sql ="select count(*) from orderitem";
        	ResultSet rs =s.executeQuery(sql);
        	while(rs.next()) {
        		total = rs.getInt(1);
        	}
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return total;
    }
    
    public void add(OrderItem oi) {
    	String sql ="INSERT INTO orderitem values(null,?,?,?,?)";
    	try(Connection con = DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);){
    		ps.setInt(1, oi.getProduct().getId());
    		
    		if(oi.getOrder()==null) {
    			ps.setInt(2, -1);
    		}else {
    			ps.setInt(2, oi.getOrder().getId());
    		}
    		ps.setInt(3, oi.getUser().getId());
    		ps.setInt(4, oi.getNumber());
    		ps.execute();
    		ResultSet rs = ps.getGeneratedKeys();
    		if(rs.next()) {
    			int id = rs.getInt(1);
    			oi.setId(id);
    		}
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void update(OrderItem oi) {
    	String sql ="UPDATE orderitem set pid = ?,oid = ?,uid = ?,number = ? where id = ?";
    	try(Connection con = DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, oi.getProduct().getId());
    		if(oi.getOrder()==null) {
    			ps.setInt(2, -1);
    		}else {
    		ps.setInt(2, oi.getOrder().getId());
    		}
    		ps.setInt(3, oi.getUser().getId());
    		ps.setInt(4, oi.getNumber());
    		ps.setInt(5, oi.getId());
    		ps.execute();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void delete(int id) {
    	String sql ="DELETE from orderitem where id = ?";
    	try(Connection con = DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, id);
    		ps.execute();
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public OrderItem get(int id) {
    	String sql ="SELECT * from orderitem where id =?";
    	OrderItem oi = null;
    	try(Connection con = DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, id);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			oi=new OrderItem();
    			oi.setId(id);
    			oi.setNumber(rs.getInt("number"));
    			int pid = rs.getInt("pid");
    			Product product =new ProductDAO().get(pid);
    			int oid = rs.getInt("oid");
    			//Order order = new OrderDAO().get(oid);
    			int uid = rs.getInt("uid");
    			User user = new UserDAO().get(uid);
    			oi.setProduct(product);
    			oi.setUser(user);
    			
    			if(oid!=-1) {
    				Order order = new OrderDAO().get(oid);
    				oi.setOrder(order);
    			}   			
    			
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return oi;
    }
    
    public List<OrderItem> listOfUser(int uid){
    	return listOfUser(uid,0,Short.MAX_VALUE);
    }
    
    public List<OrderItem> listOfUser(int uid,int start,int count){
    	List<OrderItem> ois= new ArrayList<>();
    	String sql ="select * from orderitem where uid = ? and oid=-1 order by id desc limit ?,?";
    	try(Connection con = DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, uid);
    		ps.setInt(2, start);
    		ps.setInt(3, count);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			OrderItem oi = new OrderItem();
    			int pid = rs.getInt("pid");
    			int number = rs.getInt("number");
    			int id = rs.getInt(1);
    			int oid =rs.getInt("oid");
    			if(oid!=-1) {
    				Order order = new OrderDAO().get(oid);
    				oi.setOrder(order);
    			}
    			User user = new UserDAO().get(uid);
    			oi.setUser(user);
    			Product product = new ProductDAO().get(pid);
    			oi.setProduct(product);
    			oi.setNumber(number);
    			oi.setId(id);
    			ois.add(oi);
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ois;
    }
    
    public List<OrderItem> listOfOrder(int oid){
    	return listOfOrder(oid,0,Short.MAX_VALUE);
    }
    
    public List<OrderItem> listOfOrder(int oid,int start,int count){
    	List<OrderItem> ois= new ArrayList<>();
    	String sql ="select * from orderitem where oid = ? order by id desc limit ?,?";
    	try(Connection con = DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, oid);
    		ps.setInt(2, start);
    		ps.setInt(3, count);
    		ResultSet rs =ps.executeQuery();
    		while(rs.next()) {
    			OrderItem oi = new OrderItem();
    			int pid = rs.getInt("pid");
    			int number = rs.getInt("number");
    			int id = rs.getInt(1);
    			int uid =rs.getInt("uid");
    			if(oid!=-1) {
    				Order order = new OrderDAO().get(oid);
    				oi.setOrder(order);
    			}
    			User user = new UserDAO().get(uid);
    			oi.setUser(user);
    			Product product = new ProductDAO().get(pid);
    			oi.setProduct(product);
    			oi.setNumber(number);
    			oi.setId(id);
    			ois.add(oi);
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ois;
    }
    
    public void fill(List<Order> os) {
    	for(Order o :os) {
    		List<OrderItem> ois=listOfOrder(o.getId());
    		float total = 0;
    		int totalNumber = 0;
    		for(OrderItem oi:ois) {
    			total+=oi.getNumber()*oi.getProduct().getPromotePrice();
    			totalNumber+=oi.getNumber();
    		}
    		o.setTotal(total);
    		o.setTotalNumber(totalNumber);
    		o.setOrderitems(ois);
    	}
    }
    
    public void fill(Order o) {
    	List<OrderItem> ois =listOfOrder(o.getId());
    	float total = 0;
    	int totalNumber = 0;
    	for (OrderItem oi : ois) {
    		total+=oi.getNumber()*oi.getProduct().getPromotePrice();
			totalNumber+=oi.getNumber();
    	}
    	o.setTotal(total);
    	o.setTotalNumber(totalNumber);
    	o.setOrderitems(ois);
    }
    
    public List<OrderItem> listOfProduct(int pid){
    	return listOfProduct(pid, 0, Short.MAX_VALUE);
    }
    
    public List<OrderItem> listOfProduct(int pid,int start, int count){
    	List<OrderItem> ois= new ArrayList<>();
    	String sql ="select * from orderitem where pid = ? order by id desc limit ?,?";
    	try(Connection con = DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, pid);
    		ps.setInt(2, start);
    		ps.setInt(3, count);
    		ResultSet rs =ps.executeQuery();
    		while(rs.next()) {
    			OrderItem oi = new OrderItem();
    			int oid = rs.getInt("oid");
    			int number = rs.getInt("number");
    			int id = rs.getInt(1);
    			int uid =rs.getInt("uid");
    			if(oid!=-1) {
    				Order order = new OrderDAO().get(oid);
    				oi.setOrder(order);
    			}
    			User user = new UserDAO().get(uid);
    			oi.setUser(user);
    			Product product = new ProductDAO().get(pid);
    			oi.setProduct(product);
    			oi.setNumber(number);
    			oi.setId(id);
    			ois.add(oi);
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ois;
    }
    
    public int getSaleCount(int pid) {
    	int total=0;
    	String sql ="select sum(number) from orderitem where pid =?";
    	try(Connection con = DBUtil.getConnection();PreparedStatement ps = con.prepareStatement(sql);){
    		ps.setInt(1, pid);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			total = rs.getInt(1);
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return total;
    }
}
