package service;

import domain.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class OrderBookService {
    private final Map<String, TreeMap<Integer, Pair>> orderInfo;

    private OrderBookService() {
        orderInfo = new HashMap<>();
    }

    private static OrderBookService instance;

    public static OrderBookService getInstance() {
        if (instance == null) {
            instance = new OrderBookService();
        }
        return instance;
    }

    private class Pair {
        final int buyCount;
        final int sellCount;

        private Pair(int buyCount, int sellCount) {
            this.buyCount = buyCount;
            this.sellCount = sellCount;
        }
    }

    public void orderCreatedEventHandle(Order order) {
        synchronized (orderInfo) {
            String ticker = order.getTicker();
            int price = order.getPrice();
            orderInfo.putIfAbsent(ticker, new TreeMap<>());
            if (orderInfo.get(ticker).containsKey(price)) {
                Pair has = orderInfo.get(ticker).get(price);
                Pair toPut = null;
                if (order.getSide() == Order.Side.BUY) {
                    toPut = new Pair(has.buyCount + order.getCount(), has.sellCount);
                } else {
                    toPut = new Pair(has.buyCount, has.sellCount + order.getCount());
                }
                orderInfo.get(ticker).put(price, toPut);
            } else {
                Pair toPut = null;
                if (order.getSide() == Order.Side.BUY) {
                    toPut = new Pair(order.getCount(), 0);
                } else {
                    toPut = new Pair(0, order.getCount());
                }
                orderInfo.get(ticker).put(price, toPut);
            }
        }
    }

    public void printInfo() {
        synchronized (orderInfo) {
            System.out.println(Thread.currentThread().getName());
            if (orderInfo.isEmpty()) {
                System.out.println("NO ORDERS");
                return;
            }
            for (String ticker : orderInfo.keySet()) {
                System.out.println("BUY " + ticker + "  SELL");
                System.out.println("---------------------");
                for (Map.Entry<Integer, Pair> tickerInfo : orderInfo.get(ticker).entrySet()) {
                    int buyCount = tickerInfo.getValue().buyCount;
                    int sellCount = tickerInfo.getValue().sellCount;
                    int price = tickerInfo.getKey();
                    System.out.println(buyCount + " " + price + " " + sellCount);
                }
            }
        }
    }
}
