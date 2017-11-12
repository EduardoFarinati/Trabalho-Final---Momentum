package gridObject;

import gridObject.movement.Sprite;
import gridObject.movement.Vector;

/**
 * Nave na grid, podemos especializa-la para powerups especificos da nave escolhida
 * Abstrata, so podemos instanciar suas subclasses
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public abstract class Nave extends GridObject implements Dinamico
{ 
    private final int acel = 1;//definicao da constante de aceleracao, arbitramos 1
    
    // Variaveis especificas de uma nave no jogo
    private int initX, initY;//posicao inicial da nave na grid
    private int initOrient;//orientacao inicial
    private Vector vel; //velocidade, na sua forma vetorial definido pela classe
    private boolean shielded;//varivel se a nave esta protegida contra impactos com paredes
    private boolean cooldown;//se o especial da nave esta em cooldown

    /**
     * Construtor de objetos tipo Nave polimorfico
     * podemos fornecer a velocidade inicial ou iniciar como zero
     * podemos fornecer o angulo inicial ou iniciar como 90
     */
    public Nave(int x, int y, int largura, int altura)
    {
        super(x, y, largura, altura); //utilizamos o construtor de GridObject para inicializar a posicao
        
        //salvamos a posicao inicial
        initX = x; 
        initY = y;
        
        //initOrient sera a padrao
        initOrient = super.getOrientacao();
        
        //inicializamos a velocidade com o vetor nulo
        vel = new Vector();
        
        //setamos shielded como false
        shielded = false;
        
        //setamos que o especial esta fora de cooldown
        cooldown = false;
    }
    public Nave(int x, int y, int largura, int altura, int Vx, int Vy)
    {
        super(x, y, largura, altura);
        
        //salvamos a posicao inicial
        initX = x; 
        initY = y;
        
        //initOrient sera a padrao
        initOrient = super.getOrientacao();
        
        //inicializamos a velocidade com o vetor fornecido
        vel = new Vector(Vx, Vy);
        
        //setamos shielded como false
        shielded = false;
        
        //setamos que o especial esta fora de cooldown
        cooldown = false;
    }
     public Nave(int x, int y, int largura, int altura, Vector velocidade)
    {
        super(x, y, largura, altura);
        
        //salvamos a posicao inicial
        initX = x; 
        initY = y;
        
        //initOrient sera a padrao
        initOrient = super.getOrientacao();
        
        //inicializamos a velocidade com o vetor fornecido
        vel = velocidade;
        
        //setamos shielded como false
        shielded = false;
        
        //setamos que o especial esta fora de cooldown
        cooldown = false;
    }
    public Nave(int x, int y, int largura, int altura, int orientacao)
    {
        super(x, y, largura, altura, orientacao); //utilizamos o construtor de GridObject para inicializar a posicao
        
        //salvamos a posicao inicial
        initX = x; 
        initY = y;
        
        //initOrient sera a fornecida
        initOrient = orientacao;
        
        //inicializamos a velocidade com o vetor nulo
        vel = new Vector();
        
        //setamos shielded como false
        shielded = false;
        
        //setamos que o especial esta fora de cooldown
        cooldown = false;
    }
    public Nave(int x, int y, int largura, int altura, int orientacao, int Vx, int Vy)
    {
        super(x, y, largura, altura, orientacao);
        
        //salvamos a posicao inicial
        initX = x; 
        initY = y;
        
        //initOrient sera a fornecida
        initOrient = orientacao;
        
        //inicializamos a velocidade com o vetor fornecido
        vel = new Vector(Vx, Vy);
        
        //setamos shielded como false
        shielded = false;
        
        //setamos que o especial esta fora de cooldown
        cooldown = false;
    }
     public Nave(int x, int y, int largura, int altura, int orientacao, Vector velocidade)
    {
        super(x, y, largura, altura, orientacao);
        
        //salvamos a posicao inicial
        initX = x; 
        initY = y;
        
        //initOrient sera a fornecida
        initOrient = orientacao;
        
        //inicializamos a velocidade com o vetor fornecido
        vel = velocidade;
        
        //setamos shielded como false
        shielded = false;
        
        //setamos que o especial esta fora de cooldown
        cooldown = false;
    }
   
    //retornamos os valores iniciais da nave
    public int getInitX()
    {
        return initX;
    }
    public int getInitY()
    {
        return initY;
    }
    public int getInitOrient()
    {
        return initOrient;
    }
    public void setVel(Vector vel)//seta a velocidade
    {
        this.vel = vel;
    }
    public Vector getVel()//retorna a velocidade, sobreescreve o metodo abstrato da interface dinamico
    {
        return vel;
    }
    public boolean getShielded()//retorna se a nave esta protegida ou nao
    {
        return shielded;
    }
    public void setShielded(boolean shield)
    {
        //setamos como protegido ou nao
        shielded = shield;
    }
    protected boolean getCooldown()//retorna se o especial da nave esta em cooldown
    {
        return cooldown;
    }
    public void setCooldown(boolean coolD)//setamos o cooldown
    {
        cooldown = coolD;
    }
    public void accelerate()//modificamos a velocidade por uma aceleracao
    {
        //utilizamos a constante de movimento da nave e a orientacao definida em gridObject
        vel.changeVel(acel, super.getOrientacao());
    }
    public void rotate(boolean sentido)
    {
        super.rotate(15, sentido);//fazemos uma rotacao padrao de 15 graus para a nave
    }
    public abstract void especial();//cada nave possui um especial, deve obrigatoriamente sobrescrever esse metodo
}
