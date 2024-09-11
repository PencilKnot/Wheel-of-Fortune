//package GameShow;

public class MoneyGenerator {
    public static int wheelSpin(){
        int s = (int)(Math.random() * 24) + 1;
        int spin = 0;

        if(s == 1) spin = 2500;
        else if(s == 3 || s == 5 || s == 10 || s == 13) spin = 600;
        else if(s == 4 || s == 8 || s == 17) spin = 700;
        else if(s == 6 || s == 9 || s == 15 || s == 21) spin = 650;
        else if(s == 7 || s == 12 || s == 20 || s == 22) spin = 500;
        else if(s == 11) spin = 550;
        else if(s == 19) spin = 800;
        else if(s == 23) spin = 900;
        else if(s == 2 || s == 16) spin = -1;
        else if(s == 14 || s == 24) spin = -2;
        else if(s == 18) spin = -3;
        return spin;
    }
    
    // Method for special Wild Card interactions
    public static void wildCard(){
        System.out.println("Wild Card!");
    }

    // Method for special Bankrupt interactions
    public static void bankrupt(){
        System.out.println("Bankrupt!");
        System.out.println("Unfortunately, you have lost all of your prize money and your current turn. Better luck next spin!");
    }

    // Method for special Lose a Turn interactions
    public static void loseAturn(){
        System.out.println("Lose a Turn!");
        System.out.println("Unfortunately, you have lost your current turn. Better luck next spin!");
    }
}
