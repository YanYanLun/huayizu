package tang.exam.question;

import java.util.List;

import tang.basic.common.StringUtil;
import tang.basic.util.ViewHelper;
import ximi.exam.R;
import tang.exam.common.AlertDialog;
import tang.exam.model.AnswerQuestionnaire;
import tang.exam.model.Exam;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * �ж���
 * 
 * @author Administrator
 * 
 */
@SuppressLint("InflateParams")
public class TheJudgeItem extends LinearLayout {
	private Context mContext;
	private LinearLayout jiexi;
	private Exam mQuestion;
	private String[] Letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	private List<AnswerQuestionnaire> answerList;
	private int index = 0;
	private String MyAnswer = "";
	private AnswerQuestionnaire answer;
	private View view;

	public TheJudgeItem(Context context, Exam question,
			List<AnswerQuestionnaire> answerList, int index) {
		super(context);
		this.mContext = context;
		this.mQuestion = question;
		this.answerList = answerList;
		this.index = index;
		render(mContext);
	}

	private void render(Context context) {
		view = LayoutInflater.from(context).inflate(
				R.layout.item_subject_choose, this);
	}

	/**
	 * ��ʼ�����ж���
	 */
	public void Start() {
		// ����
		jiexi = (LinearLayout) view.findViewById(R.id.jiexi);
		jiexi.setTag(mQuestion.Remark);
		jiexi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog(mContext).doMe(mQuestion);
			}
		});
		jiexi.setVisibility(View.GONE);
		// ��Ŀ
		TextView title = (TextView) view.findViewById(R.id.Title);
		// ������Ŀ����
		title.setText(index + 1 + "��" + mQuestion.Extitle);
		// ������Ŀ����
		title.setTag(mQuestion.ID + "");
		// ���Ӵ𰸵�����,����Ϊ�մ�
		answer = answerList.get(index);
		answer.QuestionnaireItemId = mQuestion.ID;
		answer.SerialNumber = index + 1;
		answer.IsRquired = true;
		answer.Type = 1;
		// ѡ���
		RadioGroup AnswerOptions = (RadioGroup) view
				.findViewById(R.id.AnswerOptions);
		// ��¼��ǰ��Ŀ���
		AnswerOptions.setTag(index + "");
		// ��
		TextView OptionsAnswer = (TextView) view
				.findViewById(R.id.OptionsAnswer);
		OptionsAnswer.setVisibility(View.GONE);
		// �״μ��������
		OptionsAnswer.setText("");
		// չʾ��һ��ѡ��
		AnswerOptions.setVisibility(View.VISIBLE);
		// ��չ���ı���������ɫ,��ɫ
		title.setTextColor(Color.parseColor("#FFFFFF"));

		for (int j = 0; j < 2; j++) {
			View subject = View.inflate(mContext, R.layout.item_subject, null);
			AnswerOptions.addView(subject);
			RadioButton radioButton = (RadioButton) subject
					.findViewById(R.id.RadioButton);
			TextView serialNumber = (TextView) subject
					.findViewById(R.id.SerialNumber);
			serialNumber.setText(Letter[j]);
			TextView options = (TextView) subject.findViewById(R.id.Options);
			if (j == 0) {
				options.setText("��");
				if (!StringUtil.isEmpty(answer.Answer)) {
					// ����תΪ��д
					if (answer.Answer.equals("��")) {
						subject.setBackgroundResource(R.drawable.rect_gary_0099cb);
						radioButton.setChecked(true);
						radioButton.setClickable(true);
						RadioGroup parent = (RadioGroup) subject.getParent();
						LinearLayout question = (LinearLayout) parent
								.getParent();
						// ����ʾ
						TextView optionsAnswer = (TextView) question
								.findViewById(R.id.OptionsAnswer);
						// ����
						optionsAnswer.setText(answer.Answer);
						// ��ʾ��
						optionsAnswer.setVisibility(View.VISIBLE);
						MyAnswer = answer.Answer;
						serialNumber.setTextColor(Color.WHITE);
						options.setTextColor(Color.WHITE);
						View line = subject.findViewById(R.id.line);
						line.setBackgroundColor(Color.WHITE);
					}
				} else if (!StringUtil.isEmpty(mQuestion.MemberChoice)) {
					// ����תΪ��д
					if (mQuestion.MemberChoice.equals("��")) {
						subject.setBackgroundResource(R.drawable.rect_gary_0099cb);
						radioButton.setChecked(true);
						radioButton.setClickable(true);
						RadioGroup parent = (RadioGroup) subject.getParent();
						LinearLayout question = (LinearLayout) parent
								.getParent();
						// ����ʾ
						TextView optionsAnswer = (TextView) question
								.findViewById(R.id.OptionsAnswer);
						// ����
						optionsAnswer.setText(mQuestion.MemberChoice);
						// ��ʾ��
						optionsAnswer.setVisibility(View.VISIBLE);
						answer.Answer = mQuestion.MemberChoice;
						MyAnswer = mQuestion.MemberChoice;
						serialNumber.setTextColor(Color.WHITE);
						options.setTextColor(Color.WHITE);
						View line = subject.findViewById(R.id.line);
						line.setBackgroundColor(Color.WHITE);
					}
				}
			} else if (j == 1) {
				options.setText("��");
				if (!StringUtil.isEmpty(answer.Answer)) {
					// ����תΪ��д
					if (answer.Answer.equals("��")) {
						subject.setBackgroundResource(R.drawable.rect_gary_0099cb);
						radioButton.setChecked(true);
						radioButton.setClickable(true);
						RadioGroup parent = (RadioGroup) subject.getParent();
						LinearLayout question = (LinearLayout) parent
								.getParent();
						// ����ʾ
						TextView optionsAnswer = (TextView) question
								.findViewById(R.id.OptionsAnswer);
						// ����
						optionsAnswer.setText(answer.Answer);
						// ��ʾ��
						optionsAnswer.setVisibility(View.VISIBLE);
						MyAnswer = answer.Answer;
						serialNumber.setTextColor(Color.WHITE);
						options.setTextColor(Color.WHITE);
						View line = subject.findViewById(R.id.line);
						line.setBackgroundColor(Color.WHITE);
					}
				} else if (!StringUtil.isEmpty(mQuestion.MemberChoice)) {
					// ����תΪ��д
					if (mQuestion.MemberChoice.equals("��")) {
						subject.setBackgroundResource(R.drawable.rect_gary_0099cb);
						radioButton.setChecked(true);
						radioButton.setClickable(true);
						RadioGroup parent = (RadioGroup) subject.getParent();
						LinearLayout question = (LinearLayout) parent
								.getParent();
						// ����ʾ
						TextView optionsAnswer = (TextView) question
								.findViewById(R.id.OptionsAnswer);
						// ����
						optionsAnswer.setText(mQuestion.MemberChoice);
						// ��ʾ��
						optionsAnswer.setVisibility(View.VISIBLE);
						answer.Answer = mQuestion.MemberChoice;
						MyAnswer = mQuestion.MemberChoice;
						serialNumber.setTextColor(Color.WHITE);
						options.setTextColor(Color.WHITE);
						View line = subject.findViewById(R.id.line);
						line.setBackgroundColor(Color.WHITE);
					}
				}
			}
			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
					RadioGroup.LayoutParams.MATCH_PARENT,
					RadioGroup.LayoutParams.WRAP_CONTENT);
			params.leftMargin = ViewHelper.dip2px(mContext, 14);
			params.rightMargin = ViewHelper.dip2px(mContext, 14);
			params.topMargin = ViewHelper.dip2px(mContext, 10);
			subject.setLayoutParams(params);
			subject.setTag(j + "");
			subject.setOnClickListener(new OnClickListener() {

				@SuppressLint("ResourceAsColor")
				@Override
				public void onClick(View v) {
					// �ҵ�����������Ŀ�ĸ��ڵ�
					RadioGroup parent = (RadioGroup) v.getParent();
					LinearLayout question = (LinearLayout) parent.getParent();
					// ����ʾ
					TextView optionsAnswer = (TextView) question
							.findViewById(R.id.OptionsAnswer);
					// ��Ŀ
					TextView title = (TextView) question
							.findViewById(R.id.Title);
					// �������ڵ�������ӽڵ�
					for (int k = 0; k < parent.getChildCount(); k++) {
						RelativeLayout choose = (RelativeLayout) parent
								.getChildAt(k);
						// ����ӽڵ������Ķ�����ͬ����Ϊ������󣬷���δ���
						if (Integer.parseInt(v.getTag().toString()) == k) {
							choose.setBackgroundResource(R.drawable.rect_gary_0099cb);
							RadioButton radioButton = (RadioButton) choose
									.findViewById(R.id.RadioButton);
							radioButton.setChecked(true);
							radioButton.setClickable(true);
							TextView serialNumber = (TextView) choose
									.findViewById(R.id.SerialNumber);
							serialNumber.setTextColor(Color.WHITE);
							TextView options = (TextView) choose
									.findViewById(R.id.Options);
							options.setTextColor(Color.WHITE);
							View line = choose.findViewById(R.id.line);
							line.setBackgroundColor(Color.WHITE);
							// ����
							optionsAnswer.setText(options.getText());
							MyAnswer = options.getText().toString();
							// ��ʾ��
							optionsAnswer.setVisibility(View.VISIBLE);
							// �������ϣ����𰸸�ֵ������
							for (AnswerQuestionnaire item : answerList) {
								if (item.QuestionnaireItemId == Integer
										.parseInt(title.getTag().toString())) {
									item.Answer = options.getText().toString();
								}
							}
							// ���Ѿ��������Ŀ��Ϊ��ɫ
							// title.setTextColor(Color.RED);
						} else {
							choose.setBackgroundResource(R.drawable.rect_gary_f5f5f5);
							RadioButton radioButton = (RadioButton) choose
									.findViewById(R.id.RadioButton);
							radioButton.setChecked(false);
							radioButton.setClickable(false);
							TextView serialNumber = (TextView) choose
									.findViewById(R.id.SerialNumber);
							serialNumber.setTextColor(R.color.black);
							TextView options = (TextView) choose
									.findViewById(R.id.Options);
							options.setTextColor(R.color.black);
							View line = choose.findViewById(R.id.line);
							line.setBackgroundColor(Color.parseColor("#C4C4C4"));
						}
					}
				}
			});
		}
	}
}