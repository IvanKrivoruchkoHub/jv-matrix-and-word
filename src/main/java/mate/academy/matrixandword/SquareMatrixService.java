package mate.academy.matrixandword;

import java.util.ArrayList;
import java.util.List;

public class SquareMatrixService {

    public char[][] getSquareMatrixCharacters(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String is null. Can't create square matrix");
        }
        if (Math.sqrt(s.length()) % 1 != 0) {
            throw new IllegalArgumentException("Sqrt of string length not integer. "
                    + "Can't create square matrix");
        }
        int rowsCount = (int) Math.sqrt(s.length());
        char[][] matrix = new char[rowsCount][rowsCount];
        s = s.toUpperCase();
        for (int i = 0, q = 0; i < rowsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                matrix[i][j] = s.charAt(q++);
            }
        }
        return matrix;
    }

    public String getCeilOfLetters(char[][] matrix, String word) {
        if (word == null) {
            throw new IllegalArgumentException("Word is null");
        }
        if (word.length() > matrix.length * matrix.length) {
            throw new IllegalArgumentException("Word length is bigger than count of matrix ceil");
        }
        int numberOfLetter = 0;
        List<String> resultCeils = new ArrayList<>();
        char[] wordCharArray = word.toUpperCase().toCharArray();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (wordCharArray[numberOfLetter] == matrix[i][j]) {
                    resultCeils.add(String.format("[%d, %d]", i, j));
                    if (!checkCeil(matrix, resultCeils, i, j, wordCharArray, numberOfLetter + 1)) {
                        resultCeils = new ArrayList<>();
                    } else {
                        return String.join(" -> ", resultCeils);
                    }
                }
            }
        }
        return String.join(" -> ", resultCeils);
    }

    private boolean checkOneDirection(char[][] matrix, int row, int column,
                                            char[] wordCharArray, int numberOfLetter,
                                      List<String> resultCeils) {
        if (matrix[row][column] == wordCharArray[numberOfLetter]
                && resultCeils.indexOf(String.format("[%d, %d]", row, column)) == -1) {
            int sizeBeforeAppend = resultCeils.size();
            resultCeils.add(String.format("[%d, %d]", row, column));
            boolean checkCeilRes = checkCeil(matrix, resultCeils, row, column, wordCharArray, numberOfLetter + 1);
            if (!checkCeilRes) {
                resultCeils.subList(sizeBeforeAppend, resultCeils.size()).clear();
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean checkLeftAndUpDirection(char[][] matrix, List<String> resultCeils,
                                                  int row, int column,
                                                  char[] wordCharArray, int numberOfLetter) {
        return checkOneDirection(matrix, row, column - 1, wordCharArray, numberOfLetter, resultCeils)
            || checkOneDirection(matrix, row - 1, column, wordCharArray, numberOfLetter, resultCeils);
    }

    private boolean checkRightAndDownDirection(char[][] matrix, List<String> resultCeils,
                                                     int row, int column,
                                                     char[] wordCharArray,
                                                     int numberOfLetter) {
        return checkOneDirection(matrix, row, column + 1, wordCharArray, numberOfLetter, resultCeils)
            || checkOneDirection(matrix,row + 1, column, wordCharArray, numberOfLetter, resultCeils);
    }

    private boolean checkLeftAndDownDirection(char[][] matrix, List<String> resultCeils,
                                                    int row, int column,
                                                    char[] wordCharArray,
                                                    int numberOfLetter) {
        return checkOneDirection(matrix, row, column - 1, wordCharArray, numberOfLetter, resultCeils)
            || checkOneDirection(matrix, row + 1, column, wordCharArray, numberOfLetter, resultCeils);
    }

    private boolean checkLeftAndRightAndDownDirection(char[][] matrix,
                                                      List<String> resultCeils, int row,
                                                            int column,
                                                            char[] wordCharArray,
                                                            int numberOfLetter) {
        return  checkOneDirection(matrix, row, column - 1, wordCharArray, numberOfLetter, resultCeils)
            || checkRightAndDownDirection(matrix, resultCeils, row, column, wordCharArray, numberOfLetter);
    }

    private boolean checkUpAndRightDirection(char[][] matrix, List<String> resultCeils,
                                                   int row, int column,
                                                   char[] wordCharArray, int numberOfLetter) {
        return  checkOneDirection(matrix, row - 1, column, wordCharArray, numberOfLetter, resultCeils)
            || checkOneDirection(matrix, row, column + 1, wordCharArray, numberOfLetter, resultCeils);
    }

    private boolean checkLeftAndUpAndRightDirection(char[][] matrix, List<String> resultCeils,
                                                          int row, int column,
                                                          char[] wordCharArray,
                                                          int numberOfLetter) {
        return  checkLeftAndUpDirection(matrix, resultCeils, row, column, wordCharArray, numberOfLetter)
            || checkOneDirection(matrix, row, column + 1, wordCharArray, numberOfLetter, resultCeils);
    }

    private boolean checkUpAndRightAndDownDirection(char[][] matrix,
                                                    List<String> resultCeils, int row, int column,
                                                          char[] wordCharArray,
                                                          int numberOfLetter) {
        return  checkOneDirection(matrix, row - 1, column, wordCharArray, numberOfLetter, resultCeils)
            || checkRightAndDownDirection(matrix, resultCeils, row, column, wordCharArray, numberOfLetter);
    }

    private boolean checkLeftAndUpAndDownDirection(char[][] matrix,
                                                   List<String> resultCeils, int row, int column,
                                                         char[] wordCharArray, int numberOfLetter) {
        return  checkLeftAndUpDirection(matrix, resultCeils, row, column, wordCharArray, numberOfLetter)
            || checkOneDirection(matrix, row + 1, column, wordCharArray, numberOfLetter, resultCeils);
    }

    private boolean checkAllDirection(char[][] matrix,
                                      List<String> resultCeils, int row, int column,
                                            char[] wordCharArray,
                                            int numberOfLetter) {
        return  checkLeftAndUpDirection(matrix, resultCeils, row, column, wordCharArray, numberOfLetter)
            || checkRightAndDownDirection(matrix, resultCeils, row, column, wordCharArray, numberOfLetter);
    }

    private boolean checkCeil(char[][] matrix, List<String> resultCeils, int row, int column,
                                    char[] wordCharArray, int numberOfLetter) {
        if (numberOfLetter == wordCharArray.length) {
            return true;
        }
        if (row == 0) {
            if (column == 0) {
                return checkRightAndDownDirection(matrix, resultCeils, row, column,
                        wordCharArray, numberOfLetter);
            }

            if (column == matrix.length - 1) {
                return checkLeftAndDownDirection(matrix, resultCeils, row, column,
                        wordCharArray, numberOfLetter);
            }

            return checkLeftAndRightAndDownDirection(matrix, resultCeils, row, column,
                    wordCharArray, numberOfLetter);
        }

        if (row == matrix.length - 1) {
            if (column == 0) {
                return checkUpAndRightDirection(matrix, resultCeils, row, column,
                        wordCharArray, numberOfLetter);
            }

            if (column == matrix.length - 1) {
                return checkLeftAndUpDirection(matrix, resultCeils, row, column,
                        wordCharArray, numberOfLetter);
            }
            return checkLeftAndUpAndRightDirection(matrix, resultCeils, row, column,
                    wordCharArray, numberOfLetter);
        }

        if (column == 0) {
            return checkUpAndRightAndDownDirection(matrix, resultCeils, row, column,
                    wordCharArray, numberOfLetter);
        }

        if (column == matrix.length - 1) {
            return checkLeftAndUpAndDownDirection(matrix, resultCeils, row, column,
                    wordCharArray, numberOfLetter);
        }

        return checkAllDirection(matrix, resultCeils, row, column, wordCharArray, numberOfLetter);
    }

}
