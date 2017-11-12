package gridObject.movement;

/**
 * MoveThread eh um classe abstrata para utilizarmos de referencia para
 * NaveThread e WallThread, manipulamos elas por polimorfismo por meio desta classe
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public abstract class MoveThread extends Thread
{
    //Define a constante de frames por segundo
    public static final int FPS = 30;
}
