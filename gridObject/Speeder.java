package gridObject;

import ui.LevelPanel;
import ui.resources.ResourceLoader;
import exceptions.SpeederSideException;
import ui.resources.ResourceLoader;
import exceptions.SpeederSideException;
import gridObject.movement.Sprite;

/**
 * Speeder é um painel q acelera a nave
 * 
 * @author Andre, Eduardo, Marcelo 
 * @version 0.0
 */
public class Speeder extends Obstaculo
{
    //booleana para pausar a aceleracao da nave temporariamente
    private boolean sleeping = false;
    
    //constante do numero de milisegundos que o speeder deve "dormir"
    private final int SLEEP = 200;
    
    /**
     * Construtor para Speeder
     */
    public Speeder(int x, int y, int type, ResourceLoader resourceLoader) throws SpeederSideException
    {
        //utilizamos o construtor de parede para inicializar a posicao
        super(x, y, 70,70);
        
        //setamos a respectiva sprite
        switch(type)
        {
            case 0:
                super.setSprite(new Sprite(resourceLoader.getSpeeder(type)));
                break;
            case 90:
                super.setSprite(new Sprite(resourceLoader.getSpeeder(type)));
                break;
            case 180:
                super.setSprite(new Sprite(resourceLoader.getSpeeder(type)));
                break;
            case 270:
                super.setSprite(new Sprite(resourceLoader.getSpeeder(type)));
                break;
            default:
                throw (new SpeederSideException(x,y));
        }
    }
     /**
     * colisao - Damos override no metodo de colisao para definir o que ocorre apos uma colisao
     */
    @Override
    public void colisao(Nave nave, LevelPanel levelP)
    {
        if(!sleeping)
        {
            //aceleramos a nave sem o controle do usuario
            nave.accelerate();
            
            //iniciamos uma thread para esperar certo tempo ate acelerarmos a nave novamente
            Thread sleepThread = new Thread(){
                @Override
                public void run()
                {
                    //setamos um sleep do speeder
                    sleeping = true;
                    
                    //pausamos a thread
                    try{
                        //usamos Sleep constante definida
                        this.sleep(SLEEP);
                    }   
                    catch (InterruptedException e)
                    {
                        System.err.println("Sleep do speeder interrompido!");
                    }
                    
                    //apos o sleep
                    sleeping = false;
                }
            };
        }
    }
}
