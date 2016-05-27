package lephee.chat.server;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lephee.chat.msgutil.Msg;

public class ChatDecoder extends ReplayingDecoder<Void> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			short tfun = in.readShort();
			int length = in.readInt();
			if (length < 0 || length > 1024 * 1024 * 50 * 10) {
				System.out.println("消息太长:fun:" + tfun + " len:" + length);
				ctx.close();
			}
			// in.flip();
			Msg tkmessage = new Msg(tfun);
			if (length > 0) {
				byte[] t = new byte[length];
				in.readBytes(t);
				tkmessage.setContent(t);
			}
			out.add(tkmessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
