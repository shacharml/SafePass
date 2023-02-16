package com.shacharml.safepass.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.shacharml.safepass.Entities.Company;
import com.shacharml.safepass.R;

import java.util.ArrayList;
import java.util.List;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.CompanyViewHolder> {

    private List<Company> companies = new ArrayList<>();
    private CompaniesAdapter.CompanyListener companyListener;


    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_card_view, parent, false);
        return new CompanyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        Company currentCompany = companies.get(position);
        holder.company_name.setText(currentCompany.getName());
        holder.company_img.setImageResource(currentCompany.getIcon());
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public Company getItem(int position) {
        return companies.get(position);
    }

    public void setPasswords(List<Company> companies) {
        this.companies = companies;
        notifyDataSetChanged();
    }


    public void setCompanyListener(CompanyListener companyListener) {
        this.companyListener = companyListener;
    }

    public interface CompanyListener {
        void companyClicked(Company company);
    }

    class CompanyViewHolder extends RecyclerView.ViewHolder {

        private final ShapeableImageView company_img;
        private final TextView company_name;

        public CompanyViewHolder(View itemView) {
            super(itemView);
            company_img = itemView.findViewById(R.id.company_img);
            company_name = itemView.findViewById(R.id.company_name);

            itemView.setOnClickListener(v -> companyListener.companyClicked(getItem(getBindingAdapterPosition())));

        }
    }
}
