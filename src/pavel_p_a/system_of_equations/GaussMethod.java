package pavel_p_a.system_of_equations;

import pavel_p_a.combinatory.CombinationsWithoutRepetitions;
import pavel_p_a.matrix.MatrixCalculator;
import util.ArrayUtils;

import java.util.Arrays;

public class GaussMethod {
    public static double[][] solve(double[][] coefficients, double[] freeTerms, int resultsQuantity) throws Exception {
        if (!MatrixCalculator.isMatrix(coefficients)) {
            throw new Exception("Передана не матрица!");
        }

        if (coefficients.length != freeTerms.length) {
            throw new Exception("Не совпадает количество свободных членов и уравнений!");
        }

        /*
        Прямой ход: приводим расширенную матрицу и матрицу коэффициентов к ступенчатому виду.
         */
        double[][] stairExtendedMatrix = MatrixCalculator.toStair(
                MatrixCalculator.insertCol(coefficients, freeTerms)
        );

        double[][] stairCoefficients = new double[coefficients.length][coefficients[0].length];
        for (int i = 0; i < coefficients.length; i++) {
            for (int j = 0; j < coefficients[0].length; j++) {
                stairCoefficients[i][j] = stairExtendedMatrix[i][j];
            }
        }

        // Определяем ранги матрицы коэффициентов
        int rankExtended = MatrixCalculator.calcRank(stairExtendedMatrix),
                rankStairCoefficients = MatrixCalculator.calcRank(stairCoefficients);

        // система несовместна
        if (rankExtended > rankStairCoefficients) {
            return new double[0][0];
        }

        // система совместна и определена
        if (rankStairCoefficients == coefficients[0].length) {
            double[][] result = new double[1][stairExtendedMatrix[0].length - 1];
            for (int r = stairExtendedMatrix.length - 1; r >= 0; r--) {
                if (MatrixCalculator.isZeroRow(stairExtendedMatrix, r)) {
                    continue;
                }
                int freeTermIndex = stairExtendedMatrix[0].length - 1;
                result[0][r] = stairExtendedMatrix[r][freeTermIndex];

                for (int c = freeTermIndex - 1, k = result[0].length - 1; c > r; c--, k--) {
                    result[0][r] -= stairExtendedMatrix[r][c] * result[0][k];
                }

                result[0][r] /= stairExtendedMatrix[r][r];
            }
            return result;
        }

        // система совместна и определена, но ранг равен нулю
        if (rankStairCoefficients == rankExtended && rankStairCoefficients == 0) {
            double[][] result = new double[1][stairCoefficients[0].length];
            Arrays.fill(result[0], 0);
            return result;
        }

        // система совместна, но не определена
        double[][] coefficientsOfUnknownVariables = new double[rankStairCoefficients][rankStairCoefficients];
        int[] rowsIndexes = new int[rankStairCoefficients];
        int[] columnsIndexes = new int[rankStairCoefficients];

        for (int i = 0; i < rankStairCoefficients; i++) {
            rowsIndexes[i] = i;
        }

        // Выбираем базисный минор. Элементы, входящие в этот минор являются базисными неизвестными
        for (Integer[] IntegerColumnsIndexes : new CombinationsWithoutRepetitions(coefficients[0].length, rankStairCoefficients)) {
            columnsIndexes = ArrayUtils.toPrimitive(IntegerColumnsIndexes);
            double determinant = MatrixCalculator.calcMinor(stairCoefficients, rowsIndexes, columnsIndexes);
            if (determinant != 0) {
                for (int r = 0; r < rankStairCoefficients; r++) {
                    int col = 0;
                    for (int c = 0; c < coefficients[0].length; c++) {
                        for (int x : columnsIndexes) {
                            if (x == c) {
                                coefficientsOfUnknownVariables[r][col] = stairCoefficients[r][c];
                                col++;
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }

        int freeVariablesNumber = stairCoefficients[0].length - rankStairCoefficients;
        int realResultsQuantity = Math.min(freeVariablesNumber, resultsQuantity);
        // определяем свободные члены, с учётом свободных переменных

        int[] freeVariableIndexes = new int[realResultsQuantity];
        int index = 0;
        for (int i = 0; i < stairCoefficients[0].length; i++) {
            boolean matched = false;
            for (int x : columnsIndexes) {
                if (x == i) {
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                freeVariableIndexes[index++] = i;
            }
        }

        // Решаем уже определённую систему
        double[][] result = new double[realResultsQuantity][coefficients[0].length];
        for (double[] doubles : result) {
            Arrays.fill(doubles, 0);
        }

        for (int i = 0; i < freeVariableIndexes.length; i++) {
            double[] newFreeTerms = new double[rankStairCoefficients];
            for (int j = 0; j < newFreeTerms.length; j++) {
                int lastElementInRowIndex = stairExtendedMatrix[0].length - 1;
                double freeTerm = stairExtendedMatrix[j][lastElementInRowIndex];
                double freeVariableCoefficient = stairExtendedMatrix[j][freeVariableIndexes[i]];
                newFreeTerms[j] = freeTerm - freeVariableCoefficient;
            }

            double[][] intermediateResult = GaussMethod.solve(coefficientsOfUnknownVariables, newFreeTerms, 1);
            int c = 0;
            for (int x : columnsIndexes) {
                result[i][x] = intermediateResult[0][c++];
            }

            result[i][freeVariableIndexes[i]] = 1;
        }

        return result;
    }
}
