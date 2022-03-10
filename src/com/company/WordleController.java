package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class WordleController { //CONTROLLER CLASS
    private final Game game;

    public WordleController(Game game) { //Contructor
        this.game = game;
    }

    public void switchkey(ActionEvent e) { //Determine which function to call according to which key is pressed
        String buttonText = ((JButton) e.getSource()).getText();
        if(buttonText.length() > 1) {
            if(buttonText.equals("ENTER"))
                checkWord();
            else
                backWord();
        }
        else
            updateWord(buttonText.charAt(0));
    }

    //THESE FUNCTIONS CALL THE CORRESPONDING FUNCTION IN GAME

    public void check1(ActionEvent e) {game.check1();}

    public void check2(ActionEvent e) {game.check2();}

    public void check3(ActionEvent e) {game.check3();}

    public void checkWord() {game.checkword();}

    public void backWord() { //call this in view if key is BACK
        game.backword();
    }

    public void updateWord(char c) {game.updateword(c);}

    public void playagain(ActionEvent e) {
        game.playagain();
    }
}
