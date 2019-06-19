package vasic.web.programiranje.servlets;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vasic.web.programiranje.dao.RezervacijaDAO;
import vasic.web.programiranje.dto.RezervacijaDTO;
import vasic.web.programiranje.model.Korisnik;
import vasic.web.programiranje.tools.GsonProvider;
import vasic.web.programiranje.tools.LoginCheck;

/**
 * Servlet implementation class RezervacijaZaLetServlet
 */
@WebServlet("/rezervacija_let")
public class RezervacijaZaLetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RezervacijaZaLetServlet() {
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
		if(!ulogovanKorisnik.isAdmin()) {
			response.sendError(403);
			return;
		}
		String tip = request.getParameter("tip");
		int letId = Integer.parseInt(request.getParameter("let_id"));
		response.getWriter().write(GsonProvider.getInstance().toJson(RezervacijaDAO.rezervacijeZaLet(letId).stream()
				.filter(rezervacija -> {
					if(tip.equals("all")) {
						return true;
					} else if(tip.equals("rezervacija")) {
						return rezervacija.getDatumProdajeKarte()==null;
					} else {
						return rezervacija.getDatumProdajeKarte()!=null;
					}
				})
				.map(RezervacijaDTO::new)
				.collect(Collectors.toList())));
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
