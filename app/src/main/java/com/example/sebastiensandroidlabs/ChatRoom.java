package com.example.sebastiensandroidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.collection.ArraySet;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sebastiensandroidlabs.data.ChatMessage;
import com.example.sebastiensandroidlabs.data.ChatMessageDAO;
import com.example.sebastiensandroidlabs.data.ChatRoomViewModel;
import com.example.sebastiensandroidlabs.data.MessageDatabase;
import com.example.sebastiensandroidlabs.data.MessageDetailsFragment;
import com.example.sebastiensandroidlabs.databinding.ActivityChatRoomBinding;
import com.example.sebastiensandroidlabs.databinding.RecievedMessageBinding;
import com.example.sebastiensandroidlabs.databinding.SentMessageBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    ArrayList<ChatMessage> messagesToDelete = new ArrayList<>();
    private RecyclerView.Adapter myAdapter;
    ChatRoomViewModel chatModel;
    ChatMessageDAO messageDAO;
    View itemView;
    String messageIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "MessageDatabase").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        messageDAO = db.cmDAO();


        setSupportActionBar(binding.myToolbar);



        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if(messages == null)
        {
            chatModel.messages.setValue(messages = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( messageDAO.getAllMessages() ); //Once you get the data from database
                binding.recyclerView.setAdapter( myAdapter ); //You can then load the RecyclerView
            });
        }


        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            int position;
            if (messages.size() == 0){
                position = 0;
            }else{
                position = messages.get(messages.size() - 1).getId() + 1;
            }

            ChatMessage message = new ChatMessage();
            message.setChatMessage(position, binding.textInput.getText().toString(), currentDateAndTime, true);
            messages.add(message);
            messageDAO.instertMessage(message);

            myAdapter.notifyItemInserted(position);
            //notifyItemRemoved
            //notifyDatasetChanged

            binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            int position;
            if (messages.size() == 0){
                position = 0;
            }else{
                position = messages.get(messages.size() - 1).getId() + 1;
            }

            ChatMessage message = new ChatMessage();
            message.setChatMessage(position, binding.textInput.getText().toString(), currentDateAndTime, false);
            messages.add(message);
            messageDAO.instertMessage(message);

            myAdapter.notifyItemInserted(position);
            //notifyItemRemoved
            //notifyDatasetChanged

            binding.textInput.setText("");
        });


        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding sentBinding = SentMessageBinding.inflate(getLayoutInflater());
                RecievedMessageBinding receivedBinding = RecievedMessageBinding.inflate(getLayoutInflater());
                if (viewType == 0){
                    itemView = sentBinding.getRoot();
                    return new MyRowHolder(sentBinding.getRoot(), chatModel, messageDAO, myAdapter);
                }else if (viewType == 1){
                    itemView = receivedBinding.getRoot();
                    return new MyRowHolder(receivedBinding.getRoot(), chatModel, messageDAO, myAdapter);
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
                    if (messages.get(position).isSentOrRecieved()){
                        return 0;
                    }
                    else{
                        return 1;
                    }

            }
        });

        chatModel.selectedMessage.observe(this, (newMessageValue) -> {


            MessageDetailsFragment chatFragment = new MessageDetailsFragment( newMessageValue );

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(
                            R.id.fragmentLocation,
                            chatFragment
                    )
                    .addToBackStack("")
                    .commit();

        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch( item.getItemId() ) {

            case R.id.delete_message:

                if(messages.size() > 0){
                    messagesToDelete.clear();
                    messagesToDelete.addAll(messages);

                    AlertDialog.Builder builder = new AlertDialog.Builder( itemView.getContext() );

                    builder.setTitle("Question:")
                            .setMessage("Do you want to delete all " + messagesToDelete.size() + " messages?")
                            .setNegativeButton("No", (dialog, which) -> {})
                            .setPositiveButton("Yes", (dialog, which) -> {

                                messages.clear();
                                for (int i = 0 ; i < messagesToDelete.size() ; i++){
                                    messageDAO.deleteMessage(messagesToDelete.get(i));
                                }
                                myAdapter.notifyDataSetChanged();

                                Snackbar.make(itemView, "You deleted " + messagesToDelete.size() + " messages.", Snackbar.LENGTH_LONG).setAction("Undo", clck ->{

                                    for (ChatMessage message : messagesToDelete){
                                        messageDAO.instertMessage(message);
                                        messages.add(message);
                                        myAdapter.notifyItemInserted(message.getId());
                                    }

                                }).show();

                            }).create().show();

                }else{
                    Toast.makeText(getApplicationContext(), "There are no messages to delete.", Toast.LENGTH_SHORT).show();
                }

                break;
        }

        return true;
    }




}

class MyRowHolder extends RecyclerView.ViewHolder {

    TextView messageText;
    TextView timeText;


    public MyRowHolder(@NonNull View itemView, ChatRoomViewModel chatModel, ChatMessageDAO messageDAO, RecyclerView.Adapter myAdapter) {

        super(itemView);

        itemView.setOnClickListener( click ->{

            int position = getAbsoluteAdapterPosition();
            ChatMessage selected = chatModel.messages.getValue().get(position);

            chatModel.selectedMessage.postValue(selected);

        });

        messageText = itemView.findViewById(R.id.messageText);
        timeText = itemView.findViewById(R.id.timeText);
    }
}