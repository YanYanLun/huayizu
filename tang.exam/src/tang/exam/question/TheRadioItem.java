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
 * 单项选择
 * 
 * @author Administrator
 * 
 */
@SuppressLint("InflateParams")
public class TheRadioItem extends LinearLayout {
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

	public TheRadioItem(Context context, Exam question,
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
	 * 开始绘制单项选择
	 */
	public void Start() {
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
		answer = answerList.get(index);
		answer.QuestionnaireItemId = mQuestion.ID;
		answer.SerialNumber = index + 1;
		answer.IsRquired = true;
		answer.Type = 0;
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
		// 展示第一个选项
		AnswerOptions.setVisibility(View.VISIBLE);
		// 给展开的标题添加颜色,蓝色
		title.setTextColor(Color.parseColor("#FFFFFF"));
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
			CandidateItems = CandidateItems.substring(0,
					CandidateItems.length() - 1);
		} else {
			return;
		}
		String[] optionList = CandidateItems.split(",");
		for (int j = 0; j < optionList.length; j++) {
			View subject = View.inflate(mContext, R.layout.item_subject, null);
			AnswerOptions.addView(subject);
			RadioButton radioButton = (RadioButton) subject
					.findViewById(R.id.RadioButton);
			TextView serialNumber = (TextView) subject
					.findViewById(R.id.SerialNumber);
			serialNumber.setText(Letter[j]);
			serialNumber.setTag(Letter[j]);
			TextView options = (TextView) subject.findViewById(R.id.Options);
			options.setText(optionList[j]);
			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
					RadioGroup.LayoutParams.MATCH_PARENT,
					RadioGroup.LayoutParams.WRAP_CONTENT);
			params.leftMargin = ViewHelper.dip2px(mContext, 14);
			params.rightMargin = ViewHelper.dip2px(mContext, 14);
			params.topMargin = ViewHelper.dip2px(mContext, 10);
			subject.setLayoutParams(params);
			subject.setTag(j + "");
			if (answer != null) {
				if (!StringUtil.isEmpty(answer.Answer)) {
					// 将答案转为大写
					if (Letter[j].equals(answer.Answer.toUpperCase())) {
						subject.setBackgroundResource(R.drawable.rect_gary_0099cb);
						radioButton.setChecked(true);
						radioButton.setClickable(true);
						RadioGroup parent = (RadioGroup) subject.getParent();
						LinearLayout question = (LinearLayout) parent
								.getParent();
						// 答案显示
						TextView optionsAnswer = (TextView) question
								.findViewById(R.id.OptionsAnswer);
						// 填充答案
						optionsAnswer.setText(answer.Answer);
						MyAnswer = answer.Answer;
						// 显示答案
						optionsAnswer.setVisibility(View.VISIBLE);
						serialNumber.setTextColor(Color.WHITE);
						options.setTextColor(Color.WHITE);
						View line = subject.findViewById(R.id.line);
						line.setBackgroundColor(Color.WHITE);
					}
				} else {
					if (!StringUtil.isEmpty(mQuestion.MemberChoice)) {
						// 将答案转为大写
						if (Letter[j].equals(mQuestion.MemberChoice
								.toUpperCase())) {
							subject.setBackgroundResource(R.drawable.rect_gary_0099cb);
							radioButton.setChecked(true);
							radioButton.setClickable(true);
							RadioGroup parent = (RadioGroup) subject
									.getParent();
							LinearLayout question = (LinearLayout) parent
									.getParent();
							// 答案显示
							TextView optionsAnswer = (TextView) question
									.findViewById(R.id.OptionsAnswer);
							// 填充答案
							optionsAnswer.setText(mQuestion.MemberChoice);

							answer.Answer = mQuestion.MemberChoice;
							MyAnswer = mQuestion.MemberChoice;
							// 显示答案
							optionsAnswer.setVisibility(View.VISIBLE);
							serialNumber.setTextColor(Color.WHITE);
							options.setTextColor(Color.WHITE);
							View line = subject.findViewById(R.id.line);
							line.setBackgroundColor(Color.WHITE);
						}
					}
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
					TextView optionsAnswer = (TextView) question
							.findViewById(R.id.OptionsAnswer);
					// 题目
					TextView title = (TextView) question
							.findViewById(R.id.Title);
					// 遍历父节点的所以子节点
					for (int k = 0; k < parent.getChildCount(); k++) {
						RelativeLayout choose = (RelativeLayout) parent
								.getChildAt(k);
						// 如果子节点与点击的对象相同，则为点击对象，否则未点击
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
							// 填充答案
							optionsAnswer.setText(serialNumber.getText());
							MyAnswer = serialNumber.getText().toString();
							// 显示答案
							optionsAnswer.setVisibility(View.VISIBLE);
							// 遍历集合，将答案赋值给对象
							for (AnswerQuestionnaire item : answerList) {
								if (item.QuestionnaireItemId == Integer
										.parseInt(title.getTag().toString())) {
									item.Answer = serialNumber.getText()
											.toString();
								}
							}
							// 给已经答过的题目改为绿色
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
