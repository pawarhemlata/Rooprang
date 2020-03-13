package com.tsi.rooprang.NotificationScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tsi.rooprang.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<NotificationDataModel> datalist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView,textView1,textView2,textView3,textView4;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text1);
            textView1 = itemView.findViewById(R.id.text2);
        }
    }

    public NotificationAdapter(List<NotificationDataModel> datalist){
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {
        NotificationDataModel dataModel = datalist.get(position);
        holder.textView.setText(dataModel.getText());
        holder.textView1.setText(dataModel.getText1());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}
