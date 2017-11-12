package fases;

import javax.swing.Timer;
import java.awt.event.*;
import gridObject.movement.MoveThread;

import exceptions.InvalidTimerState;

/**
 * Fase, superclasse para inicializacao de  cada mapa
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public abstract class Fase 
{
    //Lock das threads para iniciarem sincronizadas
    public static final Object threadLock = new Object();
    
    //atributos de contagem do tempo do mapa
    private Timer timer;
    private double runTime;
    
    public Fase()//Construtor para inicializacao do timer
    {
        runTime = 0;//tempo inicializado como zero
        
        //Timer(int delay, ActionListener listener)
        timer = new Timer(10, new ActionListener(){
            public void actionPerformed(ActionEvent e){ 
                runTime+=0.01;//cada evento de timer incrementa o contador do runTime
            }
        });//0.01 segundos de delay
    }
    /**
     * start - Inicializa o mapa
     */
    public abstract void start();
    /**
     * getLevelNum - retorna o numero do lvl
     */
    public abstract int getLevelNum();
    /**
     * getRunTime - Retorna o tempo de execucao do mapa
     * 
     * @return     runTime
     */
    public double getRunTime()
    {
        return runTime;
    }
    /**
     * stopTimer - para o timer
     * 
     */
    public void stopTimer() throws InvalidTimerState
    {
        //se o timer estiver rodando
        if(timer.isRunning())
        {
            //paramos
            timer.stop();
        }
        else
        {               
            throw (new InvalidTimerState("iniciar", "esta"));
        }
    }
    /**
     * pauseTimer - pausa o timer para o pause do jogo
     * 
     */
    public void pauseTimer() throws InvalidTimerState
    {
        //pausa o timer se este estiver rodando
        if(timer.isRunning())
        {
            timer.stop();
        }
        else
        {
            throw (new InvalidTimerState("parar", "nao esta"));
        }
    }
    /**
     * startTimer - inicia o timer
     * 
     */
    public void startTimer() throws InvalidTimerState
    {
        //se o timer nao estiver rodando
        if(!timer.isRunning())
        {
            //iniciamos o timer
            timer.start();
        }
        else
        {               
            throw (new InvalidTimerState("iniciar", "esta"));
        }
    }
    /**
     * restartTimer - reseta o timer para 0
     * 
     */
    public void restartTimer()
    {
        if(timer.isRunning())
        {
            //zeramos a variavel de runTime
            runTime = 0;
            //nao precisamos comecar timer pq ja esta rodando, e ele apenas soma a variavel
        }
        else
        {
            //zeramos a variavel de runTime
            runTime = 0;
            //iniciamos o timer
            timer.start();
        }
    }
    public synchronized void initiate(MoveThread[] threads)
    {
        //percorremos o array esperando as threads pausarem, se nao pausaram
        for(MoveThread i : threads)
        {
            //percorremos as threads e esperamos todas pausarem
            while(i.getState() != Thread.State.WAITING)
            {
                try
                {
                    Thread.sleep(0, 100);
                }
                catch(InterruptedException e)
                {
                    System.err.println("Erro no wait pelas Threads");
                }
            }
        }
        
        synchronized(threadLock)
        {
            //notificamos todas as threads para iniciar
            threadLock.notifyAll();
        }
        
        //iniciamos o timer
        this.startTimer();
    }
}