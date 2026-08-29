/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package honeypot_server;

import java.io.DataInputStream;

import java.io.InputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author INNOVATUS
 */
public class DataReceiver extends Thread
{
    public void run()
    {
        try
        {
            ServerSocket ss = new ServerSocket(6666);
            System.out.println("File Name  Receiver Thread started");
            int count=0;
            while (true)
            {
                Socket s = ss.accept();
                InputStream is = s.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                String content = dis.readUTF();
                DataQueue.que.add(content);
                count+=1;
                HoneypotServerOperationFrame.jTextField2.setText(Integer.toString(count));
               // System.out.println("Received String Is : " + content);
              

   //      ss.close();
            }
           
        } 
       catch (Exception ex)
        {
            System.out.println("Server receiver exception: " + ex);
        }
    }
    
   
}
    

