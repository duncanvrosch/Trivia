package com.example.duncan.triviaapp;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;

public class QuestionHelper implements Response.Listener<JSONObject>, Response.ErrorListener{

    private Context context;
    private ArrayList<Question> questionList;
    private Callback activity;

    public interface Callback {
        void gotQuestion(ArrayList<Question> questionList);
        void gotQuestionError(String message);
    }

    public QuestionHelper(Context aContext) {
        this.context = aContext;
    }

    @Override
    public void onResponse(JSONObject response) {
        questionList = new ArrayList<>();

        try {
            JSONArray quiz = response.getJSONArray("results");

            for (int i =  0; i < quiz.length(); i++) {
                JSONObject object = quiz.getJSONObject(i);
                String question = object.getString("question");
                String answer = object.getString("correct_answer");
                JSONArray incorrect_json = object.getJSONArray("incorrect_answers");
                String incorrect1 = incorrect_json.getString(0);
                String incorrect2 = incorrect_json.getString(1);
                String incorrect3 = incorrect_json.getString(2);
                ArrayList<String> incorrect = new ArrayList<>(Arrays.asList(incorrect1, incorrect2, incorrect3));
                Question questionObject = new Question(question, answer, incorrect);
                questionList.add(questionObject);
            }

            activity.gotQuestion(questionList);
        }

        catch (Exception e) {
            e.printStackTrace();
            String warningMessage = e.getMessage();
            Toast.makeText(context, "errorwithJSON", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotQuestionError(error.getMessage());
    }

    public void getQuestion(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://opentdb.com/api.php?amount=10&difficulty=easy&type=multiple";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        queue.add(jsonObjectRequest);
    }
}