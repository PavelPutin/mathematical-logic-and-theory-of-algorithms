package pavel_p_a.task3;

import pavel_p_a.matrix.Matrix;
import pavel_p_a.matrix.SquareMatrix;
import pavel_p_a.system_of_equations.SquareSystemOfEquations;
import util.JTableUtils;
import util.SwingUtils;

import javax.swing.*;
import java.util.Locale;

public class FrameMain extends JFrame {
    private static final int DEFAULT_CELL_SIZE = 40;
    private static final int DEFAULT_XS_QUANTITY = 2;
    private static final int MIN_XS_QUANTITY = 2;
    private static final int MAX_XS_QUANTITY = 10;
    private static final SolutionMethod DEFAULT_SOLUTION_METHOD = SolutionMethod.INVERSE_MATRIX;

    private JTable tableCoefficients;
    private JTable tableFreeTerms;
    private JTable tableResultMatrix;
    private JTable tableResultCramer;
    private JButton buttonSolve;
    private JSpinner spinnerXsQuantity;
    private JPanel panelMain;
    private JLabel labelResult;
    private JRadioButton radioButtonInverseMatrix;
    private JRadioButton radioButtonCramerMatrix;
    private JRadioButton radioButtonGaussMatrix;

    private SolutionMethod solutionMethod;

    public FrameMain() {
        Locale.setDefault(Locale.ROOT);
        this.setTitle("Решение систем уравнений");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelMain);
        this.pack();

        JTableUtils.initJTableForArray(tableCoefficients, DEFAULT_CELL_SIZE, false, false, false, false);
        JTableUtils.initJTableForArray(tableFreeTerms, DEFAULT_CELL_SIZE, false, false, false, false);
        JTableUtils.initJTableForArray(tableResultMatrix, DEFAULT_CELL_SIZE, false, false, false, false);

        solutionMethod = DEFAULT_SOLUTION_METHOD;
        ButtonGroup solutionMethodsButtons = new ButtonGroup();

        solutionMethodsButtons.add(radioButtonInverseMatrix);
        radioButtonInverseMatrix.addActionListener(e -> solutionMethod = SolutionMethod.INVERSE_MATRIX);
        radioButtonInverseMatrix.setSelected(true);

        solutionMethodsButtons.add(radioButtonCramerMatrix);
        radioButtonCramerMatrix.addActionListener(e -> solutionMethod = SolutionMethod.CRAMER);

        solutionMethodsButtons.add(radioButtonGaussMatrix);
        radioButtonGaussMatrix.addActionListener(e -> solutionMethod = SolutionMethod.GAUSS);

        spinnerXsQuantity.setModel(new SpinnerNumberModel(DEFAULT_XS_QUANTITY, MIN_XS_QUANTITY, MAX_XS_QUANTITY, 1));
        updateTablesSize();
        this.pack();
        spinnerXsQuantity.addChangeListener(e -> updateTablesSize());
        buttonSolve.addActionListener(e -> {
            try {
                double[][] coefficients = JTableUtils.readDoubleMatrixFromJTable(tableCoefficients);
                double[][] freeTerms = JTableUtils.readDoubleMatrixFromJTable(tableFreeTerms);

                SquareSystemOfEquations soe = new SquareSystemOfEquations(new SquareMatrix(coefficients), new Matrix(freeTerms));

                Matrix result = switch(solutionMethod) {
                    case GAUSS -> null;
                    case CRAMER -> soe.getResultCramer();
                    case INVERSE_MATRIX -> soe.getResultInverseMatrix();
                };

                double[][] resultArray;
                String labelResultText = "Решение: ";
                if (result == null) {
                    labelResultText += "отсутствует";
                    tableResultMatrix.setVisible(false);
                } else {
                    resultArray = result.toArray();
                    tableResultMatrix.setVisible(true);
                    JTableUtils.writeArrayToJTable(tableResultMatrix, resultArray);
                }
                labelResult.setText(labelResultText);

            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });
    }

    private void updateTablesSize() {
        int spinnerValue = (Integer) spinnerXsQuantity.getValue();
        JTableUtils.resizeJTable(tableCoefficients, spinnerValue, spinnerValue);
        JTableUtils.resizeJTable(tableFreeTerms, spinnerValue, 1);
        JTableUtils.resizeJTable(tableResultMatrix, spinnerValue, 1);
    }
}
