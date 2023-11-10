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
@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
    private static final String query = "SELECT BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA where id=?";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set content type
        res.setContentType("text/html");
        //get the id of record
        int id = Integer.parseInt(req.getParameter("id"));
        //LOAD JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        //
        //generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///book", "root", "Vishu@9480"); PreparedStatement ps = con.prepareStatement(query);) {
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
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
           

            pw.println("<form action='editurl?id=" + id + "' method='post'>");
            
            pw.println("<table class='table table-hover' style='display: grid;place-items: center;margin-top:200px;'>");
            pw.println("<tr class='bg-warning text-white border'>");
            pw.println("<th>Edit the Book Details</th> <td> </td class='bg-warning'></tr>");
     
            pw.println("<tr class='border'><td>Book Name</td>");
            pw.println("<td><input type='text' name='bookName' value='" + rs.getString(1) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr class='border'>");
            pw.println("<td>Book Edition</td>");
            pw.println("<td><input type='text' name='bookEdition' value='" + rs.getString(2) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr class='border'>");
            pw.println("<td>Book Price</td>");
            pw.println("<td><input type='text' name='bookPrice' value='" + rs.getFloat(3) + "'></td>");
            pw.println("</tr>");
            pw.println("<tr class='border'>");
            pw.println("<td><input class='btn btn-outline-info' type='submit' value='Edit'  '></td>");
            pw.println("<td><input class='btn btn-outline-danger' type='reset' value='cancel'></td>");
            pw.println("</tr>");
            pw.println("</table>");
            pw.println("</form>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<div class='card text-bg-primary mb-3'> <h1>" + se.getMessage() + "</h2> </div>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<div class='card text-bg-primary mb-3'> <h1>" + e.getMessage() + "</h2> </div>");
        }
        pw.println("<center> <a class='btn btn-primary' href='home.html'>Home</a></center>");
        pw.println(" <footer style='position: absolute; bottom: 0%;'>");
        pw.println("<p>&copy; 2023 Book Restration Application</p>");
        pw.println("</footer>");

        pw.println("</body>");
        pw.println("</html>");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}