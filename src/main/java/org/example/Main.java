package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Main {
    private static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) throws InterruptedException {
        // create new runnable object

        /*Thread thread = new Thread(() -> System.out.println("Current thread: " + Thread.currentThread().getName() +
                        " has priority: " + Thread.currentThread().getPriority()));

        thread.setName("Worker thread");
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("Current thread: " + Thread.currentThread().getName() + " before starting a new thread.");
        thread.start();
        System.out.println("Current thread: " + Thread.currentThread().getName() + " after starting a new thread.");

        Thread.sleep(10000);*/


        //////////////////////////////////////////////////////////////////

        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        for(Thread newThread : threads) {
            newThread.start();
        }

    }

    private static class Vault {
        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored){
            }
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {
        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = 0; guess < MAX_PASSWORD; guess++) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {
        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            for(int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                System.out.println(i);
            }
            System.out.println("Game over for hackers.");
            System.exit(0);
        }
    }
}