package vasic.web.programiranje.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vasic.web.programiranje.dao.KorisnikDAO;
import vasic.web.programiranje.model.Korisnik;
import vasic.web.programiranje.tools.GsonProvider;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet("/user")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		Korisnik korisnik = KorisnikDAO.getAllByUsername(username);
//		if(korisnik==null) {
//			response.sendError(HttpServletResponse.SC_NOT_FOUND);
//			return;
//		}
		korisnik.setLozinka("");
		String korisnikJson = GsonProvider.getInstance().toJson(korisnik);	
		response.getWriter().write(korisnikJson);
		response.addHeader("Access-Control-Allow-Origin", "*");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
