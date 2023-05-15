package pt.upskill.projeto1.objects;
import pt.upskill.projeto1.rogue.utils.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class LeaderBoard implements Comparable<LeaderBoard>, Serializable {

    private String username;
    private int score;

    public LeaderBoard(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public int compareTo(LeaderBoard o){
        if (score==o.score){
            return 0;
        } else if (score>o.score){
            return -1;
        } else {
            return 1;
        }
    }

    public static void showPanelEndGame(String path, boolean win){
        try {
            Scanner scanner = new Scanner(new File(path));
            Dictionary leaderBoardMessageDictionary = new Hashtable();
            leaderBoardMessageDictionary = Utils.populateLeaderBoardMessage();

            int count = 0;
            int leaderBoardCounting = 0;
            String showLeaderboard;

            while(scanner.hasNextLine()) {
                scanner.nextLine();
                count++;
            }

            scanner = new Scanner(new File(path));
            LeaderBoard[] leaderBoard = new LeaderBoard[count];

            showLeaderboard = leaderBoardMessageDictionary.get(win).toString();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[]aux;
                aux = line.split(";");

                leaderBoard[leaderBoardCounting] = new LeaderBoard(aux[0], Integer.parseInt(aux[1]));
                leaderBoardCounting++;
            }

            int countScores = 10;
            if(leaderBoard.length < 10){
                countScores = leaderBoard.length;
            }

            Arrays.sort(leaderBoard);

            for(int i=0; i<countScores; i++){
                showLeaderboard += "\n" + leaderBoard[i].getUsername() + "   |   " + leaderBoard[i].getScore();
            }
            JOptionPane.showMessageDialog(null, showLeaderboard);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
