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
 * Servlet implementation class PromeniSedisteServlet
 */
@WebServlet("/promeni_sediste")
public class PromeniSedisteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PromeniSedisteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovanKorisnik = (Korisnik) session.getAttribute("ulogovanKorisnik");
		int rezervacijaId = Integer.parseInt(request.getParameter("id"));
		Rezervacija rezervacija = RezervacijaDAO.getById(rezervacijaId);
		int sedistePolazniLet = Integer.parseInt(request.getParameter("sediste_polazni"));
		
		if(!ulogovanKorisnik.isAdmin() && !ulogovanKorisnik.getKorisnickoIme().equals(rezervacija.getKorisnik().getKorisnickoIme())) {
			response.sendError(403);
			return;
		}
		if(!RezervacijaDAO.promeniSediste(sedistePolazniLet, "polazni", rezervacijaId)) {
			response.sendError(400);
			return;
		}
		if(request.getParameter("sediste_povratni") != null) {
			int sedistePovratni = Integer.parseInt(request.getParameter("sediste_povratni"));
			if(!RezervacijaDAO.promeniSediste(sedistePovratni, "povratni", rezervacijaId)) {
				response.sendError(400);
				return;
			}
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
