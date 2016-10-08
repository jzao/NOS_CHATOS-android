package chatos.camp.noschatos;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

/**
 * Created by JorgeMiguel on 08/10/2016.
 */

public class SocketManager {

    public static SocketManager mSocketInstance;
    private Socket mSocket;
    private Activity context;
    private String user;

    public static synchronized SocketManager getInstance(Activity context, String username){
        if(mSocketInstance == null){
            mSocketInstance = new SocketManager();
        }
        mSocketInstance.context = context;
        mSocketInstance.user = username;
        return mSocketInstance;
    }

    public SocketManager(){
        try {
        IO.Options opts = new IO.Options();

            IO.setDefaultSSLContext(SSLContext.getDefault());
            opts.transports = new String[] {WebSocket.NAME};
            mSocket = IO.socket("domain",opts);

            mSocket.on(Socket.EVENT_CONNECT,onConnect);
            mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
            //mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            //mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
            mSocket.on("on_room", onRoom);

            mSocket.on("new_message", onNewMessage);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    private Emitter.Listener onRoom = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {

                        if(data.getString("status").equals("200")) {

                            String channelId = data.getString("channel_id");
                            //TODO verificar se é o channel actual !!!

                            //List<>

                            JSONArray messages = data.getJSONArray("messages");

                            for(int i=0; i<messages.length(); i++){
                                JSONObject obj = messages.getJSONObject(i);
                                String user = obj.getString("user");
                                String msg = obj.getString("message");
                            }

                            //TODO send list to UI
                        }
                        else
                        {
                            //TODO fodeu
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }

                    //addMessage(username, message);
                }
            });
        }
    };

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
          /*  getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!isConnected) {
                        if(null!=mUsername)
                            mSocket.emit("add user", mUsername);
                        Toast.makeText(getActivity().getApplicationContext(),
                                R.string.connect, Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });*/
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
          /*  getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isConnected = false;
                    Toast.makeText(getActivity().getApplicationContext(),
                            R.string.disconnect, Toast.LENGTH_LONG).show();
                }
            });*/
        }
    };

    public void joinRoom(int roomID) {
        mSocket.emit("open_room", roomID);
    }

    public void sendMessage(int roomID, String message) {
        String msg = "{\"channel_id\":\""+roomID+"\",user\":\""+user+"\",message\":\""+message+"\"}";
        mSocket.emit("new_message",msg);
    }

}
