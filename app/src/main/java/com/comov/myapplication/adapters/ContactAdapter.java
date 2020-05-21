package com.comov.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comov.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolderContact> {

    private ArrayList<String> contacts;
    private ContactListener listener;

    public ContactAdapter(ArrayList<String> contacts, ContactListener listener) {
        this.contacts = new ArrayList<>();
        this.contacts = contacts;
        this.listener = listener;
    }

    public class ViewHolderContact extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView user;
        public ViewHolderContact(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.titleChannel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClickContact(contacts.get(getAdapterPosition()));
        }

        public void setUser(String user) {
            this.user.setText(user);;
        }

    }

    @NonNull
    @Override
    public ViewHolderContact onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel,parent,false);
        return new ViewHolderContact(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderContact holder, int position) {
        String contact = contacts.get(position);
        holder.setUser(contact);

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
    public void updateItems(List<String> contactList){
        contacts.clear();
        contacts.addAll(contactList);
        notifyDataSetChanged();
    }

    public interface ContactListener{
        void onClickContact(String contact);
    }

}
