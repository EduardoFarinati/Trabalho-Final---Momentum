package gridObject;

import gridObject.movement.Sprite;
import ui.resources.ResourceLoader;

/**
 * Parede Vertical
 * 
 * @author Andre, Eduardo, Marcelo 
 * @version 0.0
 */
public class VerticalWall extends Parede
{
    /**
     * Construtor de VerticalWall
     */
    public VerticalWall(int x, int y, ResourceLoader resourceLoader)
    {
        //utilizamos o construtor de GridObject para inicializar a posicao
        super(x, y, 50, 180);
        //setamos a sprite
        super.setSprite(new Sprite(resourceLoader.getVerticalWall()));    
    }
}
