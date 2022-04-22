package pavel_p_a.matrix;

import java.util.Arrays;

public class SquareMatrix extends Matrix implements MatrixInterface {

    private static final int COLUMN_FOR_DET = 0;

    public SquareMatrix(double[][] matrixData) {
        super(Arrays.copyOf(matrixData, matrixData[0].length));
    }

    public int getSize() {
        return super.getMatrixData()[0].length;
    }

    public double getDet() throws MatrixIndexException {
        if (getSize() == 1) {
            return super.getMatrixData()[0][0];
        }
        else {
            double det = 0;

            for (int row = 0; row < getSize(); row++) {
                det += super.getMatrixData()[row][COLUMN_FOR_DET] * getAlgebraicAdjunct(row, COLUMN_FOR_DET);
            }

            return det;
        }
    }

    public double getMinor(int i, int j) throws MatrixIndexException {
        checkIndexBounds(i, j);
        double[][] newMatrixData = new double[getSize() - 1][getSize() - 1];
        int curRow = 0;
        int curCol = 0;

        for (int row = 0; row < getSize(); row++) {
            if (row == i) {
                continue;
            }

            for (int col = 0; col < getSize(); col++) {
                if (col == j) {
                    continue;
                }

                newMatrixData[curRow][curCol++] = super.getMatrixData()[row][col];
            }
            curRow++;
            curCol = 0;
        }

        SquareMatrix minorMatrix = new SquareMatrix(newMatrixData);
        return minorMatrix.getDet();
    }

    public double getAlgebraicAdjunct(int i, int j) throws MatrixIndexException {
        checkIndexBounds(i, j);
        return (Math.pow(-1, i + j + 2)) * getMinor(i, j);
    }

    public void transpose() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = i; j < getSize(); j++) {
                double temp = super.getMatrixData()[i][j];
                super.getMatrixData()[i][j] = super.getMatrixData()[j][i];
                super.getMatrixData()[j][i] = temp;
            }
        }
    }

    public SquareMatrix getInverse() throws MatrixIndexException {
        double det = getDet();
        if (det == 0) {
            return null;
        }

        double[][] algebraicAdjuncts = new double[getSize()][getSize()];

        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                algebraicAdjuncts[i][j] = getAlgebraicAdjunct(i, j);
            }
        }
        SquareMatrix inverseMatrix = new SquareMatrix(algebraicAdjuncts);
        inverseMatrix.transpose();

        inverseMatrix.multiply(1/det);
        return inverseMatrix;
    }

    public SquareMatrix replaceColumn(int column, Matrix vectorColumn) throws MatrixIndexException {
        double[][] newMatrixData = new double[getRowsQuantity()][getColumnsQuantity()];
        for (int i = 0; i < getRowsQuantity(); i++) {
            for (int j = 0; j < getColumnsQuantity(); j++) {
                newMatrixData[i][j] = (j == column) ? vectorColumn.getElement(i, 0) : super.getMatrixData()[i][j];
            }
        }
        return new SquareMatrix(newMatrixData);
    }
}
