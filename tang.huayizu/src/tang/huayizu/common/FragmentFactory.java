package tang.huayizu.common;

import java.util.HashMap;

import tang.basic.baseactivity.TANGFragment;
import tang.huayizu.fragment.Fragment_Home_01;
import tang.huayizu.fragment.Fragment_Home_02;
import tang.huayizu.fragment.Fragment_Home_03;
import tang.huayizu.fragment.Fragment_Home_04;
import tang.huayizu.fragment.Fragment_Home_05;

/**
 * Created by admin on 13-11-23.
 */
public class FragmentFactory {
	public static TANGFragment getInstanceByIndex(int index,
			HashMap<Integer, TANGFragment> map) {
		if (map.containsKey(index)) {
			return map.get(index);
		} else {
			TANGFragment fragment = null;
			switch (index) {
			case 0:
				fragment = new Fragment_Home_01();
				break;
			case 1:
				fragment = new Fragment_Home_02();
				break;
			case 2:
				fragment = new Fragment_Home_03();
				break;
			case 3:
				fragment = new Fragment_Home_04();
				break;
			case 4:
				fragment = new Fragment_Home_05();
				break;
			}
			map.put(index, fragment);
			return fragment;
		}
	}
}
