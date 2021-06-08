package com.example.helpbuy.ui.addoffer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddOfferViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddOfferViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}