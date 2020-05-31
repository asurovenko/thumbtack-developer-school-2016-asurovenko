package net.thumbtack.asurovenko.tasks.task16;

import java.util.concurrent.Executor;

public class MyTask implements Executable {

    private String taskName;

    public MyTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void execute() {
        System.out.println("OK(" + taskName + ")");
    }
}
