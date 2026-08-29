
package db;

import java.sql.Statement;


public class DBStorage 
{
    public void storeDataInServer(String content,Statement st)
    {
        try
        {
            
          String date="";
        String state="";
        String district="";
        String confirmed="";
        String recovered="";
        String deceased="";
        String other=""; 
          String splitdata[]=content.split(",");
                                   
                 //date, state, district, confirmed, recovered, deceased, other   
                    date=splitdata[0];
                    state=splitdata[1];
                    district=splitdata[2];
                    confirmed=splitdata[3];
                    recovered=splitdata[4];
                    deceased=splitdata[5];
                    other=splitdata[6];
                    date=date.trim();
                    state=state.trim();
                    district=district.trim();
                    confirmed=confirmed.trim();
                    recovered=recovered.trim();
                    other=other.trim();
                 
                    
        String query = "insert into data_info values('"+date+"','"+state+"','"+district+"','"+confirmed+"','"+recovered+"','"+deceased+"','"+other+"')";

        

         if(st.executeUpdate(query)>0)
                System.out.println("Stored  : "+content);
            
            
        }
        catch(Exception ex)
        {
            
            System.out.println("Exception at DBStorage is "+ex);
        }
        
    }
}
