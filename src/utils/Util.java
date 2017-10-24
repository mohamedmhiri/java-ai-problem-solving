package utils;

import models.Node;

import java.util.List;
import java.lang.Math;



public class Util {
    public int dataExistsInNodeList(List<Node> open, List<List<String>> util, int i) {
        int indexInNodeList = -99;
        int j = 0;
        while (( j < open.size() )
                && !(
                ( open.get(j).getData().get(0).charAt(0) == util.get(i).get(0).charAt(0) )
                        &&
                        ( open.get(j).getData().get(1).charAt(0) == util.get(i).get(1).charAt(0) )
        )
                ) {
            j++;
        }
        if ( ( j < open.size()) &&( open.get(j).getData().get(0).charAt(0) == util.get(i).get(0).charAt(0) )
                &&
                ( open.get(j).getData().get(1).charAt(0) == util.get(i).get(1).charAt(0) ) ) {
            indexInNodeList = j;
        }
        return indexInNodeList;
    }

    /**
     * first formula
     * @param data
     * @return
     */
    public int calcH(List<String> data){
        int hRes ;
        if(Integer.parseInt(data.get(0)) == 2)
            hRes = 0;
        else if( Integer.parseInt(data.get(0)) + Integer.parseInt(data.get(1)) <2)
            hRes = 7;
        else if( Integer.parseInt(data.get(1)) >2 )
            hRes = 3;
        else
            hRes = 1;
        return hRes;
    }

    public int absH(List<String> data){
        return Math.abs(Integer.parseInt( data.get(0)  ) - 2);
    }


    public boolean nodeComparator(List<String> data, List<String> compared){
        if( ( !compared.get(0).contains("?") && compared.get(1).contains("?")) && data.get(0).charAt(0)==(compared.get(0)).charAt(0))
            return true;
        else if( ( compared.get(0).contains("?") && !compared.get(1).contains("?")) && data.get(1).charAt(0)==(compared.get(1)).charAt(0))
            return true;
        else if(data.get(0).charAt(0)==(compared.get(0)).charAt(0) && data.get(1).charAt(0)==(compared.get(1)).charAt(0))
            return true;
        else
            return false;
    }

    public int calcF(Node node){
        return node.getgVar()+this.calcH(node.getData());
    }

    public int absF(Node node){
        return node.getgVar()+this.absH(node.getData());
    }

    public Node calcMinF(List<Node> open){
        int min = 9999;
        int ind =-1;
        for(int i=0; i<open.size();i++){
            if(this.calcF(open.get(i)) < min){
                min = this.calcF(open.get(i));
                ind = i;
            }

        }
        return open.get(ind);
    }

    public Node absMinF(List<Node> open){
        int min = 9999;
        int ind =-1;
        for(int i=0; i<open.size();i++){
            if(this.absF(open.get(i)) < min) {
                min = this.absF(open.get(i));
                ind = i;
            }
        }
        return open.get(ind);
    }

    /**
     * this function takes a node and a list
     * of nodes as arguments and returns true
     * if node not in the list of nodes
     * @param node
     * @param nodes
     * @return boolean
     */
    public boolean existInNodeList(Node node, List<Node> nodes){
        int var =nodes.size();
        for (int i=0; i<nodes.size();i++){
            if ( !this.nodeComparator(node.getData(),nodes.get(i).getData()) )
                var --;
            else if(this.nodeComparator(node.getData(),nodes.get(i).getData()) && node.getgVar()<nodes.get(i).getgVar()){
                var --;
            }
        }
        if (var ==0)
            return false;
        else
            return true;
    }
}
