package tang.basic.SystemScreenShot;

/** ��׼������˽ӿ� */
public interface IStdoutFilter<T> {

	/**
	 * @brief ���˲���
	 * @param stdout
	 *            ��׼���������
	 * @return true�����ˣ�false������
	 */
	boolean filter(T stdout);

	/**
	 * @brief �������
	 * @return ����������
	 */
	T handle();

}