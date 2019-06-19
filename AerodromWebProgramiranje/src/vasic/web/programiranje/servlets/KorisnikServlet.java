package vasic.web.programiranje.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import vasic.web.programiranje.dao.KorisnikDAO;
import vasic.web.programiranje.dao.LetDAO;
import vasic.web.programiranje.dao.RezervacijaDAO;
import vasic.web.programiranje.dto.LetDTO;
import vasic.web.programiranje.model.Korisnik;
import vasic.web.programiranje.tools.GsonProvider;
import vasic.web.programiranje.tools.LoginCheck;

/**
 * Servlet implementation class KorisnikServlet
 */
@WebServlet("/KorisnikServlet")
public class KorisnikServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KorisnikServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Korisnik ulogovanKorisnik = (Korisnik) session.getAttribute("ulogovanKorisnik");
		if(ulogovanKorisnik==null) {
			response.sendError(401);
			return;
		}
		if(!ulogovanKorisnik.isAdmin()) {
			response.sendError(403);
			return;
		}
		List<Korisnik> korisnici;
		if (request.getParameterMap().containsKey("role")) {
			if(request.getParameter("role").equalsIgnoreCase("admin")) {
				korisnici = KorisnikDAO.getAllByRole(true);
			} else {
				korisnici = KorisnikDAO.getAllByRole(false);

			}
		} else {
			korisnici=KorisnikDAO.getAll();
		}
		String jsonLetovi = GsonProvider.getInstance().toJson(korisnici.stream().collect(Collectors.toList()));
		response.getWriter().write(jsonLetovi); 
		response.addHeader("Access-Control-Allow-Origin", "*");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = GsonProvider.getInstance();
		Korisnik korisnik = gson.fromJson(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), Korisnik.class);
		korisnik.setDatumRegistracije(new Date());
		korisnik.setAdmin(false);
		korisnik.setBlokiran(false);
		if(!KorisnikDAO.register(korisnik)) {
			response.sendError(400);
			return;
		}
		response.setStatus(200);
	}
	
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!LoginCheck.isAdmin(request)) {
			response.sendError(403);
			return;
		}
		String username = request.getParameter("username");
		
		if(RezervacijaDAO.daLiKorisnikImaProdateKarte(username)) {
			if(!KorisnikDAO.logicnkiObrisi(username)) {
				response.sendError(400);
				return;
			}
			response.setStatus(200);
			return;
		} else {
			
			if(!RezervacijaDAO.izbrisiRezervacijeZaKorisnika(username)) {
				response.sendError(400);
				return;
			}
			if(!KorisnikDAO.izbrisiKorisnika(username)) {
				response.sendError(404);
				return;
			}
			response.setStatus(200);
		}
		
	}

}
