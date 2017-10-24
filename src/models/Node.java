package models;

import java.util.List;


public class Node {
    public Node() {
    }

    public Node(List<String> data, int gVar) {
        this.data = data;
        this.gVar = gVar;

    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", gVar=" + gVar +
                '}';
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public int getgVar() {
        return gVar;
    }

    public void setgVar(int gVar) {
        this.gVar = gVar;
    }

    private List<String> data ;
    private int gVar;


}
