package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class List {
    public static void main(String[] args) {
        try {
            int userId = 1;
            URL url = new URL("https://jsonplaceholder.typicode.com/users/" + userId + "/todos");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();

            JSONArray todos = new JSONArray(response.toString());
            System.out.println("Список відкритих задач для користувача з ідентифікатором " + userId + ":");

            for (int i = 0; i < todos.length(); i++) {
                JSONObject todo = todos.getJSONObject(i);
                if (!todo.getBoolean("completed")) {
                    System.out.println("Задача " + todo.getInt("id") + ": " + todo.getString("title"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

