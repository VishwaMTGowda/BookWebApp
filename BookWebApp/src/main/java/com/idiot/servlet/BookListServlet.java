package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
    private static final String query = "SELECT ID, BOOKNAME, BOOKEDITION, BOOKPRICE FROM BOOKDATA";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");

        // LOAD JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // Generate the JDBC URL and establish a connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/book", "root", "Vishu@9480");
             PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            // HTML structure
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<link rel='stylesheet' href='css/bootstrap.css'>");
            pw.println("<style>");
            pw.println("footer{ background-color: #333;\r\n"
            		+ "            color: #fff;\r\n"
            		+ "            text-align: center;\r\n"
            		+ "            padding: 30px;\r\n"
            		+ "            width:100%;\r\n"
            		+ "        }\r\n"
            		+ "\r\n"
            		+ "        /* Adjust styles for responsiveness */\r\n"
            		+ "        @media (max-width: 768px) {\r\n"
            		+ "            footer {\r\n"
            		+ "                font-size: 14px;\r\n"
            		+ "            }\r\n"
            		+ "        }\r\n"
            		+ "        h2 {\r\n"
            		+ "			margin:10px;\r\n"
            		+ "			padding:8px;\r\n"
            		+ "		}");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<nav class='navbar navbar-light bg-warning'>");
            pw.println("<a class='navbar-brand text-light' href='#'>");
            pw.println("<h1>Book Register Application</h1>");
            pw.println("</a>");
            pw.println("</nav>");

            pw.println("<table class='table table-hover' style='display: grid;place-items: center;margin-top:200px;'>");
            pw.println("<tr class='bg-warning text-white'>");
            pw.println("<th>Book Id</th>");
            pw.println("<th>Book Name</th>");
            pw.println("<th>Book Edition</th>");
            pw.println("<th>Book Price</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");

            while (rs.next()) {
                pw.println("<tr class='border'>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getString(3) + "</td>");
                pw.println("<td>" + "₹" + rs.getFloat(4) + "</td>");
                pw.println("<td><a class='btn btn-outline-success' href='editScreen?id=" + rs.getInt(1) + "'>Edit</a></td>");
                pw.println("<td><a class='btn btn-outline-danger' href='deleteurl?id=" + rs.getInt(1) + "'>Delete</a></td>");
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("<center><a class='btn btn-primary' type='button' href='home.html' >Home</a></center>");
            pw.println(" <footer style='position: absolute; bottom: 0%;'>");
            pw.println("<p>&copy; 2023 Book Restration Application</p>");
            pw.println("</footer>");

            pw.println("</body>");
            pw.println("</html>");

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h2>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
