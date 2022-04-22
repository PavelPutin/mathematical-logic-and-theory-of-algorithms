package pavel_p_a.task2;

public class Report {

    private final SortName sortName;
    private final int arrayLength;
    private int comparisonsNumber;

    public static class ReportException extends Exception {
        public ReportException(String message) {
            super(message);
        }
    }

    public Report(SortName sortName, int arrayLength, int comparisonsNumber) {
        this.sortName = sortName;
        this.arrayLength = arrayLength;
        this.comparisonsNumber = comparisonsNumber;
    }

    public Report(SortName sortName, int arrayLength) {
        this(sortName, arrayLength, 0);
    }

    public void addComparisons(int value) throws ReportException {
        if (value < 0) {
            throw new ReportException("Передано отрицательное значение");
        }
        this.comparisonsNumber += value;
    }

    public int getAverageComparisonsNumber() {
        return comparisonsNumber / arrayLength;
    }

    @Override
    public String toString() {
        return String.format("Тип сортировки: %s; Длина массива: %d; Среднее число сравнений: %d",
                sortName.sortName, arrayLength, getAverageComparisonsNumber());
    }
}
