package pavel_p_a.matrix;

import util.ArrayUtils;

import java.util.Arrays;

public class MatrixCalculator {

    private static final double EPS = 1E-12;

    public static double calcDeterminant(double[][] matrix) throws Exception {
        if (!isSquareMatrix(matrix)) {
            throw new Exception("Детерминант можно посчитать только для квадратной матрицы");
        }

        if (matrix.length == 1) {
            return matrix[0][0];
        }

        double determinant = 0;
        for (int i = 0; i < matrix.length; i++) {
            determinant += matrix[0][i] * calcAlgebraicAdjunct(matrix, 0, i);
        }

        return determinant;
    }

    public static double calcMinor(double[][] matrix, int i, int j) throws Exception {
        if (!isSquareMatrix(matrix)) {
            throw new Exception("Минор по элементу можно рассчитать только для квадратной матрицы");
        }
        int[] rowsIndexes = new int[matrix.length - 1];
        int p = 0;
        for (int r = 0; r < matrix.length; r++) {
            if (r != i) {
                rowsIndexes[p++] = r;
            }
        }

        int[] colsIndexes = new int[matrix.length - 1];
        p = 0;
        for (int c = 0; c < matrix.length; c++) {
            if (c != j) {
                colsIndexes[p++] = c;
            }
        }

        return calcMinor(matrix, rowsIndexes, colsIndexes);
    }

    public static double calcMinor(double[][] matrix, int[] rowsIndexes, int[] colsIndexes) throws Exception {
        if (!isMatrix(matrix)) {
            throw new Exception("Передана не матрица!");
        }

        if (rowsIndexes.length != colsIndexes.length) {
            throw new Exception("Для расчёта минора количество выбранных строк и столбцов должно совпадать!");
        }

        double[][] minorMatrix = new double[rowsIndexes.length][colsIndexes.length];
        int i = 0, j = 0;
        for (int row : rowsIndexes) {
            for (int col : colsIndexes) {
                minorMatrix[i][j++] = matrix[row][col];
            }
            i++;
            j = 0;
        }
        return calcDeterminant(minorMatrix);
    }

    public static double calcAlgebraicAdjunct(double[][] matrix, int i, int j) throws Exception {
        if (!isSquareMatrix(matrix)) {
            throw new Exception("Алгебраическое дополнение можно рассчитать только для квадратной матрицы!");
        }

        return (((i + j) & 1) == 0 ? 1 : -1) * calcMinor(matrix, i, j);
    }

    public static double[][] multiplyRowByNumber(double[][] matrix, int targetRow, double lambda) throws Exception {
        if (!isMatrix(matrix)) {
            throw new Exception("Передана не матрица!");
        }

        int rowsNumber = matrix.length, colsNumber = matrix[0].length;
        double[][] newMatrix = new double[rowsNumber][colsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < colsNumber; j++) {
                newMatrix[i][j] = matrix[i][j] * (i == targetRow ? lambda : 1);
            }
        }
        return newMatrix;
    }

    public static double[][] multiplyColByNumber(double[][] matrix, int targetCol, double lambda) throws Exception {
        if (!isMatrix(matrix)) {
            throw new Exception("Передана не матрица!");
        }

        int rowsNumber = matrix.length, colsNumber = matrix[0].length;
        double[][] newMatrix = new double[rowsNumber][colsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < colsNumber; j++) {
                newMatrix[i][j] = matrix[i][j] * (j == targetCol ? lambda : 1);
            }
        }
        return newMatrix;
    }

    public static double[][] swapRows(double[][] matrix, int row1, int row2) throws Exception {
        if (!isMatrix(matrix)) {
            throw new Exception("Передана не матрица!");
        }

        double[][] newMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i == row1) {
                    newMatrix[i][j] = matrix[row2][j];
                } else if (i == row2) {
                    newMatrix[i][j] = matrix[row1][j];
                } else {
                    newMatrix[i][j] = matrix[i][j];
                }
            }
        }
        return newMatrix;
    }

    public static double[][] swapCols(double[][] matrix, int col1, int col2) throws Exception {
        if (!isMatrix(matrix)) {
            throw new Exception("Передана не матрица!");
        }

        double[][] newMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (j == col1) {
                    newMatrix[i][j] = matrix[i][col2];
                } else if (j == col2) {
                    newMatrix[i][j] = matrix[i][col1];
                } else {
                    newMatrix[i][j] = matrix[i][j];
                }
            }
        }
        return newMatrix;
    }

    public static double[][] insertCol(double[][] matrix, double[] col, int insertionIndex) throws Exception {
        if (!isMatrix(matrix)) {
            throw new Exception("Передана не матрица!");
        }

        if (matrix.length != col.length) {
            throw new Exception("Ошибка размерностей матрицы и столбца");
        }

        int newMatrixRowsNumber = matrix.length,
                newMatrixColsNumber = matrix[0].length + 1;
        double[][] newMatrix = new double[newMatrixRowsNumber][newMatrixColsNumber];
        boolean inserted = false;
        for (int i = 0; i < newMatrixRowsNumber; i++) {
            for (int j = 0; j < newMatrixColsNumber; j++) {
                if (j == insertionIndex) {
                    newMatrix[i][j] = col[i];
                    inserted = true;
                } else {
                    newMatrix[i][j] = matrix[i][j - (inserted ? 1 : 0)];
                }
            }
            inserted = false;
        }
        return newMatrix;
    }

    public static double[][] insertCol(double[][] matrix, double[] col) throws Exception {
        return insertCol(matrix, col, matrix[0].length);
    }


    public static double[][] replaceColumn(double[][] matrix, double[] col, int replacementIndex) throws Exception {
        if (!isMatrix(matrix)) {
            throw new Exception("Передана не матрица!");
        }

        if (matrix.length != col.length) {
            throw new Exception("Ошибка размерностей матрицы и столбца");
        }

        int newMatrixRowsNumber = matrix.length,
                newMatrixColsNumber = matrix[0].length;
        double[][] newMatrix = new double[newMatrixRowsNumber][newMatrixColsNumber];
        for (int i = 0; i < newMatrixRowsNumber; i++) {
            for (int j = 0; j < newMatrixColsNumber; j++) {
                newMatrix[i][j] = j == replacementIndex ? col[i] : matrix[i][j];
            }
        }
        return newMatrix;
    }

    public static double[][] multiplyByNumber(double[][] matrix, double lambda) throws Exception {
        if (!isMatrix(matrix)) {
            throw new Exception("Передана не матрица!");
        }

        int rowsNumber = matrix.length, colsNumber = matrix[0].length;
        double[][] newMatrix = new double[rowsNumber][colsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < colsNumber; j++) {
                newMatrix[i][j] = matrix[i][j] * lambda;
            }
        }
        return newMatrix;
    }

    public static double[][] multiply(double[][] matrixA, double[][] matrixB) throws Exception {
        if (!isMatrix(matrixA)) {
            throw new Exception("Первый операнд не матрица!");
        }

        if (!isMatrix(matrixB)) {
            throw new Exception("Второй операнд не матрица!");
        }

        int rowsNumberA = matrixA.length, colsNumberA = matrixA[0].length,
                rowsNumberB = matrixB.length, colsNumberB = matrixB[0].length;

        if (colsNumberA != rowsNumberB) {
            throw new Exception("Размерности матриц не позволяют их умножить");
        }

        double[][] newMatrix = new double[rowsNumberA][colsNumberB];
        for (int i = 0; i < rowsNumberA; i++) {
            for (int j = 0; j < colsNumberB; j++) {
                newMatrix[i][j] = 0;
                for (int k = 0; k < colsNumberA; k++) {
                    newMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return newMatrix;
    }

    public static double[] multiply(double[][] matrixA, double[] vector) throws Exception {
        if (!isMatrix(matrixA)) {
            throw new Exception("Первый операнд не матрица!");
        }

        int rowsNumberA = matrixA.length, colsNumberA = matrixA[0].length,
                rowsNumberB = vector.length;

        if (colsNumberA != rowsNumberB) {
            throw new Exception("Размерности матриц не позволяют их умножить");
        }

        double[] newMatrix = new double[rowsNumberA];
        for (int i = 0; i < rowsNumberA; i++) {
            newMatrix[i] = 0;
            for (int k = 0; k < colsNumberA; k++) {
                newMatrix[i] += matrixA[i][k] * vector[k];
            }
        }
        return newMatrix;
    }

    public static double[][] transpose(double[][] matrix) throws Exception {
        if (!isSquareMatrix(matrix)) {
            throw new Exception("Транспонировать можно только квадратные матрицы!");
        }

        double[][] newMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                newMatrix[i][j] = matrix[j][i];
            }
        }
        return newMatrix;
    }

    /**
     * Возвращает матрицу, обратную переданной.
     * @param matrix
     * @return
     * @throws Exception
     */
    public static double[][] getInverseMatrix(double[][] matrix) throws Exception {
        if (!isSquareMatrix(matrix)) {
            throw new Exception("Найти обратную матрицу можно только для квадратной матрицы!");
        }

        double determinant = calcDeterminant(matrix);
        if (determinant == 0) {
            throw new Exception("Определитель равен нулю. Найти обратную матрицу невозможно!");
        }

        double[][] inverseMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                inverseMatrix[i][j] = calcAlgebraicAdjunct(matrix, i, j);
            }
        }
        return multiplyByNumber(transpose(inverseMatrix), 1/determinant);
    }

    public static double[][] toStair(double[][] matrix) throws Exception {
        if (!isMatrix(matrix)) {
            throw new Exception("Передана не матрица!");
        }

        double[][] newMatrix = Arrays.copyOf(matrix, matrix.length);
        int col = 0, currentRow = 0;
        boolean foundNonZeroElement = false;

        while (col < matrix[0].length) {
            for (int r = currentRow; r < matrix.length; r++) {
                if (Math.abs(matrix[r][col] - 0) >= EPS) {
                    if (r != currentRow) {
                        newMatrix = MatrixCalculator.swapRows(newMatrix, currentRow, r);
                    }
                    foundNonZeroElement = true;
                    break;
                }
            }

            if (foundNonZeroElement) {
                for (int r = currentRow + 1; r < matrix.length; r++) {
                    double lambda = -(newMatrix[r][col] / newMatrix[currentRow][col]);
                    newMatrix = MatrixCalculator.addUpRows(newMatrix, currentRow, r, lambda);
                }
                currentRow++;
            }
            col++;
            foundNonZeroElement = false;
        }
        return newMatrix;
    }

    public static int calcRank(double[][] matrix) throws Exception {
        if (!isMatrix(matrix)) {
            throw new Exception("Передана не матрица!");
        }

        double[][] stairMatrix = toStair(matrix);
        int rank = 0;
        for (int r = 0; r < stairMatrix.length; r++) {
            if (!isZeroRow(stairMatrix, r)) {
                rank++;
            }
        }
        return rank;
    }

    public static boolean isZeroRow(double[][] matrix, int rowIndex) throws Exception {
        if (!isMatrix(matrix)) {
            throw new Exception("Передана не матрица!");
        }

        for (int c = 0; c < matrix[0].length; c++) {
            if (Math.abs(matrix[rowIndex][c] - 0) >= EPS) {
                return false;
            }
        }
        return true;
    }

    public static double[][] addUpRows(double[][] matrix, int rowIndex, int targetRowIndex, double lambda) throws Exception {
        if (!isMatrix(matrix)) {
            throw new Exception("Передана не матрица!");
        }

        double[][] newMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                newMatrix[i][j] = matrix[i][j] + ((i == targetRowIndex) ? lambda * matrix[rowIndex][j] : 0);
            }
        }
        return newMatrix;
    }

    public static boolean isMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length - 1; i++) {
            if (matrix[i].length != matrix[i + 1].length) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSquareMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            if (row.length != matrix.length) {
                return false;
            }
        }
        return true;
    }
}
