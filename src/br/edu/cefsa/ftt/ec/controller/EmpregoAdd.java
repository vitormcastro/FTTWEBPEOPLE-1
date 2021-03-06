package br.edu.cefsa.ftt.ec.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import br.edu.cefsa.ftt.ec.dao.EmpregoDao;
import br.edu.cefsa.ftt.ec.dao.TrabalhoDao;
import br.edu.cefsa.ftt.ec.model.Emprego;
import br.edu.cefsa.ftt.ec.model.Trabalho;
import br.edu.cefsa.ftt.ec.util.JsonConverter;

/**
 * Servlet implementation class EmpregoAdd
 */
@WebServlet(name = "empregoAdd", urlPatterns = { "/empregoAdd" })
public class EmpregoAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpregoAdd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json"); //MIME Type
		response.setCharacterEncoding("utf-8");
		// TODO Auto-generated method stub
		try {
			EmpregoDao dao = new EmpregoDao();
			List<Emprego> emprega = dao.getAllEmprego();
			
			JsonConverter converter = new JsonConverter();
			String output = converter.convertToJsonArray(emprega, "Empregos");
			
			System.out.println(output);
			
			response.setStatus(200);
			response.getWriter().print(output);
			
		}catch (Exception e) {
			
			String status = "Error";
			String message = e.getMessage();	
			String now = String.valueOf(new Date());									
						
		    //create Json Object
			JsonObject json = new JsonObject();

			// put some value pairs into the JSON object .
			
			json.addProperty("Status", status);
			json.addProperty("Message", message);
			json.addProperty("Time", now);

			System.out.println(json.toString());
			
			response.setStatus(100);
			response.getWriter().print(json.toString());
	         
		}
		response.flushBuffer();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Emprego emp = new Emprego(Long.valueOf(request.getParameter("pidPeople")),Long.valueOf(request.getParameter("pidTrabalho")),
									request.getParameter("pDataAdmissao"));		
		System.out.print(emp);
		
		EmpregoDao dao = new EmpregoDao();
		
		String status = "OK";
		String message = "Emprego Adicionado com Sucesso!!!";
		String now = String.valueOf(new Date());
		
		try {
		   dao.addEmprego(emp);
		   response.setStatus(200);
		} catch (Exception e) {
			status = "Error";
			message = e.getMessage();
			System.err.println(now +  " - Ops!! - " + message);
			System.err.println(now +  " - Ops!! - " + emp);
			e.printStackTrace();
			response.setStatus(100);
		}
		
		response.setContentType("application/json"); //MIME Type
		response.setCharacterEncoding("utf-8");
		
		
	    //create Json Object
		JsonObject json = new JsonObject();

		// put some value pairs into the JSON object .
		
		json.addProperty("Status", status);
		json.addProperty("Message", message);
		json.addProperty("Time", now);


		 response.getWriter().print(json.toString());
         response.flushBuffer();
	}

}
