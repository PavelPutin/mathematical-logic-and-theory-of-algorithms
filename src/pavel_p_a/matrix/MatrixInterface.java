package pavel_p_a.matrix;

public interface MatrixInterface {
    int getRowsQuantity();
    int getColumnsQuantity();
    void multiply(double value);
    Matrix multiply(Matrix matrix) throws Exception;
    double getElement(int i, int j) throws Exception;
    Matrix replaceColumn(int column, Matrix vectorColumn) throws Exception;
    int getRank();
    double getMinor(int[] rows, int[] columns) throws Exception;
    double[][] toArray() throws Exception;
}
