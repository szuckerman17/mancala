

public class Pot extends Object {
	public int name;
	public String nextName;
	public String accrossName;
	public int stones;
	
	public Pot(int potnum) {
		name = potnum;
		stones = 4;
		accrossName = Integer.toString(14-potnum);
		if (potnum == 14)
			nextName = "1";
		else
			nextName = Integer.toString(potnum+1);
		
		
	}
	
	public void print(){
		System.out.println("Next pot: " + this.nextName);
		System.out.println("Accross Pot: " + this.accrossName);
		System.out.println("Number of Stones: " + this.stones);
		System.out.println();
	}
	
	public void addStone(){
		stones+=1;
	}
	public void addStone(int additional){
		stones = stones+additional;
	}
	
	public void empty(){
		stones = 0;
	}
	public void putStones(int stones){
		this.stones = stones;
	}

}
