package gridObject;


/**
 * Animado - Objetos com animacao devem implementar essa interface para aparecerem na grid
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public interface Animado
{
    /**
     * Esse metodo deve modificar a sprite retornada pelo objeto animado
     */
    public abstract void animate();
}
