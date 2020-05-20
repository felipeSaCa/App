package com.comov.myapplication.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.R;
import com.comov.myapplication.datamodel.Message;

import java.text.SimpleDateFormat;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    private static final int MESSAGE_SENT = 0;
    private static final int GROUP_MESSAGE_RECEIVED =1;
    private static final int IMAGE_SENT =2;

    private List<Message> messages;
    private String current_user;

    public MessageAdapter(List<Message> listMessage, String user){
        messages = listMessage;
        current_user = user;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case MESSAGE_SENT:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_sent,parent,false);
                return new ViewHolderOwnMessage(view);
            case GROUP_MESSAGE_RECEIVED:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.received_groups_message,parent,false);
                return new ViewHolderGroupMessage(view1);
            case IMAGE_SENT:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagen_sent,parent,false);
                return new ViewHolderImageMessage(view2);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        int type = getItemViewType(position);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String dateString = format.format(message.getDate());
        if(type == MESSAGE_SENT){
            ViewHolderOwnMessage ownHolder = (ViewHolderOwnMessage) holder;
            ownHolder.getText().setText(message.getTitle());
            ownHolder.getDate().setText(dateString);
        }
        else if (type == GROUP_MESSAGE_RECEIVED){
            ViewHolderGroupMessage holderGroupMessage = (ViewHolderGroupMessage) holder;
            holderGroupMessage.getText().setText(message.getTitle());
            holderGroupMessage.getUser().setText(message.getUsername());
            holderGroupMessage.getDate().setText(dateString);
        }
        else if (type == IMAGE_SENT){
            ViewHolderImageMessage holderImageMessage = (ViewHolderImageMessage) holder;
            holderImageMessage.getDate().setText(dateString);
            holderImageMessage.getText().setText(message.getUsername());

            byte[] decodedString = Base64.decode(message.getTitle(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holderImageMessage.getImage().setImageBitmap(decodedByte);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position){
        Message message = messages.get(position);
        if(message.getImagenBoolean())
            return IMAGE_SENT;
        if(message.getUsername().equals(current_user)){
            return MESSAGE_SENT;
        }
        else {
            return GROUP_MESSAGE_RECEIVED;
        }
    }


    public void addItems (List<Message> messageList){
        boolean addItem;
        boolean firstTime =false;
        if(getItemCount()<1){
            firstTime = true;
        }
        for (Message message:
             messageList) {
            addItem = true;
            for (Message message1:
                 messages) {
                if (message.equals(message1)){
                    addItem = false; break;
                }
            }
            if(addItem){
                if (firstTime){
                    messages.add(message);
                }

                else {
                    messages.add(messageList.indexOf(message),message);
                }
            }

        }
        notifyDataSetChanged();
    }

    public class ViewHolderImageMessage extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text;
        TextView date;

        public ViewHolderImageMessage(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.userMessage);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.imagenMessage);
        }

        public ImageView getImage() {
            return image;
        }

        public TextView getText() {
            return text;
        }

        public TextView getDate() {
            return date;
        }
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
