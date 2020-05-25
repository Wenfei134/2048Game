
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Integer;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class My2048{
    public grid game;
    graphics app;

    //constructors
    public My2048(){
        game = new grid();
        app = new graphics(game);
    }

    public My2048(grid g){
        game = g;
        app = new graphics(game);
    } 

    public My2048(int row, int col){
        game = new grid(row, col);
        app = new graphics(game);
    }

    public void playGame(){
        app.addWindowListener(
            new WindowAdapter(){
                public void windowClosing( WindowEvent e){
                    System.exit( 0 );
                }
            }
        );
        game.addRandomPeice();
        app.repaint();
    }

    public void reset(){
        game.clear();
        game.addRandomPeice();
        app.repaint();
    }


    public static void main(String args[]){
        My2048 Game = new My2048(6,6);
        Game.playGame();
    }
}