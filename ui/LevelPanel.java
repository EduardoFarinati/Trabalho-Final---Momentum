package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Thread;
import java.text.DecimalFormat;
import javax.swing.border.MatteBorder;

import fases.Fase;
import gridObject.movement.*;
import gridObject.GridObject;
import gridObject.Dinamico;
import gridObject.Animado;


/**
 * LevelPanel e o painel ativo durante do gameplay
 * ele implementa keylistener pois deve ser capaz de pausar o jogo
 * 
 * @author Andre, Eduardo, Marcelo 
 * @version 0.0
 */
public class LevelPanel extends JPanel implements KeyListener
{
    //Variaveis que salvamos para comparar se o jogo esta rodando
    private volatile boolean running;
    private volatile boolean paused;
    //constante para o sincronismo do pause
    public final Object pauseLock = new Object();
    
    //referencia a fase rodando
    private Fase fase;
    
    //referencia para o mainFrame
    private MainFrame mainFrame;
    
    //referencia para o painel de scores
    private HighscoresPanel scorePanel;
    
    //fundo da tela e um icone salvo em um JLabel
    private BackgroundLabel bg;
    
    //setamos o menu de pause
    private JPanel pause;
    
    //JLabel do timing do jogo
    private JLabel timerL;
    
    /**
     * Constructor for objects of class LevelPanel
     */
    public LevelPanel(MainFrame mainFrame, HighscoresPanel scorePanel)
    {
        //iniciamos o mainFrame
        this.mainFrame = mainFrame;
        
        //iniciamos o scorePanel
        this.scorePanel = scorePanel;
        
        //pegamos o tamanho
        Rectangle size = mainFrame.getBounds();
        
        //setamos o tamanho de levelpanel
        this.setPreferredSize(new Dimension((int)size.getWidth(), (int)size.getHeight()));
        
        //setamos o layout como null para podermos posicionar os componentes como quisermos
        this.setLayout(null);
        
        //insets sao a borda do nosso pane
        Insets insets = mainFrame.getContentPane().getInsets();
        
        //instanciamos o JLabel do timer, ficara no canto superior direito da tela
        timerL = new JLabel("00.00", SwingConstants.CENTER);
        //mostrara o timing em meio ao jogo
        
        //setamos sua fonte
        timerL.setFont(mainFrame.getResourceLoader().getDirtyEgo().deriveFont(35f));
        
        //setamos sua posicao
        timerL.setBounds(insets.left+1071, insets.top, 163, 74);
        
        //setamos a cor do texto
        timerL.setForeground(new Color(220, 37, 41));
        
        //setamos sua borda
        timerL.setBorder(BorderFactory.createLineBorder(Color.darkGray, 3));
        
        //o adicionamos ao panel
        this.add(timerL);
        
        //inciamos o painel de pausado(invisivel ate pausarmos)
        pause = new JPanel();
        
        //como o layout e null, setamos o tamanho
        pause.setBounds(insets.left, insets.top, (int)size.getWidth(), (int)size.getHeight());
        
        //iniciamos um box layout vertical para comportar os componentes
        pause.setLayout(new BoxLayout(pause, BoxLayout.PAGE_AXIS));
        
        //setamos sua cor de fundo
        pause.setBackground(new Color(89, 89, 89, 150));
        //recebe um cinza escuro, com seu alpha baixo
        
        //Criamos o JLabel
        JLabel paused_label = new JLabel("Jogo Pausado", SwingConstants.CENTER);
        
        //setamos sua fonte
        Font font = mainFrame.getResourceLoader().getDepletedUranium().deriveFont(55f);
        paused_label.setFont(font);
        
        //setamos seu alinhasmento
        paused_label.setAlignmentX(CENTER_ALIGNMENT);
        
        //setamos sua cor
        paused_label.setForeground(Color.white);
        
        //setamos sua borda
        paused_label.setBorder(new MatteBorder(5,0,8,0,Color.white));
        
        //iniciamos JButton
        JButton btn = new JButton("           Quit           ");
        
        //setamos o Botao para nao Focavel para evitarmos clicks acidentais
        btn.setFocusable(false);
        
        //adicionamos o ActionListener ao botao
        btn.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                //paramos o running da fase
                running = false;
                //despausamos o jogo para n retornar pausado
                pause();
                
                //removemos o keyListener das setas do panel
                for(KeyListener i : getKeyListeners())
                {
                    if(i instanceof SetasListener)
                    {
                        removeKeyListener(i);
                    }
                }
                
                //removemos os gridObjects do background
                for(Component i : bg.getComponents())
                {
                    if(i instanceof GridObject)
                    {
                        bg.remove(i);
                    }
                }
                
                //paramos o timer
                fase.stopTimer();
                
                //setamos que e possivel dar resize
                mainFrame.setResizable(true);
                
                //iniciamos o menu opcoes
                mainFrame.startMenuOpcoes();
            } 
        });
        
        //setamos sua fonte
        btn.setFont(mainFrame.getResourceLoader().getDirtyEgo().deriveFont(50f));
        
        //setamos seu alinhasmento
        btn.setAlignmentX(CENTER_ALIGNMENT);
        
        //setamos sua cor
        btn.setForeground(new Color(220, 37, 41));
        
        //setamos a cor do seu fundo
        btn.setBackground(Color.black);
        
        //setamos o tamanho preferivel
        btn.setPreferredSize(new Dimension(300, 300));
        
        //adicionamos os componentes ao panel
        pause.add(new Box.Filler(new Dimension(1,60), new Dimension(1, 190), new Dimension(1, 220)));
        pause.add(paused_label);
        pause.add(new Box.Filler(new Dimension(1,60), new Dimension(1, 90), new Dimension(1, 90)));
        pause.add(btn);
        
        //setamos o panel primeiramente como invisivel
        pause.setVisible(false);
        
        //setamos como nao focavel
        pause.setFocusable(false);
        
        //adicionamos o pause a esse panel
        this.add(pause);
        
        //Instanciamos o fundo
        bg = new BackgroundLabel((mainFrame.getResourceLoader()).getBackgroundTexture());
        
        //como o layout e null, setamos o tamanho de bg
        bg.setBounds(insets.left, insets.top, (int)size.getWidth(), (int)size.getHeight());
        
        //setamos que esta no fundo
        this.setComponentZOrder(bg, 0);
        
        //adicionamos ao panel
        this.add(bg);
        
        //adicionamos essa propria classe como keylistener
        this.addKeyListener(this);
        
        //setamos a cor de fundo
        this.setBackground(Color.black);
        
        //no final do construtor, setamos paused como false;
        paused = false;
        
        //e running como true
        running = false;
    }
    
    /**
     * As Threads se utilizam de isRunning para testar se o level ainda esta rodando
     * 
     */
    public boolean isRunning()
    {
        return running;
    }
    /**
     * As Threads se utilizam de isPaused para testar se o level esta pausado
     * 
     */
    public boolean isPaused()
    {
        return paused;
    }
    
    public void setFase(Fase fase)
    {
        this.fase = fase;
        //quando a fase e setada iniciamos o running
        running = true;
    }
    
    /**
     * resetFaseTimer - reseta o timer da fase
     * 
     */
    public void resetFaseTimer()
    {
        //reiniciamos o timer da fase
        fase.restartTimer();
    }
    
    public void endLevel()
    {
        //paramos o timer de fase
        fase.stopTimer();
        
        //mandamos o tempo do nivel para scorePanel para testamos se é um highscore
        scorePanel.testaScore(fase.getRunTime(), fase.getLevelNum());
        
        //removemos o keyListener das setas do panel
        for(KeyListener i : this.getKeyListeners())
        {
            if(i instanceof SetasListener)
            {
                this.removeKeyListener(i);
            }
        }
                
        //removemos os gridObjects do background
        for(Component i : bg.getComponents())
        {
            if(i instanceof GridObject)
            {
                bg.remove(i);
            }
        }

        //zeramos fase
        this.fase = null;
        
        //setamos que nao esta rodando
        running = false;
        
        //setamos como resizable
        mainFrame.setResizable(true);
        
        //iniciamos o lvlSelect novamente
        mainFrame.startLevelSelectPanel();
    }
    
    /**
     * Sobresecreve todos metodos necessarios para pausar o jogo
     */    
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            //pausamos o jogo se o usuario aperta esc
            this.pause();
            this.repaint();
        }
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }
    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }
    /**
     * responsavel por pausar o jogo
     * 
     */
    private synchronized void pause()
    {
        //se nao esta pausado
        if(!paused)
        {
            //setamos a variavel como true para parar as threads
            paused = true;
            
            //pausamos o timer de fase
            fase.pauseTimer();
            
            //setamos o pause screen como visivel
            pause.setVisible(true);
        }
        else//se esta pausado devemos retornar (resume)
        {
            //sincronizamos com o pause para despausar todas as threads
            synchronized(pauseLock)
            {
                //setamos o pause screen como invisivel
                pause.setVisible(false);
                
                //setamos a variavel de pausado como false
                paused = false;
                
                //notificamos o pause para as threads
                pauseLock.notifyAll();
                
                //despausamos o timer
                fase.startTimer();
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        //demos override no metodo de pintar, para podermos pintar nosso timer
        //repintamos
        super.paintComponent(g);
            
        //iniciamos o formatador de decimais
        DecimalFormat df = new DecimalFormat("0.00");
        //sempre devem conter 3 digitos
            
        //mandamos a string formatada
        timerL.setText(df.format(fase.getRunTime()));
    }
    /**
     * Metodos responsaveis pelo controle das threads
     */
    public void setGridObjects(GridObject[] estatico, Dinamico[] dinamico, Animado[] animated)
    {   
        //setamos os objetos no background
        bg.setObjects(dinamico, animated);
        int k=0;
        //adicionamos o array estatico ao background
        for(GridObject i : estatico)
        {
            if(!(i instanceof Dinamico))
            {
                //Usamos GridObject como JLabel(GridObject estende JLabel)
                i.setIcon((i.getSprite()).getImageIcon());//setamos seu fundo
            
                //pegamos a hitbox de rectangle
                Rectangle hitbox = i.getHitbox();
            
                //setamos o tamanho preferivel como do objeto
                i.setSize(new Dimension((int)hitbox.getWidth(), (int)hitbox.getHeight()));
            
                //setamos sua posicao
                i.setLocation((int)hitbox.getX(), (int)hitbox.getY());
            
                //e o adicionamos
                bg.add(i);
            }
        }
        
        //setamos o running como true para as threads
        running = true;
    }
}
