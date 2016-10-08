package chatos.camp.noschatos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import chatos.camp.noschatos.adapters.MessageAdapter;
import chatos.camp.noschatos.model.Message;

public class ChatMessagingActivity extends AppCompatActivity {


    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;
    private String mUsername;
    private MessageReceiver mBroadcastReceiver;
    private int roomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_messaging);
        mAdapter = new MessageAdapter(this, mMessages);
        mMessagesView = (RecyclerView) findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(this));
        mMessagesView.setAdapter(mAdapter);

        roomID = getIntent().getIntExtra("roomID", 0);
        SocketManager.getInstance(getApplicationContext()).joinRoom(roomID);
        Log.i("cenas", ""+getIntent().getIntExtra("roomID", 0));

        mBroadcastReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter("NewMessage");
        registerReceiver(mBroadcastReceiver, filter);

        mUsername = SocketManager.USERNAME;

        mInputMessageView = (EditText) findViewById(R.id.message_input);

        ImageButton sendButton = (ImageButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });

     //   startSendingMessages();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    private void startSendingMessages() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                addMessage("Binglas", "YOOOOOO");
                Log.i("_DEBUG", "threadddddd");
            }
        }, 2000);
    }

    private void attemptSend() {
        if (null == mUsername) return;


        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            mInputMessageView.requestFocus();
            return;
        }

        mInputMessageView.setText("");
        addMessage(mUsername, message);

        SocketManager.getInstance(getApplicationContext()).sendMessage(roomID, message);

    }

    private void addMessage(String username, String message) {
        mMessages.add(new Message.Builder(Message.TYPE_MESSAGE)
                .username(username).message(message).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            addMessage(intent.getStringExtra("user"), intent.getStringExtra("msg"));
            Log.i("cenas", intent.getStringExtra("msg"));
        }
    }


}
