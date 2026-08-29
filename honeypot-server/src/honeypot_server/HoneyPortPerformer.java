/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package honeypot_server;

//import db.DBDriver;

import db.DBDriver;
import db.DBStorage;
import java.sql.Statement;

//import java.sql.Statement;


public class HoneyPortPerformer extends Thread
{
    String dip="";
    
    DataSenderDecoy dsd=new DataSenderDecoy();
  public void setDecoyIP(String dip)
  {
      this.dip=dip;
  }
    
    public void run()
    {
//      
        try
        {
            int timer=100;
//       
            int current=0;
            int previous=0;
            int count=0;
            int decoystatus=0;
            DBStorage dbs=new DBStorage();
             DBDriver dbd=new DBDriver();
           Statement st=(Statement)dbd.getStatement();
           
          
          while(true)
          {
              Thread.sleep(timer);
              if(!DataQueue.que.isEmpty())
              { 
                  int size=DataQueue.que.size();
                  String ssize=Integer.toString(size);
                  current=size;
                  HoneypotServerOperationFrame.jTextField1.setText(ssize);
                   String content=(String) DataQueue.que.remove();
                    if(dbs.storeDataInServer(content,st))
                    {
                    String s=HoneypotServerOperationFrame.jTextField3.getText();
                    int x=Integer.parseInt(s);
                    x=x+1;
                    HoneypotServerOperationFrame.jTextField3.setText(Integer.toString(x));
                        
                    }
                   if(count==0)
                   {
                       current=size;
                       previous=size;
                       count=1;
                   }
                   
                   
                   if(size==1)
                   {
                       HoneypotServerOperationFrame.jLabel2.setText("NONE"); 
                   }
                   else if(current>previous)
                   {
                       
                       HoneypotServerOperationFrame.jLabel2.setText("DOS ATTACK");  
                   }
                   else if(current==previous)
                   {
                         HoneypotServerOperationFrame.jLabel2.setText("NONE"); 
                   }
                   else if(current<previous)
                   {
                         HoneypotServerOperationFrame.jLabel2.setText("NORMAL AND DEFENCE ACTIVATED"); 
                   }
                   if(Integer.parseInt(HoneypotServerOperationFrame.jTextField1.getText())%200==0)
                   {
                      decoystatus=1;
                      
                   }
                   if(decoystatus==1)
                   {
                       String decoy_content_1=(String) DataQueue.que.remove(); 
                       dsd.farwardDatatoDecoy(decoy_content_1, dip);
                      String s=HoneypotServerOperationFrame.jTextField4.getText();
                    int x=Integer.parseInt(s);
                    x=x+1;
                    HoneypotServerOperationFrame.jTextField4.setText(Integer.toString(x));
                       if(DataQueue.que.size()==1)
                       {
                          decoystatus=0; 
                       }
                   }
                   
                     
                 
              if(count==1)
                   {
                       previous=current;
                       
                   } 
            }
          }
              
        
        } 
        catch(Exception ex)
        {
            System.out.println("Exception at HoneyPortPerformer "+ex);
        }
    }
   

}
