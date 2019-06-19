package vasic.web.programiranje.servlets;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import vasic.web.programiranje.dao.KorisnikDAO;
import vasic.web.programiranje.model.Korisnik;
import vasic.web.programiranje.model.LoginData;
import vasic.web.programiranje.tools.GsonProvider;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().write(GsonProvider.getInstance().toJson((Korisnik)request.getSession().getAttribute("ulogovanKorisnik")));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Gson gson = GsonProvider.getInstance();
		LoginData logindata = gson.fromJson(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), LoginData.class);
		System.out.println(logindata.toString());
		Korisnik korisnik = KorisnikDAO.login(logindata.getUsername(), logindata.getPassword());
		if(korisnik==null) {
			response.sendError(403);
			return;
		}
		session.setAttribute("ulogovanKorisnik", korisnik);
		korisnik.setLozinka("");
		response.getWriter().write(GsonProvider.getInstance().toJson(korisnik));
		response.setStatus(200);
		response.addHeader("Access-Control-Allow-Origin", "*");

	}


}
