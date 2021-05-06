package com.xk.core.utils.hat;

import java.io.File;

import com.xk.common.uiLib.ICallback;
import com.xk.core.constant.Constant;

import com.xk.core.bean.ContactsStruct;
import com.xk.core.utils.interfaces.ICMDHandler;

public class FlagHandler implements ICMDHandler {

	@Override
	public void handle(String content, String user, ICallback<File> fileCall,
			ICallback<String> textCall, ICallback<File> imgCall) {
		ContactsStruct target = Constant.getContact(user);
		if(null == target) {
			textCall.callback("没有找到你的头像");
			return;
		}

        textCall.callback("老哥，你没有头像P个jj");
        return;

		/*if(null == target.head) {

		}
		ImageLoader loader = new ImageLoader();
		ImageData source = target.head.getImageData();
		loader.data = new ImageData[]{source};
		Image flag = SWTResourceManager.getImage(HatHandler.class, "/images/China.png");
		String pathTarget = "temp/target" + target.NickName + Constant.FORMATS[SWT.IMAGE_JPEG];

		Image img = new Image(null, source);
		GC gc = new GC(img);

		int width = source.width;
		int height = source.height;
		ImageData flagData = flag.getImageData();
		int realHeight = (int) (flagData.height * ((width / 3.0d) / flagData.width));

		gc.drawImage(flag, 0, 0, flagData.width, flagData.height, width - width / 3, height - realHeight, width / 3, realHeight);

		gc.dispose();
		loader.data = new ImageData[]{img.getImageData()};
		loader.save(pathTarget, SWT.IMAGE_JPEG);
		File targetFile = new File(pathTarget);
		imgCall.callback(targetFile);
		textCall.callback("来来来,存好你的新头像...");
		img.dispose();*/
	}

}
