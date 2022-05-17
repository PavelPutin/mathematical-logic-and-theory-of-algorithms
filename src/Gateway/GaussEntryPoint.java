package Gateway;

import java.util.ArrayList;
import java.util.List;

public class GaussEntryPoint {
    public static class Response {
        private List<List<Double>> eVectors;

        public Response(List<List<Double>> eVectors) {
            this.eVectors = eVectors;
        }

        public List<List<Double>> getEVectors() {
            return eVectors;
        }
    }

    public Response getResponseForMatrix(Double[][] matrix, Double[] vals) {
        // тут вызов гаусса и создание класса ответа
        Response response = null; /* = new Response(...);*/

        return response;
    }
}
