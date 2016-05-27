package lephee.chat.msgutil;

public class Msg {
	protected int msgCode;
	protected byte[] content;
	protected ByteArrayReader bar = null;
	protected ByteArrayWriter baw = null;

	public Msg(int msgCode, byte[] content) {
		this.msgCode = msgCode;
		this.content = content;
	}

	public ByteArrayReader getContentReader() {
		if (bar == null) {
			bar = new ByteArrayReader(content);
		}
		return bar;
	}

	public ByteArrayWriter getContentWriter() {
		if (baw == null) {
			baw = new ByteArrayWriter();
		}
		return baw;
	}

	public Msg(int msgCode, int OnlyIntContent) {
		this.msgCode = msgCode;
		this.content = intToBytes(OnlyIntContent);

	}

	private static byte[] intToBytes(int num) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (num >>> (24 - i * 8));
		}
		return b;
	}

	public int getMessageByteLength() {
		return 4 + getLength();
	}

	public Msg(int msgCode) {
		this.msgCode = msgCode;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public byte[] getContent() {
		if (content == null) {
			if (baw != null) {
				content = baw.toByteArray();
			}
		}
		return content;
	}

	public int getMsgCode() {
		return msgCode;
	}

	public int getLength() {
		return getContent() == null ? 0 : getContent().length;
	}

	@Override
	public String toString() {
		return msgCode + "_" + getClass().getSimpleName();

	}

	public String showBytes() {
		return "msgCode :" + msgCode + " content:" + getBytesShow(content);

	}

	public static String getBytesShow(byte[] a) {
		if (a == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (byte b : a) {
			int unsignedByte = b >= 0 ? b : 256 + b;
			sb.append(" ").append(unsignedByte);
		}
		return sb.toString();
	}
}
