package vasic.web.programiranje.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vasic.web.programiranje.dao.RezervacijaDAO;
import vasic.web.programiranje.model.Korisnik;
import vasic.web.programiranje.model.Rezervacija;

/**
 * Servlet implementation class KupiKartuServlet
 */
@WebServlet("/kupi_kartu")
public class KupiKartuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KupiKartuServlet() {
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
		int id = Integer.parseInt(request.getParameter("id"));
		Rezervacija rezervacija = RezervacijaDAO.getById(id);
		if(rezervacija==null) {
			response.sendError(404);
			return;
		}
		if(!ulogovanKorisnik.isAdmin() && !ulogovanKorisnik.getKorisnickoIme().equals(rezervacija.getKorisnik().getKorisnickoIme())) {
			response.sendError(403);
			return;
		}
		if(!RezervacijaDAO.kupiRezervaciju(rezervacija.getId())) {
			response.sendError(400);
			return;
		} 
		
		response.setStatus(200);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
