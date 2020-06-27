package com.example.taskapp2.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskapp2.App;
import com.example.taskapp2.R;
import com.example.taskapp2.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class FormFragment extends Fragment {
    private EditText editTitle;
    private EditText editDesc;
    private FloatingActionButton floatingActionButton;
    private int position;
    private String requestKey = "form";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialisation(view);
        setOnClickListeners();
        if (getArguments() != null) {
            Task taskModel = (Task) getArguments().getSerializable("keyTask");
            position = getArguments().getInt("keyPosition");
            assert taskModel != null;
            editTitle.setText(taskModel.getTitle());
            editDesc.setText(taskModel.getDesc());
        }
    }

    private void initialisation(View view) {
        editTitle = view.findViewById(R.id.editTitle);
        editDesc = view.findViewById(R.id.editDesc);
        floatingActionButton = view.findViewById(R.id.fab);
    }

    private void setOnClickListeners() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Task task = new Task(editTitle.getText().toString().trim(), editDesc.getText().toString().trim());
                if (task.getTitle().isEmpty()) {
                    Toast.makeText(getActivity(), "Введите title!", Toast.LENGTH_SHORT).show();
                    return;
                }
                App.getInstance().getDatabase().taskDao().insert(task);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putSerializable("task", task);
                getParentFragmentManager().setFragmentResult(requestKey, bundle);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);


                InputMethodManager inputManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert inputManager != null;
                inputManager.hideSoftInputFromWindow(Objects.requireNonNull(requireActivity().getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                navController.navigateUp();
            }
        });
    }
}
