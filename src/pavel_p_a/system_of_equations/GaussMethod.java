package pavel_p_a.system_of_equations;

import pavel_p_a.matrix.MatrixCalculator;
import util.ArrayUtils;

import java.util.Arrays;

public class GaussMethod {
    public static double[] solve(double[][] coefficients, double[] freeTerms) throws Exception {
        if (!MatrixCalculator.isMatrix(coefficients)) {
            throw new Exception("Передана не матрица!");
        }

        if (coefficients.length != freeTerms.length) {
            throw new Exception("Не совпадает количество свободных членов и уравнений!");
        }

        double[][] stairExtendedMatrix = MatrixCalculator.toStair(
                MatrixCalculator.insertCol(coefficients, freeTerms)
        );

        double[][] stairCoefficients = new double[coefficients.length][coefficients[0].length];
        for (int i = 0; i < coefficients.length; i++) {
            for (int j = 0; j < coefficients[0].length; j++) {
                stairCoefficients[i][j] = stairExtendedMatrix[i][j];
            }
        }

        int rankExtended = MatrixCalculator.calcRank(stairExtendedMatrix),
                rankStairCoefficients = MatrixCalculator.calcRank(stairCoefficients);

        if (rankExtended > rankStairCoefficients) {
            return new double[0];
        }

        if (rankStairCoefficients == coefficients[0].length) {
            double[] result = new double[stairExtendedMatrix[0].length - 1];
            for (int r = stairExtendedMatrix.length - 1; r >= 0; r--) {
                if (MatrixCalculator.isZeroRow(stairExtendedMatrix, r)) {
                    continue;
                }
                int freeTermIndex = stairExtendedMatrix[0].length - 1;
                result[r] = stairExtendedMatrix[r][freeTermIndex];

                for (int c = freeTermIndex - 1, k = result.length - 1; c > r; c--, k--) {
                    result[r] -= stairExtendedMatrix[r][c] * result[k];
                }

                result[r] /= stairExtendedMatrix[r][r];
            }

            return result;
        }

        return new double[0];
    }
}
