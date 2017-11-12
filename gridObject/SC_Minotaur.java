package gridObject;

import ui.resources.ResourceLoader;
import gridObject.especiais.ShieldThread;
import gridObject.movement.Vector;
import gridObject.movement.Sprite;

/**
 * SC_Minotaur e um nave capaz de proteger-se temporariamente
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class SC_Minotaur extends Nave
{
    //carregador de recursos
    ResourceLoader resourceLoader;
    
    /**
     * Construtor de SC_Minotaur recebe sua posicao e o resourceLoader do jogo
     * podemos fornecer, opicionalmente, uma velocidade inicial
     */
    public SC_Minotaur(int x, int y, ResourceLoader resourceLoader)
    {
        super(x, y, 24, 34);//tamanho da nave
        
        //inicializamos a sprite
        Sprite sprite = new Sprite(resourceLoader.getSpritesSC_Minotaur());
        
        //mandamos a sprite para gridObject
        super.setSprite(sprite);
        
        //setamos o carregador de recursos
        this.resourceLoader = resourceLoader;
    }
    public SC_Minotaur(int x, int y, ResourceLoader resourceLoader, int Vx, int Vy)
    {
        super(x, y, 3, 5, Vx, Vy);//tamanho da nave
        
        //inicializamos a sprite
        Sprite sprite = new Sprite(resourceLoader.getSpritesSC_Minotaur());
        
        //mandamos a sprite para gridObject
        super.setSprite(sprite);
        
        //setamos o carregador de recursos
        this.resourceLoader = resourceLoader;
    }
    public SC_Minotaur(int x, int y, ResourceLoader resourceLoader, Vector vel)
    {
        super(x, y, 3, 5, vel);//tamanho da nave
        
        //inicializamos a sprite
        Sprite sprite = new Sprite(resourceLoader.getSpritesSC_Minotaur());
        
        //mandamos a sprite para gridObject
        super.setSprite(sprite);
        
        //setamos o carregador de recursos
        this.resourceLoader = resourceLoader;
    }
    /**
     * especial - o especial dessa nave e um escudo
     * a protegera por alguns segundos
     */
    public void especial()
    {
        if(!super.getCooldown())//testamos se o especial esta em cooldown
        {
            //o especial desta nave e um escudo
            //ela deve ficar imune por um tempo e poder encostar nas paredes
            ShieldThread shieldThread = new ShieldThread(this, resourceLoader);
            
            //iniciamos a thread
            shieldThread.start();
        }
        else
        {
            /*
             * Obs: se estiver em cooldown, o metodog apenas toca um som
             * o usuario deve esperar o cooldown
             */
            ResourceLoader.playSound("ui/resources/Audio/Cooldown.wav", -5f);
        }
    }
}
