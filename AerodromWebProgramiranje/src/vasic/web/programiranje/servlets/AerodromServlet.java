package vasic.web.programiranje.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vasic.web.programiranje.dao.AerodromDAO;
import vasic.web.programiranje.dto.LetDTO;
import vasic.web.programiranje.model.Aerodrom;
import vasic.web.programiranje.tools.GsonProvider;

/**
 * Servlet implementation class AerodromServlet
 */
@WebServlet("/aerodromi")
public class AerodromServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AerodromServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Aerodrom> aerodromi = AerodromDAO.getAll();
		String jsonAerodromi = GsonProvider.getInstance().toJson(aerodromi.stream().collect(Collectors.toList()));
		response.getWriter().write(jsonAerodromi);
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
