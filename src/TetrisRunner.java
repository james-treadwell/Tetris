import java.util.Random;

public class TetrisRunner {
    private static int[][] board = new int[20][10];
    private static int currentPiece;
    private static int currentScore;
    private static int currentLevel;
    private static int curX;
    private static int curY;

    public static void addScore() {
        currentScore += 40 * currentLevel;
    }

    public void moveDown(){
        //if (isValidMove(currentPiece, curX, curY))curY++;
        if (true){
            int[][] thisShape = Piece.Pieces(currentPiece);
            for (int i = 0; i < thisShape.length; i++) {
                for (int j = 0; j < thisShape[i].length; j++) {
                    board[curY + i][curX + j] = 0;
                }
            }
        }
        else{
            placePiece();
            checkRows();
            currentPiece = nextPiece();
            curY = 0;
            curX = 3;
        }
        int[][] thisShape = Piece.Pieces(currentPiece);
        for (int i = 0; i < thisShape.length; i++) {
            for (int j = 0; j < thisShape[i].length; j++) {
                    board[curY + i][curX + j] = thisShape[i][j];
            }
        }
    }

    public static void placePiece(){
        int[][] thisShape = Piece.Pieces(currentPiece);
        for (int i = 0; i < thisShape.length; i++) {
            for (int j = 0; j < thisShape[i].length; j++) {
                if (thisShape[i][j] != 0) {
                    board[curY + i][curX + j] = thisShape[i][j];
                }
            }
        }
    }

    public static boolean isValidMove(int piece,int x, int y ) {
        int[][] thisShape = Piece.Pieces(piece);
        for (int i = 0; i < thisShape.length; i++) {
            for (int j = 0; j < thisShape[i].length; j++) {
                if (thisShape[i][j] != 0) {
                    int newX = x + j;
                    int newY = y + i;

                    if (newX < 0 || newX >= 10 || newY >= 20) {
                        return false;  // Outside the board boundaries
                    }

                    if (newY >= 0 && board[newY][newX] != 0) {
                        return false;  // Hits another block
                    }
                }
            }
        }
        return true;
    }
    public static int nextPiece(){
        Random rand = new Random();
        return rand.nextInt(7);
    }

    public static void checkRows(){
        for (int rw = 19; rw > 0; rw--){
            boolean fullLine = true;
            for (int cl = 0; cl < 10; cl++){
                if (board[rw][cl] == 0){
                    fullLine = false;
                    break;
                }
            }
            if (fullLine){
                clearRow(rw);
                moveRowsDown(rw);
            }
        }
        addScore();
        //if (currentScore >= )
    }
    public static void clearRow(int row){
        for(int col = 0; col < board.length; col++){
            board[row][col] = 0;
        }
    }

    public static void moveRowsDown(int del){
        for (int r = del; r > 0; r--){
            for(int c = 0; c < 10; c++){
                board[r][c] = board[r-1][c];
            }
        }
    }

    /**
     * Method rotates the piece 90 degrees
     * @param x is the piece you want to rotate
     * @return a piece that is rotated
     */
    public static int rotatePiece(int x){
        currentPiece = x;
        //I-BLOCK ROTATIONS
        if (currentPiece == 0)currentPiece = 7;
        else if (currentPiece == 7)currentPiece = 0;
        //J-BLOCK ROTATIONS
        else if (currentPiece == 1)currentPiece = 8;
        else if (currentPiece == 8)currentPiece = 9;
        else if (currentPiece == 9)currentPiece = 10;
        else if (currentPiece == 10)currentPiece = 1;
        //L-BLOCK ROTATIONS
        else if (currentPiece == 2)currentPiece = 11;
        else if (currentPiece == 11)currentPiece = 12;
        else if (currentPiece == 12)currentPiece = 13;
        else if (currentPiece == 13)currentPiece = 2;
        //S-BLOCK ROTATIONS
        else if (currentPiece == 4)currentPiece = 14;
        else if (currentPiece == 14)currentPiece = 4;
        //T-BLOCK ROTATIONS
        else if (currentPiece == 5)currentPiece = 15;
        else if (currentPiece == 15)currentPiece = 16;
        else if (currentPiece == 16)currentPiece = 17;
        else if (currentPiece == 17)currentPiece = 5;
        //Z-BLOCK ROTATIONS
        else if (currentPiece == 6)currentPiece = 18;
        else if (currentPiece == 18)currentPiece = 6;
        return currentPiece;
    }

/**
    public static void main(String[] args) {
        //nextPiece();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        moveDown();
        for (int trow = 0; trow < board.length; trow++){
            for (int tcol = 0; tcol < board[0].length; tcol++){
                System.out.print(board[trow][tcol]);
            }
            System.out.println();
        }
    }
 */
}
