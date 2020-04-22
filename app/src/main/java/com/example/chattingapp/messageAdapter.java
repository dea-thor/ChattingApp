package com.example.chattingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import java.util.List;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.CustomViewHolder> {


    public class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.text_Message);
        }
    }
    List<ResponseMessage> responseMessgeList;

    public messageAdapter(List<ResponseMessage> responseMessgeList) {
        this.responseMessgeList = responseMessgeList;
    }

    @Override
    public int getItemViewType(int position) {
        if(responseMessgeList.get(position).getME()){
            return R.layout.me_bubble;
        }
        else
        {
            return R.layout.bot_background;
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(i,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(messageAdapter.CustomViewHolder customViewHolder, int i) {

       customViewHolder.textView.setText(responseMessgeList.get(i).getTextMessage());
    }


    @Override
    public int getItemCount() {
        return responseMessgeList.size();
    }
}
