package ru.spbstu.telematics.java;

import java.util.Random;

public class BerriesField {
    public static final int MAX_BERRIES_COLLECTED_ONCE = 100;
    public static final int ONE_BERRY_TIME_MS = 1;
    public static final int SIMULATION_TIME_MS = 5_000;
    private final Random r = new Random();

    public int harvest() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " started harvesting");
        int berriesCollected = r.nextInt(MAX_BERRIES_COLLECTED_ONCE);
        try {
            Thread.sleep(berriesCollected * ONE_BERRY_TIME_MS);
        } finally {
            System.out.println(Thread.currentThread().getName() + " finished harvesting");
        }
        return berriesCollected;
    }

    public static void main(String[] args) throws InterruptedException {
        BerriesField field = new BerriesField();
        Flags flags = new Flags();
        Neighbour[] neighbours = new Neighbour[] {
                new Neighbour(Flags.ALICE, flags, field),
                new Neighbour(Flags.BOB, flags, field) };
        neighbours[0].start();
        neighbours[1].start();
        Thread.sleep(SIMULATION_TIME_MS);
        neighbours[0].interrupt();
        neighbours[1].interrupt();
    }
}
