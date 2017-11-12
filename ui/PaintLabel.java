package ui;

import javax.swing.JLabel;
import java.awt.*;

import gridObject.Dinamico;
import gridObject.Animado;
import gridObject.GridObject;
import exceptions.UnsetObjectsException;

/**
 * PaintLabel existe para podermos imprimir os objetos dinamicos
 * no mais opaco da tela possivel
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class PaintLabel extends JLabel
{
    //imagens q irao se mover na tela
    private GridObject[] movObjects;
    
    //array de objetos que possuem animacao
    private Animado[] animado;
    
    @Override
    protected void paintComponent(Graphics g) {
        //demos override no metodo de pintar, para podermos pintar nosso objetos especiais na grid
        
        if(movObjects != null)
        {
            //percorremos o array de objetos animados e mudamos suas sprites
            for(Animado j : animado)
            {
                //modificamos a sprite de cada objeto animado
                j.animate();
                
                //fazemos um casting para trabalharmos como gridObject
                GridObject i = (GridObject)j;
                
                //atualizamos o imageIcon
                i.setIcon((i.getSprite()).getImageIcon());
            }
            
            //repintamos todos objetos que nao se movem(podem ser animados)
            super.paintComponent(g);
            
            //percorremos o array de objetos
            for(int i = 0; i < movObjects.length; i++)
            {
                //recuperamos a image pelo array de gridObject, todo parametro necesario esta em gridObject e Sprite
                Image sprite = ((movObjects[i].getSprite()).getImageIcon(movObjects[i].getOrientacao())).getImage();
                
                //pegamos sua hitbox para descobrir a posicao
                Rectangle hitbox = movObjects[i].getHitbox();  
                
                //desenhamos o objeto
                g.drawImage(sprite, (int)hitbox.getX(), (int)hitbox.getY(), this);
            }            
        }
        else
        {
           throw (new UnsetObjectsException());
        }
    }
    public void setObjects(Dinamico[] dinamico, Animado[] animated)
    {
        //iniciamos nosso array de gridObjects
        movObjects = new GridObject[dinamico.length];
        
        for(int i = 0; i<dinamico.length; i++)
        {
            movObjects[i] = (GridObject)dinamico[i];
        }
        
        //setamos o array de objetos animado
        animado = animated;        
    }
}
