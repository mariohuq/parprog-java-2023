package ru.spbstu.telematics.java;

import java.util.List;
import org.apache.commons.math3.primes.Primes;

public class App {

    public static List<Integer> primeFactors(int n) {
        return Primes.primeFactors(n);
    }

    public static void main(String[] args) {
    }
}
