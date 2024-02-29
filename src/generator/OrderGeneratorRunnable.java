package generator;

import domain.Order;
import service.OrderBookService;

import java.util.Random;

public class OrderGeneratorRunnable implements Runnable {
    private final Random random;

    private final int minDelay;

    private final int maxDelay;

    private final OrderGenerator orderGenerator;

    private final OrderBookService orderBookService;

    public OrderGeneratorRunnable() {
        random = new Random();
        minDelay = 1;
        maxDelay = 10;
        orderGenerator = new OrderGenerator();
        orderBookService = OrderBookService.getInstance();
    }

    private int generateInBounds(int l, int r) {
        return Math.abs(random.nextInt()) % (r - l + 1) + l;
    }

    @Override
    public void run() {
        for (;;) {
            Order order = orderGenerator.generate();
            System.out.println(order.toString() + " from " + Thread.currentThread().getName());
            orderBookService.orderCreatedEventHandle(order);

            int delay = generateInBounds(minDelay, maxDelay);
            try {
                Thread.sleep(delay * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
