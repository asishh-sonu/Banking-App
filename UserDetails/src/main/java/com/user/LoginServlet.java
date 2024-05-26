package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/preeti";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "root", "PHW#84#jeor");

            String sql = "SELECT * FROM register WHERE username = ? AND pwd = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            response.setContentType("text/html");
            if (rs.next()) {
                out.println("<h1>Hello " + username + ", Welcome to Banking Services</h1>");
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                RequestDispatcher rd = request.getRequestDispatcher("Services.html");
                rd.include(request, response);
            } else {
                out.println("<h1>Login Failed, Please Login Again</h1>");
                RequestDispatcher rd = request.getRequestDispatcher("Login.html");
                rd.include(request, response);
            }
        } catch (Exception e) {
            out.println("<h1>Exception : " + e.getMessage() + "</h1>");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
