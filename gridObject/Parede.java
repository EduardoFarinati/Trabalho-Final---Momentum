package gridObject;

import ui.LevelPanel;
import gridObject.movement.Vector;
import ui.resources.ResourceLoader;

import java.awt.Rectangle;

/**
 * Paredes na grid, teremos diversas dessas controladas pela main
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public abstract class Parede extends Obstaculo
{   
    public Parede(int x, int y, int width, int height)//a parede pode ser vertical ou horizontal
    {
       super(x, y, width, height);
    }
    
    /**
     * colisao - Damos override no metodo de colisao para definir o que ocorre apos uma colisao
     */
    @Override
    public void colisao(Nave nave, LevelPanel levelP)
    {
        //apenas acionamos se a nave nao esta protegida
        if(!nave.getShielded())
        {
            //Zeramos o mapa caso bata em uma parede!
            
            //tocamos o som
            ResourceLoader.playSound("ui/resources/Audio/Explosion.wav", -10f);
            
            //Carregamos o hitbox
            Rectangle hitbox = nave.getHitbox();
        
            //Colocamos a nave na posicao inicial
            hitbox.setLocation(nave.getInitX(), nave.getInitY());
        
            //setamos a velocidade como zero
            nave.setVel(new Vector());
        
            //setamos a orientacao como a inicial
            nave.setOrientacao(nave.getInitOrient());
            
            //resetamos o cooldown do especial
            nave.setCooldown(false);
        
            //zeramos o timer da fase
            levelP.resetFaseTimer();
        }
    }
}