package utils;

import models.*;

import java.util.ArrayList;
import java.util.List;
//import org.apache.commons.lang3.StringUtils;

public class Analyzer {
    /**
     * generates the verifying process
     * and outputs the list of releasable rules
     * @param fact
     * @param rules

     *
     */
    public void verify(Fact fact, List<Rule> rules){
        StringBuffer output = new StringBuffer();
        //
        for(int i=0; i<rules.size(); i++){
//            output.append('(')
//                  .append(rules.get(i).getData().get(0))
//                  .append(", ")
//                  .append(rules.get(i).getData().get(1))
//                  .append(")\n")
//            ;
            if(this.testCondition(rules.get(i),fact.getHypothesis()) != null){
                System.out.println(rules.get(i) +"\n====>is releasable with "+fact.getHypothesis()+" as initial state");
                List<String> conclusions = this.conclusionToData(rules.get(i).getConclusions(), fact.getHypothesis());
                System.out.println("====>New Conclusion : ("+conclusions.get(0)+", "+conclusions.get(1)+")");
            }
//            else{
//                System.out.println(rules.get(i) +"\n====>is not releasable with "+fact.getHypothesis()+" as initial state");
//            }
        }
//        System.out.println(output.toString());

    }

    /**
     * for a given Fact and List of Rules
     * returns a list of releasable Rules
     * @param fact
     * @param rules
     * @return List<Rule>
     */
    public List<Rule> releasableRules(Fact fact, List<Rule> rules){
        List<Rule> result = new ArrayList<Rule>();
        int[] facts = new int[2];
        facts[0] = Integer.parseInt(fact.getHypothesis().get(0));
        facts[1] = Integer.parseInt(fact.getHypothesis().get(1));
        int []concls = new int[2];
        int t = 555;
        for(int i=0; i<rules.size(); i++){
//
//            if (((fact.getHypothesis().get(0).charAt(0)!=this.conclusionToData(rules.get(i).getConclusions(),fact.getHypothesis()).get(0).charAt(0))&& (fact.getHypothesis().get(1).charAt(0)!=this.conclusionToData(rules.get(i).getConclusions(),fact.getHypothesis()).get(1).charAt(0))))
//                i++;
            List<String> conclusions = this.conclusionToData(rules.get(i).getConclusions(), fact.getHypothesis());
//            System.out.println("====>New Conclusion : ("+conclusions.get(0).charAt(0)+", "+conclusions.get(1).charAt(0)+")");
            concls[0]= Integer.parseInt(conclusions.get(0));
            concls[1]= Integer.parseInt(conclusions.get(1));

            if(this.testCondition(rules.get(i),fact.getHypothesis()) != null && (!( facts[0]==concls[0] && facts[1]==concls[1]))){
                for(int j=0;j<result.size();j++){
                    if((Integer.parseInt(this.conclusionToData(result.get(j).getConclusions(),fact.getHypothesis()).get(0))==concls[0])&&(Integer.parseInt(this.conclusionToData(result.get(j).getConclusions(),fact.getHypothesis()).get(1))==concls[1])){
                        t=1;
                    }
                }
                if(t!=1)
                    result.add(rules.get(i));
//                System.out.println(rules.get(i) +"\n====>is releasable with "+fact.getHypothesis()+" as initial state");
            }
        }
        return result;
    }

    /**
     * tests weather this rule is releasable
     * if rules.data == fact.hypothesis
     * @param rule
     * @param data
     * @return Rule
     */
    public Rule testCondition(Rule rule, List<String>data){
        int i=0;
        int res =0;
//        while( dataTester < rule.getData().size()){
//            if(!rule.getData().get(dataTester).contains("?")){
//                if (rule.getData().get(dataTester).equals(data) )
//                    dataTester++;
//                else
//                    return null;
//            }
//
//        }
        for(int j=0;j<2;j++){
            if((!rule.getData().get(j).contains("?")) && (!rule.getData().get(j).equals(data.get(j)))){
                    return null;
            }
        }

        while( i<rule.getConditions().size() ){
            String [] conditions = new String[0];
            String [] tmp = new String[0];
            int []operands = new int[2];
            int last = -99;
            if(rule.getConditions().get(i).contains("<=")){
                conditions = rule.getConditions().get(i).split("<=");
                operands = this.calculate(data,conditions);
                if( operands[0] <= operands[1] )
                    res++;
            }else if ( rule.getConditions().get(i).contains("=>")){
                conditions = rule.getConditions().get(i).split("=>");
                operands = this.calculate(data,conditions);
                if( operands[0] >= operands[1] )
                    res++;
            }else if(rule.getConditions().get(i).contains("<") ){
                conditions = rule.getConditions().get(i).split("<");
                operands = this.calculate(data,conditions);
                if( operands[0] < operands[1] )
                    res++;
            }else if(rule.getConditions().get(i).contains(">")){
                conditions = rule.getConditions().get(i).split(">");
                operands = this.calculate(data,conditions);
                if( operands[0] > operands[1] )
                    res++;
            }
            i++;
        }
        if(res == rule.getConditions().size())
            return rule;
        else
            return null;
    }

    /**
     * gets a couple of data (data1, data2)
     * and an array of conditions and returns
     * the result of the conditions as a couple
     * of integers
     * @param data
     * @param conditions
     * @return int[]
     */
    public int[] calculate(List<String> data,String[]conditions){
        String []tmp = new String[2];
        int[]result= new int[2];
        if(conditions[0].contains("+")){
            tmp = conditions[0].split("\\+");
            if(tmp[0].contains("?x")){
                tmp[0]= data.get(0);
            }
            if(tmp[0].contains("?y")){
                tmp[0]= data.get(1);
            }
            if(tmp[1].contains("?x")){
                tmp[1]= data.get(0);
            }
            if(tmp[1].contains("?y")){
                tmp[1]= data.get(1);
            }
            result[0] = Integer.parseInt(tmp[0]) + Integer.parseInt(tmp[1]);

        }else if(conditions[0].contains("-")){
            tmp = conditions[0].split("\\-");
            if(tmp[0].contains("?x")){
                tmp[0]= data.get(0);
            }
            if(tmp[0].contains("?y")){
                tmp[0]= data.get(1);
            }
            if(tmp[1].contains("?x")){
                tmp[1]= data.get(0);
            }
            if(tmp[1].contains("?y")){
                tmp[1]= data.get(1);
            }
            result[0] = Integer.parseInt(tmp[0]) - Integer.parseInt(tmp[1]);

        }else{
            if(conditions[0].contains("?x"))
                result[0] = Integer.parseInt(data.get(0));
            else if(conditions[0].contains("?y"))
                result[0] = Integer.parseInt(data.get(1));
            else
                result[0] = Integer.parseInt(conditions[0]);
        }
        if(conditions[1].contains("+")){
            tmp = conditions[1].split("\\+");
            if(tmp[0].contains("?x")){
                tmp[0]= data.get(0);
            }
            if(tmp[0].contains("?y")){
                tmp[0]= data.get(1);
            }
            if(tmp[1].contains("?x")){
                tmp[1]= data.get(0);
            }
            if(tmp[1].contains("?y")){
                tmp[1]= data.get(1);
            }
            result[1] = Integer.parseInt(tmp[0]) + Integer.parseInt(tmp[1]);

        } else if(conditions[1].contains("-")){
            tmp = conditions[1].split("\\-");
            if(tmp[0].contains("?x")){
                tmp[0]= data.get(0);
            }
            if(tmp[0].contains("?y")){
                tmp[0]= data.get(1);
            }
            if(tmp[1].contains("?x")){
                tmp[1]= data.get(0);
            }
            if(tmp[1].contains("?y")){
                tmp[1]= data.get(1);
            }
            result[1] = Integer.parseInt(tmp[0]) - Integer.parseInt(tmp[1]);

        }else{

            if(conditions[1].contains("?x"))
                result[1] = Integer.parseInt(data.get(0));
            else if(conditions[1].contains("?y"))
                result[1] = Integer.parseInt(data.get(1));
            else
                result[1] = Integer.parseInt(conditions[1]);
        }
        return result;
    }

    public List<String> conclusionToData(List<String>conclusions, List<String> data){
        List<String> res = new ArrayList<String>();
        String[] tmp = new String[2];
        int[] operated = new int[2];
        String str ="",sss ="";

        for(int i=0; i<conclusions.size();i++){
            if(conclusions.get(i).matches("^[0-9]*$")){
                tmp[i]=conclusions.get(i);
            }else if(conclusions.get(i).equals("?x")){
                tmp[i]=data.get(0);
            }else if(conclusions.get(i).equals("?y")){
                tmp[i]=data.get(1);

            }else{
                if(conclusions.get(i).contains("?x")){
                    str = conclusions.get(i).replaceAll("\\?x",data.get(0));
                }else{
                    str = conclusions.get(i);
                }
                if(str.contains("?y")){
                    sss = str.replaceAll("\\?y",data.get(1));
                }else{
                    sss = str;
                }
                if(sss.contains("-")){
                    operated = this.strToList(sss,"-",i);
                    tmp[i]=operated[0]-operated[1]+"";
                }

                if(sss.contains("+")){
                    operated = this.strToList(sss,"\\+",i);
                    tmp[i]=operated[0]+operated[1]+"";
                }
            }


        }
        res.add(tmp[0]);
        res.add(tmp[1]);
        return res;

    }

    public int[] strToList( String in, String operand, int i){
        String[] operands = in.split(operand,2);
        int [] ops = new int[2];
        for ( int k=0; k<2; k++){
            if(operands[k].contains("-")){
                String _tmp = operands[k].replace("(","");
                String op = _tmp.replace(")","");
                String[] _operands = op.split("-");
                ops[k] = Integer.parseInt(_operands[0]) - Integer.parseInt(_operands[1]);

            }else if(operands[k].contains("+")){
                String _tmp = operands[k].replace("(","");
                String op = _tmp.replace(")","");
                String[] _operands = op.split("\\+");
                ops[k] = Integer.parseInt(_operands[0]) + Integer.parseInt(_operands[1]);

            }else{
                ops[k] = Integer.parseInt(operands[k]);
            }
        }
        return ops;
    }


}
