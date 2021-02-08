package com.example.mytestapplication.stocks;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StockTest {

    private Jsoup jsoup;
    private Connection connection;

    @Before
    public void setUp() {
        Jsoup jsoup = Mockito.mock(Jsoup.class);
        Connection connection = Mockito.mock(Connection.class);
    }

    @Test
    public void testDummyMethod() {
        // TODO - remove this method when other methods are fixed. Need this method here as
        // an empty test class is invalid.
        assertTrue(true);
    }

    /*
    TODO - Remove this test and replace with one that doesn't use real internet connection
    @Test
    public void testGetPrice() throws Exception {
        Stock mnzsStock = new Stock("MNZS");
        mnzsStock.refreshStockData();
        String price = String.valueOf(mnzsStock.getPrice());
        assertEquals("230.0", price);
    }*/

    /*
    TODO - fix this test so it works without mockito-inline (which isn't supported on Android).
    @Test
    public void testSetStockData() throws IOException, Exception {
        Stock stock;
        File testShareDocFile = new File("src/test/resources/testData/testShareDoc_card.txt");
        Document doc = Jsoup.parse(testShareDocFile, Charset.defaultCharset().displayName());
        try (MockedStatic<Jsoup> theMock = Mockito.mockStatic(Jsoup.class)) {
            theMock.when(() -> Jsoup.connect(Mockito.anyString())).thenReturn(connection);
            Mockito.when(connection.get()).thenReturn(doc);

            stock = new Stock("CARD");
            stock.refreshStockData();
        }
        assertEquals(stock.getPrice(), "36.06");
        assertEquals(stock.getPriceDiff(), "+0.060");
        assertEquals(stock.getDateTime(), "29 Jan, 16:38 GMT");
    }*/

}
