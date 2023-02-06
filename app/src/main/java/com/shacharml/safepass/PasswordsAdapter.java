package com.shacharml.safepass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.shacharml.safepass.Entities.Password;

import java.util.ArrayList;
import java.util.List;

public class PasswordsAdapter extends RecyclerView.Adapter<PasswordsAdapter.PasswordViewHolder> {

    private List<Password> passwords = new ArrayList<>();


    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.password_card_view,parent,false);
        return new PasswordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {
        Password currentPassword = passwords.get(position);
        holder.password_TXV_password_name.setText(currentPassword.getName());
        holder.password_TXV_url.setText(currentPassword.getUrlToSite());
        holder.password_TXV_password.setText(currentPassword.getPassword());
    }

    @Override
    public int getItemCount() {
        return passwords.size();
    }

    public Password getItem(int position) {
        return passwords.get(position);
    }

    public void setPasswords(List<Password> passwords) {
        this.passwords = passwords;
        notifyDataSetChanged();
    }

    class PasswordViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView password_TXV_password_name;
        private MaterialTextView password_TXV_url;
        private MaterialTextView password_TXV_password;

        private AppCompatImageButton password_IMB_edit_password;
        private AppCompatImageButton password_IMB_see_password;
        private AppCompatImageButton password_IMB_copy_password;

        public PasswordViewHolder(View itemView) {
            super(itemView);
           password_TXV_password_name = itemView.findViewById(R.id.password_TXV_password_name);
           password_TXV_url = itemView.findViewById(R.id.password_TXV_url);
           password_TXV_password = itemView.findViewById(R.id.password_TXV_password);
           password_IMB_edit_password = itemView.findViewById(R.id.password_IMB_edit_password);
           password_IMB_see_password = itemView.findViewById(R.id.password_IMB_see_password);
           password_IMB_copy_password = itemView.findViewById(R.id.password_IMB_copy_password);


            // TODO: 06/02/2023 handel the buttons and the card click
//            list_BTN_delete.setOnClickListener(view -> contactListener.delete(getItem(getAdapterPosition())));
//            list_BTN_edit.setOnClickListener(view -> contactListener.edit(getItem(getAdapterPosition())));
//            itemView.setOnClickListener(view -> contactListener.clicked(getItem(getAdapterPosition())));
        }



    }

}
