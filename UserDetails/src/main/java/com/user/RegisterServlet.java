package com.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String cnfm = request.getParameter("cnfm");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/preeti";

        if (pwd.equals(cnfm)) {
            Connection con = null;
            PreparedStatement ps = null;
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, "root", "PHW#84#jeor");

                String query = "INSERT INTO register (username, pwd) VALUES (?, ?)";
                ps = con.prepareStatement(query);

                ps.setString(1, username);
                ps.setString(2, pwd);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    // Redirect to login.html after successful registration
                    response.sendRedirect("Login.html");
                } else {
                    out.println("Not registered");
                }
            } catch (ClassNotFoundException | SQLException e) {
                out.println("Exception: " + e.getMessage());
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    out.println("Error closing resources: " + e.getMessage());
                }
            }
        } else {
            out.println("Passwords do not match");
        }
    }
}
