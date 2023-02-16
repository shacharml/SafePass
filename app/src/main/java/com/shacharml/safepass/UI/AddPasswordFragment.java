package com.shacharml.safepass.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.shacharml.safepass.Adapters.CompaniesAdapter;
import com.shacharml.safepass.Utils.EncryptionManager;
import com.shacharml.safepass.Entities.Company;
import com.shacharml.safepass.Entities.CompanyList;
import com.shacharml.safepass.Entities.Password;
import com.shacharml.safepass.R;
import com.shacharml.safepass.ViewModels.PasswordViewModel;

import java.net.URL;
import java.util.regex.Pattern;


public class AddPasswordFragment extends Fragment {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{4,}" +               //at least 4 characters
            "$");

    private TextInputLayout new_TIL_name;
    private TextInputLayout new_TIL_url;
    private TextInputLayout new_TIL_password;
    private TextInputLayout new_TIL_verify;
    private MaterialButton new_BTN_Save;
    private ImageButton new_BTN_back;
    private RecyclerView new_RYC_images;
    private FloatingActionButton new_FAB_img_edit;
//    private ShapeableImageView new_IMG_password;
    private AppCompatImageView new_IMG_password;

    private View view;
    private PasswordViewModel passwordViewModel;
    String currentIcon;

    private CompanyList companyList ;
    private CompaniesAdapter adapter ;

    public AddPasswordFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//create view model
        passwordViewModel = new ViewModelProvider(requireActivity()).get(PasswordViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_password, container, false);

        initViews(view);
        adapter = new CompaniesAdapter();
        companyList = new CompanyList();

        adapter.setCompanyListener(new CompaniesAdapter.CompanyListener() {
            @Override
            public void companyClicked(Company company) {
                // TODO: 09/02/2023 complete
                new_IMG_password.setImageResource(company.getIcon());
//                new_IMG_password.setBackground(requireContext().getDrawable(company.getIcon()));
                currentIcon = String.valueOf(company.getIcon());
            }
        });

        //init the companies list
        adapter.setPasswords(companyList.getCompanyData());
        new_RYC_images.setAdapter(adapter);

        new_BTN_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 06/02/2023 save methode
                savePassword(view);
            }
        });

        return view;
    }

    private void savePassword(View view) {

        //Check validation form
        if (!validateUsername(new_TIL_name))
            return;

        if (!validateUrl(new_TIL_url))
            return;

        if (!validatePassword(new_TIL_password))
            return;

        if (!validatePassword(new_TIL_verify))
            return;

        String password = new_TIL_password.getEditText().getText().toString();
        String verify = new_TIL_verify.getEditText().getText().toString();
        if (!password.equals(verify)) {
            Toast.makeText(requireActivity(), "The Passwords arent The same", Toast.LENGTH_LONG).show();
            new_TIL_verify.setError("Enter the Same Passwords");
            return;
        }

        String name = new_TIL_name.getEditText().getText().toString();
        String url = new_TIL_url.getEditText().getText().toString();
        Password newPassword = new Password(name, EncryptionManager.encrypt(password));
//        newPassword.setImg(String.valueOf(new_IMG_password.getDrawable()));

            newPassword.setImg(currentIcon);

        newPassword.setUrlToSite(url);

        passwordViewModel.insert(newPassword);
        Navigation.findNavController(requireView()).navigate(AddPasswordFragmentDirections.actionPasswordShowFragmentToPasswordListFragment());

    }

    private void initViews(View view) {
        new_TIL_name = view.findViewById(R.id.new_TIL_name);
        new_TIL_url = view.findViewById(R.id.new_TIL_url);
        new_TIL_password = view.findViewById(R.id.new_TIL_password);
        new_TIL_verify = view.findViewById(R.id.new_TIL_verify);
        new_BTN_Save = view.findViewById(R.id.new_BTN_Save);
//        new_BTN_back = view.findViewById(R.id.new_BTN_back);
        new_RYC_images = view.findViewById(R.id.new_RYC_images);
        new_FAB_img_edit = view.findViewById(R.id.new_FAB_img_edit);
        new_IMG_password = view.findViewById(R.id.new_IMG_password);

    }


    private boolean validateUsername(TextInputLayout textInputUsername) {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            textInputUsername.setError("Username too long");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }

    private boolean validateUrl(TextInputLayout textInputUrl) {
        String urlInput = textInputUrl.getEditText().getText().toString().trim();

        if (urlInput.isEmpty()) {
            textInputUrl.setError("Field can't be empty");
            return false;
        } else {
            try {
                new URL(urlInput).toURI();
            } catch (Exception e) {
                Log.d("new password", e.toString());
                textInputUrl.setError("Incorrect URL");
                return false;
            }
            textInputUrl.setError(null);
            return true;
        }
    }

    private boolean validatePassword(TextInputLayout textInputPassword) {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputPassword.setError("Password too weak");
            //IDC if the password is weak or not - just want to show him that
            return true;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }
}