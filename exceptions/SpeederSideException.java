package exceptions;


/**
 * SpeederException ocorre quando um speeder e colocado em uma orientacao invalida
 * apenas podemos multiplos de 90 graus
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class SpeederSideException extends SideException
{
    public SpeederSideException(int x, int y)
    {
        super("speeder", x, y);
    }
}
