package servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.CategoryDAO;
import DAO.OrderDAO;
import DAO.OrderItemDAO;
import DAO.ProductDAO;
import DAO.ProductImageDAO;
import DAO.PropertyDAO;
import DAO.PropertyValueDAO;
import DAO.ReviewDAO;
import DAO.UserDAO;
import Util.Page;

/**
 * Servlet implementation class BaseForeServlet
 */
@WebServlet("/BaseForeServlet")
public class BaseForeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	    protected CategoryDAO  categoryDAO = new CategoryDAO();
	    protected OrderDAO     orderDAO    = new OrderDAO();
	    protected OrderItemDAO orderItemDAO= new OrderItemDAO();
	    protected ProductDAO   productDAO  = new ProductDAO();    
	    protected PropertyDAO  propertyDAO = new PropertyDAO();    
	    protected ReviewDAO    reviewDAO   = new ReviewDAO();
	    protected UserDAO      userDAO     = new UserDAO();
	    protected ProductImageDAO productImageDAO = new ProductImageDAO();
	    protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			int start=0;
			int count=10;
			try {
				start = Integer.parseInt(request.getParameter("page.start"));
			}catch(Exception e) {
			}
			
			try {
				count = Integer.parseInt(request.getParameter("page.count"));
			}catch(Exception e) {	
			}
			Page page = new Page(start,count);
			
			/*借用反射调用对应方法*/
			String method = (String)request.getAttribute("method");
			Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,javax.servlet.http.HttpServletResponse.class,Page.class);
			String redirect = m.invoke(this, request,response,page).toString();
			
			if(redirect.startsWith("@"))
				response.sendRedirect(redirect.substring(1));
			else if(redirect.startsWith("%"))
				response.getWriter().print(redirect.substring(1));
			else 
				request.getRequestDispatcher(redirect).forward(request,response);
			
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
