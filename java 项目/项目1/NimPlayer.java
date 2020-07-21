import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
/*
 @author Shengyi Zhao
 @student id : 990160
 @Nim gamer projB
 */
public abstract class NimPlayer implements Serializable { //a Nimplayer of a player in Nim game with all the information of him
    private String user_name;
    private String family_name;
    private String given_name;
    private int games;
    private int wins;

    public abstract void setname(String user,String family,String given);        //set name of user

    public abstract String getfullname();       //return full name
    public abstract String get_user_name();                      //return user name
    public abstract void reset();                                                       //reset user static

    public abstract String display();
    public abstract double getpercentage();                                          //return the percentage of winning

    @Override
    public String toString() {
        NumberFormat nf   =   NumberFormat.getPercentInstance();
        String result;
        if(this.games==0){
            result="0%";
        }else {
            nf.setMinimumFractionDigits(0);
            result=nf.format((double)this.wins/this.games);
        }
        if(this.games<10){
            if(result.length()=="100%".length()) {
                return (result + " | 0" + this.games + " games | " + given_name + " " + family_name);
            }else if(result.length()=="10%".length()){
                return (result + "  | 0" + this.games + " games | " + given_name + " " + family_name);
            }else {
                return (result + "   | 0" + this.games + " games | " + given_name + " " + family_name);
            }
        }else {
            if(result.length()=="100%".length()) {
                return (result + " | " + this.games + " games | " + given_name + " " + family_name);
            }else if(result.length()=="10%".length()){
                return (result + "  | " + this.games + " games | " + given_name + " " + family_name);
            }else {
                return (result + "   | " + this.games + " games | " + given_name + " " + family_name);
            }
        }

    }

    public static Comparator ascComparator = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            if (((NimPlayer) o1).getpercentage() ==((NimPlayer) o2).getpercentage()){
                String first=((NimPlayer) o1).get_user_name();
                String second=((NimPlayer) o2).get_user_name();
                ArrayList<String> they_two=new ArrayList<String>();
                they_two.add(first);
                they_two.add(second);
                Collections.sort(they_two);
                if(they_two.get(0).equalsIgnoreCase(first)){
                    return -1;
                }else {
                    return 1;
                }
            }else if(((NimPlayer) o1).getpercentage() < ((NimPlayer) o2).getpercentage()){return -1;}
            else {return 1; }
        }
    };
    public static Comparator descComparator = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            if (((NimPlayer) o1).getpercentage() ==((NimPlayer) o2).getpercentage()){
                String first=((NimPlayer) o1).get_user_name();
                String second=((NimPlayer) o2).get_user_name();
                ArrayList<String> they_two=new ArrayList<String>();
                they_two.add(first);
                they_two.add(second);
                Collections.sort(they_two);
                if(they_two.get(0).equalsIgnoreCase(first)){
                    return -1;
                }else {
                    return 1;
                }
            }else if(((NimPlayer) o1).getpercentage() < ((NimPlayer) o2).getpercentage()){return 1;}
            else {return -1; }
            }
        };
    public NimPlayer() {
        super();
    }

   public abstract int removeStone(Scanner keyboard,int upper);

    public abstract String win_game() ;     //return he wins! after game

    public abstract void set_win();                  //change the number of wining game if he win

    public abstract void set_lose();
    static Object readFile() throws IOException {
        Object temp = null;
        File file = new File("players.dat");
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            objIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }
    static void writeFile(Object obj) throws IOException {
        File file = new File("players.dat");
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



