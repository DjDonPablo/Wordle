package com.company;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class WordleFindWord {
    private final File resultwords;
    private final File acceptwords;
    public String theword;
    private final ArrayList<String> words;
    private final ArrayList<String> awords;

    public int filenblines() { //get number of lines for gettheword
        try{
            BufferedReader scanner = new BufferedReader(new FileReader(resultwords));
            int nblines = 0;
            while(scanner.readLine() != null)
                nblines++;
            scanner.close();
            return nblines;
        }
        catch (IOException e) {
            System.out.println("File not found.");
            return -1;
        }
    }

    public String gettheword(int nblines) { //get the word from result words
        try{
            Scanner scanner = new Scanner(resultwords);
            Random rand = new Random();
            int random = rand.nextInt(nblines);
            for(int i = 0; i < random; i++)
                scanner.nextLine();
            return scanner.nextLine();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return null;
        }
    }

    public boolean isthewordin(String word) { //return if a word is in
        return words.contains(word) || awords.contains(word);
    } //check if word is in one of the two lists

    public void fillwords() { //fill words with resultwords
        try{
            BufferedReader scanner = new BufferedReader(new FileReader(resultwords));
            String curword = scanner.readLine();
            while(curword != null) {
                words.add(curword);
                curword = scanner.readLine();
            }
            scanner.close();
        }
        catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public void fillawords() { //fill words with acceptwords
        try{
            BufferedReader scanner = new BufferedReader(new FileReader(acceptwords));
            String curword = scanner.readLine();
            while(curword != null) {
                awords.add(curword);
                curword = scanner.readLine();
            }
            scanner.close();
        }
        catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public WordleFindWord() { //Contructor
        resultwords = new File("C:\\Users\\maelr\\IdeaProjects\\Wordle\\src\\common.txt");
        acceptwords = new File("C:\\Users\\maelr\\IdeaProjects\\Wordle\\src\\words.txt");
        int nblines = filenblines();
        theword = gettheword(nblines);
        words = new ArrayList<>();
        awords = new ArrayList<>();
        fillwords();
        fillawords();
    }
}
