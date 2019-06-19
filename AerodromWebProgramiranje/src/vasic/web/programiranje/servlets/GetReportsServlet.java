package vasic.web.programiranje.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vasic.web.programiranje.dao.AerodromDAO;
import vasic.web.programiranje.dao.IzvestajDAO;
import vasic.web.programiranje.model.Izvestaj;
import vasic.web.programiranje.model.IzvestajUkupno;
import vasic.web.programiranje.model.Korisnik;
import vasic.web.programiranje.tools.GsonProvider;

/**
 * Servlet implementation class GetReportsServlet
 */
@WebServlet("/report")
public class GetReportsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetReportsServlet() {
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
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date datumOdTemp = null,datumDoTemp=null;

		try {
			 datumOdTemp = formatter.parse(request.getParameter("datum_od"));
			 datumDoTemp = formatter.parse(request.getParameter("datum_do"));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final Date datumOd = datumOdTemp;
		final Date datumDo=datumDoTemp;
		
		IzvestajUkupno ukupno = new IzvestajUkupno();
		AerodromDAO.getAll().forEach(
				aerodrom->{
					
					Izvestaj izvestaj = new Izvestaj();
					izvestaj.setAerodromNaziv(aerodrom.getNaziv());
					izvestaj.setBrojLetova(IzvestajDAO.getBrojLetova(aerodrom.getId(), datumOd, datumDo));
					izvestaj.setBrojProdatihKarata(IzvestajDAO.getBrojProdatihKarata(aerodrom.getId(), datumOd, datumDo));
					izvestaj.setUkupnaCenaKarata(IzvestajDAO.ukupnaCenaProdatihKarata(aerodrom.getId(), datumOd, datumDo));
					
					ukupno.addIzvestaj(izvestaj);
					
			}
				
		);
		
		response.getWriter().write(GsonProvider.getInstance().toJson(ukupno));
		response.setStatus(200);
		
		
		
		
		
		
		
	}

}
