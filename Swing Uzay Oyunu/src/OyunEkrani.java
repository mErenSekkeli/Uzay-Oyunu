
import java.awt.HeadlessException;
import javax.swing.JFrame;

public class OyunEkrani extends JFrame{
private static OyunEkrani ekran=new OyunEkrani("Uzay Oyunu");
    public static void main(String[] args) {


        ekran.setResizable(false);
        ekran.setFocusable(false);//JFrame e odaklanmayacak bu sayede Jpanele odaklanacak
        ekran.setSize(800,600);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        
        anaMenu menu=new anaMenu(0,false);
        menu.requestFocus();
        menu.setFocusable(true);
        menu.setFocusTraversalKeysEnabled(false);
        ekran.add(menu);
        ekran.setLocationRelativeTo(null);
        ekran.setVisible(true);
        
       /* Oyun oyun=new Oyun();
        oyun.requestFocus();//klavyedeki islemleri almak icin oyun clasina fokuslaniyor
        
        oyun.addKeyListener(oyun);//Klavyedeki islemleri almamizi sagliyor
        oyun.setFocusable(true);//artik odak oyun JPanelinde
        
        oyun.setFocusTraversalKeysEnabled(false);
        
        ekran.add(oyun);//JPaneli JFrame e eklemis olduk
        
        ekran.setVisible(true);  */
        

        //bu islemlerin yerleri onemlidir
      
        
    }
    public static void closeMenu(){
        ekran.setVisible(false);
    }

    public OyunEkrani(String title) throws HeadlessException {
        super(title);
    }
    
    
}
