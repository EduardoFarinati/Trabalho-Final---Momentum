package exceptions;

import java.lang.RuntimeException;

/**
 * UnsetObjectsException - eh lancada quando tentamos iniciar um nivel
 * sem, primeiramente, setar os objetos no componente
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class UnsetObjectsException extends RuntimeException
{
    /**
     *Constructor para excecao UnsetObjectsException
     */
    public UnsetObjectsException()
    {
        super("ERRO: Objetos nao setados, jogo nao pode continuar!");    
    }
}
