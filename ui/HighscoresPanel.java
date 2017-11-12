package ui;

import ui.resources.ResourceLoader;

import java.awt.*;
import java.awt.font.TextAttribute;
import javax.swing.*;
import java.lang.Math;
import java.text.DecimalFormat;
import java.lang.StringBuilder;
import java.util.Locale;
import java.nio.file.AccessDeniedException;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.Map;
import java.util.InputMismatchException;
import java.util.Hashtable;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;


//importamos a constante LVLNUM
import static fases.LvlSelect.LEVELNUM;

/**
 * Highscores Panel - Abre a o arquivo e ve os highscore de cada level
 * 
 * @author Andre, Eduardo, Marcelo 
 * @version 0.0
 */
public class HighscoresPanel extends JPanel
{
    //numero de scores salvos por nivel
    public static final int MAXSCORES = 3;
    
    //imagem teste
    private JLabel backG;
    
    // o arquivo a ser manipulado
    private File highscoresFile;
    
    //string com os highscores
    private String[] highscores;
    
    //Jlabels dos highscores
    private JLabel[] scoresOrdenados;
    
    //JPanels respectivos de cada fase
    private JPanel fase1, fase2, fase3;;
    
    //variavel da posicao do maior score de cada lvl
    private int[] topScores;
    
    //botao de saida
    private JButton exit;
    
    //variavel sobre o blink do panel
    private boolean blink;
    
    //variavel do mainframe
    private MainFrame mainFrame;

    /**
     * Construtor de HighscoresPanel
     */
    public HighscoresPanel(MainFrame mainFrame)
    {
        //utilizamos o construtor de JPanel
        super();
        
        //setamos o mainFrame
        this.mainFrame = mainFrame;
        
        //pegamos o carregador de recursos
        ResourceLoader resourceLoader = mainFrame.getResourceLoader();
        Border bordas = BorderFactory.createLineBorder(Color.white, 4);
        
        //JLabel para por imagem de fundo        
        backG = new JLabel();
        backG.setIcon(new ImageIcon("ui/background/HSImage.png"));
        backG.setPreferredSize(new Dimension(1250,750));
        backG.setAlignmentX(CENTER_ALIGNMENT);
        backG.setAlignmentY(CENTER_ALIGNMENT);
        
        
        fase1 = new JPanel();
        fase1.setLayout(new BoxLayout(fase1, BoxLayout.PAGE_AXIS));
        fase1.setBackground(Color.black);
        fase1.setAlignmentX(CENTER_ALIGNMENT);
        fase1.setMinimumSize(new Dimension(1000,200));
        fase1.setBorder(bordas);

        
        fase2 = new JPanel();
        fase2.setLayout(new BoxLayout(fase2, BoxLayout.PAGE_AXIS));
        fase2.setBackground(Color.black);
        //fase2.setPreferredSize(new Dimension());
        fase2.setBorder(bordas);
        
        fase3 = new JPanel();
        fase3.setLayout(new BoxLayout(fase3, BoxLayout.PAGE_AXIS));
        fase3.setBackground(Color.black);
        //fase3.setPreferredSize(new Dimension());
        fase3.setBorder(bordas);        
               
      
        //adicionamos um espacamento entre entre a parte superior e os Labels
        backG.add(new Box.Filler(new Dimension(1,15), new Dimension(1, 45), new Dimension(1, 65)));
        //new Box.Filler(minSize, prefSize, maxSize)        
        //inicia a file 
        highscoresFile = new File("Highscores.txt");
        
        //inicia o botao de aaida
        exit =  new JButton("Menu Principal");
        
        //adicionamos o action listener ao botao
        exit.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                //paramos o isBlinking
                setBlinking(false);
                //iniciamos o menu principal novamente
                mainFrame.startMenuOpcoesNoMusicChange();
            } 
        } );
        
        //setamos exit como nao focavel
        exit.setFocusable(false);
        //setamos o alinhamento no centro do botao
        exit.setAlignmentX(CENTER_ALIGNMENT);
        //mudamos a fonte do botao
        exit.setFont(new Font("Arial", Font.PLAIN, 20));
        //setamos a cor do fundo do botao
        exit.setBackground(Color.black);
        //setamos a cor da fonte do botao
        exit.setForeground(Color.white);
        //setamos o tamanho do botao
        exit.setPreferredSize(new Dimension (400,59));
        
        //setamos o layout desse panel
        backG.setLayout(new BoxLayout(backG, BoxLayout.PAGE_AXIS));
        
        
        //tamanho do array de string e setado pelas constantes
        highscores = new String[LEVELNUM*MAXSCORES];
        //LEVELNUM e a constante do numero de levels, esta definida em fases.LvlSelect
        
        //inicia o array de  JLabel para a impressao na tela dos valores
        scoresOrdenados = new JLabel[highscores.length + LEVELNUM*2];
        //+LEVELNUM*2 pois precisamos adicionar uma linha de espaco e o nome do level 
        
        //salva na string os valores no arquivo
        this.leHighscores();
        
        //iniciamos o array do score mais alto de cada lvl, 1 para cada Lvl
        topScores = new int[LEVELNUM];
        //Inicia todos os Jlabels necessarios
        //counter dos loops
        int j = 1;
        int i;
        //counters das strings
        int k = 0, l = 0, t = 0;
        MatteBorder faseBorda = new MatteBorder(0,0,4,0, Color.white);
        
        
        while (j <= LEVELNUM)
        {
            scoresOrdenados[k] = new JLabel("Fase " + j);
            
            //configura e adiciona os JLabels
            scoresOrdenados[k].setFont(resourceLoader.getDepletedUranium().deriveFont(40f));//derivamos a fonte para o tamanho necessario
            scoresOrdenados[k].setForeground(new Color(238,201,0));//iniciamos a cor (ouro) com seus valores R
            scoresOrdenados[k].setAlignmentX(CENTER_ALIGNMENT);
            scoresOrdenados[k].setOpaque(true);
            scoresOrdenados[k].setBackground(Color.black);
            scoresOrdenados[k].setBorder(faseBorda);
            
            switch(j)
            {
                case 1: {fase1.add(scoresOrdenados[k]);break;}
                case 2: {fase2.add(scoresOrdenados[k]);break;}
                case 3: {fase3.add(scoresOrdenados[k]);break;}            
            }
            //backG.add(scoresOrdenados[k]);
            
            
            j++;
            k++;
            
            for(i = 1; i <= MAXSCORES; i++)
            {
                scoresOrdenados[k] = new JLabel(highscores[l]);
                
                //configura e adiciona os JLabels
                scoresOrdenados[k].setFont(resourceLoader.getDirtyEgo().deriveFont(30f));
                scoresOrdenados[k].setBackground(Color.black);
                
                if(i==1)
                {
                    //para os primeiros highscores de cada nivel, salvamos em um array sua posicao para trabalharmos com ele na thread
                    topScores[t] = k;
                    t++;//aumentamos o contador na posicao de topScores;
                }
                else
                {
                    scoresOrdenados[k].setForeground(Color.white);
                }
                scoresOrdenados[k].setAlignmentX(CENTER_ALIGNMENT);
                
                switch(j)
               {
                    case 2: {fase1.add(scoresOrdenados[k]);break;}
                    case 3: {fase2.add(scoresOrdenados[k]);break;}
                    case 4: {fase3.add(scoresOrdenados[k]);break;}            
               }
               // backG.add(scoresOrdenados[k]);
              
                
                l++;
                k++;
            }
            
            //adicionamos um JLabel vazio para o espacamento
            scoresOrdenados[k] = new JLabel("\n\n");
            
            
            k++;
        }
        backG.add(fase1);
        backG.add(new Box.Filler(new Dimension(1,10), new Dimension(1, 27), new Dimension(1,35)));
        backG.add(fase2);
        backG.add(new Box.Filler(new Dimension(1,10), new Dimension(1, 27), new Dimension(1, 35)));
        backG.add(fase3);  
        
        //adicionamos um espacamento entre os labels e o botao
         backG.add(new Box.Filler(new Dimension(1,10), new Dimension(1, 32), new Dimension(1, 50)));
        //new Box.Filler(minSize, prefSize, maxSize)        
             
        //setamos a fonte do botao de saida
        exit.setFont(resourceLoader.getDepletedUranium().deriveFont(30f));
        
        //setamos a cor do fundo
       
        backG.setBackground(Color.black);
        //adiciona o botao ao panel
        
        //adicionamos os componentes ao background e o adicionamos ao panel
        backG.add(exit);        
        this.add(backG);
    }
    
    /**
     * leHighscores - abre ou cria o arquivo de highscores e salva na string
     */
    private void leHighscores()
    {   
        try
        {
            if(!highscoresFile.exists())//se o arquivo nao existe
            {
                highscoresFile.createNewFile();//cria o novo arquivo
            }
            if(!highscoresFile.canRead() || !highscoresFile.canWrite())
            {
                throw (new AccessDeniedException("Permissao negada de acesso a highscoresFile!"));
            }
            
            //inicializamos o scanner o teste
            Scanner scTest = new Scanner(highscoresFile);
            boolean foraDeFormato = false;
            
            //se o arquivo esta totalmente vazio
            if(highscoresFile.length() < LEVELNUM*MAXSCORES)
            {
                foraDeFormato = true;
            }
            
            while(scTest.hasNextLine() && !foraDeFormato)//se esta fora de formato nao ha pq continuar
            {
                String aux = scTest.nextLine();
                
                //testa as opcoes, a string ou deve estar fazia ou deve conter um "Fase"
                if(!aux.isEmpty() && (aux.indexOf('-') == -1 || aux.indexOf("Fase") == -1) && aux.indexOf(':') == -1)     
                {
                    foraDeFormato = true;
                }
            }
            
            if(foraDeFormato)
            {
                FileWriter fw = new FileWriter(highscoresFile);
                BufferedWriter bw = new BufferedWriter(fw);

                //percorre o array escrevendo os scores como vazios
                for(int i = 1; i <= LEVELNUM; i++)
                {
                    //Escreve o numero da fase
                    bw.write("Fase " + i + ":\r\n");
                    
                    //escreve a ordem do highscores
                    for(int j = 1; j <=  MAXSCORES; j++)
                    {
                        bw.write("\t" + j + " - TEMPLATE : 00.00\r\n");//imprime com um tab para facilitar visualizacao      
                    }
                    
                    //termino de scores de uma fase
                    bw.write("\r\n");
                }
                
                //fechamos os recursos
                bw.close();
                fw.close();
            }
            
            //inicializamos o scanner real
            Scanner sc = new Scanner(highscoresFile);
            
            //setamos os delimitadore do tokens
            sc.useDelimiter("\\s\t|\\s\n");
            
            //i sera usado para a contagem do array de highscores
            int i = 0;
                
            while(sc.hasNext() && i < highscores.length)
            {
                //salva a linha no array de strings
                String aux = sc.next();
                
                //chega se a string contem o hifen, ou seja se e um valor de highscores
                if(aux.indexOf('-') != -1)
                {
                    //criamos um stringBuilder
                    StringBuilder sb = new StringBuilder(aux);
                    
                    //deletamos o primeiro char da string (\t)
                    sb.deleteCharAt(0);
                    
                    //igualamos a nova string
                    highscores[i] = sb.toString();
                    i++;
                }
            }
        
            //fechamos a stream
            sc.close();
        }
         catch (IOException ioe) 
        {
             System.err.println("Erro na abertura de Highscores.txt!"); 
        }
    }

    /**
     * testaScore - testa o novo score e salva se for maior
     */
    public void testaScore(double score, int level)
    {
        //prmeiramente formatamos para 2 casas decimais
        DecimalFormat df = new DecimalFormat("0.00"); 
        
        //usamos o scanner para pegarmos a string
        Scanner sc = new Scanner(df.format(score));
        
        //evitamos erros pelo . e ,
        sc.useLocale(Locale.US);
        
        //formatamos o score e salvamos
        try
        {
            score = sc.nextDouble();
        }
        catch(InputMismatchException e)
        {
            //caso ocorra um erro, testamos usar o Locale brasileiro
            sc.useLocale(new Locale("pt", "BR"));
        }
        
        //fechamos o scanner
        sc.close();
        
        //temos de testar se eh maior que os highscores salvos
        boolean isHighscore = false;
        
        //temos de fazer uma pesquisa comecando em:
        int startPoint = (MAXSCORES*level)-1;
        
        //variavel da posicao do scores
        //setamos para MAXSCORES+1 para demonstrar q estao incorreto
        int pos = MAXSCORES+1;
        
        //variavel da posicao do array
        int arrPos = 0;
        
        //percorremos todos scores do level
        for(int i = startPoint; i > startPoint-MAXSCORES; i--)
        {
            //iniciamos o scanner novamente
            sc = new Scanner(highscores[i]);
            
            //todo highscore deve ter esse token
            sc.useDelimiter(" : ");
            
            //pulamos a parte anterior aos dois pontos
            sc.next();
            
            //double para compararmos
            double comp = sc.nextDouble();
                       
            //se o score e maior, paramos o loop (score eh medido em tempo, quando menor, melhor)
            if(score > comp && comp != 0)//entramos tambem se o valor eh nulo, pois nao eh um highscore
            {
                i = startPoint-MAXSCORES;
            }
            else
            {
                //incrementamos a variavel de posicao
                pos--;
                //setamos o array position
                arrPos = i;
                //setamos q e um highscore
                isHighscore = true;
            }
        }
        
        //se e um highscore
        if(isHighscore)
        {
            //iniciamos uma nova dialog box e mandamos as informacoes do highscore
            this.newHighscoreDialog(pos, arrPos, level, score);
        }
    }
    /**
     * newHighscoreDialog - se um novo highscore for encontrado, esse metodo deve ser executado para exibir um JDialog para o usuario
     */
    private void newHighscoreDialog(final int pos, final int arrPos, final int level, final double score)
    {
        //iniciamos uma dialogBox para pegarmos o nome do usuario
        JDialog jd = new JDialog(mainFrame, "New Highscore!");
        
        //iniciamos o painel que contera os componentes
        JPanel pan = new JPanel();
        //setamos o layout do panel
        pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
        //setamos a cor do Panel
        pan.setBackground(Color.black);
        
        //editamos a fonte para ser utilizada nos textos
        Font font = mainFrame.getResourceLoader().getDepletedUranium().deriveFont(35f);
        //usamos de um map para editar os atributos
        Map<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
        map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        
        
        //criamos o JLabel
        JLabel jl = new JLabel("NEW HIGHSCORE");
        //setamos o JLabel com a fonte do jogo, com o tamanho desejado
        jl.setFont((font.deriveFont(map)).deriveFont(35f));
        //setamos o alinhamento
        jl.setAlignmentX(CENTER_ALIGNMENT);
        //setamos o tamamnho preferivel do componente
        jl.setPreferredSize(new Dimension(450, 100));
        //setamos a cor do texto
        jl.setForeground(Color.yellow);
        
        //criamos o text Field para o usuario inserir seu nome
        JTextField txt_f = new JTextField("Name Here", 20);//o numero é o numero de colunas do textField
        //setamos a cor de fundo
        txt_f.setBackground(Color.white);
        //setamos a fonte
        txt_f.setFont((font.deriveFont(map)).deriveFont(30f));
        txt_f.setAlignmentX(CENTER_ALIGNMENT);
        txt_f.setHorizontalAlignment(JTextField.CENTER);
        
        //setamos o tamanho
        txt_f.setPreferredSize(new Dimension(50, 100));
        
        //criamos a borda do textField
        Border borda = BorderFactory.createLineBorder(new Color(161,161,161), 7);
        txt_f.setBorder(borda);
        
        
        //para ajustarmos a largura do textField, criamos um outro panel
        JPanel pan2 = new JPanel();
        //setamos a cor dele
        pan2.setBackground(Color.black);
        pan2.add(txt_f);
        
        //adicionamos os componentes ao panel
        pan.add(jl);
        pan.add(new Box.Filler(new Dimension(1,20), new Dimension(1, 25), new Dimension(1, 30)));
        pan.add(pan2);
        pan.add(new Box.Filler(new Dimension(1,20), new Dimension(1, 25), new Dimension(1, 30)));
        
        //adicionamos o panel a JDialog
        jd.add(pan);
        
        //setamos um "botao" para escutar o teclado quando apertar enter
        JButton submit = new JButton();
        submit.setVisible(false);
        pan2.add(submit);
        submit.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                //iniciamos o metodo de adicionar o highscore quando for executado
                addHighscore(pos, arrPos, level, score, txt_f.getText());
                //fechamos a JDialg
                jd.dispose();
            } 
        });
        jd.getRootPane().setDefaultButton(submit);
        
        
        //setamos o tamnho para comportar os componentes
        jd.pack();
        
        //centramos na tela
        jd.setLocationRelativeTo(null);
        
        //setamos como visivel
        jd.setVisible(true);
    }
    /**
     * addHighscore - se um novo highscore for encontrado, esse metodo deve ser executado para salvar o novo highscore
     */
    private void addHighscore(final int pos, int arrPos, final int level, final double score, String name)
    {
        //inicializamos o StringBuilder para operarmos a string
        StringBuilder saveName = new StringBuilder(name);
        
        //testamos se a string contem um caracter invalido
        for(int i = 0; i < name.length(); i++)
        {
            //pegamos o char naquela posicao da string
            char check = saveName.charAt(i);
            
            if(check == ':' || check == '-')
            {
                //setamos o char na posicao como um espaco
                saveName.setCharAt(i, ' ');
            }
        }
        
        //o comeco da string deve ser
        String defaultScoreBegin = pos + " - ";
        String defaultScoreEnd = " : " + score;
        
        //inserimos no comeco da string;
        saveName.insert(0, defaultScoreBegin);
        
        //inserimos no final da string
        saveName.append(defaultScoreEnd);
        
        //para salvarmos a String montada no array
        String aux1 = highscores[arrPos];
        String aux2 = null;
        
        //salvamos a antiga posicao
        int antArrPos = arrPos;
        
        //percorremos os outros highscores do level alterando-os, se necessario
        for(int i = pos; i <= MAXSCORES; i++)
        {
            //recebe a antiga string
            aux2 = highscores[arrPos];
            
            //criamos o string builder para editar as strings
            StringBuilder sb = new StringBuilder(aux1);
            
            //editamos a string para conter a posicao correta
            sb.replace(0, 1, ""+i);
            //start at 0, end at 1
            
            //recebe a nova string
            highscores[arrPos] = sb.toString();
            //salvamos no JLabel
            scoresOrdenados[arrPos+(level*2)-1].setText(highscores[arrPos]);
            
            //nova string vira a antiga
            aux1 = aux2;
            
            //alteramos a posicao do array
            arrPos++;
        }
        
        //salvamos em highscores a nova string montada apos mover todas strings anteriores para baixo
        highscores[antArrPos] = saveName.toString();
        //modificamos o JLabel respectivo
        scoresOrdenados[antArrPos+(level*2)-1].setText(highscores[antArrPos]);
        
        //salvamos a string montada no arquivo
        this.escreveHighscores();
    }
     /**
     * escreveHighscore - se um novo highscore for encontrado, esse metodo escreve no arquivo o novo highscore
     */
    private void escreveHighscores()
    {
         try
        {
            if(highscoresFile.exists())//se o arquivo existe
            { 
                //abrimos os recursos
                FileWriter fw = new FileWriter(highscoresFile);
                BufferedWriter bw = new BufferedWriter(fw);
                
                
                for(int i = 0; i < LEVELNUM; i++)
                {
                    //Escreve o numero da fase
                    bw.write("Fase " + (i+1) + ":\r\n");
                    
                    //escreve a ordem do highscores
                    for(int j = 0; j < MAXSCORES; j++)
                    {
                        bw.write("\t" + highscores[j+(i*MAXSCORES)]+"\r\n");//imprime com um tab para facilitar visualizacao
                    }
                    
                    //termino de scores de uma fase
                    bw.write("\r\n");
                }
                
                //fechamos os recursos
                bw.close();
                fw.close();
            }
            else
            {
                //se o arquivo nao existe, devemos cria-lo
                this.leHighscores(); 
            }
        }
        catch (IOException ioe) 
        {
             System.err.println("Erro na abertura de Highscores.txt!");
             ioe.printStackTrace();
        }
    }
    //metodo para o blink, a thread compara com esse metodo para continuar
    public boolean isBlinking()
    {
        return blink;
    }
    
    //inicia as threads de isBlinking
    protected void setBlinking(boolean run)
    {
        //setamos a variavel do isBlinking
        blink = run;
        
        //se o panel deve piscar
        if(blink)
        {
            //Iniciamos a thread, ela recebe o numero de scores e uma referencia e esse panel
            BlinkThread blinkThread = new BlinkThread(topScores.length, this);
        
            for(int i = 0; i < topScores.length; i++)
            {
                //a posicao esta salva no array topScore
                int pos = topScores[i];
            
                //adicionamos o JLabel a thread
                blinkThread.addScore(scoresOrdenados[pos]);
            }
        
            //iniciamos a thread, ela deve rodar ate retornarmos ao menu principal            
            blinkThread.start();
        }
        //caso contrario as threads pararam assim a usarem o metodo isBlinking();
    }
}
