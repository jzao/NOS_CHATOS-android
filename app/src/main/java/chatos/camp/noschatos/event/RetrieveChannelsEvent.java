package chatos.camp.noschatos.event;

import java.util.List;

import chatos.camp.noschatos.model.Channel;

/**
 * Created by joaozao on 07/10/16.
 */

public class RetrieveChannelsEvent {

    private final List<Channel> mChannels;

    public RetrieveChannelsEvent(List<Channel> pChannels) {
        mChannels = pChannels;
    }

    public List<Channel> getChannels() {
        return mChannels;
    }

}
