package com.shacharml.safepass.Objects;

import com.shacharml.safepass.R;

import java.util.ArrayList;

public class CompanyList {

    private final ArrayList<Company> companyData = new ArrayList<>();

    public CompanyList() {
        companyData.add(0, new Company(0, R.drawable.cl_general_account, "General Account"));
        companyData.add(1, new Company(1, R.drawable.cl_amazon, "Amazon"));
        companyData.add(2, new Company(2, R.drawable.cl_icloud, "Apple iCloud"));
        companyData.add(3, new Company(3, R.drawable.cl_canva, "Canva"));
        companyData.add(4, new Company(4, R.drawable.cl_discord, "Discord"));
        companyData.add(5, new Company(5, R.drawable.cl_facebook, "Facebook"));
        companyData.add(6, new Company(6, R.drawable.cl_github, "Github"));
        companyData.add(7, new Company(7, R.drawable.cl_gmail, "Gmail"));
        companyData.add(8, new Company(8, R.drawable.cl_instegram, "Instagram"));
        companyData.add(9, new Company(9, R.drawable.cl_linkedin, "LinkedIn"));
        companyData.add(10, new Company(10, R.drawable.cl_microsoft, "Microsoft"));
        companyData.add(11, new Company(11, R.drawable.cl_outlook, "Outlook"));
        companyData.add(12, new Company(12, R.drawable.cl_paypal, "PayPal"));
        companyData.add(13, new Company(13, R.drawable.cl_paytm, "Paytm"));
        companyData.add(14, new Company(14, R.drawable.cl_protonmail, "Proton Mail"));
        companyData.add(15, new Company(15, R.drawable.cl_quora, "Quora"));
        companyData.add(16, new Company(16, R.drawable.cl_reddit, "Reddit"));
        companyData.add(17, new Company(17, R.drawable.cl_snapchat, "Snapchat"));
        companyData.add(18, new Company(18, R.drawable.cl_spotify, "Spotify"));
        companyData.add(19, new Company(19, R.drawable.cl_udemy, "Udemy"));
        companyData.add(20, new Company(20, R.drawable.cl_yahoo, "Yahoo"));
    }

    public ArrayList<Company> getCompanyData() {
        return companyData;
    }
}
