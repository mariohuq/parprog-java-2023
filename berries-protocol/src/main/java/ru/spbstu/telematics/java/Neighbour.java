package ru.spbstu.telematics.java;

import java.util.Random;

public class Neighbour extends Thread {

    public static final int MAX_PAUSE_MS = 200;
    private final Flags flags;

    private final BerriesField field;

    private final Random random = new Random();

    private int berriesCollected;

    public Neighbour(String name, Flags flags, BerriesField field) {
        super(name);
        this.flags = flags;
        this.field = field;
    }

    @Override
    public void run() {
        String name = getName();
        while (!isInterrupted()) {
            try {
                Thread.sleep(random.nextInt(MAX_PAUSE_MS));
            } catch (InterruptedException e) {
                System.err.println(name + " was interrupted between flag raises");
                break;
            }
            flags.raise(name);
            if (flags.isOtherRaised(name)) {
                flags.lower(name);
                continue;
            }
            System.out.println(name + " enters field");
            try {
                berriesCollected += field.harvest();
            } catch (InterruptedException e) {
                System.err.println(name + " was interrupted while harvesting");
                break;
            } finally {
                System.out.println(name + " left field");
                flags.lower(name);
            }
        }
        System.out.println(name + " collected " + berriesCollected + " berries.");
    }
}
