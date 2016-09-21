package tang.huayizu.activity;

import java.util.HashMap;

import tang.basic.baseactivity.TANGActivity;
import tang.basic.baseactivity.TANGFragment;
import tang.huayizu.R;
import tang.huayizu.common.FragmentFactory;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.gc.materialdesign.views.RadioRipple;

public class ActivityIndex extends TANGActivity implements
		OnCheckedChangeListener {
	private FragmentManager fragmentManager;
	private RadioGroup radioGroup;
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, TANGFragment> map = new HashMap<Integer, TANGFragment>();
	private int allIndex = 0;
	private TANGFragment nowFragment = null;
	private FragmentTransaction fragmentTransaction;

	@Override
	protected int layoutID() {
		return R.layout.activity_index;
	}

	@Override
	protected View layoutView() {
		return null;
	}

	@Override
	protected void fouseChange() {
		init();
	}

	private void init() {
		fragmentManager = getFragmentManager();
		radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
		radioGroup.setOnCheckedChangeListener(this);
		chick(0);
	}

	private void chick(int index) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		TANGFragment fragment = FragmentFactory.getInstanceByIndex(index, map);
		transaction.add(R.id.content, fragment);
		transaction.commit();
		nowFragment = fragment;
	}

	public void addTransition(int index) {
		if (index > allIndex) {
			fragmentTransaction = fragmentManager.beginTransaction();
			TANGFragment newFragment = FragmentFactory.getInstanceByIndex(
					index, map);
			FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(
					this, fragmentTransaction, nowFragment, newFragment,
					R.id.content);
			fragmentTransactionExtended.addTransition(9);
			fragmentTransactionExtended.commit();
			allIndex = index;
			nowFragment = newFragment;
		} else {
			fragmentTransaction = fragmentManager.beginTransaction();
			TANGFragment newFragment = FragmentFactory.getInstanceByIndex(
					index, map);
			FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(
					this, fragmentTransaction, nowFragment, newFragment,
					R.id.content);
			fragmentTransactionExtended.addTransition(9);
			fragmentTransactionExtended.commit();
			allIndex = index;
			nowFragment = newFragment;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioRipple ra = (RadioRipple) group.findViewById(checkedId);
		addTransition(Integer.parseInt(ra.getTag().toString()));
	}
}
