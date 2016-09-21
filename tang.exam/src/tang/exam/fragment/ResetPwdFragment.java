package tang.exam.fragment;

import tang.basic.baseactivity.TANGV4Fragment;
import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.common.ShowMessage;
import tang.basic.common.StringUtil;
import tang.basic.http.TxException;
import tang.basic.model.User;
import ximi.exam.R;
import tang.exam.activity.ActivityLogin;
import tang.exam.activity.ActivityReviseInfo;
import tang.exam.net.NetServerCenter;
import tang.exam.net.RequestResetPwd;
import tang.exam.net.ResponseResetPwd;
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
 * 重置密码
 */
public class ResetPwdFragment extends TANGV4Fragment {
	private View view;
	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_rest_pwd, null);
		init();
		return view;
	}

	private void init() {
		user = Util.getDao().getUserinfo();
		btn_lgon().setOnClickListener(this);
		Reg();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_lgon:
			if (StringUtil.isEmpty(et_old_pwd().getText().toString())) {
				ShowMessage.showToast("请填写旧密码", getActivity(), false);
				return;
			}
			if (StringUtil.isEmpty(et_psw().getText().toString())) {
				ShowMessage.showToast("请填写新密码", getActivity(), false);
				return;
			}
			if (StringUtil.isEmpty(et_psw2().getText().toString())) {
				ShowMessage.showToast("请填重复密码", getActivity(), false);
				return;
			}
			if (!et_psw().getText().toString()
					.equals(et_psw2().getText().toString())) {
				ShowMessage.showToast("两次密码不一致", getActivity(), false);
				return;
			}
			RequestResetPwd request = Util.getRequest(RequestResetPwd.class);
			request.Membernum = user.ID;
			request.token = user.token;
			request.Memberoldpwd = et_old_pwd().getText().toString();
			request.Memberpwd = et_psw().getText().toString();
			NetServerCenter.GetResetPwdRequest(getActivity(), request, null,
					"ResetPwd");
			Util.beginWaiting();
			break;

		default:
			break;
		}
	}

	private Button btn_lgon() {
		return (Button) view.findViewById(R.id.btn_lgon);
	}

	private EditText et_old_pwd() {
		return (EditText) view.findViewById(R.id.et_old_pwd);
	}

	private EditText et_psw() {
		return (EditText) view.findViewById(R.id.et_psw);
	}

	private EditText et_psw2() {
		return (EditText) view.findViewById(R.id.et_psw2);
	}

	private void Reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseResetPwd.class)
				+ "_ResetPwd");
		filter.addAction(Util.getErrorAction(ResponseResetPwd.class)
				+ "_ResetPwd");
		getActivity().registerReceiver(receiver, filter);
	}

	private BroadcastReceiver receiver = new GenericRemoteBroadcastReceiver<ResponseResetPwd>() {

		@Override
		public void onComplet(ResponseResetPwd data) {
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
					ShowMessage.showToast("修改成功！", getActivity(), true);
					// 删除原来的缓存数据
					User.deleteAll(Util.getDao());
					Intent intent = new Intent();
					intent.setAction("Login.success");
					getActivity().sendBroadcast(intent);
					getActivity().finish();
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
		super.onDestroy();
		Util.releaseWaiting();
	}

}
