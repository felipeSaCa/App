package com.comov.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.R;
import com.comov.myapplication.datamodel.Message;
import com.comov.myapplication.datamodel.Users;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    private static final int MESSAGE_SENT = 0;
    private static final int GROUP_MESSAGE_RECEIVED =1;


    ArrayList<Message> messages;
    private LayoutInflater inflater;
    Users current_user;

    public MessageAdapter(ArrayList<Message> listMessage, LayoutInflater inflater, Users user){
        messages = listMessage;
        this.inflater = inflater;
        current_user = user;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case MESSAGE_SENT:
                return new ViewHolderOwnMessage(inflater.inflate(R.layout.message_sent, parent, false));
            case GROUP_MESSAGE_RECEIVED:
                return new ViewHolderGroupMessage(inflater.inflate(R.layout.received_groups_message, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        int type = getItemViewType(position);
        if(type == MESSAGE_SENT){
            ViewHolderOwnMessage ownHolder = (ViewHolderOwnMessage) holder;
            ownHolder.getText().setText(message.getTitle());
            ownHolder.getDate().setText(message.getDate().toString());
        }
        else if (type == GROUP_MESSAGE_RECEIVED){
            ViewHolderGroupMessage holderGroupMessage = (ViewHolderGroupMessage) holder;
            holderGroupMessage.getText().setText(message.getTitle());
            holderGroupMessage.getUser().setText(message.getUsername());
            holderGroupMessage.getDate().setText(message.getDate().toString());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position){
        Message message = messages.get(position);
        if(message.getChannelID().equals(current_user.getName())){
            return MESSAGE_SENT;
        }
        else {
            return GROUP_MESSAGE_RECEIVED;
        }
    }


    public void addItems (List<Message> message){
        messages.addAll(message);
        notifyDataSetChanged();
    }


    public class ViewHolderOwnMessage extends RecyclerView.ViewHolder {
        TextView text;
        TextView date;

        public ViewHolderOwnMessage(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.sentTxt);
            date = itemView.findViewById(R.id.dateText);
        }

        public TextView getText() {
            return text;
        }

        public TextView getDate() {
            return date;
        }
    }

    public class ViewHolderGroupMessage extends RecyclerView.ViewHolder {
        TextView text;
        TextView user;
        TextView date;

        public ViewHolderGroupMessage(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.userText);
            text = itemView.findViewById(R.id.receivedTxt);
            date = itemView.findViewById(R.id.dateReceivedText);
        }


        public TextView getText() {
            return text;
        }

        public TextView getUser() {
            return user;
        }

        public TextView getDate() {
            return date;
        }
    }
}
