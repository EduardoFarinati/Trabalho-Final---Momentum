package gridObject.movement;

import javax.swing.ImageIcon;
 
/**
 * Sprite - Todos os objetos na grid devem ter uma Sprite
 * e possivel adicionar um icone a essa sprite ou varios dependendo de como o objeto se comporta
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class Sprite { 
    //vetor de imagens da sprite
    private ImageIcon icons[];
    
    public Sprite(ImageIcon[] icone){
        //inicializamos o array com o numero necessario
        icons = new ImageIcon[icone.length];
        
        //igualamos os dois arrays
        for(int i = 0; i < icone.length; i++)
        {
            icons[i] = icone[i];
        }
    }
    public Sprite(ImageIcon icone)
    {
        //utilizamos esse construtor para objetos com apenas uma orientacao
        icons = new ImageIcon[1];
        
        icons[0] = icone;
    }
    
    public ImageIcon getImageIcon(int orientacao){
        //retornamos o imageIcon da posicao desejada
        if(icons.length > 1)
        {
            if(orientacao < 360)
            {
                //calculamos a posicao do array
                int arrPos = (int)orientacao/15;//angulos de 15 em 15
            
                //retornamos o respectivo icone
                return icons[arrPos];
            }
            else
            {
                System.err.println("Erro, Sprite de angulo " + orientacao + " nao encontrada!");
                
                //retornamos uma imagem qualquer do array
                return icons[0];
            }
        }
        else
            return icons[0];
    }
    public ImageIcon getImageIcon(){
        //retornamos o imageIcon
        if(icons.length > 1)
        {
           System.err.println("Erro, Esse Objeto possui multiplas Sprites!");
           
           return null;
        }
        else
            return icons[0];
    }
}