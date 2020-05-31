package net.thumbtack.asurovenko.tasks.task14;

public class Transport {

    public void send(Message message) {
        try {
            Thread.sleep(500);  //sending
            System.out.println("Message sent to " + message.getEmailAddress());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
