package filter;

import java.io.IOException;


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





/**
 * Servlet Filter implementation class BackServletFilter
 */
@WebFilter("/BackServletFilter")
public class BackServletFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String ContextPath = request.getServletContext().getContextPath();
		String uri = request.getRequestURI();
		uri = StringUtils.remove(uri,ContextPath);
		if(uri.startsWith("/admin_")){
			String servletPath = StringUtils.substringBetween(uri, "_","_")+"Servlet";
			String method = StringUtils.substringAfterLast(uri, "_");
			request.setAttribute("method", method);
			request.getRequestDispatcher("/"+servletPath).forward(request, response);
			return ;
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
