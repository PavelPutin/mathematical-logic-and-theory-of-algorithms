package pavel_p_a.system_of_equations;

import pavel_p_a.matrix.Matrix;
import pavel_p_a.matrix.MatrixIndexException;
import pavel_p_a.matrix.SquareMatrix;

public class ArbitrarySystemOfEquations implements SystemOfEquations{

    private SquareMatrix coefficientsMatrix;
    private Matrix freeTermMatrix;

    public ArbitrarySystemOfEquations(SquareMatrix coefficientsMatrix, Matrix freeTermMatrix) {
        this.coefficientsMatrix = coefficientsMatrix;
        this.freeTermMatrix = freeTermMatrix;
    }

    @Override
    public SquareMatrix getCoefficientsMatrix() {
        return coefficientsMatrix;
    }

    @Override
    public Matrix getFreeTermMatrix() {
        return freeTermMatrix;
    }
}
