package com.mio.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleClient {
	  public static void main(String[] args) throws Exception {
	        String host = args[0];
	        int port = Integer.parseInt(args[1]);
	        //如果你只用了一个EventLoopGroup,那么它将被用在boos和worker上。只是客户端用不上bosss事件池。
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        
	        try {
	        	//Bootstrap 和ServerBootstrap比较像，只是Bootstrap主要用于客户端或无连接的channel.
	            Bootstrap b = new Bootstrap(); 
	            b.group(workerGroup); 
	            b.channel(NioSocketChannel.class); //这里我采用NioSocketChannel而不是NioServerSocketChannel。
	            b.option(ChannelOption.SO_KEEPALIVE, true); 
	            b.handler(new ChannelInitializer<SocketChannel>() {
	                @Override
	                public void initChannel(SocketChannel ch) throws Exception {
	                    ch.pipeline().addLast(new SimpleClientHandler());
	                }
	            });
	            
	            // Start the client.
	            ChannelFuture f = b.connect(host, port).sync(); //我们应该调用connect()方法而不是bind()方法。

	            // Wait until the connection is closed.
	            f.channel().closeFuture().sync();
	        } finally {
	            workerGroup.shutdownGracefully();
	        }
	    }
}
