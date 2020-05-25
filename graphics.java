
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.event.*;

public class graphics extends JFrame implements KeyListener{
    grid board;
    boolean canMove;
    
    public graphics(){
        super("2048");
        setSize(600, 600);
        setVisible(true);
        board = new grid();
        addKeyListener(this);
        canMove = true;
    }

    public graphics(grid b){
        super("2048");
        setSize(20+120*grid.NUM_COLS, 40+120*grid.NUM_ROWS);
        setVisible(true);
        board = b;
        addKeyListener(this);
        canMove = true;
    }

    public void paint (Graphics g){
        int padheight = 20;
        int padwidth = 20;
        int height  = 100;
        int width = 100;
        g.setColor(Color.gray);
        g.fillRect(0, 0, 20+120*grid.NUM_COLS, 40+120*grid.NUM_ROWS);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        System.out.println(board);
        for(int row = 0; row < grid.NUM_ROWS; row++){
            for(int col = 0; col < grid.NUM_COLS; col++){
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(padwidth + (padwidth+width)*col, 2*padheight + (padheight+height)*row, width, height);
                if(board != null){
                    int value = board.board[row][col];
                    if(value >0){
                        g.setColor(new Color(0.1f, (float) 1.0 / value, (float) ((1.0 / 11) * Math.log(value))));
                        g.fillRect(padwidth + (padwidth+width)*col, 2*padheight + (padheight+height)*row, width, height);
                        g.setColor(Color.lightGray);
                        g.drawString(Integer.toString(value), (padwidth+width)*col + 25, 2*padheight + (padheight+height)*row + height/2 + padheight/2);
                    }
                }
            }
        }
        if(!canMove){
            g.setColor(Color.black);
            g.fillRect(0, 0, 20+120*grid.NUM_COLS, 40+120*grid.NUM_ROWS);
        }
    }

    public void keyPressed(KeyEvent e){
    }

    public void keyReleased(KeyEvent e){
        int i = e.getKeyCode();
        if(move(i)){
            board.addRandomPeice();
            if(!board.canMove()){
                canMove = false;
            }
        }
        repaint();
    }

    public void keyTyped(KeyEvent e){
    }

    public boolean move(int i){
        if(i == 37) {
            System.out.println(board);
            return board.moveLeft();
        }
        if(i == 38){
            System.out.println(board);
            return board.moveUp();
        }
        if(i == 39) {
            System.out.println(board);
            return board.moveRight();
        }
        if(i == 40) {
            System.out.println(board);
            return board.moveDown();
        }
        System.out.println(board);
        return false;
    }

}