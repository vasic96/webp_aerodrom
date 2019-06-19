package vasic.web.programiranje.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vasic.web.programiranje.model.Korisnik;

public class LoginCheck {
	
	public static boolean isLoggedIn(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Korisnik ulogovanKorisnik = (Korisnik) session.getAttribute("ulogovanKorisnik");
		return ulogovanKorisnik == null;
	}
	
	public static boolean isAdmin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Korisnik ulogovanKorisnik = (Korisnik) session.getAttribute("ulogovanKorisnik");
		if(ulogovanKorisnik==null) {
			return false;
		} else if (ulogovanKorisnik.isAdmin()) {
			return true;
		} else {
			return false;
		}
	}

}
