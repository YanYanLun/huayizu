package tang.exam.fragment;

import tang.basic.baseactivity.TANGV4Fragment;
import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.common.ShowMessage;
import tang.basic.common.StringUtil;
import tang.basic.http.TxException;
import tang.basic.model.User;
import ximi.exam.R;
import tang.exam.net.NetServerCenter;
import tang.exam.net.RequestLogin;
import tang.exam.net.ResponseLogin;
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

/**
 * 登录
 */
public class LoginFragment extends TANGV4Fragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_new_login, null);
		init();
		return view;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_lgon:
			if (StringUtil.isEmpty(et_name().getText().toString())) {
				ShowMessage.showToast("请填写用户名", getActivity(), false);
				return;
			}
			if (StringUtil.isEmpty(et_psw().getText().toString())) {
				ShowMessage.showToast("请填写登录密码", getActivity(), false);
				return;
			}
			User user = Util.getDao().getUserinfo();
			RequestLogin requestLogin = Util.getRequest(RequestLogin.class);
			requestLogin.mebcode = et_name().getText().toString();
			requestLogin.mebpwd = et_psw().getText().toString();
			requestLogin.token = user.token;
			NetServerCenter.GetLoginRequest(getActivity(), requestLogin, null,
					"Login");
			Util.beginWaiting();
			break;

		default:
			break;
		}
	}

	private void init() {
		Reg();
		btn_lgon().setOnClickListener(this);
	}

	private EditText et_name() {
		return (EditText) view.findViewById(R.id.et_name);
	}

	private EditText et_psw() {
		return (EditText) view.findViewById(R.id.et_psw);
	}

	private Button btn_lgon() {
		return (Button) view.findViewById(R.id.btn_lgon);
	}

	private void Reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseLogin.class) + "_Login");
		filter.addAction(Util.getErrorAction(ResponseLogin.class) + "_Login");
		getActivity().registerReceiver(receiver, filter);
	}

	private BroadcastReceiver receiver = new GenericRemoteBroadcastReceiver<ResponseLogin>() {

		@Override
		public void onComplet(ResponseLogin data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode != 1) {
					ShowMessage.showToast(data.StatusMessage, getActivity(),
							false);
					return;
				}
				if (data.CarSerieses != null) {
					if (data.CarSerieses.get(0) != null) {
						data.CarSerieses.get(0).Save(Util.getDao());
						ShowMessage.showToast("登录成功", getActivity(), true);
						Intent intent = new Intent();
						intent.setAction("Login.success");
						getActivity().sendBroadcast(intent);
						getActivity().finish();
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
