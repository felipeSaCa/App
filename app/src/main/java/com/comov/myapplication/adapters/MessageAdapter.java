package com.comov.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.R;
import com.comov.myapplication.datamodel.Message;
import com.comov.myapplication.location.MapsActivity;
import com.google.android.gms.maps.MapView;

import java.text.SimpleDateFormat;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    private static final int MESSAGE_SENT = 0;
    private static final int GROUP_MESSAGE_RECEIVED = 1;
    private static final int IMAGE_SENT = 2;
    private static final int IMAGE_RECEIVED = 3;
    private static final int LOCATION_SENT = 4;
    private static final int LOCATION_RECEIVED = 5;

    private List<Message> messages;
    private String current_user;
    private Context context;

    public MessageAdapter(Context context,List<Message> listMessage, String user){
        messages = listMessage;
        current_user = user;
        this.context = context;
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
                return new ViewHolderImageSentMessage(view2);
            case IMAGE_RECEIVED:
                View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagen_received,parent,false);
                return new ViewHolderImageReceivedMessage(view3);
            case LOCATION_SENT:
                View view4 = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_sent,parent,false);
                return new ViewHolderLocationSentMessage(view4);
            case LOCATION_RECEIVED:
                View view5 = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_received,parent,false);
                return new ViewHolderLocationSentMessage(view5);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        int type = getItemViewType(position);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String dateString = format.format(message.getDate());
        switch (type){
            case MESSAGE_SENT:
                ViewHolderOwnMessage ownHolder = (ViewHolderOwnMessage) holder;
                ownHolder.getUser().setText(message.getUsername());
                ownHolder.getText().setText(message.getTitle());
                ownHolder.getDate().setText(dateString);
                break;
            case GROUP_MESSAGE_RECEIVED:
                ViewHolderGroupMessage holderGroupMessage = (ViewHolderGroupMessage) holder;
                holderGroupMessage.getText().setText(message.getTitle());
                holderGroupMessage.getUser().setText(message.getUsername());
                holderGroupMessage.getDate().setText(dateString);
                break;
            case IMAGE_SENT:
                ViewHolderImageSentMessage holderImageMessage = (ViewHolderImageSentMessage) holder;
                holderImageMessage.getDate().setText(dateString);
                holderImageMessage.getUser().setText(message.getUsername());

                byte[] decodedString = Base64.decode(message.getTitle(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holderImageMessage.getImage().setImageBitmap(decodedByte);
                break;
            case IMAGE_RECEIVED:
                ViewHolderImageReceivedMessage holderImageMessage2 = (ViewHolderImageReceivedMessage) holder;
                holderImageMessage2.getDate().setText(dateString);
                holderImageMessage2.getText().setText(message.getUsername());

                byte[] decodedString2 = Base64.decode(message.getTitle(), Base64.DEFAULT);
                Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                holderImageMessage2.getImage().setImageBitmap(decodedByte2);
                break;
            case LOCATION_SENT:
                ViewHolderLocationSentMessage holderLocationSentMessage = (ViewHolderLocationSentMessage) holder;
                holderLocationSentMessage.getDate().setText(dateString);
                holderLocationSentMessage.getUser().setText(message.getUsername());
                holderLocationSentMessage.getMap().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] geocode =message.getTitle().split(",");
                        Log.i("Coordenadas", "Size "+ geocode.length);
                        Intent intent = new Intent(context, MapsActivity.class);
                        intent.putExtra("latitud",geocode[0]);
                        intent.putExtra("longitud",geocode[1]);
                        context.startActivity(intent);
                    }
                });
                break;
            case LOCATION_RECEIVED:
                ViewHolderLocationReceivedMessage holderLocationReceivedMessage = (ViewHolderLocationReceivedMessage) holder;
                holderLocationReceivedMessage.getDate().setText(dateString);
                holderLocationReceivedMessage.getUser().setText(message.getUsername());
                holderLocationReceivedMessage.getMap().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] geocode =message.getTitle().split(",");
                        Log.i("Coordenadas", "Size "+ geocode.length);
                        Intent intent = new Intent(context, MapsActivity.class);
                        intent.putExtra("latitud",geocode[0]);
                        intent.putExtra("longitud",geocode[1]);
                        context.startActivity(intent);
                    }
                });
                break;
        }
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position){
        Message message = messages.get(position);

        if(message.getUsername().equals(current_user)){
            if(message.isImage())
                return IMAGE_SENT;
            else if(message.isLocation())
                return LOCATION_SENT;
            return MESSAGE_SENT;
        }
        else {
            if(message.isImage())
                return IMAGE_RECEIVED;
            else if(message.isLocation())
                return LOCATION_RECEIVED;
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

    public class ViewHolderImageSentMessage extends RecyclerView.ViewHolder{
        ImageView image;
        TextView user;
        TextView date;

        public ViewHolderImageSentMessage(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.userImageSent);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.imagenMessage);
        }

        public ImageView getImage() {
            return image;
        }

        public TextView getUser() {
            return user;
        }

        public TextView getDate() {
            return date;
        }
    }

    public class ViewHolderImageReceivedMessage extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text;
        TextView date;

        public ViewHolderImageReceivedMessage(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.userMessageReceived);
            date = itemView.findViewById(R.id.dateImageReceived);
            image = itemView.findViewById(R.id.imageMessageReceived);
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
        TextView user;
        TextView text;
        TextView date;

        public ViewHolderOwnMessage(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.userMessageSent);
            text = itemView.findViewById(R.id.sentTxt);
            date = itemView.findViewById(R.id.dateText);
        }

        public TextView getUser() {
            return user;
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

    public class ViewHolderLocationSentMessage extends RecyclerView.ViewHolder {
        ImageButton map;
        TextView user;
        TextView date;

        public ViewHolderLocationSentMessage(@NonNull View itemView) {
            super(itemView);
            map = itemView.findViewById(R.id.mapViewSent);
            user = itemView.findViewById(R.id.userLocationSent);
            date = itemView.findViewById(R.id.dateLocationSent);
        }

        public ImageButton getMap() {
            return map;
        }

        public TextView getUser() {
            return user;
        }

        public TextView getDate() {
            return date;
        }
    }

    public class ViewHolderLocationReceivedMessage extends RecyclerView.ViewHolder {
        ImageButton map;
        TextView user;
        TextView date;

        public ViewHolderLocationReceivedMessage(@NonNull View itemView) {
            super(itemView);
            map = itemView.findViewById(R.id.mapViewReceived);
            user = itemView.findViewById(R.id.userLocationReceived);
            date = itemView.findViewById(R.id.dateLocationReceived);
        }

        public ImageButton getMap() {
            return map;
        }

        public TextView getUser() {
            return user;
        }

        public TextView getDate() {
            return date;
        }
    }

}
