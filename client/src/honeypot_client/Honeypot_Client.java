/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package honeypot_client;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Honeypot_Client 
{
    public static void main(String[] args)
    {
        HoneypotOperationFrame hpof=new HoneypotOperationFrame();
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        hpof.setVisible(true);
        hpof.setSize(d);
     
    }
    
}
