
import java.util.ArrayList;
import java.lang.Integer;


public class grid {
    public int[][] board;
    public static int NUM_COLS;
    public static int NUM_ROWS;
    public ArrayList<Integer> empty;
    public int numEmpty;


    //constructors, one for the default 4 by 4, and one for whatever size you want.
    public grid(){
        board = new int[4][4];
        NUM_COLS = 4;
        NUM_ROWS = 4;
        empty = new ArrayList<Integer>();
        for(int index = 0; index < 16; index++){
            empty.add(index);
        }
        numEmpty = 16;
    }

    public grid(int numRows, int numCols){
        board = new int[numRows][numCols];
        NUM_COLS = numCols;
        NUM_ROWS = numRows;
        empty = new ArrayList<Integer>();
        for(int index = 0; index < numRows*numCols; index++){
            empty.add(index);
        }
        numEmpty = numRows*numCols;
    }

    //if a row can move, returns true. If a column can move, returns true.
    private boolean canMoveRow(int row){
        for(int index = 0; index < NUM_COLS; index++){
            if(board[row][index] == 0)
                return true;
            if(index < NUM_COLS - 1){
                if(board[row][index] == board[row][index + 1]){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canMoveCol(int col){
        for(int index = 0; index < NUM_ROWS; index++){
            if(board[index][col] == 0){
                return true;
            }
            if(index < NUM_ROWS - 1){
                if(board[index][col] == board[index+1][col]){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMove(){
        if(numEmpty > 0)
            return true;
        for(int row = 0; row < NUM_ROWS; row++){
            if(canMoveRow(row) == true)
                return true;
        }
        for(int col = 0; col < NUM_COLS; col++){
            if(canMoveCol(col) == true)
                return true;
        }
        return false;
    }

    /* returns true if a peice was successfully added*/
    public boolean addRandomPeice(){
        if(numEmpty ==  0) return false;
        int emptyindex = (int) (Math.random()*numEmpty);
        Integer index = empty.remove(emptyindex);
        int row = (int) index / NUM_ROWS;
        int col = index % NUM_COLS;
        board[row][col] = ((int) (Math.random()*2))*2 + 2;
        numEmpty--;
        return true;
    }

    public boolean addPeice(int row, int col){
        if(numEmpty ==  0) return false;
        if(row >= NUM_ROWS || row < 0 || col >= NUM_COLS || col < 0) return false;
        if(empty.contains(Integer.valueOf(row*NUM_COLS + col))){
            empty.remove(Integer.valueOf(row*NUM_COLS + col));
            board[row][col] = ((int) (Math.random()*2))*2 + 2;
            return true;
        }
        numEmpty--;
        return false;
    }

    /*moves should make it act as though it were a 2048 game. Helpers move all the peices so there there is no zeros inbetween, 
    and the other helper merges the numbers if need be. Both update the empty ArrayList when new tiles are emptied and filled through moving  */
    
    
    private boolean mergeRowLeft(int row){
        boolean hasMerged = false;
        for(int col = 0; col < NUM_COLS - 1; col++){
            if(board[row][col] == 0)
                continue;
            else if(board[row][col] == board[row][col+1]){
                hasMerged = true;
                board[row][col] *= 2;
                board[row][col+1] = 0;
                empty.add(Integer.valueOf(row*NUM_COLS + col + 1));
                numEmpty++;
            }
        }
        return hasMerged;
    }

    private boolean moveRowLeft(int row){
        boolean hasMoved = false;
        boolean zerosBefore = false;
        int numsBefore = 0;
        for(int col = 0; col < NUM_COLS; col++){
            if(board[row][col] == 0){
                zerosBefore = true;
            }
            else{
                if(zerosBefore){
                    board[row][numsBefore] = board[row][col];
                    board[row][col] = 0;
                    empty.remove(Integer.valueOf(row*NUM_COLS + numsBefore));
                    empty.add(Integer.valueOf(row*NUM_COLS + col));
                    hasMoved = true;
                }
                numsBefore++;
            }
        }
        return hasMoved;
    }

    public boolean moveLeft(){
        boolean hasMoved = false;
        for(int row = 0; row<NUM_ROWS; row++){
            if(this.moveRowLeft(row)){
                hasMoved = true;
            }
            if(this.mergeRowLeft(row)){
                this.moveRowLeft(row);
                hasMoved = true;
            }
        }
        return hasMoved;
    }

    private boolean mergeRowRight(int row){
        boolean hasMerged = false;
        for(int col = NUM_COLS - 1; col > 0; col--){
            if(board[row][col] == 0)
                continue;
            else if(board[row][col] == board[row][col-1]){
                hasMerged = true;
                board[row][col] *= 2;
                board[row][col-1] = 0;
                empty.add(Integer.valueOf(row*NUM_COLS + col - 1));
                numEmpty++;
            }
        }
        return hasMerged;
    }

    private boolean moveRowRight(int row){
        boolean hasMoved = false;
        boolean zerosBefore = false;
        int numsBefore = 0;
        for(int col = NUM_COLS -  1; col >= 0; col--){
            if(board[row][col] == 0){
                zerosBefore = true;
            }
            else{
                if(zerosBefore){
                    board[row][NUM_COLS - 1 - numsBefore] = board[row][col];
                    board[row][col] = 0;
                    empty.remove(Integer.valueOf(row*NUM_COLS + NUM_COLS - 1 - numsBefore));
                    empty.add(Integer.valueOf(row*NUM_COLS + col));
                    hasMoved = true;
                }
                numsBefore++;
            }
        }
        return hasMoved;
    }

    public boolean moveRight(){
        boolean hasMoved = false;
        for(int row = 0; row<NUM_ROWS; row++){
            if(this.moveRowRight(row))
                hasMoved = true;
            if(this.mergeRowRight(row)){
                this.moveRowRight(row);
                hasMoved = true;
            }
        }
        return hasMoved;
    }


    private boolean mergeColUp(int col){
        boolean hasMerged = false;
        for(int row = 0; row < NUM_ROWS - 1; row++){
            if(board[row][col] == 0)
                continue;
            else if(board[row][col] == board[row+1][col]){
                hasMerged = true;
                board[row][col] *= 2;
                board[row+1][col] = 0;
                empty.add(Integer.valueOf((row+1)*NUM_COLS + col));
                numEmpty++;
            }
        }
        return hasMerged;
    }

    private boolean moveColUp(int col){
        boolean hasMoved = false;
        boolean zerosBefore = false;
        int numsBefore = 0;
        for(int row = 0; row < NUM_ROWS; row++){
            if(board[row][col] == 0){
                zerosBefore = true;
            }
            else{
                if(zerosBefore){
                    board[numsBefore][col] = board[row][col];
                    board[row][col] = 0;
                    empty.remove(Integer.valueOf(numsBefore*NUM_COLS + col));
                    empty.add(Integer.valueOf(row*NUM_COLS + col));
                    hasMoved = true;
                }
                numsBefore++;
            }
        }
        return hasMoved;
    }

    public boolean moveUp(){
        boolean hasMoved = false;
        for(int col = 0; col < NUM_COLS; col++){
            if(this.moveColUp(col)){
                hasMoved = true;
            }
            if(this.mergeColUp(col)){
                hasMoved = true;
                this.moveColUp(col);
            }
        }
        return hasMoved;
    }

    private boolean mergeColDown(int col){
        boolean hasMerged = false;
        for(int row = NUM_ROWS - 1; row > 0; row--){
            if(board[row][col] == 0)
                continue;
            else if(board[row][col] == board[row-1][col]){
                hasMerged = true;
                board[row][col] *= 2;
                board[row-1][col] = 0;
                empty.add(Integer.valueOf((row-1)*NUM_COLS + col));
                numEmpty++;
            }
        }
        return hasMerged;
    }

    private boolean moveColDown(int col){
        boolean hasMoved = false;
        boolean zerosBefore = false;
        int numsBefore = 0;
        for(int row = NUM_ROWS - 1; row >= 0; row--){
            if(board[row][col] == 0){
                zerosBefore = true;
            }
            else{
                if(zerosBefore){
                    board[NUM_ROWS - 1 - numsBefore][col] = board[row][col];
                    board[row][col] = 0;
                    empty.remove(Integer.valueOf((NUM_ROWS - 1 - numsBefore)*NUM_COLS + col));
                    empty.add(Integer.valueOf(row*NUM_COLS + col));
                    hasMoved = true;
                }
                numsBefore++;
            }
        }
        return hasMoved;
    }
    public boolean moveDown(){
        boolean hasMoved = false;
        for(int col = 0; col < NUM_COLS; col++){
            if(this.moveColDown(col)){
                hasMoved = true;
            }
            if(this.mergeColDown(col)){
                this.moveColDown(col);
                hasMoved = true;
            }
        }
        return hasMoved;
    }

    public boolean clear(){
        for(int row = 0; row< NUM_ROWS; row++){
            for(int col = 0; col < NUM_COLS; col++){
                board[row][col] = 0;
            }
        }
        return true;
    }

    public String toString(){
        String sboard = "";
        for(int[] row : board){
            sboard += "|";
            for(int num : row){
                sboard += Integer.toString(num);
                sboard += ",";
            }
            sboard += "|\n";
        }
        return sboard;
    }
    /*
    public static void main(String[] args){
        grid newGame = new grid();
        boolean play = true;
        int move = 1;
        while(play){
            newGame.addRandomPeice();
            System.out.println(newGame);
            System.out.println(newGame.empty.toString());
            System.out.println(Integer.valueOf(newGame.numEmpty));
            if(move == 1){
                newGame.moveLeft();
            }
            if(move == 3){
                newGame.moveDown();
            }
            if(move == 2){
                newGame.moveUp();
            }
            if(move > 4){
                newGame.moveDown();
            }
            if(move == 0){
                play = false;
            }
            move++;
            if(move > 12){
                move = 0;
            }
        }

    }
    */
}


    /*
    my attempt at generalizing a move funciton. Not sure how it's supposed to work. 
    I've given up, and now I'm just gonna create four funtions that do pretty much the same thing.





    If we are moving up or down, then rowOrCol = NUM_COLS;
    Right or left, rowOrCol is set to 1;
    direction : up and left are positive 1, down and right are negative 1,

    */


    /*
    public boolean moveOneGeneralized(int rowOrCol, int direction, int whichone){
        boolean zerosBefore = false;
        int numsBefore = 0;
        int totalChange = rowOrCol*NUM_COLS*direction;
        int start = whichone;
        int end = start+totalChange;
        int changesby = rowOrCol*direction;

        
        for(int index = start; index*direction < end; index += changesby){
            int row = index / NUM_ROWS;
            int col = index % NUM_COLS;
            if(board[row][col] == 0){
                zerosBefore = true;
            }
            else{
                if(zerosBefore){
                    board[row][numsBefore] = board[row][col];
                    board[row][col] = 0;
                    empty.remove(Integer.valueOf(row*NUM_COLS + numsBefore));
                    empty.add(Integer.valueOf(row*NUM_COLS + col));
                }
                numsBefore++;
            }
        }
        if(!zerosBefore || numsBefore == 0){
            return false;
        }
        return true;
    }

    */