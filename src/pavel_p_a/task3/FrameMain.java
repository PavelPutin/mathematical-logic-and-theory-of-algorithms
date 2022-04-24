package pavel_p_a.task3;

import pavel_p_a.system_of_equations.CantSolveException;
import pavel_p_a.system_of_equations.CrammerMethod;
import pavel_p_a.system_of_equations.GaussMethod;
import pavel_p_a.system_of_equations.InverseMatrixMethod;
import util.JTableUtils;
import util.SwingUtils;

import javax.swing.*;
import java.util.Locale;

public class FrameMain extends JFrame {
    private static final int DEFAULT_CELL_SIZE = 40;
    private static final int DEFAULT_XS_QUANTITY = 2;
    private static final int MIN_XS_QUANTITY = 2;
    private static final int MAX_XS_QUANTITY = 10;
    private static final int DEFAULT_ES_QUANTITY = 2;
    private static final int MIN_ES_QUANTITY = 2;
    private static final int MAX_ES_QUANTITY = 10;
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
    private JSpinner spinnerEquationsQuantity;
    private JPanel panelEquationsQuantity;

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
        radioButtonInverseMatrix.addActionListener(e -> {
            solutionMethod = SolutionMethod.INVERSE_MATRIX;
            panelEquationsQuantity.setVisible(false);
            spinnerEquationsQuantity.setValue(spinnerXsQuantity.getValue());
            updateTablesSize();
        });
        radioButtonInverseMatrix.setSelected(true);

        solutionMethodsButtons.add(radioButtonCramerMatrix);
        radioButtonCramerMatrix.addActionListener(e -> {
            solutionMethod = SolutionMethod.CRAMER;
            panelEquationsQuantity.setVisible(false);
            spinnerEquationsQuantity.setValue(spinnerXsQuantity.getValue());
            updateTablesSize();
        });

        solutionMethodsButtons.add(radioButtonGaussMatrix);
        radioButtonGaussMatrix.addActionListener(e -> {
            solutionMethod = SolutionMethod.GAUSS;
            panelEquationsQuantity.setVisible(true);
            updateTablesSize();
        });

        spinnerXsQuantity.setModel(new SpinnerNumberModel(DEFAULT_XS_QUANTITY, MIN_XS_QUANTITY, MAX_XS_QUANTITY, 1));
        spinnerEquationsQuantity.setModel(new SpinnerNumberModel(DEFAULT_ES_QUANTITY, MIN_ES_QUANTITY, MAX_ES_QUANTITY, 1));
        updateTablesSize();
        panelEquationsQuantity.setVisible(false);
        this.pack();
        spinnerXsQuantity.addChangeListener(e -> {
            if (!panelEquationsQuantity.isVisible()) {
                spinnerEquationsQuantity.setValue(spinnerXsQuantity.getValue());
            }
            updateTablesSize();
        });
        spinnerEquationsQuantity.addChangeListener(e -> {
            if (panelEquationsQuantity.isVisible()) {
                updateTablesSize();
            }
        });
        buttonSolve.addActionListener(e -> {
            try {
                double[][] coefficients = JTableUtils.readDoubleMatrixFromJTable(tableCoefficients);
                double[] freeTerms = JTableUtils.readDoubleArrayFromJTable(tableFreeTerms);

                double[] result = switch(solutionMethod) {
                    case GAUSS -> GaussMethod.solve(coefficients, freeTerms);
                    case CRAMER -> CrammerMethod.solve(coefficients, freeTerms);
                    case INVERSE_MATRIX -> InverseMatrixMethod.solve(coefficients, freeTerms);
                };

                tableResultMatrix.setVisible(true);
                JTableUtils.writeArrayToJTable(tableResultMatrix, result);
                labelResult.setText("Решение:" + (result.length == 0 ? " отсутствует" : ""));

            } catch (CantSolveException ex) {
                labelResult.setText(ex.getMessage());
                JTableUtils.writeArrayToJTable(tableResultMatrix, new double[0]);
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });
    }

    private void updateTablesSize() {
        int xsQuantityValue = (Integer) spinnerXsQuantity.getValue();
        int equationsQuantityValue = xsQuantityValue;
        if (spinnerEquationsQuantity.isVisible()) {
            equationsQuantityValue = (Integer) spinnerEquationsQuantity.getValue();
        }
        JTableUtils.resizeJTable(tableCoefficients, equationsQuantityValue, xsQuantityValue);
        JTableUtils.resizeJTable(tableFreeTerms, 1, equationsQuantityValue);
        JTableUtils.resizeJTable(tableResultMatrix, 1, xsQuantityValue);
    }
}
