package com.xk.core.utils.hat;

import java.io.File;

import com.xk.common.uiLib.ICallback;
import com.xk.core.constant.Constant;

import com.xk.core.bean.ContactsStruct;
import com.xk.core.utils.interfaces.ICMDHandler;

public class HatHandler implements ICMDHandler {

	
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
			textCall.callback("老哥，你没有头像P个jj");
			return;
		}
		InputStream input = HTTPUtil
                .getInstance().getInput(String.format(Constant.BASE_URL, Constant.HOST) + target.HeadImgUrl + "&type=big");
		ImageLoader loader = new ImageLoader();
		if(null == input) {
			ImageData source = target.head.getImageData();
			loader.data = new ImageData[]{source};
		} else {
			loader.load(input);
		}
		Image greenHat = SWTResourceManager.getImage(HatHandler.class, "/images/green.png");
		String path = "temp/source" + target.NickName + Constant.FORMATS[SWT.IMAGE_JPEG];
		String pathTarget = "temp/target" + target.NickName + Constant.FORMATS[SWT.IMAGE_JPEG];
		loader.save(path, SWT.IMAGE_JPEG);
		List<Rect> faces = ImageFace.findFaces(path);
		if(null == faces || faces.isEmpty()) {
			textCall.callback("找不到正脸，给不了图。");
			return;
		}
		
		ImageData id = greenHat.getImageData();
		Image img = new Image(null, path);
		GC gc = new GC(img);
		for(Rect rect : faces) {
			int width = rect.width;
			int realHeight = (int) (((double)width / id.width) * id.height);
			gc.drawImage(greenHat, 0, 0, id.width, id.height, (int)(rect.x - width * 0.2), (int)(rect.y - realHeight + rect.height * 0.1d), width, realHeight);
		}
		gc.dispose();
		loader.data = new ImageData[]{img.getImageData()};
		loader.save(pathTarget, SWT.IMAGE_JPEG);
		File sourceFile = new File(path);
		File targetFile = new File(pathTarget);
		imgCall.callback(targetFile);
		textCall.callback("来来来,存好你的新头像...");
		sourceFile.delete();
		img.dispose();*/
	}

}
