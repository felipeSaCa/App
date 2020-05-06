package com.comov.myapplication.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.datamodel.Channel;
import com.comov.myapplication.datamodel.Users;

import java.util.ArrayList;

public class ChannelAdapter extends RecyclerView.Adapter {
    Users current_user;
    ArrayList<Channel> channels;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }





}
