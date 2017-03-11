import java.util.ArrayList;

public class Data extends Object{
	int games;
	int wins;
	ArrayList<String> possibleMoves = new ArrayList<String>();
	ArrayList<String> possibleBoards = new ArrayList<String>();
	
	
	public Data() {
		games = 0;
		wins = 0;
	}
	
	public void addGame(){
		this.games+=1;
	}
	
	public void addWin(){
		this.wins+=1;
	}
	public void addPossibleMove(String movePos){
		if(possibleMoves.contains(movePos)!=true)
			possibleMoves.add(movePos);
	}
	
}