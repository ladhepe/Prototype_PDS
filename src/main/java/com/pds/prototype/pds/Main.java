package com.pds.prototype.pds;

import java.sql.*;     // Use classes in java.sql package
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// JDK 7 and above
@Path("/bdd")
public class Main {    // Save as "JdbcUpdateTest.java"

    // This method is called if XML is request
    public String sayPlainTextHello() {
        return "Hello Jersey";
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
    }

    // This method is called if HTML is request
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        String title = "";
        double price = 0;
        int qty = 0;
        try (
                // Step 1: Allocate a database "Connection" object
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/ebookshop", "root", ""); // MySQL

                // Step 2: Allocate a "Statement" object in the Connection
                Statement stmt = conn.createStatement();) {
                    // Step 3: Execute a SQL SELECT query, the query result
                    //  is returned in a "ResultSet" object.
                    String strSelect = "select title, price, qty from books";
                    System.out.println("The SQL query is: " + strSelect); // Echo For debugging
                    System.out.println();

                    ResultSet rset = stmt.executeQuery(strSelect);

                    // Step 4: Process the ResultSet by scrolling the cursor forward via next().
                    //  For each row, retrieve the contents of the cells with getXxx(columnName).
                    System.out.println("The records selected are:");
                    int rowCount = 0;
                    while (rset.next()) {   // Move the cursor to the next row
                        title = rset.getString("title");
                        price = rset.getDouble("price");
                        qty = rset.getInt("qty");
                        System.out.println(title + ", " + price + ", " + qty);
                        ++rowCount;
                    }
                    System.out.println("Total number of records = " + rowCount);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    
                }
                return title + ", " + price + ", " + qty;
    }
}
