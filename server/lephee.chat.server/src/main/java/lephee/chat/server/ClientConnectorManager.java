package lephee.chat.server;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;
import lephee.chat.msgutil.Msg;

public class ClientConnectorManager {
	
	private static ClientConnectorManager instance;
	
	private ConcurrentHashMap<Integer, ChannelHandlerContext> clientMap = new ConcurrentHashMap<Integer, ChannelHandlerContext>();
	
	public static ClientConnectorManager getInstance() {
		if (instance == null) {
			instance = new ClientConnectorManager();
		}
		return instance;
	}
	
	private ClientConnectorManager() {
		
	}
	
	public void addClient(int uid, ChannelHandlerContext ctx) {
		clientMap.put(uid, ctx);
		System.out.println("add a client: uid=" + uid);
	}

	public void sendMsgToAll(Msg msg) {
		for (ChannelHandlerContext ctx : clientMap.values()) {
			ctx.writeAndFlush(msg);
		}
	}
	
	public void sendMsg(int roleId, Msg msg) {
		ChannelHandlerContext ctx = clientMap.get(roleId);
		if (ctx != null) {
			ctx.writeAndFlush(msg);
		}
	}
}
