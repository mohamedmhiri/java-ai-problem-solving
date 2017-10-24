package utils;

import models.Fact;
import models.Goal;
import models.Node;
import models.Rule;

import java.util.ArrayList;
import java.util.List;
//import utils.Util;

public class Algorithm {

    /**
     * util attribute which contains all
     * AAlgorithm Useful methods
     */
    Util util = new Util();

    Analyzer analyzer = new Analyzer();

    /**
     * this function gets a list of rules,
     * fact which contains initial state,
     * goal which represents the desired result
     * and finally the choice which references which
     * heuristic to use
     * @param fact
     * @param goal
     * @param rules
     * @param choice
     * @return
     */
    public StringBuilder AAlgorithm(Fact fact, Goal goal, List<Rule> rules, char choice){
        List<Node> open = new ArrayList<Node>();
        List<Node> closed = new ArrayList<Node>();
        Analyzer analyzer = new Analyzer();
        Node initialNode = new Node(fact.getHypothesis(),0);
        boolean found = false;
        open.add(initialNode);
        StringBuilder result = new StringBuilder();
        StringBuilder path = new StringBuilder();
        int tabNum =0;
        path.append("\nFollowed PATH ====>\n");
        do{
            result.append("\nOpened nodes :\n");
            for(Node n :open){
                result.append(n);
            }
            result.append("\n");
            result.append("\nClosed nodes :\n");
            for(Node n :closed){
                result.append(n);
            }
            result.append("\n");
            Node node = new Node();
            if(choice == '1')
                node = this.util.calcMinF(open);
            else if(choice == '2')
                node = this.util.absMinF(open);
            open.remove(node);
            result.append("\nChosen node :("+node.getData().get(0)+ ", " + node.getData().get(1) + ")\n");
            for (int k=0;k<node.getgVar();k++)
                path.append("\t");
            tabNum = node.getgVar();
            path.append(node+"\n");
            closed.add(node);
            if(this.util.nodeComparator(node.getData(),goal.getConclusions())) {
                found = true;
                break;
            }else{
                Fact tmpFact = new Fact();
                tmpFact.setHypothesis(node.getData());
                List<Rule> releasableRules =analyzer.releasableRules(tmpFact,rules);
                List<Node> tmpNode = new ArrayList<Node>();
                for(int j=0; j<releasableRules.size();j++){
                    Node node1 = new Node();
                    List<String> tmpData =analyzer.conclusionToData(releasableRules.get(j).getConclusions(), tmpFact.getHypothesis());
                    node1.setData(tmpData);
                    node1.setgVar(node.getgVar()+1);
                    tmpNode.add(node1);
                }
                // to be optimized Cause : redundancy when adding to util
                // data was present, new allocation then added again
                List<List<String>> util = new ArrayList<List<String>>();
                for(int i =0; i<tmpNode.size(); i++) {
                    //list of nodes ==> list of Strings
                        util.add(tmpNode.get(i).getData());
                }
                for(int i =0; i<util.size(); i++) {
                    int indexInOpen= this.util.dataExistsInNodeList(open, util, i);
                    int indexInClosed = this.util.dataExistsInNodeList(closed, util, i);
                    if( ( indexInClosed == -99) && ( indexInOpen == -99)){
                        int tmpG = node.getgVar() + 1;
                        Node node1 = new Node(util.get(i),tmpG);
                        open.add(node1);
                    }else {

                        if( ( indexInClosed == -99 )&& (indexInOpen !=-99) && ( (node.getgVar() + 1) < open.get(indexInOpen).getgVar() )){
                            open.get(indexInOpen).setgVar(node.getgVar() + 1);
                        } else if( ( indexInOpen == -99 )&& (indexInClosed !=-99) && ( (node.getgVar() + 1) < closed.get(indexInClosed).getgVar() )){
                            Node node2 =closed.remove(indexInClosed);
                            node2.setgVar(node.getgVar() + 1);
                            open.add(node2);
                        }
                    }
                }
            }

        }while (open.size() != 0);
        if(found){
            result.append("Success============\n"+goal +" found");
            for (int j=0;j<tabNum;j++)
                path.append("\t");
            path.append(goal);
            result.append(path);
            if( goal.getConclusions().get(0).contains("?")){
                result.append("\t"+goal.getConclusions().get(0) +"\\" +closed.get(closed.size()-1).getData().get(0));
            }else if( goal.getConclusions().get(1).contains("?")){
                result.append("\t"+goal.getConclusions().get(1) +"\\" +closed.get(closed.size()-1).getData().get(1));
            }
        }
        else
            result.append("Failure============\n" + goal +" not found");
        return result;
    }

    /**
     *
     * @param fact
     * @param goal
     * @param rules
     * @param level
     * @return
     */
    public StringBuilder LimitedDepthSearch(Node fact, Goal goal, List<Rule> rules, int level,int initLevel, List<Node> closed, int g){
        StringBuilder res = new StringBuilder();
//        if(level==0)
//            res.append("salem");
        if(level>=0 && (util.nodeComparator(fact.getData(),goal.getConclusions()))){
            StringBuilder str = new StringBuilder();
            Node node = new Node(fact.getData(),g);
            closed.add(node);
            for (int i=0;i<g;i++)
                str.append("\t");
            str.append(goal+" found]\n");
            res.append(str.toString());
        }else {
            if(level==initLevel)
                res.append('[');
            Fact fact1 = new Fact();
            fact1.setHypothesis(fact.getData());
            Node node3 = new Node(fact.getData(),g);
            closed.add(node3);
            for (int i=0;i<node3.getgVar();i++ ) {
                res.append("\t");
            }
            res.append("{Data: "+node3.getData()+", Level: "+node3.getgVar()+"}\n");
            List<Rule> tmpRules =this.analyzer.releasableRules(fact1,rules);
            int tmpG = g+1;
            int i=0;

            while (i<tmpRules.size() && !res.toString().contains("found")){
//            for (int i=0;i<tmpRules.size();i++){
                Node node2 = new Node(analyzer.conclusionToData(
                        this.analyzer.releasableRules(fact1,rules).get(i).getConclusions(),
                        fact1.getHypothesis()),
                        tmpG);
                if ( !this.util.existInNodeList(node2,closed) && tmpG <= initLevel){

//                    System.out.println(node2);
                    res.append(LimitedDepthSearch(node2,goal,rules,level-1,initLevel,closed,tmpG));
//                    System.out.println(str.toString());
                }
                i++;
            }

        }
//        res.append("salem");
        return res;
    }


    public StringBuilder IterativeDeepeningSearch(Node fact, Goal goal, List<Rule> rules, int level){
        StringBuilder str = new StringBuilder();
        int i=0;
        while ( i<level && !str.toString().contains("found")){
            int j=i+1;
            str.append("\n===== "+j);
            switch (i){
                case 0:
                    str.append("st");
                    break;
                case 1:
                    str.append("nd");
                    break;
                case 2:
                    str.append("rd");
                    break;
                default:
                    str.append("th");
                    break;
            }
            str.append(" iteration =====\n");
            str.append(this.LimitedDepthSearch(fact,goal,rules,i,i,new ArrayList<>(),0));
            i++;
        }
        return str;
    }


}
