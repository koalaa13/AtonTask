package domain;

public class Order {
    public static enum Side {
        BUY,
        SELL
    }

    private Side side;

    private String ticker;

    private int count;

    private int price;

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return side.toString() + ' ' + ticker + ' ' + count + '@' + price;
    }
}
