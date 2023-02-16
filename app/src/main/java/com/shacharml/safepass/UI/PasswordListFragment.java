package com.shacharml.safepass.UI;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;
import static androidx.recyclerview.widget.ItemTouchHelper.RIGHT;
import static androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shacharml.safepass.Adapters.PasswordsAdapter;
import com.shacharml.safepass.Entities.Password;
import com.shacharml.safepass.R;
import com.shacharml.safepass.Utils.EncryptionManager;
import com.shacharml.safepass.ViewModels.PasswordViewModel;

import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class PasswordListFragment extends Fragment {

    ClipboardManager clipboardManager;
    FrameLayout layout;
    private PasswordViewModel passwordViewModel;
    private View view;
    private PasswordsAdapter adapter;
    SimpleCallback simpleCallback = new SimpleCallback(0, LEFT | RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            switch (direction) {
                case LEFT: {
                    passwordViewModel.delete(Objects.requireNonNull(passwordViewModel.getAllPasswords().getValue()).get(position));
                    adapter.notifyItemRemoved(position);
                    break;
                }

                case RIGHT: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    Navigation.findNavController(view).navigate(R.id.action_passwordListFragment_to_editPasswordFragment, bundle);
                }
                break;

                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }

        }


        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftLabel("Delete")
                    .setSwipeLeftLabelColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lightBlue))
                    .addSwipeRightActionIcon(R.drawable.ic_edit_w)
                    .addSwipeRightLabel("Edit")
                    .setSwipeRightLabelColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public PasswordListFragment() {
    }

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
        view = inflater.inflate(R.layout.fragment_password_list, container, false);

        layout = view.findViewById(R.id.layout_list);
        clipboardManager = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);

        //Init RecyclerView
        RecyclerView password_RCV_all_password = view.findViewById(R.id.password_RCV_all_password);
        password_RCV_all_password.setLayoutManager(new LinearLayoutManager(requireActivity()));
        password_RCV_all_password.setHasFixedSize(true);

        //create adapter
        adapter = new PasswordsAdapter();
        password_RCV_all_password.setAdapter(adapter);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(password_RCV_all_password);

        adapter.setPasswordListener(new PasswordsAdapter.PasswordListener() {

            @Override
            public void favorite(Password password) {
                Log.d("ptt", "password favorite");
                // TODO: 16/02/2023 finish favorite section

            }

            @Override
            public void passwordClicked(Password password) {
                Log.d("ptt", "passwordClicked");
                popUp(password);
            }

            @Override
            public void copy(Password password) {
                Log.d("ptt", "copy Password");
                clipboardManager.setPrimaryClip(ClipData.newPlainText("copyText", EncryptionManager.decrypt(password.getPassword())));
            }
        });


        //observe on the live data in room
        passwordViewModel.getAllPasswords().observe(requireActivity(), passwords -> {
            adapter.setPasswords(passwords);
            adapter.setPasswordsFiltered(passwords);
        });

        //Add Button
        AppCompatImageButton main_BTN_add = view.findViewById(R.id.main_BTN_add);
        main_BTN_add.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(PasswordListFragmentDirections.actionPasswordListFragmentToPasswordShowFragment()));

        SearchView main_EDT_search = view.findViewById(R.id.main_EDT_search);
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

    @SuppressLint("ClickableViewAccessibility")
    private void popUp(Password password) {
        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popUpView = inflater.inflate(R.layout.popup_password_info, null);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);

        ShapeableImageView info_IMG_img = popUpView.findViewById(R.id.info_IMG_img);
        Button info_BTN_ok = popUpView.findViewById(R.id.info_BTN_ok);
        TextView info_TXV_name_password = popUpView.findViewById(R.id.info_TXV_name_password);
        MaterialTextView info_EDT_password = popUpView.findViewById(R.id.info_EDT_password);
        MaterialTextView info_EDT_url = popUpView.findViewById(R.id.info_EDT_url);

        info_IMG_img.setImageResource(Integer.parseInt(password.getImg()));
        info_TXV_name_password.setText(password.getName());
        info_EDT_password.setText(EncryptionManager.decrypt(password.getPassword()));
        info_EDT_url.setText(password.getUrlToSite());
        info_BTN_ok.setOnClickListener(v -> popupWindow.dismiss());
        popUpView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
        layout.post(() -> popupWindow.showAtLocation(layout, Gravity.NO_GRAVITY, 0, 0));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}