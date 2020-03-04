package mate.academy.matrixandword;

import org.junit.Assert;

import org.junit.Test;

public class SquareMatrixTest {

    @Test(expected = IllegalArgumentException.class)
    public void matrixStringNotOk() {
        new SquareMatrix("QLG");
    }

    @Test(expected = IllegalArgumentException.class)
    public void matrixStringNull() {
        new SquareMatrix(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wordLengthBiggerThanMatrixLength() {
        SquareMatrix squareMatrix = new SquareMatrix("QLGY");
        squareMatrix.getCeilOfLetters("hello");
    }

    @Test
    public void getCeilOfLettersOk() {
        SquareMatrix squareMatrix = new SquareMatrix("QUENPVKIJAVNGEAG");
        String actualResult = squareMatrix.getCeilOfLetters("king");
        Assert.assertEquals("[1, 2] -> [1, 3] -> [2, 3] -> [3, 3]", actualResult);

        actualResult = squareMatrix.getCeilOfLetters("java");
        Assert.assertEquals("[2, 0] -> [2, 1] -> [2, 2] -> [3, 2]", actualResult);
    }

    @Test
    public void matrixNotConsistWord() {
        SquareMatrix squareMatrix = new SquareMatrix("QUENPVKIJAVNGEAG");
        String actualResult = squareMatrix.getCeilOfLetters("hello");
        Assert.assertEquals("", actualResult);
    }
}
