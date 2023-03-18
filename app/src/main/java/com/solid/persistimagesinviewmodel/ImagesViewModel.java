package com.solid.persistimagesinviewmodel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class ImagesViewModel extends ViewModel{

    private MutableLiveData<ArrayList<Uri>> imageL;

    public void setImageL(ArrayList<Uri> list) {
        if (imageL == null) {
            imageL = new MutableLiveData<>();
        }
        imageL.setValue(list);
    }

    public LiveData<ArrayList<Uri>> getImageL() {
        if (imageL == null) {
            imageL = new MutableLiveData<>();
        }
        return imageL;
    }
}
