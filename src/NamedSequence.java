
public class NamedSequence {
    private String fullName, shortName;
    private int total, correct;
    public String getFullName(){
        return fullName;
    }
    public String getShortName(){
        return shortName;
    }
    NamedSequence(String f, String s){
        fullName = f;
        shortName = s;
        total = 0;
        correct = 0;
    }
    double getWinrate(){
        if(correct != 0)return correct / total;
        return -1;
    }
    void addScore(boolean c){
        total++;
        if(c)correct++;
    }

}
