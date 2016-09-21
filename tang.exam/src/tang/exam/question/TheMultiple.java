package tang.exam.question;

import java.util.List;

import tang.basic.common.StringUtil;
import tang.basic.util.ExpandCollapseAnimation;
import tang.basic.util.ViewHelper;
import ximi.exam.R;
import tang.exam.common.AlertDialog;
import tang.exam.model.AnswerQuestionnaire;
import tang.exam.model.Exam;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 多项选择
 * 
 * @author Administrator
 * 
 */
@SuppressLint("InflateParams")
public class TheMultiple {
	private Activity mContext;
	private LinearLayout mLayout, jiexi;
	private Exam mQuestion;
	private int animationDuration = 330;
	private String[] Letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private List<AnswerQuestionnaire> answerList;
	private int index = 0;
	private String MyAnswer = "";
	private AnswerQuestionnaire answer;

	public TheMultiple(Activity context, LinearLayout layout, Exam question, List<AnswerQuestionnaire> answerList,
			int index) {
		this.mContext = context;
		this.mLayout = layout;
		this.mQuestion = question;
		this.answerList = answerList;
		this.index = index;
	}

	/**
	 * 开始绘制多项选择
	 */
	public void Start() {
		View view = mContext.getLayoutInflater().inflate(R.layout.item_subject_choose, null);
		// 解析
		jiexi = (LinearLayout) view.findViewById(R.id.jiexi);
		jiexi.setTag(mQuestion.Remark);
		jiexi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog(mContext).doMe(mQuestion);
			}
		});
		jiexi.setVisibility(View.GONE);
		// 题目
		TextView title = (TextView) view.findViewById(R.id.Title);
		// 添加题目标题
		title.setText(index + 1 + "、" + mQuestion.Extitle);
		// 保存题目标题
		title.setTag(mQuestion.ID + "");
		// 添加答案到集合,保存为空答案
		answer = new AnswerQuestionnaire();
		answer.QuestionnaireItemId = mQuestion.ID;
		answer.Answer = null;
		answer.SerialNumber = index + 1;
		answer.IsRquired = false;
		answer.Type = 2;
		answerList.add(answer);
		// 选项集合
		RadioGroup AnswerOptions = (RadioGroup) view.findViewById(R.id.AnswerOptions);
		// 记录当前题目序号
		AnswerOptions.setTag(index + "");
		// 答案
		TextView OptionsAnswer = (TextView) view.findViewById(R.id.OptionsAnswer);
		OptionsAnswer.setVisibility(View.GONE);
		// 首次加载清除答案
		OptionsAnswer.setText("");
		if (index == 0) {
			// 展示第一个选项
			AnswerOptions.setVisibility(View.VISIBLE);
			// 给展开的标题添加颜色,蓝色
			title.setTextColor(Color.parseColor("#FFFFFF"));
		} else {
			// 其他的隐藏
			AnswerOptions.setVisibility(View.GONE);
		}
		String CandidateItems = "";
		if (!StringUtil.isEmpty(mQuestion.ChoiceA)) {
			CandidateItems += mQuestion.ChoiceA + ",";
		}
		if (!StringUtil.isEmpty(mQuestion.ChoiceB)) {
			CandidateItems += mQuestion.ChoiceB + ",";
		}
		if (!StringUtil.isEmpty(mQuestion.ChoiceC)) {
			CandidateItems += mQuestion.ChoiceC + ",";
		}
		if (!StringUtil.isEmpty(mQuestion.ChoiceD)) {
			CandidateItems += mQuestion.ChoiceD + ",";
		}
		if (!StringUtil.isEmpty(mQuestion.ChoiceE)) {
			CandidateItems += mQuestion.ChoiceE + ",";
		}
		if (!StringUtil.isEmpty(mQuestion.ChoiceF)) {
			CandidateItems += mQuestion.ChoiceF + ",";
		}
		if (!StringUtil.isEmpty(CandidateItems)) {
			CandidateItems = CandidateItems.substring(0, CandidateItems.length() - 1);
		} else {
			return;
		}
		String[] optionList = CandidateItems.split(",");
		for (int j = 0; j < optionList.length; j++) {
			View subject = mContext.getLayoutInflater().inflate(R.layout.item_subject_chk, null);
			AnswerOptions.addView(subject);
			RadioButton radioButton = (RadioButton) subject.findViewById(R.id.RadioButton);
			TextView serialNumber = (TextView) subject.findViewById(R.id.SerialNumber);
			serialNumber.setText(Letter[j]);
			TextView options = (TextView) subject.findViewById(R.id.Options);
			options.setText(optionList[j]);
			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
					RadioGroup.LayoutParams.WRAP_CONTENT);
			params.leftMargin = ViewHelper.dip2px(mContext, 14);
			params.rightMargin = ViewHelper.dip2px(mContext, 14);
			params.topMargin = ViewHelper.dip2px(mContext, 10);
			subject.setLayoutParams(params);
			subject.setTag(j + "");
			if (!StringUtil.isEmpty(mQuestion.MemberChoice)) {
				// 将答案转为大写
				if (mQuestion.MemberChoice.indexOf(Letter[j]) != -1) {
					subject.setBackgroundResource(R.drawable.rect_gary_0099cb);
					radioButton.setChecked(true);
					radioButton.setClickable(true);
					RadioGroup parent = (RadioGroup) subject.getParent();
					LinearLayout question = (LinearLayout) parent.getParent();
					// 答案显示
					TextView optionsAnswer = (TextView) question.findViewById(R.id.OptionsAnswer);
					if (!mQuestion.MemberChoice
							.substring(mQuestion.MemberChoice.length() - 1, mQuestion.MemberChoice.length())
							.equals(",")) {
						mQuestion.MemberChoice += ",";
					}
					// 填充答案
					optionsAnswer.setText(mQuestion.MemberChoice);
					// 显示答案
					optionsAnswer.setVisibility(View.VISIBLE);
					answer.Answer = mQuestion.MemberChoice;
					testExam();
					MyAnswer = mQuestion.MemberChoice;
					serialNumber.setTextColor(Color.WHITE);
					options.setTextColor(Color.WHITE);
					View line = subject.findViewById(R.id.line);
					line.setBackgroundColor(Color.WHITE);
				}
			}
			subject.setOnClickListener(new OnClickListener() {

				@SuppressLint("ResourceAsColor")
				@Override
				public void onClick(View v) {
					// 找到包含所有题目的父节点
					RadioGroup parent = (RadioGroup) v.getParent();
					LinearLayout question = (LinearLayout) parent.getParent();
					// 答案显示
					TextView optionsAnswer = (TextView) question.findViewById(R.id.OptionsAnswer);
					// 题目
					TextView title = (TextView) question.findViewById(R.id.Title);
					String ans = optionsAnswer.getText().toString();
					// 遍历父节点的所以子节点
					for (int k = 0; k < parent.getChildCount(); k++) {
						RelativeLayout choose = (RelativeLayout) parent.getChildAt(k);
						// 如果子节点与点击的对象相同，则为点击对象，否则未点击
						if (Integer.parseInt(v.getTag().toString()) == k) {
							RadioButton radioButton = (RadioButton) choose.findViewById(R.id.RadioButton);
							TextView serialNumber = (TextView) choose.findViewById(R.id.SerialNumber);
							TextView options = (TextView) choose.findViewById(R.id.Options);
							if (radioButton.isChecked() == true) {
								radioButton.setChecked(false);
								radioButton.setClickable(false);
								choose.setBackgroundResource(R.drawable.rect_gary_f5f5f5);
								serialNumber.setTextColor(R.color.black);
								options.setTextColor(R.color.black);
								View line = choose.findViewById(R.id.line);
								line.setBackgroundColor(Color.parseColor("#C4C4C4"));
								String temp = "";
								String[] str = ans.split(",");
								for (int i = 0; i < str.length; i++) {
									if (str[i].equals(serialNumber.getText()) == false) {
										temp += str[i] + ",";
									}
								}
								// 填充答案
								optionsAnswer.setText(temp);
								MyAnswer = temp;
								// 遍历集合，将答案赋值给对象
								for (AnswerQuestionnaire item : answerList) {
									if (item.QuestionnaireItemId == Integer.parseInt(title.getTag().toString())) {
										item.Answer = temp;
									}
								}
							} else {
								choose.setBackgroundResource(R.drawable.rect_gary_0099cb);
								radioButton.setChecked(true);
								radioButton.setClickable(true);
								serialNumber.setTextColor(Color.WHITE);
								options.setTextColor(Color.WHITE);
								View line = choose.findViewById(R.id.line);
								line.setBackgroundColor(Color.WHITE);
								String temp = optionsAnswer.getText().toString();
								temp += serialNumber.getText() + ",";
								// 填充答案
								optionsAnswer.setText(temp);
								MyAnswer = temp;
								// 隐藏选项
								// animateView(parent, 1);
								// 显示答案
								optionsAnswer.setVisibility(View.VISIBLE);
								// 遍历集合，将答案赋值给对象
								for (AnswerQuestionnaire item : answerList) {
									if (item.QuestionnaireItemId == Integer.parseInt(title.getTag().toString())) {
										item.Answer = temp;
									}
								}
							}
							// 给已经答过的题目改为绿色
							title.setTextColor(Color.RED);
							// NextQuestion(parent);
						}
						// else {
						// choose.setBackgroundResource(R.drawable.rect_gary_f5f5f5);
						// RadioButton radioButton = (RadioButton) choose
						// .findViewById(R.id.RadioButton);
						// radioButton.setChecked(false);
						// radioButton.setClickable(false);
						// TextView serialNumber = (TextView) choose
						// .findViewById(R.id.SerialNumber);
						// serialNumber.setTextColor(R.color.cblack);
						// TextView options = (TextView) choose
						// .findViewById(R.id.Options);
						// options.setTextColor(R.color.cblack);
						// View line = choose.findViewById(R.id.line);
						// line.setBackgroundColor(Color.parseColor("#C4C4C4"));
						// }
					}
				}
			});
		}
		mContext.findViewById(R.id.border_2).setVisibility(View.VISIBLE);
		mLayout.addView(view);
		title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RelativeLayout rel = (RelativeLayout) v.getParent();
				LinearLayout question = (LinearLayout) rel.getParent();
				// 选项集合
				RadioGroup answerOptions = (RadioGroup) question.findViewById(R.id.AnswerOptions);
				// 答案
				TextView optionsAnswer = (TextView) question.findViewById(R.id.OptionsAnswer);
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
						title.setTextColor(Color.RED);
					}

					animateView(answerOptions, 1);
					String temp = optionsAnswer.getText().toString();
					// if (temp.length() > 0) {
					// if (temp.substring(temp.length() - 1, temp.length())
					// .equals(",")) {
					// optionsAnswer.setText(temp.substring(0,
					// temp.length() - 1));
					// }
					// }
					NextQuestion(answerOptions);
				} else {
					optionsAnswer.setVisibility(View.GONE);
					animateView(answerOptions, 0);
					// 展开，题目改为蓝色
					title.setTextColor(Color.parseColor("#FFFFFF"));
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
			LinearLayout nextQuestion = (LinearLayout) show.getChildAt(index + 1);
			// 得到下一条选项集合对象
			RadioGroup nextRadioGroup = (RadioGroup) nextQuestion.findViewById(R.id.AnswerOptions);
			// 得到下一题的答案显示对象
			TextView NextOptionsAnswer = (TextView) nextQuestion.findViewById(R.id.OptionsAnswer);
			// 得到下一题的题目
			TextView title = (TextView) nextQuestion.findViewById(R.id.Title);
			if (StringUtil.isEmpty(NextOptionsAnswer.getText().toString())) {
				// 显示出来
				animateView(nextRadioGroup, 0);
				// 展开，题目改为蓝色
				title.setTextColor(Color.parseColor("#FFFFFF"));
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
		if (type == 1) {
			testExam();
		}
	}

	private void testExam() {
		if (!StringUtil.isEmpty(MyAnswer)) {
			String answer = MyAnswer.substring(0, MyAnswer.length() - 1);
			String[] my_answer = answer.split(",");
			String[] true_answer = mQuestion.Choicetrue.split("\\*");
			if (my_answer.length == true_answer.length) {
				for (int i = 0; i < my_answer.length; i++) {
					if (mQuestion.Choicetrue.indexOf(my_answer[i]) == -1) {
						jiexi.setVisibility(View.VISIBLE);
						this.answer.isError = true;
						break;
					} else {
						jiexi.setVisibility(View.GONE);
						this.answer.isError = false;
					}
				}
			} else {
				jiexi.setVisibility(View.VISIBLE);
				this.answer.isError = true;
			}
		} else {
			jiexi.setVisibility(View.GONE);
			this.answer.isError = false;
		}
	}
}
