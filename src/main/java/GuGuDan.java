import java.util.Scanner;

public class GuGuDan implements Quiz{

    private int number1;
    private int number2;
    private int answer;


    @Override
    public void play() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Let's gugudan");
        System.out.println("Enter first number");
        number1 = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter second number");
        number2 = Integer.parseInt(scanner.nextLine());
        answer = number1 * number2;
    }

    @Override
    public String answer() {
        return String.valueOf(this.answer);
    }
}
