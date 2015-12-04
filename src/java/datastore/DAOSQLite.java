package datastore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Employee;

/**
 * DAOSQLite Data Access Object for an SQLite database
 *
 * @author John Phillips
 * @version 0.3 on 2015-11-03
 */
public class DAOSQLite {

    protected final static String DRIVER = "org.sqlite.JDBC";
    protected final static String JDBC = "jdbc:sqlite";

    /**
     * Inserts an record into the database table. Note the use of a
     * parameterized query to prevent SQL Injection attacks.
     *
     * @param employee the object to insert
     * @param dbPath the path to the SQLite database
     */
    public static void createRecord(Employee employee, String dbPath) {
        String q = "insert into employee (empId, title, author, subject, "
                + "isbn) values (null, ?, ?, ?, ?)";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, employee.getTitle());
            ps.setString(2, employee.getAuthor());
            ps.setString(3, employee.getSubject());
            ps.setDouble(4, employee.getIsbn());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieve a record given an empId.
     *
     * @param empId the empId of the record to retrieve
     * @param dbPath the path to the SQLite database
     * @return Employee object
     */
    public static Employee retrieveRecordById(int empId, String dbPath) {
        String q = "select empId, title, author, subject, isbn from employee where empId = ?";
        Employee employee = null;
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setInt(1, empId);
            employee = myQuery(conn, ps).get(0);
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return employee;
    }

    /**
     * Retrieve all of the records in the database as a list sorted by lastName,
     * firstName.
     *
     * @param dbPath the path to the SQLite database
     * @return list of objects
     */
    public static List<Employee> retrieveAllRecordsByName(String dbPath) {
        String q = "select * from employee order by title, author";
        List<Employee> list = null;
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            list = myQuery(conn, ps);
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Update a record from the database given an employee object. Note the use
     * of a parameterized query to prevent SQL Injection attacks.
     *
     * @param employee the employee record to update
     * @param dbPath the path to the SQLite database
     */
    public static void updateRecord(Employee employee, String dbPath) {
        String q = "update employee set title=?, author=?, subject=?, isbn=? where empId = ?";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, employee.getTitle());
            ps.setString(2, employee.getAuthor());
            ps.setString(3, employee.getSubject());
            ps.setDouble(4, employee.getIsbn());
            ps.setInt(5, employee.getEmpId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete a record from the database given its id. Note the use of a
     * parameterized query to prevent SQL Injection attacks.
     *
     * @param id the id of the record to delete
     * @param dbPath the path to the SQLite database
     */
    public static void deleteRecord(int id, String dbPath) {
        String q = "delete from employee where empId = ?";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a new employee table.
     *
     * @param dbPath the path to the SQLite database
     */
    public static void createTable(String dbPath) {
        String q = "create table employee ("
                + "empId integer not null primary key autoincrement, "
                + "title varchar(100) not null, "
                + "author varchar(100) not null, "
                + "subject varchar(100) not null, "
                + "isbn double not null)";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Drops the employee table erasing all of the data.
     *
     * @param dbPath the path to the SQLite database
     */
    public static void dropTable(String dbPath) {
        final String q = "drop table if exists employee";
        try (Connection conn = getConnectionDAO(dbPath);
                PreparedStatement ps = conn.prepareStatement(q)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Populates the table with sample data records.
     *
     * @param dbPath the path to the SQLite database
     */
    public static void populateTable(String dbPath) {
        Employee p;
        p = new Employee(0, "Linux Programming Bible", "Goerzen", "C,C++,Perl,CGI", 0-76-00);
        DAOSQLite.createRecord(p, dbPath);
        p = new Employee(0, "Internetworking with TCP/IP", "Comer", "TCP/IP", 0-13-6);
        DAOSQLite.createRecord(p, dbPath);
        p = new Employee(0, "HTML/CSS Contents", "Murach", "HTML/CSS", 8-38-3-3);
        DAOSQLite.createRecord(p, dbPath);
        p = new Employee(0, "XML in easy steps", "Plain English", "XML", 3-43-975);
        DAOSQLite.createRecord(p, dbPath);
        p = new Employee(0, "XML in easy steps", "Plain English", "XML", 3-34-43-975);
        DAOSQLite.createRecord(p, dbPath);
        
    }

    /**
     * A helper method that executes a prepared statement and returns the result
     * set as a list of objects.
     *
     * @param conn a connection to the database
     * @param ps a prepared statement
     * @return list of objects from the result set
     */
    protected static List<Employee> myQuery(Connection conn, PreparedStatement ps) {
        List<Employee> list = new ArrayList();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int empId = rs.getInt("empId");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String subject = rs.getString("subject");
                double isbn = rs.getDouble("isbn");
                Employee p = new Employee(empId, title, author, subject, isbn);
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSQLite.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Creates a connection to the SQLite database.
     *
     * @param dbPath the path to the SQLite database
     * @return connection to the database
     */
    protected static Connection getConnectionDAO(String dbPath) {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(JDBC + ":" + dbPath);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOSQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
