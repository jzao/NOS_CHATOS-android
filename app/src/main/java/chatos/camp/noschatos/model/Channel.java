package chatos.camp.noschatos.model;

/**
 * Created by joaozao on 07/10/16.
 */

public class Channel {
    private int id;
    private String name;
    private String channelUri;


    public int getId() {
        return id;
    }

    public void setId(int pId) {
        id = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getChannelUri() {
        return channelUri;
    }

    public void setChannelUri(String pChannelUri) {
        channelUri = pChannelUri;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", channelUri='" + channelUri + '\'' +
                '}';
    }
}
