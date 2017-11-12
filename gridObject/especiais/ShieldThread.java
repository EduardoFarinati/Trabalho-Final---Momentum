package gridObject.especiais;

import gridObject.Nave;
import gridObject.movement.Sprite;
import ui.resources.ResourceLoader;

/**
 * ShieldThread protege a nave por algum tempo das paredes
 * Tempo definido pela constante DURATION
 * tempo para poder ser utilizado novamente pela constante COOLDOWN
 * 
 * @author Andre, Eduardo, Marcelo 
 * @version 0.0
 */
public class ShieldThread extends Thread
{
    //constantes da thread
    private final long COOLDOWN = 15000;//cooldown do especial
    private final int DURATION = 500;//duracao do especial
    
    Nave nave;//a nave que a thread deve cuidar
    ResourceLoader resourceLoader; //carregador de recursos
    
    /**
     * Construtor de ShieldThread recebe sua nave e o resourceLoader
     */
    public ShieldThread(Nave nave, ResourceLoader resourceLoader)
    {
        //salvamos a nave
        this.nave = nave;
        
        this.resourceLoader = resourceLoader;
    }
    
    //damos override e definimos o que a thread deve fazer
    @Override
    public void run()
    {
        //setamos que esta em cooldown
        nave.setCooldown(true);
        //setamos que esta protegida
        nave.setShielded(true);
        
        //trocamos sua sprite
        nave.setSprite(new Sprite(resourceLoader.getSpritesShieldedSC_Minotaur()));
        
        //tocamos o som
        ResourceLoader.playSound("ui/resources/Audio/Especial.wav", -10f);
                    
        //paramos a thread
        try{
            //setasmos o tempo de protecao
            this.sleep(DURATION);
        }   
        catch (InterruptedException e)
        {
            System.err.println("SHIELDTHREAD INTERROMPIDA, ERRO NO ESPECIAL!");
        }
                    
        //desligamos o shield
        nave.setShielded(false);
        //trocamos sua sprite
        nave.setSprite(new Sprite(resourceLoader.getSpritesSC_Minotaur()));
                    
        //paramos a thread novamente para esperar o cooldown
        try{
            //setamos o tempo de cooldown
            this.sleep(COOLDOWN);
        }   
        catch (InterruptedException e)
        {
            System.err.println("SHIELDTHREAD INTERROMPIDA, ERRO NO COOLDOWN!");
        }
                    
        //terminamos o cooldown
        nave.setCooldown(false);
    }
}

