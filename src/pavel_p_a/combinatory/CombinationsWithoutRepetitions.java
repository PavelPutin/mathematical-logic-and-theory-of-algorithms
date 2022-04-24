package pavel_p_a.combinatory;

import util.ArrayUtils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;

public class CombinationsWithoutRepetitions implements Iterable<Integer[]> {

    private int n, k;
    BigInteger count, totalQuantity;
    private Integer[] currentCombination;

    public CombinationsWithoutRepetitions(int n, int k) {
        this.n = n;
        this.k = k;
        this.count = BigInteger.ZERO;
        BigInteger fn = getFactorialIteratively(BigInteger.valueOf(n));
        BigInteger fnsk = getFactorialIteratively(BigInteger.valueOf(n - k));
        BigInteger fk = getFactorialIteratively(BigInteger.valueOf(k));
        this.totalQuantity = fn.divide(fnsk.multiply(fk));
        this.currentCombination = new Integer[k];
        for (int i = 0; i < k; i++) {
            currentCombination[i] = i;
        }
    }

    private BigInteger getFactorialIteratively(BigInteger input) {
        if (input.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("negatives are not allowed");
        }

        BigInteger result = BigInteger.ONE;
        for (BigInteger i = BigInteger.ONE; i.compareTo(input) <= 0; i = i.add(BigInteger.ONE)) {
            result = result.multiply(i);
        }
        return result;
    }

    @Override
    public Iterator<Integer[]> iterator() {
        return new Iterator<Integer[]>() {
            @Override
            public boolean hasNext() {
                return count.compareTo(totalQuantity) < 0;
            }

            @Override
            public Integer[] next() {
                Integer[] result = Arrays.copyOf(currentCombination, k);

                int increaseIndex = k - 1;
                boolean increased = false;
                do {
                    if (currentCombination[increaseIndex] + 1 <= n - k + increaseIndex) {
                        currentCombination[increaseIndex]++;
                        increased = true;
                    } else {
                        increaseIndex--;
                    }
                } while (increaseIndex >= 0 && !increased);

                for (int i = increaseIndex + 1; i < k && increaseIndex >= 0; i++) {
                    currentCombination[i] = currentCombination[i - 1] + 1;
                }
                count = count.add(BigInteger.ONE);
                return result;
            }
        };
    }
}
