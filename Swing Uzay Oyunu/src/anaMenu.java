
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class anaMenu extends JPanel implements KeyListener,ActionListener,MouseListener {
    private BufferedImage imgArkaPlan;
    private BufferedImage imgOptionArkaPlan;
    Timer timer=new Timer(5, this);
    Timer timer2=new Timer(223000, this);
    private boolean goGame=false;
    private boolean pressed1=false;
    private boolean pressed2=false;
    private boolean pressed3=false;
    private boolean pressed4=false;
    private boolean pressed5=false;
    private boolean pressed6=false;
    private boolean pressed7=false;
    private boolean pressed8=false;
    private boolean pressed9=false;
    private boolean pressed10=false;
    private boolean goOptions=false;
    private boolean goTus=false;
    private AudioInputStream stream;
    private Clip klip;
    private int time=0;
    private boolean startTime=false;
    private boolean volumeD=false;
    private boolean volumeU=false;
    private FloatControl volume;
    private int volumeSet=0;
    private boolean isMute=false;
    private boolean hasSave=false;
    private OyunEkrani ekran=new OyunEkrani("Uzay Oyunu");
    public void moveGame(){
        if(goGame){
        ekran.setResizable(false);
        ekran.setFocusable(false);//JFrame e odaklanmayacak bu sayede Jpanele odaklanacak
        ekran.setSize(800,600);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            
        Oyun oyun=new Oyun(volumeSet,isMute,hasSave,ekran);
        oyun.requestFocus();//klavyedeki islemleri almak icin oyun clasina fokuslaniyor
        
        oyun.addKeyListener(oyun);//Klavyedeki islemleri almamizi sagliyor
        oyun.setFocusable(true);//artik odak oyun JPanelinde
        
        oyun.setFocusTraversalKeysEnabled(false);
        
        ekran.add(oyun);//JPaneli JFrame e eklemis olduk
        
        ekran.setVisible(true);
                setOpaque(false);
                setEnabled(false);
                setVisible(false);        
        goGame=false;
        }

    }
    
    public void exitGame(){
        ekran.closeMenu();
    }
    
    public anaMenu(int volumeSet,boolean isMute){
        this.volumeSet=volumeSet;
        this.isMute=isMute;
        this.ekran=ekran;
        try {
            imgArkaPlan=ImageIO.read(new FileImageInputStream(new File("gameImages/anaMenu.jpg")));
            imgOptionArkaPlan=ImageIO.read(new FileImageInputStream(new File("gameImages/ayarlar.jpg")));
            
        } catch (IOException ex) {
            Logger.getLogger(anaMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer.start();
        if(!isMute){
           backGroundSound(); 
        } 
        timer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                backGroundSound();
            }
        });
        timer2.start();
        
        File file=new File("save.bin");
        if(file.exists()){
            hasSave=true;
        }
        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Font font=new Font("Arial",Font.PLAIN,25);
        g.setFont(font);
        if(goOptions){
            g.drawImage(imgOptionArkaPlan, 0, 0, this);
            String volumeUp="Ses Yükselt";
            String volumeDown="Ses Alçalt";
            String getBack="Geri Dön";
            String mute="Sesi Kapat";
            String openVol="Sesi Aç";
        if(!pressed4){
          g.setColor(new Color(240,153,53));  
        }else{
            g.setColor(Color.WHITE);
        }       
        g.drawString(volumeDown, 200, 280);            
        
        if(!pressed5){
          g.setColor(new Color(240,153,53));  
        }else{
            g.setColor(Color.WHITE);
        }       
        g.drawString(volumeUp, 420, 280);   
        
        if(!pressed6){
          g.setColor(new Color(240,153,53));  
        }else{
            g.setColor(Color.WHITE);
        }       
        g.drawString(getBack, 315, 320);
        
        if(!pressed7){
          g.setColor(new Color(240,153,53));  
        }else{
            g.setColor(Color.WHITE);
        }
        if(!isMute){
          g.drawString(mute, 315, 240);   
        }else{
            g.drawString(openVol, 315, 240); 
        }
                
        
        }else if(goTus){
            g.drawImage(imgOptionArkaPlan, 0, 0, this);
            Font font2=new Font("Arial",Font.PLAIN,20);
            g.setFont(font2);
            g.setColor(new Color(240,153,53)); 
            String esc="<escape> Oyunu Duraklatır Ve Belirli Seçenekler Sunar.";
            String p="<P> Oyunu Duraklatır.";
            String d="<D> Duraklatılmış Oyunu Devam Ettirir.";
            String spc="<space> Gemi Ateş Topu Fırlatır.";
            String don="Ana Menüye Dön";
            g.drawString(esc, 50, 100);
            g.drawString(p, 50, 140);
            g.drawString(d, 50, 180);            
            g.drawString(spc, 50, 220);
            if(!pressed9){
            g.setColor(new Color(240,153,53));  
            }else{
            g.setColor(Color.WHITE);
            }            
            g.drawString(don, 50, 300);
            
        }else{
           g.drawImage(imgArkaPlan, 0, 0, this); 
           
        String newGame="Yeni Oyun";
        if(!pressed1){
          g.setColor(new Color(240,153,53));  
        }else{
            g.setColor(Color.WHITE);
        }       
        g.drawString(newGame, 325, 200);
        
        String opt="Ses Ayarları";
        if(!pressed2){
          g.setColor(new Color(240,153,53));  
        }else{
            g.setColor(Color.WHITE);
        }        
        g.drawString(opt, 325, 260); 
        
        String exit="Oyundan Çık";
        if(!pressed3){
          g.setColor(new Color(240,153,53));  
        }else{
            g.setColor(Color.WHITE);
        }        
        g.drawString(exit, 325, 380);
        String tusInfo="Oyun Tuşları";
        if(!pressed8){
          g.setColor(new Color(240,153,53));   
        }else{
            g.setColor(Color.WHITE);
        }
        g.drawString(tusInfo, 325, 320);
        if(hasSave){
        String devamOyun="Devam Et";
        if(!pressed10){
          g.setColor(new Color(240,153,53));   
        }else{
            g.setColor(Color.WHITE);
        }
        g.drawString(devamOyun, 325, 140);            
        }

        
        }        
        

    }

    public boolean isIsMute() {
        return isMute;
    }

    @Override
    public void repaint() {
        super.repaint();
    }
    
        public void backGroundSound(){
        try {
            stream=AudioSystem.getAudioInputStream(new File("soundEffects/menuSound.wav"));
            klip=AudioSystem.getClip();
            klip.open(stream);
            volume=(FloatControl) klip.getControl(FloatControl.Type.MASTER_GAIN);
            klip.start();  
                  
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();
        moveGame();
        if(startTime){
            time++;
            if(time==5){
                if(!volumeD && !volumeU){
                 goOptions=false;   
                } 
                startTime=false;
                time=0;
            }
        }
           
       addMouseListener(new MouseAdapter() {
    public void mousePressed(MouseEvent e) {
        //System.out.println("X: "+e.getX()+"\nY: "+e.getY());
        if(goOptions){
        if(e.getX()>=200 && e.getX()<=310 && e.getY()>=260 && e.getY()<=285){
            pressed4=true;
        }else if(e.getX()>=420 && e.getX()<=550 && e.getY()>=260 && e.getY()<=285){
            pressed5=true;
        }else if(e.getX()>=315 && e.getX()<=410 && e.getY()>=300 && e.getY()<=325){
            pressed6=true;
        }else if(e.getX()>=315 && e.getX()<=430 && e.getY()>=220 && e.getY()<=245){
            pressed7=true;
        }            
            
        }else if(goTus){
         if(e.getX()>=50 && e.getX()<=205 && e.getY()>=285 && e.getY()<=305){
            pressed9=true;
        }   
        }
        else{
            startTime=false;
        if(e.getX()>=325 && e.getX()<=440 && e.getY()>=180 && e.getY()<=200){
            pressed1=true;
        }else if(e.getX()>=325 && e.getX()<=455 && e.getY()>=240 && e.getY()<=265){
            pressed2=true;
        }else if(e.getX()>=325 && e.getX()<=470 && e.getY()>=360 && e.getY()<=385){
            pressed3=true;
        }else if(e.getX()>=325 && e.getX()<=460 && e.getY()>=300 && e.getY()<=330){
            pressed8=true;
        }else if(e.getX()>=325 && e.getX()<=430 && e.getY()>=120 && e.getY()<=140){
            pressed10=true;
        }           
        }

    }
            public void mouseClicked(MouseEvent e) {
        if(goOptions){ 
        if(e.getX()>=200 && e.getX()<=310 && e.getY()>=260 && e.getY()<=285){
            if(!startTime){
                volumeSet-=5;
            pressed4=false;
            volumeD=true;
            long clipTime=klip.getMicrosecondPosition();
            klip.stop();
            volume.setValue(volumeSet);              
            klip.setMicrosecondPosition(clipTime);
            klip.start();
            startTime=true;                
            }

        }else if(e.getX()>=420 && e.getX()<=550 && e.getY()>=260 && e.getY()<=285){
            if(!startTime){
                volumeSet+=5;
            pressed5=false;
            volumeU=true;
            long clipTime=klip.getMicrosecondPosition();
            klip.stop();
            volume.setValue(volumeSet);              
            klip.setMicrosecondPosition(clipTime);
            klip.start();
            startTime=true;                
            }
        }else if(e.getX()>=315 && e.getX()<=410 && e.getY()>=300 && e.getY()<=325){
            pressed6=false;
            volumeD=false;
            volumeU=false;
            startTime=true;
        }else if(e.getX()>=315 && e.getX()<=430 && e.getY()>=220 && e.getY()<=245){
            if(!startTime){
            pressed7=false;
            if(!isMute){
            klip.stop();
            klip.flush();
            klip.close(); 
            volumeD=true;
            isMute=true; 
            startTime=true;
            }else{
                backGroundSound();
                startTime=true;
                isMute=false;
            }
            }
        }                   
        }else if(goTus){
         if(e.getX()>=50 && e.getX()<=205 && e.getY()>=285 && e.getY()<=305){
            pressed9=false;
            goTus=false;
        }   
        }
        else{         
            startTime=false;
        if(e.getX()>=325 && e.getX()<=440 && e.getY()>=180 && e.getY()<=200){
            pressed1=false;
            goGame=true;
            ekran.closeMenu();
            hasSave=false;
            klip.stop();
            klip.flush();
            klip.close();
            timer2.stop();
        }else if(e.getX()>=325 && e.getX()<=455 && e.getY()>=240 && e.getY()<=265){
            pressed2=false;
            goOptions=true;
            startTime=true;
            volumeD=true;
        }else if(e.getX()>=325 && e.getX()<=470 && e.getY()>=360 && e.getY()<=385){
            pressed3=false;
            System.exit(0);
        }else if(e.getX()>=325 && e.getX()<=460 && e.getY()>=300 && e.getY()<=330){
            pressed8=false;
            goTus=true;
        }else if(e.getX()>=325 && e.getX()<=430 && e.getY()>=120 && e.getY()<=140){
            pressed10=false;
            goGame=true;
            ekran.closeMenu();
            klip.stop();
            klip.flush();
            klip.close();
            timer2.stop();            
        }            
        }        
    }
            
        });  
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }


    
}
