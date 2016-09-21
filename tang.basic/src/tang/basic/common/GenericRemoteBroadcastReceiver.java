package tang.basic.common;

import tang.basic.http.ResponseBase;
import tang.basic.http.TxException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class GenericRemoteBroadcastReceiver<T extends ResponseBase> extends BroadcastReceiver{

	@Override
	public  void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(intent.hasExtra("DATA")){
			T data = (T) intent.getSerializableExtra("DATA");
			onComplet(data);	
		}
		if(intent.hasExtra("ERROR")){
			TxException exception=(TxException) intent.getSerializableExtra("ERROR");
			onError(exception);	
		}
	}
	
	public abstract void onComplet(T data);
	
	public abstract void onError(TxException error);
	
}
