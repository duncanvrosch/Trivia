package com.example.duncan.triviaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class QuestionActivity extends AppCompatActivity implements QuestionHelper.Callback {

    Button answer_1, answer_2, answer_3, answer_4;
    String name;
    TextView text;
    TextView questionCounter;
    ArrayList<Question> questionList;
    int points;
    int question_number = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        String name_intent = intent.getStringExtra("name");
        name = name_intent;

        text = findViewById(R.id.question);
        questionCounter = findViewById(R.id.questionCounter);
        answer_1 = findViewById(R.id.answer1);
        answer_1.setOnClickListener(new Clicked());
        answer_2 = findViewById(R.id.answer2);
        answer_2.setOnClickListener(new Clicked());
        answer_3 = findViewById(R.id.answer3);
        answer_3.setOnClickListener(new Clicked());
        answer_4 = findViewById(R.id.answer4);
        answer_4.setOnClickListener(new Clicked());

        QuestionHelper questionhelper = new QuestionHelper(this);
        questionhelper.getQuestion(this);
    }

    @Override
    public void gotQuestion(ArrayList<Question> questionList) {

        questionCounter.setText(String.format("Question %d of 10", question_number));

        this.questionList = questionList;
        text.setText(questionList.get(0).getQuestion());

        ArrayList<String> answerGuess = new ArrayList<String>(Arrays.asList(questionList.get(0).getIncorrect().get(0),
                questionList.get(0).getIncorrect().get(1), questionList.get(0).getIncorrect().get(2), questionList.get(0).getAnswer()));
        Collections.shuffle(answerGuess);

        answer_1.setText(answerGuess.get(0));
        answer_2.setText(answerGuess.get(1));
        answer_3.setText(answerGuess.get(2));
        answer_4.setText(answerGuess.get(3));
    }

    @Override
    public void gotQuestionError(String message) {
        Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
    }

    private class Clicked implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            String correct_answer = questionList.get(0).getAnswer();
            TextView answer = (TextView) v;
            if (answer.getText() == correct_answer) {
                points += 1;
                question_number += 1;
                String message = "Correct answer!";
                Toast.makeText(QuestionActivity.this, message, Toast.LENGTH_LONG).show();
            }
            else {
                question_number += 1;
                String message = "Oops, wrong answer!";
                Toast.makeText(QuestionActivity.this, message, Toast.LENGTH_LONG).show();
            }

            if (question_number == 11) {

                String score = String.valueOf(points);

                HighscoresPost post = new HighscoresPost(QuestionActivity.this);
                post.postHighscores(QuestionActivity.this, score, name);

                Intent intent = new Intent(QuestionActivity.this, HighscoresActivity.class);
                startActivity(intent);
            }

            else {
                QuestionHelper helper = new QuestionHelper(getApplicationContext());
                helper.getQuestion(QuestionActivity.this);
            }
        }
    }
}
