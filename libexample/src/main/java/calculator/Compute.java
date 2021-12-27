package calculator;

public class Compute implements ICompute {
    public Integer sum(Integer v1, Integer v2) {
        return v1 + v2;
    }

    public Integer version() {
        return 666;
    }
}
