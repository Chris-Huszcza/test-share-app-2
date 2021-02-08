package com.example.mytestapplication.files;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class ManagedFile extends File {

    private final Context context;

    public ManagedFile(Context context, String filename) throws IOException {
        super(context.getFilesDir(), filename);
        this.context = context;
        // Create file if it does not exist
        if (!this.exists()) {
            this.createNewFile();
        }
    }

    public void write(String string) {
        try (FileOutputStream fos = context.openFileOutput(this.getName(), Context.MODE_PRIVATE)) {
            fos.write(string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileOutputStream openFileOutputStream() throws FileNotFoundException {
        return context.openFileOutput(this.getName(), Context.MODE_PRIVATE);
    }

    public String read() throws FileNotFoundException {
        FileInputStream fis = context.openFileInput(this.getName());
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        String contents;
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
            e.printStackTrace();
        } finally {
            contents = stringBuilder.toString();
        }
        return contents;
    }

    public FileInputStream readAsFileInputStream() throws FileNotFoundException {
        return context.openFileInput(this.getName());
    }
}
