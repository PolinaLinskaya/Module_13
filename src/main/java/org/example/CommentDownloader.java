package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class CommentDownloader {
    public static void main(String[] args) {
        try {
            int userId = 1;
            URL postsUrl = new URL("https://jsonplaceholder.typicode.com/users/" + userId + "/posts");
            HttpURLConnection connection = (HttpURLConnection) postsUrl.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();

            JSONArray posts = new JSONArray(response.toString());
            if (posts.length() == 0) {
                System.out.println("Користувач не має жодного посту.");
                return;
            }

            JSONObject lastPost = posts.getJSONObject(posts.length() - 1);
            int lastPostId = lastPost.getInt("id");

            // Отримання коментарів до останнього посту
            URL commentsUrl = new URL("https://jsonplaceholder.typicode.com/posts/" + lastPostId + "/comments");
            connection = (HttpURLConnection) commentsUrl.openConnection();
            connection.setRequestMethod("GET");

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();

            JSONArray comments = new JSONArray(response.toString());

            // Запис у файл
            String fileName = "user-" + userId + "-post-" + lastPostId + "-comments.json";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(comments.toString());
            writer.close();

            System.out.println("Коментарі було записано у файл " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

