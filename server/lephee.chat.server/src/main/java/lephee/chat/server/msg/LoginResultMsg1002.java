package lephee.chat.server.msg;

import lephee.chat.msgutil.ByteArrayWriter;
import lephee.chat.msgutil.Msg;
import lephee.chat.persistence.role.Role;

public class LoginResultMsg1002 extends Msg {

	public LoginResultMsg1002(Role role) {
		super(1002);
		ByteArrayWriter out = getContentWriter();
		try {
			if (role == null) {
				out.writeInt(0);
				out.writeUTF("");
			} else {
				out.writeInt(role.getId());
				out.writeUTF(role.getNickname());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
