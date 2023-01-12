package me.org.jari;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class RelayInitializer extends ChannelInitializer<SocketChannel> {

    private final Node node;
    private final Channel clientChannel;

    public RelayInitializer(Node node, Channel channel) {
        this.clientChannel = channel;
        this.node = node;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        var pipeline = channel.pipeline();

        pipeline.addLast(new RelayHandler(node, clientChannel));
    }
}
