package tang.exam.common;

import tang.basic.exception.ExceptionApplication;
import tang.exam.model.Resources;

public class BaseApplication extends ExceptionApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		Resources.Get();
	}

}
