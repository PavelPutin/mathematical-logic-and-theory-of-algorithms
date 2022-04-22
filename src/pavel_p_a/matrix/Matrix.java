package pavel_p_a.matrix;

public class Matrix implements MatrixInterface {
    private double[][] matrixData;

    public Matrix(double[][] matrixData) {
        int rowsNumber = matrixData.length;
        int columnsNumber = matrixData[0].length;

        this.matrixData = new double[rowsNumber][columnsNumber];

        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < columnsNumber; j++) {
                this.matrixData[i][j] = matrixData[i][j];
            }
        }
    }

    public double[][] getMatrixData() {
        return this.matrixData;
    }

    @Override
    public int getRowsQuantity() {
        return matrixData.length;
    }

    @Override
    public int getColumnsQuantity() {
        return matrixData[0].length;
    }


    @Override
    public void multiply(double value) {
        for (int i = 0; i < getRowsQuantity(); i++) {
            for (int j = 0; j < getColumnsQuantity(); j++) {
                matrixData[i][j] *= value;
            }
        }
    }

    @Override
    public Matrix multiply(Matrix matrix) throws MatrixMultiplyingException, MatrixIndexException {
        if (getColumnsQuantity() != matrix.getRowsQuantity()) {
            throw new MatrixMultiplyingException(String.format("Невозможно умножить матрицы %dx%d и %dx%d%n",
                    getRowsQuantity(), getColumnsQuantity(),
                    matrix.getRowsQuantity(), matrix.getColumnsQuantity()));
        }

        double[][] newMatrixData = new double[getRowsQuantity()][matrix.getColumnsQuantity()];
        for (int i = 0; i < getRowsQuantity(); i++) {
            for (int j = 0; j < matrix.getColumnsQuantity(); j++) {
                double value = 0;

                for (int k = 0; k < getColumnsQuantity(); k++) {
                    value += getElement(i, k) * matrix.getElement(k, j);
                }

                newMatrixData[i][j] = value;
            }
        }

        return new Matrix(newMatrixData);
    }

    @Override
    public Matrix replaceColumn(int column, Matrix vectorColumn) throws MatrixIndexException {
        double[][] newMatrixData = new double[getRowsQuantity()][getColumnsQuantity()];
        for (int i = 0; i < getRowsQuantity(); i++) {
            for (int j = 0; j < getColumnsQuantity(); j++) {
                newMatrixData[i][j] = (j == column) ? vectorColumn.getElement(i, 0) : matrixData[i][j];
            }
        }
        return new Matrix(newMatrixData);
    }

    @Override
    public int getRank() {

        return 0;
    }

    @Override
    public double getMinor(int[] rows, int[] columns) throws MatrixIndexException, MatrixMinorSizeException {
        checkMinorSizes(rows.length, columns.length);

        double[][] newMatrixData = new double[rows.length][columns.length];
        int curRow = 0;
        int curCol = 0;

        for (int row : rows) {
            for (int column : columns) {
                checkIndexBounds(row, column);
                newMatrixData[curRow][curCol++] = matrixData[row][column];
            }
            curRow++;
            curCol = 0;
        }

        SquareMatrix minorMatrix = new SquareMatrix(newMatrixData);
        return minorMatrix.getDet();
    }

    @Override
    public double getElement(int i, int j) throws MatrixIndexException {
        checkIndexBounds(i, j);
        return matrixData[i][j];
    }

    @Override
    public double[][] toArray() throws MatrixIndexException {
        double[][] result = new double[getRowsQuantity()][getColumnsQuantity()];
        for (int i = 0; i < getRowsQuantity(); i++) {
            for (int j = 0; j < getColumnsQuantity(); j++) {
                result[i][j] = getElement(i, j);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");

        for (int i = 0; i < matrixData[0].length; i++) {
            for (int j = 0; j < matrixData[0].length; j++) {
                result.append(j > 0 ? "\t" : "").append(String.format("%.3f", matrixData[i][j]));
            }
            result.append('\n');
        }

        return result.toString();
    }

    protected void checkIndexBounds(int i, int j) throws MatrixIndexException {
        if (i < 0 && getRowsQuantity() < i || j < 0 && getColumnsQuantity() < j) {
            throw new MatrixIndexException("Нет такого элемента");
        }
    }

    protected void checkMinorSizes(int rowsQuantity, int columnsQuantity) throws MatrixMinorSizeException {
        if (rowsQuantity != columnsQuantity) {
            throw new MatrixMinorSizeException("Количество выбранных строк и столбцов в миноре должно быть одинаковым");
        }
    }
}
