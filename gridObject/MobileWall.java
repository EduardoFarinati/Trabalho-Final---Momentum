package gridObject;

import gridObject.movement.Vector;
import ui.resources.ResourceLoader;

/**
 * MobileWall e a classe abstrata que administra as paredes moveis
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public abstract class MobileWall extends Parede implements Dinamico
{
    //vetor da velocidade
    Vector vel;
    
    public MobileWall(int x, int y, int width, int height, Vector V)
    {
        super(x, y, width, height);
        //utilizamos o construtor de parede para inicializar a posicao
        
        //igualamos ao vetor fornecido
        vel = V;
    }
    public Vector getVel()//retorna a velocidade, sobreescreve o metodo abstrato da interface dinamico
    {
        return vel;
    }
    public void setVel(Vector vel)//setamos a velocidade
    {
        this.vel = vel;
    }
}
