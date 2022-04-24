package pavel_p_a.system_of_equations;

public class CantSolveException extends Exception {
    public CantSolveException() {
        super();
    }

    public CantSolveException(String message) {
        super(message);
    }
}
