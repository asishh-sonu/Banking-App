package com.pack;

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

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    static String driver = "com.mysql.cj.jdbc.Driver";
    static String url = "jdbc:mysql://localhost:3306/preeti";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int accountNumber = Integer.parseInt(request.getParameter("accountNumberDeposit"));
        double amount = Double.parseDouble(request.getParameter("amountDeposit"));
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "root", "PHW#84#jeor");

            // Check if the account exists
            String checkAccountQuery = "SELECT * FROM account WHERE num = ?";
            PreparedStatement checkAccountStmt = conn.prepareStatement(checkAccountQuery);
            checkAccountStmt.setInt(1, accountNumber);
            ResultSet accountRs = checkAccountStmt.executeQuery();

            if (accountRs.next()) {
                // Account exists, proceed with deposit
                double currentBalance = accountRs.getDouble("balance");
                double newBalance = currentBalance + amount;

                // Update balance
                String updateBalanceQuery = "UPDATE account SET balance = ? WHERE num = ?";
                PreparedStatement updateBalanceStmt = conn.prepareStatement(updateBalanceQuery);
                updateBalanceStmt.setDouble(1, newBalance);
                updateBalanceStmt.setInt(2, accountNumber);
                int updateCount = updateBalanceStmt.executeUpdate();

                if (updateCount > 0) {
                    out.println("<h1>Deposit of Rs. " + amount + " successful</h1>");
                } else {
                    out.println("<h1>Deposit failed</h1>");
                }
            } else {
                out.println("<h1>Account does not exist</h1>");
            }

            // Forward back to the services page
            RequestDispatcher rd = request.getRequestDispatcher("Services.html");
            rd.include(request, response);

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
