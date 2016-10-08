package chatos.camp.noschatos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.net.ssl.SSLContext;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

/**
 * Created by JorgeMiguel on 08/10/2016.
 */

public class SocketManager {

    private static final String DOMAIN = "http://192.168.1.80:3000";

    public static final String USERNAME = "Binglas";
    private int currentRoom;

    private static SocketManager mSocketInstance;
    private Socket mSocket;
    private Context context;
    private String user = USERNAME;

    public static synchronized SocketManager getInstance(Context context){
        if(mSocketInstance == null){
            mSocketInstance = new SocketManager();
        }
        mSocketInstance.context = context;

        return mSocketInstance;
    }

    public void Connect(){
        Log.i("AQUI", "AQUI");
        mSocket.connect();
    }

    public SocketManager(){
        //mSocketInstance.user = USERNAME;

        try {

            IO.Options opts = new IO.Options();
            IO.setDefaultSSLContext(SSLContext.getDefault());
            opts.transports = new String[] {WebSocket.NAME};
            mSocket = IO.socket(DOMAIN);

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

                    JSONObject data = (JSONObject) args[0];
                    try {
                        Log.i("cenas", data.toString());
                        if(data.getString("status").equals("200")) {

                            String channelId = data.getString("channel_id");
                            //TODO verificar se é o channel actual !!!
                            if(Integer.parseInt(channelId) != currentRoom)
                                return;

                            JSONArray messages = data.getJSONArray("messages");

                            for(int i=0; i<messages.length(); i++){
                                JSONObject obj = messages.getJSONObject(i);
                                String user = obj.getString("user");
                                String msg = obj.getString("message");

                                Intent intent = new Intent();
                                intent.setAction("NewMessage");
                                intent.putExtra("user", user);
                                intent.putExtra("msg", msg);
                                context.sendBroadcast(intent);
                            }

                        }
                        else
                        {
                            //TODO fodeu
                        }
                    } catch (JSONException e) {
                        return;
                    }

        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            try {

                    JSONObject obj = (JSONObject) args[0];
                    Log.i("DEBUG", "new message: "+obj.toString());

                    String user = obj.getString("user");

                if(!user.equals(USERNAME)) {
                    String msg = obj.getString("message");
                    Intent intent = new Intent();
                    intent.setAction("NewMessage");
                    intent.putExtra("user", user);
                    intent.putExtra("msg", msg);
                    context.sendBroadcast(intent);
                }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


    };

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
        Log.i("conn", "connected");
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i("conn", "disconnected");
        }
    };

    public void joinRoom(int roomID) {
        currentRoom = roomID;
        /* {
                channel_id : roomID,
                user        : "def"
                }
         */
        //String jsonRoom = "{\"channel_id\":\""+roomID+"\",\"user\":\"ze\"}";
        //Log.i("JSONROOM", jsonRoom);
        try {
            JSONObject o = new JSONObject();

            o.put("channel_id", String.valueOf(roomID));
            o.put("user", user);
            mSocket.emit("open_room", o);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public void sendMessage(int roomID, String message) {
        //String msg = "{\"channel_id\":\""+roomID+"\",user\":\""+user+"\",message\":\""+message+"\"}";

        JSONObject o = new JSONObject();
        try {
            Date now = new Date();
            Long longTime = new Long(now.getTime()/1000);

            o.put("channel_id", String.valueOf(roomID));
            o.put("user", user);
            o.put("message", message);
            o.put("timestamp", longTime.intValue());
            mSocket.emit("new_message",o);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
