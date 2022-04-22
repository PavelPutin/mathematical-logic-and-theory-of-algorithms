package pavel_p_a.system_of_equations;

import pavel_p_a.matrix.Matrix;

public interface SystemOfEquations {
    Matrix getCoefficientsMatrix();
    Matrix getFreeTermMatrix();
}
