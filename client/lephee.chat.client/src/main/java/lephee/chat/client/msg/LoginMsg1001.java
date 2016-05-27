package lephee.chat.client.msg;

import lephee.chat.msgutil.ByteArrayWriter;
import lephee.chat.msgutil.Msg;

public class LoginMsg1001 extends Msg {

	public LoginMsg1001(String username, String password) {
		super(1001);
		ByteArrayWriter out = getContentWriter();
		try {
			out.writeUTF(username);
			out.writeUTF(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
