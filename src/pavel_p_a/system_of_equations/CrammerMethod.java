package pavel_p_a.system_of_equations;

import pavel_p_a.matrix.MatrixCalculator;

public class CrammerMethod {
    public static double[][] solve(double[][] coefficients, double[] freeTerms) throws Exception {
        if (!MatrixCalculator.isSquareMatrix(coefficients)) {
            throw new Exception("Метод Крамера применим только тогда, когда число неизвестных равно числу уравнений!");
        }

        if (coefficients.length != freeTerms.length) {
            throw new Exception("Не совпадает количество свободных членов и уравнений!");
        }

        double delta = MatrixCalculator.calcDeterminant(coefficients);

        if (delta == 0) {
            throw new CantSolveException("Определитель матрицы коэффициентов равен нулю. Невозможно решить методом Крамера!");
        }

        double[][] results = new double[1][coefficients.length];
        for (int i = 0; i < results[0].length; i++) {
            results[0][i] = MatrixCalculator.calcDeterminant(
                    MatrixCalculator.replaceColumn(coefficients, freeTerms, i)
            ) / delta;
        }
        return results;
    }
}
