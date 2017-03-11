import java.util.*;
import java.util.Map.Entry;

public class Mancala{
    Board board;
    public  MCTS tree;
 

    public Mancala(){
        board = new Board();
	tree = new MCTS();
	for (int i = 0; i <50000; i++){
	    buildTree(board);
	    board = new Board();
	}
	System.out.println("ready");
    }

	
    public void buildTree(Board board){
	ArrayList<String>boardPos = new ArrayList<String>();
	int posIndex = 0;
	HashSet<Integer> player2 = new HashSet<Integer>();//change to hashSet
	String potNum;
	int playerNum = 2;
	Random rand = new Random();
	boardPos.add(stringPos(board));
	String originalBoard = stringPos(board);
	player2.add(0);
	while (this.tree.get(boardPos.get(posIndex))!=null && !board.gameOver()){
	    posIndex++;
	    if (playerNum == 2){
		potNum = Integer.toString(rand.nextInt(6)+8);
		while (board.get(potNum).stones == 0){
		    if (board.isEmpty(2))
			break;
		    potNum = Integer.toString(rand.nextInt(6)+8);
		}
		player2.add(posIndex);
	    }

	    else{
		potNum = Integer.toString(rand.nextInt(6)+1);
		while (board.get(potNum).stones == 0){
		    if (board.isEmpty(1))
			break;
		    potNum = Integer.toString(rand.nextInt(6)+1);
		}
	    }
	    while(board.makeMove(potNum, playerNum)){
		if (tree.get(stringPos(board))==null)
		    break;
		boardPos.add(stringPos(board));
		posIndex++;
		if (playerNum==2){
		    potNum = Integer.toString(rand.nextInt(6)+8);
		    while (board.get(potNum).stones == 0){
			if (board.isEmpty(2))
			    break;
			potNum = Integer.toString(rand.nextInt(6)+8);
		    }
		    player2.add(posIndex);
		}
		else{
		    potNum = Integer.toString(rand.nextInt(6)+1);
		    while (board.get(potNum).stones == 0){
			if (board.isEmpty(1))
			    break;
			potNum = Integer.toString(rand.nextInt(6)+1);
		    }
		}
	    }

	    if (playerNum==1)
		playerNum=2;
	    else 
		playerNum = 1;
	    boardPos.add(stringPos(board));

	}
	this.tree.put(boardPos.get(posIndex), new Data());
	String originalPos = stringPos(board);
	if (board.playGame(2)){
	    int i = 0;
	    while (boardPos.size()>i){
		if (player2.contains(i))
		    this.tree.get(boardPos.get(i)).addWin();
		this.tree.get(boardPos.get(i)).addGame();
		if (player2.contains(i)){
		    if(i<boardPos.size()-1){
			if (!this.tree.get(boardPos.get(i)).equals(originalPos))
			    this.tree.get(boardPos.get(i)).addPossibleMove(boardPos.get(i+1));
		    }
		}	

		i++;
	    }
	}
	else{
	    int i = 0;
	    while (boardPos.size()>i){
		this.tree.get(boardPos.get(i)).addGame();
		if(i<boardPos.size()-1){
		    if (player2.contains(i))
			this.tree.get(boardPos.get(i)).addPossibleMove(boardPos.get(i+1));
		}
		i++;
	    }
	}
		
    }
	
    public static String stringPos(Board board){
	String pos = "";
	for (int i = 1; i <=14; i++){
	    pos= pos + Integer.toString(board.get(Integer.toString(i)).stones) + ",";
	}
	return pos;
    }
    

    public String compMove(String  board){
	Board pass = new Board(board);
	for (int i = 0; i<120000; i++){
	    buildTree(pass);
	    pass = new Board(board);
	}
	int potNum;
		
		
	potNum = this.tree.bestMove(board);
	System.out.println("Computer moves on pot " + potNum);
	return Integer.toString(potNum);
		
		
    }
	
	
}