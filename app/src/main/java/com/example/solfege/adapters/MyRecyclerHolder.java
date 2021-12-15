package com.example.solfege.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solfege.R;

public class MyRecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title;
    ItemClickListener itemClickListener;

    public MyRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.cardTitle);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }
}