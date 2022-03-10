package com.company;

import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

public class WordleView implements Observer { //VIEW CLASS

    private final Dimension PANEL_SIZE = new Dimension(1200,800);

    private JFrame frame; //window
    private JPanel panel; //general panel
    private JPanel panelgrid; //panel grid
    private JPanel panelkeys; //panel keys
    private JPanel panelcheckboxes;
    private final Game game; //game instance
    private final WordleController controller; //UI controller
    private JTextField[][] grid; //grid
    public JButton[] keyboard1; //keyboard
    public JButton[] keyboard2; //keyboard
    public JButton[] keyboard3; //keyboard
    public String[] board = {"Q","W","E","R","T","Y","U","I","O","P"}; //first keyboard line
    public String[] board2 = {"A","S","D","F","G","H","J","K","L"}; //second keyboard line
    public String[] board3 = {"ENTER","Z","X","C","V","B","N","M","BACK"}; //third keyboard line
    public JCheckBox errormessage; //checkbox for error message
    public JCheckBox display; //checkbox to display the word
    public JCheckBox randomword; //checkbox for the word to be random
    public JButton playagain; //button to play again
    private boolean lastline = false; //boolean to know if last line
    private final Keyboard keyboard; //keyboard which contains the three boards

    private void initkeyboard() { //creates keyboard buttons
        panelkeys = new JPanel();
        panelkeys.setLayout(new GridBagLayout());
        panelkeys.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);

        JPanel panelkey1 = new JPanel();
        JPanel panelkey2 = new JPanel();
        JPanel panelkey3 = new JPanel();
        panelkey1.setLayout(new GridBagLayout());
        panelkey2.setLayout(new GridBagLayout());
        panelkey3.setLayout(new GridBagLayout());

        Font font = new Font("Comic Sans", Font.BOLD, 25);
        keyboard1 = new JButton[10];
        for(int i = 0; i < 10; i++) {
            keyboard1[i] = new JButton(board[i]);
            keyboard1[i].setBackground(Color.white);
            keyboard1[i].setFocusPainted(false);
            gbc.gridx = i;
            gbc.gridy = 0;
            keyboard1[i].setFont(font);
            keyboard1[i].addActionListener(controller::switchkey);
            keyboard1[i].setFocusable(false);
            panelkey1.add(keyboard1[i], gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelkey1.setBackground(Color.LIGHT_GRAY);
        panelkeys.add(panelkey1,gbc);

        keyboard2 = new JButton[9];
        keyboard3 = new JButton[9];

        for(int i = 0; i < 9; i++) {
            keyboard2[i] = new JButton(board2[i]);
            keyboard2[i].setBackground(Color.white);
            keyboard2[i].setFocusPainted(false);
            gbc.gridx = i;
            gbc.gridy = 1;
            keyboard2[i].setFont(font);
            keyboard2[i].addActionListener(controller::switchkey);
            keyboard2[i].setFocusable(false);
            panelkey2.add(keyboard2[i], gbc);

            keyboard3[i] = new JButton(board3[i]);
            keyboard3[i].setBackground(Color.white);
            keyboard3[i].setFocusPainted(false);
            gbc.gridx = i;
            gbc.gridy = 2;
            keyboard3[i].setFont(font);
            keyboard3[i].addActionListener(controller::switchkey);
            keyboard3[i].setFocusable(false);
            panelkey3.add(keyboard3[i],gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelkey2.setBackground(Color.LIGHT_GRAY);
        panelkeys.add(panelkey2,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelkey3.setBackground(Color.LIGHT_GRAY);
        panelkeys.add(panelkey3,gbc);
    }

    private void initgrid() { //create grid
        panelgrid = new JPanel();
        panelgrid.setLayout(new GridBagLayout());
        panelgrid.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        Font font = new Font("Comic Sans", Font.BOLD, 40);

        grid = new JTextField[6][5];
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++)
            {
                grid[i][j] = new JTextField(2);
                grid[i][j].setBorder(new LineBorder(Color.BLACK,2));
                gbc.gridx = j;
                gbc.gridy = i;
                grid[i][j].setEditable(false);
                grid[i][j].setText(" ");
                grid[i][j].setFont(font);
                grid[i][j].setForeground(Color.BLACK);
                grid[i][j].setFocusable(false);
                panelgrid.add(grid[i][j],gbc);
            }
        }
    }

    private void cleargridandstuff() { //clear grid, keyboard and checkboxes
        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j].setBackground(Color.WHITE);
                grid[i][j].setText(" ");
            }
        }
        for(int i = 0; i < 10; i++) {
            keyboard1[i].setBackground(Color.white);
        }
        for(int i = 0; i < 9; i++) {
            keyboard2[i].setBackground(Color.white);
            keyboard3[i].setBackground(Color.white);
        }
        errormessage.setFocusPainted(false);
        display.setSelected(false);
        randomword.setSelected(false);
    }

    private void initCheckBoxes() { //init checkboxes and play again button
        panelcheckboxes = new JPanel();
        panelcheckboxes.setLayout(new GridBagLayout());
        panelcheckboxes.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 10, 10);
        Font font = new Font("Comic Sans", Font.BOLD, 15);


        errormessage = new JCheckBox("Error Message");
        errormessage.setBackground(Color.LIGHT_GRAY);
        errormessage.setFocusPainted(false);
        errormessage.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        errormessage.setSelected(false);
        errormessage.addActionListener(controller::check1);
        errormessage.addKeyListener(keyboard);
        panelcheckboxes.add(errormessage, gbc);

        display = new JCheckBox("Display Word");
        display.setBackground(Color.LIGHT_GRAY);
        display.setFocusPainted(false);
        display.setFont(font);
        gbc.gridx = 1;
        gbc.gridy = 0;
        display.setSelected(false);
        display.addActionListener(controller::check2);
        display.addKeyListener(keyboard);
        panelcheckboxes.add(display, gbc);

        randomword = new JCheckBox("Random Word");
        randomword.setBackground(Color.LIGHT_GRAY);
        randomword.setFocusPainted(false);
        randomword.setFont(font);
        gbc.gridx = 2;
        gbc.gridy = 0;
        randomword.setSelected(false);
        randomword.addActionListener(controller::check3);
        randomword.addKeyListener(keyboard);
        panelcheckboxes.add(randomword, gbc);

        playagain = new JButton("Play again");
        playagain.setBackground(Color.LIGHT_GRAY);
        playagain.setFocusPainted(false);
        playagain.setFont(font);
        gbc.gridx = 3;
        gbc.gridy = 0;
        playagain.addActionListener(controller::playagain);
        playagain.addKeyListener(keyboard);
        playagain.setEnabled(false);
        panelcheckboxes.add(playagain, gbc);
    }

    public WordleView(Game game, WordleController controller) { //Constructor
        this.game = game;
        game.addObserver(this);
        this.controller = controller;
        keyboard = new Keyboard(controller);
        initgrid();
        initkeyboard();
        initCheckBoxes();
        createControls();
    }

    public void createControls() { //Create the window
        frame = new JFrame("Wordle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        createPanel();
        contentPane.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void messageDialog(String message, String windowname, int optionPane) { //function to show a message dialog
        JOptionPane.showMessageDialog(frame, message, windowname, optionPane);
    }

    @Override
    public void update(Observable o, Object arg) { //update function
        if(game.isJustchanged()){
            cleargridandstuff();
            game.setJustchanged(false);
        }
        else{
            for(int i = 0; i <= game.y; i++) { //actualization of char in grid
                for(int j = 0; j <= game.x; j++) {
                    grid[i][j].setText(String.valueOf(game.boardgame[i][j]));
                }
            }
        }

        if(game.y > 0 && game.isUpdatecolors()) { //actualization of colors in grid and keyboard
            Color C;
            if(game.y != 6 && !lastline) {
                if(game.y == 5)
                    lastline = true;
                for(int i = 0; i < 5; i++){
                    C = game.boardgamecolor[game.y-1][i];
                    grid[game.y-1][i].setBackground(C);
                    char Char = game.boardgame[game.y-1][i];
                    boolean alreadyfound = false;
                    for(int j = 0; j < 10; j++) {
                        if(keyboard1[j].getText().charAt(0) == Char && keyboard1[j].getBackground() != Color.GREEN){
                            keyboard1[j].setBackground(C);
                            alreadyfound = true;
                            break;
                        }
                    }
                    if(!alreadyfound) {
                        for(int j = 0; j < 9; j++) {
                            if(keyboard2[j].getText().charAt(0) == Char && keyboard2[j].getBackground() != Color.GREEN){
                                keyboard2[j].setBackground(C);
                                break;
                            }
                            if(keyboard3[j].getText().charAt(0) == Char && keyboard3[j].getBackground() != Color.GREEN){
                                keyboard3[j].setBackground(C);
                                break;
                            }
                        }
                    }
                }
            }
            else {
                for(int i = 0; i < 5; i++){
                    C = game.boardgamecolor[game.y][i];
                    grid[game.y][i].setBackground(C);
                }
            }

            game.setUpdatecolors(false);
        }

        if(game.isErrornotin()){ //pop up window if not in
            messageDialog("This is not a word !", "Word error", JOptionPane.ERROR_MESSAGE);
            game.setErrornotin(false);
        }
        if(game.isErrorlen()){ //pop up window if too short
            messageDialog("The word is too short !", "Word error", JOptionPane.ERROR_MESSAGE);
            game.setErrorlen(false);
        }
        if(game.isDisplaywordon()) //display word
            display.setText(game.theword);
        if(!game.isDisplaywordon()) //don't display word
            display.setText("Display Word");

        playagain.setEnabled(game.isIsplayagain());
    }

    private void createPanel() { //create panel function with all the components
        panel = new JPanel(); //general panel
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(panelgrid,gbc); //adding panelgrid to general panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(panelcheckboxes,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(panelkeys,gbc); //adding panelkeys to general panel

        panel.setPreferredSize(PANEL_SIZE); //set size
    }
}
