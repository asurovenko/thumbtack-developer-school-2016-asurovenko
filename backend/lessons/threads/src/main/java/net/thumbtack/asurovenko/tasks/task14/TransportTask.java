package net.thumbtack.asurovenko.tasks.task14;

public class TransportTask implements Runnable {
    Transport transport;
    Message message;

    public TransportTask(Transport transport, Message message) {
        this.transport = transport;
        this.message = message;
    }

    public void run() {
        transport.send(message);
    }
}
