package chatos.camp.noschatos.model;

/**
 * Created by joaozao on 08/10/16.
 */

public class Message {

    public static final int TYPE_MESSAGE = 0;

    private String mMessage;
    private String mUsername;

    private Message() {}

    public String getMessage() {
        return mMessage;
    };

    public String getUsername() {
        return mUsername;
    };


    public static class Builder {
        private final int mType;
        private String mUsername;
        private String mMessage;

        public Builder(int type) {
            mType = type;
        }

        public Builder username(String username) {
            mUsername = username;
            return this;
        }

        public Builder message(String message) {
            mMessage = message;
            return this;
        }

        public Message build() {
            Message message = new Message();
            message.mUsername = mUsername;
            message.mMessage = mMessage;
            return message;
        }
    }
}

