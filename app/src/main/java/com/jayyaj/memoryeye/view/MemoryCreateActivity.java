package com.jayyaj.memoryeye.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.jayyaj.memoryeye.R;
import com.jayyaj.memoryeye.databinding.ActivityMemoryCreateBinding;
import com.jayyaj.memoryeye.model.Memory;
import com.jayyaj.memoryeye.model.Type;
import com.jayyaj.memoryeye.viewmodel.MemoryViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoryCreateActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 10;
    private MemoryViewModel memoryViewModel;
    private ActivityMemoryCreateBinding binding;
    private List<String> imagesUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_create);

        imagesUrl = new ArrayList<>();
        binding = DataBindingUtil.setContentView(MemoryCreateActivity.this, R.layout.activity_memory_create);
        memoryViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MemoryCreateActivity.this.getApplication())
                .create(MemoryViewModel.class);

        binding.memoryCreateImages.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(galleryIntent,"Select Picture"), 1);
        });

        binding.memoryCreateButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(binding.memoryTitle.getText())
                    && !TextUtils.isEmpty(binding.memoryDate.getText())
                    && !TextUtils.isEmpty(binding.memoryDescription.getText())
                    && !TextUtils.isEmpty(binding.memoryCreator.getText())) {
                Memory memory = new Memory();
                memory.setTitle(binding.memoryTitle.getText().toString());
                memory.setDate(new Timestamp(new Date()));
                memory.setType(Type.PERSON);
                memory.setDescription(binding.memoryDescription.getText().toString());
                memory.setCreator(binding.memoryCreator.getText().toString());
                memory.setImagesUrl(imagesUrl);
                memoryViewModel.createMemory(memory);
            } else {
                Toast.makeText(MemoryCreateActivity.this,
                        "All fields are required",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                imagesUrl.add(data.getData().toString());
            }
        }
    }
}