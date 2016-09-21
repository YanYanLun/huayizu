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
 * 多行填空
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
	 * 开始绘制多行填空
	 */
	public void Start() {
		View view = mContext.getLayoutInflater().inflate(
				R.layout.item_subject_choose, null);
		// 题目
		TextView title = (TextView) view.findViewById(R.id.Title);
		// 添加题目标题
		title.setText(index + 1 + "、" + mQuestion.Name);
		// 保存题目标题
		title.setTag(mQuestion.QuestionnaireItemId + "");
		// 添加答案到集合,保存为空答案
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
		// 选项集合
		RadioGroup AnswerOptions = (RadioGroup) view
				.findViewById(R.id.AnswerOptions);
		// 记录当前题目序号
		AnswerOptions.setTag(index + "");
		// 答案
		TextView OptionsAnswer = (TextView) view
				.findViewById(R.id.OptionsAnswer);
		OptionsAnswer.setVisibility(View.GONE);
		// 首次加载清除答案
		OptionsAnswer.setText("");
		if (index == 0) {
			// 展示第一个选项
			AnswerOptions.setVisibility(View.VISIBLE);
			// 给展开的标题添加颜色,蓝色
			title.setTextColor(Color.parseColor("#43A9CF"));
		} else {
			// 其他的隐藏
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
				// 选项集合
				RadioGroup answerOptions = (RadioGroup) question
						.findViewById(R.id.AnswerOptions);
				// 答案
				TextView optionsAnswer = (TextView) question
						.findViewById(R.id.OptionsAnswer);
				// 题目
				TextView title = (TextView) question.findViewById(R.id.Title);
				if (answerOptions.getVisibility() == View.VISIBLE) {
					// 判断答案是否存在
					if (StringUtil.isEmpty(optionsAnswer.getText().toString())) {
						optionsAnswer.setVisibility(View.GONE);
						// 给展开的标题添加颜色,黑色，未答题
						title.setTextColor(Color.parseColor("#FF000000"));
					} else {
						optionsAnswer.setVisibility(View.VISIBLE);
						// 给展开的标题添加颜色,绿色，已答题
						title.setTextColor(Color.parseColor("#659A01"));
					}

					animateView(answerOptions, 1);
					NextQuestion(answerOptions);
				} else {
					optionsAnswer.setVisibility(View.GONE);
					animateView(answerOptions, 0);
					// 展开，题目改为蓝色
					title.setTextColor(Color.parseColor("#43A9CF"));
				}
			}
		});
	}

	/**
	 * 显示下一道题目
	 * 
	 * @param index
	 *            当前题目序号
	 * @param parent
	 *            当前选项集合对象
	 */
	private void NextQuestion(RadioGroup radioGroup) {
		// 得到radioGroup的父节点
		LinearLayout question = (LinearLayout) radioGroup.getParent();
		// 得到题目的总父节点
		LinearLayout show = (LinearLayout) question.getParent();
		// 得到总节点下面题目的数量
		int count = show.getChildCount();
		// 当前题目是第几题
		int index = Integer.parseInt(radioGroup.getTag().toString());
		if (index < count - 1) {
			// 得到下一题
			LinearLayout nextQuestion = (LinearLayout) show
					.getChildAt(index + 1);
			// 得到下一条选项集合对象
			RadioGroup nextRadioGroup = (RadioGroup) nextQuestion
					.findViewById(R.id.AnswerOptions);
			// 得到下一题的答案显示对象
			TextView NextOptionsAnswer = (TextView) nextQuestion
					.findViewById(R.id.OptionsAnswer);
			// 得到下一题的题目
			TextView title = (TextView) nextQuestion.findViewById(R.id.Title);
			if (StringUtil.isEmpty(NextOptionsAnswer.getText().toString())) {
				// 显示出来
				animateView(nextRadioGroup, 0);
				// 展开，题目改为蓝色
				title.setTextColor(Color.parseColor("#43A9CF"));
			}
		}
	}

	/**
	 * 动画时间
	 * 
	 * @return
	 */
	public int getAnimationDuration() {
		return animationDuration;
	}

	/**
	 * 伸缩动画
	 * 
	 * @param target
	 * @param type
	 *            0为展开，1为收缩
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
