package app;

import models.Fact;
import models.Goal;
import models.Node;
import models.Rule;
import utils.Algorithm;
import utils.Analyzer;
import utils.Binder;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;



public class Frame {

    private JFrame frame;
    private JPanel contentPane;
    private JTextField xEtatDepart;
    private JTextField yEtatDepart;

    private JTextField xEtatFinal;
    private JTextField yEtatFinal;

    private JComboBox choixBase;
    private JComboBox choixAlgo;
    private JTextField limit;
    private JTextField heuristic;

    private static final String SOURCE = "/home/mohamed/Documents/development/java/ArtificialIntelligence/problemsolving";
    private static final String RESULT = "/home/mohamed/Documents/development/java/ArtificialIntelligence/problemsolving/algosRecherche.txt";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Frame window = new Frame();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Frame() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel algoLabel = new JLabel("Choose a resolution algorithm");
        algoLabel.setBounds(29, 22, 191, 14);
        contentPane.add(algoLabel);

        choixAlgo = new JComboBox();
        choixAlgo.setModel(new DefaultComboBoxModel(new String[] {"A* Algorithm", "Limited Depth First", "Iterative Deepening Search" }));
        choixAlgo.setBounds(268, 19, 89, 20);
        contentPane.add(choixAlgo);



        JLabel etatInitial = new JLabel("Choose the INITIAL STATE");
        etatInitial.setBounds(29, 79, 163, 14);
        contentPane.add(etatInitial);

        JLabel lblX = new JLabel("X :");
        lblX.setBounds(47, 104, 24, 14);
        contentPane.add(lblX);

        JLabel lblY = new JLabel("Y:");
        lblY.setBounds(103, 104, 24, 14);
        contentPane.add(lblY);

        xEtatDepart = new JTextField();
        xEtatDepart.setBounds(69, 104, 24, 14);
        contentPane.add(xEtatDepart);
        xEtatDepart.setColumns(10);

        yEtatDepart = new JTextField();
        yEtatDepart.setBounds(120, 104, 24, 14);
        contentPane.add(yEtatDepart);
        yEtatDepart.setColumns(10);




        JLabel etatFinal = new JLabel("Choose the GOAL");
        etatFinal.setBounds(29, 129, 98, 14);
        contentPane.add(etatFinal);

        JLabel label = new JLabel("X :");
        label.setBounds(47, 150, 24, 14);
        contentPane.add(label);

        xEtatFinal = new JTextField();
        xEtatFinal.setColumns(10);
        xEtatFinal.setBounds(69, 150, 24, 14);
        contentPane.add(xEtatFinal);

        JLabel label_1 = new JLabel("Y:");
        label_1.setBounds(103, 150, 24, 14);
        contentPane.add(label_1);

        yEtatFinal = new JTextField();
        yEtatFinal.setColumns(10);
        yEtatFinal.setBounds(120, 150, 24, 14);
        contentPane.add(yEtatFinal);



        JLabel LimiteEpli = new JLabel("Limited Depth First Limit");
        LimiteEpli.setBounds(109, 47, 83, 14);
        contentPane.add(LimiteEpli);

        limit = new JTextField();
        limit.setBounds(181, 47, 39, 14);
        contentPane.add(limit);
        limit.setColumns(10);

        JLabel heuristicLabel = new JLabel("HEURISTIC");
        heuristicLabel.setBounds(109, 65, 83, 14);
        contentPane.add(heuristicLabel);

        heuristic = new JTextField();
        heuristic.setBounds(181, 65, 39, 14);
        contentPane.add(heuristic);
        heuristic.setColumns(10);





        JLabel ChoisirLaBase = new JLabel("Choose the knowledge base");
        ChoisirLaBase.setBounds(29, 192, 191, 14);
        contentPane.add(ChoisirLaBase);

        choixBase = new JComboBox();
        choixBase.setModel(new DefaultComboBoxModel(new String[] {"Water pitcher"}));
        choixBase.setBounds(266, 189, 158, 20);
        contentPane.add(choixBase);

        String chosenBase = choixBase.getSelectedItem().toString();
        String baseFile ="";
        if(chosenBase.equals("Water pitcher")){
            baseFile = "/texte.txt";
        }

        String source = SOURCE+baseFile;
        JButton btnDemarrerResolution = new JButton("Go !!!");
        btnDemarrerResolution.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                String chosenAlgo = choixAlgo.getSelectedItem().toString();
                String heuristicChar= heuristic.getText();
                String xStart = xEtatDepart.getText();
                String yStart =yEtatDepart.getText();
                String start = '('+xStart+", "+yStart+")";

                String xGoal = xEtatFinal.getText();
                String yGoal =yEtatFinal.getText();
                String goalText = '('+xGoal+", "+yGoal+")";

                Binder binder = new Binder();
                List<Rule> rules = new ArrayList<Rule>();
                rules = binder.bindRule(source);
                Analyzer analyzer = new Analyzer();
                Fact fact = binder.bindFact(start);
//        for (int i = 0; i<rules.size();i++)
////            System.out.println(rules.get(i).getConclusions());
//            System.out.println(analyzer.conclusionToData(rules.get(i).getConclusions(),fact.getHypothesis()));
                Goal goal = binder.bindGoal(goalText);
//        analyzer.verify(fact,rules);
//        List<Rule> resultedRules =analyzer.releasableRules(fact,rules);
//        for (Rule tmpR:resultedRules)
//            System.out.println(tmpR);
//        List<Fact> facts = new ArrayList<Fact>();

                Algorithm algo = new Algorithm();
                Node node = new Node(fact.getHypothesis(),0);
                String algoResult="" ;
                if( chosenAlgo.contains("Limited") ){
                    List<Node> closed = new ArrayList<>();
                    StringBuilder tmpStr = algo.LimitedDepthSearch(node,goal,rules,Integer.parseInt(limit.getText()),Integer.parseInt(limit.getText()),closed,0);
                    if(!tmpStr.toString().contains("found")){
                        for (int i=0;i<Integer.parseInt(limit.getText());i++)
                            tmpStr.append("\t");
                        tmpStr.append(goal+" not found]\n");

                    }

                    algoResult = tmpStr.toString();
                    //"A* Algorithm", "Limited Depth First", "Iterative Deepening Search"
                }else if( chosenAlgo.equals("Iterative Deepening Search")){
                    algoResult = algo.IterativeDeepeningSearch(node,goal,rules,10).toString();
                }else if( chosenAlgo.equals("A* Algorithm")){

                    algoResult = algo.AAlgorithm(fact,goal,rules,heuristicChar.charAt(0)).toString();
                }
                //String iterativeDeepingSearch =
//                System.out.println(iterativeDeepingSearch);

                /* set log text into a text file */
                BufferedWriter bw = null;
                FileWriter fw = null;

                try {

                    fw = new FileWriter(RESULT);
                    bw = new BufferedWriter(fw);
                    bw.write(algoResult);

                    System.out.println("Done");

                } catch (IOException e) {

                    e.printStackTrace();

                } finally {

                    try {

                        if (bw != null)
                            bw.close();

                        if (fw != null)
                            fw.close();

                    } catch (IOException ex) {

                        ex.printStackTrace();

                    }

                }
                //Etat etatDepart = new Etat(xDep,yDep);


                //List<Regle> regles=new ArrayList<Regle>();

//                Program p=new Program();
//                Unification u=new Unification();
//                Operateur o=new Operateur();
//                try {
////                    regles=p.affectRegle();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                Etat depart=new Etat(xDep,yDep);
//                Etat but = new Etat(xFin,yFin);

//                try {
//                    o.algorithmEtoil(regles,depart,but );
//                } catch (ScriptException e) {
//                    e.printStackTrace();
//                }
                //MainResolution main = new MainResolution();
                //String chemin = main.executerTraitement(etatDepart, etatFinal,(String)choixAlgo.getSelectedItem(),(String)choixBase.getSelectedItem(),limite.getText());

                //Frame2 frame2 = new Frame2(chemin);
                frame.dispose();
                //frame2.setVisible(true);
            }
        });
        btnDemarrerResolution.setBounds(266, 227, 158, 23);
        contentPane.add(btnDemarrerResolution);


    }
}