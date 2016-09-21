package tang.basic.http;

@SuppressWarnings("serial")
public class TxException extends Exception {
	public int errorCode = 1;// 1û�д���

	public TxException(int code) {
		this.errorCode = code;
	}

	public String getMessage() {
		if (errorCode == ErrorCode.FAIL) {
			return "����ʧ��";
		}
		if (errorCode == ErrorCode.SERVERERROR) {
			return "�������쳣";
		}
		if (errorCode == ErrorCode.AGREEMENTEXPIRES) {
			return "����Э�����";
		}
		if (errorCode == ErrorCode.LOGINTIMEOUT) {
			return "��½��ʱ";
		}
		if (errorCode == ErrorCode.ACCOUNTFREEZE) {
			return "�˺��Ѷ���";
		}

		if (errorCode == ErrorCode.SOFTEXPIRES) {
			return "����汾�ѹ���";
		}
		if (errorCode == ErrorCode.NETWORKDISCONNECT) {
			return "�����ѶϿ�";
		}
		if (errorCode == ErrorCode.CONNECTTIMEOUT) {
			return "��������ʱ";
		}

		return super.getMessage();
	}
}
