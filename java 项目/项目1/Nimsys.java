import java.io.*;
import java.util.*;
/*
 @author Shengyi Zhao
 @student id : 990160
 @Nim game projB
 */

public class Nimsys {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Welcome to Nim");
        System.out.println();
        System.out.print("$");
        Scanner keyboard = new Scanner(System.in);
        ArrayList<NimPlayer> players = new ArrayList<NimPlayer>();//Array list for Nimplayer class instance
        ArrayList<String> user_list = new ArrayList<String>();       //Array list for user name
        String FileName = "players.dat";
        File file = new File(FileName);

        if(!file.exists()) {
            file.createNewFile();
        }else {
            FileInputStream in;
            in = new FileInputStream(file);
            ArrayList objArr = new ArrayList<>();
            objArr = (ArrayList) NimPlayer.readFile();
            for (Object new_nim:objArr){
                players.add((NimPlayer) new_nim);
                user_list.add(((NimPlayer) new_nim).get_user_name());
            }
        }



            boolean if_continue = true;
        while (if_continue) {              //the loop until the player say no
            String str;
            str = keyboard.nextLine();
            String[] str_arr = str.split(" |,");
            String act_judge = str_arr[0];
            switch (act_judge)                      //judge which action we need by first word
            {
                case ("addplayer"):

                    if (str_arr.length < 4) {
                        System.out.println("Incorrect number of arguments supplied to command.");
                    } else {
                        String user_name = str_arr[1];
                        String family_name = str_arr[2];
                        String given_name = str_arr[3];
                        String[] new_str = {user_name, family_name, given_name};
                        boolean exist = false;
                        for (int m = 0; m < user_list.size(); m++) {
                            if (user_list.get(m).equalsIgnoreCase(user_name)) {
                                System.out.println("The player already exists.");
                                exist = true;
                            }
                        }
                        if (exist == false) {
                            user_list.add(user_name);
                            NimHumanPlayer NEW = new NimHumanPlayer();
                            NEW.setname(user_name, family_name, given_name);
                            players.add(NEW);
                        }
                    }
                    System.out.print("\n$");
                    break;
                case ("addaiplayer"):
                    if (str_arr.length < 4) {
                        System.out.println("Incorrect number of arguments supplied to command.");
                    } else {
                        String user_name = str_arr[1];
                        String family_name = str_arr[2];
                        String given_name = str_arr[3];
                        String[] new_str = {user_name, family_name, given_name};
                        boolean exist = false;
                        for (int m = 0; m < user_list.size(); m++) {
                            if (user_list.get(m).equalsIgnoreCase(user_name)) {
                                System.out.println("The player already exists.");
                                exist = true;
                            }
                        }
                        if (exist == false) {
                            user_list.add(user_name);
                            NimAIPlayer NEW = new NimAIPlayer();
                            NEW.setname(user_name, family_name, given_name);
                            players.add(NEW);
                        }
                    }
                    System.out.print("\n$");
                    break;
                case ("removeplayer"):
                    if (str_arr.length == 1) {
                        System.out.println("Are you sure you want to remove all players? (y/n)");
                        if (keyboard.nextLine().equalsIgnoreCase("y")) {
                            user_list.clear();
                            players.clear();
                        }
                    } else {
                        String user_name = str_arr[1];
                        if (!user_list.contains(user_name)) {
                            System.out.println("The player does not exist.");
                        } else {
                            user_list.remove("user_name");
                            Iterator<NimPlayer> iterator = players.iterator();
                            while (iterator.hasNext()) {
                                NimPlayer integer = iterator.next();
                                if (integer.get_user_name().equalsIgnoreCase(user_name)) {
                                    iterator.remove();
                                }
                            }
                        }
                    }
                    System.out.print("\n$");
                    break;
                case ("editplayer"):
                    if (str_arr.length < 4) {
                        System.out.println("Incorrect number of arguments supplied to command.");
                    } else {
                        String user_name = str_arr[1];
                        String family_name = str_arr[2];
                        String given_name = str_arr[3];
                        if (user_list.contains(user_name)) {
                            for (NimPlayer edit : players) {
                                if (edit.get_user_name().equalsIgnoreCase(user_name)) {
                                    edit.setname(user_name, family_name, given_name);
                                }
                            }
                        } else {
                            System.out.println("The player does not exist.");
                        }
                    }
                    System.out.print("\n$");
                    break;
                case ("resetstats"):
                    if (str_arr.length == 1) {
                        System.out.println("Are you sure you want to reset all player statistics? (y/n)");
                        if (keyboard.nextLine().equalsIgnoreCase("y")) {
                            if (players.size() != 0) {
                                for (NimPlayer reset_players : players) {
                                    reset_players.reset();
                                }
                            }
                        }
                    } else {
                        String user_name = str_arr[1];
                        if (!user_list.contains(user_name)) {
                            System.out.println("The player does not exist.");
                        } else {
                            for (NimPlayer reset_player : players) {
                                if (reset_player.get_user_name().equalsIgnoreCase(user_name)) {
                                    reset_player.reset();
                                }
                            }
                        }
                    }
                    System.out.print("\n$");
                    break;
                case ("displayplayer"):
                    if (str_arr.length == 1) {
                        Collections.sort(user_list);
                        for (String user : user_list) {
                            for (NimPlayer display_players : players) {
                                if (display_players.get_user_name().equalsIgnoreCase(user)) {
                                    System.out.println(display_players.display());
                                }
                            }
                        }
                    } else {
                        String user_name = str_arr[1];
                        if (!user_list.contains(user_name)) {
                            System.out.println("The player does not exist.");
                        } else {
                            for (NimPlayer display_player : players) {
                                if (display_player.get_user_name().equalsIgnoreCase(user_name)) {
                                    System.out.println(display_player.display());
                                }
                            }
                        }
                    }
                    System.out.print("\n$");
                    break;
                case ("rankings"):
                    if (str_arr.length == 2 && str_arr[1].equalsIgnoreCase("asc")) {
                        Collections.sort(players, NimPlayer.ascComparator);
                        int asc_rank = 1;
                        for (NimPlayer asc_player : players) {
                            System.out.println(asc_player);
                            asc_rank = asc_rank + 1;
                            if (asc_rank > 10) {
                                break;
                            }
                        }
                    } else {
                        Collections.sort(players, NimPlayer.descComparator);
                        int desc_rank = 1;
                        for (NimPlayer asc_player : players) {
                            System.out.println(asc_player);
                            desc_rank = desc_rank + 1;
                            if (desc_rank > 10) {
                                break;
                            }
                        }
                    }
                    System.out.print("\n$");
                    break;
                case ("startgame"):
                    if (str_arr.length < 5) {
                        System.out.println("Incorrect number of arguments supplied to command.");
                    } else {
                        int stone = Integer.parseInt(str_arr[1]);
                        int upper = Integer.parseInt(str_arr[2]);
                        String username1 = str_arr[3];
                        String username2 = str_arr[4];
                        if (!user_list.contains(username1) || !user_list.contains(username2)) {
                            System.out.println("One of the players does not exist.");
                            System.out.println();
                        } else {
                            players = NimGame.startgame(str_arr, keyboard, user_list, players);
                        }

                    }
                    System.out.print("$");
                    break;
                case ("exit"):
                    if_continue = false;
                    NimPlayer.writeFile(players);
                    System.out.print("\n");
                    break;
                case (""):
                    break;
                default:
                    System.out.println("'" + act_judge + "' is not a valid command.");
                    System.out.print("\n$");
            }
        }
    }
}

