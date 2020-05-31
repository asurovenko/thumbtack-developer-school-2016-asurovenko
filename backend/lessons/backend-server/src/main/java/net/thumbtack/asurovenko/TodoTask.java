package net.thumbtack.asurovenko;

public class TodoTask {
    private int id;
    private String description;
    private boolean isDone;

    public TodoTask(String description, boolean isDone) {
        this.description=description;
        this.isDone=isDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
