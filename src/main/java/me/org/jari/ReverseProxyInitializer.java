package me.org.jari;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ReverseProxyInitializer extends ChannelInitializer<SocketChannel> {

    private final ReverseProxyServer server;

    public ReverseProxyInitializer(ReverseProxyServer server) {

        this.server = server;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        var pipeline = channel.pipeline();

        pipeline.addLast(new ReverseProxyHandler(server));
    }
}
