package gridObject;

import gridObject.movement.Vector;

/**
 * F1 e a segunda nave do jogo
 * seu especial e um turbo
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class F1 extends Nave
{
    /**
     * Constructor for objects of class F1
     */
    public F1(int x, int y)
    {
        super(x, y, 3, 5);//tamanho da nave
    }
    public F1(int x, int y, int Vx, int Vy)
    {
        super(x, y, 3, 5, Vx, Vy);//tamanho da nave
    }
    public F1(int x, int y, Vector vel)
    {
        super(x, y, 3, 5, vel);//tamanho da nave
    }


    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void especial()
    {
        //o especial desta nave e um turbo
        
        int turbo = 150;//predefiniçao de turbo
    }
}
