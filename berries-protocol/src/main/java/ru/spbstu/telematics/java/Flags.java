package ru.spbstu.telematics.java;

public class Flags {
    private boolean aliceRaised;

    private boolean bobRaised;

    public synchronized boolean isOtherRaised(String name) {
        if (name.equals("A")) {
            return bobRaised;
        } else {
            return aliceRaised;
        }
    }

    public synchronized void raise(String name) {
        if (name.equals("A")) {
            aliceRaised = true;
        } else {
            bobRaised = true;
        }
    }

    public synchronized void lower(String name) {
        if (name.equals("A")) {
            aliceRaised = false;
        } else {
            bobRaised = false;
        }
    }
}
