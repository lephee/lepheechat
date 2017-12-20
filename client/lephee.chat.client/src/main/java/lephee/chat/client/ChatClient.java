package lephee.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lephee.chat.view.MainView;

public class ChatClient {
	public static String HOST = "localhost";
    public static int PORT = 8090;
	
	public static void main(String[] args) {
		new ChatClient().run();
	}
	
	public Channel channel;
	public static MainView mainView;

	public void run() {
		
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChatEncoder(), new ChatDecoder(), new ChatClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(HOST, PORT).sync(); // (5)
            
            f.addListener(new GenericFutureListener<ChannelFuture>() {

				public void operationComplete(ChannelFuture future) throws Exception {
					channel = future.channel();
					mainView = new MainView(channel);
					mainView.setVisible(true);
				}
			});
            
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
            workerGroup.shutdownGracefully();
        }
	}
	
}
