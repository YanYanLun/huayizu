package tang.exam.fragment;

import tang.basic.baseactivity.TANGV4Fragment;
import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.common.InputFormat;
import tang.basic.common.ShowMessage;
import tang.basic.common.StringUtil;
import tang.basic.http.TxException;
import tang.basic.model.User;
import ximi.exam.R;
import tang.exam.net.NetServerCenter;
import tang.exam.net.RequestRegister;
import tang.exam.net.ResponseRegister;

import java.util.List;

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
 * 注册
 */
public class RegisterFragment extends TANGV4Fragment {
	private View view;
	private String sex = "";

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_new_register, null);
		init();
		return view;
	}

	private void init() {
		Reg();
		btn_next().setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_next:
			register();
			break;

		default:
			break;
		}
	}

	private void register() {
		if (StringUtil.isEmpty(et_write_name().getText().toString())) {
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
		if (StringUtil.isEmpty(pwd().getText().toString())) {
			ShowMessage.showToast("请填写密码", getActivity(), false);
			return;
		}
		if (StringUtil.isEmpty(pwd_agan().getText().toString())) {
			ShowMessage.showToast("请填重复密码", getActivity(), false);
			return;
		}
		if (!pwd().getText().toString().equals(pwd_agan().getText().toString())) {
			ShowMessage.showToast("两次密码不一致", getActivity(), false);
			return;
		}
		RequestRegister request = Util.getRequest(RequestRegister.class);
		request.Membername = et_write_name().getText().toString();
		request.Membersex = sex;
		request.Membertel = mobile().getText().toString();
		request.Memberpwd = pwd().getText().toString();
		request.Email = email().getText().toString();
		request.Memberadd = address().getText().toString();
		request.QQ = qq().getText().toString();
		NetServerCenter.GetRegisterRequest(getActivity(), request, null, "Register");
		Util.beginWaiting();
	}

	private void Reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseRegister.class) + "_Register");
		filter.addAction(Util.getErrorAction(ResponseRegister.class) + "_Register");
		getActivity().registerReceiver(receiver, filter);
	}

	private BroadcastReceiver receiver = new GenericRemoteBroadcastReceiver<ResponseRegister>() {

		@Override
		public void onComplet(ResponseRegister data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode != 1) {
					ShowMessage.showToast(data.StatusMessage, getActivity(), false);
					return;
				}
				List<User> users = data.CarSerieses;
				if (users == null || users.size() <= 0) {
					ShowMessage.showToast("注册失败！", getActivity(), false);
					return;
				}
				User user = users.get(0);
				user.Save(Util.getDao());
				ShowMessage.showToast("注册成功！", getActivity(), true);
				Intent intent = new Intent();
				intent.setAction("Login.success");
				getActivity().sendBroadcast(intent);
				getActivity().finish();
			}
		}

		@Override
		public void onError(TxException error) {
			Util.releaseWaiting();
			ShowMessage.showToast(error.getMessage(), getActivity(), false);
		}
	};

	@Override
	public void onDestroyView() {
		Util.releaseWaiting();
		super.onDestroyView();
	}

	private EditText et_write_name() {
		return (EditText) view.findViewById(R.id.et_write_name);
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

	private EditText pwd() {
		return (EditText) view.findViewById(R.id.pwd);
	}

	private EditText pwd_agan() {
		return (EditText) view.findViewById(R.id.pwd_agan);
	}

	private Button btn_next() {
		return (Button) view.findViewById(R.id.btn_next);
	}
}
