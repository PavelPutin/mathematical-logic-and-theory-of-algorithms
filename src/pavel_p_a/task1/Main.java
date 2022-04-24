package pavel_p_a.task1;

import pavel_p_a.matrix.MatrixCalculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final Path ROOT_DIRECTORY_PATH = Paths.get(new File("").getAbsolutePath());
    private static final Path SOURCE_DIRECTORY_PATH = ROOT_DIRECTORY_PATH.resolve("sourceFiles").resolve("task1");
    private static final Path SOURCE_FILE_PATH = SOURCE_DIRECTORY_PATH.resolve("source1.txt");
    private static final Path RESULT_FILE_PATH = SOURCE_DIRECTORY_PATH.resolve("result2.txt");

    /*
    * Решить систему уравнений методом Крамера и методом обратной матрицы
    * Сделать графический интерфейс
    * */

    public static void main(String[] args) throws Exception {
        double[][] matrixData = readSquareMatrixFromFile(SOURCE_FILE_PATH.toFile());

        long start = System.currentTimeMillis();

        double det = MatrixCalculator.calcDeterminant(matrixData);

        long finish = System.currentTimeMillis();
        System.out.printf("Затраченное время: %d %n", finish - start);
        // затрачено на 13х13 2027840 мс = 2027 с = 33 мин
        // затрачено на 10х10 1296 мс = 1.3 с
        double[][] inverse = MatrixCalculator.getInverseMatrix(matrixData);

        String result = "" + det + "\n";
        result += inverse + "\n\n";
        result += Arrays.toString(MatrixCalculator.multiply(matrixData, inverse));
        writeToFile(RESULT_FILE_PATH.toFile(), result);

    }

    public static double[][] readSquareMatrixFromFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        int size = scanner.nextInt();
        double[][] matrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        return matrix;
    }

    public static void writeToFile(File file, Object value) throws FileNotFoundException {
        PrintStream out = new PrintStream(file);
        out.println(value);
        out.close();
    }
}
