import java.util.ArrayList;
import java.util.Random;

public class rubiksAgent {
	//HEURISTIC 1 is the bottom cross (remember:
	// to match with middle cubes with the other face centers)
	//HEURISTIC 2 is the bottom layer (remember:
	// to match with middle cubes with the other face centers)
	//HEURISTIC 3 is the second + bottom layer solved
	//HEURISTIC 4 is top cross + second/first layer
	//GOAL IS CHECKED THROUGH: cube.facesIncorrect() == 0
	//GOAL STATE: It is all solved
	Cube cube;
	
	ArrayList<int[]> exploredPaths = new ArrayList<int[]>();
	int[] tem =  {1};
	int solutionToGoal[];
	
	//Constructor
	public rubiksAgent(Cube c){
		System.out.println(c + "~~CONSTRUCTOR~~" + c.faceColor(0, 0, 1));
		cube = c;
	}

	//UNTESTED - NOT DONE
	//Returns the solution path
	public int[] getSolution(Cube c){
		System.out.println("MEMEMEME");
		Cube tempCube = new Cube(c);
		System.out.println(c);
		System.out.println(tempCube);
		exploredPaths.add(tem);
		//These are the variable containing whether the given
		//heuristic has been met.
		boolean h[] = {false, false, false, false, false};
		int currentHeuristic = 0;
		for(int hi = 0; hi < 5; hi++){
			while(hi < 5 && !h[hi]){
				
				ArrayList<int[]> fringe = expandFrontier(exploredPaths);
				for(int o = 0; o < fringe.size(); o++){
					for(int n = 0; n < fringe.get(o).length; n++){
						System.out.print(fringe.get(o)[n] + "--");
					}
					System.out.println();
				}
				int heuristicAchievedIndex = checkFrontier(fringe, hi+1);
				System.out.println(heuristicAchievedIndex + " * * * " + hi + "**" + fringe);
				if(heuristicAchievedIndex != -1){
					int[] goodBranch = exploredPaths.get(0);
					exploredPaths = new ArrayList<int[]>();
					exploredPaths.add(goodBranch);
					h[hi] = true;
					hi++;
				}else
					addFrontier(fringe, exploredPaths);
				
			}//DODODODOODOODDO
			solutionToGoal = new int[exploredPaths.size()];
			for(int q = 0; q < exploredPaths.size(); q++){
				System.out.print( (int) exploredPaths.get(0)[q] + "~");
				solutionToGoal[q] = (int) exploredPaths.get(0)[q];
			}
			//solutionToGoal
		}
		return solutionToGoal;
	}
	
	//Performs a randomly selected move
	//Returns: The int identifier for the given
	//Operation bottom=0, front=1, right=2,
	//back=3, left=4, top=5
	public int performRandomRotation(){
		Random rn = new Random();
		int op = rn.nextInt(5);
		
		switch(op){
		case 0: cube.bottomClock();
		break;
		case 1: cube.frontClock();
		break;
		case 2: cube.rightClock();
		break;
		case 3: cube.backClock();
		break;
		case 4: cube.leftClock();
		break;
		case 5: cube.topClock();
		break;
		}
		return op;
	}
	
	///UNTESTED-DONE
	//Returns six arrays (in an ArrayList) that are derived from taking the 
	//array at index 0 and expanding that into the six possible moves the cube can take.
	public ArrayList<int[]> expandFrontier(ArrayList<int[]> s){
		Cube tempCube = new Cube(cube);
		ArrayList<int[]> fringe = new ArrayList<int[]>();
		
		//This section deals with putting the array at 0 into a normal array
		//Also selects a random set of moves in the first-fifth of the Total searched moves
		Random rn = new Random();
		int selection;
		int arrayAtSelect[];
		if(s.size() < 2){
			System.out.println(s.size() < 2);
			selection = 0;
			arrayAtSelect = new int[6];
			for(int u = 0; u < 6; u++)
				arrayAtSelect[u] = u;
		}else{
			selection = rn.nextInt(s.size()/5);
			arrayAtSelect = new int[s.get(selection).length];
			for(int k = 0; k < s.get(selection).length; k++){
				arrayAtSelect[k] = s.get(selection)[k];
			}
		}
		System.out.println(s.size() + "~");
		System.out.println("Expand frontier s.get(selection).length : " + s.get(selection).length);

		int lastIndex = s.get(selection).length - 1;
		arrayAtSelect[lastIndex] = -1;
		
		//Goes through each of the 6 int[] arrays
		//and adds them to the fringe
		for(int i = 0; i < 6; i++){
			arrayAtSelect[s.get(selection).length - 1] = i;
			fringe.add(arrayAtSelect);
		}
		return fringe;
	}
	
	//NOT DONE
	//Once a heuristic is reached, adds that to solution path
	public int performActions(int[] ops){
		return 0;
	}
	
	///UNTESTED-DONE
	//Returns:	IF isMet == false -> -1
	//			ELSE isMet == true -> the index
	//		that the given heuristic is first met
	//in an Arraylist of int[]s
	//for the given heuristic (h = 5 is the goal heuristic)
	public int checkFrontier(ArrayList<int[]> s, int h){
		Cube tempCube = new Cube(cube);
		int foundAt = -1;
		boolean isMet = false;
			
		if(h == 1){
			for(int i = 0; i < s.size(); i++){
				for(int j = 0; j < s.get(i).length; j++){
					
					int op = s.get(i)[j];
					//System.out.println("tempCube" + tempCube + "; op: " + op + "; s.get(i)[j]" + ((int)(s.get(i)[j])));
					if(op == 0)
						tempCube.bottomClock();
					else if(op == 1)
						tempCube.frontClock();
					else if(op == 2)
						tempCube.rightClock();
					else if(op == 3)
						tempCube.backClock();
					else if(op == 4)
						tempCube.leftClock();
					else if(op == 5)
						tempCube.topClock();
				}
				//Once these are done, check cube
				if(tempCube.checkH1()){
					System.out.println("H1 IS FOUND~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					isMet = tempCube.checkH1();
					foundAt = i;
				}
			}
		}else if(h == 2){
			for(int i = 0; i < s.size(); i++){
				for(int j = 0; j < s.get(i).length; j++){
					int op = s.get(i)[j];
					if(op == 0)
						tempCube.bottomClock();
					else if(op == 1)
						tempCube.frontClock();
					else if(op == 2)
						tempCube.rightClock();
					else if(op == 3)
						tempCube.backClock();
					else if(op == 4)
						tempCube.leftClock();
					else if(op == 5)
						tempCube.topClock();
				}
				//Once these are done, check cube
				if(tempCube.checkH2()){
					isMet = tempCube.checkH2();
					foundAt = i;
				}
			}
		}else if(h == 3){
			for(int i = 0; i < s.size(); i++){
				for(int j = 0; j < s.get(i).length; j++){
					int op = s.get(i)[j];
					if(op == 0)
						tempCube.bottomClock();
					else if(op == 1)
						tempCube.frontClock();
					else if(op == 2)
						tempCube.rightClock();
					else if(op == 3)
						tempCube.backClock();
					else if(op == 4)
						tempCube.leftClock();
					else if(op == 5)
						tempCube.topClock();
				}
				//Once these are done, check cube
				if(tempCube.checkH3()){
					isMet = tempCube.checkH3();
					foundAt = i;
				}
			}
		}else if(h == 4){
			for(int i = 0; i < s.size(); i++){
				for(int j = 0; j < s.get(i).length; j++){
					int op = s.get(i)[j];
					if(op == 0)
						tempCube.bottomClock();
					else if(op == 1)
						tempCube.frontClock();
					else if(op == 2)
						tempCube.rightClock();
					else if(op == 3)
						tempCube.backClock();
					else if(op == 4)
						tempCube.leftClock();
					else if(op == 5)
						tempCube.topClock();
				}
				//Once these are done, check cube
				if(tempCube.checkH4()){
					isMet = tempCube.checkH4();
					foundAt = i;
				}
			}
		}else if(h == 5){
			for(int i = 0; i < s.size(); i++){
				for(int j = 0; j < s.get(i).length; j++){
					int op = s.get(i)[j];
					if(op == 0)
						tempCube.bottomClock();
					else if(op == 1)
						tempCube.frontClock();
					else if(op == 2)
						tempCube.rightClock();
					else if(op == 3)
						tempCube.backClock();
					else if(op == 4)
						tempCube.leftClock();
					else if(op == 5)
						tempCube.topClock();
				}
				//Once these are done, check cube
				if(tempCube.facesIncorrect(tempCube) == 0){
					isMet = true;
					foundAt = i;
				}
			}
		}
		
		return foundAt;
	}
	
	///UNTESTED-DONE
	//Sorts through the current frontier and puts the into the
	//sorted array of exploredPaths ArrayList<int[]>
	public ArrayList<int[]> addFrontier(ArrayList<int[]> f, ArrayList<int[]> old){
		ArrayList<int[]> nw = (ArrayList<int[]>) old.clone();
		int fIndex = 0;
		for(int i = 0; i < nw.size(); i++){
			//System.out.println(fIndex + " < " + f.size() + "&& ");
			//System.out.println(getFacesIncorrect(f.get(fIndex)) + " <= " + getFacesIncorrect(old.get(i - fIndex)) + ")");
			if(fIndex < f.size() &&
					getFacesIncorrect(f.get(fIndex)) <= getFacesIncorrect(old.get(i - fIndex))){
				nw.add(i, f.get(fIndex));
				fIndex++;
			}else if(fIndex < f.size() &&
					getFacesIncorrect(f.get(fIndex)) > getFacesIncorrect(old.get(i - fIndex))){
				nw.add(i, old.get(i - fIndex));
			}else{
				break;
			}
			System.out.println( "getFacesIncorrect(nw.get(i)): " + getFacesIncorrect(nw.get(i)) );
		}
		return nw;
	}
	
	//NOT TESTED-DONE
	//For a given int[] array of operations, tells you
	//the number of facesIncorrect
	private int getFacesIncorrect(int[] ops){
		//This is the temporary cube that has not been modified
		Cube tempCube = new Cube(cube);
		for(int i = 0; i < ops.length; i++){
			switch(i){
			case 0: tempCube.bottomClock();
			break;
			case 1: tempCube.frontClock();
			break;
			case 2: tempCube.rightClock();
			break;
			case 3: tempCube.backClock();
			break;
			case 4: tempCube.leftClock();
			break;
			case 5: tempCube.topClock();
			break;
			}
		}
		int fi = tempCube.facesIncorrect(cube);
		return fi;
	}
}
