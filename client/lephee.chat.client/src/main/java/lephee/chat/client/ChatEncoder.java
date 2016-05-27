package lephee.chat.client;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToMessageEncoder;
import lephee.chat.msgutil.Msg;

public class ChatEncoder extends MessageToMessageEncoder<Msg> {
	

	@Override
	protected void encode(ChannelHandlerContext ctx, Msg msg, List<Object> out) throws Exception {
		if (msg instanceof Msg) {
			Msg a = (Msg) msg;
			short msgCode = (short) a.getMsgCode();
			byte[] content = a.getContent();
			byte[] entire = new byte[content.length+6]; 
			entire[0] = (byte) (msgCode >> 8);
			entire[1] = (byte) msgCode;
			
			entire[2] = (byte) (content.length >> 24);
			entire[3] = (byte) (content.length >> 16);
			entire[4] = (byte) (content.length >> 8);
			entire[5] = (byte) content.length;
			
			for (int i = 6; i < entire.length; i++) {
				entire[i] = content[i-6];
			}
			out.add(Unpooled.copiedBuffer(entire));
		}
	}

}
