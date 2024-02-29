import generator.OrderGeneratorRunnable;
import service.OrderBookService;

import java.io.IOException;

public class Main {
    private final static int THREAD_COUNT = 5;

    private final static OrderBookService orderBookService = OrderBookService.getInstance();

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < THREAD_COUNT; ++i) {
            new Thread(new OrderGeneratorRunnable()).start();
        }

        for (; ; ) {
            int read = System.in.read();
            if (read == (int) '\n') {
                orderBookService.printInfo();
            }
        }
    }
}