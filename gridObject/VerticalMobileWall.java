package gridObject;

import gridObject.movement.Vector;
import gridObject.movement.Sprite;
import ui.resources.ResourceLoader;

/**
 * Parede Vertical Movel
 * 
 * @author Andre, Eduardo, Marcelo 
 * @version 0.0
 */
public class VerticalMobileWall extends MobileWall
{
    /**
     * Construtor de VerticalMobileWall
     */
    public VerticalMobileWall(int x, int y, int Vx, int Vy, ResourceLoader resourceLoader)
    {
        //utilizamos o construtor de GridObject para inicializar a posicao
        super(x, y, 50, 180, new Vector(Vx, Vy));
        //setamos a sprite
        super.setSprite(new Sprite(resourceLoader.getVerticalMobileWall()));    
    }
    public VerticalMobileWall(int x, int y, Vector V, ResourceLoader resourceLoader)
    {
        //utilizamos o construtor de GridObject para inicializar a posicao
        super(x, y, 50, 180, V);
        //setamos a sprite
        super.setSprite(new Sprite(resourceLoader.getVerticalMobileWall()));    
    }
}
