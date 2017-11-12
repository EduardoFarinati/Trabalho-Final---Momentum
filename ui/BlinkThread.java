package ui;

import javax.swing.JLabel;
import java.awt.Color;

/**
 * BlinkThread - Thread responsavel por fazer um score piscar
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class BlinkThread extends Thread
{
    //Constante de DELAY da thread
    private static final int DELAY = 220;
    //em milisegundos
    
    //scores para piscar
    private JLabel[] scores;
    private int arrPos;//posicao do array de scores
    
    //panel em que o score esta
    private HighscoresPanel panel;
    
    /**
     * BlinkThread recebe um int do numero de labels para piscar e um panel
     */
    public BlinkThread(int numScores, HighscoresPanel panel)
    {
        //inicializamos os atributos
        scores = new JLabel[numScores];
        this.panel = panel;
        //iniciamos a posicao do array como 0
        arrPos = 0;
    }
    /**
     * para adicionarmos um label a thread
     */
    public void addScore(JLabel score)
    {
        if(arrPos < scores.length)
        {
            //adicionamos um score ao array
            scores[arrPos] = score;
            //e incrementamos nossa variavel de posicao
            arrPos++;
        }
        else
        {
            System.err.println("ERRO, array de scores sobrecarregado!");
        }
    }

    //sobrescreve metodo run para definir o que a Thread fara ao ser executada
    @Override
    public void run() 
    {
        //variavel de visibilidade
        boolean isVisible = true;
        int colorChange = 0;
        //recebe a visibilidade do JLabel
        while(panel.isBlinking())
        {
            try
            { 
                /* if(isVisible)
                {
                    for(int i = 0; i < scores.length; i++)
                    {
                        scores[i].setForeground(Color.black);
                    }
                    isVisible = false;
                }
                else
                {
                    for(int i = 0; i < scores.length; i++)
                    {
                        scores[i].setForeground(Color.green);
                    }
                    isVisible = true;
                }
                //pausamos a thread
                this.sleep(DELAY);*/
                for(int i = 0; i < scores.length; i++)
                {
                    if(i!=colorChange)
                    {
                        scores[i].setForeground(Color.darkGray);
                    }
                    else
                    {
                        scores[i].setForeground(Color.green);
                    }
                }
                if(colorChange < scores.length)
                {
                    colorChange++;
                }
                else
                {
                    colorChange = 0;
                }
  
                this.sleep(DELAY);
           }
            catch (InterruptedException e)
            {//nao precisamos fazer um tratamento proprio para esta exception
            }
        }
        
        //antes de pararmos a Thread, retornamos todas as cores
        for(int i = 0; i < scores.length; i++)
        {
            scores[i].setForeground(Color.darkGray);
        }
    }
}