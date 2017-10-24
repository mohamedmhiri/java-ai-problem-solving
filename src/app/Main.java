package app;
import utils.*;
import models.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String path = "/home/mohamed/Documents/development/java/ArtificialIntelligence/problemsolving/texte.txt";
        Binder binder = new Binder();
        List<Rule> rules = new ArrayList<Rule>();
        rules = binder.bindRule(path);
        Analyzer analyzer = new Analyzer();
        Fact fact = binder.bindFact(" ( 0 , 0 )");
//        for (int i = 0; i<rules.size();i++)
////            System.out.println(rules.get(i).getConclusions());
//            System.out.println(analyzer.conclusionToData(rules.get(i).getConclusions(),fact.getHypothesis()));
        Goal goal = binder.bindGoal("(2 , 0 )");
//        analyzer.verify(fact,rules);
//        List<Rule> resultedRules =analyzer.releasableRules(fact,rules);
//        for (Rule tmpR:resultedRules)
//            System.out.println(tmpR);
//        List<Fact> facts = new ArrayList<Fact>();
        Algorithm algo = new Algorithm();
//        StringBuilder str = algo.AAlgorithm(fact,goal,rules,'2');
//        System.out.println(str.toString());
        Node node = new Node(fact.getHypothesis(),0);
        List<Node> closed = new ArrayList<>();
//        StringBuilder limitedDepthSearch = algo.LimitedDepthSearch(node,goal,rules,5,5,closed,0);
//        System.out.println(limitedDepthSearch.toString());
        StringBuilder tmpStr = algo.LimitedDepthSearch(node,goal,rules,5,5,closed,0);
        if(!tmpStr.toString().contains("found")){
            for (int i=0;i<5;i++)
                tmpStr.append("\t");
            tmpStr.append(goal+" not found]\n");

        }
        System.out.println(tmpStr.toString());
        //List
//        String iterativeDeepingSearch = algo.IterativeDeepeningSearch(node,goal,rules,10).toString();
//        System.out.println(iterativeDeepingSearch);

    }
}
