package vasic.web.programiranje.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vasic.web.programiranje.dao.LetDAO;
import vasic.web.programiranje.dao.RezervacijaDAO;
import vasic.web.programiranje.model.Let;
import vasic.web.programiranje.tools.GsonProvider;

/**
 * Servlet implementation class SlobodnaMestaServlet
 */
@WebServlet("/slobodna_mesta")
public class SlobodnaMestaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SlobodnaMestaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 int letId = Integer.parseInt(request.getParameter("let_id"));
		 Let let = LetDAO.getById(letId);
		 List<Integer> zauzetaMestaZaLet = RezervacijaDAO.dajZauzetaMesta(letId);
		 List<Integer> slobodnaMesta = new ArrayList<Integer>();
		 for(int i = 1;i<let.getBrojSedista()+1;i++) {
			 if(!zauzetaMestaZaLet.contains(i)) {
				 slobodnaMesta.add(i);
			 }
		 }
		 
		 response.getWriter().write(GsonProvider.getInstance().toJson(slobodnaMesta));
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
