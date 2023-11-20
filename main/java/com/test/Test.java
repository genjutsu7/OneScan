package com.test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.ScanDAO;
import com.dao.UserDAO;
import com.model.Scans;


@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	
		try {
			List<Scans> scanList = ScanDAO.listScans();
			request.setAttribute("scans", scanList);
			request.getRequestDispatcher("/test.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}

}
