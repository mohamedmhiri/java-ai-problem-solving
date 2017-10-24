package models;


import java.util.List;

public class Fact {
    private List<String> hypothesis;
//    public int explication;

    public List<String> getHypothesis(){
        return this.hypothesis;
    }
//    public int getExplication(){
//        return this.explication;
//    }

    public void setHypothesis (List<String> hypothesis){
        this.hypothesis = hypothesis;
    }

//    public void setExplication (int explication){
//        this.explication = explication;
//    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(1000);
        return sb.append("Fact {\n hypothesis : ").append(this.getHypothesis())
                .append("\n}")
                .toString();
    }
}
