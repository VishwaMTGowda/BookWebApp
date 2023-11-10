package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String query = "INSERT INTO BOOKDATA(BOOKNAME,BOOKEDITION,BOOKPRICE) VALUES(?,?,?)";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set content type
        res.setContentType("text/html");
        //GET THE book info
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
        //LOAD JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        //generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///book", "root", "Vishu@9480"); PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);
            int count = ps.executeUpdate();
            if (count == 1) {
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

            	 pw.println("<div class='alert alert-success' style='margin:30px; padding:50px;' role='alert'>"+ "<h2 style='text-align:center' >Record Is Registered Sucessfully</h2></div>");
            } else {
                pw.println("<h2>Record not Registered Sucessfully");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h2>");
        }
        pw.println("<center><a class ='btn btn-primary' href='home.html'>Home</a>");
        pw.println("<br>");
        pw.println("<br>");
        pw.println("<a class = 'btn btn-primary' href='bookList'>Book List</a></center>");
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