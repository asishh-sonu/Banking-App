package com.pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AccountCreationServlet")
public class AccountCreationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/preeti";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int num = Integer.parseInt(request.getParameter("accountNumber"));
        String name = request.getParameter("name");
        int balance = Integer.parseInt(request.getParameter("balance"));
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "root", "PHW#84#jeor");

            String sql = "insert into account values(?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, num);
            stmt.setString(2, name);
            stmt.setInt(3, balance);
            int count = stmt.executeUpdate();

            response.setContentType("text/html");
            if (count > 0) {
                out.println("<h1>Account Created Successfully</h1>");
                RequestDispatcher rd = request.getRequestDispatcher("Services.html");
                rd.include(request, response);
            } else {
                out.println("<h1>Account Creation Failed, Try Again</h1>");
                RequestDispatcher rd = request.getRequestDispatcher("Services.html");
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
