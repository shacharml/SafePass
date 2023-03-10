package com.shacharml.safepass.Adapters;

import android.net.Uri;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shacharml.safepass.Entities.Password;
import com.shacharml.safepass.R;
import com.shacharml.safepass.Utils.EncryptionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PasswordsAdapter extends RecyclerView.Adapter<PasswordsAdapter.PasswordViewHolder> implements Filterable {

    private List<Password> passwords = new ArrayList<>();
    private List<Password> passwordsFiltered = new ArrayList<>();
    private PasswordListener passwordListener;


    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.password_card_view, parent, false);
        return new PasswordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {
        Password currentPassword = passwords.get(position);
        holder.password_TXV_password_name.setText(currentPassword.getName());
        holder.password_TXV_url.setText(currentPassword.getUrlToSite());
        holder.password_TXV_password.setText(EncryptionManager.decrypt(currentPassword.getPassword()));
        try {
            holder.card_IMG_img.setImageResource(Integer.parseInt(currentPassword.getImg()));
        }
        catch (Exception e){
            holder.card_IMG_img.setImageURI(Uri.parse(currentPassword.getImg()));
        }
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

    public void setPasswordsFiltered(List<Password> passwordsFiltered) {
        this.passwordsFiltered = passwordsFiltered;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<Password> filterList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filterList.addAll(passwordsFiltered);
                } else {
                    String searchChr = constraint.toString().toLowerCase(Locale.ROOT);
                    for (Password password : passwordsFiltered) {
                        if (password.getName().toLowerCase(Locale.ROOT).contains(searchChr)) {
                            filterList.add(password);
                        }
                    }
                }
                filterResults.count = filterList.size();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                passwords = (List<Password>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void setPasswordListener(PasswordListener passwordListener) {
        this.passwordListener = passwordListener;
    }

    public interface PasswordListener {
//        void favorite(Password password);

        //        void seePassword(boolean bool);
        void passwordClicked(Password password);

        void copy(Password password);
    }

    class PasswordViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView password_TXV_password_name;
        private final MaterialTextView password_TXV_url;
        private final MaterialTextView password_TXV_password;
        private final ShapeableImageView card_IMG_img;

        public PasswordViewHolder(View itemView) {
            super(itemView);
            password_TXV_password_name = itemView.findViewById(R.id.password_TXV_password_name);
            password_TXV_url = itemView.findViewById(R.id.password_TXV_url);
            password_TXV_password = itemView.findViewById(R.id.password_TXV_password);
//            AppCompatImageButton password_IMB_fav_password = itemView.findViewById(R.id.password_IMB_fav_password);
            AppCompatImageButton password_IMB_see_password = itemView.findViewById(R.id.password_IMB_see_password);
            AppCompatImageButton password_IMB_copy_password = itemView.findViewById(R.id.password_IMB_copy_password);
            card_IMG_img = itemView.findViewById(R.id.card_IMG_img);

            //On clicked listeners for all the buttons
//            password_IMB_fav_password.setOnClickListener(v -> passwordListener.favorite(getItem(getBindingAdapterPosition())));
            password_IMB_copy_password.setOnClickListener(v -> passwordListener.copy(getItem(getBindingAdapterPosition())));
            password_IMB_see_password.setOnClickListener(v -> {
                if (password_TXV_password.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD)
                    password_TXV_password.setInputType(InputType.TYPE_CLASS_TEXT);
                else {
                    password_TXV_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

//                    final Handler handler = new Handler(Looper.getMainLooper());
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            password_TXV_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//
//                        }
//                    }, 2000);

            });

            itemView.setOnClickListener(v -> passwordListener.passwordClicked(getItem(getBindingAdapterPosition())));
        }
    }
}
