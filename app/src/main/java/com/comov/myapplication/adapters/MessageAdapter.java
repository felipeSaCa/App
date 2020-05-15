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

public class MessageAdapter extends RecyclerView.Adapter {

    private static final int MESSAGE_SENT = 0;
    private static final int PRIVATE_MESSAGE_RECEIVED =1;
    private static final int GROUP_MESSAGE_RECEIVED =2;


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
            case PRIVATE_MESSAGE_RECEIVED:
                return new ViewHolderPrivateMessage(inflater.inflate(R.layout.received_user_message, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        int type = getItemViewType(position);
        if(type == MESSAGE_SENT){
            //TODO
        }
        else if(type == PRIVATE_MESSAGE_RECEIVED){
            //TODO
        }
        else{ //ViewHolderGroupMessage
            //TODO
        }
        // segun los parametros que tenga definitivamente el mensaje
        //aqui donde se asocia valores con vistas
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public int getItemViewType(int position){
        Message message = messages.get(position);
        if(message.getChannelID().equals(current_user.getName())){
            return MESSAGE_SENT;
        }
        else if(message.getNumberListeners()>1){
            return GROUP_MESSAGE_RECEIVED;
        }
        else{
            return PRIVATE_MESSAGE_RECEIVED;
        }
    }

    public void addItem (Message message){
        messages.add(message);
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
    }

    public class ViewHolderPrivateMessage extends RecyclerView.ViewHolder {
        TextView text;
        TextView date;

        public ViewHolderPrivateMessage(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.receivedTxt);
            date = itemView.findViewById(R.id.dateReceivedTextPrivate);
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
            date = itemView.findViewById(R.id.dateReceivedTextPrivate);
        }
    }
}
