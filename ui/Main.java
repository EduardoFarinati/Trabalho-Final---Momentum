package ui;

import ui.resources.ResourceLoader;

/**
 * Main Program
 * Inicializa a interface grafica e controla o programa pelo escopo mais externo
 * 
 *              Jogo Momentum 
 *                   por
 * 
 *         Andre Moura Perinazzo  - 00275640
 *         Eduardo Farinati Leite - 00275632
 *         Marcelo de Oliveira Monaretto - 00273183
 * 
 * @author Andre, Eduardo, Marcelo 
 * @version 0.0
 */
public interface Main
{
    public static void main(String args[])
    {        
        //inicializamos o ResourceLoader do jogo
        ResourceLoader resourceLoader = new ResourceLoader();
        
        //inicializar padroes graficos em ui
        MainFrame frame = new MainFrame(resourceLoader);
        
        //adicionamos um hook para desligar a musica quando o programa eh fechado
        Runtime.getRuntime().addShutdownHook(new Thread() {
             @Override    
             public void run() {
                 resourceLoader.terminateMusic();
             }
        });
        
        //iniciamos a parte grafica
        frame.startUi();
    }
}
