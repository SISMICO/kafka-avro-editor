import calculator.Compute;
import calculator.ICompute;

public class Main {

    public static void main(String[] args) {
        ICompute compute = new Compute();

        System.out.println("Simple Java App");
        System.out.println(String.format("Version: %d", compute.version()));
        System.out.println(String.format("Calculator: %d", compute.sum(10, 20)));
    }

}
