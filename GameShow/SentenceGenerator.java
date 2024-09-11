//package GameShow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SentenceGenerator {
    public static String generator(String c){
        File cat = new File ("GameShow\\PeopleSentences.txt");
        ArrayList<String> category = new ArrayList<>();

        // Given random category (that is not People), get respective file of sentences
        if(c.equals("Person"))
            cat = new File ("GameShow\\PersonSentences.txt");
        else if(c.equals("Phrase"))
            cat = new File ("GameShow\\PhraseSentences.txt");
        else if(c.equals("Place"))
            cat = new File ("GameShow\\PlaceSentences.txt");

        try{
            Scanner scanner = new Scanner(cat); // read content of file for the category
            while(scanner.hasNextLine()) category.add(scanner.nextLine()); // read lines from file and add them to category ArrayList
            scanner.close();
        }
        catch (FileNotFoundException e){
            // Handle error if file is not found
            System.out.println("File not found!");
            e.printStackTrace();
        }
        return sentenceGenerator(category);
    }
    // Takes array and returns random string from it (maybe use AccessReaderFile another day)
    public static String sentenceGenerator(ArrayList<String> arr){
        int random = (int)(Math.random()*(arr.size()-1));
        return arr.get(random);
    }
    
    // Generates a random category given the four choices
    public static String categoryGenerator(){
        String[] categories = {"People", "Person", "Phrase", "Place"};
        return categories[(int)((Math.random()*4))];
    }
}
