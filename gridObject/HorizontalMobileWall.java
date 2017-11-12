package gridObject;

import gridObject.movement.Vector;
import gridObject.movement.Sprite;
import ui.resources.ResourceLoader;

/**
 * Parede Horizontal Movel
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class HorizontalMobileWall extends MobileWall
{
    /**
     * Construtor de HorizontalMobileWall
     */
    public HorizontalMobileWall(int x, int y, int Vx, int Vy, ResourceLoader resourceLoader)
    {
        //utilizamos o construtor de GridObject para inicializar a posicao
        super(x, y, 180, 50, new Vector(Vx, Vy));
        //setamos a sprite
        super.setSprite(new Sprite(resourceLoader.getHorizontalMobileWall()));    
    }
    public HorizontalMobileWall(int x, int y, Vector V, ResourceLoader resourceLoader)
    {
        //utilizamos o construtor de GridObject para inicializar a posicao
        super(x, y, 180, 50, V);
        //setamos a sprite
        super.setSprite(new Sprite(resourceLoader.getHorizontalMobileWall()));    
    }
}
