package com.comov.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.R;
import com.comov.myapplication.datamodel.Channel;
import com.comov.myapplication.datamodel.Users;

import java.util.ArrayList;
import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolderChannel>{
    ArrayList<Channel> channels;

    public ChannelAdapter(List<Channel> channels){
        this.channels = new ArrayList<Channel>();
        this.channels.addAll(channels);

    }

    public class ViewHolderChannel extends RecyclerView.ViewHolder{

        TextView title;

        public ViewHolderChannel(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleChannel);
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }
    }

    @NonNull
    @Override
    public ViewHolderChannel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel,parent,false);
        return new ViewHolderChannel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderChannel holder, int position) {
        Channel channel = channels.get(position);
        holder.setTitle(channel.getTitle());


    }

    @Override
    public int getItemCount() {
        return channels.size();
    }






}
