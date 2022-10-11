package com.example.sebastiensandroidlabs.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public MutableLiveData<String> textView = new MutableLiveData<>();
    public MutableLiveData<Boolean> checkbox = new MutableLiveData<>();
    public MutableLiveData<Boolean> switch1 = new MutableLiveData<>();
    public MutableLiveData<Boolean> radioButton1 = new MutableLiveData<>();
    public MutableLiveData<Boolean> radioButton2 = new MutableLiveData<>();
    public MutableLiveData<Boolean> radioButton3 = new MutableLiveData<>();
    public MutableLiveData<Boolean> button = new MutableLiveData<>();


}
