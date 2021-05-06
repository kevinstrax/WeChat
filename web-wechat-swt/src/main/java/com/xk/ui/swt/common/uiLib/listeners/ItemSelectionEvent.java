package com.xk.ui.swt.common.uiLib.listeners;

import com.xk.ui.swt.common.uiLib.ListItem;
import com.xk.ui.swt.common.uiLib.MyList;

public class ItemSelectionEvent<T extends ListItem> {
	public T item;
	public int itemHeights;
	public int index;
	public MyList<T> source;
}
