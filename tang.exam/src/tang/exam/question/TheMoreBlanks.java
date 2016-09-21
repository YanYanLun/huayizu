package tang.exam.question;

import java.util.List;

import tang.basic.common.InputFormat;
import tang.basic.common.StringUtil;
import tang.basic.util.ExpandCollapseAnimation;
import tang.basic.util.ViewHelper;
import tang.basic.view.ClearEditText;
import ximi.exam.R;
import tang.exam.model.AnswerQuestionnaire;
import tang.exam.model.QuestionnaireQuestion;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * �������
 * 
 * @author Administrator
 * 
 */
public class TheMoreBlanks implements OnFocusChangeListener {
	private Activity mContext;
	private LinearLayout mLayout;
	private QuestionnaireQuestion mQuestion;
	private int animationDuration = 330;
	private List<AnswerQuestionnaire> answerList;
	private int index = 0;

	public TheMoreBlanks(Activity context, LinearLayout layout,
			QuestionnaireQuestion question,
			List<AnswerQuestionnaire> answerList, int index) {
		this.mContext = context;
		this.mLayout = layout;
		this.mQuestion = question;
		this.answerList = answerList;
		this.index = index;
	}

	/**
	 * ��ʼ���ƶ������
	 */
	public void Start() {
		View view = mContext.getLayoutInflater().inflate(
				R.layout.item_subject_choose, null);
		// ��Ŀ
		TextView title = (TextView) view.findViewById(R.id.Title);
		// �����Ŀ����
		title.setText(index + 1 + "��" + mQuestion.Name);
		// ������Ŀ����
		title.setTag(mQuestion.QuestionnaireItemId + "");
		// ��Ӵ𰸵�����,����Ϊ�մ�
		AnswerQuestionnaire answer = new AnswerQuestionnaire();
		answer.QuestionnaireItemId = mQuestion.QuestionnaireItemId;
		answer.Answer = null;
		answer.SerialNumber = index + 1;
		if (mQuestion.IsRquired == true) {
			answer.IsRquired = true;
		} else {
			answer.IsRquired = false;
		}
		answer.Type = 4;
		answerList.add(answer);
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
		if (index == 0) {
			// չʾ��һ��ѡ��
			AnswerOptions.setVisibility(View.VISIBLE);
			// ��չ���ı��������ɫ,��ɫ
			title.setTextColor(Color.parseColor("#43A9CF"));
		} else {
			// ����������
			AnswerOptions.setVisibility(View.GONE);
		}
		View subject = mContext.getLayoutInflater().inflate(
				R.layout.item_subject_once_blanks, null);
		ClearEditText clearEditText = (ClearEditText) subject
				.findViewById(R.id.clearEditText);
		if (mQuestion.IsRquired == true) {
			clearEditText.setTag(index + 1 + "|" + 1);
		} else {
			clearEditText.setTag(index + 1 + "|" + 0);
		}
		clearEditText.setSingleLine(false);
		clearEditText.setOnFocusChangeListener(this);
		InputFormat.TextForTextView(clearEditText, OptionsAnswer,
				mContext);
		RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
				RadioGroup.LayoutParams.MATCH_PARENT,
				RadioGroup.LayoutParams.WRAP_CONTENT);
		params.leftMargin = ViewHelper.dip2px(mContext, 14);
		params.rightMargin = ViewHelper.dip2px(mContext, 14);
		params.topMargin = ViewHelper.dip2px(mContext, 10);
		subject.setLayoutParams(params);
		subject.setTag(1 + "");
		AnswerOptions.addView(subject);
		mContext.findViewById(R.id.border_2).setVisibility(View.VISIBLE);
		mLayout.addView(view);
		title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout question = (LinearLayout) v.getParent();
				// ѡ���
				RadioGroup answerOptions = (RadioGroup) question
						.findViewById(R.id.AnswerOptions);
				// ��
				TextView optionsAnswer = (TextView) question
						.findViewById(R.id.OptionsAnswer);
				// ��Ŀ
				TextView title = (TextView) question.findViewById(R.id.Title);
				if (answerOptions.getVisibility() == View.VISIBLE) {
					// �жϴ��Ƿ����
					if (StringUtil.isEmpty(optionsAnswer.getText().toString())) {
						optionsAnswer.setVisibility(View.GONE);
						// ��չ���ı��������ɫ,��ɫ��δ����
						title.setTextColor(Color.parseColor("#FF000000"));
					} else {
						optionsAnswer.setVisibility(View.VISIBLE);
						// ��չ���ı��������ɫ,��ɫ���Ѵ���
						title.setTextColor(Color.parseColor("#659A01"));
					}

					animateView(answerOptions, 1);
					NextQuestion(answerOptions);
				} else {
					optionsAnswer.setVisibility(View.GONE);
					animateView(answerOptions, 0);
					// չ������Ŀ��Ϊ��ɫ
					title.setTextColor(Color.parseColor("#43A9CF"));
				}
			}
		});
	}

	/**
	 * ��ʾ��һ����Ŀ
	 * 
	 * @param index
	 *            ��ǰ��Ŀ���
	 * @param parent
	 *            ��ǰѡ��϶���
	 */
	private void NextQuestion(RadioGroup radioGroup) {
		// �õ�radioGroup�ĸ��ڵ�
		LinearLayout question = (LinearLayout) radioGroup.getParent();
		// �õ���Ŀ���ܸ��ڵ�
		LinearLayout show = (LinearLayout) question.getParent();
		// �õ��ܽڵ�������Ŀ������
		int count = show.getChildCount();
		// ��ǰ��Ŀ�ǵڼ���
		int index = Integer.parseInt(radioGroup.getTag().toString());
		if (index < count - 1) {
			// �õ���һ��
			LinearLayout nextQuestion = (LinearLayout) show
					.getChildAt(index + 1);
			// �õ���һ��ѡ��϶���
			RadioGroup nextRadioGroup = (RadioGroup) nextQuestion
					.findViewById(R.id.AnswerOptions);
			// �õ���һ��Ĵ���ʾ����
			TextView NextOptionsAnswer = (TextView) nextQuestion
					.findViewById(R.id.OptionsAnswer);
			// �õ���һ�����Ŀ
			TextView title = (TextView) nextQuestion.findViewById(R.id.Title);
			if (StringUtil.isEmpty(NextOptionsAnswer.getText().toString())) {
				// ��ʾ����
				animateView(nextRadioGroup, 0);
				// չ������Ŀ��Ϊ��ɫ
				title.setTextColor(Color.parseColor("#43A9CF"));
			}
		}
	}

	/**
	 * ����ʱ��
	 * 
	 * @return
	 */
	public int getAnimationDuration() {
		return animationDuration;
	}

	/**
	 * ��������
	 * 
	 * @param target
	 * @param type
	 *            0Ϊչ����1Ϊ����
	 */
	private void animateView(final View target, final int type) {
		Animation anim = new ExpandCollapseAnimation(target, type);
		anim.setDuration(getAnimationDuration());
		target.startAnimation(anim);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			LinearLayout linear = (LinearLayout) v.getParent();
			View view = linear.getChildAt(1);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, 2);
			params.topMargin = ViewHelper.dip2px(mContext, 14);
			params.leftMargin = ViewHelper.dip2px(mContext, 0);
			params.rightMargin = ViewHelper.dip2px(mContext, 0);
			view.setBackgroundColor(Color.parseColor("#3f51b5"));
			view.setLayoutParams(params);
		} else {
			LinearLayout linear = (LinearLayout) v.getParent();
			View view = linear.getChildAt(1);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, 2);
			params.topMargin = ViewHelper.dip2px(mContext, 14);
			params.leftMargin = ViewHelper.dip2px(mContext, 0);
			params.rightMargin = ViewHelper.dip2px(mContext, 0);
			view.setBackgroundColor(Color.parseColor("#CCCCCC"));
			view.setLayoutParams(params);
		}
	}
}
