package mate.academy.matrixandword;

public class Main {
    public static void main(String[] args) {
        SquareMatrixService squareMatrixService = new SquareMatrixService();
        Character[][] matrix = squareMatrixService.getSquareMatrixCharacters(args[0]);
        System.out.println(squareMatrixService.getCeilOfLetters(matrix, args[1]));
    }
}
