import java.util.Scanner;

public class MainClass {

    public static void main(String[] args){
        Quiz quiz1 = new GuGuDan();

        quiz1.play();

        System.out.println("What's right answer?");
        Scanner scanner = new Scanner(System.in);

        String inputValue = scanner.nextLine();
        if (inputValue.equals(quiz1.answer()))
            System.out.println("correct!");
        else
            System.out.println("wrong!");

    }
}
