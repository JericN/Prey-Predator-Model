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
    static int numPredatorLife;
    static int numPreyBirth;
    static int numPredatorBirth;
    static int numReproduction;
    static int[][] movePath;
    static int[][] foodPath;
    static ArrayList<ArrayList<HashMap<String,String>>> cell;

    public void setVariable(){
        numRow=18;
        numCol=32;
        numCells=numRow*numCol;
        numPrey=40;
        numPredator=4;
        numGrass=50;
        numGrassLife=5;
        numPreyLife = 8;
        numPredatorLife = 17;
        numPreyBirth = 10;
        numPredatorBirth = 20;
        cell= new ArrayList<>(numRow);
        movePath=function.genMoveArray();
        foodPath=function.genFoodArray();
    }
}
