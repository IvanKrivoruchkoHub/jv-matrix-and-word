package mate.academy.matrixandword;

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
        StringBuilder result = new StringBuilder();
        char[] wordCharArray = word.toUpperCase().toCharArray();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (wordCharArray[numberOfLetter] == matrix[i][j]) {
                    result.append(String.format("[%d, %d]", i, j)).append(" -> ");
                    if (!checkCeil(matrix, result, i, j, wordCharArray, numberOfLetter + 1)) {
                        result = new StringBuilder();
                    } else {
                        return result.toString();
                    }
                }
            }
        }
        return result.toString();
    }

    private boolean checkOneDirection(char[][] matrix, int row, int column,
                                            char[] wordCharArray, int numberOfLetter,
                                            StringBuilder sb) {
        if (matrix[row][column] == wordCharArray[numberOfLetter]
                && sb.indexOf(String.format("[%d, %d]", row, column)) == -1) {
            int lenBeforeAppend = sb.length();
            sb.append(String.format("[%d, %d]", row, column));
            if (numberOfLetter != wordCharArray.length - 1) {
                sb.append(" -> ");
            }
            boolean checkCeilRes = checkCeil(matrix, sb, row, column, wordCharArray, numberOfLetter + 1);
            if (!checkCeilRes) {
                sb.delete(lenBeforeAppend, sb.length());
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean checkLeftAndUpDirection(char[][] matrix,StringBuilder sb,
                                                  int row, int column,
                                                  char[] wordCharArray, int numberOfLetter) {
        return checkOneDirection(matrix, row, column - 1, wordCharArray, numberOfLetter, sb)
            || checkOneDirection(matrix, row - 1, column, wordCharArray, numberOfLetter, sb);
    }

    private boolean checkRightAndDownDirection(char[][] matrix, StringBuilder sb,
                                                     int row, int column,
                                                     char[] wordCharArray,
                                                     int numberOfLetter) {
        return checkOneDirection(matrix, row, column + 1, wordCharArray, numberOfLetter, sb)
            || checkOneDirection(matrix,row + 1, column, wordCharArray, numberOfLetter, sb);
    }

    private boolean checkLeftAndDownDirection(char[][] matrix, StringBuilder sb,
                                                    int row, int column,
                                                    char[] wordCharArray,
                                                    int numberOfLetter) {
        return checkOneDirection(matrix, row, column - 1, wordCharArray, numberOfLetter, sb)
            || checkOneDirection(matrix, row + 1, column, wordCharArray, numberOfLetter, sb);
    }

    private boolean checkLeftAndRightAndDownDirection(char[][] matrix,
                                                            StringBuilder sb, int row,
                                                            int column,
                                                            char[] wordCharArray,
                                                            int numberOfLetter) {
        return  checkOneDirection(matrix, row, column - 1, wordCharArray, numberOfLetter, sb)
            || checkRightAndDownDirection(matrix, sb, row, column, wordCharArray, numberOfLetter);
    }

    private boolean checkUpAndRightDirection(char[][] matrix, StringBuilder sb,
                                                   int row, int column,
                                                   char[] wordCharArray, int numberOfLetter) {
        return  checkOneDirection(matrix, row - 1, column, wordCharArray, numberOfLetter, sb)
            || checkOneDirection(matrix, row, column + 1, wordCharArray, numberOfLetter, sb);
    }

    private boolean checkLeftAndUpAndRightDirection(char[][] matrix, StringBuilder sb,
                                                          int row, int column,
                                                          char[] wordCharArray,
                                                          int numberOfLetter) {
        return  checkLeftAndUpDirection(matrix, sb, row, column, wordCharArray, numberOfLetter)
            || checkOneDirection(matrix, row, column + 1, wordCharArray, numberOfLetter, sb);
    }

    private boolean checkUpAndRightAndDownDirection(char[][] matrix,
                                                          StringBuilder sb, int row, int column,
                                                          char[] wordCharArray,
                                                          int numberOfLetter) {
        return  checkOneDirection(matrix, row - 1, column, wordCharArray, numberOfLetter, sb)
            || checkRightAndDownDirection(matrix, sb, row, column, wordCharArray, numberOfLetter);
    }

    private boolean checkLeftAndUpAndDownDirection(char[][] matrix,
                                                         StringBuilder sb, int row, int column,
                                                         char[] wordCharArray, int numberOfLetter) {
        return  checkLeftAndUpDirection(matrix, sb, row, column, wordCharArray, numberOfLetter)
            || checkOneDirection(matrix, row + 1, column, wordCharArray, numberOfLetter, sb);
    }

    private boolean checkAllDirection(char[][] matrix,
                                            StringBuilder sb, int row, int column,
                                            char[] wordCharArray,
                                            int numberOfLetter) {
        return  checkLeftAndUpDirection(matrix, sb, row, column, wordCharArray, numberOfLetter)
            || checkRightAndDownDirection(matrix, sb, row, column, wordCharArray, numberOfLetter);
    }

    private boolean checkCeil(char[][] matrix, StringBuilder sb, int row, int column,
                                    char[] wordCharArray, int numberOfLetter) {
        if (numberOfLetter == wordCharArray.length) {
            return true;
        }
        if (row == 0) {
            if (column == 0) {
                return checkRightAndDownDirection(matrix, sb, row, column,
                        wordCharArray, numberOfLetter);
            }

            if (column == matrix.length - 1) {
                return checkLeftAndDownDirection(matrix, sb, row, column,
                        wordCharArray, numberOfLetter);
            }

            return checkLeftAndRightAndDownDirection(matrix, sb, row, column,
                    wordCharArray, numberOfLetter);
        }

        if (row == matrix.length - 1) {
            if (column == 0) {
                return checkUpAndRightDirection(matrix, sb, row, column,
                        wordCharArray, numberOfLetter);
            }

            if (column == matrix.length - 1) {
                return checkLeftAndUpDirection(matrix, sb, row, column,
                        wordCharArray, numberOfLetter);
            }
            return checkLeftAndUpAndRightDirection(matrix, sb, row, column,
                    wordCharArray, numberOfLetter);
        }

        if (column == 0) {
            return checkUpAndRightAndDownDirection(matrix, sb, row, column,
                    wordCharArray, numberOfLetter);
        }

        if (column == matrix.length - 1) {
            return checkLeftAndUpAndDownDirection(matrix, sb, row, column,
                    wordCharArray, numberOfLetter);
        }

        return checkAllDirection(matrix, sb, row, column, wordCharArray, numberOfLetter);
    }

}
