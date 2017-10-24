package models;

import java.util.List;
import java.lang.StringBuilder;

public class Rule {


    private List<String> data;
    private List<String> conditions;
    //a couple of data too
    private List<String> conclusions;
    private int number;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }



        public List<String> getConclusions(){
            return this.conclusions;
        }
        public int getNumber(){
            return this.number;
        }



        public void setConclusions (List<String> conclusions){
            this.conclusions = conclusions;
        }

        public void setNumber (int number){
            this.number = number;
        }
        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder(1000);
            sb.append("Rule {\n number : ").append(this.getNumber())
                    .append(" \n premises : (").append(this.getData().get(0))
                    .append(", ")
                    .append(this.getData().get(1))
                    .append(')');
                    for (int i=0; i<this.getConditions().size();i++)
                        sb.append(" && ")
                        .append(this.getConditions().get(i));
                        sb.append("\n conclusions : ").append(this.getConclusions())
                    .append("\n}");
            return sb.toString();
        }

}
