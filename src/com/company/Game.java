package com.company;
import java.awt.*;
import java.util.Observable;

public class Game extends Observable{ // MODEL CLASS
    public char[][] boardgame;
    public Color[][] boardgamecolor;
    public String theword;
    private final String finalword = "rodeo";
    private String randomword;
    private WordleFindWord wfw;
    private boolean errorlen;
    private boolean errornotin;
    private boolean errormessageon;
    private boolean displaywordon;
    private boolean randomwordon;
    private boolean cantwrite;
    private boolean updatecolors;
    private boolean isplayagain;
    private boolean justchanged;
    private boolean win;
    private boolean lose;
    public int x;
    public int y;

    public boolean isWin() {
        return win;
    }

    public boolean isLose() {
        return lose;
    }

    public boolean isJustchanged() {
        return justchanged;
    }

    public void setJustchanged(boolean justchanged) {
        this.justchanged = justchanged;
    }

    public boolean isIsplayagain() {
        return isplayagain;
    }

    public boolean isUpdatecolors() {
        return updatecolors;
    }

    public void setUpdatecolors(boolean updatecolors) {
        this.updatecolors = updatecolors;
    }

    public boolean isErrorlen() {
        return errorlen;
    }

    public void setErrorlen(boolean errorlen) {
        this.errorlen = errorlen;
    }

    public boolean isErrornotin() {
        return errornotin;
    }

    public void setErrornotin(boolean errornotin) { this.errornotin = errornotin; }

    public boolean isErrormessageon() {
        return errormessageon;
    }

    public void setErrormessageon(boolean errormessageon) {
        this.errormessageon = errormessageon;
    }

    public boolean isDisplaywordon() {
        return displaywordon;
    }

    public void setDisplaywordon(boolean displaywordon) {
        this.displaywordon = displaywordon;
    }

    public boolean isRandomwordon() {
        return randomwordon;
    }

    public void setRandomwordon(boolean randomwordon) {
        this.randomwordon = randomwordon;
    }

    public Game() { //Constructor
        init();
    }

    public void init() { //Init a game
        boardgame = new char[6][5];
        for(int i = 0; i < 6; i ++){
            for(int j = 0; j < 5; j++)
                boardgame[i][j] = ' ';
        }
        boardgamecolor = new Color[6][5];
        wfw = new WordleFindWord();
        this.randomword = wfw.theword;
        theword = finalword;
        x = 0;
        y = 0;
        errorlen = false;
        errornotin = false;
        errormessageon = false;
        displaywordon = false;
        randomwordon = false;
        cantwrite = false;
        updatecolors = false;
        isplayagain = false;
        justchanged = false;
        win = false;
        lose = false;
        setChanged();
        notifyObservers();
    }

    public void checkword() { //check if word is long enought, is in the list if checkbox checked, and update colors for grid and keyboard
        if(x != 4 || boardgame[y][x] == ' ')
            errorlen = true;
        else{
            char[] totest = new char[5];
            for(int i = 0; i < 5;i++){
                totest[i] = boardgame[y][i];
                totest[i] = Character.toLowerCase(totest[i]);
            }
            String realtotest = String.valueOf(totest);
            if(!errormessageon || wfw.isthewordin(realtotest)) {
                if(y == 0)
                    isplayagain = true;
                String copytheword = theword;
                char[] cc = copytheword.toCharArray();
                int countgreen = 0;
                for(int i = 0; i < 5; i++) {
                    int index = copytheword.indexOf(totest[i]);
                    if(index == i){
                        boardgamecolor[y][i] = Color.GREEN;
                        cc[index] = ' ';
                        countgreen++;
                    }
                    else{
                        if(index == -1)
                            boardgamecolor[y][i] = Color.GRAY;
                        else {
                            boardgamecolor[y][i] = Color.ORANGE;
                            cc[index] = ' ';
                        }
                    }
                    copytheword = String.valueOf(cc);
                    if(countgreen == 5)
                        win = true;
                }
                updatecolors = true;
                if(y != 5) {
                    x = 0;
                    y++;
                }
                else{
                    cantwrite = true;
                    lose = true;
                }
            }
            else {
                errornotin = true;
            }
        }
        setChanged();
        notifyObservers();
    }

    public void updateword(char c) { //add a letter to the current row
        if((x != 4 || boardgame[y][x] == ' ') && !cantwrite) {
            boardgame[y][x] = c;
            if(x != 4)
                x++;
        }
        setChanged();
        notifyObservers();
    }

    public void backword() { //remove last letter of the current row
         if(x != 0 && !cantwrite) {
            if((x != 4 || boardgame[y][x] == ' '))
                x--;
            boardgame[y][x] = ' ';
         }
        setChanged();
        notifyObservers();
    }

    public void check1() { //set the boolean for the error message and notify WordleView
        setErrormessageon(!isErrormessageon());
        setChanged();
        notifyObservers();
    }

    public void check2() { //set the boolean for displaying the word and notify WordleView
        setDisplaywordon(!isDisplaywordon());
        setChanged();
        notifyObservers();
    }

    public void check3() { //set the boolean for the word to be random and notify WordleView
        setRandomwordon(!isRandomwordon());
        if(isRandomwordon())
            theword = randomword;
        else
            theword = finalword;
        setChanged();
        notifyObservers();
    }

    public void playagain() { //create a new game
        init();
        justchanged = true;
        setChanged();
        notifyObservers();
    }
}
