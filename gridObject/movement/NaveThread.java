package gridObject.movement;

import ui.LevelPanel;
import gridObject.*;
import fases.Fase;

import java.awt.Rectangle;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**
 * NaveThread - Thread responsavel por mover uma nave na grid
 * funciona em conjunto com SetasListener para mover a nave segundo o input do usuario
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class NaveThread extends MoveThread
{   
    //Variavel para compararmos a posicao com todos objetos
    private GridObject[] arr;
    //nave salva, que se move na grid
    private Nave nave;
    
    //Panel rodando o jogo, para compararmos se a fase nao acabou
    private LevelPanel levelP;
    
    /**
     * NaveThread recebe um objeto do tipo dinamico para ser movido
     */
    public NaveThread(Nave nave, GridObject[] arr, LevelPanel levelP)
    {
        //inicializamos os atributos
        this.arr = arr;
        //precisamos fazer essa conversao para pegarmos a hitbox
        //usamos nosso objeto dinamico de referencia
        this.nave = nave;
        
        //recebemos o level
        this.levelP = levelP;
    }

    //sobrescreve metodo run para definir o que a Thread fara ao ser executada
    @Override
    public void run() 
    {
        //damos lock para sincronizarmos as threads
        synchronized(Fase.threadLock)
        {
            try
            {
                //da lock nessa thread
                Fase.threadLock.wait();
                //espera ate ser notificada pra continuar
                //assim iniciamos as threads com um sincronismo
            }
            catch(InterruptedException ex)
            {
                System.err.println("Erro: Wait do lock Interrompido!");
            }
        }
        
        //iniciamos o retangulo da hitbox
        Rectangle hitbox;
       
        //iniciamos o vetor da velocidade
        Vector vel;
        
        //pegamos o retangulo do panel
        Rectangle panel = levelP.getBounds();

        //salva a antiga orientacao
        int ant = nave.getOrientacao();
        
        while(levelP.isRunning())
        {   
            //sincronizamos com o lock para podermos pausar
            synchronized(levelP.pauseLock)
            {
                if(!levelP.isRunning())
                {
                    //eh possivel q tenha alterado enquanto sincronizava
                    //usamos um break para evitar esse problema
                    break;
                }
                if(levelP.isPaused())
                {
                    try{
                        //da lock nessa thread
                        levelP.pauseLock.wait();
                        //espera ate ser notificada pra continuar
                    }
                    catch(InterruptedException ex)
                    {
                        System.err.println("Erro: Pause Interrompido!");
                    }
                }
                if(!levelP.isRunning())
                {
                    //eh possivel q tenha alterado enquanto estava pausado
                    //usamos um break para evitar esse problema
                    break;
                }
            }
                
            //pegamos o retangulo da posicao e o vetor da velocidade
            hitbox = nave.getHitbox();//esta definida em gridObject
            vel = nave.getVel();
            
            //se a posicao do objeto deve ser alterada
            if(Math.abs(vel.getVx()) + Math.abs(vel.getVy()) != 0 || ant != nave.getOrientacao())
            {
                //nova posicao do objeto sera:
                int dx = (int)hitbox.getX() + vel.getVx();
                int dy = (int)hitbox.getY() + vel.getVy();
                
                //ajuste para os limites da tela
                if(dx < 0)//fora da tela para esquerda
                {
                    //zeramos a posicao
                    dx = 0;
                    //zeramos a velocidade
                    nave.setVel(new Vector(0, vel.getVy()));
                }
                else if(dx + hitbox.getWidth() > panel.getWidth())//para direita
                {//removemos a hitbox da contage
                    //setamos a posicao maxima
                    dx = (int)(panel.getWidth() - hitbox.getWidth());
                    //zeramos a velocidade
                    nave.setVel(new Vector(0, vel.getVy()));
                }
                if(dy < 0)//fora de tela para cima
                {
                    //setamos a posicao minima
                    dy = 0;
                    //zeramos a velocidade
                    nave.setVel(new Vector(vel.getVx(), 0));
                }
                else if(dy + hitbox.getHeight() > panel.getHeight())//fora da tela para baixo
                {
                    //setamos a posicao maxima
                    dy =(int)(panel.getHeight() - hitbox.getHeight());
                    //zeramos a velocidade
                    nave.setVel(new Vector(vel.getVx(), 0));
                }
                
                //setamos a nova posicao da hitbox
                hitbox.setLocation(dx, dy);
                
                //mandamos para gridObject
                nave.setHitbox(hitbox);
            }
            
            //percorremos o array de gridObject
            for(int i = 0; i < arr.length; i++)
            {
                //se os retangulos se encontram decentamos uma colisao
                if(hitbox.intersects(arr[i].getHitbox()))
                {
                    //Mandamos uma mensagem indicando que houve uma colisao com a nave
                    ((Obstaculo)arr[i]).colisao(nave, levelP);
                       
                    //saimos do loop setando i como o tamanho do array
                    i = arr.length;
                }
            }
            
            //repintamos o objeto na grid
            levelP.repaint();
            //mandamos nossa posicao e orientacao
            
            //salvamos a nova orientacao
            ant = nave.getOrientacao();

            //pausamos a thread
            try{
                //usamos FPS definida em MoveThread
                this.sleep(1000/FPS);
            }   
            catch (InterruptedException e)
            {
                System.err.println("THREAD INTERROMPIDA!");
            }
        }
    }
}

