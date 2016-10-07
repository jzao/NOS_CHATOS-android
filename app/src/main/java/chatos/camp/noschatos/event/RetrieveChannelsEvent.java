package chatos.camp.noschatos.event;

import chatos.camp.noschatos.model.Channel;

/**
 * Created by joaozao on 07/10/16.
 */

public class RetrieveChannelsEvent {

    private final Channel mChannel;

    public RetrieveChannelsEvent(Channel pChannel) {
        mChannel = pChannel;
    }

    public Channel getChannel() {
        return mChannel;
    }

}
