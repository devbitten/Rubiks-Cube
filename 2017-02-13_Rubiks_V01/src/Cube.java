import java.util.Random;

public class Cube {
	//The face value array is defined as follows:
	//face[ X COORDINATE FROM UPPER LEFT,
	//  Y COORDINATE FROM UPPER LEFT,
	//  FACE VALUE]
	// 0-0, 1-0, 2-0
	// 0-0, 1-0, 2-0
	// 0-0, 1-0, 2-0
	//THE DEFAULT X,Y ORIENTATION FOR THE CUBE IS
	//			TOP
	//		LEF	FRO	RIG
	// 			BOT
	//			BAC
	//{face values: bottom = 0; front = 1;
	//right = 2; back = 3; left =4; top = 5;
	private String[][][] face = new String[3][3][6];
	
	public Cube(){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				for(int k = 0; k < 6; k++){
					
					if(k==0){
						face[i][j][k] = "RED";
					}else if(k==1){
						face[i][j][k] = "GREEN";
					}else if(k==2){
						face[i][j][k] = "WHITE";
					}else if(k==3){
						face[i][j][k] = "BLUE";
					}else if(k==4){
						face[i][j][k] = "YELLOW";
					}else if(k==5){
						face[i][j][k] = "ORANGE";
					}
					
				}
			}
		}
	}
	
	//Copy constructor
	public Cube(Cube cc){
		Cube co = new Cube();
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				for(int k = 0; k < 6; k++){
					face[i][j][k] = cc.faceColor(i, j, k);
				}
			}
		}
	}
	
	//ORDER => CLOCK( TOP -> LEFT -> BACK -> RIGHT -> FRONT -> BOTTOM )
	//Performs a rotation of the top face, clockwise
	//with respect to the center of the cube
	//(This method is also the debugging one)
	public void topClock(){
		//faceDebugger();
		//This deals with the non-top face pieces moving
		String tempRow[] = {face[0][0][1], face[1][0][1], face[2][0][1]};
		int u = 2;
		for(int i = 0; i < 3; i++){
			//Begins with front face (1), and increments
			//through right (2), back (3), left (4)
			for(int f = 1; f < 5; f++){
				if(f == 1 )
					face[i][0][f] = face[i][0][f+1];
				else if( f == 2){//0,2 -> 2,0; 1,2 -> 1,0; 2,2 -> 0,0
					face[i][0][2] = face[u][2][3];
				}
				else if( f == 3){//2,0 -> 0,2; 1,0 -> 1,2; 0,0 -> 2,2
					face[u][2][3] = face[i][0][4];
				}
				else if( f == 4)
					face[i][0][f] = tempRow[i];
			}
			u--;
		}
		//This function rotates the given face
		faceClock(5);
	}
	
	public void leftClock(){
		//This deals with the non-left face pieces moving
		String tempRow[] = {face[0][0][1], face[0][1][1], face[0][2][1]};
		for(int i = 0; i < 3; i++)
			face[0][i][1] = face[0][i][5];
		for(int i = 0; i < 3; i++)
			face[0][i][5] = face[0][i][3];
		for(int i = 0; i < 3; i++)
			face[0][i][3] = face[0][i][0];
		for(int i = 0; i < 3; i++)
			face[0][i][0] = tempRow[i];
		//This function rotates the given face
		faceClock(4);
	}
	
	public void backClock(){
		//This deals with the non-back face pieces moving
		String tempRow[] = {face[0][0][5], face[1][0][5], face[2][0][5]};
		{
			face[0][0][5] = face[2][0][2];
			face[1][0][5] = face[2][1][2];
			face[2][0][5] = face[2][2][2];
		}{
			face[2][0][2] = face[2][2][0];
			face[2][1][2] = face[1][2][0];
			face[2][2][2] = face[0][2][0];
		}{
			face[2][2][0] = face[0][2][4];
			face[1][2][0] = face[0][1][4];
			face[0][2][0] = face[0][0][4];
		}{
			face[0][2][4] = tempRow[0];
			face[0][1][4] = tempRow[1];
			face[0][0][4] = tempRow[2];
		}
		//This function rotates the given face
		faceClock(3);
	}
	
	public void rightClock(){
		//This deals with the non-right face pieces moving
		String tempRow[] = {face[2][0][1], face[2][1][1], face[2][2][1]};
		for(int i = 0; i < 3; i++)
			face[2][i][1] = face[2][i][0];
		for(int i = 0; i < 3; i++)
			face[2][i][0] = face[2][i][3];
		for(int i = 0; i < 3; i++)
			face[2][i][3] = face[2][i][5];
		for(int i = 0; i < 3; i++)
			face[2][i][5] = tempRow[i];
		//This function rotates the given face
		faceClock(2);
	}
	
	public void frontClock(){
		//This deals with the non-back face pieces moving
		String tempRow[] = {face[0][2][5], face[1][2][5], face[2][2][5]};
		{
			face[0][2][5] = face[2][2][4];
			face[1][2][5] = face[2][1][4];
			face[2][2][5] = face[2][0][4];
		}{
			face[2][2][4] = face[2][0][0];
			face[2][1][4] = face[1][0][0];
			face[2][0][4] = face[0][0][0];
		}{
			face[2][0][0] = face[0][0][2];
			face[1][0][0] = face[0][1][2];
			face[0][0][0] = face[0][2][2];
		}{
			face[0][0][2] = tempRow[0];
			face[0][1][2] = tempRow[1];
			face[0][2][2] = tempRow[2];
		}
		//This function rotates the given face
		faceClock(1);
	}
	
	public void bottomClock(){//NOT DONE
		//This deals with the non-back face pieces moving
		String tempRow[] = {face[0][2][1], face[1][2][1], face[2][2][1]};
		{
			face[0][2][1] = face[0][2][4];
			face[1][2][1] = face[1][2][4];
			face[2][2][1] = face[2][2][4];
		}{
			face[0][2][4] = face[2][0][3];
			face[1][2][4] = face[1][0][3];
			face[2][2][4] = face[0][0][3];
		}{
			face[2][0][3] = face[0][2][2];
			face[1][0][3] = face[1][2][2];
			face[0][0][3] = face[2][2][2];
		}{
			face[0][2][2] = tempRow[0];
			face[1][2][2] = tempRow[1];
			face[2][2][2] = tempRow[2];
		}
		//This function rotates the given face
		faceClock(0);
	}
	
	
	
	private void faceClock(int f){
		//This is a mapping function for the given face
		//CORNER FACES
		String tempFace = face[0][0][f];
		face[0][0][f] = face[0][2][f];
		face[0][2][f] = face[2][2][f];
		face[2][2][f] = face[2][0][f];
		face[2][0][f] = tempFace;
		//MIDDLE FACES
		tempFace = face[1][0][f];
		face[1][0][f] = face[0][1][f];
		face[0][1][f] = face[1][2][f];
		face[1][2][f] = face[2][1][f];
		face[2][1][f] = tempFace;
		//faceDebugger();
		
	}
	
	//Rotates the given face indicated by the number associated with it
	//top-5, left-4, back-3, right-2, front-1, bottom-0
	public void performRotation(int i){
		switch(i){
		case 0: bottomClock();
		break;
		case 1: frontClock();
		break;
		case 2: rightClock();
		break;
		case 3: backClock();
		break;
		case 4: leftClock();
		break;
		case 5: topClock();
		break;
			
		}
	}
	
	private void faceDebugger(){
		for(int k = 0; k < 6; k++){
			System.out.println("FACE k = " + k + ": ");
			for(int i = 0; i < 3; i++){
				for(int j = 0; j < 3; j++){
					System.out.print(j + "-" + i + "~" + face[j][i][k] + ", ");
				}
				System.out.println();
			}
		}
	}
	
	//PUBLIC FUNCTIONS
	//returns face color for current square
	public String faceColor(int x, int y, int f){
		return face[x][y][f];
	}

	//returns face color for current square
	public void setFace(String s, int x, int y, int f){
		face[x][y][f] = s;
	}
	
	//returns number of faces not currently in their
	//correct position
	public int facesIncorrect(Cube tc){
		int errorsFound = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				for(int k = 0; k < 6; k++){
					if(k==0){
						if(!face[i][j][k].equals("RED"))
							errorsFound++;
					}else if(k==1){
						if(!tc.face[i][j][k].equals("GREEN"))
							errorsFound++;
					}else if(k==2){
						if(!tc.face[i][j][k].equals("WHITE"))
							errorsFound++;
					}else if(k==3){
						if(!tc.face[i][j][k].equals("BLUE"))
							errorsFound++;
					}else if(k==4){
						if(!tc.face[i][j][k].equals("YELLOW"))
							errorsFound++;
					}else if(k==5){
						if(!tc.face[i][j][k].equals("ORANGE"))
							errorsFound++;
					}
					
				}
			}
		}
		return errorsFound;
	}
	

	//One for performing a single random move
	public void shuffle(){
		Random rn = new Random();
		int op = -1;
		op = rn.nextInt(5);
		if(op == 0)
			bottomClock();
		else if(op == 1)
			frontClock();
		else if(op == 2)
			rightClock();
		else if(op == 3)
			backClock();
		else if(op == 4)
			leftClock();
		else if(op == 5)
			rightClock();
		
	}
	//One for performing multiple shuffles in one go
	public void shuffle(int s){
		Random rn = new Random();
		int op = -1;
		for(int i = 0; i < s; i++){
			op = rn.nextInt(5);
			if(op == 0)
				bottomClock();
			else if(op == 1)
				frontClock();
			else if(op == 2)
				rightClock();
			else if(op == 3)
				backClock();
			else if(op == 4)
				leftClock();
			else if(op == 5)
				rightClock();
		}
	}
	
	public void sysState(Cube c){
		for(int i = 0; i < 6; i++){
			switch (i){
			case 0 : System.out.println("Bottom:");
			break;
			case 1 : System.out.println("Front:");
			break;
			case 2 : System.out.println("Right:");
			break;
			case 3 : System.out.println("Back (weird):");
			break;
			case 4 : System.out.println("Left:");
			break;
			case 5 : System.out.println("Top:");
			break;
			}
			for(int j = 0; j < 3; j++){
				for(int k = 0; k < 3; k++){
					System.out.print( c.faceColor(j, k, i).substring(0, 1) );
				}
				System.out.println();
			}
		}
	}
	
	//HEURISTIC CHECKING FUNCTIONS~~~~~~~~~
	//CHECKS BOTTOM CROSS
	public boolean checkH1(){
		boolean cross = true;
		//Checks bottom faces
		String bottomFace = faceColor(1, 1, 0);
		if( !bottomFace.equals(faceColor(1, 0, 0)) || 
				!bottomFace.equals(faceColor(2, 1, 0)) || 
				!bottomFace.equals(faceColor(0, 2, 0)) || 
				!bottomFace.equals(faceColor(1, 2, 0)) )
			cross = false;
		//Checks the side of the cross
		String frontFace = faceColor(1, 1, 1);
		String rightFace = faceColor(1, 1, 2);
		String backFace = faceColor(1, 1, 3);
		String leftFace = faceColor(1, 1, 4);
		if( !frontFace.equals(faceColor(1, 2, 1)) || 
				!rightFace.equals(faceColor(1, 2, 2)) || 
				!backFace.equals(faceColor(1, 0, 3)) || 
				!leftFace.equals(faceColor(1, 2, 4)) )
			cross = false;
		return cross;
	}
	
	//This checks for the entire bottom layer
	public boolean checkH2(){
		boolean bLayer = true;
		if(checkH1() == false)
			return false;
		//Checks the bottom face
		String bottomFace = faceColor(1, 1, 0);
		if( !bottomFace.equals(faceColor(0, 0, 0)) || 
				!bottomFace.equals(faceColor(2, 0, 0)) || 
				!bottomFace.equals(faceColor(2, 2, 0)) || 
				!bottomFace.equals(faceColor(0, 2, 0)) )
			bLayer = false;
		//Checks the sides of the front, right, back, and left
		String frontFace = faceColor(1, 1, 1);
		String rightFace = faceColor(1, 1, 2);
		String backFace = faceColor(1, 1, 3);
		String leftFace = faceColor(1, 1, 4);
		if( !frontFace.equals(faceColor(0, 2, 1)) ||
				!frontFace.equals(faceColor(2, 2, 1)) ||
				!rightFace.equals(faceColor(0, 2, 2)) ||
				!rightFace.equals(faceColor(2, 2, 2)) ||
				!backFace.equals(faceColor(0, 0, 3)) ||
				!backFace.equals(faceColor(2, 0, 3)) ||
				!leftFace.equals(faceColor(0, 2, 4)) ||
				!leftFace.equals(faceColor(2, 2, 4)) )
			bLayer = false;
		System.out.println("The bottom LAYER is currently: " + bLayer);
		return bLayer;
	}
	
	//This checks for the entire second layer
	public boolean checkH3(){
		boolean bLayer = true;
		if(checkH2() == false)
			return false;
		//Checks the sides of the front, right, back, and left
		String frontFace = faceColor(1, 1, 1);
		String rightFace = faceColor(1, 1, 2);
		String backFace = faceColor(1, 1, 3);
		String leftFace = faceColor(1, 1, 4);
		if( !frontFace.equals(faceColor(0, 1, 1)) ||
				!frontFace.equals(faceColor(2, 1, 1)) ||
				!rightFace.equals(faceColor(0, 1, 2)) ||
				!rightFace.equals(faceColor(2, 1, 2)) ||
				!backFace.equals(faceColor(0, 1, 3)) ||
				!backFace.equals(faceColor(2, 1, 3)) ||
				!leftFace.equals(faceColor(0, 1, 4)) ||
				!leftFace.equals(faceColor(2, 1, 4)) )
			bLayer = false;
		System.out.println("The SECOND LAYER is currently: " + bLayer);
		return bLayer;
	}
	
	//This checks for the top layer for cross
	public boolean checkH4(){
		boolean bLayer = true;
		if(checkH3() == false)
			return false;
		//Checks the top face
		String topFace = faceColor(1, 1, 5);
		if( !topFace.equals(faceColor(0, 0, 5)) || 
				!topFace.equals(faceColor(2, 0, 5)) || 
				!topFace.equals(faceColor(2, 2, 5)) || 
				!topFace.equals(faceColor(0, 2, 5)) )
			bLayer = false;
		//Checks the sides of the front, right, back, and left
		String frontFace = faceColor(1, 1, 1);
		String rightFace = faceColor(1, 1, 2);
		String backFace = faceColor(1, 1, 3);
		String leftFace = faceColor(1, 1, 4);
		if( !frontFace.equals(faceColor(1, 0, 1)) || 
				!rightFace.equals(faceColor(1, 0, 2)) || 
				!backFace.equals(faceColor(1, 2, 3)) || 
				!leftFace.equals(faceColor(1, 0, 4)) )
			bLayer = false;
		System.out.println("The SECOND LAYER is currently: " + bLayer);
		return bLayer;
	}
}
