package gridObject;

import java.awt.Rectangle;
import gridObject.movement.Sprite;
import exceptions.InvalidAngleException;
import javax.swing.JLabel;

/**
 * Todo Objeto pertencente a grid bidimensional do jogo deve extender essa classe
 * Abstrata, nunca sera instanciada
 * estende JLabel para podermos adicionar o objeto a um container
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public abstract class GridObject extends JLabel
{
    // Hitbox de um objeto na grid, definimos para detectar as colisoes
    private Rectangle hitbox;
    private int orientacao;
    //todo objeto da grid deve ter uma hitbox
    private Sprite sprite;
    
    /**
     * Construtor deve inicializar na orientacao default ou em uma orientacao dada
     */
    public GridObject(int x, int y, int largura, int altura)
    {
        //utilizamos o construtor Rectangle(int x, int y, int width, int height) x e y sao as coordenadas superiores esquerdas
        hitbox = new Rectangle(x, y, largura, altura);
        
        //90 graus de inclinacao 
        orientacao = 90;
        
        //inicializamos uma Sprite vazia
        sprite = null;
    }
    public GridObject(int x, int y, int largura, int altura, int orientacao)
    {
        //utilizamos o construtor Rectangle(int x, int y, int width, int height) x e y sao as coordenadas superiores esquerdas
        hitbox = new Rectangle(x, y, largura, altura);
        
        //testamos se o angulo é valido
        if(orientacao >= 0 && orientacao < 360)
        {
            //setamos a orientacao fornecida
            this.orientacao = orientacao;
        }
        else
        {
            throw (new InvalidAngleException(orientacao));
        }
        //inicializamos uma Sprite vazia
        sprite = null;
    }
    public Rectangle getHitbox()
    {
        return hitbox;
    }
    /**
     * setHitbox seta a nova hitbox
     */
    public void setHitbox(Rectangle hitbox)
    {
        this.hitbox = hitbox;
    }
    //getter e setter da orientacao
    public int getOrientacao()
    {
        return orientacao;
    }
    public void setOrientacao(int orientacao)
    {
        //testamos se o angulo setado é valido
        if(orientacao >= 0 && orientacao < 360)
        {
            this.orientacao = orientacao;
        }
        else
        {
            throw (new InvalidAngleException(orientacao));
        }
    }
    /**
     * retornamos a sprite do objeto
     */
    public Sprite getSprite()
    {
        return sprite;
    }
    public void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
    }
    
    /**
     * rotate gira o objeto em um angulo fornecido positiva ou negativamente
     */
    protected void rotate(int graus, boolean sentido)//true para anti-horario, false para horario
    {
        if(sentido)//anti horario, graus positivos
        {
            if(orientacao + graus < 360)
            {
                orientacao += graus;
            }
            else//caso passarmos um circulo
            {
                orientacao = orientacao + graus - 360;
            }
        }
        else//horario, graus negativos
        {
            if(orientacao - graus >= 0)
            {
                orientacao -= graus;
            }
            else//caso passarmos um circulo
            {
                orientacao = orientacao - graus + 360;
            }
        }
    }
}
