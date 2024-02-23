import javax.swing.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TetrisBot extends Thread {
    private Block[][] gameBoard;
    GameScreen screen;
    int currentPiece, curX, curY;
    int nextEmpty, currentLoc;

    /**
     * Constructor. Creates the tetris bot thread. Takes in the GameScreen object that the thread will run on.
     * @param screen
     */
    public TetrisBot(GameScreen screen){
        this.screen = screen;
        this.gameBoard = screen.getGameBoard();
        this.currentPiece = screen.currentPiece;
        this.curX = screen.curX;
        this.curY = screen.curY;
    }

    /**
     * Overrides the run method from the thread class. Sets a new speed for the bot to run at.
     * Loops through checking the rows for the best move, reading the piece's current location, and
     * moving the piece.
     */
    public void run(){
        screen.speed = 150;
        while(true) {
            nextEmpty = checkRows();
            currentLoc = readCurLocation();
            doNextAction(nextEmpty, currentLoc);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
                JOptionPane.showMessageDialog( screen, "Game Over", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                screen.dispose();
                System.exit(0);
            }

        }

    }

    /**
     * Checks the rows of the game board to find the best position for the piece.
     * Searches for the deepest holes in the game board.
     * @return X value of the best location to move the block to.
     */
    public int checkRows(){
        HashMap<Integer, Integer> options= new HashMap<>();
        for (int cl = 0; cl < 10; cl++){
            boolean ReachedFill = false;
            for (int rw = screen.currentTopY; rw <= 19; rw++){
                if (!gameBoard[rw][cl].isFilled && !ReachedFill ){
                    if (options.containsKey(cl)){
                        int count = options.get(cl);
                        count++;
                        options.put(cl, count); // increment associated count
                    }
                    else{
                        options.put(cl,1);
                    }

                }
                else{
                    ReachedFill = true;
                }
            }

        }
        int max = Collections.max(options.values());

        for (Map.Entry<Integer, Integer> entry : options.entrySet()){
            if (entry.getValue() == max){
                return entry.getKey();
            }
        }
        return 99;
    }

    /**
     * Reads the current location of the bottom-left most block of the piece.
     * @return the x value of the bottom-left most block of the piece
     */
    public int readCurLocation(){
        String nextAction;
        int[][] currentPieceArr= Piece.Pieces(currentPiece);

        for(int rw = 3; rw >= 0; rw--){
            for (int cl = 0; cl < 4; cl++ ){
                if (currentPieceArr[rw][cl] == 1){
                    return screen.curX+cl;
                }
            }
        }

    return 99;
    }

    /**
     * Moves the piece to the correct position. Reads the current position of the piece and the desired position
     * and calls the moveRight() and moveLeft() functions from the gamescreen class.
     * @param nextEmpty
     * @param curLoc
     */
    public void doNextAction(int nextEmpty, int curLoc){
        while (nextEmpty < curLoc){

            screen.moveLeft();
            curX = screen.curX;
            nextEmpty = checkRows();
            curLoc = readCurLocation();
        }
        while (nextEmpty > curLoc){
            screen.moveRight();
            curX = screen.curX;
            nextEmpty = checkRows();
            curLoc = readCurLocation();

        }
    }

}
