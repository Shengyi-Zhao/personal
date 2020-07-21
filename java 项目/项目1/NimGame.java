import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*
 @author Shengyi Zhao
 @student id : 990160
 @Nim game process projB
 */

public class NimGame {                                                //A NimGame for start a game by Nim
    List<String> user_list = new ArrayList<String>();
    public static ArrayList<NimPlayer> startgame(String[] str_arr, Scanner keyboard, List<String> user_list, ArrayList<NimPlayer> players){
        int stone=Integer.parseInt(str_arr[1]);
        int upper=Integer.parseInt(str_arr[2]);
        String username1=str_arr[3];
        String username2=str_arr[4];
        if(!user_list.contains(username1)||!user_list.contains(username2)){
            System.out.println("One of the players does not exist.");
            System.out.println();
            return players;
        }else {

            boolean player1_ai=false;
            boolean player2_ai=false;
            for (NimPlayer player : players) {
                if (player.get_user_name().equalsIgnoreCase(username1)) {
                    if (player instanceof NimAIPlayer){
                        player1_ai=true;
                    }else {
                        player1_ai=false;
                    }
                } else if (player.get_user_name().equalsIgnoreCase(username2)) {
                    if (player instanceof NimAIPlayer){
                        player2_ai=true;
                    }else { player2_ai=false; }
                }
            }
            if(player1_ai==true&&player2_ai==true){
                NimAIPlayer nplayer1=new NimAIPlayer();
                NimAIPlayer nplayer2=new NimAIPlayer();
                for(NimPlayer player:players){
                    if(player.get_user_name().equalsIgnoreCase(username1)){
                        nplayer1= (NimAIPlayer) player;
                    }else if(player.get_user_name().equalsIgnoreCase(username2)){
                        nplayer2= (NimAIPlayer) player;
                    }
                }
                System.out.println();
                System.out.println("Initial stone count: "+stone);
                System.out.println("Maximum stone removal: "+upper);
                System.out.println("Player 1: "+nplayer1.getfullname());
                System.out.println("Player 2: "+nplayer2.getfullname());
                System.out.println();
                int turn=0;
                int move;
                int left = stone;
                for (turn = 0; left > 0;) {                     //the for loop until the left stones is 0
                    System.out.print(left + " stones left:");
                    for (int n = 0; n < left; n++) {
                        System.out.print(" *");
                    }
                    System.out.println();
                    if ((turn % 2) == 0) {                         //user the parameter turn decide who's turn to play
                        move = nplayer1.removeStone(keyboard,upper);
                        if(move>=1&&move<=upper){
                            left = left - move;
                            if (left <= 0) {                           //another player win if on player clear rest stones
                                System.out.println("Game Over");
                                System.out.println(nplayer2.win_game());
                                for(NimPlayer player:players){
                                    if(player.get_user_name().equalsIgnoreCase(username2)){
                                        player.set_win();
                                    }else if(player.get_user_name().equalsIgnoreCase(username1)){
                                        player.set_lose();
                                    }
                                }
                            }
                            turn=turn+1;
                        }else if(move>upper){
                            System.out.println("Invalid move. You must remove between 1 and "+upper+" stones.");
                            System.out.println();
                        }else {
                            System.out.println("Invalid move. You must remove between 1 and 1 stones.");
                            System.out.println();
                        }
                    } else {
                        move = nplayer2.removeStone(keyboard,upper);
                        if(move>=1&&move<=upper){
                            left = left - move;
                            if (left <= 0) {
                                System.out.println("Game Over");
                                System.out.println(nplayer1.win_game());
                                for(NimPlayer player:players){
                                    if(player.get_user_name().equalsIgnoreCase(username1)){
                                        player.set_win();
                                    }else if(player.get_user_name().equalsIgnoreCase(username2)){
                                        player.set_lose();
                                    }
                                }
                            }
                            turn=turn+1;
                        }else if (move>upper){
                            System.out.println("Invalid move. You must remove between 1 and "+upper+" stones.");
                            System.out.println();
                        }else {
                            System.out.println("Invalid move. You must remove between 1 and 1 stones.");
                            System.out.println();
                        }
                    }
                }
                return players;
            }else if(player1_ai&&player2_ai==false){
                    NimAIPlayer nplayer1=new NimAIPlayer();
                    NimHumanPlayer nplayer2=new NimHumanPlayer();
                    for(NimPlayer player:players){
                        if(player.get_user_name().equalsIgnoreCase(username1)){
                            nplayer1= (NimAIPlayer) player;
                        }else if(player.get_user_name().equalsIgnoreCase(username2)){
                            nplayer2= (NimHumanPlayer) player;
                        }
                    }
                    System.out.println();
                    System.out.println("Initial stone count: "+stone);
                    System.out.println("Maximum stone removal: "+upper);
                    System.out.println("Player 1: "+nplayer1.getfullname());
                    System.out.println("Player 2: "+nplayer2.getfullname());
                    System.out.println();
                    int turn=0;
                    int move;
                    int left = stone;
                    for (turn = 0; left > 0;) {                     //the for loop until the left stones is 0
                        System.out.print(left + " stones left:");
                        for (int n = 0; n < left; n++) {
                            System.out.print(" *");
                        }
                        System.out.println();
                        if ((turn % 2) == 0) {                         //user the parameter turn decide who's turn to play
                            move = nplayer1.removeStone(keyboard,upper);
                            if(move>=1&&move<=upper){
                                left = left - move;
                                if (left <= 0) {                           //another player win if on player clear rest stones
                                    System.out.println("Game Over");
                                    System.out.println(nplayer2.win_game());
                                    for(NimPlayer player:players){
                                        if(player.get_user_name().equalsIgnoreCase(username2)){
                                            player.set_win();
                                        }else if(player.get_user_name().equalsIgnoreCase(username1)){
                                            player.set_lose();
                                        }
                                    }
                                }
                                turn=turn+1;
                            }else if(move>upper){
                                System.out.println("Invalid move. You must remove between 1 and "+upper+" stones.");
                                System.out.println();
                            }else {
                                System.out.println("Invalid move. You must remove between 1 and 1 stones.");
                                System.out.println();
                            }
                        } else {
                            move = nplayer2.removeStone(keyboard,upper);
                            if(move>=1&&move<=upper){
                                left = left - move;
                                if (left <= 0) {
                                    System.out.println("Game Over");
                                    System.out.println(nplayer1.win_game());
                                    for(NimPlayer player:players){
                                        if(player.get_user_name().equalsIgnoreCase(username1)){
                                            player.set_win();
                                        }else if(player.get_user_name().equalsIgnoreCase(username2)){
                                            player.set_lose();
                                        }
                                    }
                                }
                                turn=turn+1;
                            }else if (move>upper){
                                System.out.println("Invalid move. You must remove between 1 and "+upper+" stones.");
                                System.out.println();
                            }else {
                                System.out.println("Invalid move. You must remove between 1 and 1 stones.");
                                System.out.println();
                            }
                        }
                    }
                    return players;
                }else if(player1_ai==false&&player2_ai==true){
                NimHumanPlayer nplayer1=new NimHumanPlayer();
                NimAIPlayer nplayer2=new NimAIPlayer();
                for(NimPlayer player:players){
                    if(player.get_user_name().equalsIgnoreCase(username1)){
                        nplayer1= (NimHumanPlayer) player;
                    }else if(player.get_user_name().equalsIgnoreCase(username2)){
                        nplayer2= (NimAIPlayer) player;
                    }
                }
                System.out.println();
                System.out.println("Initial stone count: "+stone);
                System.out.println("Maximum stone removal: "+upper);
                System.out.println("Player 1: "+nplayer1.getfullname());
                System.out.println("Player 2: "+nplayer2.getfullname());
                System.out.println();
                int turn=0;
                int move;
                int left = stone;
                for (turn = 0; left > 0;) {                     //the for loop until the left stones is 0
                    System.out.print(left + " stones left:");
                    for (int n = 0; n < left; n++) {
                        System.out.print(" *");
                    }
                    System.out.println();
                    if ((turn % 2) == 0) {                         //user the parameter turn decide who's turn to play
                        move = nplayer1.removeStone(keyboard,upper);
                        if(move>=1&&move<=upper){
                            left = left - move;
                            if (left <= 0) {                           //another player win if on player clear rest stones
                                System.out.println("Game Over");
                                System.out.println(nplayer2.win_game());
                                for(NimPlayer player:players){
                                    if(player.get_user_name().equalsIgnoreCase(username2)){
                                        player.set_win();
                                    }else if(player.get_user_name().equalsIgnoreCase(username1)){
                                        player.set_lose();
                                    }
                                }
                            }
                            turn=turn+1;
                        }else if(move>upper){
                            System.out.println("Invalid move. You must remove between 1 and "+upper+" stones.");
                            System.out.println();
                        }else {
                            System.out.println("Invalid move. You must remove between 1 and 1 stones.");
                            System.out.println();
                        }
                    } else {
                        move = nplayer2.removeStone(keyboard,upper);
                        if(move>=1&&move<=upper){
                            left = left - move;
                            if (left <= 0) {
                                System.out.println("Game Over");
                                System.out.println(nplayer1.win_game());
                                for(NimPlayer player:players){
                                    if(player.get_user_name().equalsIgnoreCase(username1)){
                                        player.set_win();
                                    }else if(player.get_user_name().equalsIgnoreCase(username2)){
                                        player.set_lose();
                                    }
                                }
                            }
                            turn=turn+1;
                        }else if (move>upper){
                            System.out.println("Invalid move. You must remove between 1 and "+upper+" stones.");
                            System.out.println();
                        }else {
                            System.out.println("Invalid move. You must remove between 1 and 1 stones.");
                            System.out.println();
                        }
                    }
                }
                return players;
            }else {
                NimHumanPlayer nplayer1=new NimHumanPlayer();
                NimHumanPlayer nplayer2=new NimHumanPlayer();
                for(NimPlayer player:players){
                    if(player.get_user_name().equalsIgnoreCase(username1)){
                        nplayer1= (NimHumanPlayer) player;
                    }else if(player.get_user_name().equalsIgnoreCase(username2)){
                        nplayer2= (NimHumanPlayer) player;
                    }
                }
                System.out.println();
                System.out.println("Initial stone count: "+stone);
                System.out.println("Maximum stone removal: "+upper);
                System.out.println("Player 1: "+nplayer1.getfullname());
                System.out.println("Player 2: "+nplayer2.getfullname());
                System.out.println();
                int turn=0;
                int move;
                int left = stone;
                for (turn = 0; left > 0;) {                     //the for loop until the left stones is 0
                    System.out.print(left + " stones left:");
                    for (int n = 0; n < left; n++) {
                        System.out.print(" *");
                    }
                    System.out.println();
                    if ((turn % 2) == 0) {                         //user the parameter turn decide who's turn to play
                        move = nplayer1.removeStone(keyboard,upper);
                        if(move>=1&&move<=upper){
                            left = left - move;
                            if (left <= 0) {                           //another player win if on player clear rest stones
                                System.out.println("Game Over");
                                System.out.println(nplayer2.win_game());
                                for(NimPlayer player:players){
                                    if(player.get_user_name().equalsIgnoreCase(username2)){
                                        player.set_win();
                                    }else if(player.get_user_name().equalsIgnoreCase(username1)){
                                        player.set_lose();
                                    }
                                }
                            }
                            turn=turn+1;
                        }else if(move>upper){
                            System.out.println("Invalid move. You must remove between 1 and "+upper+" stones.");
                            System.out.println();
                        }else {
                            System.out.println("Invalid move. You must remove between 1 and 1 stones.");
                            System.out.println();
                        }
                    } else {
                        move = nplayer2.removeStone(keyboard,upper);
                        if(move>=1&&move<=upper){
                            left = left - move;
                            if (left <= 0) {
                                System.out.println("Game Over");
                                System.out.println(nplayer1.win_game());
                                for(NimPlayer player:players){
                                    if(player.get_user_name().equalsIgnoreCase(username1)){
                                        player.set_win();
                                    }else if(player.get_user_name().equalsIgnoreCase(username2)){
                                        player.set_lose();
                                    }
                                }
                            }
                            turn=turn+1;
                        }else if (move>upper){
                            System.out.println("Invalid move. You must remove between 1 and "+upper+" stones.");
                            System.out.println();
                        }else {
                            System.out.println("Invalid move. You must remove between 1 and 1 stones.");
                            System.out.println();
                        }
                    }
                }
                return players;
            }
            }
            }
}
