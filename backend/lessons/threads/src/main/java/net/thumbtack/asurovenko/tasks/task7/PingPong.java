package net.thumbtack.asurovenko.tasks.task7;

import java.util.concurrent.Semaphore;

public class PingPong {
    Semaphore semPing = new Semaphore(0);
    Semaphore semPong = new Semaphore(1);

    public void ping() {
        try {
            semPing.acquire();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println("PING");
        semPong.release();
    }

    public void pong() {
        try {
            semPong.acquire();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println("Pong");
        semPing.release();
    }
}
