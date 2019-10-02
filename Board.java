//CS664 Artificial Intelligence
//JAVA group #1, for on class project #1 Tic Tac Toe
//Silei Song
//Duoyao Zhang
//Matthew Tse
//Xi You


public class Board {

	private int []table; 
	private int []userArray;
	private int []machineArray;
	private int TURN;
	
	public Board() {
		TURN = 0;
		table = new int[9];
		for (int i = 0; i < 9; i++) 
			table[i] = 0;
		userArray = new int[8];
		machineArray = new int[8]; 
		for (int i = 0; i < 8; i++) {
			userArray[i] = 0;
			machineArray[i] = 0;
		}
	}
	
	/* public int getLoss() {
		return loss;
	}
	
	public int getTotalTurn() {
		return totalTurn;
	} */
	
	public void clean() {
		TURN = 0;
		for (int i = 0; i < 9; i++) 
			table[i] = 0;
		for (int i = 0; i < 8; i++) {
			userArray[i] = 0;
			machineArray[i] = 0;
		}
	}
	
	
	
	/* public boolean turn(int num) {
		if(!userMove(num)) {
			return false;
		}
		
		machineMove();
		TURN ++;
		System.out.println(this.toString());
		return false;
	} */
	
	public int checkEnd() {
		if(userSuccess())
			return 1; 
		else if(machineSuccess()) 
			return -1;
		else if(!checkfull())
			return 2;
		return 0;
	}
	
	public boolean userSuccess() {
		for(int i = 0; i < 8; i++) {
			if(userArray[i] == 3)
				return true;
		}
		return false;
	}
	
	public boolean machineSuccess() {
		for(int i = 0; i < 8; i++) {
			if(machineArray[i] == 3)
				return true;
		}
		return false;
	}
	
	public int machineFirst() {
		int rtn = machineMove();
		TURN ++;
		//System.out.println(this.toString());
		return rtn;
	}
	
	public boolean userMove(int num) {
		if(checkMove(num)) {
			table[num] = 1;
			userArrayChange(num);
			TURN++;
			return true;
		} else 
			return false;
	}
	
	private boolean checkMove(int num) {
		if(table[num]!=0) {
			//System.out.println("The choosing unit is already been occupied! Please try again.");
			return false;
		} else 
			return true;
	}
	
	private int[] BoardtoArray(int num) {
		int []line = new int[4];
		line[0] = num / 3;
		line[1] = (num % 3) + 3;
		if(num % 4 == 0) 
			line[2] = 6;
		else
			line[2] = -1;
		if(num % 2 == 0 && 0 < num && num < 8)
			line[3] = 7;
		else
			line[3] = -1;
		return line;
	}
	
	private void userArrayChange(int num) {
		int []line = BoardtoArray(num);
		for(int i = 0; i < 4; i++) {
			if(line[i] != -1) {
				if(userArray[line[i]] != -1)
					userArray[line[i]] ++;
				machineArray[line[i]] = -1;
			}
		}
	}
	
	public int machineMove() {
		int place = win();
		if(place != 10)
			return place;
		place = block();
		if(place != 10)
			return place;
		if(TURN == 2) {
			if((table[0] == 1 && table[8] == 1) || (table[2] == 1 && table[6] == 1)) {
				table[1] = 2;
				machineArrayChange(1);
				return 1;
			}
		}
		if(table[4] == 0) {
			table[4] = 2;
			machineArrayChange(4);
			return 4;
		} else {
			int Blockmost = blockMost();
			table[Blockmost] = 2;
			machineArrayChange(Blockmost);
			return Blockmost;
		}
	}
	
	private int blockMost() {
		int most = 0;
		int block = -1;
		for(int i = 0; i < 9; i ++) {
			if(table[i] == 0) {
				int []tempArray = BoardtoArray(i);
				int tempBlock = 0;
				for(int j = 0; j < 4; j++) {
					if(tempArray[j] != -1 && userArray[tempArray[j]] != -1) 
						tempBlock += (userArray[tempArray[j]] + 1);
				}
				if(tempBlock > block) {
					block = tempBlock;
					most = i;
				}
			}
		}
		return most;
	}
	
	private void machineArrayChange(int num) {
		int []line = BoardtoArray(num);
		for(int i = 0; i < 4; i++) {
			if(line[i] != -1) {
				if(machineArray[line[i]] != -1)
					machineArray[line[i]] ++;
				userArray[line[i]] = -1;
			}
		}
	}
	
	private int fill(int i) {
		if(0 <= i && i < 3) {
			for(int j = 0; j < 3; j++) {
				if(table[j + i*3] == 0) {
					table[j + i*3] = 2;
					return j + i * 3;
				}
			}
		} else if(i < 6) {
			for(int j = 0; j < 3; j++) {
				if(table[( i % 3 ) + ( 3 * j )] == 0) {
					table[( i % 3 ) + ( 3 * j )] = 2;
					return ( i % 3 ) + ( 3 * j );
				}
			}
		} else if(i == 6) {
			for(int j = 0; j < 9; j++) {
				if(j % 4 == 0 && table[j] == 0) {
					table[j] = 2;
					return j;
				}
			}
		} else {
			for(int j = 2; j < 8; j = j + 2) {
				if(table[j] == 0) {
					table[j] = 2;
					return j;
				}
			}
		}
		
		return -1;

	}
	
	private int win() {
		for(int i = 0; i < 8; i++) {
			if(machineArray[i] == 2) {
				int Fill = fill(i);
				machineArrayChange(Fill);
				//System.out.println("You lose!");
				return Fill;
			}
		}
		return 10;
	}
	
	private int block() {
		for(int i = 0; i < 8; i++) {
			if(userArray[i] == 2) {
				int Fill = fill(i);
				machineArrayChange(Fill);
				//System.out.println("block!");
				return Fill;
			}
		}
		return 10;
	}
	
	public boolean checkfull() {
		for(int i = 0; i < 9; i++) {
			if(table[i] == 0)
				return true;
		}
		//System.out.println("draw");
		return false;
	}
	
	public boolean checkWin() {
		for(int i = 0; i < 8; i++) {
			if(machineArray[i] == 3)
				return true;
		}
		return false;
	}
	
	public String toString() {
		String rtn = "";
		for(int i = 0; i < 9; i++) {
			rtn += table[i] + " ";
			if(i % 3 == 2)
				rtn += "\n";
		}
		rtn += "this is turn ";
		rtn += TURN;
		rtn += "\n";
		return rtn;
	}
	
	
}
