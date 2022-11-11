package com.example.sebastiensandroidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sebastiensandroidlabs.data.ChatMessage;
import com.example.sebastiensandroidlabs.data.ChatRoomViewModel;
import com.example.sebastiensandroidlabs.databinding.ActivityChatRoomBinding;
import com.example.sebastiensandroidlabs.databinding.RecievedMessageBinding;
import com.example.sebastiensandroidlabs.databinding.SentMessageBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    private RecyclerView.Adapter myAdapter;
//    ChatRoomViewModel chatModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
//        messages = chatModel.messages.getValue();
//        if(messages == null)
//        {
//            chatModel.messages.postValue( messages = new ArrayList<ChatMessage>());
//        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));



        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding sentBinding = SentMessageBinding.inflate(getLayoutInflater());
                RecievedMessageBinding receivedBinding = RecievedMessageBinding.inflate(getLayoutInflater());
                if (viewType == 0){
                    return new MyRowHolder(sentBinding.getRoot());
                }else if (viewType == 1){
                    return new MyRowHolder(receivedBinding.getRoot());
                }else{
                    return null;
                }

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            public int getItemViewType(int position){
                if (messages.get(position).isSentButton()){
                    return 0;
                }
                else{
                    return 1;
                }
            }
        });


        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage message = new ChatMessage();
            message.setChatMessage(binding.textInput.getText().toString(), currentDateAndTime, true);
            messages.add(message);

            myAdapter.notifyItemInserted(messages.size() - 1);
                    //notifyItemRemoved
                    //notifyDatasetChanged

            binding.textInput.setText("");
        });

        binding.recieveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage message = new ChatMessage();
            message.setChatMessage(binding.textInput.getText().toString(), currentDateAndTime, false);
            messages.add(message);

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