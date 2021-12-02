/*************************************
Program name: ChangeOneLetterGame
Author: Daniel Zhang
Date: November 29, 2021
Purpose: A game played by 2 players, where a word is changed 1 letter at a time with player commands until
it is changed into another word.
*************************************/
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ChangeOneLetterGameV2 {

	public static String [] dictionary = getFileContents("dictionary.txt"); //store all 4 letter words
	
	public static void main(String[] args) {
		int maxTurns; //max amount of turns
		int turnCounter = 0; //count turns played
		int playerTurn = 0; //store which player's turn it is
		String currentWord; //store the word being changed
		String goalWord; //store word thats being changed into
		String inputCommand; //store players command inputs
		boolean winCheck = false; //store if the game has been won
		
		//output instructions
		outputInstructions();
		
		//get max turns
		maxTurns = maxTurns();
		
		//get start word
		currentWord = inputWords("start");
		
		//get goal word
		goalWord = inputWords("goal");
		
		System.out.println("-------------------");
		System.out.println("current word: " + currentWord + " \ngoal word: " + goalWord + "\nmaximum turns: " + maxTurns + " per player");
		System.out.println("-------------------");
		
		for (int i = 0; i < maxTurns * 2; i++) {
			
			//count turns
			turnCounter ++;

			playerTurn = getPlayer(turnCounter);
			
			do {
				//get command
				inputCommand = inputCommands(playerTurn);
				
				if (updateWord(currentWord, inputCommand).equals("invalid command")) {
					errorMessage(1);
					continue;
				} //if
				
				currentWord = updateWord(currentWord, inputCommand);
				
				System.out.println("-------------------");
				System.out.println("player " + playerTurn + "'s turn" + "\ncurrent word: " + currentWord + " \ngoal word: " + goalWord + "\nattempts: " + turnCounter + "/" + maxTurns);
				System.out.println("-------------------");
				break;
				
			} while (true);
				
				//check if you won
				if (currentWord.equals(goalWord)) {
					winCheck = true;
					break;
				}
		} //turnLoop
		
		if (winCheck) {
			System.out.println("player " + playerTurn + " wins!");
		} else {
			System.out.println("no one won this game, no more turns left");
		}
		
	} //main
	
	public static void outputInstructions () {
		System.out.println("Instructions: \n "
				+ "- player 1 will pick a start word \n "
				+ "- player 2 will pick a goal word \n "
				+ "- player 1 and 2 will alternate turns changing 1 letter at a time \n "
				+ "- the letters are changed using commands in this format: (index of old letter) (new letter)\n "
				+ "- the game is won when the start word is changed into the goal word");
		System.out.println("-----------------------------------------------------------");
	} //ouputInstructions
	
	public static int maxTurns () {
		String input;
		do {
			try {
				input = JOptionPane.showInputDialog("how many turns per player?");
				if (input == null) {
					System.exit(0);
				}
				return Integer.parseInt(input);
			} catch (Exception e) {
				errorMessage(0);
				continue;
			}	
		} while (true);
	} //maxTurns
	
	public static String inputWords (String wordType) {
		String input;
		
		do {
			input = JOptionPane.showInputDialog("enter " + wordType +  " word");
			if (input == null) {
				System.exit(0);
			} else if (checkWord(input)) {
				return input.toLowerCase();
			}
			
			errorMessage(1);
			
		} while (true);
	} //inputWords
	
	public static int getPlayer (int turnCounter) {
		//get player
		switch (turnCounter % 2) {
			case 1:
				return 1;
			default:
				return 2;
		}
	}
	
	public static String inputCommands (int playerTurn) {
		String input;
		
		do {
			input = JOptionPane.showInputDialog("p" + playerTurn + " input command");
			
			if (input == null) {
				System.exit(0);
			}
			
			input = input.toLowerCase();
			try {
				if (input.charAt(0) >= 48 && input.charAt(0) <= 51 && input.charAt(2) >= 97 && input.charAt(2) <= 122) {
					return input;
				}
			} catch (Exception e) {
				errorMessage(2);
			}
			errorMessage(2);
		} while (true);
	} //inputCommands
	
	public static String updateWord (String currentWord, String inputCommand) {
		char [] tempWord = new char [4];
		
		tempWord = currentWord.toCharArray();

		tempWord [inputCommand.charAt(0) - 48] = inputCommand.charAt(2);

		if (checkWord(new String (tempWord))) {
			return new String (tempWord);
		}
		return "invalid command";
	} //updateWord
	
	public static boolean checkWord (String currentWord) {
		for (int i = 0; i < dictionary.length; i++) {
			if (currentWord.equals(dictionary [i])) {
				return true;
			}
		}
		return false;
	} //checkWord

	public static void errorMessage (int errorType) {
		switch (errorType) {
		case 0:
			System.out.println("error: must enter an integer");
			break;
		case 1:
			System.out.println("error: must enter a 4 letter word in the english alphabet");
			break;
		case 2:
			System.out.println("error: invalid command, must be in this format (index) (new char)");
			break;
		default:
			System.out.println("error: dictionary not valid");
		}
	} //errorMessage
	
    public static String [] getFileContents (String fileName){

        String [] contents = null;
        int length = 0;
        try {

           // input
           String folderName = "/subFolder/"; // if the file is contained in the same folder as the .class file, make this equal to the empty string
           String resource = fileName;

			// this is the path within the jar file
			InputStream input = ChangeOneLetterGameV2.class.getResourceAsStream(folderName + resource);
			if (input == null) {
				// this is how we load file within editor (eg eclipse)
				input = ChangeOneLetterGameV2.class.getClassLoader().getResourceAsStream(resource);
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(input));	
           
           
           
           in.mark(Short.MAX_VALUE);  // see api

           // count number of lines in file
           while (in.readLine() != null) {
             length++;
           }

           in.reset(); // rewind the reader to the start of file
           contents = new String[length]; // give size to contents array

           // read in contents of file and print to screen
           for (int i = 0; i < length; i++) {
             contents[i] = in.readLine();
           }
           in.close();
       } catch (Exception e) {
           errorMessage(3);
       }

       return contents;

    } // getFileContents
    
} //class ChangeOneLetterGameV2