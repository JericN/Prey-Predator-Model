import java.util.*;


public class Function {

    Random numGen = new Random();

    public int randNum( int max){
        return numGen.nextInt(max);
    }
    public int randNum(int min, int max){
        return numGen.nextInt(max-min)+min;
    }
    public int[] randArray(int min,int max){
        int[] array = new int[max-min];
        for (int i = min; i < max; i++) {
            array[i-min]=i;
        }
        for (int i = min; i < max; i++) {
            int temp = array[i-min];
            int flag = randNum(min,max);
            array[i-min] = array[flag-min];
            array[flag-min] = temp;
        }
        return array;
    }
    public int[][] genMoveArray(){
        int[][] movePath = new int[8][2];
        int k=0;
        for (int i = -1; i <= 1 ; i++) {
            for (int j = -1; j <= 1; j++) {
                if(i!=0 || j!=0){
                    movePath[k][0]=i;
                    movePath[k][1]=j;
                    k++;
                }
            }
        }
        return movePath;
    }

    public int[][] genFoodArray(){
        int[][] foodPath = new int[25][2];
        int k=0;
        for (int p = 0; p<=2 ; p++) {
            for (int i = -p; i<=p ; i++) {
                for (int j = -p; j<=p ; j++) {
                    if(Math.abs(i)>=p || Math.abs(j)>=p){
                        foodPath[k][0]=i;
                        foodPath[k][1]=j;
                        k++;
                    }
                }
            }
        }
        return foodPath;
    }

}
