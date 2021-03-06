package tang.exam.activity;

import java.util.ArrayList;
import java.util.List;

import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.common.ShowMessage;
import tang.basic.common.StringUtil;
import tang.basic.http.TxException;
import tang.basic.model.User;
import tang.basic.niftynotification.Effects;
import tang.basic.niftynotification.NiftyNotificationView;
import ximi.exam.R;
import tang.exam.common.ActivityBarList;
import tang.exam.common.ActivityBarList.onFinishListener;
import tang.exam.common.AlertDialog;
import tang.exam.common.AlertDialog.onSaveExamSpeedListener;
import tang.exam.common.SweetAlertDialog;
import tang.exam.model.AnswerQuestionnaire;
import tang.exam.model.Exam;
import tang.exam.model.Menu;
import tang.exam.net.NetServerCenter;
import tang.exam.net.RequestErrorExam;
import tang.exam.net.RequestExam;
import tang.exam.net.RequestExamSpeed;
import tang.exam.net.RequestRememberExam;
import tang.exam.net.ResponseErrorExam;
import tang.exam.net.ResponseExam;
import tang.exam.net.ResponseExamSpeed;
import tang.exam.net.ResponseRememberExam;
import tang.exam.question.TheJudgeItem;
import tang.exam.question.TheMultipleItem;
import tang.exam.question.TheRadioItem;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.gc.materialdesign.views.LayoutRipple;

public class ActivityExamAlone extends ActivityBarList implements
		onFinishListener, onSaveExamSpeedListener {
	private Menu menu;
	private List<AnswerQuestionnaire> answerList = new ArrayList<AnswerQuestionnaire>();
	private boolean isHaveExam = false;
	private User user;
	private boolean isDoExam = false;
	private AlertDialog dia;
	private SweetAlertDialog didlog;
	private List<Exam> mList;
	private int examIndex = 0;

	@Override
	protected int layoutID() {
		return R.layout.activity_exam_alone;
	}

	@Override
	protected void fouseChange() {
		super.fouseChange();
		dia = new AlertDialog(this);
		dia.setOnSaveExamSpeedListener(this);
		setOnFinishListener(this);
		user = Util.getDao().getUserinfo();
		menu = (Menu) getIntent().getSerializableExtra("Menu");
		if (!StringUtil.isEmpty(menu.Name)) {
			setTitle(menu.Name);
		}
		last().setOnClickListener(this);
		next().setOnClickListener(this);
		submit().setOnClickListener(this);
		Reg();
		QueryHaveSpeedExam();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		user = Util.getDao().getUserinfo();
		switch (v.getId()) {
		case R.id.submit:
			if (isHaveExam == true) {
				if (!StringUtil.isEmpty(user.token)) {
					dia.doSumbit();
				} else {
					ShowMessage.showToast("游客不能提交", this, false);
				}
			} else {
				ShowMessage.showToast("未找到试题！", this, false);
			}
			break;
		case R.id.last:
			if (mList == null) {
				ShowMessage.showToast("未找到试题！", this, false);
				return;
			}
			if (next().getVisibility() == View.GONE) {
				next().setVisibility(View.VISIBLE);
				submit().setVisibility(View.GONE);
			}
			if (examIndex == 0) {
				ShowMessage.showToast("已经是第一题！", this, false);
				return;
			}
			examIndex--;
			loadExam(mList.get(examIndex), examIndex);
			break;
		case R.id.next:
			if (mList == null) {
				ShowMessage.showToast("未找到试题！", this, false);
				return;
			}
			if (examIndex == mList.size() - 1) {
				submit().setVisibility(View.VISIBLE);
				next().setVisibility(View.GONE);
				return;
			} else {
				next().setVisibility(View.VISIBLE);
				submit().setVisibility(View.GONE);
			}
			boolean tr = VerificationExam(examIndex);
			if (tr) {
				dia.doMe(mList.get(examIndex));
				return;
			} else {
				examIndex++;
				loadExam(mList.get(examIndex), examIndex);
				if (examIndex == mList.size() - 1) {
					submit().setVisibility(View.VISIBLE);
					next().setVisibility(View.GONE);
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isHaveExam == true && !StringUtil.isEmpty(user.token)) {
				dia.doLeave();
			} else {
				finish();
			}
		}
		return true;
	}

	@Override
	public void onFinish() {
		if (isHaveExam == true && !StringUtil.isEmpty(user.token)) {
			dia.doLeave();
		} else {
			finish();
		}
	}

	@Override
	public void onSaveExamSpeed(SweetAlertDialog sDialog) {
		this.didlog = sDialog;
		String exerID = "";
		String exerAnswer = "";
		for (int i = 0; i < answerList.size(); i++) {
			AnswerQuestionnaire questionnaire = answerList.get(i);
			if (!StringUtil.isEmpty(questionnaire.Answer)) {
				exerID += questionnaire.QuestionnaireItemId + "|";
				if (questionnaire.Answer.length() > 1) {
					questionnaire.Answer = questionnaire.Answer.substring(0,
							questionnaire.Answer.length() - 1);
				}
				exerAnswer += questionnaire.Answer + "|";
			}
		}
		RequestRememberExam request = Util
				.getRequest(RequestRememberExam.class);
		request.coursecode = menu.ID;
		request.mebcode = user.ID;
		request.token = user.token;
		request.exerID = exerID;
		if (exerAnswer.length() > 0) {
			exerAnswer = exerAnswer.substring(0, exerAnswer.length() - 1);
		}
		request.exerAnswer = exerAnswer;
		if (isDoExam == true) {
			request.gostate = 2;
		} else {
			request.gostate = 2;
		}
		if (StringUtil.isEmpty(request.exerAnswer)) {
			ShowMessage.showToast("未检出到已做题目，已取消经度保存", ActivityExamAlone.this,
					false);
			finish();
		} else {
			NetServerCenter.GetRememberExamRequest(this, request, null,
					"RememberExam");
		}
	}

	@Override
	public void onRememberExam(SweetAlertDialog sDialog) {
		this.didlog = sDialog;
		SubmitExam();
	}

	@Override
	public void onNextExam(SweetAlertDialog sDialog) {
		examIndex++;
		loadExam(mList.get(examIndex), examIndex);
		if (examIndex == mList.size() - 1) {
			submit().setVisibility(View.VISIBLE);
			next().setVisibility(View.GONE);
		}
	}

	/**
	 * 进度不存在的情况下查询
	 */
	private void QueryNoSpeedExam() {
		RequestExam requestExam = Util.getRequest(RequestExam.class);
		requestExam.code = menu.ID;
		if (user != null) {
			requestExam.memID = user.ID;
			requestExam.token=user.token;
		}
		NetServerCenter.GetExamRequest(this, requestExam, null, "Exam");
		Util.beginWaiting();
	}

	/**
	 * 查询进度
	 */
	private void QueryHaveSpeedExam() {
		user = Util.getDao().getUserinfo();
		RequestExamSpeed requestExam = Util.getRequest(RequestExamSpeed.class);
		requestExam.coursecode = menu.ID;
		if (user != null) {
			requestExam.mebcode = user.ID;
			requestExam.token=user.token;
		}
		requestExam.gostate = 0;
		NetServerCenter.GetExamSpeedRequest(this, requestExam, null,
				"ExamSpeed");
		Util.beginWaiting();
	}

	private void Reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseExam.class) + "_Exam");
		filter.addAction(Util.getErrorAction(ResponseExam.class) + "_Exam");
		registerReceiver(receiver, filter);
		filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseExamSpeed.class)
				+ "_ExamSpeed");
		filter.addAction(Util.getErrorAction(ResponseExamSpeed.class)
				+ "_ExamSpeed");
		registerReceiver(receiver2, filter);
		filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseErrorExam.class)
				+ "_ErrorExam");
		filter.addAction(Util.getErrorAction(ResponseErrorExam.class)
				+ "_ErrorExam");
		registerReceiver(receiver3, filter);
		filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseRememberExam.class)
				+ "_RememberExam");
		filter.addAction(Util.getErrorAction(ResponseRememberExam.class)
				+ "_RememberExam");
		registerReceiver(receiver4, filter);
	}

	private void showNiftynotification(String value) {
		NiftyNotificationView.build(this, value, Effects.flip, R.id.mLyout)
				.setIcon(R.drawable.icon_logo).show();
	}

	private BroadcastReceiver receiver = new GenericRemoteBroadcastReceiver<ResponseExam>() {

		@Override
		public void onComplet(ResponseExam data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode != 0) {
					showNiftynotification("没有找到试题，请重试");
					isHaveExam = false;
					return;
				}
				mList = data.CarSerieses;
				if (mList == null) {
					showNiftynotification("没有找到试题，请重试");
					isHaveExam = false;
					return;
				}
				if (mList.size() <= 0) {
					showNiftynotification("没有找到试题，请重试");
					isHaveExam = false;
					return;
				}
				isHaveExam = true;
				isDoExam = true;
				examIndex = 0;
				Exam exam = mList.get(examIndex);
				answerList.clear();
				for (int i = 0; i < mList.size(); i++) {
					AnswerQuestionnaire a = new AnswerQuestionnaire();
					answerList.add(a);
				}
				loadExam(exam, examIndex);
			} else {
				showNiftynotification("没有找到试题，请重试");
				isHaveExam = false;
			}
		}

		@Override
		public void onError(TxException error) {
			Util.releaseWaiting();
			showNiftynotification(error.getMessage());
			isHaveExam = false;
		}
	};
	private BroadcastReceiver receiver2 = new GenericRemoteBroadcastReceiver<ResponseExamSpeed>() {

		@Override
		public void onComplet(ResponseExamSpeed data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode != 1) {
					QueryNoSpeedExam();
					isHaveExam = false;
					return;
				}
				mList = data.CarSerieses;
				if (mList == null) {
					// showNiftynotification("没有找到试题，请重试");
					isHaveExam = false;
					QueryNoSpeedExam();
					return;
				}
				if (mList.size() <= 0) {
					// showNiftynotification("没有找到试题，请重试");
					isHaveExam = false;
					QueryNoSpeedExam();
					return;
				}
				isHaveExam = true;
				answerList.clear();
				for (int i = 0; i < mList.size(); i++) {
					AnswerQuestionnaire a = new AnswerQuestionnaire();
					answerList.add(a);
				}
				if (data.gonum > 0) {
					examIndex = data.gonum;
				}
				Exam exam = mList.get(examIndex);
				loadExam(exam, examIndex);
			} else {
				// showNiftynotification("没有找到试题，请重试");
				QueryNoSpeedExam();
				isHaveExam = false;
			}
		}

		@Override
		public void onError(TxException error) {
			Util.releaseWaiting();
			// showNiftynotification(error.getMessage());
			QueryNoSpeedExam();
		}
	};

	private void loadExam(Exam exam, int index) {
		Get_layout_5().removeAllViews();
		View view = null;
		if (exam.Choicestate == 0) {
			// 单选
			view = new TheRadioItem(ActivityExamAlone.this, exam, answerList,
					index);
			((TheRadioItem) view).Start();
		} else if (exam.Choicestate == 1) {
			// 是非
			view = new TheJudgeItem(ActivityExamAlone.this, exam, answerList,
					index);
			((TheJudgeItem) view).Start();
		} else if (exam.Choicestate == 2) {
			// 多选
			view = new TheMultipleItem(ActivityExamAlone.this, exam,
					answerList, index);
			((TheMultipleItem) view).Start();
		}
		Get_layout_5().addView(view);
	}

	private BroadcastReceiver receiver3 = new GenericRemoteBroadcastReceiver<ResponseErrorExam>() {

		@Override
		public void onComplet(ResponseErrorExam data) {
			if (data != null) {
				if (data.StatusCode != 1) {
					ShowMessage.showToast(data.StatusMessage,
							ActivityExamAlone.this, false);
					if (didlog != null) {
						didlog.dismiss();
						finish();
					}
					return;
				}
				if (didlog != null) {
					didlog.setTitleText("试题提交！")
							.setContentText("试题提交成功！")
							.setConfirmText("确定")
							.showCancelButton(false)
							.setCancelClickListener(null)
							.setConfirmClickListener(
									new SweetAlertDialog.OnSweetClickListener() {

										@Override
										public void onClick(
												SweetAlertDialog sweetAlertDialog) {
											finish();
										}
									})
							.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
				}
			} else {
				if (didlog != null) {
					didlog.setTitleText("试题提交！")
							.setContentText("试题提交成功！")
							.setConfirmText("确定")
							.showCancelButton(false)
							.setCancelClickListener(null)
							.setConfirmClickListener(
									new SweetAlertDialog.OnSweetClickListener() {

										@Override
										public void onClick(
												SweetAlertDialog sweetAlertDialog) {
											finish();
										}
									})
							.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
				}
			}
		}

		@Override
		public void onError(TxException error) {
			ShowMessage.showToast(error.getMessage(), ActivityExamAlone.this,
					false);
			if (didlog != null) {
				didlog.dismiss();
				finish();
			}
		}
	};
	private BroadcastReceiver receiver4 = new GenericRemoteBroadcastReceiver<ResponseRememberExam>() {

		@Override
		public void onComplet(ResponseRememberExam data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode != 1) {
					ShowMessage.showToast(data.StatusMessage,
							ActivityExamAlone.this, false);
					if (didlog != null) {
						didlog.dismiss();
						finish();
					}
					return;
				}
				if (didlog != null) {
					didlog.setTitleText("进度保存！")
							.setContentText("试题进度已经保存！")
							.setConfirmText("确定")
							.showCancelButton(false)
							.setCancelClickListener(null)
							.setConfirmClickListener(
									new SweetAlertDialog.OnSweetClickListener() {

										@Override
										public void onClick(
												SweetAlertDialog sweetAlertDialog) {
											finish();
										}
									})
							.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
				}
			}
		}

		@Override
		public void onError(TxException error) {
			Util.releaseWaiting();
			ShowMessage.showToast(error.getMessage(), ActivityExamAlone.this,
					false);
			if (didlog != null) {
				didlog.dismiss();
				finish();
			}
		}
	};

	/**
	 * 提交试题
	 */
	private void SubmitExam() {
		int qusnum = 0;
		int errorqusnum = 0;
		String Errorid = "";
		for (int i = 0; i < answerList.size(); i++) {
			AnswerQuestionnaire questionnaire = answerList.get(i);
			if (!StringUtil.isEmpty(questionnaire.Answer)) {
				qusnum++;
				if (questionnaire.isError == true) {
					errorqusnum++;
					Errorid += questionnaire.QuestionnaireItemId + "|";
				}
			}
		}
		if (Errorid.length() > 0) {
			Errorid = Errorid.substring(0, Errorid.length() - 1);
		}
		RequestErrorExam request = Util.getRequest(RequestErrorExam.class);
		request.CID = menu.ID;
		request.meID = user.ID;
		request.token = user.token;
		request.qusnum = qusnum;
		request.errorqusnum = errorqusnum;
		request.Errorid = Errorid;
		request.myscore = 100;
		NetServerCenter.GetErrorExamRequest(this, request, null, "ErrorExam");
	}

	private boolean VerificationExam(int index) {
		Exam mQuestion = mList.get(index);
		AnswerQuestionnaire answer = answerList.get(index);
		if (mQuestion.Choicestate == 0) {
			// 单选
			if (!StringUtil.isEmpty(answer.Answer)) {
				if (!answer.Answer.equals(mQuestion.Choicetrue)) {
					answer.isError = true;
				} else {
					answer.isError = false;
				}
			} else {
				answer.isError = false;
			}
		} else if (mQuestion.Choicestate == 1) {
			// 判断
			if (!StringUtil.isEmpty(answer.Answer)) {
				if (!answer.Answer.equals(mQuestion.Choicetrue)) {
					answer.isError = true;
				} else {
					answer.isError = false;
				}
			} else {
				answer.isError = false;
			}
		} else if (mQuestion.Choicestate == 2) {
			// 多选
			if (!StringUtil.isEmpty(answer.Answer)) {
				String answer2 = answer.Answer.substring(0,
						answer.Answer.length() - 1);
				String[] my_answer = answer2.split(",");
				String[] true_answer = mQuestion.Choicetrue.split("\\*");
				if (my_answer.length == true_answer.length) {
					for (int i = 0; i < my_answer.length; i++) {
						if (mQuestion.Choicetrue.indexOf(my_answer[i]) == -1) {
							answer.isError = true;
							break;
						} else {
							answer.isError = false;
						}
					}
				} else {
					answer.isError = true;
				}
			} else {
				answer.isError = false;
			}
		}
		return answer.isError;
	}

	private LinearLayout Get_layout_5() {
		return (LinearLayout) findViewById(R.id.layout_1);
	}

	private LayoutRipple last() {
		return (LayoutRipple) findViewById(R.id.last);
	}

	private LayoutRipple next() {
		return (LayoutRipple) findViewById(R.id.next);
	}

	private LayoutRipple submit() {
		return (LayoutRipple) findViewById(R.id.submit);
	}

}
