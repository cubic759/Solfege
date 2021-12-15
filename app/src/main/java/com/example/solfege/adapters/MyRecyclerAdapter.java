package com.example.solfege.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solfege.DoingTestActivity;
import com.example.solfege.R;

import java.util.Arrays;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerHolder> {
    Context context;
    List<String> list;
    private long mLastClickTime = 0;

    public MyRecyclerAdapter(Context context, String[] list) {
        this.context = context;
        this.list = Arrays.asList(list);
    }

    @NonNull
    @Override
    public MyRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_choice, parent, false);
        return new MyRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerHolder holder, int position) {
        holder.title.setText(list.get(position));
        holder.setItemClickListener((v, position1) -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            Intent intent = new Intent(context, DoingTestActivity.class);
            intent.putExtra("index", position1);//Set the title name
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

interface ItemClickListener {
    void onItemClickListener(View v, int position);
}