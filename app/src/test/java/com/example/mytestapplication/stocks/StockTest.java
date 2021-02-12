package com.example.mytestapplication.stocks;

import com.example.mytestapplication.jsoup.JsoupWrapper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
//import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//@RunWith(PowerMockRunner.class)
public class StockTest {

    private JsoupWrapper jsoupWrapper;
    private Connection connection;

    @Before
    public void setUp() {
        jsoupWrapper = Mockito.mock(JsoupWrapper.class);
        Connection connection = Mockito.mock(Connection.class);
    }

    @Test
    public void testGetPrice() throws Exception {
        File testShareDocFile = new File("src/test/resources/testData/testShareDoc_card.txt");
        Document doc = Jsoup.parse(testShareDocFile, Charset.defaultCharset().displayName());
        Mockito.when(jsoupWrapper.get(Mockito.anyString())).thenReturn(doc);
        Stock stock = new Stock("CARD", jsoupWrapper);
        stock.refreshStockData();
        assertEquals(36.06, stock.getPrice(), 0.0);
    }

    @Test
    public void testSetPriceDouble() throws Exception {
        Stock stock = new Stock("STUFF");
        stock.setPrice(10.0);
        assertEquals(10.0, stock.getPrice(), 0.0);
    }
}
