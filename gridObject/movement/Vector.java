package gridObject.movement;

/**
 * Vector é o vetor da velocidade do jogo
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class Vector
{
    //constante de movimento delimitadora da velocidade maxima
    private static final int velMax = 20;
    
    // Atributos de vector sao a velocidade na sua forma vetorial
    private int Vx;//unidades em x
    private int Vy;//unidades em y
    
    //variveis auxiliares para acumularmos o x e y
    private double Deci_X;
    private double Deci_Y;
    
    /**
     * Constructor de Vector - Polimorfico
     * inicializa ou com o vetor nulo ou com uma velocidade fornecida
     */
    public Vector()//vetor nulo
    {
        Vx = 0;
        Vy = 0;
        //iniciamos os decimais
        Deci_X = 0;
        Deci_Y = 0;
    }
    public Vector(int Vx, int Vy)//V fornecida
    {
        this.Vx = Vx;
        this.Vy = Vy;
        
        //iniciamos os decimais
        Deci_X = 0;
        Deci_Y = 0;
    }

    /**
     * changeVel - muda a intensidade em x e/ou em y do vetor
     * 
     * @param  acel aceleracao recebida
     */
    public void changeVel(int acel, int orientacao)//utiliza senos e cosseno para calcular
    {
        //calculamos calc
        double calc = Math.cos(Math.toRadians(orientacao));
        
        //iniciamos o decimal
        Deci_X += calc - (int)calc;
        
        //damos uma tolereancia de 0.001
        if(Math.abs(Deci_X) < 0.001)
        {
            Deci_X = 0;
        }
        
        //somamos o inteiro a Vx
        if(Math.abs(Vx+(int)(acel*calc))<=velMax)
        {
            Vx += (int)(acel * calc);
        }
        else
        {
            Vx = (int)(Math.signum(Vx)*velMax);
        }
        
        //trocamos calc para y, medimos o zero em cima entao, deve ser negativado
        calc = -(Math.sin(Math.toRadians(orientacao)));
        
        //calculamos o decimal
        Deci_Y += calc - (int)calc;
        
        
        //damos uma tolereancia de 0.001
        if(Math.abs(Deci_Y) < 0.001)
        {
            Deci_Y = 0;
        }
        
        //somamos o inteiro a Vy
        if((Math.abs(Vy+(int)(acel*calc)))<=velMax)
        {
            Vy += (int)(acel * calc);
        }
        else
        {
            //setamos no mesmo sinal
            Vy = (int)(Math.signum(Vy)*velMax);
        }
        
        
        if(Deci_X >= 1 || Deci_X <= -1)
        {
            //somamos a parte inteira
            Vx += (int)Deci_X;
            
            //subtraimos de decimal
            Deci_X -= (int)Deci_X;
        }
        if(Deci_Y >= 1 || Deci_Y <= -1)
        {
            //somamos a parte inteira
            Vy += (int)Deci_Y;
            
            //subtraimos de decimal
            Deci_Y -= (int)Deci_Y;
        }
    }
    //getters da velocidades
    public int getVx()
    {
        return Vx;
    }
    public int getVy()
    {
        return Vy;
    }
}
