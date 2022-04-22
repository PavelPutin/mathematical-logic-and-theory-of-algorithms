package pavel_p_a.task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Path ROOT_DIRECTORY_PATH = Paths.get(new File("").getAbsolutePath());
    private static final Path SOURCE_DIRECTORY_PATH = ROOT_DIRECTORY_PATH.resolve("sourceFiles").resolve("task2");
    private static final Path SOURCE_FILE_PATH = SOURCE_DIRECTORY_PATH.resolve("source.txt");
    private static final Path RESULT_FILE_PATH = SOURCE_DIRECTORY_PATH.resolve("result.txt");

    public static void main(String[] args) throws Exception {
        int[] array = readArrayFromFile(SOURCE_FILE_PATH.toFile());
        String result = analyzeLinearSearch(array).toString();
        result += '\n' + analyzeBinarySearch(array).toString();
        result += '\n' + analyzeInterpolationSearch(array).toString();
        writeToFile(RESULT_FILE_PATH.toFile(), result);
    }

    private static Report analyzeLinearSearch(int[] array) throws Exception {
        Report report = new Report(SortName.LINEAR, array.length);

        for (int i : array) {
            int comparisons = getLinearSearchComparisons(array, i);
            report.addComparisons(comparisons);
        }

        return report;
    }

    private static int getLinearSearchComparisons(int[] array, int element) {
        int comparisonsNumber = 0;

        for (int i : array) {
            comparisonsNumber++;
            if (i == element) {
                break;
            }
        }

        return comparisonsNumber;
    }

    private static Report analyzeBinarySearch(int[] array) throws Exception {
        Report report = new Report(SortName.BINARY, array.length);
        Arrays.sort(array);

        for (int i : array) {
            int comparisons = getBinarySearchComparisons(array, i);
            report.addComparisons(comparisons);
        }

        return report;
    }

    private static int getBinarySearchComparisons(int[] array, int element) {
        int comparisonsNumber = 0;

        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (high + low) / 2;

            comparisonsNumber++;
            if (array[mid] == element) {
                break;
            } else if (array[mid] < element) {
                low = mid + 1;
            } else if (array[mid] > element) {
                high = mid - 1;
            }
        }

        return comparisonsNumber;
    }

    private static Report analyzeInterpolationSearch(int[] array) throws Exception {
        Report report = new Report(SortName.INTERPOLATION, array.length);
        Arrays.sort(array);

        for (int i : array) {
            int comparisons = getInterpolationSearchComparisons(array, i);
            report.addComparisons(comparisons);
        }

        return report;
    }

    private static int getInterpolationSearchComparisons(int[] array, int element) {
        int comparisonsNumber = 0;

        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            if (array[high] == array[low]) {
                break;
            }
            int mid = low + (element - array[low]) * (high - low) / (array[high] - array[low]);

            comparisonsNumber++;
            if (array[mid] == element) {
                break;
            } else if (array[mid] < element) {
                low = mid + 1;
            } else if (array[mid] > element) {
                high = mid - 1;
            }
        }

        comparisonsNumber++;
//        if (array[high] == element) {
//
//        }

        return comparisonsNumber;
    }

    private static int[] readArrayFromFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        List<Integer> list = new ArrayList<>();

        while (scanner.hasNext()) {
            list.add(scanner.nextInt());
        }

        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private static void writeToFile(File file, Object value) throws FileNotFoundException {
        PrintStream out = new PrintStream(file);
        out.println(value);
        out.close();
    }
}

