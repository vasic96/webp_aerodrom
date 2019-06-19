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
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/change_password")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovanKorisnik = (Korisnik) session.getAttribute("ulogovanKorisnik");
		String newPassword = request.getParameter("new_password");
		String username = request.getParameter("username");
//		if(ulogovanKorisnik==null) {
//			response.sendError(401);
//			return;
//		}
		if(ulogovanKorisnik.isBlokiran()) {
			response.sendError(403);
			return;
		}
		if(ulogovanKorisnik.getKorisnickoIme().equals(username)) {
			String oldPassword = request.getParameter("old_password");
			if(!KorisnikDAO.changeMyPassword(username, oldPassword, newPassword)) {
				response.sendError(400);
				return;
			}
			
			response.setStatus(200);
			
		} else {
			if(!KorisnikDAO.changePassword(username, newPassword)) {
				response.sendError(400);
				return;
			}
			
			response.setStatus(200);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
