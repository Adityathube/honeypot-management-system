/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package honeypot_client;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author hp
 */
public class Transporter 
{

 public boolean transferData(ArrayList data,String ip)
 {
     boolean flag=false;
     int datacount=0;
     int status=0;
     try
     {
         int timercount=100;
         DataSender ds=new DataSender();
                for(int i=0;i<data.size();i++)
                {
                   // System.out.println("i= "+i);
                     Thread.sleep(timercount);
                     ArrayList<String> row = (ArrayList<String>) data.get(i);
                     String rowstring="";
                     
                     if((i+1)%225==0)
                     {
                         timercount=50;
                         status=1;
                         System.out.println("ATTACK STATUS IS "+status);
                     }
                     if(status==1)
                    {
                        datacount+=1;
                        if(datacount==150)
                        {
                             System.out.println("DATA COUNT "+datacount);
                            status=0;
                            datacount=0;
                            timercount=100;
                            
                            System.out.println("ATTACK STATUS IS "+status);
                           
                        }
                    }
                     
                     
//                     int min = 1;
//                    int max = 50;
//                    
//                    Random rand = new Random();
//                    int randomNumber = rand.nextInt(max - min + 1) + min;
//                    if(status==0)
//                    {
//                    if(randomNumber%17==0)
//                    {
//                         timercount=30;
//                         status=1;
//                         System.out.println("STATUS IS "+status);
//                    }
//                      
//                     }
//                    if(status==1)
//                    {
//                        datacount+=1;
//                        if(datacount==200)
//                        {
//                            status=0;
//                            datacount=0;
//                            System.out.println("STATUS IS "+status);
//                            System.out.println("DATA COUNT "+datacount);
//                        }
//                    }
                        
                        
                     for (int j = 0; j < row.size(); j++)
                     {
                        String cell=(String)row.get(j);
                        rowstring=rowstring+","+cell;
                        
                     }
                     rowstring=rowstring.substring(1,rowstring.length());
                     ds.farwardData(rowstring, ip);
                     
                      //System.out.println(rowstring);
                }
     flag=true;
     }
     catch(Exception ex)
     {
         System.out.println("Exceptipon at class Transporter in method transferData()"+ex);
     }
     
      
     return flag;
     
     
 }
}
