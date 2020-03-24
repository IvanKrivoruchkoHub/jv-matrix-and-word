package mate.academy.matrixandword;

public class SquareMatrixService {

    public Character[][] getSquareMatrixCharacters(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String is null. Can't create square matrix");
        }
        if (Math.sqrt(s.length()) % 1 != 0) {
            throw new IllegalArgumentException("Sqrt of string length not integer. "
                    + "Can't create square matrix");
        }
        int rowsCount = (int) Math.sqrt(s.length());
        Character[][] matrix = new Character[rowsCount][rowsCount];
        s = s.toUpperCase();
        for (int i = 0, q = 0; i < rowsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                matrix[i][j] = s.charAt(q++);
            }
        }
        return matrix;
    }

    public String getCeilOfLetters(Character[][] matrix, String word) {
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
                    int startLen = result.length();
                    result = checkCeil(matrix, result, i, j, wordCharArray, numberOfLetter + 1);
                    if (startLen == result.length()) {
                        result = new StringBuilder();
                    } else {
                        return result.toString();
                    }
                }
            }
        }
        return result.toString();
    }

    private StringBuilder checkOneDirection(Character[][] matrix, int row, int column,
                                            char[] wordCharArray, int numberOfLetter,
                                            StringBuilder sb) {
        if (matrix[row][column] == wordCharArray[numberOfLetter]
                && sb.indexOf(String.format("[%d, %d]", row, column)) == -1) {
            int lenBeforeAppend = sb.length();
            sb.append(String.format("[%d, %d]", row, column));
            if (numberOfLetter != wordCharArray.length - 1) {
                sb.append(" -> ");
            }
            int lenAfterAppend = sb.length();
            sb = checkCeil(matrix, sb, row, column, wordCharArray, numberOfLetter + 1);
            if (sb.length() == lenAfterAppend && numberOfLetter != wordCharArray.length - 1) {
                sb.delete(lenBeforeAppend, sb.length());
            }
        }
        return sb;
    }

    private StringBuilder checkLeftAndUpDirection(Character[][] matrix,StringBuilder sb,
                                                  int row, int column,
                                                  char[] wordCharArray, int numberOfLetter) {
        int lenBefore = sb.length();
        sb = checkOneDirection(matrix, row, column - 1, wordCharArray, numberOfLetter, sb);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkOneDirection(matrix, row - 1, column, wordCharArray, numberOfLetter, sb);
    }

    private StringBuilder checkRightAndDownDirection(Character[][] matrix, StringBuilder sb,
                                                     int row, int column,
                                                     char[] wordCharArray,
                                                     int numberOfLetter) {
        int lenBefore = sb.length();
        sb = checkOneDirection(matrix, row, column + 1, wordCharArray, numberOfLetter, sb);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkOneDirection(matrix,row + 1, column, wordCharArray, numberOfLetter, sb);
    }

    private StringBuilder checkLeftAndDownDirection(Character[][] matrix, StringBuilder sb,
                                                    int row, int column,
                                                    char[] wordCharArray,
                                                    int numberOfLetter) {
        int lenBefore = sb.length();
        sb = checkOneDirection(matrix, row, column - 1, wordCharArray, numberOfLetter, sb);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkOneDirection(matrix, row + 1, column, wordCharArray, numberOfLetter, sb);
    }

    private StringBuilder checkLeftAndRightAndDownDirection(Character[][] matrix,
                                                            StringBuilder sb, int row,
                                                            int column,
                                                            char[] wordCharArray,
                                                            int numberOfLetter) {
        int lenBefore = sb.length();
        sb = checkOneDirection(matrix, row, column - 1, wordCharArray, numberOfLetter, sb);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkRightAndDownDirection(matrix, sb, row, column, wordCharArray, numberOfLetter);
    }

    private StringBuilder checkUpAndRightDirection(Character[][] matrix, StringBuilder sb,
                                                   int row, int column,
                                                   char[] wordCharArray, int numberOfLetter) {
        int lenBefore = sb.length();
        sb = checkOneDirection(matrix, row - 1, column, wordCharArray, numberOfLetter, sb);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkOneDirection(matrix, row, column + 1, wordCharArray, numberOfLetter, sb);
    }

    private StringBuilder checkLeftAndUpAndRightDirection(Character[][] matrix, StringBuilder sb,
                                                          int row, int column,
                                                          char[] wordCharArray,
                                                          int numberOfLetter) {
        int lenBefore = sb.length();
        sb = checkLeftAndUpDirection(matrix, sb, row, column, wordCharArray, numberOfLetter);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkOneDirection(matrix, row, column + 1, wordCharArray, numberOfLetter, sb);
    }

    private StringBuilder checkUpAndRightAndDownDirection(Character[][] matrix,
                                                          StringBuilder sb, int row, int column,
                                                          char[] wordCharArray,
                                                          int numberOfLetter) {
        int lenBefore = sb.length();
        sb = checkOneDirection(matrix, row - 1, column, wordCharArray, numberOfLetter, sb);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkRightAndDownDirection(matrix, sb, row, column, wordCharArray, numberOfLetter);
    }

    private StringBuilder checkLeftAndUpAndDownDirection(Character[][] matrix,
                                                         StringBuilder sb, int row, int column,
                                                         char[] wordCharArray, int numberOfLetter) {
        int lenBefore = sb.length();
        sb = checkLeftAndUpDirection(matrix, sb, row, column, wordCharArray, numberOfLetter);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkOneDirection(matrix, row + 1, column, wordCharArray, numberOfLetter, sb);
    }

    private StringBuilder checkAllDirection(Character[][] matrix,
                                            StringBuilder sb, int row, int column,
                                            char[] wordCharArray,
                                            int numberOfLetter) {
        int lenBefore = sb.length();
        sb = checkLeftAndUpDirection(matrix, sb, row, column, wordCharArray, numberOfLetter);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkRightAndDownDirection(matrix, sb, row, column, wordCharArray, numberOfLetter);
    }

    private StringBuilder checkCeil(Character[][] matrix, StringBuilder sb, int row, int column,
                                    char[] wordCharArray, int numberOfLetter) {
        if (numberOfLetter == wordCharArray.length) {
            return sb;
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
