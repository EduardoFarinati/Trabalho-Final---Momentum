package ui;

import gridObject.movement.*;
import fases.Fase;
import ui.resources.ResourceLoader;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.Graphics.*;
import javax.sound.sampled.*;
import java.util.logging.*;
import java.util.Locale;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * MainFrame e o container principal do programa
 * a "janela"
 * 
 * controla todos os paineis graficos
 * 
 * @author Andre, Eduardo, Marcelo
 * @version 0.0
 */
public class MainFrame extends JFrame
{
    //Usamos um CardLayout para facilitar a troca entre os panels
    private JPanel switchPanel;
    private CardLayout c;
    private JLabel menuScreen;
    
    
    //inicializamos o panel principal
    private JPanel menuOpcoes;
    //inicializamos os panels auxiliares
    private HighscoresPanel scores;
    private LevelSelectPanel lvlSelect;
    private LevelPanel lvlPanel;
    //inicializamos as strings de operacao do cardLayout
    private static final String MENU = "Card Menu", SCORES = "Card Highscores", LVLSELECT = "Card LvlSelect", LVL = "Card Lvl";
    
    //inicializamos os botoes
    private JButton start, highscores, exit;
    //inicializo uma serie de label's para que eu possa preencher a tela do menu e depois fragmenta-la em grid's
    private JLabel labelVazio[];
    
    //carregador de recursos utilizados pelo jogo
    ResourceLoader resourceLoader;
    
    /**
     * Construtor de MainFrame, baseado no de JFrame, por meio de super
     */
    public MainFrame(ResourceLoader resourceLoader)
    {
        // inicializamos o construtor de JFrame com o nome da janela
        super("Momentum - Java");
        
        //recebemos o ResourceLoader
        this.resourceLoader = resourceLoader;
        
        //configuramos nosso frame
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//apenas definindo para ser fechado 
        this.setSize(new Dimension(1250,750));//seta no tamanho preferivel do frame
        this.setLocationRelativeTo(null);//centraliza o frame na tela
        this.setVisible(true);//seta como visivel
        this.setLayout(new GridLayout(0,1));
        
        //setamos o local padrao
        Locale.setDefault(Locale.US);
    }
    

    /**
     * startUi - inicializa o frame principal e torna-o visivel
     */
    public void startUi()// inicializa toda interface grafica
    {    
        //Inicializamos os botoes com seus nomes e icones
        start = new JButton(resourceLoader.getStartIcon());
        highscores = new JButton(resourceLoader.getHighscoresIcon());
        exit = new JButton(resourceLoader.getExitIcon());

        //iniciamos o array de Labels vazios
        labelVazio = new JLabel[9];
        for(int i = 0; i < labelVazio.length; i++)
        {
            labelVazio[i] = new JLabel();
        }
        
        //eu crio uma grid dentro do Label para que eu possa usar areas especificas do menu, contidas nessa grid
        labelVazio[2].setLayout(new GridLayout(0,2)); //utilizamos estes labels espeficicos para isso
        labelVazio[6].setLayout(new GridLayout(0,2));
        labelVazio[7].setLayout(new GridLayout(0,2)); 
        
        //setamos o tamanho preferivel dos botoes
        start.setPreferredSize(new Dimension(30, 50));
        highscores.setPreferredSize(new Dimension(30, 50));
        exit.setPreferredSize(new Dimension(30, 50));
        
        //setamos a cor dos botoes
        start.setBackground(Color.black);
        highscores.setBackground(Color.black);
        exit.setBackground(Color.black);
        
        //temos de adicionar os action listeners aos botoes, para isso usamos classes anonimas
        start.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                startLevelSelectPanel();
            } 
        } );
        highscores.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                startHighscoresPanel();
            } 
        } );
        exit.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                System.exit(0);//fecha o programa
            } 
        } );
        
        //setamos os botoes como nao focaveis
        start.setFocusable(false);
        highscores.setFocusable(false);
        exit.setFocusable(false);
        
        //Inicializamos aqui um panel vertical para adicionarmos os botoes
        menuOpcoes = new JPanel();
        menuOpcoes.setPreferredSize(new Dimension((int)this.getBounds().getWidth(),(int)this.getBounds().getHeight()));

        menuOpcoes.setLayout(new GridBagLayout());//seta o layout do panel menuOpcoes
        
        //inicializamos o painel
        menuScreen = new JLabel();
        //configuramos o panel
        menuScreen.setOpaque(true);
        menuScreen.setIcon(resourceLoader.getBackgroundIcon());
        menuScreen.setPreferredSize(new Dimension(20,20));
        menuScreen.setBackground(Color.black);
        
        menuScreen.setLayout(new GridLayout(0,2));
        menuScreen.add(labelVazio[0]);
        menuScreen.add(labelVazio[1]);
        //labelVazio[2].setBackground(Color.black);
        labelVazio[2].add(labelVazio[3]);
        labelVazio[2].add(labelVazio[4]);
        labelVazio[2].add(start);  
        labelVazio[2].add(labelVazio[5]);
        menuScreen.add(labelVazio[2]);
        menuScreen.add(labelVazio[6]);
        menuScreen.add(labelVazio[7]);
        //labelVazio[7].setLayout (new GridLayout (0,2));
        labelVazio[7].add(highscores);
        labelVazio[7].add(labelVazio[8]);
        labelVazio[7].add(exit);
       
        //inicializamos os panels para cada opcao
        scores = new HighscoresPanel(this);
        lvlSelect = new LevelSelectPanel(this);
        lvlPanel = new LevelPanel(this, scores);//levelPanel recebe o highscore panel para interagir pro highscore
        
        //criamos um panel com cardlayout para a troca entre os panels
        c = new CardLayout();
        switchPanel = new JPanel(c);//recebe o CardLayout no construtor
        switchPanel.add(menuScreen, MENU);//adicionamos o menu principal ao panel
        switchPanel.add(scores, SCORES);
        switchPanel.add(lvlSelect, LVLSELECT);
        switchPanel.add(lvlPanel, LVL);
        
        //por ultimo adicionamos a propria frame o switchPanel
        this.setContentPane(switchPanel);
        
        try{  //tenta carregar o icone do jogo 
            this.setIconImage(ImageIO.read(new File("ui/Resources/Icon.png")));//carrega a imagem
            //vale lembrar que o caminho fornecido deve vir do codigo base, no caso
            //devemos entrar no folder ui e img para encontrar o icone
        }
        catch(IOException exc){ //caso de falha exibimos uma mensagem de erro
            System.err.println("Erro na Procura do arquivo de ícone!");
            exc.printStackTrace(); 
        }
        
        try 
        {
            resourceLoader.playMusic("ui/Resources/Audio/Theme.wav", -(float)5.0);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        //repintamos o painel apos modifica-lo
        this.revalidate();
        this.repaint();
    }
    
    /**
     * startLevelPanel - inicializa o panel de selecao de level
     */
    public void startLevelSelectPanel() 
    {
        c.show(switchPanel, LVLSELECT);//mudamos para o panel desejado
    }
    /**
     * startHighscoresPanel - inicializa o panel de selecao de level
     */
    public void startHighscoresPanel()
    {
        c.show(switchPanel, SCORES);//mudamos para o panel desejado
        //setamos a variavel do blinke e iniciamos as threads
        scores.setBlinking(true);
    }
    /**
     * startMenuOpcoes - inicializa o panel principal
     */
    public void startMenuOpcoes()
    {
       c.show(switchPanel, MENU);//mudamos para o panel desejado
        
       //trocamos a musica para a musica tema
       resourceLoader.changeMusic("ui/Resources/Audio/Theme.wav", -5.0f);
    }
    /**
     * startMenuOpcoes - inicializa o panel principal sem inicializar a musica
     */
    public void startMenuOpcoesNoMusicChange()
    {
        c.show(switchPanel, MENU);//mudamos para o panel desejado
    }
    /**
     * startLevelPanel - inicializa o panel de um level em especifico 
     */
    public void startLevelPanel(Fase fase)
    {   
        c.show(switchPanel, LVL);//mudamos para o panel desejado
        
        //setamos a fase no level
        lvlPanel.setFase(fase);
        
        //quando iniciamos o levelPanel devemos evitar o resize, logo
        this.setResizable(false);
        
        //exigimos focus no level Panel
        lvlPanel.requestFocus();
        
        //setamos o painel como focavel
        lvlPanel.setFocusable(true);
        
        //trocamos a musica
        resourceLoader.changeMusic("ui/Resources/Audio/Fases.wav", -5.0f);
    }
    /**
     * getLevelPanel - retorna o panel do lvl 
     */
    public LevelPanel getLevelPanel()
    {        
        return lvlPanel;
    }
    public ResourceLoader getResourceLoader()
    {
        return resourceLoader;
    }
}
