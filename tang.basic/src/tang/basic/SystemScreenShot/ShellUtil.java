package tang.basic.SystemScreenShot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/** Shell������ */
public final class ShellUtil {
	private static ShellUtil instance = null;

	private ShellUtil() {

		// Exists only to defeat instantiation.
	}

	/** ����ShellUtil�ĵ��� */
	public static ShellUtil getInstance() {
		if (instance == null) {
			instance = new ShellUtil();
			instance.root();
		}
		return instance;
	}

	/** \link #root()\endlink��Ľ��� */
	private Process process;

	/** \link #root()\endlink��ĸ����̵ı�׼���� */
	private DataOutputStream dos;

	/** ��׼����Ĺ��� */
	private IStdoutFilter<String> mIStdoutFilter;

	/** ���ñ�׼����Ĺ����� */
	public void setFilter(IStdoutFilter<String> filter) {
		this.mIStdoutFilter = filter;
	}

	/** ���ù�����Ϊ�� */
	public void resetFilter() {
		this.mIStdoutFilter = null;
	}

	/**
	 * @brief �л���ROOT�û�
	 * @details ִ��su������Ϊroot�û�
	 * @pre �豸�Ѿ��ƽ⣬����su������
	 * 
	 * @return �Ƿ�ɹ�
	 */
	public boolean root() {
		try {
			// ִ��su����û����Ϊroot
			process = Runtime.getRuntime().exec("su");
			// ת��DataOutputStream����д���ַ���
			dos = new DataOutputStream(process.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @brief ROOTȨ����ִ������
	 * @pre ִ��\link #root()\endlink
	 * 
	 * @param cmd
	 *            ����
	 */
	public boolean rootCommand(String cmd) {
		if (null != dos) {
			try {
				dos.writeBytes(cmd);
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}

	// /**
	// * @brief \link #rootCommand()\endlink��Ľ��
	// * @pre ִ��\link #rootCommand()\endlink
	// *
	// * @warning ������stdin������������ٴ�stdout��������
	// * ��֮ǰ���԰�Ҳ���ڲ�ͬλ���Թ��������ɣ�������û�ҵ��������ϣ�
	// *
	// * @return �������ļ���
	// */
	// public ArrayList<String> getStdout() {
	// ArrayList<String> lineArray = new ArrayList<String>();
	// try {
	// handleStdout(lineArray, process);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return lineArray;
	// }

	/** �ͷ�ռ����Դ */
	public boolean rootRelease() {
		try {
			dos.writeBytes("exit\n");
			dos.flush();
			process.waitFor(); // �ȴ�ִ�����
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (null != process) {
					process.destroy();
				}
				if (null != dos) {
					dos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * @brief ִ��һ��shell����
	 * 
	 * @param cmd
	 *            ����&������ɵ�����
	 * @param workDir
	 *            �����Ŀ¼
	 * @param isStdout
	 *            �Ƿ�������
	 * @return �������ļ���
	 */
	public ArrayList<String> execCommand(String[] cmd, String workDir,
			boolean isStdout) {
		ArrayList<String> lineArray = null;
		try {
			// ��������ϵͳ���̣�Ҳ������Runtime.exec()������
			ProcessBuilder builder = new ProcessBuilder(cmd);
			// ���������Ŀ¼
			if (workDir != null) {
				builder.directory(new File(workDir));
			}
			// �ϲ���׼����ͱ�׼���
			builder.redirectErrorStream(true);
			// ����һ���½���
			Process process = builder.start();

			// ����������Ļ�
			if (isStdout) {
				lineArray = new ArrayList<String>(); // ��������
				handleStdout(lineArray, process);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineArray;
	}

	/**
	 * �����׼�������
	 * 
	 * @throws IOException
	 */
	private void handleStdout(ArrayList<String> lineArray, Process process)
			throws IOException {
		InputStream is = process.getInputStream(); // ��ñ�׼�����
		if (null != mIStdoutFilter) { // ��������˹���
			// �ж��Ƿ����й�����
			if (mIStdoutFilter instanceof AbstractLineFilter) {
				// ת��BufferedReader
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String line;
				while (null != (line = br.readLine())) {
					/* ���δ�����ˣ��򽫴�������ݼ���List */
					if (!mIStdoutFilter.filter(line)) {
						lineArray.add(mIStdoutFilter.handle());
					}
				}
				if (br != null) {
					br.close();
				}
			} else {
				// Ĭ�ϰ���ֱ��ת���ַ�������
				lineArray.add(inputStream2Str(is));
			}
		} else {
			// Ĭ�ϰ���ֱ��ת���ַ�������
			lineArray.add(inputStream2Str(is));
		}
		if (is != null) {
			is.close();
		}
	}

	/**
	 * ������ת���ַ���
	 * 
	 * @throws IOException
	 */
	public String inputStream2Str(InputStream is) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = is.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

}