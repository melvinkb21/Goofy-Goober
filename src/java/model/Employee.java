package model;

import java.io.Serializable;

/**
 *
 * @author John Phillips
 */
public class Employee implements Serializable {

    private int empId;
    private String title;
    private String author;
    private String subject;
    private double isbn;

    public Employee() {
        empId = 0;
        title = "";
        author = "";
        subject = "";
        isbn = 0;
    }

    public Employee(int empId, String title, String author, String subject, double isbn) {
        this.empId = empId;
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.isbn = isbn;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getIsbn() {
        return isbn;
    }

    public void setIsbn(double isbn) {
        this.isbn = isbn;
    }    

    public String inHTMLRowFormat() {
        return "<tr><td>" + empId + "</td>"
                + "<td>" + title + "</td>"
                + "<td>" + author + "</td>"
                + "<td>" + subject + "</td>"
                + "<td>" + isbn + "</td></tr>\n";
    }

    @Override
    public String toString() {
        return "Employee{" + "empId=" + empId + ", title=" + title
                + ", author=" + author + ", subject=" + subject
                + ", isbn=" + isbn + '}';
    }
}
