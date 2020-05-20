package filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import tmall.User;

/**
 * Servlet Filter implementation class ForeAuthFilter
 */
@WebFilter("/ForeAuthFilter")
public class ForeAuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ForeAuthFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        String contextPath = request.getServletContext().getContextPath();
        String[] noNeedAuthPage =new String[] {"home","checkLogin","register","login","product","category","search","loginAjax"};
        String uri =request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);
        if(uri.startsWith("/fore")&&!uri.startsWith("/foreServlet")) {
        	String method = StringUtils.substringAfterLast(uri, "/fore");
        	if(!Arrays.asList(noNeedAuthPage).contains(method)) {
        		User user =(User) request.getSession().getAttribute("user");
        		if(user==null) {
        			response.sendRedirect("login.jsp");
        			return;
        		}
        	}
        }
        
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
