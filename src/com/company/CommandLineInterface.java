package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class CommandLineInterface {

    private final Game game; //game instance
    private char[][] grid; //grid
    private ArrayList<Character> available;
    private ArrayList<Character> good;
    private ArrayList<Character> notgoodplace;
    private boolean clearword;
    private boolean lastline = false;
    private String theword;

    public CommandLineInterface(Game game) {
        this.game = game;
        clearword = false;
        play();
    }

    public void initgrid() {
        grid = new char[6][5];
        available = new ArrayList<>();
        good = new ArrayList<>();
        notgoodplace = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
                grid[i][j] = ' ';
            }
        }
        for(int i = 65; i < 91; i++)
            available.add((char)i);
    }

    public void printgrid() {
        System.out.println("---------------------");
        for(int i = 0; i < 6; i++) {
            System.out.println("| "+grid[i][0]+" | "+grid[i][1]+" | "+grid[i][2]+" | "+grid[i][3]+" | "+grid[i][4]+" |");
            System.out.println("---------------------");
        }
    }

    public void inittags() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want an error message if the word doesn't exist ? yes/no");
        String str = scanner.nextLine();
        if(Objects.equals(str, "y") || Objects.equals(str, "yes"))
            game.setErrormessageon(true);

        System.out.println("Do you want the word to be print ? yes/no");
        str = scanner.nextLine();
        if(Objects.equals(str, "y") || Objects.equals(str, "yes"))
            clearword = true;

        System.out.println("Do you want the word to be random ? yes/no");
        str = scanner.nextLine();
        if(Objects.equals(str, "y") || Objects.equals(str, "yes"))
            game.check3();
    }

    public boolean readword() {
        System.out.print("Enter a five letter word : ");
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        System.out.println();
        str = str.toUpperCase(Locale.ROOT);
        boolean b = true;
        if(str.length() != 5)
            System.out.println("This word is not valid!\n");
        else {
            for(int i = 0; i < 5; i++) {
                if (str.charAt(i) < 'A' || str.charAt(i) > 'Z') {
                    b = false;
                    break;
                }
            }
            if(b) {
                for(int i = 0; i < 5; i++)
                    game.updateword(str.charAt(i));
                game.checkword();
                if(game.isErrornotin()) {
                    for(int i = 0; i < 5; i++)
                        game.boardgame[game.y][i] = ' ';
                    game.x = 0;
                    System.out.println("This word is not valid!\n");
                    game.setErrornotin(false);
                    return false;
                }
                return true;
            }
            else
                System.out.println("This word is not valid!\n");
        }
        return false;
    }

    public void printinfos() {
        System.out.println();
        if(clearword)
            System.out.println("The word is : " + game.theword);
        System.out.println("Available letters are : " + available);
        System.out.println("Well-placed letters are : " + good);
        System.out.println("Bad-placed letters are : " + notgoodplace);
        System.out.println();
    }

    public void update() {
        //actualization of char in grid
        if(!lastline)
            System.arraycopy(game.boardgame[game.y - 1], 0, grid[game.y - 1], 0, 5);
        else
            System.arraycopy(game.boardgame[game.y], 0, grid[game.y], 0, 5);
        for(int i = 0 ; i < 5; i++) {
            if(!lastline) {
                if(game.boardgamecolor[game.y - 1][i] == Color.GREEN) {
                    good.add(grid[game.y - 1][i]);
                    if(notgoodplace.contains(grid[game.y - 1][i]))
                        notgoodplace.remove((Character)grid[game.y - 1][i]);
                }
                if(game.boardgamecolor[game.y - 1][i] == Color.ORANGE)
                    notgoodplace.add(grid[game.y - 1][i]);
                if(game.boardgamecolor[game.y - 1][i] == Color.GRAY && available.contains(grid[game.y - 1][i]))
                    available.remove((Character)grid[game.y - 1][i]);
            }
            else {
                if(game.boardgamecolor[game.y][i] == Color.GREEN) {
                    good.add(grid[game.y][i]);
                    if(notgoodplace.contains(grid[game.y][i]))
                        notgoodplace.remove((Character) grid[game.y][i]);
                }
                if(game.boardgamecolor[game.y][i] == Color.ORANGE)
                    notgoodplace.add(grid[game.y][i]);
                if(game.boardgamecolor[game.y][i] == Color.GRAY && available.contains(grid[game.y - 1][i]))
                    available.remove((Character)grid[game.y][i]);
            }
        }
        if(game.y == 5)
            lastline = true;
    }

    public boolean playagain() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to play again ? : yes/no");
        String str = scanner.nextLine();
        if(Objects.equals(str, "y") || Objects.equals(str, "yes")){
            game.init();
            initgrid();
            inittags();
            lastline = false;
            return false;
        }
        return true;
    }

    public void play() {
        initgrid();
        inittags();
        while(true) {
            if(readword())
                update();
            printgrid();
            printinfos();
            if(game.isWin()) {
                System.out.println("You found the word !\n");
                if(playagain())
                    break;
            }
            if(game.isLose()) {
                System.out.println("You didn't found the word ! The word was : " + theword + "\n");
                if(playagain())
                    break;
            }
        }
    }
}
