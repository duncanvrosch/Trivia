package com.example.duncan.triviaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoresAdapter extends ArrayAdapter<Score> {

    public HighscoresAdapter(Context context, ArrayList<Score> highscoresList) {
        super(context, 0, highscoresList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.score_item, parent, false);
        }

        Score score = getItem(position);

        TextView text_name = convertView.findViewById(R.id.name);
        TextView text_points = convertView.findViewById(R.id.points);

        text_points.setText("Points: " + score.getPoints());
        text_name.setText("Name: " + score.getName());

        return convertView;
    }
}
