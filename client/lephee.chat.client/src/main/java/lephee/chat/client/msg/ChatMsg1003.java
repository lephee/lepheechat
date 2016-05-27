package lephee.chat.client.msg;

import lephee.chat.msgutil.ByteArrayWriter;
import lephee.chat.msgutil.Msg;

public class ChatMsg1003 extends Msg {

	public ChatMsg1003(int roleId, String content) {
		super(1003);
		ByteArrayWriter out = getContentWriter();
		try {
			out.writeInt(roleId);
			out.writeUTF(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
