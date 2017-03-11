import java.util.HashMap;
import java.util.Random;

public class Board extends HashMap<String, Pot> {
	
	public Board() {
		
		for (int i = 1; i<=14; i++){
			put(Integer.toString(i), new Pot(i));
		}
		
		get("7").empty();
		get("14").empty();
	}
	public Board(String boardPos){
		int[] stones = new int[14];
		int i=0;
		for (String a: boardPos.split(",")){
			stones[i] = Integer.parseInt(a);
			i++;
		}
		for (int j = 1; j<=14; j++){
			put(Integer.toString(j), new Pot(j));
			get(Integer.toString(j)).putStones(stones[j-1]);
		}
		
	}
	
	public boolean makeMove(String potName, int playerNum){
		Pot currentPot = this.get(potName);
		int stones = currentPot.stones;
		currentPot.empty();
		int potNum;
		while (stones>0){
			potNum = currentPot.name;
			if ((potNum!=6) && (potNum!=13))
				currentPot = this.get(currentPot.nextName);
			else if (((potNum==6) && (playerNum==1)) || ((potNum==13) && (playerNum==2)))
				currentPot = this.get(currentPot.nextName);
			else
				currentPot = this.get(currentPot.accrossName);
			currentPot.addStone();
			stones--;
		}
		//code for capturing
		if (currentPot.stones == 1){
			if ((currentPot.name < 7) && (playerNum == 1)){
				this.get("7").addStone(this.get(currentPot.accrossName).stones);
				this.get(currentPot.accrossName).empty();
				this.get("7").addStone();
				currentPot.empty();
			}
			else if ((7 < currentPot.name) && (currentPot.name< 14) && (playerNum == 2)){
				this.get("14").addStone(this.get(currentPot.accrossName).stones);
				this.get(currentPot.accrossName).empty();
				this.get("14").addStone();
				currentPot.empty();
			}
		}
		//code for going again if landing in your pot
		if ((currentPot.name == 7) || currentPot.name == 14){
			//System.out.println("Hey! You landed in your pot so you get to go again!");
			return true;
			}
		else
			return false;
	}
	
	public boolean gameOver(){
		int total1 = 0;
		int total2 = 0;
		Pot a;
		if (this.get("7").stones>24 || this.get("14").stones>24)
			return true;
		else{
			for (int i = 1; i<7; i++){

			a = this.get(Integer.toString(i));
			total1 = total1 + a.stones;
			total2 = total2 + this.get(a.accrossName).stones;
			}
		
			if ((total1 == 0) || (total2==0))
				return true;
			else 
				return false;
		}
	}
	
	public boolean tallyScores(int playerNum){
		int total1 = 0;
		int total2 = 0;
		Pot a;
		Pot b;
			for (int i = 1; i<7; i++){
				a = this.get(Integer.toString(i));
				b = this.get(a.accrossName);
				total1 = total1 + a.stones;
				total2 = total2 + b.stones;
				a.empty();
				b.empty();
			}
		this.get("7").addStone(total1);
		this.get("14").addStone(total2);

		int p1 = this.get("7").stones;
		int p2 = this.get("14").stones;
		int winner;
		if (p1>p2)
			winner = 1;
		else
			winner = 2;
//		System.out.println("This is the final score: Player 1 with " + p1 + " stones and Player 2 with " + p2 + " stones.");
//		System.out.println("     Congratulations Player " + winner + ", you win!!");
		if (winner == playerNum)
			return true;
		else 
			return false;
	}
	
	//randomly plays a full game and returns true if the starting player wins
	public boolean playGame(int playerNum){
		int startingPlayer = playerNum;
		String potNum;
		Random rand = new Random();
		while (!gameOver()){
			if (playerNum == 1){
				potNum = Integer.toString(rand.nextInt(6)+1);
				while (get(potNum).stones == 0){
					potNum = Integer.toString(rand.nextInt(6)+1);
				}
			}
			else{
				potNum = Integer.toString(rand.nextInt(6)+8);
				while (get(potNum).stones == 0){
					potNum = Integer.toString(rand.nextInt(6)+8);
				}
			}
			
			if (!makeMove(potNum, playerNum)){
				if (playerNum == 1)
					playerNum = 2;
				else
					playerNum = 1;
			}
			//printBoard();
		}
		
		
		return tallyScores(startingPlayer);
	}
	
	public void printBoard(){
		System.out.println();
		System.out.println("   Player 2's pot: " + this.get("14").stones);
		for (int i = 1; i<7; i++){
			System.out.print("Pot " + i + ": " + this.get(Integer.toString(i)).stones);
			System.out.println("      Pot " + (14-i) + ": " + this.get(Integer.toString(14-i)).stones);
		}
		System.out.println("   Player 1's pot: " + this.get("7").stones);
		System.out.println();
	}
	public boolean isEmpty(int playerNum) {
		if (playerNum == 1){
			for (int i = 1; i<7; i++){
				if (this.get(Integer.toString(i)).stones!=0)
					return false;
			}
			return true;
				
		}
		else{
			for (int i = 8; i<14; i++){
				if (this.get(Integer.toString(i)).stones!=0)
					return false;
			}
			return true;
		}
	}
	

	
	
	
	
	
//	
//
//	public Board(int initialCapacity) {
//		super(initialCapacity);
//		// TODO Auto-generated constructor stub
//	}
//
//	public Board(Map m) {
//		super(m);
//		// TODO Auto-generated constructor stub
//	}
//
//	public Board(int initialCapacity, float loadFactor) {
//		super(initialCapacity, loadFactor);
//		// TODO Auto-generated constructor stub
//	}

}
