/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package honeypotdecoyserver;

import db.DBDriver;
import db.DBStorage;
import java.io.DataInputStream;

import java.io.InputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author INNOVATUS
 */
public class DecoyDataReceiver extends Thread
{
    public void run()
    {
        try
        {
            ServerSocket ss = new ServerSocket(1215);
            System.out.println("Decoy thread Data Receiver Started");
             DBStorage dbs=new DBStorage();
             DBDriver dbd=new DBDriver();
            Statement st=(Statement)dbd.getStatement();
            while (true)
            {
                Socket s = ss.accept();
                InputStream is = s.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                String content = dis.readUTF();
                  dbs.storeDataInServer(content,st);
               

   //      ss.close();
            }
           
        } 
       catch (Exception ex)
        {
            System.out.println("Server receiver exception: " + ex);
        }
    }
    
   
}
    

