package servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Page;
import tmall.Category;
import tmall.Product;
import tmall.Property;
import tmall.PropertyValue;

/**
 * 产品Control
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends BaseBackServlet {
	private static final long serialVersionUID = 1L;

    @Override
    	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
    		int cid = Integer.parseInt(request.getParameter("cid"));
    		Category c = categoryDAO.get(cid);
    		List<Product> ps = productDAO.list(cid, page.getStart(), page.getCount());
    		int total = productDAO.getTotal(cid);
    		page.setTotal(total);
    		page.setParam("&cid="+c.getId());
    		request.setAttribute("ps", ps);
    		request.setAttribute("c", c);
    		request.setAttribute("page", page);
    		return "admin/listProduct.jsp";
    	}

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		 String name = request.getParameter("name");
		 String subTitle = request.getParameter("subTitle");
		 float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
		 float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		 int stock = Integer.parseInt(request.getParameter("stock"));
		 int cid = Integer.parseInt(request.getParameter("cid"));
		 Category c = categoryDAO.get(cid);
		 
		 Product p = new Product();
		 p.setName(name);
		 p.setSubTitle(subTitle);
		 p.setOriginalPrice(originalPrice);
		 p.setPromotePrice(promotePrice);
		 p.setStock(stock);
		 p.setCategory(c);
		 p.setCreateDate(new Date());
		 productDAO.add(p);
		 return "@admin_product_list?cid="+cid;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product p = productDAO.get(id);
		productDAO.delete(id);
		return "@admin_product_list?cid="+p.getCategory().getId();
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product p = productDAO.get(id);
		request.setAttribute("p", p);
		return "admin/editProduct.jsp";
	}
	
	public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product p = productDAO.get(id);
		request.setAttribute("p", p);
		
		
		propertyValueDAO.init(p);
		List<PropertyValue> pvs = propertyValueDAO.list(p.getId());
		request.setAttribute("pvs", pvs);
		return "admin/editPropertyValue.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c= categoryDAO.get(cid);
		
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String subTitle = request.getParameter("subTitle");
		float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		
		Product p = new Product();
		p.setName(name);
		p.setId(id);
		p.setSubTitle(subTitle);
		p.setPromotePrice(promotePrice);
		p.setStock(stock);
		p.setCategory(c);
		productDAO.update(p);
		return "@admin_product_list?cid="+p.getCategory().getId();
	}
	
	public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pvid = Integer.parseInt(request.getParameter("pvid"));
		String value = request.getParameter("value");
		//int pid = Integer.parseInt(request.getParameter("pid"));
		//Product p = productDAO.get(pid);
		PropertyValue pv = propertyValueDAO.get(pvid);
		pv.setValue(value);
		//pv.setProduct(p);
		propertyValueDAO.update(pv);
		return "%success";
	}

}
