package com.company;

public class MainMVC {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(
                MainMVC::creategame
        );
    }

    public static void creategame(){
        Game game = new Game();
        WordleController controller = new WordleController(game);
        WordleView view = new WordleView(game, controller);
    }
}
