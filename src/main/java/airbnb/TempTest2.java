package airbnb;

public class TempTest2 {
    public static void main(String[] args) {
        new Thread() {
            public void run() {
                synchronized (Lock.class) {
                    System.out.println("new thread start!");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    new Danger();
                }
                System.out.println("new thread end!");
            }
        }.start();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        System.out.println(new Danger());
        System.out.println("DONE!");
    }
}

class Lock {
}

class Danger {
    static {
        System.out.println("clinit begin...");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        synchronized (Lock.class) {
            System.out.println("clinit done!");
        }
    }
}
