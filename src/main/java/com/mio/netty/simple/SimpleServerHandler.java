package com.mio.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 根据官方列子
 * 
 * ChannelInboundHandlerAdapter是ChannelInboundHandler接口的实现.ChannelInboundHandler的很多事件方法你都可以继承。目前我们只需要继承ChannelInboundHandlerAdapter无需自己实现接口。
 * @author admin
 *
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 重写channelRead()方法，当你收到消息时会调用该方法，在本例中我们收到的消息是一个ByteBuf
	 */
	  @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) { 
	   //要发送新消息，我们需要一个新的buffer来存放消息。我们要写入一个32位的整数，因此我们需要一个4字节大小的ByteBuf。
		  //我们通过ChannelHandlerContext.alloc()来获取当前的ByteBufAllocator并且申请一个新的buffer
		   final ByteBuf time = ctx.alloc().buffer(4);   
	        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));  
	          
	        final ChannelFuture f = ctx.writeAndFlush(time); // (3) 
	        //另一个需要注意的地方就是ChannelHandlerContext.write() (writeAndFlush())方法返回一个ChannelFuture对象。
	        //ChannelFuture表示I/O操作还未发生，意味着任何请求都不会立即完成因为Netty所有的操作都是异步的。下面的代码可能会在消息发出前关闭连接:
	        f.addListener(new ChannelFutureListener() {  
	            @Override  
	            public void operationComplete(ChannelFuture future) {  
	                assert f == future;  
	                ctx.close();  
	            }  
	        }); // (4) 
	    }
	/**
	 * 当有I/O错误或其它异常信息从Netty中抛出时exceptionCaught()方法将会被调用。大多数情况下，捕捉到异常后应该将其记录到日志中并且关闭channel，
	 */
		    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
	        cause.printStackTrace();
	        ctx.close();
	    }
}
