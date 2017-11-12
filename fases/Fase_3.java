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
 * Fase 3, instanciação do mapa
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class Fase_3 extends Fase
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
    
    public Fase_3 (MainFrame mainFrame)
    {
        //iniciamos as referencias ao frame. ao painel e ao resourceLoader
        this.mainFrame = mainFrame;
        levelP = mainFrame.getLevelPanel();
        resourceLoader = mainFrame.getResourceLoader();
        
         //iniciamos os insets para usarmos de referencia
        insets = mainFrame.getContentPane().getInsets();
        
        //iniciamos o array de paredes
        Parede[] paredes = new Parede[20];
        
        paredes[0] = new VerticalWall(insets.left + 436, insets.top+439, resourceLoader);
        paredes[1] = new VerticalWall(insets.left + 750, insets.top+628, resourceLoader);
        paredes[2] = new VerticalWall(insets.left + 939, insets.top-56, resourceLoader);
        
        paredes[3] = new HorizontalWall(insets.left - 72, insets.top+201, resourceLoader);
        paredes[4] = new HorizontalWall(insets.left - 27, insets.top+151, resourceLoader);
        paredes[5] = new HorizontalWall(insets.left, insets.top+101, resourceLoader);
        paredes[6] = new HorizontalWall(insets.left + 263, insets.top+201, resourceLoader);
        paredes[7] = new HorizontalWall(insets.left + 308, insets.top+151, resourceLoader);
        paredes[8] = new HorizontalWall(insets.left + 335, insets.top+101, resourceLoader);        
        paredes[9]  = new HorizontalWall(insets.left + 784, insets.top+124, resourceLoader);
        paredes[10] = new HorizontalWall(insets.left + 732, insets.top+174, resourceLoader);
        paredes[11] = new HorizontalWall(insets.left + 687, insets.top+224, resourceLoader);
        paredes[12] = new HorizontalWall(insets.left + 635, insets.top+274, resourceLoader);
        paredes[13] = new HorizontalWall(insets.left + 583, insets.top+324, resourceLoader);
        paredes[14] = new HorizontalWall(insets.left + 538, insets.top+374, resourceLoader);
        paredes[15] = new HorizontalWall(insets.left + 436, insets.top+424, resourceLoader);
        paredes[16] = new HorizontalWall(insets.left + 761, insets.top+416, resourceLoader);
        paredes[17] = new HorizontalWall(insets.left + 941, insets.top+416, resourceLoader);
        paredes[18] = new HorizontalWall(insets.left + 761, insets.top+557, resourceLoader);
        paredes[19] = new HorizontalWall(insets.left + 941, insets.top+557, resourceLoader);
        
        //paredes moveis no array
        MobileWall[] mWalls = new MobileWall[7];
        WallThread[] wallThreads = new WallThread[7];
        
        mWalls[0] = new VerticalMobileWall(insets.left + 53, insets.top+531, new Vector(0,-5), resourceLoader);
        //posicao final: 53, 431
        wallThreads[0] = new WallThread(mWalls[0], insets.left + 53, insets.top+431, true, levelP);
        
        mWalls[1] = new VerticalMobileWall(insets.left + 53, insets.top+351, new Vector(0,-5), resourceLoader);
        //posicao final: 53, 251
        wallThreads[1] = new WallThread(mWalls[1], insets.left + 53, insets.top+251, true, levelP);
        
        mWalls[2] = new VerticalMobileWall(insets.left + 243, insets.top+531, new Vector(0,-5), resourceLoader);
        //posicao final: 243, 431
        wallThreads[2] = new WallThread(mWalls[2], insets.left + 243, insets.top+431, true, levelP);
        
        mWalls[3] = new VerticalMobileWall(insets.left + 243, insets.top+351, new Vector(0,-5), resourceLoader);
        //posicao final: 243, 251
        wallThreads[3] = new WallThread(mWalls[3], insets.left + 243, insets.top+251, true, levelP);
        
        mWalls[4] = new HorizontalMobileWall(insets.left + 83, insets.top+661, new Vector(0,-5), resourceLoader);
        //posicao final: 83, 561
        wallThreads[4] = new WallThread(mWalls[4], insets.left + 83, insets.top+561, true, levelP);
        
        mWalls[5] = new HorizontalMobileWall(insets.left + 83, insets.top+301, new Vector(4,0), resourceLoader);
        //posicao final: 243, 301
        wallThreads[5] = new WallThread(mWalls[5], insets.left + 243, insets.top+301, true, levelP);
        
        mWalls[6] = new HorizontalMobileWall(insets.left + 348, insets.top+25, new Vector(-5,0), resourceLoader);
        //posicao final: -177, 25
        wallThreads[6] = new WallThread(mWalls[6], insets.left - 177, insets.top+25, true, levelP);
        
        //Bumpers do nivel
        Bumper[] bumpers = new Bumper[6];
       
        bumpers[0] = new Bumper(insets.left + 528, insets.top+24, 180, resourceLoader);
        bumpers[1] = new Bumper(insets.left + 887, insets.top, 180, resourceLoader);
        bumpers[2] = new Bumper(insets.left + 887, insets.top+72, 180, resourceLoader);
        bumpers[3] = new Bumper(insets.left + 361, insets.top+646, 0, resourceLoader);
        bumpers[4] = new Bumper(insets.left + 651, insets.top+486, 0, resourceLoader);
        bumpers[5] = new Bumper(insets.left + 577, insets.top+24, 180, resourceLoader);
        
        //Speeders do nivel
        Speeder[] speeders = new Speeder[7];
        
        speeders[0] = new Speeder(insets.left + 248, insets.top+15, 0, resourceLoader);
        speeders[1] = new Speeder(insets.left + 318, insets.top+15, 0, resourceLoader);
        speeders[2] = new Speeder(insets.left + 388, insets.top+15, 0, resourceLoader);
        speeders[3] = new Speeder(insets.left + 785, insets.top+477, 0, resourceLoader);
        speeders[4] = new Speeder(insets.left + 855, insets.top+477, 0, resourceLoader);
        speeders[5] = new Speeder(insets.left + 926, insets.top+477, 0, resourceLoader);
        speeders[6] = new Speeder(insets.left + 995, insets.top+477, 0, resourceLoader);
        
        //iniciamos o warp
        Warp warp = new Warp(insets.left + 1146, insets.top+466, resourceLoader);
        
        //setamos que warp e animado
        animado = new Animado[1];
        animado[0] = warp;
        
        //array recebe os objetos da grid
        arr = new GridObject[41];
        
        //contadores auxiliares para percorrer o array
        int i, j=0;
        //recebemos todas paredes
        for(i = 0; i < paredes.length; i++)
        {
            arr[i] = paredes[i];
        }
        //adicionamos as paredes moveis
        for(j = 0; j < mWalls.length; i++, j++)
        {
            arr[i] = mWalls[j];
        }
        //adicionamos os Bumpers
        for(j = 0; j < bumpers.length; i++, j++)
        {
            arr[i] = bumpers[j];
        }
        //adicionamos os speeder
        for(j = 0; j < speeders.length; i++, j++)
        {
            arr[i] = speeders[j];
        }
        //adicionamoso warp a grid
        arr[i] = warp;
        
        //iniciamos a nave em sua posicao
        nave = new SC_Minotaur(insets.left+163, insets.top+486, resourceLoader);
        //posicao 163, 486
        
        //iniciamos o listener das setas
        SetasListener setas = new SetasListener(nave);
        
        //adicionamos o listener
        levelP.addKeyListener(setas);
        
        //adicionamos os objetos dinamicos
        arr_Dinamico = new Dinamico[8];
        arr_Dinamico[0] = nave;
        //adicionamos cada uma das paredes moveis
        for(i = 1; i <arr_Dinamico.length; i++)
        {
            arr_Dinamico[i] = mWalls[i-1];
        }
        
        //criamos o array que salva todas threads
        threads = new MoveThread[8];
        i=0;
        threads[i] = new NaveThread(nave, arr, levelP);//thread da nave
        i++;
        for(j = 0; j < wallThreads.length; i++, j++)
        {//salvamos as threads das paredes
            threads[i] = wallThreads[j];
        }
    }
   
    /**
     * start - Inicializa a fase 3
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
     * Retorna nosso nivel, no caso: 3
     */
    public int getLevelNum()
    {
        return 3;
    }
}