package gridObject;

import ui.LevelPanel;

/**
 * Obstaculos da nave na grid
 * Devem estender dessa classe abstrata e sobrescrever o metodo "colisao()"
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public abstract class Obstaculo extends GridObject
{
    public Obstaculo(int x, int y, int width, int height)
    {
        //repassamos para o construtor de GridObject
        super(x, y, width, height);
    }
    public abstract void colisao(Nave nave, LevelPanel levelP);
}
