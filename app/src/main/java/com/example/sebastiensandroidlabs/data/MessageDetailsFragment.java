package com.example.sebastiensandroidlabs.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sebastiensandroidlabs.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {

    ChatMessage selected;
    DetailsLayoutBinding binding;
    String sentOrRecieved;

    public MessageDetailsFragment (ChatMessage message){
        selected = message;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = DetailsLayoutBinding.inflate(inflater);
        Integer id = selected.id;

        if (selected.sentOrRecieved){
            sentOrRecieved = "Sent";
        }else {
            sentOrRecieved = "Recieved";
        }

        binding.messageText.setText(selected.getMessage());
        binding.timeText.setText(selected.timeSent);
        binding.sentOrRecievedText.setText(sentOrRecieved);
        binding.databaseIdText.setText(id.toString());

        System.out.println("data set, fragment running");



        return binding.getRoot();
    }

}
