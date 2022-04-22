package pavel_p_a.task2;

public enum SortName {
    LINEAR("линейный поиск"),
    BINARY("двоичный поиск"),
    INTERPOLATION("интерполяционный поиск");

    public final String sortName;

    SortName(String sortName) {
        this.sortName = sortName;
    }
}
