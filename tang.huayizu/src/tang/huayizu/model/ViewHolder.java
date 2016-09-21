package tang.huayizu.model;

import java.io.Serializable;

import tang.basic.progressbar9ge.CircularProgress;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ViewHolder implements Serializable {
	public CircularProgress progress;
	public RelativeLayout blurred_image_header;
	public ImageView image;
	public RelativeLayout view;
	public int index = 0;
}
