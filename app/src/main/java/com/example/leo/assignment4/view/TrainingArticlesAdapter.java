package com.example.leo.assignment4.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leo.assignment4.R;
import com.example.leo.assignment4.database.models.TrainingArticles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TrainingArticlesAdapter extends RecyclerView.Adapter<TrainingArticlesAdapter.MyViewHolder> {

    private Context context;
    private List<TrainingArticles> articlesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView article;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            article = view.findViewById(R.id.article);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }


    public TrainingArticlesAdapter(Context context, List<TrainingArticles> articlesList) {
        this.context = context;
        this.articlesList = articlesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_training_articles, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TrainingArticles article = articlesList.get(position);

        holder.article.setText(article.getArticles());

        // Formatting and displaying timestamp
        holder.timestamp.setText(formatDate(article.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}