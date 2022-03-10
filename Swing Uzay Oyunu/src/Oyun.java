
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ates{
    private int x;
    private int y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}

public class Oyun extends JPanel implements KeyListener,ActionListener{
    private int difficulDegree=1000;
    Timer timer=new Timer(5, this);
    Timer timer2=new Timer(difficulDegree, this);
    Timer timer3=new Timer(difficulDegree+150, this);
    private int time=0;
    private int harcanan_ates=0;
    private BufferedImage imgGemi;
    private BufferedImage imgAtes;
    private BufferedImage imgBosluk;
    private BufferedImage imgDusmanGemi1;
    private BufferedImage imgDusmanGemi2;
    private BufferedImage imgDusmanGemi3;
    private BufferedImage imgDusmanGemi4;
    private BufferedImage imgPatlama;
    private BufferedImage imgAtesDusman;
    private BufferedImage imgAtesDusman2;
    private BufferedImage imgKalp;
    private Random rand=new Random();
    private boolean stopCheck=false;
    private boolean openGame=false;
    private boolean isAgain=false;
    private boolean dusmanGemi2Activated=false;
    private int diffCheck;
    
    private ArrayList<Ates> atesler=new ArrayList<Ates>();
    private ArrayList<Ates> atesDusman=new ArrayList<Ates>();
    private ArrayList<Ates> atesDusman2=new ArrayList<Ates>();
    
    private int atesdirY=2;//ates edilen topun kayma miktari
    
    private int atesDusmandirY=2;
    
    private int topX=1;//topun bulundugu nokta
    
    private int topY=0;
    
    private int top2X=741;
    
    private int top2Y=0;
    
    private int topdir2X=2;
    
    private int topdirX=2;//topun kayma miktari
    
    private int uzayGemisiX=0;//uzay gemisinin bulundugu nokta
    
    private int dirUzayX=20;//uzay gemisinin kayma miktari
    
    private int toplamTop=0;//Toplam top miktari
    
    private int shipHealth=3;//Geminin can miktari
    
    private boolean isShot=false;
    
    private boolean isShipShot=false;
    
    private int level=1;
    
    private int randomCan;//Can artisi level bilgisi
    
    private int randX=50;
    
    private int randY=50;
    
    private int randDir=2;
    
    private int topWidth=40;
    
    private int topHeight=20;
    
    private int topWidth2=80;
    
    private int topHeight2=50;
    
    private int dusmanGemiSayisi=1;
    
    private int timeExplosion=0;
    
    private int explosedShips=0;
    
    private int  timeShipExp=0;
    
    private int tmpX=0;
    
    private int tmpY=0;
    
    private int tmpBullet;
    
    private int volumeSet=0;
    
    private boolean isMute=false;
    
    private boolean isSaveGood=false;
    
    private boolean hasSave=false;
    
    private boolean ship1Dead=false;
    
    private boolean ship2Dead=false;
    
    private boolean exp1Done=false;
    
    private boolean exp2Done=false;
    
    private OyunEkrani ekran;
    
    public boolean kontrolEt(){
        Ates tmp=new Ates(-31, -31);
        for(Ates ates: atesler){
            if(dusmanGemiSayisi==1){
            if(new Rectangle(ates.getX(),ates.getY(),20,20).intersects(new Rectangle(topX,topY,topWidth,topHeight))){
                atesler.remove(ates);
                isShot=true;
                explosionEffects();
                timer2.stop();
                return true;
            }                
            }else if(dusmanGemiSayisi==2){
                if(explosedShips<2){
                if(new Rectangle(ates.getX(),ates.getY(),20,20).intersects(new Rectangle(topX,topY,topWidth,topHeight)) && !ship1Dead){
                    tmpX=topX; tmpY=topY;
                    ship1Dead=true;
                    explosionEffects();
                    isShot=true;
                    explosedShips++;
                    timer2.stop();
                    tmp=ates;
                }else if(new Rectangle(ates.getX(),ates.getY(),20,20).intersects(new Rectangle(top2X,top2Y,topWidth2,topHeight2)) && !ship2Dead){
                    tmpX=top2X; tmpY=top2Y;
                    ship2Dead=true;
                    isShot=true;
                    explosionEffects();
                    explosedShips++;
                    timer3.stop();
                    tmp=ates;
                }
                }else if(explosedShips==2){
                  return true;  
                }

            }
        }
        if(tmp.getX()!=-31 && tmp.getY()!=-31){//:D
            atesler.remove(tmp);
        }else{
            
        }
        return false;
    }
    
    public boolean kontrolEt2(){
        for(Ates ates : atesDusman){
            
            if(new Rectangle(ates.getX(),ates.getY(),30,30).intersects(new Rectangle(uzayGemisiX,470,imgGemi.getWidth()/6,
            imgGemi.getHeight()/6))){
                atesDusman.remove(ates);
                isShipShot=true;
                explosionEffects();
                return true;
            }
        }
        
        for(Ates ates: atesDusman2){
            if(new Rectangle(ates.getX(),ates.getY(),30,30).intersects(new Rectangle(uzayGemisiX,470,imgGemi.getWidth()/6,
            imgGemi.getHeight()/6))){
                atesDusman2.remove(ates);
                isShipShot=true;
                explosionEffects();
                return true;
            }            
        }
        return false;
    }
    public boolean kontrolEt3(){
        
        for(Ates ates : atesler){
            if(new Rectangle(ates.getX(),ates.getY(),20,20).intersects(new Rectangle(randX,randY,imgKalp.getWidth()/13,
            imgKalp.getHeight()/13))&&randomCan!=-1){
                randomCan=-1;
                shipHealth++;
                hearthEffect();
                atesler.remove(ates);
                return true;
            }
        }
        return false;
    }
    public Oyun(int volumeSet,boolean isMute,boolean hasSave,OyunEkrani ekran) {
        this.volumeSet=volumeSet;
        this.isMute=isMute;
        this.hasSave=hasSave;
        this.ekran=ekran;
        if(hasSave){
            getSavedGame();
        }else{
         zorlukSecimi();   
        }   
        try {
            imgGemi=ImageIO.read(new FileImageInputStream(new File("gameImages/uzayGemisi.png")));
            imgAtes=ImageIO.read(new FileImageInputStream(new File("gameImages/atesTopu.png")));
            imgBosluk=ImageIO.read(new FileImageInputStream(new File("gameImages/uzayBoslugu.jpg")));
            imgDusmanGemi1=ImageIO.read(new FileImageInputStream(new File("gameImages/dusmanGemisiLVL1.png")));
            imgDusmanGemi2=ImageIO.read(new FileImageInputStream(new File("gameImages/dusmanGemisiLVL2.png")));
            imgDusmanGemi3=ImageIO.read(new FileImageInputStream(new File("gameImages/dusmanGemisiLVL3.png")));
            imgDusmanGemi4=ImageIO.read(new FileImageInputStream(new File("gameImages/dusmanGemisiLVL4.png")));
            imgPatlama=ImageIO.read(new FileImageInputStream(new File("gameImages/explosion.png")));
            imgAtesDusman=ImageIO.read(new FileImageInputStream(new File("gameImages/dusmanAtesi.png")));
            imgKalp=ImageIO.read(new FileImageInputStream(new File("gameImages/kalp.png")));
            imgAtesDusman2=ImageIO.read(new FileImageInputStream(new File("gameImages/dusmanAtesi2.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        timer.start();
        timer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                atesDusman.add(new Ates(topX+9, 30+topY));
                dusmanFireEffects();
            }
        });
        timer2.start();
        
        timer3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                atesDusman2.add(new Ates(top2X+9, top2Y+30));
                dusmanFireEffects();
            }
        });
    }
    
    public void getSavedGame(){
            try(Scanner read=new Scanner(new FileReader("save.bin"))){
                String[] tmp=read.nextLine().split(",");
                for(String s : tmp){
                    System.out.println(s);
                }
                level=Integer.valueOf(tmp[0]);
                toplamTop=Integer.valueOf(tmp[1]);
                shipHealth=Integer.valueOf(tmp[2]);
                dusmanGemiSayisi=Integer.valueOf(tmp[3]);
                diffCheck=Integer.valueOf(tmp[4]);
                isAgain=Boolean.valueOf(tmp[5]);
                topX=Integer.valueOf(tmp[6]);
                topY=Integer.valueOf(tmp[7]);
                top2X=Integer.valueOf(tmp[8]);
                top2Y=Integer.valueOf(tmp[9]);
                difficulDegree=Integer.valueOf(tmp[10]);
                topdirX=Integer.valueOf(tmp[11]);
                topdir2X=Integer.valueOf(tmp[12]);
                ship1Dead=Boolean.valueOf(tmp[13]);
                ship2Dead=Boolean.valueOf(tmp[14]);
                harcanan_ates=Integer.valueOf(tmp[15]);
                time=Integer.valueOf(tmp[16]);
                tmpBullet=Integer.valueOf(tmp[17]);
                randomCan=Integer.valueOf(tmp[18]);
                explosedShips=Integer.valueOf(tmp[19]);
                exp1Done=Boolean.valueOf(tmp[20]);
                exp2Done=Boolean.valueOf(tmp[21]);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
            }        
    }
    
    public void zorlukSecimi(){
        if(!isAgain){
            randomCan=rand.nextInt(14)+1;
            randX=rand.nextInt(600)+100;
            randY=rand.nextInt(350)+50;
        String message="Dikkat! Oyun İçerisinde Kayıt İşlemini Sadece Siz Yapabilirsiniz.\n"
                + "Kolay Mod: 20 Topunuz Bulunmaktadır Ve Düşman Gemisi Hızlı Değildir. Toplam 3 Canınız Vardır.\n"
                + "Orta Mod: 10 Topunuz Bulunmaktadir Ve Düşman Gemisi Biraz Hızlıdır. Toplam 2 Canınız Vardır.\n"
                + "Zor Mod: 5 Topunuz Bulunmaktadir Ve Düşman Gemisi Çok Hızlıdır. Sadece 1 Canınız Vardır.\n\n"
                + "İpucu: Oyun Zorlaştıkça Düşman Gemisinin Size Daha Fazla Saldıracağını Unutmayın.";
        String[] opt={"Kolay","Orta","Zor"};
        diffCheck=JOptionPane.showOptionDialog(this, message, "Zorluk Secimi", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE
                , null, opt, opt[1]);            
        }
        timeShipExp=0; 
        exp1Done=false;
        exp2Done=false;
        switch(diffCheck){
            case 0:
                shipHealth=3;
                topdirX=2;
                topdir2X=2;
                atesDusmandirY=2;
                toplamTop=20;
                difficulDegree=1000;
                timer2.setDelay(difficulDegree);
                break;
            case 1:
                shipHealth=2;
                topdirX=3;
                topdir2X=3;
                atesDusmandirY=2;
                toplamTop=10;
                difficulDegree=800;
                timer2.setDelay(difficulDegree);
                break;
            case 2:
                shipHealth=1;
                topdirX=4;
                topdir2X=4;
                atesDusmandirY=2;
                toplamTop=5;
                difficulDegree=700;
                timer2.setDelay(difficulDegree);
                break;
        }
       tmpBullet=toplamTop;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        time+=5;
        g.drawImage(imgBosluk, 0, 0, this);
        //Top Yapimi
        
        g.drawImage(imgAtes, 1, 500, imgAtes.getWidth()/15,imgAtes.getHeight()/15,this);
       String ballInfo=" "+toplamTop;
        g.setColor(new Color(208,208,54));
        Font font=new Font("Arial",Font.PLAIN,20);
        g.setFont(font);
        g.drawString(ballInfo, 45, 527); 
        
        g.drawImage(imgKalp, 670, 500,imgKalp.getWidth()/15,imgKalp.getHeight()/15,this);
        String healthInfo=" "+shipHealth;
        g.setColor(new Color(255,82,123));
        g.drawString(healthInfo, 715, 527);
        
        if(!ship1Dead){
        if(level<=4){
            topWidth=40; topHeight=20;
            g.drawImage(imgDusmanGemi1, topX, topY, topWidth,topWidth,this);
        }else if(level<=8){
            topWidth=50; topHeight=25;
            g.drawImage(imgDusmanGemi2, topX, topY, topWidth,topWidth,this);
        }else if(level<=12){
            topWidth=60; topHeight=32;
            g.drawImage(imgDusmanGemi3, topX, topY, topWidth,topWidth,this);
        }else if(level<=16){
            topWidth=80; topHeight=50;
            g.drawImage(imgDusmanGemi4, topX, topY, topWidth,topWidth,this);
        }else if(level<=20){
            topWidth=40; topHeight=20;
            g.drawImage(imgDusmanGemi1, topX, topY, topWidth,topWidth,this);
        }else if(level<=24){
            topWidth=50; topHeight=25;
            g.drawImage(imgDusmanGemi2, topX, topY, topWidth,topWidth,this);            
        }else if(level<=28){
            topWidth=60; topHeight=32;
            g.drawImage(imgDusmanGemi3, topX, topY, topWidth,topWidth,this);
        }else if(level<=32){
            topWidth=80; topHeight=50;
            g.drawImage(imgDusmanGemi4, topX, topY, topWidth,topWidth,this);
        }            
        }

        if(dusmanGemiSayisi==2 && !ship2Dead){
        if(level<=20){
            topWidth2=80; topHeight2=50;
            g.drawImage(imgDusmanGemi4, top2X, top2Y, topWidth2,topWidth2,this);
        }else if(level<=24){
            topWidth2=60; topHeight2=32;
            g.drawImage(imgDusmanGemi3, top2X, top2Y, topWidth2,topWidth2,this);
        }else if(level<=28){
            topWidth2=50; topHeight2=25;
            g.drawImage(imgDusmanGemi2, top2X, top2Y, topWidth2,topWidth2,this);
        }else if(level<=32){
            topWidth2=40; topHeight2=20;
            g.drawImage(imgDusmanGemi1, top2X, top2Y, topWidth2,topWidth2,this);
        }            
        }
        if(isSaveGood){
            String Message="Oyun Kaydedildi!";
            g.setColor(new Color(208,208,54));
            g.drawString(Message, 320, 300);
            g.drawString("Devam Etmek İçin 'D' Tuşuna Basın", 250, 330);
            stopCheck=true;
            timer.stop();
            timer2.stop();
        }         
        
        //g.fillOval(topX, 0, 20, 20);
        g.drawImage(imgGemi, uzayGemisiX, 470, imgGemi.getWidth()/6,imgGemi.getHeight()/6,this);
        
        if(level==randomCan){
            g.drawImage(imgKalp, randX, randY, imgKalp.getWidth()/13,imgKalp.getHeight()/13,this);
        }
      
        for(Ates ates : atesler){
            if(ates.getY()<0){
                atesler.remove(ates);
            }
        }
        for(Ates ates : atesDusman){
            if(ates.getY()>530){
                atesDusman.remove(ates);
            }
        }
        if(dusmanGemiSayisi==2 && !atesDusman2.isEmpty()){
        for(Ates ates : atesDusman2){
            if(ates.getY()>530){
                atesDusman.remove(ates);
            }
            g.drawImage(imgAtesDusman2,ates.getX(), ates.getY(), 30, 30,this);
        }            
        }
       
        for(Ates ates: atesler){
            g.drawImage(imgAtes, ates.getX(), ates.getY(),20,20,this);
        }
        if(!ship1Dead || !atesDusman.isEmpty()){
        for(Ates ates: atesDusman){
            g.drawImage(imgAtesDusman, ates.getX(), ates.getY(),30,30,this);
            }             
        }
        
        if(isShot){
            if(dusmanGemiSayisi==1){
                g.drawImage(imgPatlama, topX,topY, 50,50,this);
            }else if(dusmanGemiSayisi==2){
                
            if(ship1Dead && !exp1Done){
            g.drawImage(imgPatlama, tmpX, tmpY, 50,50,this);
            timeShipExp++;
            if(timeShipExp>=20){
                timeShipExp=0;
                isShot=false;
                exp1Done=true;
            }
        }
            if(ship2Dead && !exp2Done){
            g.drawImage(imgPatlama, tmpX, tmpY, 50,50,this);
            timeShipExp++;
            if(timeShipExp>=20){
                timeShipExp=0;
                isShot=false;
                exp2Done=true;
            }
        }
            }
            
        }
        if(isShipShot){
            g.drawImage(imgPatlama, uzayGemisiX+11, 470, 50,50,this);
            timeExplosion++;
            if(timeExplosion>=50){
                isShipShot=false;
                timeExplosion=0;
            }
        }
        
    }

    @Override
    public void repaint() {//repaint painti tekrar cagiriyor
        super.repaint();
        
    }
  
    @Override
    public void keyTyped(KeyEvent e) {
    }
   //Ses Efektlerinin Fonksiyonlari
    public void fireTopSoundEffect(){
        if(!isMute){
        try {
            AudioInputStream stream=AudioSystem.getAudioInputStream(new File("soundEffects/fireTop.wav"));
            
            Clip klip=AudioSystem.getClip();
            klip.open(stream);
            FloatControl volume=(FloatControl) klip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volumeSet);
            klip.start();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    public void dusmanFireEffects(){
        if(!isMute){
           
        try {
            AudioInputStream stream=AudioSystem.getAudioInputStream(new File("soundEffects/dusmanFire.wav"));
            
            Clip klip=AudioSystem.getClip();            
            klip.open(stream);
            /*
            FloatControl volume=(FloatControl) klip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-3); Ses Alçaltıp Artırma */
            FloatControl volume=(FloatControl) klip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volumeSet);            
            klip.start();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }

    public void explosionEffects(){
        if(!isMute){
        try {
            AudioInputStream stream=AudioSystem.getAudioInputStream(new File("soundEffects/explosionEffect.wav"));  
            Clip klip=AudioSystem.getClip();
            klip.open(stream);
            FloatControl volume=(FloatControl) klip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volumeSet);  
            klip.start();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }  

    public void losingGameEffects(){
        if(!isMute){
        try {
            AudioInputStream stream=AudioSystem.getAudioInputStream(new File("soundEffects/losingEffect.wav"));  
            Clip klip=AudioSystem.getClip();
            klip.open(stream);
            FloatControl volume=(FloatControl) klip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volumeSet);  
            klip.start();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    
    public void winningGameEffects(){
        if(!isMute){
        try {
            AudioInputStream stream=AudioSystem.getAudioInputStream(new File("soundEffects/winningEffect.wav"));
            Clip klip=AudioSystem.getClip();
            klip.open(stream);
            FloatControl volume=(FloatControl) klip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volumeSet);  
            klip.start();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    
    public void hearthEffect(){
        if(!isMute){      
        try {
            AudioInputStream stream=AudioSystem.getAudioInputStream(new File("soundEffects/upHealthEffect.wav"));
            Clip klip=AudioSystem.getClip();
            klip.open(stream);
            FloatControl volume=(FloatControl) klip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volumeSet);  
            klip.start();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }     
    
    @Override
    public void keyPressed(KeyEvent e) {
               
        int c=e.getKeyCode();
        if(!stopCheck){
        if(c==KeyEvent.VK_LEFT){
            
            if(uzayGemisiX<=0){
                uzayGemisiX=0;
            }else{
                uzayGemisiX-=dirUzayX;
            }
            
        }else if(c==KeyEvent.VK_RIGHT){
            if(uzayGemisiX>=720){
                uzayGemisiX=720;
            }else{
                uzayGemisiX+=dirUzayX;
            }            
        }else if(c==KeyEvent.VK_SPACE){
            if(toplamTop>0){
            atesler.add(new Ates(uzayGemisiX+25, 470));
            harcanan_ates++;
            toplamTop--;
            fireTopSoundEffect();
            }else{
                if(shipHealth>1){
                        shipHealth--;
                        toplamTop=tmpBullet;
               }else if(atesler.size()==0){
                    losingGameEffects();
            timer.stop();
            timer2.stop();
            timer3.stop();
            String message="Topunuz Bitti! Dusman Gemiyi Vuramadiniz Ve Tum murettebat Yok Edildi. :/\n"
                    + "Gecen Sure: "+time/1000.0+" Saniye\n"
                            + "Gelinen Seviye: "+level;
            ArrayList<String> opt=new ArrayList<String>();
            File file=new File("save.bin");
            opt.add("Yeni Oyun");
             opt.add("Oyundan Çık");
            if(file.exists()){
              opt.add("Son Kaydı Yükle");  
            }
            
             Object[] opt2=opt.toArray();
          int check=JOptionPane.showOptionDialog(this, message,"Oyun Bitti!",
                  JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt2, opt2[0]);
          if(check==1){
            System.exit(0);
          }else if(check==0){
              createNewGame();
          }else if(check==2){
              getSavedGame();
              atesler.clear();
              atesDusman.clear();
              atesDusman2.clear();
              isShot=false;
              timer.start();
              timer2.start();
              if(dusmanGemiSayisi==2 && !ship2Dead){
                  timer3.start();
              }
          }                   
                }                
            }     
        }            
        } if(c==KeyEvent.VK_P){
            stopCheck=true;
            timer.stop();
            timer2.stop();
            
        } if(stopCheck && c==KeyEvent.VK_D){
            stopCheck=false;
            openGame=true;
            if(isSaveGood=true){
                isSaveGood=false;
            }
            timer.start();
            timer2.start();
        }
        //Buradan Devam Et
        if(c==KeyEvent.VK_ESCAPE){
            stopCheck=true;
            String message="Ne Yapmak İstiyorsunuz? ";
            ArrayList<String> opt=new ArrayList<String>();
            opt.add("Oyunu Kaydet");
            opt.add("Kaydet Ve Menüye Dön");
            opt.add("Oyundan Çık");
            File file=new File("save.bin");
            if(file.exists()){
               opt.add("Son Kaydı Aç"); 
            }
            Object[] opt2=opt.toArray();
            int check=JOptionPane.showOptionDialog(this, message, "Oyun Duraklatıldı", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt2, opt2[0]);
            if(check==0){
                saveGame();
            }else if(check==1){
                saveGame(); 
        anaMenu menu=new anaMenu(volumeSet,isMute);
        menu.setFocusable(true);
        menu.setFocusTraversalKeysEnabled(false);
        ekran.add(menu);
        ekran.setVisible(true);
                setOpaque(false);
                setEnabled(false);
                setVisible(false);
                
            }else if(check==2){
                System.exit(0);
            }else if(check==3){
              getSavedGame();
              atesler.clear();
              atesDusman.clear();
              atesDusman2.clear();
              isShot=false;
              stopCheck=false;
              openGame=true;
              timer.start();
              timer2.start();
              if(dusmanGemiSayisi==2 && !ship2Dead){
                  timer3.start();
              }
            }
        }
    }
    public void saveGame(){
                try(FileWriter out=new FileWriter("save.bin")){
                    
                    String tmp=Integer.toString(level)+",";
                    tmp+=Integer.toString(toplamTop)+",";
                    tmp+=Integer.toString(shipHealth)+",";
                    tmp+=Integer.toString(dusmanGemiSayisi)+",";
                    tmp+=Integer.toString(diffCheck)+",";
                    tmp+=Boolean.toString(isAgain)+",";
                    tmp+=Integer.toString(topX)+",";
                    tmp+=Integer.toString(topY)+",";
                    tmp+=Integer.toString(top2X)+",";
                    tmp+=Integer.toString(top2Y)+",";
                    tmp+=Integer.toString(difficulDegree)+",";
                    tmp+=Integer.toString(topdirX)+",";
                    tmp+=Integer.toString(topdir2X)+",";
                    tmp+=Boolean.toString(ship1Dead)+",";
                    tmp+=Boolean.toString(ship2Dead)+",";
                    tmp+=Integer.toString(harcanan_ates)+",";
                    tmp+=Integer.toString(time)+",";
                    tmp+=Integer.toString(tmpBullet)+",";
                    tmp+=Integer.toString(randomCan)+",";
                    tmp+=Integer.toString(explosedShips)+",";
                    tmp+=Boolean.toString(exp1Done)+",";
                    tmp+=Boolean.toString(exp2Done);
                    out.write(tmp+"\n");
                    timer.start();
                    timer2.start();
                    isSaveGood=true;
                } catch (IOException ex) {
                    Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public void createNewGame(){
        topY=0;
        top2Y=0;
        isAgain=false;
        level=1;
        zorlukSecimi();
        atesdirY=2;
        time=0;
        topX=1;//topun bulundugu nokta
        top2X=741;
        harcanan_ates=0;
        uzayGemisiX=0;//uzay gemisinin bulundugu nokta
        dirUzayX=20;//uzay gemisinin kayma miktari
        atesler.clear();
        atesDusman.clear();
        atesDusman2.clear();
        explosedShips=0;
        dusmanGemiSayisi=1;
        isShot=false;
        ship1Dead=false;
        ship2Dead=false;
        exp1Done=false;
        exp2Done=false;
         timer.start();
         timer2.start();
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(stopCheck){
            timer.stop();
            timer2.stop();
            if(dusmanGemiSayisi==2){
               timer3.stop(); 
            }            
        }else if(openGame){
            openGame=false;
            timer.start();
            timer2.start();
            if(dusmanGemiSayisi==2){
               timer3.start(); 
            }            
        }
        for(Ates ates : atesler){
            ates.setY(ates.getY()-atesdirY);
        }
        if(!ship1Dead || !atesDusman.isEmpty()){
        for(Ates ates : atesDusman){
            ates.setY(ates.getY()+atesDusmandirY);
            }
        topX+=topdirX;
        }

  
        if(dusmanGemiSayisi==2 && !ship2Dead){
            if(!dusmanGemi2Activated){
                timer3.start();
                dusmanGemi2Activated=true;
            }
            
            top2X+=topdir2X;
            if(top2X>=742){
                topdir2X=-topdir2X;
            }
            if(top2X<=1){
                topdir2X=-topdir2X;
            }
        }
        
        if(!ship2Dead || !atesDusman2.isEmpty()){
            for(Ates ates : atesDusman2){
                ates.setY(ates.getY()+atesDusmandirY);
            }            
        }
        if(level==randomCan){
            
        randX+=randDir;    
        if(randX>=700){
            randDir=-randDir;
        }
        if(randX<=50){
            randDir=-randDir;
        }            
        }
        
        if(topX>=742){
            topdirX=-topdirX;
        }
        if(topX<=1){//Change 1
            topdirX=-topdirX;
        }
        repaint();
        if(kontrolEt2()){
            if(shipHealth>1){
                shipHealth--;
            }else{
                losingGameEffects();
                timer.stop();
                timer2.stop();
                timer3.stop();
                String message="Dusman Gemisi Tarafindan Yok Edildiniz! Tum Murettebat Yok Edildi. :/\n"
                        + "Gecen Sure: "+time/1000.0+" Saniye\n"
                        + "Ates Edilen Toplam Top Sayisi: "+harcanan_ates+"\n"
                        + "Gelinen Seviye: "+level;
                
            ArrayList<String> opt=new ArrayList<String>();
            File file=new File("save.bin");
            opt.add("Yeni Oyun");
             opt.add("Oyundan Çık");
            if(file.exists()){
              opt.add("Son Kaydı Yükle");  
            }
             Object[] opt2=opt.toArray();
          int check=JOptionPane.showOptionDialog(this, message,"Oyun Bitti!",
                  JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt2, opt2[0]);
          if(check==1){
            System.exit(0);
          }else if(check==0){
              createNewGame();
          }else if(check==2){
              getSavedGame();
              atesler.clear();
              atesDusman.clear();
              atesDusman2.clear();
              isShot=false;
              timer.start();
              timer2.start();
              if(dusmanGemiSayisi==2 && !ship2Dead){
                  timer3.start();
              }
          }          
            }
        }
        if(kontrolEt()){
            timer.stop();
            String message="Tebrikler! Dusman Gemiyi Vurdunuz.\n"
                    + "Gecen Sure: "+time/1000.0+" Saniye\n"
                            + "Ates Edilen Toplam Top Sayisi: "+harcanan_ates+"\n"+
                    "Level: "+level;
            Object[] opt={"Bir Sonraki Seviye","Oyunu Kapat"};
          int check=JOptionPane.showOptionDialog(this, message,"Oyun Bitti!",
                  JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt[0]);
          if(check==1){
            System.exit(0);
          }else if(check==0){
              explosedShips=0;
        isAgain=true;
        level++;
        int tmpHealth=shipHealth;
        zorlukSecimi();
        if(diffCheck==0){
        if(level==32){
            winningGameEffects();
            String message2="Tebrikler! Tum Bolumleri Tamamladiniz! Artik Dusman Kalmadi.\n"
                    +"Gelinen Seviye: "+level;
            Object[] opt2={"Yeni Oyun","Oyunu Kapat"};
          int check2=JOptionPane.showOptionDialog(this, message2,"Tum Seviyeler Tamamlandi!",
                  JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt2, opt2[0]);
          
          if(check2==1){
              System.exit(0);
          }else if(check2==0){
              createNewGame();
          }
         
        }else if(level>=28){
            topdirX+=2;
            topdir2X+=2;
            difficulDegree-=300;
            top2Y=40;
            topY=60;
            shipHealth=tmpHealth;
            toplamTop-=8;          
        }else if(level>=24){
            topdirX++;
            topdir2X++;
            difficulDegree-=100;
            top2Y=80;
            topY=120;
            shipHealth=tmpHealth;
            toplamTop-=4;             
        }else if(level>=20){
            dusmanGemiSayisi=2;
            top2Y=0;
            topY=40;
            shipHealth=tmpHealth;
            toplamTop-=2;             
        }else if(level>=16){
            shipHealth=tmpHealth;
            atesDusmandirY+=4;
            toplamTop-=16;
            topY=100;
            difficulDegree-=300;
            topdirX+=3; 
        }else if(level>=14){
            shipHealth=tmpHealth;
            atesDusmandirY+=3;
            toplamTop-=14;
            topY=200;
            difficulDegree-=300;
            topdirX+=3;            
        }
        else if(level>=12){
            shipHealth=tmpHealth;
            atesDusmandirY+=3;
            toplamTop-=14;
            topY=60;
            difficulDegree-=300;
            topdirX+=2;            
        }
        else if(level>=10){
            shipHealth=tmpHealth;
            atesDusmandirY+=2;
            toplamTop-=12;
            topY=120;
            difficulDegree-=300;
            topdirX+=2;            
        }
        else if (level>=8){
            shipHealth=tmpHealth;
            atesDusmandirY+=2;
            toplamTop-=10;
            topY=15;
            difficulDegree-=200;
            topdirX+=2;
        }else if(level>=6){
            shipHealth=tmpHealth;
            atesDusmandirY++;
            toplamTop-=8;
            topY=30;
            difficulDegree-=100;
            topdirX++;
        }else if(level>=4){
            shipHealth=tmpHealth;
            topdirX++;
            topY=65;
            atesDusmandirY++;
            toplamTop-=4;
        }else if(level>=2){
            topY=40;
            shipHealth=tmpHealth;
            toplamTop-=2;            
        }            
        }else if(diffCheck==1){
        if(level==32){
            winningGameEffects();
            String message2="Tebrikler! Tum Bolumleri Tamamladiniz! Artik Dusman Kalmadi.\n"
                    +"Gelinen Seviye: "+level;
            Object[] opt2={"Yeni Oyun","Oyunu Kapat"};
          int check2=JOptionPane.showOptionDialog(this, message2,"Tum Seviyeler Tamamlandi!",
                  JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt2, opt2[0]);
          
          if(check2==1){
              System.exit(0);
          }else if(check2==0){
              createNewGame();           
          }
          
        }else if(level>=28){
            topdirX+=2;
            topdir2X+=2;
            difficulDegree-=100;
            top2Y=10;
            topY=150;
            shipHealth=tmpHealth;            
        }
        else if(level>=24){
            topdirX++;
            topdir2X++;
            difficulDegree-=100;
            top2Y=80;
            topY=30;
            shipHealth=tmpHealth;
            toplamTop-=2;            
        }else if(level>=20){
            dusmanGemiSayisi=2;
            top2Y=0;
            topY=120;
            shipHealth=tmpHealth; 
        }
        else if(level>=16){
            topY=20;
            shipHealth=tmpHealth;
            atesDusmandirY+=3;
            toplamTop-=7;
            topdirX+=3;            
        }
        else if(level>=14){
            topY=90;
            shipHealth=tmpHealth;
            atesDusmandirY+=3;
            toplamTop-=6;
            topdirX+=3;
        }
        else if(level>=12){
            topY=20;
            shipHealth=tmpHealth;
            atesDusmandirY+=3;
            toplamTop-=5;
            topdirX+=2;            
        }
        else if(level>=10){
            topY=60;
            shipHealth=tmpHealth;
            atesDusmandirY+=2;
            toplamTop-=5;
            topdirX+=2;            
        }
        else if (level>=8){
            topY=120;
            shipHealth=tmpHealth;
            atesDusmandirY+=2;
            toplamTop-=4;
            topdirX++;
        }else if(level>=6){
            topY=30;
            shipHealth=tmpHealth;
            atesDusmandirY++;
            toplamTop-=3;
            topdirX++;
        }else if(level>=4){
            topY=80;
            shipHealth=tmpHealth;
            atesDusmandirY++;
            toplamTop-=2;
        }else if(level>=2){
            topY=50;
            shipHealth=tmpHealth;
            toplamTop-=1;            
        }            
        }else if(diffCheck==2){
        if(level==32){
            winningGameEffects();
            String message2="Tebrikler! Tum Bolumleri Tamamladiniz! Artik Dusman Kalmadi.\n"
                    +"Gelinen Seviye: "+level;
            Object[] opt2={"Yeni Oyun","Oyunu Kapat"};
          int check2=JOptionPane.showOptionDialog(this, message2,"Tum Seviyeler Tamamlandi!",
                  JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt2, opt2[0]);
          
          if(check2==1){
              System.exit(0);
          }else if(check2==0){
             createNewGame();          
          }
          
        }else if(level>=28){
            topY=0;
            top2X=100;
            shipHealth=tmpHealth;
            atesDusmandirY++;
            toplamTop--;
            topdirX+=2;
            topdir2X+=2;            
        }else if(level>=24){
            topY=75;
            top2X=20;
            shipHealth=tmpHealth;
            atesDusmandirY++;
            toplamTop--;
            topdirX++;
            topdir2X++;
        }else if(level>=20){
            dusmanGemiSayisi=2;
            top2Y=0;
            topY=120;
            shipHealth=tmpHealth;             
        }else if(level>=16){
            topY=100;
            shipHealth=tmpHealth;
            atesDusmandirY+=3;
            toplamTop-=2;
            topdirX+=2;            
        }else if(level>=14){
            topY=0;
            shipHealth=tmpHealth;
            atesDusmandirY+=3;
            toplamTop-=3;
            topdirX+=2;            
        }else if(level>=12){
            topY=200;
            shipHealth=tmpHealth;
            atesDusmandirY+=3;
            toplamTop-=2;
            topdirX+=2;            
        }else if(level>=10){
            topY=10;
            shipHealth=tmpHealth;
            atesDusmandirY+=3;
            toplamTop-=2;
            topdirX++;            
        }else if (level>=8){
            topY=140;
            shipHealth=tmpHealth;
            atesDusmandirY+=2;
            toplamTop-=2;
            topdirX++;
        }else if(level>=6){
            topY=75;
            shipHealth=tmpHealth;
            atesDusmandirY++;
            toplamTop--;
            topdirX++;
        }else if(level>=4){
            topY=45;
            shipHealth=tmpHealth;
            atesDusmandirY++;
            toplamTop--;
        }else if(level>=2){
            topY=100;
            shipHealth=tmpHealth;            
        }            
        }
        atesdirY=2;
        time=0;
        topX=1;//topun bulundugu nokta
        top2X=741;
        harcanan_ates=0;
        uzayGemisiX=0;//uzay gemisinin bulundugu nokta
        dirUzayX=20;//uzay gemisinin kayma miktari
        atesler.clear();
        atesDusman.clear();
        atesDusman2.clear();
        isShot=false;
        ship1Dead=false;
        exp1Done=false;
        tmpBullet=toplamTop;
         timer.start();
         timer2.start();
         if(dusmanGemiSayisi==2){
             timer3.start();
             ship2Dead=false;
             exp2Done=false;
         }  
          }
        }
        if(level==randomCan){
            kontrolEt3();
        }
        
    }
    
    
    
}
