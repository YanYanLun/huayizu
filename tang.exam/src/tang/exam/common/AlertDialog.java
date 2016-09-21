package tang.exam.common;

import tang.basic.common.Utils;
import tang.exam.common.SweetAlertDialog.OnSweetClickListener;
import tang.exam.model.Exam;
import android.app.Activity;
import android.content.Context;

public class AlertDialog {
	public Context mContext;
	private SweetAlertDialog log;
	private Exam exam_me;

	public AlertDialog(Context context) {
		this.mContext = context;
		log = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
		log.setCancelable(false);
	}

	public void doMe(Exam exam) {
		this.exam_me = exam;
		String ans = "��ȷ����:" + "\n\n";
		if (exam.Choicestate == 1) {
			if (exam.Choicetrue.equals("��")) {
				ans += "A����" + "\n";
			} else if (exam.Choicetrue.equals("��")) {
				ans += "B����" + "\n";
			}
		} else {
			String[] str = exam.Choicetrue.split("\\*");
			for (int i = 0; i < str.length; i++) {
				if (str[i].equals("A")) {
					ans += "A��" + exam.ChoiceA + "\n";
				} else if (str[i].equals("B")) {
					ans += "B��" + exam.ChoiceB + "\n";
				} else if (str[i].equals("C")) {
					ans += "C��" + exam.ChoiceC + "\n";
				} else if (str[i].equals("D")) {
					ans += "D��" + exam.ChoiceD + "\n";
				} else if (str[i].equals("E")) {
					ans += "E��" + exam.ChoiceE + "\n";
				} else if (str[i].equals("F")) {
					ans += "F��" + exam.ChoiceF + "\n";
				}
			}
		}
		ans += "\n";
		log.setTitleText("���������ʾ")
				.setContentText(ans + "�Ƿ�鿴���������")
				.setConfirmText("ȷ��")
				.setCancelText("ȡ��")
				.showCancelButton(true)
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								sDialog.setTitleText("�������")
										.setContentText(
												Utils.ToDBC("����ϰ�����Ϊ��\n\n"
														+ exam_me.Remark))
										.setConfirmText("ȷ��")
										.setConfirmClickListener(
												new OnSweetClickListener() {

													@Override
													public void onClick(
															SweetAlertDialog sweetAlertDialog) {
														sweetAlertDialog
																.dismiss();
														if (listener != null) {
															listener.onNextExam(sweetAlertDialog);
														}
													}
												})
										.showCancelButton(false)
										.setCancelClickListener(null)
										.changeAlertType(
												SweetAlertDialog.SUCCESS_TYPE);
							}
						})
				.setCancelClickListener(
						new SweetAlertDialog.OnSweetClickListener() {

							@Override
							public void onClick(
									SweetAlertDialog sweetAlertDialog) {
								sweetAlertDialog.dismiss();
								if (listener != null) {
									listener.onNextExam(sweetAlertDialog);
								}
							}
						}).show();
	}

	public void doLeave() {
		if (!log.isShowing()) {
			log.setTitleText("���ȱ���")
					.setContentText("������δ�����Ƿ񱣴棿")
					.setCancelText("ȡ��")
					.setConfirmText("ȷ��")
					.showCancelButton(true)
					.setConfirmClickListener(
							new SweetAlertDialog.OnSweetClickListener() {
								@Override
								public void onClick(SweetAlertDialog sDialog) {
									if (listener != null) {
										listener.onSaveExamSpeed(sDialog);
									}
								}
							})
					.setCancelClickListener(
							new SweetAlertDialog.OnSweetClickListener() {

								@Override
								public void onClick(
										SweetAlertDialog sweetAlertDialog) {
									sweetAlertDialog.dismiss();
									((Activity) mContext).finish();
								}
							}).show();
		}
	}

	public void doSumbit() {
		log.setTitleText("ȷ���ύ")
				.setContentText("ȷ���ύ���⣿")
				.setCancelText("ȡ��")
				.setConfirmText("ȷ��")
				.showCancelButton(true)
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								if (listener != null) {
									listener.onRememberExam(sDialog);
								}
							}
						})
				.setCancelClickListener(
						new SweetAlertDialog.OnSweetClickListener() {

							@Override
							public void onClick(
									SweetAlertDialog sweetAlertDialog) {
								sweetAlertDialog.dismiss();
							}
						}).show();
	}

	public interface onSaveExamSpeedListener {
		public void onSaveExamSpeed(SweetAlertDialog sDialog);

		public void onRememberExam(SweetAlertDialog sDialog);

		public void onNextExam(SweetAlertDialog sDialog);
	}

	public onSaveExamSpeedListener listener;

	public void setOnSaveExamSpeedListener(onSaveExamSpeedListener li) {
		this.listener = li;
	}
}
