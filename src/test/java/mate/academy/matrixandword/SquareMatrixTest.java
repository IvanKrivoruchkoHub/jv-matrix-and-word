package mate.academy.matrixandword;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

public class SquareMatrixTest {

    private SquareMatrixService matrixService;

    @Before
    public void init() {
        matrixService = new SquareMatrixService();
    }

    @Test(expected = IllegalArgumentException.class)
    public void matrixStringNotOk() {
        matrixService.getSquareMatrixCharacters("QLG");
    }

    @Test(expected = IllegalArgumentException.class)
    public void matrixStringNull() {
        matrixService.getSquareMatrixCharacters(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wordLengthBiggerThanMatrixLength() {
        Character[][] matrix = matrixService.getSquareMatrixCharacters("QLGY");
        matrixService.getCeilOfLetters(matrix, "hello");
    }

    @Test
    public void getCeilOfLettersOk() {
        Character[][] matrix = matrixService.getSquareMatrixCharacters("QUENPVKIJAVNGEAG");
        String actualResult = matrixService.getCeilOfLetters(matrix, "king");
        Assert.assertEquals("[1, 2] -> [1, 3] -> [2, 3] -> [3, 3]", actualResult);

        actualResult = matrixService.getCeilOfLetters(matrix,"java");
        Assert.assertEquals("[2, 0] -> [2, 1] -> [2, 2] -> [3, 2]", actualResult);
    }

    @Test
    public void matrixNotConsistWord() {
        Character[][] matrix = matrixService.getSquareMatrixCharacters("QUENPVKIJAVNGEAG");
        String actualResult = matrixService.getCeilOfLetters(matrix,"hello");
        Assert.assertEquals("", actualResult);
    }
}
