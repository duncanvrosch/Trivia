package com.example.duncan.triviaapp;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class HighscoresHelper implements Response.Listener<JSONArray>, Response.ErrorListener{

    private Context context;
    private ArrayList<Score> highscoresList;
    private HighscoresHelper.Callback activity;

    public interface Callback {
        void gotScore(ArrayList<Score> highscoresList);
        void gotScoreError(String message);
    }

    public HighscoresHelper(Context aContext) {
        this.context = aContext;
    }

    @Override
    public void onResponse(JSONArray response) {
        highscoresList = new ArrayList<>();

        try {
            for (int i =  0; i < response.length(); i++) {
                JSONObject object = response.getJSONObject(i);
                String name = object.getString("name");
                String points = object.getString("points");
                Score score = new Score(name, points);
                highscoresList.add(score);
            }
            activity.gotScore(highscoresList);
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "errorwithJSON", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotScoreError(error.getMessage());
    }

    public void getScore(HighscoresHelper.Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://ide50-duncanvrosch.cs50.io:8080/list";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, this, this);
        queue.add(jsonArrayRequest);
    }

}
