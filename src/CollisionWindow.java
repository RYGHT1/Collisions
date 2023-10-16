import javax.swing.*;
import java.awt.*;

public class CollisionWindow extends JFrame {
    public static final int SIZE = 1000;
    CollisionWindow(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(false);
        this.setSize(SIZE,SIZE);
        this.setLocationRelativeTo(null);

        this.add(new BallPanel());

        this.setVisible(true);
    }
}
