package pavel_p_a.matrix;

import java.util.Arrays;

public class MatrixCalculator {
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

    public static boolean isSquareMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            if (row.length != matrix.length) {
                return false;
            }
        }
        return true;
    }
}
