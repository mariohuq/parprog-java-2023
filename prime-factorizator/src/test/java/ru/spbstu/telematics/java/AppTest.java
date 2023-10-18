package ru.spbstu.telematics.java;

import java.util.Collections;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import ru.spbstu.telematics.java.App;

import org.junit.Test;

public class AppTest {

    @Test
    public void testJunitWorking() {
        assertTrue(true);
    }

    @Test
    public void testPrimeFactors() {
        assertEquals("0 has no prime factors", Collections.emptyList(), App.primeFactors(0));
        assertEquals("1 has no prime factors", Collections.emptyList(), App.primeFactors(1));
        assertEquals("-1 has no prime factors", Collections.emptyList(), App.primeFactors(-1));
        assertEquals("2 is prime", Arrays.asList(2), App.primeFactors(2));
        assertEquals("4 has 2 prime factors", Arrays.asList(2, 2), App.primeFactors(4));
        assertEquals("many prime factors", Arrays.asList(2, 2, 2, 2, 3, 3, 5, 7, 11, 13), App.primeFactors(720720));
        assertEquals("works with negative numbers", Arrays.asList(2, 3), App.primeFactors(-6));
        assertEquals("maximum integer is prime", Arrays.asList(Integer.MAX_VALUE), App.primeFactors(Integer.MAX_VALUE));
        assertEquals("minimum integer is -2^31", Collections.nCopies(31, 2), App.primeFactors(Integer.MIN_VALUE));
    }
}
