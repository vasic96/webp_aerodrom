package vasic.web.programiranje.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vasic.web.programiranje.model.Korisnik;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
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
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String uri = request.getRequestURI();
		Korisnik korisnik = (Korisnik) request.getSession().getAttribute("ulogovanKorisnik");
		if(uri.equals("/webp/login")) {
			chain.doFilter(request, response);
			return;
		}
		if(uri.equals("/webp/") || uri.contains("aerodromi") || uri.contains(".")) {
			chain.doFilter(request, response);
			return;
		}
		if(request.getRequestURI().equals("/webp/LetServlet") && request.getMethod().equals("GET")) {
			chain.doFilter(request, response);
			return;
		}
		if(korisnik==null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
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
