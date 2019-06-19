package vasic.web.programiranje.servlets;
import java.util.concurrent.ThreadLocalRandom;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import vasic.web.programiranje.dao.LetDAO;
import vasic.web.programiranje.dao.RezervacijaDAO;
import vasic.web.programiranje.dto.LetDTO;
import vasic.web.programiranje.model.Korisnik;
import vasic.web.programiranje.model.Let;
import vasic.web.programiranje.tools.GsonProvider;
import vasic.web.programiranje.tools.LoginCheck;

/**
 * Servlet implementation class LetServlet
 */
@WebServlet("/LetServlet")
public class LetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Let> letovi = getLetovi(request);
		String jsonLetovi = GsonProvider.getInstance().toJson(letovi.stream().map(LetDTO::new).collect(Collectors.toList()));
		response.getWriter().write(jsonLetovi); 
		response.addHeader("Access-Control-Allow-Origin", "*");

	}
  
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!LoginCheck.isAdmin(request)) {
			response.sendError(403);
			return;
		}
		LetDTO letDTO = GsonProvider.getInstance().fromJson(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), LetDTO.class);
		Let let = new Let(letDTO);
		String brojLeta = (let.getPolazniAerodrom().getNaziv().substring(0,2) + 
				let.getDolazniAerodrom().getNaziv().substring(0,2) + ThreadLocalRandom.current().nextInt(1000, 9998 + 1));
		let.setBroj(brojLeta.toUpperCase());
		if(!LetDAO.dodajLet(let)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		response.setStatus(200);


	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		LetDTO letDTO = GsonProvider.getInstance().fromJson(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), LetDTO.class);
		if(!LetDAO.azurirajLet(letDTO) || LoginCheck.isAdmin(request)) {
			response.sendError(400);
		}
		response.setStatus(200);



		
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!LoginCheck.isAdmin(request)) {
			response.sendError(403);
			return;
		}
		int letId = Integer.valueOf(request.getParameter("letId"));
		
		if(RezervacijaDAO.daLiLetImaProdateKarte(letId)) {
			if(!LetDAO.logicalDelte(letId)) {
				response.sendError(400);
				return;
			}
			response.setStatus(200);
			return;
		} else {
			
			if(!RezervacijaDAO.izbrisiRezervacijeZaLet(letId)) {
				response.sendError(400);
				return;
			}
			if(!LetDAO.izbrisiLet(letId)) {
				response.sendError(404);
				return;
			}
			response.setStatus(200);
		}
	}
	
	private List<Let> getLetovi(HttpServletRequest request){
		List<Let> letovi = new ArrayList<Let>();
		if(request.getParameterMap().containsKey("broj")) {
			letovi = LetDAO.getAllByBroj(request.getParameter("broj"));
		}
		else if(request.getParameterMap().containsKey("cenaOd") && request.getParameterMap().containsKey("cenaDo")){
			letovi = LetDAO.getAllByCena(Float.parseFloat(request.getParameter("cenaOd")), Float.parseFloat(request.getParameter("cenaDo")));
		} else if(request.getParameterMap().containsKey("tip") && request.getParameterMap().containsKey("aerodromId")) {
			letovi = LetDAO.getAllByAerodrom(request.getParameter("tip"), Integer.parseInt(request.getParameter("aerodromId")));
		} else if(request.getParameterMap().containsKey("datum_od")) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date datumOd = null,datumDo=null;
			try {
				datumOd = formatter.parse(request.getParameter("datum_od"));
				datumDo = formatter.parse(request.getParameter("datum_do"));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			letovi = LetDAO.getAllByDatum(request.getParameter("tip"), datumOd, datumDo);
		}
		else {
			letovi = LetDAO.getAll();
		}
		
		return letovi;
	}
	


}