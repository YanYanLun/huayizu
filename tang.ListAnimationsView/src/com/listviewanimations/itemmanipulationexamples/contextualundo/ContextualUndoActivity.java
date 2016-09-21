package com.listviewanimations.itemmanipulationexamples.contextualundo;

import android.os.Bundle;

import com.listviewanimations.R;
import com.listviewanimations.MyListActivity;
import com.listviewanimations.adapter.ArrayAdapter;
import com.listviewanimations.adapter.contextualundo.ContextualUndoAdapter;
import com.listviewanimations.adapter.contextualundo.ContextualUndoAdapter.DeleteItemCallback;

public class ContextualUndoActivity extends MyListActivity {

	private final ArrayAdapter<String> mAdapter = createListAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ContextualUndoAdapter contextualUndoAdapter = new ContextualUndoAdapter(mAdapter, R.layout.undo_row, R.id.undo_row_undobutton);
		contextualUndoAdapter.setListView(getListView());
		getListView().setAdapter(contextualUndoAdapter);
		contextualUndoAdapter.setDeleteItemCallback(new MyDeleteItemCallback());
	}

	private class MyDeleteItemCallback implements DeleteItemCallback {

		@Override
		public void deleteItem(int position) {
			mAdapter.remove(position);
			mAdapter.notifyDataSetChanged();
		}
	}
}