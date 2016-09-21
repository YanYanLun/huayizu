package tang.basic.SystemScreenShot;

/**
 * @brief ������й�����
 * @details ���еķ�ʽ������׼�����������һ�ι����ж�
 */
public abstract class AbstractLineFilter implements IStdoutFilter<String> {

	/** ������ */
	protected String line;

	/**
	 * @brief �й��˲���
	 * @param line
	 *            ��׼�����ĳ������
	 * @return true�����ˣ�false������
	 */
	protected abstract boolean lineFilter(String line);

	public boolean filter(String stdout) {
		this.line = stdout;
		return lineFilter(stdout);
	}

	public String handle() {
		return line; // Ĭ�Ϸ���ԭ��
	}

}