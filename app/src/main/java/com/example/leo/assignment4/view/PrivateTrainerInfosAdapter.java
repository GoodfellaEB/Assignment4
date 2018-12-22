package com.example.leo.assignment4.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leo.assignment4.R;
import com.example.leo.assignment4.database.models.PrivateTrainerInfos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PrivateTrainerInfosAdapter extends RecyclerView.Adapter<PrivateTrainerInfosAdapter.MyViewHolder> {

    private Context context;
    private List<PrivateTrainerInfos> infosList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView info;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            info = view.findViewById(R.id.info);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }


    public PrivateTrainerInfosAdapter(Context context, List<PrivateTrainerInfos> infosList) {
        this.context = context;
        this.infosList = infosList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_private_trainer_infos, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PrivateTrainerInfos info = infosList.get(position);

        holder.info.setText(info.getInfos());

        // Formatting and displaying timestamp
        holder.timestamp.setText(formatDate(info.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return infosList.size();
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