package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.ImageUtil;
import Util.Page;
import tmall.Category;
/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseBackServlet  {

	@Override
   public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
			Map<String,String> params = new HashMap<>();
			InputStream is = super.parseUpload(request, params);
			String name = params.get("name");
			Category c  = new Category();
			c.setName(name);
			categoryDAO.add(c);
			
			//File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
			String path="D:/eclipse-workspace/tmall/web/img/category";
			File imageFolder = new File(path);
			File file = new File(imageFolder, c.getId()+".jpg");

			try {
				if(is!=null&&is.available()!=0) {
					try(FileOutputStream fos = new FileOutputStream(file)){ 
						byte b[]=new byte[1024*1024];
						int length=0;
						while((length=is.read(b))!=-1) {
							fos.write(b, 0, length);
						}
						fos.flush();
						BufferedImage img = ImageUtil.change2jpg(file);
						ImageIO.write(img, "jpg", file);
						System.out.println(file);
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
				}catch(Exception e ) {
				e.printStackTrace();
			}
			return "@admin_category_list";	
		}
	
	@Override
   public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
			int id = Integer.parseInt(request.getParameter("id"));
			categoryDAO.delete(id);
			String path="D:/eclipse-workspace/tmall/web/img/category";
			//File imageFile = new File(request.getSession().getServletContext().getRealPath("img/category")+"/"+id+".jpg");
			File imageFile = new File(path+"/"+id+".jpg");
			imageFile.delete();
			return "@admin_category_list";
		}
	
   @Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Category c = categoryDAO.get(id);
		request.setAttribute("c", c);
		return "admin/editCategory.jsp";
	}
   
   @Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String,String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		String name = params.get("name");
		int id = Integer.parseInt(params.get("id"));
		Category c = new Category();
		c.setId(id);
		c.setName(name);
		categoryDAO.update(c);
		
		File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
		File file        = new File(imageFolder,c.getId()+".jpg");
		file.getParentFile().mkdirs();
		try {
			if(is!=null&&is.available()!=0) {
				try(FileOutputStream fos = new FileOutputStream(file)){
					byte b[] = new byte[1024*1024];
					int length = 0;
					while((length=is.read(b))!=-1) {
						fos.write(b, 0, length);
					}
					fos.flush();
					BufferedImage img = ImageUtil.change2jpg(file);
					ImageIO.write(img, "jpg", file);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "@admin_category_list";
	}
   
   public String list(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Page page) {
	   List<Category> cs = categoryDAO.list(page.getStart(),page.getCount());
	   int total = categoryDAO.getTotal();
	   page.setTotal(total);
	   request.setAttribute("thecs", cs);
	   request.setAttribute("page", page);
	   return "admin/listCategory.jsp";
   }

   
}
