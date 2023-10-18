package ru.spbstu.telematics.java;

import java.util.List;
import java.util.Collections;
import org.apache.commons.math3.primes.Primes;

public class App {

    public static List<Integer> primeFactors(int n) {
        if (n == Integer.MIN_VALUE) {
            var result = primeFactors(n / 2);
            result.addFirst(2);
            return result;
        }
        if (n < 0) {
            return primeFactors(-n);
        }
        if (n < 2) {
            return Collections.emptyList();
        }
        return Primes.primeFactors(n);
    }
}
