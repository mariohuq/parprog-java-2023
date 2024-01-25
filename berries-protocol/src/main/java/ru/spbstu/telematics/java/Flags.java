package ru.spbstu.telematics.java;

public class Flags {
    public static final String ALICE = "A";
    public static final String BOB = "B";
    private boolean aliceRaised;

    private boolean bobRaised;

    public synchronized boolean isOtherRaised(String name) {
        return switch (name) {
            case ALICE -> bobRaised;
            case BOB -> aliceRaised;
            default -> throw new IllegalArgumentException("No such a neigbour: " + name);
        };
    }

    public synchronized void raise(String name) {
        switch (name) {
            case ALICE -> aliceRaised = true;
            case BOB -> bobRaised = true;
        }
    }

    public synchronized void lower(String name) {
        switch (name) {
            case ALICE -> aliceRaised = false;
            case BOB -> bobRaised = false;
        }
    }
}
