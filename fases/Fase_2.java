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
 * Fase 2, instanciação do mapa
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class Fase_2 extends Fase
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
    
    public Fase_2 (MainFrame mainFrame)
    {
        //iniciamos as referencias ao frame. ao painel e ao resourceLoader
        this.mainFrame = mainFrame;
        levelP = mainFrame.getLevelPanel();
        resourceLoader = mainFrame.getResourceLoader();
        
        //iniciamos os insets para usarmos de referencia
        insets = mainFrame.getContentPane().getInsets();
        
        //iniciamos o array de paredes
        Parede[] paredes = new Parede[27];
    
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
        
        paredes[12] = new HorizontalWall(insets.left + 0, insets.top+369, resourceLoader);
        paredes[13] = new HorizontalWall(insets.left + 1062, insets.top+369, resourceLoader);
        
        paredes[14] = new VerticalWall(insets.left + 596, insets.top-106, resourceLoader);
        
		paredes[15] = new HorizontalWall(insets.left + 81, insets.top+664, resourceLoader);
        paredes[16] = new HorizontalWall(insets.left + 232, insets.top+664, resourceLoader);
        
		paredes[17] = new VerticalWall(insets.left + 362, insets.top+124, resourceLoader);
        paredes[18] = new VerticalWall(insets.left + 412, insets.top+214, resourceLoader);
        paredes[19] = new VerticalWall(insets.left + 462, insets.top+304, resourceLoader);
        paredes[20] = new VerticalWall(insets.left + 512, insets.top+394, resourceLoader);
        paredes[21] = new VerticalWall(insets.left + 702, insets.top+274, resourceLoader);
        paredes[22] = new VerticalWall(insets.left + 752, insets.top+334, resourceLoader);
        paredes[23] = new VerticalWall(insets.left + 802, insets.top+394, resourceLoader);
        paredes[24] = new VerticalWall(insets.left + 852, insets.top+484, resourceLoader);
       
		paredes[25] = new HorizontalWall(insets.left + 852, insets.top+664, resourceLoader);
        paredes[26] = new HorizontalWall(insets.left + 981, insets.top+664, resourceLoader);
        
        //paredes moveis no array
        MobileWall[] mWalls = new MobileWall[2];
        
        mWalls[0] = new VerticalMobileWall(insets.left + 155, insets.top+486, new Vector(5,0), resourceLoader);
        //posicao final: 460, 486
        WallThread wallThread1 = new WallThread(mWalls[0], insets.left + 460, insets.top+486, true, levelP);
        
        mWalls[1] = new HorizontalMobileWall(insets.left + 1052, insets.top+254, new Vector(-5,0), resourceLoader);
        //posicao final: 752, 254
        WallThread wallThread2 = new WallThread(mWalls[1], insets.left + 752, insets.top+254, true, levelP);
        
        //Bumpers do nivel
        Bumper[] bumpers = new Bumper[5];
       
        bumpers[0] = new Bumper(insets.left + 180, insets.top+369, 0, resourceLoader);
        bumpers[1] = new Bumper(insets.left + 362, insets.top+304, 270, resourceLoader);
        bumpers[2] = new Bumper(insets.left + 412, insets.top+394, 270, resourceLoader);
        bumpers[3] = new Bumper(insets.left + 801, insets.top+124, 270, resourceLoader);
		bumpers[4] = new Bumper(insets.left + 852, insets.top+432, 90, resourceLoader);
        
        //iniciamos o warp
        Warp warp = new Warp(insets.left + 219, insets.top+173, resourceLoader);
        
        //setamos que warp e animado
        animado = new Animado[1];
        animado[0] = warp;
        
        //array recebe os objetos da grid
        arr = new GridObject[35];
        
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
        //adicionamoso warp a grid
        arr[i] = warp;
        
        //iniciamos a nave em sua posicao
        nave = new SC_Minotaur(insets.left+984, insets.top+588, resourceLoader);
        
        //iniciamos o listener das setas
        SetasListener setas = new SetasListener(nave);
        
        //adicionamos o listener
        levelP.addKeyListener(setas);
        
        //adicionamos os objetos dinamicos
        arr_Dinamico = new Dinamico[3];
        arr_Dinamico[0] = nave;
        //adicionamos cada uma das paredes moveis
        for(i = 1; i <arr_Dinamico.length; i++)
        {
            arr_Dinamico[i] = mWalls[i-1];
        }

		threads = new MoveThread[3];
		threads[0] = wallThread1;
		threads[1] = wallThread2;//threads das paredes
		threads[2] = new NaveThread(nave, arr, levelP);//thread da nave
    }
   
    /**
     * start - Inicializa a fase 2
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
     * Retorna nosso nivel, no caso: 2
     */
    public int getLevelNum()
    {
        return 2;
    }
}