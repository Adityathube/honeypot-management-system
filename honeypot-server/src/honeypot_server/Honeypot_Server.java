
package honeypot_server;

import java.awt.Dimension;
import java.awt.Toolkit;


public class Honeypot_Server {

    public static void main(String[] args)
    {
     HoneypotServerOperationFrame hof=new HoneypotServerOperationFrame();
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        hof.setSize(d);
        hof.setVisible(true);    
    }
    
}
