public class SampleApp {

public static void main(String[] args) {
	System.out.println("hello");

}

}
//==========================
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread loader = new Thread(() -> {
            System.out.println("Loading data...");
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println("Data loaded!");
        });

        loader.start();
        loader.join(); // main waits here until loader is done
        System.out.println("Now processing data.");
    }
}
//=============================
Thread bg = new Thread(() -> {
    while (true) {
        System.out.println("Background heartbeat");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }
});
bg.setDaemon(true); // must be set BEFORE start()
bg.start();
//=============================
class Counter { // RACE conditions
    int count = 0;
    void increment() { count++; } // NOT atomic!
}

public class RaceDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();

        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) c.increment();
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join();  t2.join();

        System.out.println("Expected 200000, got " + c.count); // usually less!
    }
}
//=============================
class SafeCounter {
    private int count = 0;
    synchronized void increment() { count++; }   // locks on 'this'
    int get() { synchronized (this) { return count; } }
}
 or 
private final Object lock = new Object();
void increment() {
    // ...non-critical work here runs in parallel...
    synchronized (lock) { count++; }
}
//=============================
import java.util.concurrent.locks.ReentrantLock;
class LockCounter {
    private final ReentrantLock lock = new ReentrantLock();
    private int count;
    void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock(); // ALWAYS unlock in finally
        }
    }
}
//=============================
class Worker {
    private volatile boolean running = true;
    void run() {
        while (running) { /* work */ }
    }
    void stop() { running = false; } // other threads see this immediately
}
//=============================
class Box {
    private String item;
    private boolean hasItem = false;

    synchronized void put(String s) throws InterruptedException {
        while (hasItem) wait();      // wait until box is empty
        item = s;
        hasItem = true;
        System.out.println("Produced: " + s);
        notifyAll();                 // wake up the consumer
    }

    synchronized String take() throws InterruptedException {
        while (!hasItem) wait();     // wait until box has something
        hasItem = false;
        System.out.println("Consumed: " + item);
        notifyAll();                 // wake up the producer
        return item;
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        Box box = new Box();

        new Thread(() -> {
            try { for (int i = 1; i <= 3; i++) box.put("Item-" + i); }
            catch (InterruptedException e) {}
        }).start();

        new Thread(() -> {
            try { for (int i = 1; i <= 3; i++) box.take(); }
            catch (InterruptedException e) {}
        }).start();
    }
}
//=============================
BlockingQueue<String> queue = new LinkedBlockingQueue<>();
queue.put("data");      // blocks if full
String s = queue.take(); // blocks if empty
//=============================
public class DeadlockDemo {
    static final Object A = new Object();
    static final Object B = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (A) {
                sleep(100);
                synchronized (B) { System.out.println("T1 done"); }
            }
        }).start();

        new Thread(() -> {
            synchronized (B) {
                sleep(100);
                synchronized (A) { System.out.println("T2 done"); }
            }
        }).start();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}
//=============================
import java.util.concurrent.*;

public class PoolDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 6; i++) {
            int taskId = i;
            pool.submit(() ->  System.out.println("Task " + taskId + " on " + Thread.currentThread().getName()));
        }

        pool.shutdown();                          // stop accepting new tasks
        pool.awaitTermination(5, TimeUnit.SECONDS); // wait for running tasks
    }
}
//=============================
ExecutorService pool = Executors.newFixedThreadPool(2);

Callable<Integer> sumTask = () -> { //Runnable returns nothing. Callable returns a value.
    int sum = 0;
    for (int i = 1; i <= 100; i++) sum += i;
    return sum;
};

Future<Integer> future = pool.submit(sumTask);
System.out.println("Doing other work...");
System.out.println("Result: " + future.get()); // blocks until ready -> 5050
pool.shutdown();
//=============================
import java.util.concurrent.CompletableFuture;

CompletableFuture.supplyAsync(() -> "Hello") //chaining
    .thenApply(s -> s + " World")
    .thenAccept(System.out::println)   // prints "Hello World"
    .join();
//=============================
CompletableFuture<Integer> a = CompletableFuture.supplyAsync(() -> 10);
CompletableFuture<Integer> b = CompletableFuture.supplyAsync(() -> 20);

a.thenCombine(b, Integer::sum)
 .thenAccept(r -> System.out.println("Sum: " + r)) // 30
 .join();
//=============================
// Start one virtual thread
Thread.startVirtualThread(() -> System.out.println("Hi from virtual thread"));

// Or use an executor: one virtual thread per task
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 10_000; i++) {
        int id = i;
        executor.submit(() -> {
            Thread.sleep(1000);
            return id;
        });
    }
} // auto-waits for completion
//=============================
import java.util.List;
import java.util.concurrent.*;

public class ProductPageService {

    // ---------- Simple data classes (records, Java 16+) ----------
    record Product(long id, String name) {}
    record Price(double amount, double discountPercent) {}
    record Stock(int quantity) {}
    record Review(String user, int rating, String comment) {}
    record Shipping(String estimate) {}

    record ProductPage(Product product, Price price, Stock stock,
                       List<Review> reviews, Shipping shipping) {}

    // ---------- Shared thread pool for I/O-bound calls ----------
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    // ---------- Simulated remote service calls ----------
    private Product fetchProduct(long id) {
        simulateNetwork("ProductService", 400);
        return new Product(id, "Wireless Headphones");
    }

    private Price fetchPrice(long id) {
        simulateNetwork("PricingService", 300);
        return new Price(120.0, 15);
    }

    private Stock fetchStock(long id) {
        simulateNetwork("InventoryService", 500);
        return new Stock(42);
    }

    private List<Review> fetchReviews(long id) {
        simulateNetwork("ReviewService", 600);
        // Simulate a failing service to show error handling
        throw new RuntimeException("Review service is down");
    }

    private Shipping fetchShipping(long id) {
        simulateNetwork("ShippingService", 350);
        return new Shipping("Delivery in 2-3 days");
    }

    private void simulateNetwork(String service, long ms) {
        System.out.println(Thread.currentThread().getName() + " -> calling " + service);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    // ---------- The important part ----------
    public ProductPage loadProductPage(long productId) {

        CompletableFuture<Product> productF =
            CompletableFuture.supplyAsync(() -> fetchProduct(productId), executor);

        CompletableFuture<Price> priceF =
            CompletableFuture.supplyAsync(() -> fetchPrice(productId), executor);

        CompletableFuture<Stock> stockF =
            CompletableFuture.supplyAsync(() -> fetchStock(productId), executor)
                // Timeout: give up if inventory is slower than 2s (Java 9+)
                .orTimeout(2, TimeUnit.SECONDS)
                // Fallback if it fails or times out
                .exceptionally(ex -> {
                    System.out.println("Stock fallback: " + ex.getMessage());
                    return new Stock(0);
                });

        CompletableFuture<List<Review>> reviewsF =
            CompletableFuture.supplyAsync(() -> fetchReviews(productId), executor)
                // Reviews are non-critical: show an empty list instead of failing the page
                .exceptionally(ex -> {
                    System.out.println("Reviews fallback: " + ex.getCause().getMessage());
                    return List.of();
                });

        CompletableFuture<Shipping> shippingF =
            CompletableFuture.supplyAsync(() -> fetchShipping(productId), executor)
                .exceptionally(ex -> new Shipping("Shipping info unavailable"));

        // Wait for ALL five to finish, then assemble the page
        return CompletableFuture
            .allOf(productF, priceF, stockF, reviewsF, shippingF)
            .thenApply(v -> new ProductPage(
                productF.join(),
                priceF.join(),
                stockF.join(),
                reviewsF.join(),
                shippingF.join()))
            .join();
    }

    public void shutdown() {
        executor.shutdown();
    }

    // ---------- Run it ----------
    public static void main(String[] args) {
        ProductPageService service = new ProductPageService();

        long start = System.currentTimeMillis();
        ProductPage page = service.loadProductPage(101L);
        long elapsed = System.currentTimeMillis() - start;

        System.out.println("\n===== PAGE =====");
        System.out.println(page);
        System.out.println("\nLoaded in " + elapsed + " ms");

        service.shutdown();
    }
}
//=============================
CompletableFuture<List<String>> ordersF =
    CompletableFuture.supplyAsync(() -> fetchUserId("alice@mail.com"), executor)
        .thenCompose(userId ->
            CompletableFuture.supplyAsync(() -> fetchOrders(userId), executor));
//=============================
CompletableFuture<Object> fastest = CompletableFuture.anyOf( // curr rate providers
    CompletableFuture.supplyAsync(() -> callProviderA(), executor),
    CompletableFuture.supplyAsync(() -> callProviderB(), executor));

System.out.println("Rate: " + fastest.join());
//=============================

