import java.util.ArrayList;
import java.util.HashMap;

public class Variable {
    Function function =  new Function();
    static int numRow;
    static int numCol;
    static int numCells;
    static int numPrey;
    static int numPredator;
    static int numGrass;
    static int numGrassLife;
    static int numPreyLife;
    static int numPreyLifeThreshold;
    static int numPredatorLife;
    static int numPredatorLifeThreshold;
    static int numPreyBirth;
    static int numPredatorBirth;
    static int[][] movePath;
    static int[][] foodPath;
    static ArrayList<ArrayList<HashMap<String,String>>> cell;

    public void setVariable(){
        numRow=27;
        numCol=48;
        numCells=numRow*numCol;

        numPrey=40;
        numPredator=5;
        numGrass=70;

        numGrassLife=5;
        numPreyLife = 8;
        numPreyLifeThreshold = 15;

        numPredatorLife = 15;
        numPredatorLifeThreshold = 23;

        numPreyBirth = 18;
        numPredatorBirth = 35;

        cell= new ArrayList<>(numRow);
        movePath=function.genMoveArray();
        foodPath=function.genFoodArray();
    }
}
