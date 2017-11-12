package gridObject;

import gridObject.movement.Sprite;
import ui.resources.ResourceLoader;

/**
 * Parede Horizontal
 * 
 * @author Andre, Eduardo, Marcelo 
 * @version 0.0
 */
public class HorizontalWall extends Parede
{
    /**
     * Construtor de HorizontalWall
     */
    public HorizontalWall(int x, int y, ResourceLoader resourceLoader)
    {
        //utilizamos o construtor de GridObject para inicializar a posicao
        super(x, y, 180, 50);
        //setamos a sprite
        super.setSprite(new Sprite(resourceLoader.getHorizontalWall()));    
    }
}
