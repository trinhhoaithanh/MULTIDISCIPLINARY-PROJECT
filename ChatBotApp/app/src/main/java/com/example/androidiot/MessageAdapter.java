package com.example.androidiot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private Context context;
    private List<MessageClass> messageList;

    public MessageAdapter(Context context, List<MessageClass> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_item_list, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        MessageClass message = messageList.get(position);
        if (message.sender == 0) {
            FrameLayout user_message = getUserLayout();
            holder.linear_layout.addView(user_message);
            TextView message_et = user_message.findViewById(R.id.message_tv_user);
            message_et.setText(message.message);
        } else if (message.sender == 1) {
            FrameLayout bot_message = getBotLayout();
            holder.linear_layout.addView(bot_message);
            TextView message_et = bot_message.findViewById(R.id.message_tv_user);
            message_et.setText(message.message);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public FrameLayout getUserLayout() {
        LayoutInflater inflater = LayoutInflater.from(context);
        return (FrameLayout) inflater.inflate(R.layout.user_message_box, null);
    }

    public FrameLayout getBotLayout() {
        LayoutInflater inflater = LayoutInflater.from(context);
        return (FrameLayout) inflater.inflate(R.layout.bot_message_box, null);
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linear_layout;

        MessageViewHolder(View itemView) {
            super(itemView);
            linear_layout = itemView.findViewById(R.id.linear_layout);
        }
    }
}
