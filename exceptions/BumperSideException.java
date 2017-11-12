package exceptions;

/**
 * BumperSideException ocorre quando setamos uma orientacao invalida para o bumper
 * apenas podemos multiplos de 90 graus
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class BumperSideException extends SideException
{
    public BumperSideException(int x, int y)
    {
        super("bumper", x, y);
    }
}
