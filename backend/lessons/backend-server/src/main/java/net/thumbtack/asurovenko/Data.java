package net.thumbtack.asurovenko;

import java.util.ArrayList;
import java.util.List;

public class Data {
    //REVU: it would be better to have Map of id and TodoTask rather than list
    private static List<TodoTask> todoTaskList = new ArrayList<>();
    //REVU: why do you need count? List class already has size method
    private static int countTodoTaskList=0;

    public static List<TodoTask> getTodoTaskList() {
        return todoTaskList;
    }

    public static int getCountTodoTaskList() {
        return countTodoTaskList;
    }

    public static void setCountTodoTaskList(int countTodoTaskList) {
        Data.countTodoTaskList = countTodoTaskList;
    }
}
