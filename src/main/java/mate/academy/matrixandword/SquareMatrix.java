package mate.academy.matrixandword;

public class SquareMatrix {
    private Character[][] matrix;

    public SquareMatrix() {
    }

    public void createMatrix(String s) {
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
        this.matrix = matrix;
    }

    public String getCeilOfLetters(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Word is null");
        }
        if (word.length() > matrix.length * matrix.length) {
            throw new IllegalArgumentException("Word length is bigger than count of matrix ceil");
        }
        StringBuilder result = new StringBuilder();
        char[] wordCharArray = word.toUpperCase().toCharArray();
        for (int i = 0, q = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (wordCharArray[q] == matrix[i][j]) {
                    result.append(String.format("[%d, %d]", i, j)).append(" -> ");
                    int startLen = result.length();
                    result = checkCeil(result, i, j, wordCharArray, q + 1);
                    if (startLen == result.length()) {
                        result = new StringBuilder();
                    } else {
                        return result.toString();
                    }
                }
            }
        }
        return "";
    }

    private StringBuilder checkOneDirection(int row, int column,
                                            char[] wordCharArray, int q, StringBuilder sb) {
        if (matrix[row][column] == wordCharArray[q]
                && sb.indexOf(String.format("[%d, %d]", row, column)) == -1) {
            int lenBeforeAppend = sb.length();
            sb.append(String.format("[%d, %d]", row, column));
            if (q != wordCharArray.length - 1) {
                sb.append(" -> ");
            }
            int lenAfterAppend = sb.length();
            sb = checkCeil(sb, row, column, wordCharArray, q + 1);
            if (sb.length() == lenAfterAppend && q != wordCharArray.length - 1) {
                sb.delete(lenBeforeAppend, sb.length());
            }
        }
        return sb;
    }

    private StringBuilder checkLeftAndUpDirection(StringBuilder sb, int row, int column,
                                                  char[] wordCharArray, int q) {
        int lenBefore = sb.length();
        sb = checkOneDirection(row, column - 1, wordCharArray, q, sb);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkOneDirection(row - 1, column, wordCharArray, q, sb);
    }

    private StringBuilder checkRightAndDownDirection(StringBuilder sb, int row, int column,
                                                     char[] wordCharArray, int q) {
        int lenBefore = sb.length();
        sb = checkOneDirection(row, column + 1, wordCharArray, q, sb);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkOneDirection(row + 1, column, wordCharArray, q, sb);
    }

    private StringBuilder checkLeftAndDownDirection(StringBuilder sb, int row, int column,
                                                    char[] wordCharArray, int q) {
        int lenBefore = sb.length();
        sb = checkOneDirection(row, column - 1, wordCharArray, q, sb);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkOneDirection(row + 1, column, wordCharArray, q, sb);
    }

    private StringBuilder checkLeftAndRightAndDownDirection(StringBuilder sb, int row, int column,
                                                            char[] wordCharArray, int q) {
        int lenBefore = sb.length();
        sb = checkOneDirection(row, column - 1, wordCharArray, q, sb);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkRightAndDownDirection(sb, row, column, wordCharArray, q);
    }

    private StringBuilder checkUpAndRightDirection(StringBuilder sb, int row, int column,
                                                   char[] wordCharArray, int q) {
        int lenBefore = sb.length();
        sb = checkOneDirection(row - 1, column, wordCharArray, q, sb);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkOneDirection(row, column + 1, wordCharArray, q, sb);
    }

    private StringBuilder checkLeftAndUpAndRightDirection(StringBuilder sb, int row, int column,
                                                          char[] wordCharArray, int q) {
        int lenBefore = sb.length();
        sb = checkLeftAndUpDirection(sb, row, column, wordCharArray, q);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkOneDirection(row, column + 1, wordCharArray, q, sb);
    }

    private StringBuilder checkUpAndRightAndDownDirection(StringBuilder sb, int row, int column,
                                                          char[] wordCharArray, int q) {
        int lenBefore = sb.length();
        sb = checkOneDirection(row - 1, column, wordCharArray, q, sb);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkRightAndDownDirection(sb, row, column, wordCharArray, q);
    }

    private StringBuilder checkLeftAndUpAndDownDirection(StringBuilder sb, int row, int column,
                                                         char[] wordCharArray, int q) {
        int lenBefore = sb.length();
        sb = checkLeftAndUpDirection(sb, row, column, wordCharArray, q);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkOneDirection(row + 1, column, wordCharArray, q, sb);
    }

    private StringBuilder checkAllDirection(StringBuilder sb, int row, int column,
                                            char[] wordCharArray, int q) {
        int lenBefore = sb.length();
        sb = checkLeftAndUpDirection(sb, row, column, wordCharArray, q);
        if (sb.length() != lenBefore) {
            return sb;
        }
        return checkRightAndDownDirection(sb, row, column, wordCharArray, q);
    }

    private StringBuilder checkCeil(StringBuilder sb, int row, int column,
                                    char[] wordCharArray, int q) {
        if (q == wordCharArray.length) {
            return sb;
        }
        if (row == 0) {
            if (column == 0) {
                return checkRightAndDownDirection(sb, row, column, wordCharArray, q);
            }

            if (column == matrix.length - 1) {
                return checkLeftAndDownDirection(sb, row, column, wordCharArray, q);
            }

            return checkLeftAndRightAndDownDirection(sb, row, column, wordCharArray, q);
        }

        if (row == matrix.length - 1) {
            if (column == 0) {
                return checkUpAndRightDirection(sb, row, column, wordCharArray, q);
            }

            if (column == matrix.length - 1) {
                return checkLeftAndUpDirection(sb, row, column, wordCharArray, q);
            }
            return checkLeftAndUpAndRightDirection(sb, row, column, wordCharArray, q);
        }

        if (column == 0) {
            return checkUpAndRightAndDownDirection(sb, row, column, wordCharArray, q);
        }

        if (column == matrix.length - 1) {
            return checkLeftAndUpAndDownDirection(sb, row, column, wordCharArray, q);
        }

        return checkAllDirection(sb, row, column, wordCharArray, q);
    }
}
