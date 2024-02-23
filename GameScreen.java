import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.awt.event.KeyEvent.*;

public class GameScreen extends JFrame implements ActionListener, KeyListener {
    private int userLevel, userScore;
    private JTextPane userScoreText, userLevelText;
    private Font fontObject = null;
    private int numLevels;
    public int speed = 800;
    private Block[][] gameBoard;
    private boolean curClear;
    private Image nextBlock = new ImageIcon("blank.png").getImage();
    private Image lBlock = new ImageIcon("Test.png").getImage();
    private JLabel nextBlockLabel;
    private JButton homeButton;
    private Image defaultImage = new ImageIcon("download.png").getImage();
    private ImageIcon defaultImageIcon = new ImageIcon(defaultImage.getScaledInstance(59, 45, Image.SCALE_SMOOTH));
    private String[] colors = {"blueBlock1.png","greenBlock.png","orangeBlock.png", "redBlock.png",
            "lightBlueBlock.png", "purpleBlock.png", "yellowBlock.png"};
    private TetrisRunner runner = new TetrisRunner();
    private JPanel thisPanel = new JPanel();

    private String currentColor;
    private Font arcadeFont;
    public int currentPiece, curX, curY;
    private boolean isRunning = true;
    private HashMap<String, Integer> leaderBoard = new HashMap<>();
    private File scoresFile = new File("highscores");
    private int nexB, checko;
    public int currentTopY = 19;
    private boolean botPlaying = false;

    private TetrisBot myBot;
    public GameScreen(){
        super("Tetris - Game");
        try {
            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new File("ARCADE_N.ttf")).deriveFont(19f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(arcadeFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        this.setFocusable(true);
        addKeyListener(this);


        this.fontObject = new Font("Serif", Font.BOLD, 25);

        this.setSize(635,1000);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        thisPanel.setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(20,10));
        centerPanel.setBackground(Color.BLACK);

        this.gameBoard = makeGameBoard(centerPanel);

        thisPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.BLACK);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(14,1));
        rightPanel.setBackground(Color.BLACK);
        JTextPane nextPieceText = new JTextPane();
        nextPieceText.setFont(this.fontObject);
        nextPieceText.setText("  NextPiece: \n");
        nextPieceText.setForeground(Color.pink);
        nextPieceText.setBackground(Color.black);
        rightPanel.add(nextPieceText);

        ImageIcon nextBlockPic = new ImageIcon(this.nextBlock.getScaledInstance(150, 100, Image.SCALE_SMOOTH));
        nextBlockLabel = new JLabel(nextBlockPic);
        rightPanel.add(nextBlockLabel);

        rightPanel.add(new JLabel());

        JTextPane scoreText = new JTextPane();
        scoreText.setFont(this.fontObject);
        scoreText.setText("  Score: ");
        scoreText.setForeground(Color.pink);
        scoreText.setBackground(Color.black);
        rightPanel.add(scoreText);

        userScoreText = new JTextPane();

        userScoreText.setFont(this.fontObject);
        this.userScore = this.getUserScore();
        userScoreText.setText("  " + String.valueOf(this.userScore));
        userScoreText.setForeground(Color.pink);
        userScoreText.setBackground(Color.black);
        rightPanel.add(userScoreText);

        rightPanel.add(new JLabel());

        JTextPane levelText = new JTextPane();
        levelText.setFont(this.fontObject);
        levelText.setText("  Level: ");
        levelText.setForeground(Color.pink);
        levelText.setBackground(Color.black);
        rightPanel.add(levelText);

        userLevelText = new JTextPane();
        userLevelText.setFont(this.fontObject);
        this.userLevel = getUserLevel();
        userLevelText.setText("  "+ String.valueOf(this.userLevel));
        userLevelText.setForeground(Color.pink);
        userLevelText.setBackground(Color.black);
        rightPanel.add(userLevelText);

        JPanel downPanel = new JPanel();
        downPanel.setBackground(Color.BLACK);

        JPanel upPanel = new JPanel(new GridLayout(1,6));
        JButton homeButton;


        try{
            Image homePic = new ImageIcon("HomeButton.png").getImage();
            ImageIcon pic = new ImageIcon(homePic.getScaledInstance(50,50,Image.SCALE_SMOOTH));
            homeButton = new JButton(pic);
            homeButton.addActionListener(this);
            homeButton.setBorderPainted(false);
            homeButton.setSize(60,60);

            homeButton.setBackground(Color.BLACK);
            upPanel.add(homeButton);
        }
        catch(Exception e){
            System.out.println("Error. Class cast exception. 2");
        }
        JPanel filler = new JPanel();
        filler.setBackground(Color.BLACK);
        upPanel.add(filler);
        upPanel.add(filler);

        try{
            Image image2 = new ImageIcon("tetris2.png").getImage();
            ImageIcon pic = new ImageIcon(image2.getScaledInstance(100,40,Image.SCALE_SMOOTH));
            upPanel.add(new JLabel(pic));
        }
        catch(Exception e){
            System.out.println("Error. Class cast exception. 2");
        }
        upPanel.add(filler);



        upPanel.setBackground(Color.BLACK);

        thisPanel.add(centerPanel, BorderLayout.CENTER);

        thisPanel.add(rightPanel, BorderLayout.EAST);

        thisPanel.add(leftPanel, BorderLayout.WEST);

        thisPanel.add(upPanel, BorderLayout.NORTH);

        thisPanel.add(downPanel, BorderLayout.SOUTH);

        this.add(thisPanel);
        this.repaint();
    }

    /**
     * Creates a 2-D array of Block objects that make up the tetris grid
     * @param centerPanel - This is the panel that the board is placed into
     * @return A 2-D array of block objects.
     */
    public Block[][] makeGameBoard(JPanel centerPanel) {
        Block[][] gameBoard = new Block[20][10];
        try{
            for (int row = 0; row < 20; row++) {
                for (int col = 0; col < 10; col++) {

                    Block temp = new Block(row,col, defaultImageIcon);
                    gameBoard[row][col] = temp;
                    //centerPanel.add(new JLabel(temp.getImageIcon()));
                    centerPanel.add(temp);
                }
            }
        }
        catch (Exception e){
            System.out.println("Error. Class cast exception");
        }
        return gameBoard;
    }

    /**
     * Method that reads contents of a file and returns the lines as an ArrayList of strings.
     * @param filePath: File that you are reading
     * @return fileContents: ArrayList<String> of the file contents, username - score.
     */
    public ArrayList<String> fileReader(File filePath) {
        ArrayList<String> fileContents = new ArrayList<>();
        try {
            Scanner in = new Scanner(filePath);
            while (in.hasNextLine()) {
                fileContents.add(in.nextLine());
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return fileContents;
    }

    /**
     * Method that takes an ArrayList of strings and adds the string to a HashMap.
     * @param fileContents: ArrayList<String> of usernames and scores.
     * @return: leaderBoard: HashMap<String, Integer> with usernames as the key and score as the value.
     */
    public HashMap<String, Integer> leaderMaker(ArrayList<String> fileContents) {
        String currentTot;
        String[] currentPair;
        String currentName;
        Integer currentScore;
        for (int i = 0; i < fileContents.size(); i++) {
            currentTot = fileContents.get(i);
            currentPair = currentTot.split("-");
            currentName = currentPair[0];
            currentScore = Integer.parseInt(currentPair[1]);
            leaderBoard.put(currentName, currentScore);
        }
        return leaderBoard;
    }

    /**
     * Method that adds the current userScore to the leaderboard.
     * @param leaderScores: HashMap<String, Integer> of usernames and their high scores.
     * @return leaderScores: HashMap<String, Integer> of usernames and their high scores.
     */
    private HashMap<String, Integer> userScores(HashMap<String, Integer> leaderScores){
        Boolean newName = true;
        for (Map.Entry<String, Integer> entry : leaderScores.entrySet()) {
            if (entry.getKey().equals(Main.getHomeScreen().getUserNameStr())) {
                if(entry.getValue() < getUserScore()){
                    entry.setValue(getUserScore());
                }
                newName = false;
            }
            else{
                newName = true;
            }
        }
        if(newName){
            leaderScores.put(Main.getHomeScreen().getUserNameStr(), getUserScore());
        }
        return leaderScores;
    }

    /**
     * Method that writes elements in leaderBoard HashMap to a file.
     * @param filePath: File that you are writing to.
     * @param leaderNums: HashMap<String, Integer> of usernames and scores that is being written to file.
     */
    public void scoresWriter(File filePath, HashMap<String, Integer> leaderNums){
        try {
            FileWriter out = new FileWriter(filePath, false);
            for (Map.Entry<String, Integer> entry : leaderNums.entrySet()) {
                out.write(entry.getKey() + "-" + entry.getValue() + "\r\n");
            }
            out.close();
        } catch (IOException e1){
            e1.printStackTrace();
        }
    }





    public int getUserScore(){
        return this.userScore;
    }
    public void setUserScore(int newScore){
        this.userScore = newScore;
        userScoreText.setText("  " + String.valueOf(this.userScore));
        this.repaint();
    }

    public int getUserLevel(){
        return this.userLevel;
    }
    public void setUserLevel(int newLevel){
        this.userLevel = newLevel;
        userLevelText.setText("  " + String.valueOf(this.userLevel));
        this.repaint();
    }
    public Block[][] getGameBoard(){
        return this.gameBoard;
    }

    public Image getNextBlock() {
        return nextBlock;
    }

    public void setNextBlock(String newNextBlock) {
        this.nextBlock = new ImageIcon(newNextBlock).getImage();
        ImageIcon nextBlockPic = new ImageIcon(this.nextBlock.getScaledInstance(100, 50, Image.SCALE_SMOOTH));

        this.nextBlockLabel.setIcon(nextBlockPic);

    }

    /**
     * Method that checks if the move given by the
     * @param dir is a valid location on the board, when it is not out of bounds,
     *            and when it doesn't hit another block
     * @return true if the move is valid and false if not
     */
    public synchronized boolean isValidMove(String dir ) {
        int[][] thisShape = Piece.Pieces(currentPiece);
        int newX, newY;
        if (dir.equals("down")) {
            newX = curX;
            newY = curY;
            newY++;
        } else if (dir.equals("left")) {
            newX = curX;
            newY = curY;
            newX--;
        } else if (dir.equals("right")) {
            newX = curX;
            newY = curY;
            newX++;
        } else {
            newX = curX;
            newY = curY;
        }


        for (int i = 0; i < thisShape.length; i++) {
            for (int j = 0; j < thisShape[i].length; j++) {
                if (thisShape[i][j] == 1) {
                    if (newY+i-1 >= 19 || newX +j < 0 || newX+j >= 10){
                        return false;
                    }
                    else if (gameBoard[newY+i][newX+j].isFilled ){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method that checks if a rotation of the current piece is allowed
     * based on if it's out of bounds or hitting another block
     * @return true if it's a valid rotation and false if not
     */
    public synchronized boolean isValidMove() {
        int newPiece = TetrisRunner.rotatePiece(this.currentPiece);
        int[][] thisShape = Piece.Pieces(newPiece);
        int newX, newY;
        newX = curX;
        newY = curY;
        for (int i = 0; i < thisShape.length; i++) {
            for (int j = 0; j < thisShape[i].length; j++) {
                if (thisShape[i][j] == 1) {
                    if (newY + i - 1 >= 19 || newX + j < 0 || newX + j >= 10){
                        return false;
                    }
                    else if (gameBoard[newY+i][newX+j].isFilled ){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method that makes a new random block that is the next block and
     * sets the current block to the previous next block unless it's
     * the first then makes a random one. Gives it a random color and
     * a position in the middle
     */
    public synchronized void generateNextBlock(){
        Random r = new Random();
        int randColor = r.nextInt(7);
        int next = r.nextInt(7);
        if (checko == 0) {
            currentPiece = next;
            nexB = r.nextInt(7);
        }
        else {
            currentPiece = nexB;
            nexB = next;
        }
        int[][] nextShape = Piece.Pieces(currentPiece);
        for (int i = 0; i < nextShape.length; i++) {
            for (int j = 0; j < nextShape[i].length; j++) {
                if (nextShape[i][j] == 1){
                    currentColor = colors[randColor];
                    gameBoard[i][j+3].setColor(currentColor);
                    curY = 0;
                    curX =3;
                }
            }
        }
    }

    /**
     * Calls setIsFilled on each block in the current piece. Moving blocks cannot move through filled blocks.
     */
    public void setFilled(){
        int[][] thisShape = Piece.Pieces(currentPiece);
        for (int i = 0; i < thisShape.length; i++) {
            for (int j = 0; j < thisShape[i].length; j++) {
                if (thisShape[i][j] == 1) {
                    gameBoard[curY + i][curX + j].setIsFilled(true);
                    if ((curY + i) < currentTopY){
                        currentTopY = curY+i;
                    }
                }
            }
        }
    }

    /**
     * Method checks all rows for a full row and if it is full calls
     * the clearRow and movesRowDown methods
     */
    public void checkRows(){
        int numRowsCleared = 0;
        for (int rw = 19; rw > 0; rw--){
            boolean lineFilled = true;
            for (int cl = 0; cl < 10; cl++){
                if (!gameBoard[rw][cl].isFilled){
                    lineFilled = false;
                    break;
                }
            }
            if (lineFilled){
                curClear = true;
                numRowsCleared++;
                numLevels++;
                clearRow(rw);
                moveRowsDown(rw);
                rw = rw + 1;
            }
        }
        if (numLevels % 10 == 0 && numLevels != 0 && curClear){
            setUserLevel(getUserLevel() + 1);
            if (getUserLevel() == 1) speed = 720;
            if (getUserLevel() == 2) speed = 630;
            if (getUserLevel() == 3) speed = 550;
            if (getUserLevel() == 4) speed = 470;
            if (getUserLevel() == 5) speed = 380;
            if (getUserLevel() == 6) speed = 300;
            if (getUserLevel() == 7) speed = 220;
            if (getUserLevel() == 8) speed = 130;
            if (getUserLevel() == 9) speed = 100;
            if (getUserLevel() == 10) speed = 80;
            if (getUserLevel() == 13) speed = 70;
            if (getUserLevel() == 16) speed = 50;
            if (getUserLevel() == 19) speed = 30;
            if (getUserLevel() == 29) speed = 20;
            curClear = false;
        }
        if (numRowsCleared == 1){
            setUserScore(getUserScore()+ 40*(getUserLevel() + 1));
        }
        else if (numRowsCleared == 2){
            setUserScore(getUserScore()+ 100*(getUserLevel() + 1));
        }
        else if (numRowsCleared == 3){
            setUserScore(getUserScore()+ 300*(getUserLevel() + 1));
        }
        else if (numRowsCleared == 4){
            setUserScore(getUserScore()+ 1200*(getUserLevel() + 1));
        }
    }

    /**
     * Method clears a given row
     * @param row is the row to be cleared
     */
    public void clearRow(int row){
        for(int col = 0; col < 10; col++){
            gameBoard[row][col].setIsFilled(false);
            gameBoard[row][col].setColor("download.png");
        }
    }

    /**
     * Takes a row and moves everything above it down
     * @param row is the row everything starts moving down at
     */
    public synchronized void moveRowsDown(int row){
        for (int r = row-1; r >=0; r--){ // start at row above row to be deleted, go up
            for(int c = 0; c < 10; c++){ // go across row
                if (gameBoard[r][c].getColor() == null){
                    // gameBoard[r + 1][c] = new Block(c,r+1,defaultImageIcon);
                }
                else {
                    String currentColor = gameBoard[r][c].getColor();
                    boolean currentFill = gameBoard[r][c].getIsFilled();
                    gameBoard[r][c].setColor("download.png");
                    gameBoard[r][c].setIsFilled(false);
                    gameBoard[r + 1][c].setColor(currentColor);
                    gameBoard[r + 1][c].setIsFilled(currentFill);
                }
            }
        }

    }

    /**
     * Method checks if a rotation is allowed then rotates it if so
     * Rotation is based on what piece it is and is rotated 90
     * degrees clockwise
     */
    public synchronized void rotate(){
        if (isValidMove()) {
            int[][] myShape = Piece.Pieces(currentPiece);
            for (int i = 0; i < myShape.length; i++) {
                for (int j = 0; j < myShape[i].length; j++) {
                    if (myShape[i][j] == 1) {
                        gameBoard[curY + i][curX + j].setColor("download.png");
                    }
                }
            }
            this.currentPiece = TetrisRunner.rotatePiece(currentPiece);
            myShape = Piece.Pieces(currentPiece);
            for (int i = 0; i < myShape.length; i++) {
                for (int j = 0; j < myShape[i].length; j++) {
                    if (myShape[i][j] == 1) {
                        gameBoard[curY + i][curX + j].setColor(currentColor);
                    }
                }
            }
        }
    }

    /**
     * Method checks if it can move down one position if so, it repaints it down
     * If position can't go down one, set the block down, check the rows for a
     * full one, make a new block, and potentially show the game over screen
     */
    public synchronized void moveDown() {
        int[][] thisShape = Piece.Pieces(currentPiece);

        if (isValidMove("down")) {
            //     if (true){
            for (int i = 0; i < thisShape.length; i++) {
                for (int j = 0; j < thisShape[i].length; j++) {
                    if (thisShape[i][j] == 1) {
                        gameBoard[curY + i][curX + j].setColor("download.png");
                    }
                }
            }
            curY++;

            for (int i = 0; i < thisShape.length; i++) {
                for (int j = 0; j < thisShape[i].length; j++) {
                    if (thisShape[i][j] == 1) {
                        gameBoard[curY + i][curX + j].setColor(currentColor);

                    }
                }
            }
        }
        else{
            setFilled();
            checkRows();
            generateNextBlock();

            checko = 13;
            //I-BLOCK
            if (nexB == 0 || nexB == 7) setNextBlock("blueBlock copy 5.png");
                //J-BLOCK
            else if (nexB == 1 || nexB == 8 || nexB == 9 || nexB == 10)setNextBlock("blueBlock copy 2.png");
                //L-BLOCK
            else if (nexB == 2 || nexB == 11 || nexB == 12 || nexB == 13)setNextBlock("blueBlock copy.png");
                //S-BLOCK ROTATIONS
            else if (nexB == 4 || nexB == 14)setNextBlock("blueBlock copy 4.png");
                //T-BLOCK ROTATIONS
            else if (nexB == 5 || nexB == 15 || nexB == 16 || nexB == 17)setNextBlock("blueBlock.png");
                //Z-BLOCK ROTATIONS
            else if (nexB == 6 || nexB == 18)setNextBlock("blueBlock copy 3.png");
                //O-BLOCK
            else if (nexB == 3)setNextBlock("blueBlocksss.png");

            if (!isValidMove("down")) {
                setIsRunning(false);
                String[] gameOverArr = {"Game over..."};
                JList gameOverList = new JList<String>();
                gameOverList.setListData(gameOverArr);
                gameOverList.setFont(arcadeFont);
                gameOverList.setBackground(Color.BLACK);
                gameOverList.setForeground(Color.WHITE);
                JScrollPane gameOverScreen = new JScrollPane();
                gameOverScreen.setPreferredSize(new Dimension(300,50));
                gameOverScreen.setBackground(Color.BLACK);
                gameOverScreen.getViewport().setView(gameOverList);
                JOptionPane.showConfirmDialog(null, gameOverScreen, "Game Over :(", JOptionPane.DEFAULT_OPTION);
                scoresWriter(scoresFile, userScores(leaderMaker(fileReader(scoresFile))));
                this.setVisible(false);
                Main.getHomeScreen().setVisible(true);
            }
        }
    }

    /**
     * Checks if the piece can move right. If it can, sets the area around the piece to the default image
     * and generates the piece right 1 block. If the move is not valid, the piece is not moved.
     */
    public synchronized void moveRight(){
        if (isValidMove("right")) {
            int[][] thisShape = Piece.Pieces(currentPiece);
            for (int i = 0; i < thisShape.length; i++) {
                for (int j = 0; j < thisShape[i].length; j++) {
                    if (thisShape[i][j] == 1) {
                        gameBoard[curY + i][curX + j].setColor("download.png");
                    }
                }
            }
            curX++;

            for (int i = 0; i < thisShape.length; i++) {
                for (int j = 0; j < thisShape[i].length; j++) {
                    if (thisShape[i][j] == 1) {
                        gameBoard[curY + i][curX + j].setColor(currentColor);

                    }
                }
            }
        }
        else{

        }
    }

    /**
     * Checks if the piece can move left. If it can, sets the area around the piece to the default image
     * and generates the piece left 1 block. If the move is not valid, the piece is not moved.
     */
    public synchronized void moveLeft(){
        if (isValidMove("left")) {
            int[][] thisShape = Piece.Pieces(currentPiece);
            for (int i = 0; i < thisShape.length; i++) {
                for (int j = 0; j < thisShape[i].length; j++) {
                    if (thisShape[i][j] == 1) {
                        gameBoard[curY + i][curX + j].setColor("download.png");
                    }
                }
            }
            curX--;

            for (int i = 0; i < thisShape.length; i++) {
                for (int j = 0; j < thisShape[i].length; j++) {
                    if (thisShape[i][j] == 1) {
                        gameBoard[curY + i][curX + j].setColor(currentColor);

                    }
                }
            }
        }
        else{

        }
    }

    /**
     * Creates a new bot thread. Sets the priority of the thread to 10 to avoid clashing between threads and
     * starts the thread.
     */
    public void playBot(){
        myBot  = new TetrisBot(this);
        myBot.setPriority(10);
        myBot.start();

    }

    public boolean getIsRunning(){
        return isRunning;
    }

    public void setIsRunning(Boolean b){
        isRunning = b;
    }

    /**
     * Reacts to action events. If the home button is pressed, navigate to the home screen. If an arrow key is
     * pressed, move the piece that direction. If 0 is pressed, start the tetris bot.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("")){
            setIsRunning(false);
            String[] wrongDashArr = {"", "Going home..."};
            JList wrongDashList = new JList<String>();
            wrongDashList.setListData(wrongDashArr);
            wrongDashList.setFont(arcadeFont);
            wrongDashList.setBackground(Color.BLACK);
            wrongDashList.setForeground(Color.WHITE);
            JScrollPane wrongDashScreen = new JScrollPane();
            wrongDashScreen.setPreferredSize(new Dimension(250,50));
            wrongDashScreen.setBackground(Color.BLACK);
            wrongDashScreen.getViewport().setView(wrongDashList);
            JOptionPane.showConfirmDialog(null, wrongDashScreen, "Home", JOptionPane.DEFAULT_OPTION);
            scoresWriter(scoresFile, userScores(leaderMaker(fileReader(scoresFile))));
            this.setVisible(false);
            Main.getHomeScreen().setVisible(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == VK_RIGHT && !botPlaying){
            moveRight();
        }
        else if (e.getKeyCode() == VK_UP && !botPlaying){
            rotate();
        }
        else if (e.getKeyCode() == VK_LEFT && !botPlaying){
            moveLeft();
        }
        else if (e.getKeyCode() == VK_DOWN){
            moveDown();
        }
        else if (e.getKeyCode() == VK_0){
            if (!botPlaying){
                playBot();
            }
            botPlaying = true;
        }
        else if (e.getKeyCode() == VK_9){
            System.out.println(curX + " : " +curY);

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
