package vasic.web.programiranje.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vasic.web.programiranje.dao.KorisnikDAO;
import vasic.web.programiranje.dao.LetDAO;
import vasic.web.programiranje.dao.RezervacijaDAO;
import vasic.web.programiranje.dto.LetDTO;
import vasic.web.programiranje.dto.RezervacijaDTO;
import vasic.web.programiranje.model.Korisnik;
import vasic.web.programiranje.model.Let;
import vasic.web.programiranje.model.Rezervacija;
import vasic.web.programiranje.tools.GsonProvider;
import vasic.web.programiranje.tools.LoginCheck;

/**
 * Servlet implementation class RezervacijaFirstStepServlet
 */
@WebServlet("/rezervacija")
public class RezervacijaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RezervacijaServlet() {
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
		response.getWriter().write(GsonProvider.getInstance().toJson(RezervacijaDAO.sveREzervacije().stream()
				.filter(rezervacija -> {			
					return (LoginCheck.isAdmin(request) || rezervacija.getKorisnik().getKorisnickoIme().equals(ulogovanKorisnik.getKorisnickoIme()));					
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
		Korisnik korisnik = (Korisnik) request.getSession().getAttribute("ulogovanKorisnik");
//		if(korisnik==null) {
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//			return;
//		}
		if(korisnik.isBlokiran()) {
			response.sendError(403);
			return;
		}
		String action = request.getParameter("action");
		String body = request.getReader().lines()
			    .reduce("", (accumulator, actual) -> accumulator + actual);
		
		RezervacijaDTO rezervacijaDTO = GsonProvider.getInstance().fromJson(body, RezervacijaDTO.class);
		if(RezervacijaDAO.dajZauzetaMesta(rezervacijaDTO.getPolazniLetId()).contains(rezervacijaDTO.getSedistePolazniLet())) {
			response.sendError(400);
			return;
		}
		Rezervacija rezervacija = new Rezervacija();
		rezervacija.setPolazniLet(LetDAO.getById(rezervacijaDTO.getPolazniLetId()));
		rezervacija.setDatumRezervacije(new Date());
		rezervacija.setImePutnika(rezervacijaDTO.getImePutnika());
		rezervacija.setPrezimePutnika(rezervacijaDTO.getPrezimePutnika());
		rezervacija.setKorisnik(KorisnikDAO.getAllByUsername(korisnik.getKorisnickoIme()));
		rezervacija.setSedistePolazniLet(rezervacijaDTO.getSedistePolazniLet());
		rezervacija.setUkupnaCena(rezervacija.getPolazniLet().getCena());
		if(rezervacijaDTO.getPovratniLetId()>0) {
			if(RezervacijaDAO.dajZauzetaMesta(rezervacijaDTO.getPovratniLetId()).contains(rezervacijaDTO.getSedistePovratniLet())) {
				response.sendError(400);
				return;
			}
			rezervacija.setPovratniLet(LetDAO.getById(rezervacijaDTO.getPovratniLetId()));
			rezervacija.setSedistePovratniLet(rezervacijaDTO.getSedistePovratniLet());
			rezervacija.setUkupnaCena(rezervacija.getUkupnaCena()+rezervacija.getPovratniLet().getCena());
		}
		if(action.equalsIgnoreCase("kupi")) {
			rezervacija.setDatumProdajeKarte(new Date());
		}
		System.out.println(rezervacija.toString());
		
		if(!RezervacijaDAO.dodajRezervaciju(rezervacija)) {
			response.sendError(400);
			return;
		}
		

		response.addHeader("Access-Control-Allow-Origin", "*");
	}
	
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Korisnik korisnik = (Korisnik) req.getSession().getAttribute("ulogovanKorisnik");
//		if(korisnik == null) {
//			resp.sendError(401);
//			return;
//		}
		if(!korisnik.isAdmin() || korisnik.isBlokiran()) {
			resp.sendError(403);
			return;
		}
		
		int id = Integer.parseInt(req.getParameter("id"));
		if(!RezervacijaDAO.izbrisiRezervaciju(id)) {
			resp.sendError(400);
			return;
		}
		resp.setStatus(200);
	}

}
