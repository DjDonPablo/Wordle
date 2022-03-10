package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    WordleController controller;

    public Keyboard(WordleController controller){
        this.controller = controller;
    } //Constructor

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) { //function which call controllers function according to which key is pressed on real keyboard
        char c = e.getKeyChar();
        if('a' <= c && c <= 'z')
            controller.updateWord(Character.toUpperCase(c));
        else {
            if(c == 10)
                controller.checkWord();
            else
                controller.backWord();
        }
    }

    public void keyReleased(KeyEvent e) {}
}
