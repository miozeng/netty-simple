package com.mio.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleServer {
	 private int port;
	    
	    public SimpleServer(int port) {
	        this.port = port;
	    }
	    
	    public void run() throws Exception {
	    	/**
	    	 * NioEventLoopGroup是一个多线程的I/O操作事件循环池，Netty为各种传输方式提供了多种EventLoopGroup的实现。我们可以像上面的例子一样来实现一个服务器应用，
	    	 * 代码中的两个NioEventLoopGroup都会被使用到。第一个NioEventLoopGroup通常被称为'boss'，用于接收所有连接到服务器端的客户端连接。
	    	 * 第二个被称为'worker',当有新的连接进来时将会被注册到worker中。至于要在EventLoopGroup创建多少个线程，
	    	 * 映射多少个Channel可以在EventLoopGroup的构造方法中进行配置。
	    	 */
	        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	        	//ServerBootstrap是一个用于设置服务器的辅助类。你可以直接用Channel来设置服务器,但是这样做会比较麻烦，大多数情况下还是不要这样做。
	            ServerBootstrap b = new ServerBootstrap(); // (2)
	            b.group(bossGroup, workerGroup)
	             .channel(NioServerSocketChannel.class) // (3)这里我们采用NioServerSocketChannel类来实例化一个进来的连接
	          // (4)我们总是为新连接到服务器的handler分配一个新的Channel. ChannelInitializer用于配置新生成的Channel, 就和你通过配置ChannelPipeline来配置Channel是一样的效果。
	             .childHandler(new ChannelInitializer<SocketChannel>() { 
	                 @Override
	                 public void initChannel(SocketChannel ch) throws Exception {
	                     ch.pipeline().addLast(new SimpleServerHandler());
	                 }
	             })
	             .option(ChannelOption.SO_BACKLOG, 128)          // (5)向指定的Channel设置参数
	             .childOption(ChannelOption.SO_KEEPALIVE, true); 
	            // (6)代码中用到了option(), childOption()两个不同的方法。option() 方法用于设置监听套接字。childOption()则用于设置连接到服务器的客户端套接字。
	    
	            // Bind and start to accept incoming connections.
	            ChannelFuture f = b.bind(port).sync(); // (7)
	    
	            // Wait until the server socket is closed.
	            // In this example, this does not happen, but you can do that to gracefully
	            // shut down your server.
	            f.channel().closeFuture().sync();
	        } finally {
	            workerGroup.shutdownGracefully();
	            bossGroup.shutdownGracefully();
	        }
	    }
	    
	    public static void main(String[] args) throws Exception {
	        int port;
	        if (args.length > 0) {
	            port = Integer.parseInt(args[0]);
	        } else {
	            port = 8080;
	        }
	        new SimpleServer(port).run();
	    }
}
