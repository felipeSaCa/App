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
    private ArrayList<Channel> channels;
    private ChannelListener listener;

    public ChannelAdapter(List<Channel> channels, ChannelListener listener){
        this.channels = new ArrayList<Channel>();
        this.channels.addAll(channels);
        this.listener = listener;

    }

    public class ViewHolderChannel extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;

        public ViewHolderChannel(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleChannel);
            itemView.setOnClickListener(this);
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        @Override
        public void onClick(View v) {
            listener.onClickChannel(channels.get(getAdapterPosition()));
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


    public void addItems (List<Channel> channels){
        boolean addItem;
        for (Channel channel : channels) {
            addItem = true;
            for (Channel channel1 : this.channels){
                if(channel1.equals(channel)){
                    addItem = false;
                }
            }
            if (addItem)
                this.channels.add(channel);
        }
        notifyDataSetChanged();
    }

    public interface ChannelListener{
        void onClickChannel(Channel channel);
    }


}
