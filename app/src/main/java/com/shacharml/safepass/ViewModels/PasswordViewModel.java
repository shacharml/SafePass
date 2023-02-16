package com.shacharml.safepass.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shacharml.safepass.Entities.Password;
import com.shacharml.safepass.Repositories.PasswordRepository;

import java.util.List;

public class PasswordViewModel extends AndroidViewModel {

    private final PasswordRepository passwordRepository;
    private final LiveData<List<Password>> allPasswords;

    public PasswordViewModel(@NonNull Application application) {
        super(application);
        passwordRepository = new PasswordRepository(application);
        allPasswords = passwordRepository.getAllPasswords();
    }

    public void insert(Password password) {
        passwordRepository.insert(password);
    }

    public void updatePassword(Password password) {
        passwordRepository.updatePassword(password);
    }

    public void deleteAll() {
        passwordRepository.deleteAll();
    }

    public void delete(Password password) {
        passwordRepository.delete(password);
    }

    public LiveData<List<Password>> getAllPasswords() {
        return allPasswords;
    }

}
