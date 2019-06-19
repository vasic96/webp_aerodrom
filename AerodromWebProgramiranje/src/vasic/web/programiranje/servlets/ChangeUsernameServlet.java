package vasic.web.programiranje.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vasic.web.programiranje.dao.KorisnikDAO;
import vasic.web.programiranje.model.Korisnik;

/**
 * Servlet implementation class ChangeUsernameServlet
 */
@WebServlet("/change_username")
public class ChangeUsernameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeUsernameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovanKorisnik = (Korisnik) session.getAttribute("ulogovanKorisnik");
		if(ulogovanKorisnik.isBlokiran()) {
			response.sendError(403);
			return;
		}
		String username = request.getParameter("username");
		String newUsername = request.getParameter("new_username");
		if(!ulogovanKorisnik.isAdmin() && !ulogovanKorisnik.getKorisnickoIme().equals(username)) {
			response.sendError(403);
			return;
		}
		if(KorisnikDAO.getAllByUsername(username)==null) {
			response.sendError(400);
			return;
		}
		if(!KorisnikDAO.changeUsername(username,newUsername)) {
			response.sendError(404);
			return;
		}
		response.setStatus(200);
		response.addHeader("Access-Control-Allow-Origin", "*");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
