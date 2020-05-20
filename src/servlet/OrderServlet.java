package servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Page;
import tmall.Order;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends BaseBackServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {	
    	List<Order> os = orderDAO.list(page.getStart(), page.getCount());
    	orderItemDAO.fill(os);
    	int total = orderDAO.getTotal();
    	page.setTotal(total);
    	request.setAttribute("page", page);
    	request.setAttribute("os", os);
    	
    	return "admin/listOrder.jsp";
    }

    
    public String delivery(HttpServletRequest request, HttpServletResponse response, Page page) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	Order o = orderDAO.get(id);
    	o.setDeliveryDate(new Date());
    	o.setStatus(orderDAO.waitConfirm);
    	orderDAO.update(o);
    	return "@admin_order_list";
    }
}
