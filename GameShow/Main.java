/* Author: Wing Chang
 * Date: October 23, 2023
 * Description: A program runs a singleplayer version of Wheel of Fortune.
 * The challenge feature implemented was: a functional timer that will interrupt the user mid-input. If the user does not answer question in 10 seconds, stop them from entering input and tell them time is up. (Counts as 2 challenge features)
 */

//package GameShow;
import java.util.concurrent.TimeUnit;

public class Main {
    // Creates a thread that is used to read input
    static InputTimer inputThread = new InputTimer();
    public static void main(String[]args){
        inputThread.start();

        String user = "";
        int money = 0;

        // Main menu loops until "q" is pressed to quit
        while(true){
            System.out.println("|| WELCOME TO WHEEL OF FORTUNE ||\n\n\n");
            System.out.println("PRESS P TO PLAY");
            System.out.println("PRESS I FOR INSTRUCTIONS");
            System.out.println("PRESS Q TO EXIT\n");

            while(true){
                user = inputThread.getInput(30, TimeUnit.MINUTES).toLowerCase();

                if (user == null) {
					// If user forgets to enter input within 30 minutes, terminate the program
					System.out.println("\nTimed out. No input entered for longer than 30 minutes.");
					System.exit(0);
				}
                else{
                    // Checks for invalid input and prompts user to reenter
                    if(user.equals("p") || user.equals("i") || user.equals("q")) break;
                    System.out.println("Invalid input. You must enter P, I or Q to continue.\n");
                }
            }
            System.out.println();

            // End program if user quits
            if(user.toLowerCase().equals("q")){
                System.out.println("Thanks for playing! (Press enter or any key to confirm quit)");
                break;
            }

            // Prints out game instructions
            else if(user.toLowerCase().equals("i")){
                System.out.println("Here are the instructions!\n");
                System.out.println("|| INSTRUCTIONS ||\n");

                System.out.println("*BASICS*");
                System.out.println("  - During the game, a category and a sentence from that category are randomly chosen.");
                System.out.println("  - The goal of the game is to guess the sentence while amassing the biggest prize pool you can.");
                System.out.println("  - In this singleplayer version of the game, the goal is to guess the sentence in 15 turns.");
                System.out.println("  - Each round you can: spin the wheel and guess a consonant, buy a vowel for $250 or solve the puzzle.");
                System.out.println("     - Each consonant is worth the cash value of the wedge the wheel lands on.");
                System.out.println("     - Ex: You spin $500 and guess \"s\", which is found 2 times in the sentence. You earn $1000.");
                System.out.println("     - You earn $3000 for correctly solving, and lose $1000 if it is incorrect.");
                System.out.println("\n*THE WHEEL OF FORTUNE*");
                System.out.println("- The Wheel of Fortune has 24 wedges with cash values from $550 to $2500 as well as 3 special wedges.");
                System.out.println("      1. Wild Card: Gives you a free consonant to guess");
                System.out.println("      2. Bankrupt: You lose all of the money and possesions (ex: Wild Card) you earned in the game so far.");
                System.out.println("      3. Lose a Turn: You lose a turn in the game");
                System.out.println("  - You have a limit of 30 seconds to answer each question and will lose a turn if you don't answer in time.");
                System.out.println("\nPress enter to return to the main menu.");

                // Gives user 10 minutes before starting game if enter is pressed
                inputThread.getInput(10, TimeUnit.MINUTES);
            }

            money += Guessing.guessing(); // Calls game guessing method

            // Print out final prize money
            System.out.println("\nCongratulations for completing the game! You've won $" + money + " in prize money.\n");
        }
        
        // Closes thread and scanner
        inputThread.interrupt();
    }
}