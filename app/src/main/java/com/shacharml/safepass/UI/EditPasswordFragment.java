package com.shacharml.safepass.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.imageview.ShapeableImageView;
import com.shacharml.safepass.Entities.Password;
import com.shacharml.safepass.R;
import com.shacharml.safepass.ViewModels.PasswordViewModel;

import java.util.Objects;


public class EditPasswordFragment extends Fragment {

    Button update_BTN_save;
    EditText update_EDT_verify_password;
    EditText update_EDT_new_password;
    EditText update_EDT_password;
    TextView update_TXV_name_password;
    ShapeableImageView update_IMG_img;
    private TextView ed;
    private View view;
    private PasswordViewModel passwordViewModel;
    private Password password;


    public EditPasswordFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordViewModel = new ViewModelProvider(requireActivity()).get(PasswordViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_password, container, false);
//        ed = view.findViewById(R.id.ed);
//        ed.setText(""+getArguments().getInt("position"));

        update_BTN_save = view.findViewById(R.id.update_BTN_save);
        update_EDT_verify_password = view.findViewById(R.id.update_EDT_verify_password);
        update_EDT_new_password = view.findViewById(R.id.update_EDT_new_password);
        update_EDT_password = view.findViewById(R.id.update_EDT_password);
        update_TXV_name_password = view.findViewById(R.id.update_TXV_name_password);
        update_IMG_img = view.findViewById(R.id.update_IMG_img);

        assert getArguments() != null;
        password = Objects.requireNonNull(passwordViewModel.getAllPasswords().getValue()).get(getArguments().getInt("position"));
        update_TXV_name_password.setText(password.getName());
        update_IMG_img.setImageResource(Integer.parseInt(password.getImg()));

        update_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!updatePassword())
                    Toast.makeText(requireContext(), "no correct ", Toast.LENGTH_SHORT).show();
                else
                    Navigation.findNavController(requireView()).navigate(EditPasswordFragmentDirections.actionEditPasswordFragmentToPasswordListFragment());
            }
        });


        return view;
    }

    private boolean updatePassword() {
        if (update_EDT_password.getText().toString().equals(password.getPassword().toString())){
            if (update_EDT_verify_password.getText().toString().equals(update_EDT_new_password.getText().toString())){
                password.setPassword(update_EDT_new_password.getText().toString());
                passwordViewModel.updatePassword(password);
                return true;
            }
        }
        return false;
    }
}