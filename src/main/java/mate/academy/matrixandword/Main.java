package mate.academy.matrixandword;

public class Main {
    public static void main(String[] args) {
        SquareMatrix squareMatrix = new SquareMatrix(args[0]);
        System.out.println(squareMatrix.getCeilOfLetters(args[1]));
    }
}