package models;

import java.util.List;

public class Goal {

    private List<String> conclusions;

    public List<String> getConclusions() {
        return conclusions;
    }


    public void setConclusions(List<String> conclusions) {
        this.conclusions = conclusions;
    }
    @Override
    public String toString() {
        return "Goal{" +
                "conclusions=" + conclusions +
                '}';
    }
}
