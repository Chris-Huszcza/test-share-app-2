package com.example.mytestapplication.stocks;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StockComparatorTest {

    private StockComparator stockComparator;

    @Before
    public void setUp() {
        stockComparator = new StockComparator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDifferentStockCodes() {
        Stock mnzsStock = new Stock("MNZS");
        mnzsStock.setPrice(100.0);
        Stock cardStock = new Stock("CARD");
        cardStock.setPrice(100.0);
        stockComparator.compare(mnzsStock, cardStock);
    }

    @Test
    public void testLowerPrice() {
        Stock stock1 = new Stock("MNZS");
        stock1.setPrice(99.9);
        Stock stock2 = new Stock("MNZS");
        stock2.setPrice(100.0);
        assertEquals(-1, stockComparator.compare(stock1, stock2));
    }

    @Test
    public void testHigherPrice() {
        Stock stock1 = new Stock("MNZS");
        stock1.setPrice(100.1);
        Stock stock2 = new Stock("MNZS");
        stock2.setPrice(100.0);
        assertEquals(1, stockComparator.compare(stock1, stock2));
    }

    @Test
    public void testEqualPrice() {
        Stock stock1 = new Stock("MNZS");
        stock1.setPrice(100.0);
        Stock stock2 = new Stock("MNZS");
        stock2.setPrice(100.0);
        assertEquals(0, stockComparator.compare(stock1, stock2));
    }

    @Test
    public void testEquals() {
        Stock stock1 = new Stock("MNZS");
        stock1.setPrice(100.0);
        Stock stock2 = new Stock("MNZS");
        stock2.setPrice(100.0);
        assertTrue(stockComparator.equals(stock1, stock2));
    }

    @Test
    public void testNotEquals() {
        Stock stock1 = new Stock("MNZS");
        stock1.setPrice(99.9);
        Stock stock2 = new Stock("MNZS");
        stock2.setPrice(100.0);
        assertFalse(stockComparator.equals(stock1, stock2));
    }
}
