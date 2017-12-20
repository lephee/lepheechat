package lephee.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lephee.chat.server.ChatDecoder;
import lephee.chat.server.ChatEncoder;
import lephee.chat.server.ChatServerHandler;
import lephee.chat.util.ChatUtil;

public class ChatServer {

	public static final int PORT = 443;
	EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
	EventLoopGroup workerGroup = new NioEventLoopGroup();

	public void run() {
		try {
			ServerBootstrap b = new ServerBootstrap(); // (2)
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
					.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new ChatEncoder(), new ChatDecoder(), new  ChatServerHandler());
						}
					}).option(ChannelOption.SO_BACKLOG, 128) // (5)
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(PORT).sync(); // (7)
			f.toString();
			
			ChatUtil.init();
			System.out.println("Server Started!");

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to
			// gracefully
			// shut down your server.
//			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			workerGroup.shutdownGracefully();
//			bossGroup.shutdownGracefully();
		}
	}
	
	public void shutDown() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

	public static void main(String[] args) {
		new ChatServer().run();
	}

}
