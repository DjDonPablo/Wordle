package com.company;

public class MainCLI {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(
                MainCLI::creategame
        );
    }

    public static void creategame(){
        Game game = new Game();
        CommandLineInterface cmd = new CommandLineInterface(game);
    }
}
