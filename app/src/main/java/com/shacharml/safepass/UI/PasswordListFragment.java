package com.shacharml.safepass.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shacharml.safepass.Entities.Password;
import com.shacharml.safepass.PasswordsAdapter;
import com.shacharml.safepass.R;
import com.shacharml.safepass.ViewModels.PasswordViewModel;

import java.util.List;


public class PasswordListFragment extends Fragment {

    private PasswordViewModel passwordViewModel;
    private RecyclerView password_RCV_all_password;
    private AppCompatImageButton main_BTN_add;
    private View view;
    private PasswordsAdapter adapter;
    private SearchView main_EDT_search;


    public PasswordListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// TODO: 06/02/2023 check if need to be here or on Create View
        //create view model
        passwordViewModel = new ViewModelProvider(requireActivity()).get(PasswordViewModel.class);


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

        // TODO: 06/02/2023 need to be in the Activity?
        //observe on the live data in room
        passwordViewModel.getAllPasswords().observe(getViewLifecycleOwner(), new Observer<List<Password>>() {
            @Override
            public void onChanged(List<Password> passwords) {
                // TODO: 06/02/2023 update the recycler view
                adapter.setPasswords(passwords);
                adapter.setPasswordsFiltered(passwords);
            }
        });

        //Add Button
        main_BTN_add= view.findViewById(R.id.main_BTN_add);
        main_BTN_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(PasswordListFragmentDirections.actionPasswordListFragmentToPasswordShowFragment());
            }
        });

        main_EDT_search = view.findViewById(R.id.main_EDT_search);
        main_EDT_search.clearFocus();
        main_EDT_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}