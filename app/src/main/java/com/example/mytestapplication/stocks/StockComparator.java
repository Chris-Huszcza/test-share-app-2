package com.example.mytestapplication.stocks;

import java.util.Comparator;

public class StockComparator implements Comparator<Stock> {

    /**
     * Will return negative
     * @param stock1
     * @param stock2
     * @return
     */
    public int compare(Stock stock1, Stock stock2) {
        String stockCode1 = stock1.getStockCode();
        String stockCode2 = stock2.getStockCode();
        if (!stockCode1.equals(stockCode2)) {
            throw new IllegalArgumentException("Stocks [" + stockCode1 + "] and [" +
                    stockCode2 + "] have different stock codes. Can not compare.");
        }
        return Double.compare(stock1.getPrice(), stock2.getPrice());
    }

    public boolean equals(Stock stock1, Stock stock2) {
        return (compare(stock1, stock2) == 0);
    }
}
