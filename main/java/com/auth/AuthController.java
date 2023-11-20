package com.auth;

import com.dao.UserDAO;
import com.model.User;
import com.scanner.ScanUtils;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;



@WebServlet(name = "AuthController", urlPatterns = {"/login","/logout","/register"})
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        HttpSession session;
        User user = new User();
      
        try{
        	switch (action){
            case "/login":
                user.setName(request.getParameter("user"));
                user.setPassword(ScanUtils.buildHash(request.getParameter("pass")));
                if(!UserDAO.validateLogin(user)){
                    response.sendRedirect("login.jsp");
                }
                else {
                    session = request.getSession();
                    session.setAttribute("user", user);
                    response.sendRedirect("index.jsp");
                }
                break;
            case "/register":
            	 try {
                user.setName(request.getParameter("user"));
                user.setEmail(request.getParameter("email"));
                user.setPassword(ScanUtils.buildHash(request.getParameter("pass")));
                UserDAO.registerUser(user);
                session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("index.jsp");
            	 }
            	 catch (java.sql.SQLIntegrityConstraintViolationException e) {
            		 response.sendRedirect("duplicate.jsp");
            	 }
                break;
            case "/logout":
                session = request.getSession(false);
                session.invalidate();
                response.sendRedirect("login.jsp");
                break;
        	}
        }
        catch (Exception e){
            e.printStackTrace();
       }
   	}
}
