import java.util.Scanner;

public class NestedLoops {
    public static void main (String[]args){
        Scanner in = new Scanner(System.in);
        // 1.& 2.
        /*int w = in.nextInt();
        for(int i = 0; i < w; i++){
            for(int j = 0; j <= i; j++){
                System.out.print("*");
                if(j != i) System.out.print(" ");
            }
            System.out.println();
        }

        // 3.
        int width = in.nextInt(), height = in.nextInt(), count = 0;

        for(int i = 0; i < height; i++){
            count = 0;

            if(i % 2 == 0){ // if even row
                while(count <= width){
                    System.out.print("* ");
                    count+=2;
                }
            }
            else{
                while(count <= width - 2){
                    System.out.print(" *");
                    count+=2;
                }
            }
            System.out.println();
        }

        // 4.
        System.out.println("Base \t Result");
        for(int i = 1; i <= 20; i++){
            System.out.print(i + "\t" + i * i);
            System.out.println();    
        }

        // 5.
        int user, count = 0;
        String value;

        System.out.print("Enter tracking value: ");
        value = in.next();
        System.out.println("Enter values or -1 to stop: ");
        user = in.nextInt();
        while(user != -1){
            String values = String.valueOf(user);
            for(int i = 0; i < values.length(); i++){
                //System.out.println(values.substring(i, i+1) + " " + value);
                if(values.substring(i, i+1).equals(value)) count++;
            }
            user = in.nextInt();
        }
        System.out.println("In total, " + value + " was entered: " + count + " times.");*/

        // 6.
        System.out.print("Enter maximum value: ");
        int user = in.nextInt();
        for(int c = 0; c < user; c++)
            for(int b = 0; b < c; b++)
                for(int a = 0; a < b; a++)
                    if((a*a) + (b*b) == c*c) System.out.println(a + " " + b +" " + c);
        
    }
}
