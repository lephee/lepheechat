package lephee.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lephee.chat.msgutil.Msg;
import lephee.chat.persistence.record.Record;
import lephee.chat.persistence.record.RecordManager;
import lephee.chat.persistence.role.Role;
import lephee.chat.persistence.role.RoleManager;
import lephee.chat.server.msg.ChatResponseMsg1004;
import lephee.chat.server.msg.LoginResultMsg1002;

public class ChatServerHandler extends ChannelInboundHandlerAdapter  {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel Activated!");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Msg lepheeMsg = (Msg) msg;
		int msgCode = lepheeMsg.getMsgCode();
		System.out.println("server msg read: "+msgCode);
		if (msgCode == 1001) {
			String username = lepheeMsg.getContentReader().readUTF();
			String password = lepheeMsg.getContentReader().readUTF();
			Role role = RoleManager.getInstance().checkRole(username, password);
			if (role != null) {
				ClientConnectorManager.getInstance().addClient(role.getId(), ctx);
			}
			
//			Role role = new Role(uid, "LePhee");
//			RoleManager.getInstance().insert(role);
//			
//			Record record = Record.create(uid, content);
//			RecordManager.getInstance().insert(record);
			
//			System.out.println("uid=" + uid + ", content="+content);
//			ClientConnectorManager.getInstance().addClient(uid, ctx);
			ctx.writeAndFlush(new LoginResultMsg1002(role));
		} else if (msgCode == 1003) {
			int roleId = lepheeMsg.getContentReader().readInt();
			String content = lepheeMsg.getContentReader().readUTF();
			Role role = RoleManager.getInstance().queryById(roleId);
			if (role != null) {
				String nickname = role.getNickname();
				Record record = Record.create(roleId, content);
				RecordManager.getInstance().insert(record);
				ClientConnectorManager.getInstance().sendMsgToAll(new ChatResponseMsg1004(nickname, content));
			}
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
