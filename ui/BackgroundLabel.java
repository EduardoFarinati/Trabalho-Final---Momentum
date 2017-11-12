package ui;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.*;

import gridObject.Dinamico;
import gridObject.Animado;

/**
 * Background e onde guardamos todos objetos da grid
 * 
 * @author Andre, Marcelo, Eduardo 
 * @version 0.0
 */
public class BackgroundLabel extends JLabel
{   
    //Onde imprimiremos os objetos
    PaintLabel paint;
    
    /**
     * Construtor para BackgroundLabel, recebe o fundo
     */
    public BackgroundLabel(ImageIcon icon)
    {
        //usamos o construtor com um icone de JLabel
        super(icon);
        //setamos o layout como null
        this.setLayout(null);
        
        //instanciamos paint
        paint = new PaintLabel();
        
        //adicionamos o paint para pintarmos na tela
        this.add(paint);
    }
    public void setObjects(Dinamico[] dinamico, Animado[] animated)
    {
        //mandamos para o paint
        paint.setObjects(dinamico, animated);   
    }
    @Override
    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x, y, width, height);
        paint.setBounds(x, y, width, height);
    }
}