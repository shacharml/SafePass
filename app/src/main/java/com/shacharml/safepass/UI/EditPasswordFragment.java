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
import com.shacharml.safepass.Utils.EncryptionManager;
import com.shacharml.safepass.ViewModels.PasswordViewModel;

import java.util.Objects;


public class EditPasswordFragment extends Fragment {

    private Button update_BTN_save;
    private EditText update_EDT_verify_password;
    private EditText update_EDT_new_password;
    private EditText update_EDT_password;
    private TextView update_TXV_name_password;
    private ShapeableImageView update_IMG_img;
    private View view;
    private PasswordViewModel passwordViewModel;
    private Password currentPassword;


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

        findViews(view);

        //Get Bundle arguments
        assert getArguments() != null;
        currentPassword = Objects.requireNonNull(passwordViewModel.getAllPasswords().getValue()).get(getArguments().getInt("position"));
        update_TXV_name_password.setText(currentPassword.getName());
        update_IMG_img.setImageResource(Integer.parseInt(currentPassword.getImg()));

        update_BTN_save.setOnClickListener(v -> {
            if (!updatePassword())
                Toast.makeText(requireContext(), "The current Password is incorrect ", Toast.LENGTH_SHORT).show();
            else
                Navigation.findNavController(requireView()).navigate(EditPasswordFragmentDirections.actionEditPasswordFragmentToPasswordListFragment());
        });
        return view;
    }

    private void findViews(View view) {
        update_BTN_save = view.findViewById(R.id.update_BTN_save);
        update_EDT_verify_password = view.findViewById(R.id.update_EDT_verify_password);
        update_EDT_new_password = view.findViewById(R.id.update_EDT_new_password);
        update_EDT_password = view.findViewById(R.id.update_EDT_password);
        update_TXV_name_password = view.findViewById(R.id.update_TXV_name_password);
        update_IMG_img = view.findViewById(R.id.update_IMG_img);
    }

    private boolean updatePassword() {
        if (update_EDT_password.getText().toString().equals(EncryptionManager.decrypt(currentPassword.getPassword()))) {
            if (update_EDT_verify_password.getText().toString().equals(update_EDT_new_password.getText().toString())) {
                currentPassword.setPassword(EncryptionManager.encrypt(update_EDT_new_password.getText().toString()));
                passwordViewModel.updatePassword(currentPassword);
                return true;
            }
        }
        return false;
    }


}