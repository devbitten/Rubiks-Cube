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
	int LOWESTGFC = 9999;
	
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
				
				ArrayList<int[]> fringe = new ArrayList<int[]>();
				fringe = expandFrontier(exploredPaths);
				//Prints out the fringe
				for(int o = 0; o < fringe.size(); o++){
					for(int n = 0; n < fringe.get(o).length; n++){
						System.out.print(fringe.get(o)[n] + "--");
					}
					System.out.println("<-- fringe");
				}
				
				int heuristicAchievedIndex = checkFrontier(fringe, hi+1);
				
				System.out.println(heuristicAchievedIndex + " * * * " + hi + "**************" + LOWESTGFC);
				if(heuristicAchievedIndex != -1){
					int[] goodBranch = fringe.get(heuristicAchievedIndex);
					exploredPaths = new ArrayList<int[]>();
					exploredPaths.add(goodBranch);
					h[hi] = true;
					hi++;
				}else
					exploredPaths = addFrontier(fringe, exploredPaths, hi);

				//* TEST FOR PERFORMING THE SOLUTION :3
				System.out.println(" TESTING FOR PERFORMING THE SOLUTION :3");
				if(heuristicAchievedIndex != -1){

					System.out.println("exploredPaths.get(0).length === " + exploredPaths.get(0).length);
					solutionToGoal = new int[exploredPaths.get(0).length];
					for(int q = 0; q < exploredPaths.get(0).length; q++){
						System.out.print( (int) exploredPaths.get(0)[q] + "~");
						solutionToGoal[q] = (int) exploredPaths.get(0)[q];
					}
					System.out.println("Oh yeah boi, you've made it to dumb agent status");
					return solutionToGoal;
				}
				//*/
				for(int o = 0; o < fringe.size(); o++){
					for(int n = 0; n < exploredPaths.get(o).length; n++){
						System.out.print(exploredPaths.get(o)[n] + "~~");
					}
					System.out.println("<== exploredPaths");
					//******************************************************************IT BREEASKSS HERE FYI
				}
				/* FOR TESTING
				hi++;
				if(hi == 4){
					System.exit(0);
				}
				//*/
			}//LOOPS ON NEW HEURISTICS
			solutionToGoal = new int[exploredPaths.size()];
			for(int q = 0; q < exploredPaths.size(); q++){
				System.out.print( (int) exploredPaths.get(0)[q] + "~");
				solutionToGoal[q] = (int) exploredPaths.get(0)[q];
			}
			//solutionToGoal
		}
		System.out.print("IT GETS TO VICTORY");
		System.exit(0);
		return solutionToGoal;
	}
	
	///UNTESTED-DONE
	//Returns six arrays (in an ArrayList) that are derived from taking the 
	//array at index 0 and expanding that into the six possible moves the cube can take.
	public ArrayList<int[]> expandFrontier(ArrayList<int[]> s){
		Cube tempCube = new Cube(cube);
		ArrayList<int[]> fringe = new ArrayList<int[]>(6);
		
		//This section deals with putting the array at 0 into a normal array
		//Also selects a random set of moves in the first-fifth of the Total searched moves
		Random rn = new Random();
		int selection;
		int flip;
		int arrayAtSelect[];
		if(s.size() < 16){
			System.out.println(s.size() < 2);
			selection = 0;
			arrayAtSelect = new int[6];
			for(int u = 0; u < 6; u++)
				arrayAtSelect[u] = rn.nextInt(6);
		}else{
			//The flip makes it so that it has a fifty-fifty chance
			//Of landing in the first-sixth, and the last-five-sixths
			flip = rn.nextInt(7);
			if(flip == 0)
				selection = rn.nextInt(16);
			else
				selection = rn.nextInt(2*s.size()/4 + 4);
			arrayAtSelect = new int[s.get(selection).length];
			for(int k = 0; k < s.get(selection).length; k++){
				arrayAtSelect[k] = s.get(selection)[k];
			}
		}
		
		//At this point arrayAtSelect[] should be the path chosen
		//to explore the fringe of
		/*This for loop tests that theory
		for(int i = 0; i < arrayAtSelect.length; i++)
			System.out.print( arrayAtSelect[i] );
		System.out.println("<<< arrayAtSelect[]");
		*/
		System.out.println("s.size() ~ " + s.size());
		System.out.println("Expand frontier s.get(" + selection + ").length : " + s.get(selection).length);

		//int lastIndex = s.get(selection).length - 1;
		//arrayAtSelect[lastIndex] = -1;
		
		//Goes through each of the 6 int[] arrays
		//and adds them to the fringe
		/* OLD CODE BLOCK A
		int[] tempSelection = new int[arrayAtSelect.length+1];
		for(int j = 0; j < arrayAtSelect.length; j++){
			tempSelection[j] = arrayAtSelect[j];
		}
		*/
		for(int i = 0; i < 6; i++){
			fringe.add(new int[1]);

			int[] tArray = {i};
			int[] array1and2 = new int[arrayAtSelect.length + tArray.length];
			System.arraycopy(arrayAtSelect, 0, array1and2, 0, arrayAtSelect.length);
			System.arraycopy(tArray, 0, array1and2, arrayAtSelect.length, tArray.length);
			
			/*// OLD CODE BLOCK A
			tempSelection[arrayAtSelect.length] = i;
			
			for(int ii = 0; ii < tempSelection.length; ii++)
				System.out.print( tempSelection[ii] );
			System.out.println("<<< tempSelection[" + i + "]");
			///
			//arrayAtSelect[s.get(selection).length - 1] = i;
			*/
			fringe.set(i, array1and2);
			
		}
		/*
		for(int i = 0; i < fringe.size(); i++){
			for(int ii = 0; ii < tempSelection.length; ii++)
				System.out.print( fringe.get(i)[ii] );
			System.out.println("<<< fringe.get(" + i + ")");
		}
		//*/
		return fringe;
	}
	
	///UNTESTED-DONE
	//Returns:	IF isMet == false -> -1
	//			ELSE isMet == true -> the index
	//		that the given heuristic is first met
	//		in the ArrayList<int[]> s (aka fringe) for the 
	//		given heuristic (h = 5 is the goal heuristic)
	public int checkFrontier(ArrayList<int[]> s, int h){
		Cube tempCube = new Cube(cube);
		int foundAt = -1;
		boolean isMet = false;
		
		if(h == 1){
			for(int i = 0; i < s.size(); i++){
				for(int j = 0; j < s.get(i).length; j++){
					//if(i == 0 && j == 0)
						//tempCube.sysState(tempCube);
					int op = s.get(i)[j];
					tempCube.performRotation(op);
					
				}
				//Once these are done, check cube
				if(tempCube.facesIncorrect(tempCube) < LOWESTGFC)
					LOWESTGFC = tempCube.facesIncorrect(tempCube);
				if(tempCube.checkH1()){
					System.out.println("H1 IS FOUND~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					//System.out.println("vvv--- SOLVED tempCube ---vvv");
					tempCube.sysState(tempCube);
					System.out.println( tempCube.checkHeuristic(1) );
					isMet = tempCube.checkH1();
					foundAt = i;
					for(int q = 0 ; q < s.get(foundAt).length; q++ ){
						System.out.print(s.get(foundAt)[q] + "+");
					}
					System.out.println();
				}
			}
		}else if(h == 2){
			for(int i = 0; i < s.size(); i++){
				for(int j = 0; j < s.get(i).length; j++){
					
					int op = s.get(i)[j];
					tempCube.performRotation(op);
					
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
					tempCube.performRotation(op);
					
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
					tempCube.performRotation(op);
					
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
					tempCube.performRotation(op);
					
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
	//SORTS BY heuristic indicated by hi
	public ArrayList<int[]> addFrontier(ArrayList<int[]> f, ArrayList<int[]> old, int hi){
		ArrayList<int[]> nw = (ArrayList<int[]>) old.clone();
		int fIndex = 0;
		int aIndex = 0;
		for(int i = 0; i < nw.size(); i++){
			//System.out.println(fIndex + " < " + f.size() + "&& ");
			//System.out.println(getFacesIncorrect(f.get(fIndex)) + " <= " + getFacesIncorrect(old.get(i - fIndex)) + ")");
			if(fIndex < f.size()){
				if(i - fIndex <= old.size() && 
						getFacesIncorrect(f.get(fIndex)) <= getFacesIncorrect(old.get(i - aIndex))){
					
					//STILL GETTING ERRORS HERE, IDK WHAT TO DO :/
					System.out.print("It chose ##if## ");
					//maybe change add to set??
					nw.add(i, f.get(fIndex));
					fIndex++;
					aIndex++;
				}else if(i - fIndex <= old.size()){
					nw.add(i, old.get(i - fIndex));
					System.out.print("It chose ##else if## )");
				}else{
					nw.add(i, f.get(fIndex));
					System.out.print("It chose ##else## ");
					aIndex++;
				}
			
			}else{
				break;
			}
			System.out.println( "getFacesIncorrect(nw.get(i)): " + getFacesIncorrect(nw.get(i)) );
		}
		return nw;
	}
	
	//NOT DONE
	//Once a heuristic is reached, adds that to solution path
	public int performActions(int[] ops){
		return 0;
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
		int fi = tempCube.facesIncorrect(tempCube);
		return fi;
	}
}
