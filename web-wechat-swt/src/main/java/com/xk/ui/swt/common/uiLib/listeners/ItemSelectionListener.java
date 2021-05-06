package com.xk.ui.swt.common.uiLib.listeners;

import com.xk.ui.swt.common.uiLib.ListItem;

public interface ItemSelectionListener<T extends ListItem> {

	public void selected(ItemSelectionEvent<T> e);
	
}
