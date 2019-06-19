package vasic.web.programiranje.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import vasic.web.programiranje.dao.KorisnikDAO;
import vasic.web.programiranje.model.Korisnik;
import vasic.web.programiranje.model.LoginData;
import vasic.web.programiranje.tools.GsonProvider;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = GsonProvider.getInstance();
		LoginData logindata = gson.fromJson(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), LoginData.class);
		Korisnik korisnik = new Korisnik();
		korisnik.setKorisnickoIme(logindata.getUsername());
		korisnik.setLozinka(logindata.getPassword());
		korisnik.setDatumRegistracije(new Date());
		korisnik.setAdmin(false);
		korisnik.setBlokiran(false);
		if(!KorisnikDAO.register(korisnik)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		response.setStatus(200);
		response.addHeader("Access-Control-Allow-Origin", "*");
		
	}

}
