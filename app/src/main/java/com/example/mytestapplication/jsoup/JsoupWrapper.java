package com.example.mytestapplication.jsoup;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class JsoupWrapper {

    public JsoupWrapper() {

    }

    public Document parse(File file, String charset) throws IOException {
        return Jsoup.parse(file, charset);
    }

    public Document get(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
