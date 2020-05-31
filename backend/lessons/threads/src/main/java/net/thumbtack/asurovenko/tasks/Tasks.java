package net.thumbtack.asurovenko.tasks;


import net.thumbtack.asurovenko.tasks.task10.MyReentrantLockList;
import net.thumbtack.asurovenko.tasks.task11.ConditionalPingPong;
import net.thumbtack.asurovenko.tasks.task12.MyConcurrentHashMap;
import net.thumbtack.asurovenko.tasks.task13.Formatter;
import net.thumbtack.asurovenko.tasks.task13.FormatterThread;
import net.thumbtack.asurovenko.tasks.task14.Message;
import net.thumbtack.asurovenko.tasks.task14.Transport;
import net.thumbtack.asurovenko.tasks.task14.TransportTask;
import net.thumbtack.asurovenko.tasks.task15.Data;
import net.thumbtack.asurovenko.tasks.task15.ReaderThread;
import net.thumbtack.asurovenko.tasks.task15.WriterThread;
import net.thumbtack.asurovenko.tasks.task16.ConsumerMyTask;
import net.thumbtack.asurovenko.tasks.task16.ProducerMyTask;
import net.thumbtack.asurovenko.tasks.task16.TaskBuffer;
import net.thumbtack.asurovenko.tasks.task3.Task3Thread;
import net.thumbtack.asurovenko.tasks.task4.MyList;
import net.thumbtack.asurovenko.tasks.task4.Task4ThreadA;
import net.thumbtack.asurovenko.tasks.task4.Task4ThreadB;
import net.thumbtack.asurovenko.tasks.task5.MySinchronizedList;
import net.thumbtack.asurovenko.tasks.task5.Task5ThreadA;
import net.thumbtack.asurovenko.tasks.task5.Task5ThreadB;
import net.thumbtack.asurovenko.tasks.task7.PingPong;
import net.thumbtack.asurovenko.tasks.task8.ReaderWriterSystem;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Tasks {
    // task 1
    public void printInformationThread(Thread thread) {
        System.out.println(thread);
    }

    // task 2
    public void startNewThreandAndWait() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.println("Start thread");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted");
                }
                System.out.println("Finish thread");
            }
        });
        thread.start();
        try {
            System.out.println("Main thread waited");
            thread.join();
            System.out.println("Continue Main thread");
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }

    // task 3
    public void startThreeThreadAndWait() {
        Task3Thread t1 = new Task3Thread("A");
        Task3Thread t2 = new Task3Thread("B");
        Task3Thread t3 = new Task3Thread("C");
        try {
            t1.getThread().join();
            t2.getThread().join();
            t3.getThread().join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }

    // task 4
    public void listIntegerPutAndRemove() {
        MyList myList = new MyList();
        new Task4ThreadA(myList);
        new Task4ThreadB(myList);
    }

    // task 5
    public void synchronizedListIntegerPutAndRemove() {
        MySinchronizedList mySinchronizedList = new MySinchronizedList();
        new Task5ThreadA(mySinchronizedList);
        new Task5ThreadB(mySinchronizedList);
    }

    // task 6
    public void javaSynchronizedList() {
        final List list = Collections.synchronizedList(new ArrayList());
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    System.out.println("Put " + i);
                    list.add(i);
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    if (!list.isEmpty()) {
                        System.out.println("Removed");
                        list.remove((int) (Math.random() * list.size()));
                    } else {
                        System.out.println("Empty list");
                    }
                }
            }
        }).start();
    }

    // task 7
    public void pingPong() {
        final PingPong pingPong = new PingPong();
        new Thread(new Runnable() {
            public void run() {
                for (; ; ) {
                    pingPong.ping();
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                for (; ; ) {
                    pingPong.pong();
                }
            }
        }).start();
    }

    // task 8
    public void readerWriterSystem() {
        final ReaderWriterSystem system = new ReaderWriterSystem();
        Runnable writeTask = new Runnable() {
            public void run() {
                system.write();
            }
        };
        Runnable readTask = new Runnable() {
            public void run() {
                system.read();
            }
        };

        for (int i = 0; i < 3; i++) {
            new Thread(readTask).start();
            new Thread(writeTask).start();
            new Thread(readTask).start();
            new Thread(writeTask).start();
            new Thread(readTask).start();
            new Thread(writeTask).start();
        }
    }

    // task 10
    public void reentrantLockList() {
        final MyReentrantLockList lockList = new MyReentrantLockList();
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    lockList.put(i);
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    lockList.randomRemove();
                }
            }
        }).start();
    }

    // task 11
    public void pingPongCondition() {
        final ConditionalPingPong pingPong = new ConditionalPingPong();
        new Thread(new Runnable() {
            public void run() {
                for (; ; ) {
                    pingPong.ping();
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                for (; ; ) {
                    pingPong.pong();
                }
            }
        }).start();
    }

    // task 13
    public void formatterDate() {
        final Formatter formatter = new Formatter();
        for (int i = 0; i < 5; i++) {
            new FormatterThread(formatter, "thread" + (i + 1));
        }
    }

    // task 14
    public void sendingMessages() {
        final String bodyText = "TEXT";
        final Transport transport = new Transport();
        ExecutorService service = Executors.newFixedThreadPool(3);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("addresses.txt"), StandardCharsets.UTF_8))) {
            String line;
            Message message;
            while ((line = reader.readLine()) != null) {
                message = new Message(line, bodyText);
                service.submit(new TransportTask(transport, message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }

    // task 16
    public void taskQueue() {
        TaskBuffer buffer = new TaskBuffer();
        Scanner console = new Scanner(System.in);
        System.out.print("Number of producer = ");
        int numberProducer = console.nextInt();
        System.out.print("Number of consumer = ");
        int numberConsumer = console.nextInt();

        for (int i = 0; i < numberProducer; i++) {
            new ProducerMyTask("Producer" + i, buffer);
        }

        for (int i = 0; i < numberConsumer; i++) {
            new ConsumerMyTask(buffer);
        }
    }

    // task 12
    public void myConcurrentHashMap() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        final MyConcurrentHashMap hashMap = new MyConcurrentHashMap(lock);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    hashMap.put(i, String.valueOf(i));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 100; i < 200; i++) {
                    hashMap.put(i, String.valueOf(i));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(hashMap.get(i));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 100; i < 200; i++) {
                    System.out.println(hashMap.get(i));
                }
            }
        }).start();
    }

    // task 15
    public void dataQueue() {
        Scanner console = new Scanner(System.in);
        System.out.print("Number of producer = ");
        int numberProducer = console.nextInt();
        System.out.print("Number of consumer = ");
        int numberConsumer = console.nextInt();
        BlockingQueue<Data> queue = new LinkedBlockingQueue<>();
        for (int i = 0; i < numberProducer; i++) {
            new WriterThread(queue);
        }
        for (int i = 0; i < numberConsumer; i++) {
            new ReaderThread(queue);
        }
    }
}
