package com.xk.core.utils.interfaces;

import com.xk.common.uiLib.ICallback;

import java.io.File;


public interface ICMDHandler {

	public void handle(String content, String user, ICallback<File> fileCall, ICallback<String> textCall, ICallback<File> imgCall);
	
}
