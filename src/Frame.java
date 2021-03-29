import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Frame {
    ArrayList<ArrayList<HashMap<String,String>>> cell;
    Function function = new Function();
    int numRow;
    int numCol;
    int numCells;
    int panelHeight;
    int panelWidth;

    JFrame frame;
    JLabel[] label;
    ImageIcon wolf;
    ImageIcon bunny;
    ImageIcon grass;
    ImageIcon wolfGrass;
    ImageIcon bunnyGrass;
    ImageIcon wolf2;
    ImageIcon bunny2;
    ImageIcon grass2;
    ImageIcon wolfGrass2;
    ImageIcon bunnyGrass2;

    public void startFrame(){
        setVariables();
        setFrame();
        setCells();
        setIcons();

    }
    public void doFrame(){
        updateCells();
        SwingUtilities.updateComponentTreeUI(frame);
    }
    public void setVariables(){
        numRow = Variable.numRow;
        numCol = Variable.numCol;
        numCells =Variable.numCells;
        cell = Variable.cell;
        label = new JLabel[numCells];
    }

    public void setFrame(){
        frame = new JFrame("PreyPredator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1224, 722);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setBackground(Color.red);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.white);
    }

    public void setCells() {
        Rectangle r = frame.getBounds();
        panelHeight = ((r.height-47) / numRow);
        panelWidth = ((r.width-24) / numCol);
        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                int flag = (i*numCol)+j;
                label[flag] = new JLabel();
                label[flag].setBounds(5+(panelWidth)*j,5+(panelHeight)*i,panelWidth,panelHeight);
                label[flag].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                frame.add( label[flag]);
            }
        }

    }
    public void setIcons(){
        try{
            Image iWolf = ImageIO.read(new File("img/wolf.png"));
            Image iBunny = ImageIO.read(new File("img/bunny.png"));
            Image iGrass = ImageIO.read(new File("img/grass.png"));
            Image iWolfGrass = ImageIO.read(new File("img/wolfGrass.png"));
            Image iBunnyGrass = ImageIO.read(new File("img/bunnyGrass.png"));
            Image iWolf2 = ImageIO.read(new File("img/wolf2.png"));
            Image iBunny2 = ImageIO.read(new File("img/bunny2.png"));
            Image iGrass2 = ImageIO.read(new File("img/grass2.png"));
            Image iWolfGrass2 = ImageIO.read(new File("img/wolfGrass2.png"));
            Image iBunnyGrass2 = ImageIO.read(new File("img/bunnyGrass2.png"));
            iWolf = iWolf.getScaledInstance(panelWidth, panelHeight,  java.awt.Image.SCALE_SMOOTH);
            iBunny = iBunny.getScaledInstance(panelWidth, panelHeight,  java.awt.Image.SCALE_SMOOTH);
            iGrass = iGrass.getScaledInstance(panelWidth, panelHeight,  java.awt.Image.SCALE_SMOOTH);
            iWolfGrass = iWolfGrass.getScaledInstance(panelWidth, panelHeight,  java.awt.Image.SCALE_SMOOTH);
            iBunnyGrass = iBunnyGrass.getScaledInstance(panelWidth, panelHeight,  java.awt.Image.SCALE_SMOOTH);
            iWolf2 = iWolf2.getScaledInstance(panelWidth, panelHeight,  java.awt.Image.SCALE_SMOOTH);
            iBunny2 = iBunny2.getScaledInstance(panelWidth, panelHeight,  java.awt.Image.SCALE_SMOOTH);
            iGrass2 = iGrass2.getScaledInstance(panelWidth, panelHeight,  java.awt.Image.SCALE_SMOOTH);
            iWolfGrass2 = iWolfGrass2.getScaledInstance(panelWidth, panelHeight,  java.awt.Image.SCALE_SMOOTH);
            iBunnyGrass2 = iBunnyGrass2.getScaledInstance(panelWidth, panelHeight,  java.awt.Image.SCALE_SMOOTH);
            wolf = new ImageIcon(iWolf);
            bunny = new ImageIcon(iBunny);
            grass = new ImageIcon(iGrass);
            wolfGrass = new ImageIcon(iWolfGrass);
            bunnyGrass = new ImageIcon(iBunnyGrass);
            wolf2 = new ImageIcon(iWolf2);
            bunny2 = new ImageIcon(iBunny2);
            grass2 = new ImageIcon(iGrass2);
            wolfGrass2 = new ImageIcon(iWolfGrass2);
            bunnyGrass2 = new ImageIcon(iBunnyGrass2);
        }catch(IOException e){
            System.out.println("ERROR 101");
        }
    }

    public void updateCells() {
        for(int i=0; i<numRow; i++){
            for (int j=0; j<numCol; j++){
                int flag = (i*numCol)+j;
                String type = cell.get(i).get(j).get("type");
                String location = cell.get(i).get(j).get("location");
                if(type.equals("prey")){
                    if(location.equals("grass")){
                        if(function.randNum(10)%2==0){
                            label[flag].setIcon(bunnyGrass);
                        }else{
                            label[flag].setIcon(bunnyGrass2);
                        }

                    }else{
                        if(function.randNum(10)%2==0){
                            label[flag].setIcon(bunny);
                        }else{
                            label[flag].setIcon(bunny2);
                        }
                    }
                }else if(type.equals("predator")){
                    if(location.equals("grass")){
                        if(function.randNum(10)%2==0){
                            label[flag].setIcon(wolfGrass);
                        }else{
                            label[flag].setIcon(wolfGrass2);
                        }
                    }else{
                        if(function.randNum(10)%2==0){
                            label[flag].setIcon(wolf);
                        }else{
                            label[flag].setIcon(wolf2);
                        }
                    }
                }else if(location.equals("grass")){
                    if(function.randNum(10)%2==0){
                        label[flag].setIcon(grass);
                    }else{
                        label[flag].setIcon(grass2);
                    }
                }else{
                    label[flag].setIcon(null);
                }
            }
        }
        SwingUtilities.updateComponentTreeUI(frame);
    }
}
