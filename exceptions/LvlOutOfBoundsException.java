package exceptions;

import java.lang.RuntimeException;

/**
 * Excecao de Level Inexistente
 * Ocorre quando tentamos utilizar um nivel ainda nao definido
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class LvlOutOfBoundsException extends RuntimeException
{
    /**
     * Constructor para excecao LvlOutOfBoundsException
     */
    public LvlOutOfBoundsException(int lvl)
    {
        super("ERRO: Level " + lvl + " inexistente!");
    }
}
