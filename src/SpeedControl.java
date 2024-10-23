import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpeedControl extends JPanel implements ActionListener{
    private int speed = 0;
    private final int SPEED_CHANGE = 1;
    private int positionX = 50;
    private Timer timer;

    public SpeedControl(){
        timer = new Timer(100, e -> movePoint());
        timer.start();

        JButton increaseSpeedButton = new JButton("Збiльшити швидкiсть");
        increaseSpeedButton.addActionListener(e -> increaseSpeed());
        JButton decreaseSpeedButton = new JButton("Зменшити швидкiсть");
        decreaseSpeedButton.addActionListener(e -> decreaseSpeed());

        setLayout(new FlowLayout());
        add(increaseSpeedButton);
        add(decreaseSpeedButton);
    }

    private void increaseSpeed(){
        speed += SPEED_CHANGE;
        System.out.println("Швидкість: " + speed);
    }

    private void decreaseSpeed(){
        //speed += SPEED_CHANGE;
        speed = Math.max(0, speed - SPEED_CHANGE);
        System.out.println("Швидкість: " + speed);
    }

    private void movePoint(){
        positionX += speed;
        if(positionX > getWidth()){
            positionX = 0;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.fillOval(positionX, 50, 20, 20);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Керування швидкiстю");
        SpeedControl panel = new SpeedControl();
        frame.add(panel);
        frame.setSize(500,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}