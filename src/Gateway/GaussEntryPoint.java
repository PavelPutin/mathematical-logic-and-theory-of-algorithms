package Gateway;

import java.util.List;
import java.util.Map;

public class GaussEntryPoint {
    /**
     * Ответ java "сервера"
     */
    public static class Response { // выходные данные напиши такие, которые будут тебе удобны
        private List<List<Double>> eVectors;

        // можно опциально передать: код выполнения/текст ошибки

        public Response(List<List<Double>> eVectors) {
            this.eVectors = eVectors;
        }

        public List<List<Double>> getEVectors() {
            return eVectors;
        }
    }

    /**
     * Основная функция, вызываемая python клиентом
     *
     * @param matrix
     * @param vals   собственные значения и их кратности
     */
    public Response getResponseForMatrix(Double[][] matrix, Map<Double, Integer> vals) {
        // тут вызов гаусса и создание класса ответа
        Response response = null; /* = new Response(...);*/

        return response;
    }
}
