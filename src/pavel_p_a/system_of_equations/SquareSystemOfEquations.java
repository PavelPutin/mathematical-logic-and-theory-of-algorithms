package pavel_p_a.system_of_equations;

import pavel_p_a.matrix.Matrix;
import pavel_p_a.matrix.MatrixException;
import pavel_p_a.matrix.SquareMatrix;

public class SquareSystemOfEquations extends ArbitrarySystemOfEquations implements SystemOfEquations {

    public SquareSystemOfEquations(SquareMatrix coefficientsMatrix, Matrix freeTermMatrix) {
        super(coefficientsMatrix, freeTermMatrix);
    }

    @Override
    public SquareMatrix getCoefficientsMatrix() {
        return super.getCoefficientsMatrix();
    }

    @Override
    public Matrix getFreeTermMatrix() {
        return super.getFreeTermMatrix();
    }

    public Matrix getResultCramer() throws Exception {
        double delta = getCoefficientsMatrix().getDet();
        if (delta == 0) {
            return null;
        }

        double[][] resultData = new double[getCoefficientsMatrix().getColumnsQuantity()][1];
        for (int columnIndex = 0; columnIndex < getCoefficientsMatrix().getColumnsQuantity(); columnIndex++) {
            resultData[columnIndex][0] = getCoefficientsMatrix().replaceColumn(columnIndex, getFreeTermMatrix()).getDet() / delta;
        }

        return new Matrix(resultData);
    }

    public Matrix getResultInverseMatrix() throws MatrixException {
        SquareMatrix inverseMatrix = getCoefficientsMatrix().getInverse();
        if (inverseMatrix == null) {
            return null;
        }
        return inverseMatrix.multiply(getFreeTermMatrix());
    }

    @Override
    public String toString() {
        return "";
    }
}
