package com.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.ScanDAO;
import com.dao.UserDAO;
import com.model.User;
import com.scanner.ScanUtils;


@WebServlet(name="AdminOps", urlPatterns= {"/deletescan","/deleteuser"})
public class AdminOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
    	String name;
    	User user = new User();
		String referer = request.getHeader("referer");
        try{
        	switch (action){
        	case "/deleteuser":
        		name = request.getParameter("name");
        		UserDAO.deleteUser(name);
        		response.sendRedirect(referer);
                break;
            case "/deletescan":
            	  String scanDate = request.getParameter("scanDate");
            	  ScanDAO.deleteScan(scanDate);
            	  response.sendRedirect(referer);
            	  break;
        		}
        	}
        catch (Exception e) {
        	e.printStackTrace();
        }
	}

}
