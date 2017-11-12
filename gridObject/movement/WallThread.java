package gridObject.movement;

import ui.LevelPanel;
import fases.Fase;
import gridObject.*;

import java.awt.Rectangle;

/**
 * WallThread descreve as paredes moveis na grid
 * e seus comportamentos
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class WallThread extends MoveThread
{
    //Wall salva, se movera
    private MobileWall wall;
    
    //variaveis da posicao inicial
    private int startX, startY;
    
    //variaveis da posicao final
    private int endX, endY;
    
    //podemos ter uma parede que vai e volta ou uma que vai e se "teleporta"
    boolean wallType;
    
    //variaveis de calculo da posicao
    int dx;
    int dy;
    
    //variavel do vetor da velocidade
    Vector vel;
    
    //referencia ao painel rodando o jogo
    private LevelPanel levelP;

    /**
     *  WallThread recebe uma parede a ser movida
     */
    public WallThread(MobileWall wall, int endX, int endY, boolean wallType, LevelPanel levelP)
    {
        //inicializamos a referencia a wall
        this.wall = wall;
        
        //setamos o tipo de parede
        this.wallType = wallType;
        //wallType pode ser true: reverte ciclos (vai e volta)
        //wallType pode ser false: nao reverte (apenas vai)
        
        //inciamos a posicao inicial
        startX = (int)wall.getHitbox().getX();
        startY = (int)wall.getHitbox().getY();
        
        //iniciamos a posicao final
        this.endX = endX;
        this.endY = endY;
        
        //setamos o vetor
        vel = wall.getVel();
        
        //salvamos o level
        this.levelP = levelP;
    }
    
    //sobrescreve metodo run para definir o que a Thread fara ao ser executada
    @Override
    public void run() 
    {   
        //damos lock para sincronizamos as threads
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
        
        
        //definimos o rectangle de hitbox
        Rectangle hitbox;
        
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
                
            //encontramos a posicao
            hitbox = wall.getHitbox();
                
            //calculamos nova posicao
            this.calculaPosicao();
            
            if(dx != endX || dy != endY)
            {
                //seta a posicao no lugar calculado
                hitbox.setLocation(dx, dy);   
            }
            else
            {
                //seta a posicao no zero
                if(!wallType)
                {
                    hitbox.setLocation(startX, startY);
                }
                else
                {
                    //revertemos o ciclo
                    hitbox.setLocation(dx, dy);
                    //setamos a posicao inicial como a final e a final como a inicial
                    int aux = endX;
                    
                    endX = startX;
                    startX = aux;
                    
                    aux = endY;
                    
                    endY = startY;
                    startY = aux;
                    
                    //setamos a velocidade como o contario
                    wall.setVel(new Vector(-vel.getVx(), -vel.getVy()));
                }
            }
            
            //mandamos para gridObject
            wall.setHitbox(hitbox);
            
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
    private void calculaPosicao()
    {
        //carregamos o vetor da velocidade
        vel = wall.getVel();
        
        //calculamos nova posicao
        dx = (int)wall.getHitbox().getX() + vel.getVx();
        dy = (int)wall.getHitbox().getY() + vel.getVy();
    }
}
