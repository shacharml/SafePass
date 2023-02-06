package com.shacharml.safepass.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shacharml.safepass.Entities.Password;
import com.shacharml.safepass.PasswordsAdapter;
import com.shacharml.safepass.R;
import com.shacharml.safepass.ViewModels.PasswordViewModel;

import java.util.List;


public class PasswordListFragment extends Fragment {

    private PasswordViewModel passwordViewModel;
    private RecyclerView password_RCV_all_password;
    private View view;
    private PasswordsAdapter adapter;


    public PasswordListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// TODO: 06/02/2023 check if need to be here or on Create View
        //create view model
        passwordViewModel = new ViewModelProvider(requireActivity()).get(PasswordViewModel.class);
        passwordViewModel.getAllPasswords().observe(this, new Observer<List<Password>>() {
            @Override
            public void onChanged(List<Password> passwords) {
                // TODO: 06/02/2023 update the recycler view
                adapter.setPasswords(passwords);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_password_list, container, false);

        //Init RecyclerView
        password_RCV_all_password = view.findViewById(R.id.password_RCV_all_password);
        password_RCV_all_password.setLayoutManager(new LinearLayoutManager(requireActivity()));
        password_RCV_all_password.setHasFixedSize(true);

        //create adapter
        adapter = new PasswordsAdapter();
        password_RCV_all_password.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}