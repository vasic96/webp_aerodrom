package vasic.web.programiranje.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vasic.web.programiranje.dao.LetDAO;
import vasic.web.programiranje.dto.LetDTO;
import vasic.web.programiranje.model.Let;
import vasic.web.programiranje.tools.GsonProvider;

/**
 * Servlet implementation class DostupniLetoviServlet
 */
@WebServlet("/dostupni_letovi")
public class DostupniLetoviServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DostupniLetoviServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Let> letovi = new ArrayList<Let>();	
		letovi = LetDAO.dostupniLetovi();
		String jsonLetovi = GsonProvider.getInstance().toJson(letovi.stream().map(LetDTO::new).collect(Collectors.toList()));
		response.getWriter().write(jsonLetovi); 
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
