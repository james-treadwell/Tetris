import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.util.*;
import java.awt.GraphicsEnvironment;
import javax.swing.UIManager;

public class HomeScreen extends JFrame implements ActionListener {
    private int SCREEN_WIDTH = 635;
    private int SCREEN_HEIGHT = 1000;
    private int TITLE_WIDTH = 391;
    private int TITLE_HEIGHT = 128;
    private JPanel homePanel;
    private JPanel namePanel;
    private JLabel userLabel;
    private JTextField userName;
    private JButton playButton;
    private JButton leaderButton;
    private JButton instructButton;
    private JButton exitButton;
    private Font arcadeFont;
    private HashMap<String, Integer> leaderBoard = new HashMap<>();
    private File scoresFile = new File("highscores");
    private UIManager UI = new UIManager();

    private String userNameStr;
    private int lineCount;

    public HomeScreen() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        super();
        try {
            arcadeFont = Font.createFont(Font.TRUETYPE_FONT, new File("ARCADE_N.ttf")).deriveFont(19f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(arcadeFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

        JPanel thisPanel = new JPanel();
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setTitle("Tetris - Home");
        thisPanel.setOpaque(true);
        thisPanel.setBackground(Color.BLACK);

        thisPanel.setLayout(new BorderLayout());

        homePanel = new JPanel();
        homePanel.setLayout(new GridLayout(5, 1));

        JLabel titleLabel = new JLabel();
        titleLabel.setIcon(new ImageIcon(new ImageIcon("tetrisLogo.png").getImage().getScaledInstance(TITLE_WIDTH, TITLE_HEIGHT, Image.SCALE_DEFAULT)));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(1, 2));
        namePanel.setBackground(Color.BLACK);
        userLabel = new JLabel("<html><body style='text-align:center'> Enter 3-Character Username:</body></html>", SwingConstants.CENTER);
        userLabel.setFont(arcadeFont);
        userLabel.setForeground(Color.RED);
        userName = new JTextField("");
        userName.setFont(arcadeFont);
        userName.setBackground(Color.BLACK);
        userName.setForeground(Color.white);
        userName.setHorizontalAlignment(JTextField.CENTER);

        namePanel.add(userLabel);
        namePanel.add(userName);


        playButton = new JButton("Play!");
        playButton.setFont(arcadeFont);
        playButton.setForeground(Color.GREEN);
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        leaderButton = new JButton("Leaderboard");
        leaderButton.setFont(arcadeFont);
        leaderButton.setForeground(Color.CYAN);
        leaderButton.setOpaque(false);
        leaderButton.setContentAreaFilled(false);
        leaderButton.setBorderPainted(false);
        instructButton = new JButton("Instructions");
        instructButton.setFont(arcadeFont);
        instructButton.setForeground(Color.MAGENTA);
        instructButton.setOpaque(false);
        instructButton.setContentAreaFilled(false);
        instructButton.setBorderPainted(false);
        homePanel.add(titleLabel);
        homePanel.add(namePanel);
        homePanel.add(playButton);
        homePanel.add(leaderButton);
        homePanel.add(instructButton);

        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        exitButton = new JButton("Exit");
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(arcadeFont);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitPanel.add(exitButton);

        exitButton.addActionListener(this);
        playButton.addActionListener(this);
        leaderButton.addActionListener(this);
        instructButton.addActionListener(this);

        userName.addActionListener(this);

        exitPanel.setBackground(Color.BLACK);
        homePanel.setBackground(Color.BLACK);
        thisPanel.add(exitPanel, BorderLayout.NORTH);
        thisPanel.add(homePanel, BorderLayout.CENTER);

        this.add(thisPanel);
        this.repaint();

        UI.put("OptionPane.background",new ColorUIResource(0,0,0));
        UI.put("Panel.background",new ColorUIResource(0,0,0));

        File homeMusic = new File("themeMusic1.wav");
        Clip clip = AudioSystem.getClip();
        AudioInputStream ais = AudioSystem.getAudioInputStream(homeMusic);
        clip.open(ais);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public String getUserNameStr(){
        return userNameStr.toUpperCase();
    }

    /**
     * Method that reads contents of a file and returns the lines as an ArrayList of strings.
     * @param filePath: File that you are reading
     * @return fileContents: ArrayList<String> of usernames and scores in the file.
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
     * Method that looks through a HashMap and finds the n highest integer values.
     * @param leaderScores: HashMap<String, Integer> of usernames and scores.
     * @param n: int that represents the n highest scores you want to see.
     * @return: highScores: HashMap<String, Integer> of the n highest scores and their corresponding usernames.
     */
    private ArrayList<String> highScores(HashMap<String, Integer> leaderScores, int n){
        HashMap<String, Integer> cloneHash = new HashMap<>();
        ArrayList<String> highScores = new ArrayList<>();
        cloneHash = (HashMap<String, Integer>) leaderScores.clone();
        int maxValue = 0;
        String currentEntry = "";
        Integer currentValue = 0;
        String wordString = "";
        if(leaderScores.size() > n){
            n = n;
        }
        else{
            n = leaderScores.size();
        }
        for(int i = 0; i < n; i++) {
            maxValue = (Collections.max(cloneHash.values()));
            for (Map.Entry<String, Integer> entry : cloneHash.entrySet()) {
                if (entry.getValue() == maxValue) {
                    wordString = entry.getKey() + ": " + entry.getValue().toString();
                    highScores.add(wordString);
                    currentEntry = entry.getKey();
                    currentValue = entry.getValue();
                }
            }
            cloneHash.remove(currentEntry, currentValue);
        }
        return highScores;
    }

    /**
     * Method that writes elements in leaderBoard HashMap to a file.
     * @param filePath: File that you are writing to.
     * @param leaderNums: HashMap<String, Integer> of usernames and scores to write to file.
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

    /**
     * Method that checks if buttons are pressed and calls things if so.
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            String[] exitConfirm = {" ", "Are you sure you want to quit?"};
            JList exitList = new JList<String>();
            exitList.setListData(exitConfirm);
            exitList.setFont(arcadeFont);
            exitList.setBackground(Color.BLACK);
            exitList.setForeground(Color.WHITE);
            JScrollPane exitScreen = new JScrollPane();
            exitScreen.setPreferredSize(new Dimension(575,10));
            exitScreen.setBackground(Color.BLACK);
            exitScreen.getViewport().setView(exitList);

            int n = JOptionPane.showConfirmDialog(null, exitScreen, "Exit", JOptionPane.YES_NO_OPTION);
            if(n == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        }
        if (e.getSource() == playButton) {
            if(userName.getText().length() == 3 && userName.getText().charAt(0) != '-' && userName.getText().charAt(1) != '-' && userName.getText().charAt(2) != '-'){
                userNameStr = userName.getText();
                Packer packerThread = new Packer();
                packerThread.start();
                userName.setText("");
                this.setVisible(false);
            }
            else if(userName.getText().length() != 3){
                String[] wrongUserArr = {"Username must be 3 characters"};
                JList wrongUserList = new JList<String>();
                wrongUserList.setListData(wrongUserArr);
                wrongUserList.setFont(arcadeFont);
                wrongUserList.setBackground(Color.BLACK);
                wrongUserList.setForeground(Color.WHITE);
                JScrollPane wrongUserScreen = new JScrollPane();
                wrongUserScreen.setPreferredSize(new Dimension(600,50));
                wrongUserScreen.setBackground(Color.BLACK);
                wrongUserScreen.getViewport().setView(wrongUserList);
                JOptionPane.showConfirmDialog(null, wrongUserScreen, "Invalid Username", JOptionPane.DEFAULT_OPTION);
            }
            else if(userName.getText().charAt(0) != '-' || userName.getText().charAt(1) != '-' || userName.getText().charAt(2) != '-'){
                String[] wrongDashArr = {"Username cannot contain a dash"};
                JList wrongDashList = new JList<String>();
                wrongDashList.setListData(wrongDashArr);
                wrongDashList.setFont(arcadeFont);
                wrongDashList.setBackground(Color.BLACK);
                wrongDashList.setForeground(Color.WHITE);
                JScrollPane wrongDashScreen = new JScrollPane();
                wrongDashScreen.setPreferredSize(new Dimension(600,50));
                wrongDashScreen.setBackground(Color.BLACK);
                wrongDashScreen.getViewport().setView(wrongDashList);
                JOptionPane.showConfirmDialog(null, wrongDashScreen, "Invalid Username", JOptionPane.DEFAULT_OPTION);
            }
        }
        if(e.getSource() == leaderButton){
            ArrayList<String> leaderArrList = highScores(leaderMaker(fileReader(scoresFile)), 3);
            String[] leaderArr = new String[3];
            for(int i = 0; i < leaderArrList.size(); i++){
                leaderArr[i] = i+1 + " - " + leaderArrList.get(i);
            }
            JList leaderList = new JList<String>();
            leaderList.setListData(leaderArr);
            leaderList.setFont(arcadeFont);
            leaderList.setBackground(Color.BLACK);
            leaderList.setForeground(Color.WHITE);
            JScrollPane leaderScreen = new JScrollPane();
            leaderScreen.setPreferredSize(new Dimension(250,100));
            leaderScreen.setBackground(Color.BLACK);
            leaderScreen.getViewport().setView(leaderList);
            JOptionPane.showConfirmDialog(null, leaderScreen, "LEADERBOARD", JOptionPane.DEFAULT_OPTION);
            scoresWriter(scoresFile, leaderBoard);
        }
        if(e.getSource() == instructButton){
            String[] instructArr = {"Press up arrow to rotate block", "Press down arrow to move block down", "Press right or left arrow to move block right or left", "Press 0 to have robot play", "If a row of the screen is filled, that row will clear", "If a block crosses the top line, the game is over"};
            JList instructList = new JList<String>();
            instructList.setListData(instructArr);
            instructList.setFont(arcadeFont);
            instructList.setBackground(Color.BLACK);
            instructList.setForeground(Color.WHITE);
            JScrollPane instructScreen = new JScrollPane();
            instructScreen.setPreferredSize(new Dimension(1050,200));
            instructScreen.setBackground(Color.BLACK);
            instructScreen.getViewport().setView(instructList);
            JOptionPane.showConfirmDialog(null, instructScreen, "Instructions", JOptionPane.DEFAULT_OPTION);
        }
    }

    private class Packer extends Thread {
        /**
         * Method that calls a thread to create a new GameScreen and run it.
         */
        public void run() {
            GameScreen gameScreen = new GameScreen();
            gameScreen.setIsRunning(true);
            gameScreen.setVisible(true);
            gameScreen.generateNextBlock();
            while (gameScreen.getIsRunning()){
                gameScreen.moveDown();
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }
    }

}