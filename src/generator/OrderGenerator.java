package generator;

import domain.Order;

import java.util.Random;

public class OrderGenerator {
    private final String[] availableTickers;

    private final int[] minPriceForTickers;

    private final int[] maxPriceForTickers;
    private final int minCount;

    private final int maxCount;

    private final Random random;

    public OrderGenerator() {
        availableTickers = new String[]{"SBER", "GMKN", "GAZP"};

        minPriceForTickers = new int[]{270, 17000, 150};
        maxPriceForTickers = new int[]{300, 17500, 180};
        minCount = 1;
        maxCount = 100;
        random = new Random();
    }

    public Order generate() {
        Order order = new Order();

        int tickerInd = Math.abs(random.nextInt()) % availableTickers.length;
        String ticker = availableTickers[tickerInd];
        order.setTicker(ticker);

        order.setSide(random.nextBoolean() ? Order.Side.BUY : Order.Side.SELL);

        order.setCount(generateInBounds(minCount, maxCount));

        order.setPrice(generateInBounds(minPriceForTickers[tickerInd], maxPriceForTickers[tickerInd]));

        return order;
    }

    private int generateInBounds(int l, int r) {
        return Math.abs(random.nextInt()) % (r - l + 1) + l;
    }
}
