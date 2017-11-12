package fases;

import ui.*;
import ui.resources.ResourceLoader;
import gridObject.*;
import gridObject.movement.*;

import java.awt.Insets;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import javax.imageio.*;

/**
 * Fase 1, instanciação do mapa
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class Fase_1 extends Fase
{   
    //referencia as classes principais do programa
    private MainFrame mainFrame;
    private LevelPanel levelP;
    private ResourceLoader resourceLoader;
    
    //referencia para os objetos
    private Insets insets;
    
    //nave que percorrera o mapa
    private Nave nave;
    
    //adicionamos as threads para iniciarmos "juntas"
    private MoveThread[] threads;
    
    //array de objetos na fase
    private GridObject[] arr;
    
    //array de objetos moveis
    private Dinamico[] arr_Dinamico;
    
    //array de objetos animados
    Animado[] animado;
    
    public Fase_1 (MainFrame mainFrame)
    {
        //iniciamos as referencias ao frame. ao painel e ao resourceLoader
        this.mainFrame = mainFrame;
        levelP = mainFrame.getLevelPanel();
        resourceLoader = mainFrame.getResourceLoader();
        
        //iniciamos os insets para usarmos de referencia
        insets = mainFrame.getContentPane().getInsets();
        
        //iniciamos o array de paredes
        Parede[] paredes = new Parede[31];
        
        //iniciamos todas paredes
        paredes[0] = new VerticalWall(insets.left + 81, insets.top+484, resourceLoader);
        paredes[1] = new VerticalWall(insets.left + 81, insets.top+304, resourceLoader);
        paredes[2] = new VerticalWall(insets.left + 81, insets.top+124, resourceLoader);
        
        paredes[3] = new HorizontalWall(insets.left + 81, insets.top+74, resourceLoader);
        paredes[4] = new HorizontalWall(insets.left + 261, insets.top+74, resourceLoader);
        paredes[5] = new HorizontalWall(insets.left + 441, insets.top+74, resourceLoader);
        paredes[6] = new HorizontalWall(insets.left + 621, insets.top+74, resourceLoader);
        paredes[7] = new HorizontalWall(insets.left + 801, insets.top+74, resourceLoader);
        paredes[8] = new HorizontalWall(insets.left + 981, insets.top+74, resourceLoader);
        
        paredes[9]  = new VerticalWall(insets.left + 1111, insets.top+124, resourceLoader);
        paredes[10] = new VerticalWall(insets.left + 1111, insets.top+304, resourceLoader);
        paredes[11] = new VerticalWall(insets.left + 1111, insets.top+484, resourceLoader);
        paredes[12] = new VerticalWall(insets.left + 362, insets.top+484, resourceLoader);
        
        paredes[13] = new HorizontalWall(insets.left + 362, insets.top+434, resourceLoader);
        paredes[14] = new HorizontalWall(insets.left + 362, insets.top+384, resourceLoader);
        paredes[15] = new HorizontalWall(insets.left + 362, insets.top+334, resourceLoader);
        paredes[16] = new HorizontalWall(insets.left + 362, insets.top+284, resourceLoader);
        paredes[17] = new HorizontalWall(insets.left + 542, insets.top+284, resourceLoader);
        paredes[18] = new HorizontalWall(insets.left + 722, insets.top+284, resourceLoader);
        paredes[19] = new HorizontalWall(insets.left + 722, insets.top+334, resourceLoader);
        paredes[20] = new HorizontalWall(insets.left + 722, insets.top+384, resourceLoader);
        paredes[21] = new HorizontalWall(insets.left + 722, insets.top+434, resourceLoader);
        paredes[22] = new HorizontalWall(insets.left + 542, insets.top+334, resourceLoader);
        paredes[23] = new HorizontalWall(insets.left + 477, insets.top+234, resourceLoader);
        paredes[24] = new HorizontalWall(insets.left + 607, insets.top+234, resourceLoader);
        paredes[25] = new HorizontalWall(insets.left + 8, insets.top+369, resourceLoader);
        paredes[26] = new HorizontalWall(insets.left + 1054, insets.top+369, resourceLoader);
        
        paredes[27] = new VerticalWall(insets.left + 852, insets.top+484, resourceLoader);
        paredes[28] = new VerticalWall(insets.left + 607, insets.top+531, resourceLoader);
        paredes[29] = new VerticalWall(insets.left + 607, insets.top-106, resourceLoader);
        paredes[30] = new VerticalWall(insets.left + 607, insets.top+204, resourceLoader);
        
        //iniciamos o warp para a proxima fase
        Warp warp = new Warp(insets.left + 219, insets.top+573, resourceLoader);
        
        //setamos que warp e animado
        animado = new Animado[1];
        animado[0] = warp;
        
        //array recebe os objetos da grid
        arr = new GridObject[32];
        
        //contador auxiliar para percorrer o array
        int i;
        //recebemos todas paredes
        for(i = 0; i < paredes.length; i++)
        {
            arr[i] = paredes[i];
        }
        
        //adicionamoso warp a grid
        arr[i] = warp;
        
        //iniciamos a nave em sua posicao
        nave = new SC_Minotaur(insets.left+987, insets.top+614, resourceLoader);
        
        //iniciamos o listener das setas
        SetasListener setas = new SetasListener(nave);
        
        //adicionamos o listener
        levelP.addKeyListener(setas);
        
        //adicionamos os objetos dinamicos
        arr_Dinamico = new Dinamico[1];
        arr_Dinamico[0] = nave;
        
        //moveThread da nave
        threads = new MoveThread[1];
        threads[0] = new NaveThread(nave, arr, levelP);
    }
    /**
     * start - Inicializa a fase 1
     */
    public void start()
    {   
        //setamos os objetos e separamos em moveis e estaticos, mandamos os animados separadamente tambem
        levelP.setGridObjects(arr, arr_Dinamico, animado);
        
        for(MoveThread i : threads)
        {
            //todas iniciadas pelo enhanced for
            i.start();
        }
        
        //usamos o metodo em super para iniciarmos as threads sincronizadas
        super.initiate(threads);
    }
    /**
     * Retorna nosso nivel, no caso: 1
     */
    public int getLevelNum()
    {
        return 1;
    }
}