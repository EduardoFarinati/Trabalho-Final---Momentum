package exceptions;

import java.lang.RuntimeException;

/**
 * InvalidTimerState - excecao de estado do timer de level invalido
 * tentamos iniciar o timer com ele ja iniciado
 * ou pausar com ele parado
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class InvalidTimerState extends RuntimeException
{
    /**
     * Constructor para a excecao InvalidTimerState
     */
    public InvalidTimerState(String acao, String running)
    {
        super("ERRO: timer nao foi possivel realizar acao " + ", pois " + running + " rodando");
    }
}
