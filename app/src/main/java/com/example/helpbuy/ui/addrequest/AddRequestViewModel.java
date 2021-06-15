package com.example.helpbuy.ui.addrequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddRequestViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddRequestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Location:");
    }

    public LiveData<String> getText() {
        return mText;
    }
}