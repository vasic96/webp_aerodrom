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
import vasic.web.programiranje.tools.GsonProvider;

/**
 * Servlet implementation class KorisnikByUsernameServlet
 */
@WebServlet("/username")
public class KorisnikByUsernameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KorisnikByUsernameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovanKorisnik = (Korisnik) session.getAttribute("ulogovanKorisnik");
//		if(ulogovanKorisnik==null) {
//			response.sendError(401);
//			return;
//		}
		String username = request.getParameter("username");
		if(!ulogovanKorisnik.isAdmin() && !username.equals(ulogovanKorisnik.getKorisnickoIme())) {
			response.sendError(403);
			return;
		}
		Korisnik korisnik = KorisnikDAO.getAllByUsername(username);
		if(korisnik==null) {
			response.sendError(400);
			return;
		}
		korisnik.setLozinka("");
		response.getWriter().write(GsonProvider.getInstance().toJson(korisnik));
		response.setStatus(200);
	}

}
