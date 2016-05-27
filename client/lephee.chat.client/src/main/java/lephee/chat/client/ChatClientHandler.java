package lephee.chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lephee.chat.client.msg.ChatMsg1003;
import lephee.chat.msgutil.Msg;
import lephee.chat.view.MainView;

public class ChatClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			Msg lepheeMsg = (Msg) msg;
			int msgCode = lepheeMsg.getMsgCode();
			System.out.println("msg read: "+msgCode);
			if (msgCode == 1002) {
				int roleId = lepheeMsg.getContentReader().readInt();
				String nickname = lepheeMsg.getContentReader().readUTF();
				System.out.println("roleid=" + roleId + ", nickname=" + nickname);
				if (roleId == 0) {
//					ctx.close();
					return;
				}
				ChatClient.mainView.setRoleId(roleId);
				ChatClient.mainView.changeView();
//				ctx.writeAndFlush(new ChatMsg1003(roleId, "Hello World! " + roleId));
			} else if (msgCode == 1004){
				String nickname = lepheeMsg.getContentReader().readUTF();
				String content = lepheeMsg.getContentReader().readUTF();
				ChatClient.mainView.receiveChatMsg(nickname, content);
				System.out.println(nickname + ": " + content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}
