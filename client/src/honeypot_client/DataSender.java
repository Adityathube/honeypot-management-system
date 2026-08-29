/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package honeypot_client;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DataSender 
{
public void farwardData(String data,String ip)
   {
       //System.out.println("Sender string is "+n);
       
       try
       {
        
           Socket s1=new Socket(ip,6666);
           OutputStream o=s1.getOutputStream();
           DataOutputStream dos=new DataOutputStream(o);
          // SenderScreen.jTextArea1.setText(n);
           dos.writeUTF(data);


         //  System.out.println("Forwarded Data From Sender : "+data);
           
          
           
       }
       catch(Exception e){System.out.println(e);}
   }    
}
