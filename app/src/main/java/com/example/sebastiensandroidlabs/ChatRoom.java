package com.example.sebastiensandroidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sebastiensandroidlabs.databinding.ActivityChatRoomBinding;
import com.example.sebastiensandroidlabs.databinding.SentMessageBinding;

import java.util.ArrayList;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<String> messages = new ArrayList<>();
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));


        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                String obj = messages.get(position);
                holder.messageText.setText(obj);
                holder.timeText.setText("");
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            public int getItemViewType(int position){
                return 0;
            }
        });


        binding.sendButton.setOnClickListener(click -> {
            messages.add(binding.textInput.getText().toString());

            myAdapter.notifyItemInserted(messages.size() - 1);
                    //notifyItemRemoved
                    //notifyDatasetChanged

            binding.textInput.setText("");
        });

    }
}

class MyRowHolder extends RecyclerView.ViewHolder {

    TextView messageText;
    TextView timeText;


    public MyRowHolder(@NonNull View itemView) {

        super(itemView);
        messageText = itemView.findViewById(R.id.messageText);
        timeText = itemView.findViewById(R.id.timeText);
    }
}