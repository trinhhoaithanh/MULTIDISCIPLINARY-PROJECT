package com.example.androidiot;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidiot.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainActivity;
    private ArrayList<MessageClass> messageList;
    private final int USER = 0;
    private final int BOT = 1;
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainActivity.getRoot());
        messageList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mainActivity.messageList.setLayoutManager(linearLayoutManager);
        adapter = new MessageAdapter(this, messageList);
        adapter.setHasStableIds(true);
        mainActivity.messageList.setAdapter(adapter);
        mainActivity.sendBtn.setOnClickListener(view -> {
            String msg = mainActivity.messageBox.getText().toString();
            sendMessage(msg);
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
            mainActivity.messageBox.setText("");
        });
    }

    void sendMessage(String message) {
        MessageClass userMessage = null;
        if (message.isEmpty()) {
            Toast.makeText(this,"Please type your message",Toast.LENGTH_SHORT).show();
        }
        else{
            userMessage = new MessageClass(message, USER);
            messageList.add(userMessage);
            adapter.notifyDataSetChanged();
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://2bfd04ec3146.ngrok.io/webhooks/rest/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        MessageSender messageSender = retrofit.create(MessageSender.class);
        Call<ArrayList<BotResponse>> response = messageSender.messageSender(userMessage);
        response.enqueue(new Callback<ArrayList<BotResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<BotResponse>> call, Response<ArrayList<BotResponse>> response) {
                if (response.body() != null || response.body().size() != 0) {
                    BotResponse message = response.body().get(0);
                    messageList.add(new MessageClass(message.getText(), BOT));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BotResponse>> call, Throwable t) {
                String message = "Check your connection";
                messageList.add(new MessageClass(message, BOT));
            }
        });
    }
}
