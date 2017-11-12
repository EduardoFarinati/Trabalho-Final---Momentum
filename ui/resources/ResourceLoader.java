package ui.resources;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.imageio.*;
import java.util.Random;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.lang.Runnable;

import exceptions.InvalidAngleException;

/**
 * ResourceLoader e a classe responsavel por carregar todos recursos do jogo
 * 
 * @author Andre, Eduardo, Marcelo 
 * @version 0.0
 */
public class ResourceLoader
{   
    //ImageIcons usados no trabalho
    private ImageIcon startIcon;
    private ImageIcon highscoresIcon;
    private ImageIcon exitIcon;
    private ImageIcon backgroundIcon;
    private ImageIcon[] levelPanel;
    private ImageIcon[] sc_Minotaur;
    private ImageIcon[] shieldedSc_Minotaur;
    private ImageIcon vertical_Rectangle;
    private ImageIcon horizontal_Rectangle;
    private ImageIcon[] warp;//warp e animado, tem multiplos frames
    private ImageIcon[] bumper;
    private ImageIcon[] speeder;
    private ImageIcon horizontalMobileWall;
    private ImageIcon verticalMobileWall;
    
    //textura do fundo
    private ImageIcon[] backgroundTexture;
    
    //fontes usadas no trabalho
    private Font depletedUranium;
    private Font dirtyEgo;
    
    //music player
    private Clip clip;
    
    private Thread volumeThread;
    //controle do volume é feito pelo floatControl
    private FloatControl gainControl;
    
    public ResourceLoader()
    {
        //Inicializamos os icones do botoes (obs USAR ICONES DO TAMANHO CORRETO)
        startIcon = new ImageIcon("ui/background/Start.png");
        highscoresIcon = new ImageIcon("ui/background/Highscores.png");
        exitIcon = new ImageIcon("ui/background/Exit.png");
        backgroundIcon = new ImageIcon ("ui/background/backgroundIcon.png"); 
        vertical_Rectangle = new ImageIcon("ui/sprites/Obstaculos/VerticalRectangle.png");
        horizontal_Rectangle = new ImageIcon("ui/sprites/Obstaculos/HorizontalRectangle.png");
        horizontalMobileWall = new ImageIcon("ui/sprites/Obstaculos/HorizontalMobileWall.png");
        verticalMobileWall = new ImageIcon("ui/sprites/Obstaculos/VerticalMobileWall.png");
        
        //carregamos as imagens dos niveis
        levelPanel = new ImageIcon[3];
        levelPanel[0] =new ImageIcon ("ui/background/Level1Panel.png");
        levelPanel[1] = new ImageIcon ("ui/background/Level2Panel.png");
        levelPanel[2] = new ImageIcon ("ui/background/Level3Panel.png");
        
        
        //carregamos todas sprites de bumper
        bumper = new ImageIcon[4];
        
        for(int i = 0; i<bumper.length; i++)
        {
            bumper[i] = new ImageIcon("ui/sprites/Obstaculos/Bumper/" + (i*90) + ".png");
        }
        
        //carregamos todas sprites de speeder
        speeder = new ImageIcon[4];
        
        for(int i = 0; i<speeder.length; i++)
        {
            speeder[i] = new ImageIcon("ui/sprites/Obstaculos/Speeder/" + (i*90) + ".png");
        }
        
        //carregamos todos sprites de warp
        warp = new ImageIcon[9];
        
        for(int i = 0; i<warp.length; i++)
        {
            warp[i] = new ImageIcon("ui/sprites/Obstaculos/Warp/" + (i+1) + ".png");
        }
        
        //carregamos todos os sprites da nave sc_Minotaur
        sc_Minotaur = new ImageIcon[24];
        int angulo = 0;
        
        for(int i=0;i<sc_Minotaur.length;i++)
        {  
           sc_Minotaur[i] = new ImageIcon("ui/sprites/Nave/"+ angulo +".png");
           
           angulo=angulo+15;
        }

        //carregamos todos os sprites da nave protegida
        shieldedSc_Minotaur = new ImageIcon[24];
        angulo = 0;
        
        for(int i=0;i<shieldedSc_Minotaur.length;i++)
        {  
           shieldedSc_Minotaur[i] = new ImageIcon("ui/sprites/nave/Shielded/"+ angulo +".png");
          
           angulo=angulo+15;
        }
        
        //carregamos o fundo
        backgroundTexture = new ImageIcon[4];
        for(int i = 0; i < backgroundTexture.length; i++)
        {
            backgroundTexture[i] = new ImageIcon("ui/background/backgroundTexture" + (i+1) + ".png");
        }
        
        //tentamos carregar a fonte do trabalho
        try {
            //iniciamos o graphics enenvironment
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("ui/Resources/Font/Depleted Uranium.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("ui/Resources/Font/DIRTYEGO.ttf")));
            
            depletedUranium = Font.createFont(Font.TRUETYPE_FONT, new File("ui/Resources/Font/Depleted Uranium.ttf"));
            dirtyEgo = Font.createFont(Font.TRUETYPE_FONT, new File("ui/Resources/Font/DIRTYEGO.ttf"));
        } catch (IOException|FontFormatException e) {
            System.err.println("Erro no carregamento das fontes na pasta Resources!");//Handle exception
            depletedUranium = new Font("Sans Serif", Font.PLAIN, 38);
            dirtyEgo = new Font("Sans Serif", Font.PLAIN, 38);
            e.printStackTrace();
        }
    }
    /**
     * playSound é o metodo utilizado para tocar as musicas no trabalho
     */
    public void playMusic(String fileName, float volume) throws LineUnavailableException, UnsupportedAudioFileException, IOException
    {
        //iniciamos a file de som
        File url = new File(fileName);
        
        //recebemos o clip
        clip = AudioSystem.getClip();
        
        //inserimos no audio input stream
        AudioInputStream ais = AudioSystem.getAudioInputStream(url);
        
        //abrimos a stream
        clip.open(ais);
        
        //iniciamos o controle de volume
        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume); // altera o volume por "volume" decibeis
        
        //setamos o loop, continuo
        clip.loop(clip.LOOP_CONTINUOUSLY);
        
        //iniciamos o som
        clip.start();
    }
    /**
     * StopMusic é o metodo utilizado para parar as musicas no trabalho
     */
    public void stopMusic(String fileName)
    {
        //esse metodo da um fade out rapidamente para zero
        //por meio de uma thread
        if(clip.isRunning())
        {    
            //intanciamos a thread do volume
            volumeThread = new Thread(){
                @Override
                public void run()
                {
                    //nao desce linearmente, descida exponencial, pois decibeis eh logaritmo
                    for(float i = gainControl.getValue(); i >= -50; i-=Math.abs(i/1000))
                    {
                        gainControl.setValue(i); // altera o volume por "volume" decibeis
                        try
                        {
                            Thread.sleep(1);
                        }
                        catch(InterruptedException e)
                        {
                            System.err.println("Erro, Audio Thread interrompida!");
                        }
                    }
                    
                    //limpamos o clip
                    clip.stop();
                    clip.flush();
                }
            };
              
            //iniciamos a thread
            volumeThread.start();
        }
    }
    /**
     * changeMusic altera a musica tocando no momento
     */
    public void changeMusic(String fileName, float volume)
    {
        //paramos a musica por meio da thread
        this.stopMusic(fileName);
        
        //Thread responsavel por esperar o som parar
        Thread waitFade = new Thread(){
            @Override
            public void run()
            {
                //esperamos o clip  parar, ou seja a volumeThread acabar
                while(clip.isRunning())
                {
                    try
                    {
                        Thread.sleep(1);
                    }
                    catch(InterruptedException e)
                    {
                        System.err.println("Erro, Audio Thread interrompida!");
                    }
                }
                
                try
                {
                    //iniciamos a file de som
                    File url = new File(fileName);
        
                    //recebemos o clip
                    clip = AudioSystem.getClip();
                
                    //inserimos no audio input stream
                    AudioInputStream ais = AudioSystem.getAudioInputStream(url);
                    
                    //abrimos a stream
                    clip.open(ais);
                    
                    //iniciamos o controle de volume
                    gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(volume); // altera o volume por "volume" decibeis
        
                    //setamos o loop, continuo
                    clip.loop(clip.LOOP_CONTINUOUSLY);
        
                    //iniciamos o som
                    clip.start();
                }
                catch(LineUnavailableException | UnsupportedAudioFileException | IOException e)
                {
                    System.err.println("Nao foi possivel iniciar a audio file!");
                    e.printStackTrace();
                }
            }
        };
        
        //iniciamos a thread
        waitFade.start();
    }
    /**
     * playSound - usada para tocar os sound effects do jogo
     */
    public static void playSound(String fileName, float volume)
    {
        try
        {
            //iniciamos a file de som
            File url = new File(fileName);
        
            //criamos um novo clip
            Clip clip = AudioSystem.getClip();
        
            //inserimos no audio input stream
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
        
            //abrimos a stream
            clip.open(ais);
        
            //iniciamos o controle de volume
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume); // altera o volume por "volume" decibeis
        
            //iniciamos o som
            clip.start();
        }
        catch(LineUnavailableException | UnsupportedAudioFileException | IOException e)
        {
            System.err.println("Nao foi possivel iniciar a audio file!");
            e.printStackTrace();
        }
    }
    /**
     * terminateMusic - deve ser executado quando o programa eh fechado
     * para a musica muito rapidamente
     */
    public void terminateMusic()
    {
            Thread volumeThread = new Thread(){
                @Override
                public void run()
                {
                    //nao desce linearmente, descida exponencial, pois decibeis eh logaritmo
                    for(float i = gainControl.getValue(); i >= -50; i-=Math.abs(i/10))
                    {
                        gainControl.setValue(i); // altera o volume por "volume" decibeis
                        try
                        {
                            Thread.sleep(1);
                        }
                        catch(InterruptedException e)
                        {
                            System.err.println("Erro, Audio Thread interrompida!");
                        }  
                    }
                    
                    clip.stop();
                }
            };
            //inciamos a thread que parara o volume mto rapidamente
            volumeThread.start();
    }
    public Font getDepletedUranium()
    {
        return depletedUranium;
    }
    public Font getDirtyEgo()
    {
        return dirtyEgo;
    }
    public ImageIcon getStartIcon()
    {
        return startIcon;
    }
    public ImageIcon getHighscoresIcon()
    {
        return highscoresIcon;
    }
    public ImageIcon getExitIcon()
    {
        return exitIcon;
    }
    public ImageIcon getBackgroundIcon()
    {
        return backgroundIcon;
    }
    public ImageIcon getLvlPanel(int i)
    {
        return levelPanel[i];
    }
    public ImageIcon[] getSpritesSC_Minotaur()
    {   
        return sc_Minotaur;        
    }
    public ImageIcon[] getSpritesShieldedSC_Minotaur()
    {   
        return shieldedSc_Minotaur;        
    }
    public ImageIcon getHorizontalWall()
    {
        return horizontal_Rectangle;
    }
    public ImageIcon getVerticalWall()
    {
        return vertical_Rectangle;
    }
    public ImageIcon getVerticalMobileWall()
    {
        return verticalMobileWall;
    }
    public ImageIcon getHorizontalMobileWall()
    {
        return horizontalMobileWall;
    }
    public ImageIcon getBumper(int angle)
    {   //retorna a Sprite do angulo fornecido
        switch(angle)
        {
            //nao e necessario breaks pois usamos returns
            case 0:
                return bumper[0];
                
            case 90:
                return bumper[1];
               
            case 180:
                return bumper[2];
                
            case 270:
                return bumper[3];
                
            default:
                throw (new InvalidAngleException(angle));
        }
    }
    public ImageIcon getSpeeder(int angle)
    {   //retorna a Sprite do angulo fornecido
        switch(angle)
        {
            //nao e necessario breaks pois usamos returns
            case 0:
                return speeder[0];
                
            case 90:
                return speeder[1];
               
            case 180:
                return speeder[2];
                
            case 270:
                return speeder[3];
                
            default:
                throw (new InvalidAngleException(angle));
        }
    }
    public ImageIcon getWarp(int frame)
    { 
        //ajustamos para o array
        int arr_pos = frame-1;
        
        //retornamos aquele frame de warp
        return warp[arr_pos];
    }
    public ImageIcon getBackgroundTexture()
    {   
        //retornamos aleatoriamente um dos fundos
        //instanciamos o aleatorizador
        Random bg = new Random();
        
        //iniciamos a variavel
        int i = bg.nextInt(4);//valores dentro de [0-3]
        
        //retornamos a textura aleatorizada
        return backgroundTexture[i];
    }
    public ImageIcon reSize(Image img)
    {
        Image aux = img.getScaledInstance(30, 30,Image.SCALE_SMOOTH);
        ImageIcon resizedImg = new ImageIcon(aux);
        
        return resizedImg;
    }
}
