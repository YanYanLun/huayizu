package tang.huayizu.widget;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HomeGuideAdapter extends PagerAdapter {
	private List<ImageView> imageViewList;

	public HomeGuideAdapter(List<ImageView> imageList) {
		this.imageViewList = imageList;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	/**
	 * �жϳ�ȥ��view�Ƿ���ڽ�����view ���Ϊtrueֱ�Ӹ���
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	/**
	 * ����Ԥ���������view����, �����Ҫ���ٵĶ��������λ�ô���������position
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container
				.removeView(imageViewList.get(position % imageViewList.size()));
	}

	/**
	 * ����һ��view
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(imageViewList.get(position % imageViewList.size()));
		return imageViewList.get(position % imageViewList.size());
	}

}
