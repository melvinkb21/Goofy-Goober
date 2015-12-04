package servlets;

import datastore.DAOSQLite;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Employee;

/**
 * All of this application's web pages send their requests to this controller
 * which then updates the model / database as needed and transfers control with
 * data to one the the HTML/JSP view-oriented programs for display.
 *
 * @author John Phillips
 */
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get real path to the sqlite db
        ServletContext sc = this.getServletContext();
        String dbPath = sc.getRealPath("/WEB-INF/employee.db");

        // set default url
        String url = "/home.html";

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "home";
        }

        // perform action and set url
        if (action.equalsIgnoreCase("home")) {
            url = "/home.html";

        } else if (action.equalsIgnoreCase("createRecord")) {
            double isbn;

            // get parameters passed in from the request
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String subject = request.getParameter("subject");
            String isbnString = request.getParameter("isbn");

            // validate and convert isbn string into a double
            if (isbnString == null || isbnString.isEmpty()) {
                isbn = 0;
            } else {
                isbn = Double.parseDouble(isbnString);
            }

            // store data in an Employee object
            Employee employee = new Employee(0, title, author, subject, isbn);

            // validate the parameters
            if (title == null || author == null || subject == null
                    || isbnString == null || title.isEmpty()
                    || author.isEmpty() || subject.isEmpty()
                    || isbnString.isEmpty()) {
                url = "/createRecord.jsp";
            } else {
                // insert this data record into the database
                DAOSQLite.createRecord(employee, dbPath);
                url = "/home.html";
            }

            // add the user object to the request object so that the data is available on the next page
            request.setAttribute("employee", employee);
            
        } else if (action.equalsIgnoreCase("report")) {
            List<Employee> mydata = null;
            mydata = DAOSQLite.retrieveAllRecordsByName(dbPath);
            request.setAttribute("mydata", mydata);
            url = "/displayRecords.jsp";

        } else if (action.equalsIgnoreCase("updateRecord1")) {
            String idString = request.getParameter("empId");
            if (idString == null || idString.isEmpty()) {
                url = "/updateRecord1.jsp";
            } else {
                // get employee
                Employee employee = DAOSQLite.retrieveRecordById(Integer.parseInt(idString), dbPath);
                request.setAttribute("employee", employee);
                url = "/updateRecord2.jsp";
            }

        } else if (action.equalsIgnoreCase("updateRecord2")) {
            int empId;
            double isbn;

            // get parameters passed in from the request
            String empIdString = request.getParameter("empId");
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String subject = request.getParameter("subject");
            String isbnString = request.getParameter("isbn");

            // validate and convert empId string into an int
            if (empIdString == null || empIdString.isEmpty()) {
                empId = 0;
            } else {
                empId = Integer.parseInt(empIdString);
            }

            // validate and convert isbn string into a double
            if (isbnString == null || isbnString.isEmpty()) {
                isbn = 0;
            } else {
                isbn = Double.parseDouble(isbnString);
            }

            // store data in an Employee object
            Employee employee = new Employee(empId, title, author, subject, isbn);

            // validate the parameters
            if (empIdString == null || title == null || author == null
                    || subject == null || isbnString == null
                    || empIdString.isEmpty() || title.isEmpty()
                    || author.isEmpty() || subject.isEmpty()
                    || isbnString.isEmpty()) {
                request.setAttribute("employee", employee);
                url = "/updateRecord2.jsp";
            } else {
                // update this record in the database
                DAOSQLite.updateRecord(employee, dbPath);
                url = "/home.html";
            }

        } else if (action.equalsIgnoreCase("deleteRecord")) {
            String idString = request.getParameter("id");
            if (idString == null || idString.isEmpty()) {
                url = "/deleteRecord.jsp";
            } else {
                // delete this data record from the database
                DAOSQLite.deleteRecord(Integer.parseInt(idString), dbPath);
                url = "/home.html";
            }
            
        } else if (action.equalsIgnoreCase("makeDB")) {
            DAOSQLite.dropTable(dbPath);
            DAOSQLite.createTable(dbPath);
            DAOSQLite.populateTable(dbPath);
            url = "/home.html";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controller for Employee App";
    }// </editor-fold>

}
