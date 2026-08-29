package honeypot_client;

import db.DBDriver;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ClientDataOperation {
    
   //date, state, district, confirmed, recovered, deceased, other

    public boolean isDataInserted(String date,String state,String district,String confirmed,String recovered,String deceased,String other) 
    {
        boolean flag=false;
        try
        {
            DBDriver dbd=new DBDriver();
            Statement st=dbd.getStatement();

           // String query="insert into datainfo values('"+srno+"','"+customerno+"','"+name+"','"+address+"','"+bu+"','"+connectiontype+"','"+billmonth+"','"+consumptionUnit+"','"+status+"','"+billAmount+"','"+paidAmount+"','"+paymentAmount+"')";
            String query="insert into data values('"+date+"','"+district+"','"+confirmed+"','"+recovered+"','"+deceased+"','"+other+"'";
            System.out.println("Query is "+ query);

            int n = st.executeUpdate(query);

            if(n>0)
                flag=true;

            dbd.st.close();
            dbd.conn.close();
                     
        }
        catch(Exception ex)
        {
            flag=false;
            System.out.println("Exception at ClientDataOperation in isDataInserted() "+ex);
        }
        return flag;
    }


   public ArrayList<ArrayList<String>> getExcelData(String filepath)
   {
    ArrayList<ArrayList<String>> data = new ArrayList<>();
       System.out.println("Reached 111");
    try
    {
        File file = new File(filepath);
        Workbook wb = Workbook.getWorkbook(file);
        Sheet sht = wb.getSheet(0);

        int rows = sht.getRows();
        int cols = sht.getColumns();
        
        System.out.println("rows = "+rows);
        System.out.println("columns = "+cols);

        for (int i = 1; i < rows; i++) 
        {  
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) 
            {
                jxl.Cell cell = sht.getCell(j, i);
                row.add(cell.getContents());
            }
            data.add(row);
        }

        wb.close();
    } catch (Exception ex) 
    {
        System.out.println("Exception at class ClientDataOperation() in  method getExcelData(): " + ex);
    }

    return data;
}
    public ArrayList<String> getColumnName(String filepath)
    {
        ArrayList<String> columnname=new ArrayList<String>();
        
        
        try
        {
            File file=new File(filepath); 
            Workbook wobj=Workbook.getWorkbook(file);
            Sheet sht=wobj.getSheet(0);
            int rows=sht.getRows();
            int columns=sht.getColumns();
            System.out.println("Rows ="+rows+"Columns="+columns);
            
            for(int j=0;j<columns;j++)
            {
                Cell c1=sht.getCell(j,0);
                String content=c1.getContents();
                columnname.add(content);
                
            }
            wobj.close();
            
        }
        catch(Exception ex)
        {
            System.out.println("Exception at class EcxcelOperations in Method getColumnName()"+ex);
        }
        return columnname;
    }

   public boolean isDataStored(String filepath,ArrayList<String> columnnames,String tablename)
    {
        boolean flag=false;   // ✅ corrected
      //  String tablename1="data";
        try
        {
            int batchsize=2000;  
            int count=0;
            DBDriver dbd=new DBDriver();
            dbd.getStatement();
            Connection con=dbd.conn;

            String query1="INSERT INTO "+tablename+"(";
            String query2="";
            String query3=") VALUES(";

            for (int i = 0; i < columnnames.size(); i++) 
            {
                query2=query2+columnnames.get(i)+", ";
                query3=query3+"?, ";
            }

            query2=query2.substring(0,query2.length()-2);
            query3=query3.substring(0,query3.length()-2)+")";

            String finalquery=query1+query2+query3;

            System.out.println("Final Query ="+finalquery);

            PreparedStatement ps=con.prepareStatement(finalquery);

            File file=new File(filepath);
            Workbook wb=Workbook.getWorkbook(file);
            Sheet sht=wb.getSheet(0);

            int rows=sht.getRows();
            int cols=sht.getColumns();

            for (int i = 1; i < rows; i++) 
            {
                for (int j = 0; j < cols; j++) 
                {
                    Cell cl=sht.getCell(j,i);   // ✅ Correct Cell usage
                    String cell_contents=cl.getContents();
                    ps.setString(j+1, cell_contents);
                }
                ps.addBatch();
                count++;
                if(count%batchsize==0)
                {
                     ps.executeBatch();
                     flag = true;  
                     ps.clearBatch();
                }
                
            }
             ps.executeBatch();
             ps.close();
             wb.close();
             con.close();
        }
        catch(Exception ex)
        {
            System.out.println("Exception in class  ClientDataOperation() in method isDataStored() "+ex);
        }

        return flag;
    }
   
//    public boolean isDataStored(String filepath,ArrayList<String> columnnames,String tablename)
//    {
//        boolean flag=false;   // ✅ corrected
//      //  String tablename1="data";
//        try
//        {
//            DBDriver dbd=new DBDriver();
//            dbd.getStatement();
//            Connection con=dbd.conn;
//
//            String query1="INSERT INTO "+tablename+"(";
//            String query2="";
//            String query3=") VALUES(";
//
//            for (int i = 0; i < columnnames.size(); i++) 
//            {
//                query2=query2+columnnames.get(i)+", ";
//                query3=query3+"?, ";
//            }
//
//            query2=query2.substring(0,query2.length()-2);
//            query3=query3.substring(0,query3.length()-2)+")";
//
//            String finalquery=query1+query2+query3;
//
//            System.out.println("Final Query ="+finalquery);
//
//            PreparedStatement ps=con.prepareStatement(finalquery);
//
//            File file=new File(filepath);
//            Workbook wb=Workbook.getWorkbook(file);
//            Sheet sht=wb.getSheet(0);
//
//            int rows=sht.getRows();
//            int cols=sht.getColumns();
//
//            for (int i = 1; i < rows; i++) 
//            {
//                for (int j = 0; j < cols; j++) 
//                {
//                    Cell cl=sht.getCell(j,i);   // ✅ Correct Cell usage
//                    String cell_contents=cl.getContents();
//                    ps.setString(j+1, cell_contents);
//                }
//                ps.addBatch();
//            }
//
//            ps.executeBatch();
//            flag = true;  
//
//            ps.close();
//            wb.close();
//            con.close();
//        }
//        catch(Exception ex)
//        {
//            System.out.println("Exception in class  ClientDataOperation() in method isDataStored() "+ex);
//        }
//
//        return flag;
//    }
}
