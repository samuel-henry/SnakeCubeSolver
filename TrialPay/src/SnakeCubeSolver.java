public class SnakeCubeSolver {
	//store current state of filled path across recursive calls
	private static boolean[][][] filledPosn;
	
	//store individual cubelet values (0 or 1)
	private static int[] cubelets;
	
	//store length of each side of cube
	private static int sideLen;
	
	public static void main(String[] args) {
		//make sure we got four inputs (x, y, z, cubelets)
		if (args.length != 4) {
			System.out.println("Incorrect number of inputs");
			return;
		} 
		
		//make sure the dimensions are valid
		boolean validDims = validateDimensions(args);
		if (!validDims) {
			return;
		}
		
		//convert cubelet String to array of ints
		cubelets = convertToInts(args[3]);
		
		//if cubelets is still null, the String does not represent a valid snake. user alerted in prev method call.
		if (cubelets == null) {
			return;
		}
		
		//get the side length based on the size of each array in cubelets
		sideLen = (int) Math.pow(cubelets.length, 1.0/3);
		
		//initialize array of whether each point in the cube is currently filled
		filledPosn = new boolean[sideLen][sideLen][sideLen];
		
		//solve the cube. 
		//pass in -1, 0, 0 for starting coordinates 
		//and 0 for the current index in cubelets that we're considering
		System.out.println(solveSnakeCube(-1, 0, 0, 0));
		
	}

	private static int solveSnakeCube(int prevX, int prevY, int prevZ, int currIdx) {
		//if we are on the last cubelet in cubelets, the cube has been solved. return 1.
		if (currIdx == cubelets.length) {
			return 1;
		}
		
		//store the current fill state of the cube in a temporary variable in case a traversed edge leads to a dead end
		boolean[][][] backupFilledPosn = filledPosn.clone();
		
		//initialize the length of the current group of cubelets to 0
		int groupLen = 0;
		
		//add to the group length while we're not at the end of the cubelets and we're encountering 0's
		while (currIdx + groupLen < cubelets.length && cubelets[currIdx + groupLen] == 0) {
			groupLen++;
		}
		
		//add once more if we're not on the last cubelet because we've hit the next 1
		if (currIdx+groupLen < cubelets.length) {
			groupLen++;
		}
		
		//see if there is space on x axis
		//check positive move on x
		if (prevX + groupLen < sideLen) {
			//fill from prevX + 1 to prevX + groupLen
			for (int i = prevX + 1; i <= prevX + groupLen; i++) {
				if (filledPosn[i][prevY][prevZ]) {
					//revert to state of filledPosn when we entered this recursive call
					filledPosn = backupFilledPosn.clone();;
					break;
				} else {
					filledPosn[i][prevY][prevZ] = true;
				}
			}
			
			//recurse if change
			if (filledPosn != backupFilledPosn) {
				return solveSnakeCube(prevX + groupLen, prevY, prevZ, currIdx + groupLen);
			}
		}
		
		//check negative move on x
		if (prevX - groupLen >= 0) {
			//fill from prevX - 1 to prevX - groupLen
			for (int i = prevX - 1; i >= prevX - groupLen; i++) {
				if (filledPosn[i][prevY][prevZ]) {
					//revert to state of filledPosn when we entered this recursive call
					filledPosn = backupFilledPosn.clone();;
					break;
				} else {
					filledPosn[i][prevY][prevZ] = true;
				}
			}
			
			//recurse if change
			if (filledPosn != backupFilledPosn) {
				return solveSnakeCube(prevX - groupLen, prevY, prevZ, currIdx + groupLen);
			}
		}
		
		//see if there is space on y axis
		//check positive move on y
		if (prevY + groupLen < sideLen) {
			//fill from prevX + 1 to prevX + groupLen
			for (int i = prevY + 1; i <= prevY + groupLen; i++) {
				if (filledPosn[prevX][i][prevZ]) {
					//revert to state of filledPosn when we entered this recursive call
					filledPosn = backupFilledPosn.clone();;
					break;
				} else {
					filledPosn[prevX][i][prevZ] = true;
				}
			}
			
			//recurse if change
			if (filledPosn != backupFilledPosn) {
				return solveSnakeCube(prevX, prevY + groupLen, prevZ, currIdx + groupLen);
			}
		}
		
		//check negative move on y
		if (prevY - groupLen >= 0) {
			//fill from prevX - 1 to prevX - groupLen
			for (int i = prevY - 1; i >= prevY - groupLen; i++) {
				if (filledPosn[prevX][i][prevZ]) {
					//revert to state of filledPosn when we entered this recursive call
					filledPosn = backupFilledPosn.clone();;
					break;
				} else {
					filledPosn[prevX][i][prevZ] = true;
				}
			}
			
			//recurse if change
			if (filledPosn != backupFilledPosn) {
				return solveSnakeCube(prevX, prevY - groupLen, prevZ, currIdx + groupLen);
			}
		}
		
		//see if there is space on z axis
		//check positive move on z
		if (prevZ + groupLen < sideLen) {
			//fill from prevX + 1 to prevX + groupLen
			for (int i = prevZ + 1; i <= prevZ + groupLen; i++) {
				if (filledPosn[prevX][prevY][i]) {
					//revert to state of filledPosn when we entered this recursive call
					filledPosn = backupFilledPosn.clone();;
					break;
				} else {
					filledPosn[prevX][prevY][i] = true;
				}
			}
			
			//recurse if change
			if (filledPosn != backupFilledPosn) {
				return solveSnakeCube(prevX, prevY, prevZ + groupLen, currIdx + groupLen);
			}
		}
		
		//check negative move on z
		if (prevZ - groupLen >= 0) {
			//fill from prevX - 1 to prevX - groupLen
			for (int i = prevZ - 1; i >= prevZ - groupLen; i++) {
				if (filledPosn[prevX][prevY][i]) {
					//revert to state of filledPosn when we entered this recursive call
					filledPosn = backupFilledPosn.clone();;
					break;
				} else {
					filledPosn[prevX][prevY][i] = true;
				}
			}
			
			//recurse if change
			if (filledPosn != backupFilledPosn) {
				return solveSnakeCube(prevX, prevY, prevZ - groupLen, currIdx + groupLen);
			}
		}
		
		//weren't able to move from this position, return 0
		return 0;
	}
	
	//convert the String cubelet input to an array of ints
	private static int[] convertToInts(String stringCubelets) {
		int[] cubelets = new int[stringCubelets.length()];
		
		//try converting each char to an int
		try {
			for (int i=0; i<stringCubelets.length(); i++) {
				cubelets[i] = Integer.valueOf(stringCubelets.charAt(i)) - 48;
				
				//if it's an int but it's not 0 or 1, the input cubelet String is invalid
				if (cubelets[i] < 0 || cubelets[i] > 1) {
					System.out.println("Cubelets can only be 0 or 1");
					return null;				
				}
			}
		} catch (NumberFormatException ex) {
			//if this char is not numeric, the input cubelet String is invalid
			System.out.println("Invalid cubelet input. Must all be integers");
			return null;
		}
		
		return cubelets;
	}
	
	//check that the input dimensions are valid
	private static boolean validateDimensions(String[] args) {
		int sideLen;
		try {
			//make sure the x, y, and z inputs are all the same number and >= 1
			sideLen = Integer.valueOf(args[0]);
			sideLen += Integer.valueOf(args[1]);
			sideLen -= Integer.valueOf(args[2]);
			
			if (sideLen != Integer.valueOf(args[0])) {
				System.out.println("Dimensions do not form a cube");
				return false;
			} else if (sideLen < 1) {
				System.out.println("Cube side length must be >= 1");
				return false;
			}
		} catch (NumberFormatException ex) {
			//one of the dimensions is not an integer so the dimensions are invalid
			System.out.println("Dimensions not all integers");
			return false;
		}
		
		//make sure the number of cubelets is equal to the spaces in the cube
		if (args[3].length() != Math.pow(sideLen, 3)) {
			System.out.println("Incorrect number of cubelets for the given dimensions");
			return false;
		}
		
		return true;
	}
	
}
