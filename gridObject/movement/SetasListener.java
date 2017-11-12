package gridObject.movement;

import gridObject.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;

/**
 * SetasListener - Escuta as setas para alterar os valores da nave
 * escuta multiplas setas ao mesmo tempo
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class SetasListener extends KeyAdapter
{
    //Nave que se move na grid
    private Nave nave;
    
    //Array para o caso de multiplas keys
    private HashSet<Integer> keysDown;
    //utilizamos do recurso de generics para indicar que sao inteiros
    
    /*  Para adicionar a um componente
        
        SetasListener listener = new SetasListener(nave);
        componente.addKeyListener(listener);
        componente.setFocusable(true);
    */
   
    /**
     * Recebe uma nave a ser tratada com os eventos
     */
    public SetasListener(Nave nave)
    {
        //inicializamos a nave
        this.nave = nave;
        
        //Inciamos o hashSet de multiplas keys
        keysDown = new HashSet<Integer>();
    }     
    
    //Sobreescreve os metodos de Keylistener para mover a nave como desejado
    @Override
    public void keyPressed(KeyEvent e)
    {   
        if(!keysDown.contains(e.getKeyCode()))//se o array nao contem a tecla apertada
        {
            keysDown.add(new Integer(e.getKeyCode()));//adicionamos a tecla apertada ao array
            //chamamos o metodo para tratar as teclas apertadas
            
            //iniciamos o iterador para encontrarmos as teclas apertadas
            Iterator i = keysDown.iterator();
        
            //percorremos as teclas
            while(i.hasNext())
            {
                //mandamos a tecla encontrada
                this.moveNave(((Integer)i.next()).intValue());
            }
            
            //removemos a tecla
            keysDown.remove(new Integer(e.getKeyCode()));
        }
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
        keysDown.remove(new Integer(e.getKeyCode()));
    }
    
    /**
     * metodo privado, auxilia na manipulacao do array de teclas apertadas
     */
    private void moveNave(int key)
    {
        //damos o tratamento proprio dado as teclas apertadas
        if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
        {//acelerar
            nave.accelerate();
        }
        if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
        {//rotar
            nave.rotate(true);//mudamos a orientacao positivamente(sentido anti-horario)
        }
        if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
        {//rotar
            nave.rotate(false);//mudamos a orientacao positiviamente(sentido horario)
        }
        if(key == KeyEvent.VK_Z)
        {
            //setamos a orientacao em 180 graus
            nave.setOrientacao(180);
        }
        if(key == KeyEvent.VK_V)
        {
            //setamos a orientacao em 180 graus
            nave.setOrientacao(90);
        }
        if(key == KeyEvent.VK_C)
        {
            //setamos a orientacao em 180 graus
            nave.setOrientacao(0);
        }
        if(key == KeyEvent.VK_X)
        {
           //setamos a orientacao em 180 graus
           nave.setOrientacao(270);
        }
        if(key == KeyEvent.VK_SPACE)//especial
        {
            nave.especial();
        }
    }
}
