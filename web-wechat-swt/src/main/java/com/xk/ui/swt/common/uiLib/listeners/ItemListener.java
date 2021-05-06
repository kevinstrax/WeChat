package com.xk.ui.swt.common.uiLib.listeners;

import com.xk.ui.swt.common.uiLib.ListItem;


public interface ItemListener<T extends ListItem> {

	public void itemRemove(ItemEvent<T> e);
	
}
