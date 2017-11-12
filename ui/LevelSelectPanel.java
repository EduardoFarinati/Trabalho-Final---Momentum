package ui;

import ui.resources.ResourceLoader;
import java.awt.font.TextAttribute;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * LevelSelectPanel - Painel seletor de leveis
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class LevelSelectPanel extends JPanel
{
    //Botoes de LevelSelectPanel
    private JButton[] lvlButtons;
    private JButton exit;
    private ResourceLoader resourceLoader;
    
    //Label de escolha do lvl
    private JLabel escolhaLvl;
    
    //Label da imagem do fundo
    private JLabel bg;

    /**
     * Constructor de LevelSelectPanel inicia sua parte grafica
     */
    public LevelSelectPanel(MainFrame mainFrame)
    {
        //utilizamos o construtor de JPanel
        super();
        //iniciamos o resourceLoader
        this.resourceLoader = mainFrame.getResourceLoader();
        
        //setamos o layout
        this.setLayout(new BorderLayout());
        
        //iniciamos o backgroudn
        bg = new JLabel(resourceLoader.getBackgroundTexture());
        
        
        //Para o estilo do painel utilizaremos 2 layouts e 1 Panels internos
        BoxLayout boxL = new BoxLayout(bg, BoxLayout.PAGE_AXIS);
        FlowLayout flowL = new FlowLayout();
        JPanel flow = new JPanel(flowL);
        
        //iniciamos o JLabel
        escolhaLvl = new JLabel("Escolha o Level");
        
        //editamos a fonte para que seja sublinhada
        Map<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
        map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
       
        //editamos o JLabel
        escolhaLvl.setFont((resourceLoader.getDirtyEgo().deriveFont(map)).deriveFont(60f));
        escolhaLvl.setAlignmentX(CENTER_ALIGNMENT);
        escolhaLvl.setForeground(Color.white);
        
        //setamos o panel flow
        flow.setOpaque(false);
        
        //adicionamos ao BoxLayout, primeiramente um filler, dps o JLabel e outro filler
        bg.add(new Box.Filler(new Dimension(1,10), new Dimension(1, 55), new Dimension(1, 80)));
        //new Box.Filler(minSize, prefSize, maxSize)
        bg.add(escolhaLvl);
        bg.add(new Box.Filler(new Dimension(1,20), new Dimension(1, 75), new Dimension(1, 85)));
        
        //iniciamos os botoes
        lvlButtons = new JButton[fases.LvlSelect.LEVELNUM];
        exit = new JButton("            Retornar            ");
        
        //configuramos o botao de retorno
        exit.setFont(resourceLoader.getDepletedUranium().deriveFont(40f));
        exit.setBackground(Color.black);
        exit.setForeground(Color.white);
        exit.setPreferredSize(new Dimension(500,100));
        exit.setAlignmentX(CENTER_ALIGNMENT);
        exit.setFocusable(false);
        
        //Percorremos o array de botoes
        for(int i = 0; i < lvlButtons.length; i++)
        {
            //Precisamos de uma variavel constante para os action listeners, entao:
            final int j = i+1;
            //+1 correcao pois os levels sao positivos
            
            //iniciamos o botao com seu numero
            lvlButtons[i] = new JButton(resourceLoader.getLvlPanel(i));
            lvlButtons[i].setBackground(Color.black);
            lvlButtons[i].setForeground(Color.white);
            lvlButtons[i].setFocusable(false);
            
            //Adicionamos o action listener
            lvlButtons[i].addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    fases.LvlSelect.startLvl(j, mainFrame);//Mandamos uma mensagem para iniciar a fase 
                    //Utilizamos um metodo estatico de LvlSelect
                } 
            } );
            
            //adicionamos ao FlowLayout
            flow.add(lvlButtons[i]);
        }
        
        
        //adicionamos o action listener ao exit
        exit.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
               mainFrame.startMenuOpcoes();
            } 
        } );
        
        //adicionamos o flowLayout e o exit button ao boxLayout
        bg.add(flow);
        bg.add(exit);
        
        //setamos o fundo do panel
        this.setBackground(Color.black);
        
        //adicionamos o boxLayout ao background
        bg.setLayout(boxL);
        
        this.add(bg);
        
    }
}
