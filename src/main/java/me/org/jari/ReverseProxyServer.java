package me.org.jari;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Scanner;

public class ReverseProxyServer {

    private final NodeHandler nodeHandler;

    private final int port;
    private final EventLoopGroup bossGroup, workerGroup;

    public ReverseProxyServer(int port) {
        this.port = port;
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        this.nodeHandler = new NodeHandler(this, new LoadBalancerHandler());
    }

    public void start() {
        var bootstrap = new ServerBootstrap();

        try {
            var channel = bootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ReverseProxyInitializer(this))
                    .bind(port)
                    .sync()
                    .channel();

            var scanner = new Scanner(System.in);
            while (!scanner.nextLine().equals("exit")) {

            }

            nodeHandler.closeAll();
            channel.close();

            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    public NodeHandler getNodeHandler() {
        return nodeHandler;
    }
}
