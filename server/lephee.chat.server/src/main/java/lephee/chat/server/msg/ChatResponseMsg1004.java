package lephee.chat.server.msg;

import lephee.chat.msgutil.ByteArrayWriter;
import lephee.chat.msgutil.Msg;
import lephee.chat.persistence.role.Role;

public class ChatResponseMsg1004 extends Msg {

	public ChatResponseMsg1004(String nickname, String content) {
		super(1004);
		ByteArrayWriter out = getContentWriter();
		try {
			out.writeUTF(nickname);
			out.writeUTF(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
