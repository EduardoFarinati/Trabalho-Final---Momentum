package gridObject;

import gridObject.movement.Vector;

/**
 * Dinamico define todo tipo de objeto dinamico na grid
 * 
 * define constantes de movimento e serve para o polimorfismo
 * 
 * @author Andre, Eduardo, Marcelo 
 * @version 0.0
 */
public interface Dinamico
{
     //Variaveis constantes
    public final int max_Spd = 100;//definicao do maximo de velocidade, arbitramos 100
    
    //metodos de movimento
    /**
     * Todo objeto dinamico deve ter uma variavel vel  e um metodo de retorno deste
     */
    public abstract Vector getVel();
    /**
     * Todo objeto dinamico deve ter uma variavel vel  e um metodo para podermos seta-lo
     */
    public void setVel(Vector vel);
    //A interface serve como uma referencia generica para nos referirmos as classes moveis
}
