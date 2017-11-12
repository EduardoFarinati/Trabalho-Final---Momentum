package exceptions;

import java.lang.RuntimeException;

/**
 * InvalidAngleException ocorre quando setamos um angulo invalido e fornecido
 * angulos invalidos dependem da inplementacao na propria classe que se utiliza de
 * InvalidAngleException
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class InvalidAngleException extends RuntimeException
{
    /**
     * Constructor para a excecao InvalidAngleException
     */
    public InvalidAngleException(int angle)
    {
       super("Angulo Invalido: + " + angle);
    }
}
