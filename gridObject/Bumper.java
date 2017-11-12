package gridObject;

import ui.LevelPanel;
import java.lang.Math;
import ui.resources.ResourceLoader;
import gridObject.movement.Vector;
import exceptions.BumperSideException;
import gridObject.movement.Sprite;

/**
 * Bumper serve como uma especie de mola para lancar a nave rapidamente
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class Bumper extends Obstaculo
{
    //forca do empurrao na nave
    private final int BUMP = 2;
    
    //o tipo representa a angulacao do bumper
    private int type;
    
    //variavel se o efeito do bumper esta tocando
    private boolean soundPlaying;
    
    /**
     * Construtor da classe Bumper
     * 
     * @param pos_x, pos_y parametros de posicao
     * @param tyoe orientacao do Bumper em graus
     */
    public Bumper(int x, int y, int type, ResourceLoader resourceLoader) throws BumperSideException
    {
        super(x, y, 52, 52);
        //utilizamos o construtor de parede para inicializar a posicao
        
        //setamos a respectiva sprite
        switch(type)
        {
            case 0:
                super.setSprite(new Sprite(resourceLoader.getBumper(type)));
                break;
            case 90:
                super.setSprite(new Sprite(resourceLoader.getBumper(type)));
                break;
            case 180:
                super.setSprite(new Sprite(resourceLoader.getBumper(type)));
                break;
            case 270:
                super.setSprite(new Sprite(resourceLoader.getBumper(type)));
                break;
            default:
                throw (new BumperSideException(x,y));
        }
        //salvamos o tipo
        this.type = type;
        
        //setamos o som como false
        soundPlaying = false;
    }
    /**
     * colisao - Damos override no metodo de colisao para definir o que ocorre apos uma colisao
     */
    @Override
    public void colisao(Nave nave, LevelPanel levelP)
    {
        //testamos se devemos tocar o som
        if(!soundPlaying)
        {
            //tocamos o som
            ResourceLoader.playSound("ui/resources/Audio/Bumper.wav", -5f);
            
            Thread soundWait = new Thread(){
                @Override
                public void run()
                {
                    soundPlaying = true;
                    
                    //pausamos a thread
                    try{
                        this.sleep(2000);
                    }   
                    catch (InterruptedException e)
                    {
                        System.err.println("Sleep do speeder interrompido!");
                    }
                    
                    soundPlaying = false;
                }
            };
            
            soundWait.start();
        }
        
        //pegamos o vetor de velocidade
        Vector vel = nave.getVel();
        
        //calculamos os empurroes
        int x_Bump = BUMP*(int)Math.cos(Math.toRadians(type));
        int y_Bump = -BUMP*(int)Math.sin(Math.toRadians(type));
        
        //Empuramos a nave em uma direcao sem o controle do usuario
        nave.setVel(new Vector(vel.getVx() + x_Bump, vel.getVy() + y_Bump));
    }
}
