package com.mdits.sqlinject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "userCheck1", urlPatterns = {"/userCheck1"})
public class userCheck1 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    	
    	response.setContentType("text/html;charset=UTF-8");
       
    	PrintWriter out = response.getWriter();
        out.println("<h1>SQL Injection Example</h1><br/><br/>");
        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet userCheck</title>");
            out.println("</head>");
            out.println("<body>");
            String user = request.getParameter("user");


            System.out.println("MySQL Connect Example.");
            
            // JDBC
            
            Connection conn = null;
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "test";
            String driver = "com.mysql.cj.jdbc.Driver";
            String userName = "root";
            String password = "1234";
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url + dbName, userName, password);
                System.out.println("Connected to the database");

       // ######################  SQL INJECTION OCCUR ##################    
                
//                Statement st = conn.createStatement();
//                String query="SELECT * FROM  User where userid='"+user+"'";
//                out.println("Query : "+query);
//                out.println("Query : "+query);
//
//                System.out.printf(query);
//                        ResultSet res = st.executeQuery(query);
                
       // ######################  SQL INJECTION Prevention ##################       
                
                
      PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM  User where userId=?");
      preparedStatement.setString(1, user);
      ResultSet res = preparedStatement.executeQuery();
                       
                out.println("<br/><br/>Results");
                while (res.next()) {
                    //int i = res.getInt("Emp_code");
                    String s = res.getString("userId");
                    out.println("<br/><br/>\t\t" + s);
                }

                conn.close();
                
                System.out.println("Disconnected from database");
            } catch (Exception e) {
                e.printStackTrace();
            }

            out.println("</body>");
            out.println("</html>");

        } finally {
            out.close();
        }
    }

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
}
