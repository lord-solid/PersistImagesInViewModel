package com.solid.persistimagesinviewmodel;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.solid.persistimagesinviewmodel.databinding.FragmentMainBinding;
import java.util.ArrayList;

public class MainFragment extends Fragment{
    ArrayList<Uri> imageList = new ArrayList<>();
    private ImagesViewModel viewModel;
    private ImageAdapter imageAdapter;
    private FragmentMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Bind the views to the screen
        binding = FragmentMainBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this.requireActivity()).get(ImagesViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set up adapter and layout manager for recyclerview
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.requireContext()));
        imageAdapter = new ImageAdapter(this.requireContext(), imageList);
        binding.recyclerView.setAdapter(imageAdapter);

        //Observe list from the ViewModel
        viewModel.getImageL().observe(this.requireActivity(), list -> {
            imageList = list;
            imageAdapter.setImagesList(list);
        });

        binding.pickImageButton.setOnClickListener(v -> openUtility());
    }

    //Pick image from camera
    private void openUtility() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncherUtility.launch(intent);
    }

    //Set images from result of openUtility.
    ActivityResultLauncher<Intent> resultLauncherUtility =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageList.add(data != null ? data.getData() : null);
                            imageAdapter.setImagesList(imageList);
                            viewModel.setImageL(imageList);
                        }
                    });

    // Handle any configuration changes here
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        viewModel.setImageL(imageList);
    }

    //Cleanup binding for garbage collection
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}