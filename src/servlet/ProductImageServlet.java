package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProductImageDAO;
import Util.ImageUtil;
import Util.Page;
import tmall.Product;
import tmall.ProductImage;

/**
 * Servlet implementation class ProductImageServlet
 */
@WebServlet("/ProductImageServlet")
public class ProductImageServlet extends BaseBackServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
    	Map<String,String> params = new HashMap<>();
    	InputStream is = super.parseUpload(request, params);
    	String type = params.get("type");
    	int pid = Integer.parseInt(params.get("pid"));
    	Product p = productDAO.get(pid);
    	ProductImage pi = new ProductImage();
    	pi.setType(type);
    	pi.setProduct(p);
    	productImageDAO.add(pi);
    	String imageFolder;
    	String imageFolder_small = null;
    	String imageFolder_middle = null;
    	//生成文件
    	if(ProductImageDAO.type_single.equals(pi.getType())) {
    		
    		//imageFolder = request.getSession().getServletContext().getRealPath("img/productSingle");
    		imageFolder = "D:/eclipse-workspace/tmall/web/img/productSingle";
    		//imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
    		imageFolder_small = "D:/eclipse-workspace/tmall/web/img/productSingle_small";
    	    //imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");
    		imageFolder_middle = "D:/eclipse-workspace/tmall/web/img/productSingle_middle";
    		File file_small = new File(imageFolder_small,pi.getId()+".jpg");
    		File file_middle = new File(imageFolder_middle,pi.getId()+".jpg");
    		file_small.getParentFile().mkdirs();
    		file_middle.getParentFile().mkdirs();
    	}
    	else
    		//imageFolder = request.getSession().getServletContext().getRealPath("img/productDetail");
    		imageFolder = "D:/eclipse-workspace/tmall/web/img/productDetail";
    	File file = new File(imageFolder,pi.getId()+".jpg");
    	file.getParentFile().mkdirs();
    	
    	try{
    		if(is!=null&&is.available()!=0) {
    			try(FileOutputStream fos = new FileOutputStream(file)){
    				byte b[] = new byte[1024*1024];
    				int length=0;
    				while((length=is.read(b))!=-1) {
    					fos.write(b,0,length);
    				}
    				fos.flush();
    				BufferedImage img = ImageUtil.change2jpg(file);
    				ImageIO.write(img, "jpg", file);
    				
    				if(ProductImageDAO.type_single.equals(pi.getType())) {
    					File file_small = new File(imageFolder_small,pi.getId()+".jpg");
    					File file_middle = new File(imageFolder_middle,pi.getId()+".jpg");
    					
    				    ImageUtil.resizeImage(file, 56, 56,file_small);
    				    ImageUtil.resizeImage(file, 217, 190, file_middle);
    				
    				}
    			}catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    	
    	return "@admin_productImage_list?pid="+p.getId();
    }
    
    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
    	int id = Integer.parseInt(request.getParameter("id"));
    	ProductImage pi = productImageDAO.get(id);
    	productImageDAO.delete(id);
    	if(ProductImageDAO.type_single.equals(pi.getType())) {
    		String path ="D:/eclipse-workspace/tmall/web/img";
    		//File f_single = new File(request.getSession().getServletContext().getRealPath("img/productSingle")+"/"+id+".jpg");
    		File f_single = new File(path+"/"+"productSingle"+"/"+id+".jpg");
    	    //File f_small = new File(request.getSession().getServletContext().getRealPath("img/productSingle_small")+"/"+id+".jpg");
    		File f_small = new File(path+"/"+"productSingle_small"+"/"+id+".jpg");
    	    //File f_middle = new File(request.getSession().getServletContext().getRealPath("img/productSingle_middle")+"/"+id+".jpg");
    		File f_middle = new File(path+"/"+"productSingle_middle"+"/"+id+".jpg");
    	    f_single.delete();
    	    f_small.delete();
    	    f_middle.delete();
    	        	
    	}else {
    		File f_detail = new File(request.getSession().getServletContext().getRealPath("img/productDetail")+"/"+id+".jpg");
    		f_detail.delete();
    	}
    	
    	return "@admin_productImage_list?pid="+pi.getProduct().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
    	// TODO Auto-generated method stub
    	return null;
    }
    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
    	// TODO Auto-generated method stub
    	return null;
    }
    
    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
    	int pid = Integer.parseInt(request.getParameter("pid"));
    	Product p = productDAO.get(pid);
    	List<ProductImage> pisSingle = productImageDAO.list(p, ProductImageDAO.type_single);
    	List<ProductImage> pisDetail = productImageDAO.list(p, ProductImageDAO.type_detail);
    	
    	request.setAttribute("p", p);
    	request.setAttribute("pisSingle", pisSingle);
    	request.setAttribute("pisDetail", pisDetail);
    	
    	return "admin/listProductImage.jsp";
    }
}
