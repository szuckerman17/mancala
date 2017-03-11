import java.util.*;

public class MCTS extends HashMap<String, Data>{
	
	public MCTS() {
	
	}
	
	public void printTree(){
		for (Map.Entry<String, Data> entry: this.entrySet()){
			System.out.println("Board: " + entry.getKey() + ";   Games: " + entry.getValue().games + ";   Wins: " + entry.getValue().wins);
			for (int i = 0; i<entry.getValue().possibleMoves.size(); i++)
				System.out.println(entry.getValue().possibleMoves.get(i));
		}
	}
	
	public int bestMove(String position){
		double games;
		double wins;
		double bestPer = 0;
		String bestMove = "";
		ArrayList<String> moveSet = this.get(position).possibleMoves;
		for (int i = 0; i<moveSet.size(); i++){
			games = this.get(moveSet.get(i)).games;
			wins = this.get(moveSet.get(i)).wins;
			double winPer = wins/games;
		
			if ((wins/games)>=bestPer){
				bestPer = wins/games;
				bestMove = moveSet.get(i);
			}
		}
		int[] oldBoard = new int[14];	
		int i = 0;
		for (String a: position.split(",")){
			oldBoard[i] = Integer.parseInt(a);
			i++;
		}
		
		
		int[] newBoard = new int[14];
		i = 0;
		for (String a: bestMove.split(",")){
			newBoard[i] = Integer.parseInt(a);
			i++;
		}
		
		int potMove = 0;
		for (i = 6; i<=13; i++){
			if (newBoard[i]<oldBoard[i]){
				System.out.println("new board at " + (i+1) + ": "+ newBoard[i]);
				System.out.println("old board at " + (i+1) + ": "+ oldBoard[i]);
				potMove = i;
			}
		}
		return potMove+1;
	}
	
	
}   
