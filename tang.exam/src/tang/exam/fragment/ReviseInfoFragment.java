package tang.exam.fragment;

import tang.basic.baseactivity.TANGV4Fragment;
import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.common.InputFormat;
import tang.basic.common.ShowMessage;
import tang.basic.common.StringUtil;
import tang.basic.http.TxException;
import tang.basic.model.User;
import ximi.exam.R;
import tang.exam.activity.ActivityLogin;
import tang.exam.activity.ActivityReviseInfo;
import tang.exam.net.NetServerCenter;
import tang.exam.net.RequestReviseInfo;
import tang.exam.net.ResponseReviseInfo;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 修改信息
 */
public class ReviseInfoFragment extends TANGV4Fragment {
	private View view;
	private User user;
	private String sex = "";

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_revise_info, null);
		init();
		return view;
	}

	private Button btn_logout() {
		return (Button) view.findViewById(R.id.btn_logout);
	}

	private Button btn_next() {
		return (Button) view.findViewById(R.id.btn_next);
	}

	private void init() {
		user = Util.getDao().getUserinfo();
		btn_logout().setOnClickListener(this);
		btn_next().setOnClickListener(this);
		if (user != null) {
			if (!StringUtil.isEmpty(user.Membername)) {
				et_write_phone().setText(user.Membername);
			}
			if (!StringUtil.isEmpty(user.Membertel)) {
				mobile().setText(user.Membertel);
			}
			if (!StringUtil.isEmpty(user.Email)) {
				email().setText(user.Email);
			}
			if (!StringUtil.isEmpty(user.Memberadd)) {
				address().setText(user.Memberadd);
			}
			if (!StringUtil.isEmpty(user.QQ)) {
				qq().setText(user.QQ);
			}
			if (!StringUtil.isEmpty(user.Membercard)) {
				card().setText(user.Membercard);
			}
			if (!StringUtil.isEmpty(user.Membername)) {
				et_write_phone().setText(user.Membername);
			}
			if (!StringUtil.isEmpty(user.Membername)) {
				et_write_phone().setText(user.Membername);
			}
			if (!StringUtil.isEmpty(user.Membersex)) {
				if (user.Membersex.equals("男")) {
					radiogroup().check(RadioYes().getId());
				} else {
					radiogroup().check(RadioNo().getId());
				}
			}
		}
		Reg();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_logout:
			User.deleteAll(Util.getDao());
			ShowMessage.showToast("注销成功！", getActivity(), true);
			Intent intent = new Intent();
			intent.setAction("Login.success");
			getActivity().sendBroadcast(intent);
			getActivity().finish();
			break;
		case R.id.btn_next:
			if (StringUtil.isEmpty(et_write_phone().getText().toString())) {
				ShowMessage.showToast("请填写用户名", getActivity(), false);
				return;
			}
			int radioButtonId = radiogroup().getCheckedRadioButtonId();
			if (radioButtonId == -1) {
				ShowMessage.showToast("请选择性别", getActivity(), false);
				return;
			} else {
				// 根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton) view.findViewById(radioButtonId);
				sex = rb.getTag().toString();
			}
			if (StringUtil.isEmpty(mobile().getText().toString())) {
				ShowMessage.showToast("请填写手机号", getActivity(), false);
				return;
			} else if (!InputFormat.isMobileNO(mobile().getText().toString())) {
				ShowMessage.showToast("手机格式不正确", getActivity(), false);
				return;
			}
			if (!StringUtil.isEmpty(email().getText().toString())) {
				if (!InputFormat.isEmail(email().getText().toString())) {
					ShowMessage.showToast("邮箱格式不正确", getActivity(), false);
					return;
				}
			}
			if (!StringUtil.isEmpty(card().getText().toString())) {
				if (!InputFormat.isCarDID(card().getText().toString())) {
					ShowMessage.showToast("身份证格式不正确", getActivity(), false);
					return;
				}
			}
			RequestReviseInfo request = Util
					.getRequest(RequestReviseInfo.class);
			request.Membername = et_write_phone().getText().toString();
			request.Membersex = sex;
			request.Membertel = mobile().getText().toString();
			request.Membernum = user.Membernum;
			request.Email = email().getText().toString();
			request.Memberadd = address().getText().toString();
			request.QQ = qq().getText().toString();
			request.Membercard = card().getText().toString();
			request.token = user.token;
			NetServerCenter.GetReviseInfoRequest(getActivity(), request, null,
					"ReviseInfo");
			Util.beginWaiting();
			break;
		default:
			break;
		}
	}

	private EditText et_write_phone() {
		return (EditText) view.findViewById(R.id.et_write_phone);
	}

	private EditText mobile() {
		return (EditText) view.findViewById(R.id.mobile);
	}

	private EditText email() {
		return (EditText) view.findViewById(R.id.email);
	}

	private EditText address() {
		return (EditText) view.findViewById(R.id.address);
	}

	private EditText qq() {
		return (EditText) view.findViewById(R.id.qq);
	}

	private EditText card() {
		return (EditText) view.findViewById(R.id.card);
	}

	private RadioGroup radiogroup() {
		return (RadioGroup) view.findViewById(R.id.radiogroup);
	}

	private RadioButton RadioYes() {
		return (RadioButton) view.findViewById(R.id.RadioYes);
	}

	private RadioButton RadioNo() {
		return (RadioButton) view.findViewById(R.id.RadioNo);
	}

	private void Reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseReviseInfo.class)
				+ "_ReviseInfo");
		filter.addAction(Util.getErrorAction(ResponseReviseInfo.class)
				+ "_ReviseInfo");
		getActivity().registerReceiver(receiver, filter);
	}

	private BroadcastReceiver receiver = new GenericRemoteBroadcastReceiver<ResponseReviseInfo>() {

		@Override
		public void onComplet(ResponseReviseInfo data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode != 1) {
					ShowMessage.showToast(data.StatusMessage + "请重新登录修改",
							getActivity(), false);
					User.deleteAll(Util.getDao());
					((ActivityReviseInfo) getActivity())
							.doActivity(ActivityLogin.class);
					getActivity().finish();
				} else {
					if (data.CarSerieses != null) {
						if (data.CarSerieses.get(0) != null) {
							data.CarSerieses.get(0).Save(Util.getDao());
							ShowMessage.showToast("修改成功！", getActivity(), true);
							Intent intent = new Intent();
							intent.setAction("Login.success");
							getActivity().sendBroadcast(intent);
							getActivity().finish();
						}
					}
				}
			}
		}

		@Override
		public void onError(TxException error) {
			Util.releaseWaiting();
			ShowMessage.showToast(error.getMessage(), getActivity(), false);
		}
	};

	@Override
	public void onDestroy() {
		Util.releaseWaiting();
		super.onDestroy();
	}

}
