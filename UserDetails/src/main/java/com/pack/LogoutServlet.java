package com.pack;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet 
{
private static final long serialVersionUID = 1L;
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
 HttpSession session = request.getSession(false); 
 if (session != null) {
 session.invalidate();
 }
 
 response.sendRedirect("Login.html");
 }
}
