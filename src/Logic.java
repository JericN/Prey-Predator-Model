import java.util.ArrayList;
import java.util.HashMap;


public class Logic {
    Function function = new Function();
    ArrayList<ArrayList<HashMap<String, String>>> cell;

    int numRow;
    int numCol;
    int numCells;
    int numPrey;
    int numPredator;
    int numGrass;
    int numPreyLife;
    int numPredatorLife;
    int numGrassLife;
    int numPreyBirth;
    int numPredatorBirth;

    int[][] foodPath;
    int[][] movePath;
    int[] cellCheck;
    int[] moveCheck;
    int randX;
    int randY;

    HashMap<String, String> currCell;
    HashMap<String, String> targetCell;

//    type: prey/predator/empty
//    food: predator/prey
//    hunger: hungry/eating
//    state: free/moved
//    location: field/grass
//    grassLife: 0-9
//    objectLife: 0-9
//    reproduction: 0-9

    public void startLogic() {
        System.out.println("startLogic");
        getVariables("initialize");
        setCells();
        updateVariables();
    }

    public void doLogic() {
        getVariables();
        action();
        resetCells();
    }
    public void getVariables(String method) {
        numRow = Variable.numRow;
        numCol = Variable.numCol;
        numCells = Variable.numCells;
        numGrassLife = Variable.numGrassLife;
        numPreyLife = Variable.numPreyLife;
        numPredatorLife = Variable.numPredatorLife;
        numPreyBirth = Variable.numPreyBirth;
        numPredatorBirth = Variable.numPredatorBirth;
        movePath = Variable.movePath;
        foodPath = Variable.foodPath;
        getVariables();
    }
    public void getVariables(){
        numPrey = Variable.numPrey;
        numPredator = Variable.numPredator;
        numGrass = Variable.numGrass;
        cell = Variable.cell;
    }
    public void updateVariables() {
        Variable.numPrey = numPrey;
        Variable.numPredator = numPredator;
        Variable.cell = cell;
    }

    public void setCells() {
        System.out.println("Set Cells");
        for (int i = 0; i < numRow; i++) {
            cell.add(new ArrayList<>(numCol));
            for (int j = 0; j < numCol; j++) {
                cell.get(i).add(new HashMap<>(4));
                targetCell = cell.get(i).get(j);
                targetCell.put("type", "empty");
                targetCell.put("food", "null");
                targetCell.put("hunger", "null");
                targetCell.put("objectLife", "null");
                targetCell.put("reproduction", "null");
                targetCell.put("state", "null");
                targetCell.put("location", "field");
                targetCell.put("grassLife", "null");
            }
        }
        for (int i = 0; i < numPrey; i++) {
            targetCell = findCell("empty");
            addPrey();
        }
        for (int i = 0; i < numPredator; i++) {
            targetCell = findCell("empty");
            addPredator();
        }
        for (int i = 0; i < numGrass; i++) {
            targetCell = findCell("field");
            targetCell.replace("location", "grass");
            targetCell.replace("grassLife", Integer.toString(numGrassLife));
        }

    }

    public HashMap<String, String> findCell(String method) {
        randX = function.randNum(0,numCol);
        randY = function.randNum(0,numRow);
        if (method.equals("empty")) {
            while (!cell.get(randY).get(randX).get("type").equals("empty")) {
                randX = function.randNum(0,numCol);
                randY = function.randNum(0,numRow);
            }
        } else if (method.equals("field")) {
            while (!cell.get(randY).get(randX).get("location").equals("field")) {
                randX = function.randNum(0,numCol);
                randY = function.randNum(0,numRow);
            }
        }
        return cell.get(randY).get(randX);
    }
    public void addPrey(){
        targetCell.replace("type", "prey");
        targetCell.replace("food", "grass");
        targetCell.replace("hunger", "hungry");
        targetCell.replace("objectLife", Integer.toString(numPreyLife));
        targetCell.replace("reproduction", "0");
        targetCell.replace("state", "free");
    }
    public void addPredator(){
        targetCell.replace("type", "predator");
        targetCell.replace("food", "prey");
        targetCell.replace("hunger", "hungry");
        targetCell.replace("objectLife", Integer.toString(numPredatorLife));
        targetCell.replace("reproduction", "0");
        targetCell.replace("state", "free");
    }

    public void action() {
        cellCheck = function.randArray(0,numCells);
        for (int i = 0; i < numCells; i++) {
            int x = cellCheck[i] % numCol;
            int y = (cellCheck[i] - x) / numCol;
            currCell= cell.get(y).get(x);
            if (!currCell.get("type").equals("empty") && currCell.get("state").equals("free")) {
                if(currCell.get("hunger").equals("eating")) {
                    eatGrass();
                }else{
                    findFoodCell(x,y);
                }
                if(currCell.get("state").equals("free")){
                    findMovementCell(x,y);
                }
            }
        }
    }

    public void findFoodCell(int x, int y){
        int[] arrA = function.randArray(1,9);
        int[] arrB = function.randArray(9,25);
        moveCheck = new int[25];
        moveCheck[0]=0;
        System.arraycopy(arrA, 0, moveCheck, 1, 8);
        System.arraycopy(arrB, 0, moveCheck, 9, 16);
        for (int i = 0; i <25 ; i++) {
            int pathY = foodPath[moveCheck[i]][0];
            int pathX = foodPath[moveCheck[i]][1];
            int checkY = y+pathY;
            int checkX = x+pathX;
            try{
                targetCell = cell.get(checkY).get(checkX);
            }catch(IndexOutOfBoundsException e){
                continue;
            }
            if(currCell.get("food").equals(targetCell.get("type")) || currCell.get("food").equals(targetCell.get("location"))){
                if(Math.abs(pathY) <2 && Math.abs(pathX)<2 ){
                    eatObject(x,y);
                }else{
                    if(Math.abs(pathY)==2){
                        checkY=y+(pathY/2);
                    }
                    if(Math.abs(pathX)==2){
                        checkX=x+(pathX/2);
                    }
                    targetCell = cell.get(checkY).get(checkX);
                    if(targetCell.get("type").equals("empty")){
                        moveObject();
                    }else{
                        findMovementCell(x, y);
                    }
                }
                break;
            }
        }
    }
    public void findMovementCell(int x, int y){
        moveCheck=function.randArray(0,8);
        for (int i = 0; i <8 ; i++) {
            int checkY = y+movePath[moveCheck[i]][0];
            int checkX = x+movePath[moveCheck[i]][1];
            try{
                targetCell = cell.get(checkY).get(checkX);
            }catch(IndexOutOfBoundsException e){
                continue;
            }
            if(targetCell.get("type").equals("empty")){
                moveObject();
                break;
            }
        }
    }

    public void eatObject(int x, int y){
        int life = Integer.parseInt(currCell.get("objectLife"));
        if(currCell.get("type").equals("predator")){
            life+=5;
            if(life>numPredatorLife){
                life=numPredatorLife;
            }
            currCell.replace("objectLife",Integer.toString(life));
            moveObject();
        }else{
            if(currCell.equals(targetCell)){
                eatGrass();
            }else if(!targetCell.get("type").equals("empty")){
                findMovementCell(x, y);
            }else if(targetCell.get("type").equals("empty")){
                moveObject();
            }else{
                System.out.println("fuck");
            }
        }
    }
    public void moveObject(){
        targetCell.replace("type",currCell.get("type"));
        targetCell.replace("food",currCell.get("food"));
        targetCell.replace("hunger",currCell.get("hunger"));
        targetCell.replace("objectLife",currCell.get("objectLife"));
        targetCell.replace("reproduction",currCell.get("reproduction"));
        targetCell.replace("state","moved");
        removeObject();
    }
    public void removeObject(){
        currCell.replace("type","empty");
        currCell.replace("food","null");
        currCell.replace("hunger","null");
        currCell.replace("objectLife","null");
        currCell.replace("reproduction","null");
        currCell.replace("state","null");
        if(targetCell!=null){
            currCell= targetCell;
        }
    }

    public void eatGrass(){
        int preyLife = Integer.parseInt(currCell.get("objectLife"));
        int grassLife = Integer.parseInt(currCell.get("grassLife"))-1;
        preyLife +=3;
        if(preyLife>numPreyLife){
            preyLife=numPreyLife;
        }
        if(grassLife==0){
            grassLife=-3;
            currCell.replace("hunger","hungry");
        }else{
            currCell.replace("hunger","eating");
        }
        currCell.replace("objectLife", Integer.toString(preyLife));
        currCell.replace("grassLife", Integer.toString(grassLife));
        currCell.replace("state","moved");
    }

    public void reproduction(int x, int y){
        int birth= Integer.parseInt(currCell.get("reproduction"));
        birth++;
        if(currCell.get("type").equals("prey") && birth>numPreyBirth){
            if(findBirthCell(x,y)){
                addPrey();
                birth=0;
            }
        }else if(currCell.get("type").equals("predator") && birth>numPredatorBirth){
            if(findBirthCell(x,y)){
                addPredator();
                birth=0;
            }
        }
        currCell.replace("reproduction", Integer.toString(birth));
    }
    public boolean findBirthCell(int x, int y){
        moveCheck=function.randArray(0,8);
        for (int i = 0; i <8 ; i++) {
            int checkY = y+movePath[moveCheck[i]][0];
            int checkX = x+movePath[moveCheck[i]][1];
            try{
                targetCell = cell.get(checkY).get(checkX);
            }catch(IndexOutOfBoundsException e){
                continue;
            }
            if(targetCell.get("type").equals("empty")){
                return true;
            }
        }
        return false;
    }

    public void objectLife(){
        int life = Integer.parseInt(currCell.get("objectLife"))-1;
        System.out.println(life+1);
        if(life==0){
            removeObject();
        }else{
            currCell.replace("objectLife",Integer.toString(life));
        }
    }
    public void grassLife(){
        int grassLife = Integer.parseInt(currCell.get("grassLife"));
        if(currCell.get("hunger").equals("eating")){
            return;
        }else if(grassLife<5){
            grassLife++;
        }
        if(grassLife<1){
            currCell.replace("location","field");
        }else{
            currCell.replace("location","grass");
        }
        currCell.replace("grassLife",Integer.toString(grassLife));
    }
    public void resetCells(){
        int preyCount=0;
        targetCell=null;
        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                currCell = cell.get(i).get(j);
                if(!currCell.get("type").equals("empty")){
                    currCell.replace("state", "free");
                    preyCount++;
                }
                if(!currCell.get("grassLife").equals("null")){
                    grassLife();
                }
                if(!currCell.get("type").equals("empty")){
                    objectLife();
                }
                if(!currCell.get("type").equals("empty")){
                    reproduction(j,i);
                }
            }
        }
    }


}
