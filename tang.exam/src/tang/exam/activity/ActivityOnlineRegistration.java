package tang.exam.activity;

import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.common.InputFormat;
import tang.basic.common.ShowMessage;
import tang.basic.common.StringUtil;
import tang.basic.http.TxException;
import tang.basic.model.User;
import ximi.exam.R;
import tang.exam.common.ActivityBarList;
import tang.exam.common.ActivityBarList.onFinishListener;
import tang.exam.model.Type;
import tang.exam.net.NetServerCenter;
import tang.exam.net.RequestOnlineRegistration;
import tang.exam.net.ResponseOnlineRegistration;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityOnlineRegistration extends ActivityBarList implements onFinishListener {

	private final String ACTION = "AbbreviationChoose";
	private Type type;
	private User user;

	@Override
	protected int layoutID() {
		return R.layout.activity_online_registration;
	}

	@Override
	protected void fouseChange() {
		super.fouseChange();
		setTitle("在线报名");
		Reg();
		this.setOnFinishListener(this);
		user = Util.getDao().getUserinfo();
		enroll_type().setOnClickListener(this);
		enroll_address().setOnClickListener(this);
		btn_next().setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.enroll_type:
			doActivity(ActivityTypeChoose.class);
			break;
		case R.id.enroll_address:
			doActivity(ActivityAddressChoose.class);
			break;
		case R.id.btn_next:
			if (StringUtil.isEmpty(et_write_name().getText().toString())) {
				ShowMessage.showToast("请填写用户名", this, false);
				return;
			}
			if (StringUtil.isEmpty(enroll_type().getText().toString())) {
				ShowMessage.showToast("请选择报名类型", this, false);
				return;
			}
			if (StringUtil.isEmpty(enroll_address().getText().toString())) {
				ShowMessage.showToast("请选择报名地址", this, false);
				return;
			}
			if (StringUtil.isEmpty(mobile().getText().toString())) {
				ShowMessage.showToast("请填写手机号", this, false);
				return;
			} else if (!InputFormat.isMobileNO(mobile().getText().toString())) {
				ShowMessage.showToast("手机格式不正确", this, false);
				return;
			}
			if (!StringUtil.isEmpty(email().getText().toString())) {
				if (!InputFormat.isEmail(email().getText().toString())) {
					ShowMessage.showToast("邮箱格式不正确", this, false);
					return;
				}
			}
			RequestOnlineRegistration request = Util.getRequest(RequestOnlineRegistration.class);
			request.MemberID = user.ID;
			request.token = user.token;
			request.Enname = et_write_name().getText().toString();
			request.Enadd = enroll_address().getText().toString();
			request.Enemail = email().getText().toString();
			request.Entel = mobile().getText().toString();
			request.Remark = remarks().getText().toString();
			request.RID = type.ID;
			NetServerCenter.GetOnlineRegistrationRequest(this, request, null, "OnlineRegistration");
			Util.beginWaiting();
			break;
		default:
			break;
		}
	}

	private void Reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION);
		filter.addAction("enroll_type");
		registerReceiver(receiver, filter);
		filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseOnlineRegistration.class) + "_OnlineRegistration");
		filter.addAction(Util.getErrorAction(ResponseOnlineRegistration.class) + "_OnlineRegistration");
		registerReceiver(receiver2, filter);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION)) {
				enroll_address().setText(intent.getStringExtra("abbreviation"));
			} else if (action.equals("enroll_type")) {
				type = (Type) intent.getSerializableExtra("enroll_type");
				enroll_type().setText(type.Classtitle);
			}
		}
	};
	private BroadcastReceiver receiver2 = new GenericRemoteBroadcastReceiver<ResponseOnlineRegistration>() {

		@Override
		public void onComplet(ResponseOnlineRegistration data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode != 1) {
					ShowMessage.showToast(data.StatusMessage, ActivityOnlineRegistration.this, true);
					return;
				}
				ShowMessage.showToast("报名成功！", ActivityOnlineRegistration.this, true);
				finish();
			}
		}

		@Override
		public void onError(TxException error) {
			Util.releaseWaiting();
			Util.handlerTxException(error);
		}
	};

	private EditText et_write_name() {
		return (EditText) findViewById(R.id.et_write_name);
	}

	private EditText mobile() {
		return (EditText) findViewById(R.id.mobile);
	}

	private EditText email() {
		return (EditText) findViewById(R.id.email);
	}

	private EditText remarks() {
		return (EditText) findViewById(R.id.remarks);
	}

	private TextView enroll_type() {
		return (TextView) findViewById(R.id.enroll_type);
	}

	private TextView enroll_address() {
		return (TextView) findViewById(R.id.enroll_address);
	}

	private Button btn_next() {
		return (Button) findViewById(R.id.btn_next);
	}

	@Override
	public void onFinish() {
		finish();
	}
}
