/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


/**
 *
 * @author INNOVATUS
 */
public class DBDriver
{
  
    public Statement st=null;
    public Connection conn=null;
    public Statement getStatement()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String dbUrl = System.getenv("HONEYPOT_SERVER_DB_URL");
            String dbUser = System.getenv("HONEYPOT_DB_USER");
            String dbPassword = System.getenv("HONEYPOT_DB_PASSWORD");
            if (dbUrl == null || dbUser == null || dbPassword == null) {
                throw new IllegalStateException("Database environment variables are not configured.");
            }
            conn=DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            st=conn.createStatement();
        }
        catch(Exception ex)
        {
            System.out.println("Exception at class DBDriver in method getStatement()"+ex);
        }
        return st;
        
    }
    
}
