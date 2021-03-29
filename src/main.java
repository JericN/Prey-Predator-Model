import java.util.ArrayList;
import java.util.HashMap;

public class main{

    public static void main(String[] args){
        Variable variable = new Variable();
        Logic logic = new Logic();
        Frame frame = new Frame();

        variable.setVariable();
        logic.startLogic();
        frame.startFrame();

        frame.doFrame();
        for (int i = 0; i <10000 ; i++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Frame: "+i);
            logic.doLogic();
            frame.doFrame();
        }
    }

}
