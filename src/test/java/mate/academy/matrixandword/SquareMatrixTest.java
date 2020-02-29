package mate.academy.matrixandword;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SquareMatrixTest {

    private SquareMatrix squareMatrix;

    @Before
    public void init() {
        squareMatrix = new SquareMatrix();
    }

    @Test(expected = IllegalArgumentException.class)
    public void matrixStringNotOk() {
        squareMatrix.createMatrix("QLG");
    }

    @Test(expected = IllegalArgumentException.class)
    public void matrixStringNull() {
        squareMatrix.createMatrix(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wordLengthBiggerThanMatrixLength() {
        squareMatrix.createMatrix("QLGY");
        squareMatrix.getCeilOfLetters("hello");
    }

    @Test
    public void getCeilOfLettersOk() {
        squareMatrix.createMatrix("QUENPVKIJAVNGEAG");
        String actualResult = squareMatrix.getCeilOfLetters("king");
        Assert.assertEquals("[1, 2] -> [1, 3] -> [2, 3] -> [3, 3]", actualResult);

        actualResult = squareMatrix.getCeilOfLetters("java");
        Assert.assertEquals("[2, 0] -> [2, 1] -> [2, 2] -> [3, 2]", actualResult);
    }

    @Test
    public void matrixNotConsistWord() {
        squareMatrix.createMatrix("QUENPVKIJAVNGEAG");
        String actualResult = squareMatrix.getCeilOfLetters("hello");
        Assert.assertEquals("", actualResult);
    }
}
