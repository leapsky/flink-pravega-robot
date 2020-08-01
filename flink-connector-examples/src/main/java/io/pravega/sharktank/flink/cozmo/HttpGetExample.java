package io.pravega.sharktank.flink.cozmo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpGetExample {
    public static void main(String[] args) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://127.0.0.1:8080/recognizeImage?image=fromcozmo-91.jpeg");

        try {
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream stream = entity.getContent()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder sb = new StringBuilder();
                    for (int c; (c = in.read()) >= 0;)
                        sb.append((char)c);
                    String rsp = sb.toString();

                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(rsp);
                    System.out.println(jsonObject);
                    double duration = (double) jsonObject.get("seconds");
                    System.out.println("Duration: " + duration + " seconds");
                    System.out.println(jsonObject.get("answer"));
                    JSONObject jsonObjectAnswer = (JSONObject) jsonObject.get("answer");

                    for (Object id : jsonObjectAnswer.keySet()) {
                        double val = (double) jsonObjectAnswer.get(id);
                        System.out.println(id + " : " + val);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}