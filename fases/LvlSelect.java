package fases;

import exceptions.LvlOutOfBoundsException;

/**
 * LvlSelect é uma interface simples para a selecao e inicializacao de um mapa
 * funciona por metodos estaticos, nao pode ser instanciada
 * 
 * @author Andre, Eduardo, Marcelo 
 * @version 0.0
 */
public interface LvlSelect
{
    //Constante do numero de levels
    public static final int LEVELNUM = 3;
    
    public static void startLvl(int lvl, ui.MainFrame mainFrame) throws LvlOutOfBoundsException
    {
        Fase fase = null;
        
        switch(lvl)//Seletor de level
        {
            case 1:
                fase = new Fase_1(mainFrame);
                break;
            
            case 2:
                fase = new Fase_2(mainFrame);
                break;
            
            case 3:
                fase = new Fase_3(mainFrame);
                break;
            
            default:
               throw (new LvlOutOfBoundsException(lvl));
        }
        
        //se um level foi escolhido, iniciamos o mapa 
        //iniciamos o levelPanel
        mainFrame.startLevelPanel(fase);
            
        //iniciamos a fase
        fase.start();
    }
}
