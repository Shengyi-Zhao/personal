/*
	NimAIPlayer.java
	
	This class is provided as a skeleton code for the tasks of 
	Sections 2.4, 2.5 and 2.6 in Project C. Add code (do NOT delete any) to it
	to finish the tasks. 
*/

import java.text.NumberFormat;
import java.util.*;

public class NimAIPlayer extends NimPlayer implements Testable {
	// you may further extend a class or implement an interface
	// to accomplish the tasks.
	public NimAIPlayer() {super();}
	public String advancedMove(boolean[] available, String lastMove) {
		// the implementation of the victory
		// guaranteed strategy designed by you
		String move = "";
		return move;
	}
	private String user_name;
	private String family_name;
	private String given_name;
	private int games;
	private int wins;

	public void setname(String user,String family,String given) {         //set name of user
		this.user_name=user;
		this.family_name=family;
		this.given_name=given;
		this.wins=0;
		this.games=0;

	}
	public String getfullname() {
		return  given_name+" "+family_name;
	}        //return full name
	public String get_user_name() {
		return  user_name;
	}                       //return user name
	public void reset(){                                                       //reset user static
		this.games=0;
		this.wins=0;
	}
	public String display(){
		return(user_name+","+given_name+","+family_name+","+games+" games,"+wins+" wins");
	}
	public double getpercentage(){                                           //return the percentage of winning
		double a;
		if(this.games==0){
			a=(double) 0;
		}else {
			a=(double)this.wins/this.games;
		}
		return a;
	}
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


	public String win_game() {
		return  given_name+" "+family_name+" wins!\n";                      //return he wins! after game
	}
	public void set_win(){                                                  //change the number of wining game if he win
		this.wins=this.wins+1;
		this.games=this.games+1;
	}
	public void set_lose(){                                               //change the number of playing game if he lose
		this.games=this.games+1;
	}


	public int removeStone(Scanner keyboard,int upper){
		System.out.println(given_name + "\'s turn - remove how many?\n");
		Random random = new Random();
		return random.nextInt(upper)%(upper-1+1) + 1;
	}
}
