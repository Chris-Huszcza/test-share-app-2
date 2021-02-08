package com.example.mytestapplication.stocks;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Stock {

    private final String stockCode;
    private static final String SHARE_PRICE_URL_PREFIX = "https://www.google.com/search?q=share+price+";
    private static final String PRICE_SELECTOR = "span.IsqQVc.NprOob.XcVN5d";
    private static final String PRICE_DIFF_SELECTOR = "span.WlRRw.IsqQVc";
    private static final String DATE_TIME_SELECTOR = "span[jsname$=ihIZgd]";
    private Double price;
    private String priceDiff;
    private String dateTime;

    public Stock(String stockCode) {
        this.stockCode = stockCode;
    }

    public void refreshStockData() throws Exception {
        System.out.println("Connecting to " + SHARE_PRICE_URL_PREFIX + this.stockCode);
        Document doc = Jsoup.connect(SHARE_PRICE_URL_PREFIX + this.stockCode).get();
        setPrice(doc);
        setPriceDiff(doc);
        setDateTime(doc);
    }

    public String getStockCode() {
        return this.stockCode;
    }

    private void setPrice(Document doc) throws Exception {
        setPrice(Double.parseDouble(getTextOfFirstElementBySelector(doc, PRICE_SELECTOR)));
    }

    public void setPrice(double price) throws Exception {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    private void setPriceDiff(Document doc) throws Exception {
        this.priceDiff = getTextOfFirstElementBySelector(doc, PRICE_DIFF_SELECTOR);
        this.priceDiff = this.priceDiff.substring(0, this.priceDiff.indexOf(" "));
    }

    public String getPriceDiff() {
        return this.priceDiff;
    }

    private void setDateTime(Document doc) throws Exception {
        this.dateTime = getTextOfFirstElementBySelector(doc, DATE_TIME_SELECTOR);
        this.dateTime = this.dateTime.replace(" Â·", "");
        this.dateTime = this.dateTime.replace(" ·", "");
    }

    public String getDateTime() {
        return this.dateTime;
    }

    private String getTextOfFirstElementBySelector(Document doc, String selector) throws Exception {
        Elements elements = doc.select(selector);
        for (Element element : elements) {
            return element.text();
        }
        throw new Exception("The selector [" + selector + "] doesn't match.");
    }
}
