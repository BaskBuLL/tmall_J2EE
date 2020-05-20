package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Util.DBUtil;
import Util.DateUtil;
import tmall.Order;
import tmall.User;

public class OrderDAO {
   public final static String waitPay = "waitPay";
   public final static String waitDelivery = "waitDelivery";
   public final static String waitConfirm = "waitConfirm";
   public final static String waitReview = "waitReview";
   public final static String finish = "finish";
   public final static String delete = "delete";

   
   public int getTotal() {
	   int total = 0 ;
	   try(Connection con=DBUtil.getConnection();Statement s=con.createStatement();) {	
		   String sql ="select count(*) from order_";
		   ResultSet rs = s.executeQuery(sql);
		   while(rs.next()) {
			   total = rs.getInt(1);
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	     return total;
   }
   
   public void add(Order order) {
	   String sql ="INSERT INTO order_  values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
	   try(Connection con = DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS); ){
		   ps.setString(1,order.getOrderCode());
		   ps.setString(2, order.getAddress());
		   ps.setString(3, order.getPost());
		   ps.setString(4, order.getReceiver());
		   ps.setString(5, order.getMobile());
		   ps.setString(6, order.getUserMessage());
		   
		   ps.setTimestamp(7, DateUtil.dateToTimestamp(order.getCreateDate()));
		   ps.setTimestamp(8, DateUtil.dateToTimestamp(order.getPayDate()));
		   ps.setTimestamp(9, DateUtil.dateToTimestamp(order.getDeliveryDate()));
		   ps.setTimestamp(10, DateUtil.dateToTimestamp(order.getConfirmDate()));
		   
		   ps.setInt(11, order.getUser().getId());
		   ps.setString(12, order.getStatus());
		   
		   ps.execute();
		   ResultSet rs = ps.getGeneratedKeys();
		   if(rs.next()) {
			   int id =rs.getInt(1);
			   order.setId(id);			   
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   public void update(Order order) {
	   String sql =" UPDATE order_ set orderCode= ?,address = ?,post = ? ,receiver = ?,mobile = ?,userMessage = ?,createDate = ?,PayDate = ?,DeliveryDate = ?,confirmDate= ?,uid= ?,status = ? where id =?";
	   try(Connection con = DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql) ){
		   ps.setString(1, order.getOrderCode());
		   ps.setString(2, order.getAddress());
		   ps.setString(3, order.getPost());
		   ps.setString(4, order.getReceiver());
		   ps.setString(5, order.getMobile());
		   ps.setString(6, order.getUserMessage());
		   
		   ps.setTimestamp(7, DateUtil.dateToTimestamp(order.getCreateDate()));
		   ps.setTimestamp(8, DateUtil.dateToTimestamp(order.getPayDate()));
		   ps.setTimestamp(9, DateUtil.dateToTimestamp(order.getDeliveryDate()));
		   ps.setTimestamp(10, DateUtil.dateToTimestamp(order.getConfirmDate()));
		   
		   ps.setInt(11, order.getUser().getId());
		   ps.setString(12, order.getStatus());
           ps.setInt(13, order.getId());
           ps.execute();
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			   
   }
   
   public void delete(int id) {
	   String sql ="DELETE from order_ where id = ?";
	   try(Connection con = DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql) ){
		   ps.setInt(1, id);
		   ps.execute();
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   public Order get(int id) {
	   Order o = null;
	   String sql ="select * from order_ where id =?";
	   try(Connection con = DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql) ){
		   ps.setInt(1, id);
		   ResultSet rs = ps.executeQuery();
		   if(rs.next()) {
			   o=new Order();
			   o.setOrderCode(rs.getString("orderCode"));
			   o.setAddress(rs.getString("address"));
			   o.setPost(rs.getString("post"));
			   o.setReceiver(rs.getString("receiver"));
			   o.setMobile(rs.getString("mobile"));
			   o.setUserMessage(rs.getString("userMessage"));
			   o.setCreateDate(DateUtil.TimestampToDate(rs.getTimestamp("createDate")));
			   o.setPayDate(DateUtil.TimestampToDate(rs.getTimestamp("payDate")));
			   o.setDeliveryDate(DateUtil.TimestampToDate(rs.getTimestamp("deliveryDate")));
			   o.setConfirmDate(DateUtil.TimestampToDate(rs.getTimestamp("confirmDate")));
			   int uid = rs.getInt("uid");
			   User user = new UserDAO().get(uid);
			   o.setUser(user);
			   o.setStatus(rs.getString("status"));
			   o.setId(id);
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return o;
   }
   
   public List<Order> list(){
	   return list(0,Short.MAX_VALUE);
   }
   
   public List<Order> list(int start,int count){
	   List<Order> orders = new ArrayList<Order>();
	   String sql ="select * from order_ order by id desc limit ?,?";
	   try(Connection con = DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql) ){
		   ps.setInt(1, start);
		   ps.setInt(2, count);
		   ResultSet rs = ps.executeQuery();
		   while(rs.next()) {
			   Order o = new Order();
			   o.setOrderCode(rs.getString("orderCode"));
			   o.setAddress(rs.getString("address"));
			   o.setPost(rs.getString("post"));
			   o.setReceiver(rs.getString("receiver"));
			   o.setMobile(rs.getString("mobile"));
			   o.setUserMessage(rs.getString("userMessage"));
			   o.setCreateDate(DateUtil.TimestampToDate(rs.getTimestamp("createDate")));
			   o.setPayDate(DateUtil.TimestampToDate(rs.getTimestamp("payDate")));
			   o.setDeliveryDate(DateUtil.TimestampToDate(rs.getTimestamp("deliveryDate")));
			   o.setConfirmDate(DateUtil.TimestampToDate(rs.getTimestamp("confirmDate")));
			   int uid = rs.getInt("uid");
			   User user = new UserDAO().get(uid);
			   o.setUser(user);
			   o.setStatus(rs.getString("status"));
			   o.setId(rs.getInt(1));
			   orders.add(o);
		   }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return orders;
   }
   
  public List<Order> list(int uid ,String status){
	  return list(uid,status,0,Short.MAX_VALUE);
  }
  
  public List<Order> list(int uid , String status,int start,int count){
	  List<Order> orders = new ArrayList<>();
	  String sql ="select * from order_ where uid = ? and status != ? order by id desc limit ?,?";
	  try(Connection con = DBUtil.getConnection();PreparedStatement ps=con.prepareStatement(sql) ){
		  ps.setInt(1, uid);
		  ps.setString(2, status);
		  ps.setInt(3, start);
		  ps.setInt(4, count);
		  ResultSet rs = ps.executeQuery();
		   while(rs.next()) {
			   Order o = new Order();
			   o.setOrderCode(rs.getString("orderCode"));
			   o.setAddress(rs.getString("address"));
			   o.setPost(rs.getString("post"));
			   o.setReceiver(rs.getString("receiver"));
			   o.setMobile(rs.getString("mobile"));
			   o.setUserMessage(rs.getString("userMessage"));
			   o.setCreateDate(DateUtil.TimestampToDate(rs.getTimestamp("createDate")));
			   o.setPayDate(DateUtil.TimestampToDate(rs.getTimestamp("payDate")));
			   o.setDeliveryDate(DateUtil.TimestampToDate(rs.getTimestamp("deliveryDate")));
			   o.setConfirmDate(DateUtil.TimestampToDate(rs.getTimestamp("confirmDate")));			 
			   User user = new UserDAO().get(uid);
			   o.setUser(user);
			   o.setStatus(rs.getString("status"));
			   o.setId(rs.getInt(1));
			   orders.add(o);
		   }		  
	  } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return orders;
  }
}
