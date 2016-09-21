package tang.basic.SystemScreenShot;

/**
 * ps������й��˼������pid��ʵ��
 */
public final class PsLineFilter extends AbstractLineFilter {

	@Override
	protected boolean lineFilter(String line) {
		// ���˿��м�������
		if (null == line || "".endsWith(line) || line.startsWith("USER")) {
			return true;
		}
		return false;
	}

	@Override
	public String handle() {
		try {
			return line.trim().split("\\s+")[1]; // ��ȡPID��
		} catch (Exception e) { // null��Խ���쳣
			return line;
		}
	}

}