package utils;

import java.io.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import models.*;


public class Binder{

    /**
     * this function reads from text file
     * parses it and returns a list of rules
     * @param path
     * @return List<Rule>
     */
    public List<Rule> bindRule(String path){

        try (BufferedReader in =  new BufferedReader(new FileReader( path ))) {
            String line;

            List<Rule> rules = new ArrayList<Rule>();
            while((line = in.readLine()) != null && (line.toUpperCase().matches("^R[0-9]*.*$"))) {
                Rule rule=new Rule();
                String _line=line.substring(line.lastIndexOf(":") + 1);
                //System.out.println(_line);
                // pickup number between R and :
                String _nbr = line.substring(1,line.indexOf(":"));
                int nbr = Integer.parseInt(_nbr);
                //pickup text between : and >>
                String _premises=_line.substring(0,_line.indexOf(">>")-1);
                //test if "&&" is in premises
                List<String> data = new ArrayList<String>();
                String da ;
                List<String> cond = new ArrayList<String>();
                if(_premises.contains("&&")){
                    String[] pre = _premises.split("&&");
                    for (String p: pre){
                        String pp = p.replace(" ","");
                    }
                    da = pre[0].replace(" ","");
                    for(int i =1; i<pre.length;i++) {
                        cond.add(pre[i].replace(" ", ""));
                    }
                }
                else{
                    da = _premises.replace(" ","");
                }
                String []ppp = da.split(",");
                String d = ppp[0].replace("(","");
                data.add(d);
                String sd = ppp[1].replace(")","");
                data.add(sd);
                String _conclusions = _line.substring(_line.indexOf(">>")+3);
                List<String> conclusions = new ArrayList<String>();
                String conc = _conclusions.replace(" ","");
                String []c = conc.split(",");
                String c1 = c[0].replaceFirst("\\(","");
                conclusions.add(c1);
                String c2 = c[1].replaceFirst("\\)","");
                conclusions.add(c2);
                rule.setNumber(nbr);
                rule.setData( data );
                rule.setConditions(cond);
                rule.setConclusions( conclusions );
                rules.add( rule );

            }
            return rules;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * this function reads a string
     * and returns a Fact
     * @param _fact
     * @return Fact
     */
    public Fact bindFact(String _fact){
        Fact fact = new Fact();
        String __fact = _fact.replace(" ","");
        String []facts = __fact.split(",");
        String d1 = facts[0].replace("(","");
        List<String> hypothesis = new ArrayList<String>();
        hypothesis.add(d1);
        String d2 = facts[1].replace(")","");
        hypothesis.add(d2);
        fact.setHypothesis(hypothesis);
        return fact;
    }

    /**
     *this function reads a string
     * and returns a Goal
     * @param _goal
     * @return Goal
     */
    public Goal bindGoal(String _goal){
        Goal goal = new Goal();
        String __goal = _goal.replace(" ","");
        String []goals = __goal.split(",");
        String c1 = goals[0].replace("(","");
        List<String> concls = new ArrayList<String>();
        concls.add(c1);
        String c2 = goals[1].replace(")","");
        concls.add(c2);
        goal.setConclusions(concls);
        return goal;
    }

//    public List<Fact> bindFact(String path){
//        try (BufferedReader in =  new BufferedReader(new FileReader( path ))) {
//            String line;
//            List<Fact> facts = new ArrayList<Fact>();
//            while( ( ( line = in.readLine() ) != null ) ) {
//                if(! line.toUpperCase().matches("^(R|BUT)[0-9]*.*$"))
//                    System.out.println( line );
////                List<String> hypothesis = new ArrayList<String>();
////                Fact fact = new Fact();
////                if(line.contains(",")){
////                    String[] pre = line.split(",");
////                    for (String p: pre){
////                        hypothesis.add(p);
////                    }
////                }
////                else{
////                    hypothesis.add(line);
////                }
////                fact.setExplication(0);
////                fact.setHypothesis( hypothesis );
////                facts.add( fact );
//            }
//            return facts;
//        }
//        catch (IOException e) {
//            System.out.println("error" + e.getMessage());
//            return null;
//        }
//    }

//    public List<String> bindBut( String path){
//        try (BufferedReader in =  new BufferedReader(new FileReader( path ))) {
//            List<String> buts = new ArrayList<String>();
//            String line;
//            while( ( ( line = in.readLine() ) != null ) ){
//                line = line.replace(" ", "");
//                if( line.toLowerCase().contains( "BUT".toLowerCase() ) ){
//                    String prebuts=line.substring(line.lastIndexOf(":") + 1);
//                    if( prebuts.contains( "," ) ){
//                        String[] pre = prebuts.split(",");
//                        for (String p: pre){
//                            buts.add(p);
//                        }
//                    }
//                    else{
//                        buts.add(prebuts);
//                    }
//
//                }
//            }
//            return buts;
//        }
//        catch (IOException e) {
//            System.out.println("error" + e.getMessage());
//            return null;
//        }
//    }
}
