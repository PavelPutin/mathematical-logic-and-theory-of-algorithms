package Gateway;

import pavel_p_a.matrix.MatrixCalculator;
import pavel_p_a.system_of_equations.GaussMethod;

import java.util.*;

public class GaussEntryPoint {
    /**
     * Ответ java "сервера"
     */
    public static class Response { // выходные данные напиши такие, которые будут тебе удобны
        private Map<Double, List<List<Double>>> eVectors;

        // можно опциально передать: код выполнения/текст ошибки

        public Response(Map<Double, List<List<Double>>> eVectors) {
            this.eVectors = eVectors;
        }

        public Map<Double, List<List<Double>>> getEVectors() {
            return eVectors;
        }
    }

    /**
     * Основная функция, вызываемая python клиентом
     *
     * @param matrix
     * @param vals   собственные значения и их кратности
     */
    public Response getResponseForMatrix(Double[][] matrix, Map<Double, Integer> vals) throws Exception {
        // тут вызов гаусса и создание класса ответа
        Map<Double, List<List<Double>>> eVectors = new TreeMap<>();
        for (Double val : vals.keySet()) {
            int number = vals.get(val);
            double[][] nm = new double[matrix.length][matrix[0].length];
            for (int r = 0; r < matrix.length; r++) {
                for (int c = 0; c < matrix[0].length; c++) {
                    nm[r][c] = (r == c) ? matrix[r][c] - val : matrix[r][c];
                }
            }
            int rank = MatrixCalculator.calcRank(nm);
            int s = 1;
            if (number > 1) {
                s = nm.length - rank;
            }
            double[] ft = new double[nm.length];
            double[][] result = GaussMethod.solve(nm, ft, s);

            eVectors.put(val, new ArrayList<>());
            for (int i = 0; i < s; i++) {
                List<Double> vector = new ArrayList<>();
                for (int j = 0; j < result[0].length; j++) {
                    vector.add(result[i][j]);
                }
                eVectors.get(val).add(vector);
            }
        }
        Response response = new Response(eVectors);

        return response;
    }
}
