package com.example.taskapp2.ui.onBoard;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.taskapp2.Prefs;
import com.example.taskapp2.R;
import com.google.android.material.tabs.TabLayout;

public class BoardFragment extends Fragment {
    private PageAdapter pageAdapter;
    private ViewPager viewPager;
    Button buttonSkip;
    TabLayout tabLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialisation(view);
        setOnClickListeners();
        viewPager.setAdapter(pageAdapter);
        onBackPressedCallback();
        buttonSkip = view.findViewById(R.id.btn_skip);
        buttonSkip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
                buttonSkip.setVisibility(View.INVISIBLE);
            }
        });
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager,true);
        onBackPressedCallback();

    }

    private void onBackPressedCallback() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        };
        requireActivity()
                .getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), onBackPressedCallback);
    }

    private void setOnClickListeners() {

        pageAdapter.setOnStartClickLListener(new PageAdapter.onStartClickLListener() {
            @Override
            public void onStart() {
                new Prefs(requireActivity()).isShown(true);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigateUp();
            }
        });
    }

    private void initialisation(View view) {
        pageAdapter = new PageAdapter();
        viewPager = view.findViewById(R.id.viewPager);
        pageAdapter = new PageAdapter();
    }
}
