//package GameShow;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Guessing {
    // Initialize variables that are used in several methods
    static ArrayList<String> guesses = new ArrayList<>();
    static String answer = "";

    public static int guessing(){
        // Call SentenceGenerator methods to get a random category and sentence for the game
        String category = SentenceGenerator.categoryGenerator(), sentence = SentenceGenerator.generator(category);
        
        String user = "", solve = "", vowel;
        char[] letters = sentence.toCharArray();
        int counter = 1, money = 0, spin = 0;
        boolean guessed = false, wildCard = false;

        // Initialize arraylist of vowels and add them to the list
        ArrayList<Character> vowels  = new ArrayList<>();
        vowels.add('a'); vowels.add('e'); vowels.add('o'); vowels.add('u'); vowels.add('i');

        // Clear data from previous rounds
        answer = "";
        guesses = new ArrayList<>();

        // Print out category and general structure of sentence to guess
        System.out.println("Category: " + category);
        for(int i = 0; i < sentence.length(); i++){
            if(sentence.charAt(i) == ' ') answer += " ";
            else answer += "_";
        }
        System.out.println(answer + "\n");

        // Loop through fifteen spins to guess sentence
        game: while(counter <= 15){
            System.out.println("|| SPIN " + counter + " ||\n");

            // Ask if user wants to buy vowel/solve before spinning (only if not 1st spin)
            if(counter != 1){
                // Print category and current state of sentence
                System.out.println("Category: " + category);
                System.out.println(answer);

                System.out.println("Would you like to buy a vowel or solve the puzzle?");
                System.out.println("Type B for buy, S to solve or Q to skip.");
                
                // Check for valid input (B, S or Q)
                while(true){
                    user = Main.inputThread.getInput(5, TimeUnit.MINUTES).toLowerCase();
                    if(user == null){
                        // If user forgets to enter input within 5 minutes, move on
					    System.out.println("\nTimed out. No input entered for longer than 5 minutes.");
                        user = "q";
                    }
                    if(user.equals("b") || user.equals("s") || user.equals("q")) break;
                    System.out.println("Invalid input. You must enter B, S or Q to continue.\n");
                }
                System.out.println();
                
                // Code for buying a vowel
                vowel: if(user.toLowerCase().equals("b")){
                    if(vowels.size() == 0){ // check if all vowels have been bought
                        System.out.println("There are no vowels left to buy!\n");
                        break vowel; // break out of vowel code using labelled break
                    }
                    
                    // Print out all currently available vowels
                    System.out.print("The currently available vowels are:");
                    for(int i = 0; i < vowels.size(); i++){
                        System.out.print(" ");
                        System.out.print(vowels.get(i));
                    }
                    System.out.println();

                    // Get the vowel that they want to buy
                    System.out.print("What vowel would you like to buy: ");
                    vowel = Main.inputThread.getInput(5, TimeUnit.MINUTES).toLowerCase();
                    if(vowel == null){
                        // If user forgets to choose vowel within 5 minutes, move on
					    System.out.println("\nTimed out. No input entered for longer than 5 minutes.");
                        break vowel;
                    }

                    // Check if they have the money to buy it
                    if(money - 250 >= 0){
                        if(count(vowel, letters) == 0) System.out.println("The vowel is not found in the sentence!");
                        else System.out.println("The vowel has been found " + count(vowel, letters) + " times!");

                        answer = print(vowel, letters, answer);
                        System.out.println(answer + "\n");

                        // Remove vowel that has been bought from the ArrayList of available vowels and remove cost from current prize money
                        for(int i = 0; i < vowels.size(); i++){
                            if(vowels.get(i) == vowel.charAt(0)){
                                vowels.remove(i);
                            }
                        }
                        money -= 250;
                    }
                    else System.out.println("You don't have enough money to buy a vowel!\n");
                }
                solve: if(user.toLowerCase().equals("s")){
                    System.out.println("Enter the answer to the puzzle: ");
                    solve = Main.inputThread.getInput(5, TimeUnit.MINUTES).toLowerCase();
                    if(solve == null){
                        // If user forgets to choose vowel within 5 minutes, move on
					    System.out.println("\nTimed out. No input entered for longer than 5 minutes.");
                        break solve;
                    }

                    // Check if the guessed sentence is correct
                    if(solve.equals(sentence.toLowerCase())){
                        System.out.println("\nCongratulations, you've guessed the sentence!");
                        guessed = true;
                        money += 3000;
                        break game; // exit entire game, back to main menu
                    }
                    else{
                        System.out.println("That was incorrect. Good luck next time!\n");
                        if(money - 500 < 0) money = 0;
                        else money -= 1000;
                    }
                }
            }

            // Spin the Wheel of Fortune
            System.out.println("The Wheel of Fortune has been spun!");
            System.out.print("You have spun ");

            // Call MoneyGenerator class to spin wheel
            spin = MoneyGenerator.wheelSpin();

            // For special wedges, call specific function from MoneyGenerator
            if(spin == -1){
                MoneyGenerator.wildCard();
                wildCard = true;
            }
            else if(spin == -2){
                MoneyGenerator.bankrupt();
                money = 0;

                // Delete previous game boosts
                wildCard = false;
            }
            else if(spin == -3) MoneyGenerator.loseAturn();
            else System.out.println("$" + spin);

            // If user did not spin Bankrupt or Lose a Turn, continue with this spin
            if(spin != -2 && spin != -3){

                // Run if a Wild Card is available to use
                wC: if(wildCard){
                    System.out.println("You have a Wild Card avaliable. Would you like to use it? Enter Y for yes, N for no.");
                    
                    // Get and check for valid input
                    while(true){
                        user = null; // clear previous user input
                        user = Main.inputThread.getInput(5, TimeUnit.MINUTES).toLowerCase();
                        if(user == null){
                            // If user does not choose yes or no within 5 minutes, move on
                            System.out.println("\nTimed out. No input entered for longer than 5 minutes.");
                            break wC; // break out of Wild Card code
                        }

                        if(user.equals("y")) break;
                        else if(user.equals("n")) break wC; // use labelled break to get out of Wild Card code
                        System.out.println("Invalid input. You must enter Y or N to continue.\n");
                    }
                    money += guess(500, guesses, letters); // $500 per consonant guessed
                    wildCard = false; // delete the Wild Card from user's inventory

                    System.out.println();
                }
                
                // Call guess function to guess a letter in the sentence
                money += guess(spin, guesses, letters);

                // Check if full sentence has been guessed and end game
                if(answer.equals(sentence)){
                    guessed = true;
                    break game;
                }
            }
            counter++; // increase counter of spins
            System.out.println("Your current prize money is $" + money + "\n"); // prints out current prize money
        }
        // Print answer to sentence if it has not been guessed
        if(!guessed){
            System.out.println("You did not guess the sentence in the given spins!");
            System.out.println("The sentence was: " + sentence);
        }

        return money; // return prize money amount
    }

    // Method takes the spin (cost), ArrayList of previous guesses and char array of letters in sentence
    // Method returns money earned from the guess
    public static int guess(int spin, ArrayList<String> guesses, char[] letters){
        int money = 0;

        String guess = input(); // Get and check for invalid input

        // Check if input has already been entered/is a vowel and loop until a consonant is given
        if(guess.equals("0")){
            System.out.println("Time is up! You have lost a turn.");
            return 0;
        }

        // check for repeating guesses
        if(repeat(guess, guesses)){ 
            while(repeat(guess, guesses)){
                System.out.println("You already guessed this letter!");
                guess = input();
            }
        }
        // check for vowels
        else if(vowels(guess)){ 
            while(vowels(guess)){
                System.out.println("You can only guess consonants!");
                guess = input();
            }
        }
        
        // Let user know if their guess has been found and how many times
        if(count(guess, letters) == 0) System.out.println("Your guess is not found in the sentence!");
        else System.out.println("Your guess has been found " + count(guess, letters) + " times!");

        // Add guesses to already guessed list, print answer and calculate money
        guesses.add(guess.toLowerCase());
        answer = print(guess, letters, answer);
        money = count(guess, letters)*spin;
                
        System.out.println(answer + "\n"); // print out where the letters are found in the sentence
        return money;
    }

    // Takes in a single letter from user, checks for potential errors and if within time limit of 10 seconds
    public static String input(){
        String guess = "";
        int time = 30; // 30 seconds

        while(true){
            System.out.print("Enter a letter: ");

            // Keep waiting for input for every second, break if input received
            while (time >= 0) {
				guess = Main.inputThread.getInput(1, TimeUnit.SECONDS);

                // If input if received, break from loop
				if (guess != null) {
					break;
				}
				time--;
			}
            if (guess == null) return "0"; // return error value if no input
            
            // Check for invalid input (must be a single letter)
            if(guess.length() != 1) System.out.println("Invalid input! You must enter a single letter.");
            else if(!Character.isLetter(guess.charAt(0))) System.out.println("Invalid input. You must enter an alphabetical letter.");
            else break;
        }
        System.out.println();
        return guess;
    }

    // Count the number of times a guess appears in the sentence
    public static int count(String guess, char[]letters){
        int count = 0;

        // Loop through all letters and check if it is same to guess
        for(int i = 0; i < letters.length; i++){
            String letter = String.valueOf(letters[i]).toLowerCase();
            if(guess.toLowerCase().equals(letter)) count++;
        }

        return count;
    }
    
    // Print out what letters have been guessed in context of whole sentence
    public static String print(String guess, char[] letters, String current){
        String sentence = "";

        for(int i = 0; i < letters.length; i++){
            String letter = String.valueOf(letters[i]).toLowerCase();

            // Check if guess is equal to current letter, if so make it appear in sentence
            if(guess.toLowerCase().equals(letter)) sentence += letters[i];
            else if(letters[i] == ' ') sentence += " ";
            else sentence += current.charAt(i); // print out what has been guessed so far
        }

        return sentence;
    }

    // Check if a vowel appears in the sentence
    public static boolean vowels (String guess){
        String v = "aeoui";

        // Loop through and compare each vowel with each letter in sentence, true if match found
        for(int i = 0; i < v.length(); i++)
            if(v.substring(i, i+1).equals(guess.toLowerCase()))
                return true;
        
        return false;
    }

    // Check for a repeated guess using previous guess ArrayList
    public static boolean repeat (String guess, ArrayList<String> guesses){
        for(int i = 0; i < guesses.size(); i++)
            if(guess.toLowerCase().equals(guesses.get(i)))
                return true;
        
        return false;
    }
}