package com.xk.ui.swt.hook;

import com.sun.jna.platform.win32.WinUser.MSG;

public interface HotKeyListener {

	public void notify(MSG msg);
	
}
