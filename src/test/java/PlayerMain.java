import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerMain {
    public static void main(String[] args){
        Point pos = new Point(200,100);
        Player pl = new Player(pos);
        pl.info();
    }
}
