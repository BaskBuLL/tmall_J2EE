package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
 * Servlet implementation class BaseBackServlet
 */
@WebServlet("/BaseBackServlet")
public abstract class BaseBackServlet extends HttpServlet {
		
    public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page);
    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page);
    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page);
    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page);
    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page);
    
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
		/*获取分页信息*/
		try {
		int start=0;
		int count=5;
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
		String redirect = null;
		Method m;
		m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,javax.servlet.http.HttpServletResponse.class,Page.class);
		redirect = m.invoke(this, request,response,page).toString();
	
		/*根据方法的返回值跳转*/
		if(redirect.startsWith("@"))
			response.sendRedirect(redirect.substring(1));//重定向
		else if(redirect.startsWith("%"))
			response.getWriter().print(redirect.substring(1));
		else 
			request.getRequestDispatcher(redirect).forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public InputStream parseUpload(HttpServletRequest request,Map<String,String> params) {
		InputStream is = null;
		try {
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			factory.setSizeThreshold(1024*10240);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while(iter.hasNext()) {
				FileItem item = (FileItem)iter.next();
				if(!item.isFormField()) {
					is=item.getInputStream();
				}else {
					String paramName = item.getFieldName();//获取name的属性值
					String paramValue = item.getString();
					paramValue = new String(paramValue.getBytes("ISO-8859-1"),"UTF-8");
					params.put(paramName, paramValue);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}

}
