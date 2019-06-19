package vasic.web.programiranje.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vasic.web.programiranje.dao.LetDAO;
import vasic.web.programiranje.model.Let;
import vasic.web.programiranje.tools.GsonProvider;

/**
 * Servlet implementation class PovratniLetServer
 */
@WebServlet("/povratni_let")
public class PovratniLetServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PovratniLetServer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!request.getParameterMap().containsKey("let_id")) {
			response.sendError(400);
			return;
		}
		int letId = Integer.parseInt(request.getParameter("let_id"));
		Let let = LetDAO.getById(letId);
		if(let == null) {
			response.sendError(404);
			return;
		}
		List<Let> povratniLetovi = LetDAO.dostupniPovratniLetvi(let);
		String povratniLetoviJson = GsonProvider.getInstance().toJson(povratniLetovi);
		response.getWriter().write(povratniLetoviJson);
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
