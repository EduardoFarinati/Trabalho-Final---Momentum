package exceptions;

import java.lang.RuntimeException;

/**
 * SideException ocorre quando uma posicao invalida e escolhida pra um objeto
 * 
 * @author Andre, Eduardo, Marcelo 
 * @version 0.0
 */
public class SideException extends RuntimeException
{
    public SideException(String obj, int x, int y)
    {
        super("Erro: Orientacao do objeto + " + obj + ", em (" + x + "," + y + ")" + " invalida!");
    }
}
