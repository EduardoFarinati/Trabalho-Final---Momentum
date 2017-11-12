package gridObject;

import ui.LevelPanel;
import ui.resources.ResourceLoader;
import gridObject.movement.Sprite;

/**
 * Warp - Ponto de chegada, warp para o proximo level
 * tem uma animacao, definida por 9 sprites
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class Warp extends Obstaculo implements Animado
{
    //essa variavel serve para sabermos em que frame da animacao estamos
    private int animatedFrame;
    
    //ultimo frame da animacao e salvo nessa constante 
    private final int MAXFRAME = 9;
    
    //tempo de pausa entre cada frame
    private final int SLEEP = 35;
    
    //se devemos mudar o frame
    private boolean sleep;
    
    //resourceLoader para carregarmos os frames da animacao
    ResourceLoader resourceLoader;
    
    /**
     * Constructor de Warp usa o construtor de GridObject
     * Warp deve ter uma posicao padrao para cada mapa
     */
    public Warp(int x, int y, ResourceLoader resourceLoader)
    {
        super(x, y, 50, 90);//setamos a posicao e o tamanho 
        
        //setamos como o primeiro frame da animacao
        animatedFrame = 1;
        
        //setamos a sprite como o primeiro frame
        super.setSprite(new Sprite(resourceLoader.getWarp(animatedFrame)));
        
        //setamos que devemos modificar a sprite
        sleep = false;
        
        //salvamos o resourceLoader para usarmos na animacao
        this.resourceLoader = resourceLoader;
    }
    /**
     * colisao - Damos override no metodo de colisao para definir o que ocorre apos uma colisao
     */
    @Override
    public void colisao(Nave nave, LevelPanel levelP)
    {
        //tocamos o som
        ResourceLoader.playSound("ui/resources/Audio/Portal.wav", -5f);
        
        //Terminamos o nivel, foi completado
        levelP.endLevel();
    }
    /**
     * Animate - modifica a sprite para animarmos o gridObject
     */
    public void animate()
    {
        //testa se e o momento para modificar a sprite
        if(!sleep)
        {
            if(animatedFrame <= MAXFRAME)
            {   
                //manda o frame que deve ser pintado agora
                super.setSprite(new Sprite(resourceLoader.getWarp(animatedFrame)));
                
                //mudamos o animatedFrame
                animatedFrame++;
                
                //setamos a thread do sleep da animacao
                Thread sleepThread = new Thread(){
                    @Override
                    public void run()
                    {   
                        //iniciamos o sleep
                        setSleep(true);
                        
                        try{
                            //setamos o tempo de cooldown
                            this.sleep(SLEEP);
                        }   
                        catch (InterruptedException e)
                        {
                            System.err.println("SLEEPTHREAD INTERROMPIDA, ERRO NA TROCA DE FRAMES!");      
                        }
                    
                        //paramos o sleep
                        setSleep(false);
                    }
                };
            
                //iniciamos a thread
                sleepThread.start();
            }
            else
            {
                //retornamos ao primeiro frame
                animatedFrame = 1;
            }
        }
    }
    //setamos se esta em modo de sleep
    protected void setSleep(boolean set)
    {
        sleep = set;
    }
}
