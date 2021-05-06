package com.xk.ui.swt.main.chat;

import java.io.File;
import java.util.List;

import com.xk.common.utils.song.SongLocation;
import com.xk.ui.swt.core.utils.SWTTools;
import com.xk.ui.swt.core.utils.WeChatUtil;
import com.xk.ui.swt.vlc.HTTPUrlCallbackMedia;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jcodec.player.Player;

import com.xk.ui.swt.chatlogs.ChatLog;
import com.xk.ui.swt.main.FloatWindow;
import com.xk.ui.swt.common.uiLib.MyList;
import com.xk.ui.swt.common.uiLib.VideoComp;


public class VideoItem extends ChatItem {
	
	private Image img = SWTResourceManager.getImage(getClass(), "/images/playfoc.png");
	private File video;
	
	private Player player;
	
	public VideoItem(String user,Image head, List<Object> chatContent, boolean fromSelf, Font font, ChatLog log) {
		super(user, head, chatContent, fromSelf, font, log);
		if(null != log && null != log.relatedPath) {
			video = new File(log.relatedPath);
		}
	}
	
	

	@Override
	protected void drawContentL(GC gc, int start, int width) {
		super.drawContentL(gc, start, width);
		int junkY = nameHeight + LINE_SPACE_HEIGHT * 3 + LINE_SPACE_HEIGHT + MARGIN;
		int imgW = img.getImageData().width;
		int imgH = img.getImageData().height;
		int realSize = Math.min(maxWidth, getHeight() - junkY) / 2;
		
		int destX = HEAD_IMG_HEIGHT + LINE_SPACE_HEIGHT * 5 + (maxWidth - realSize) / 2;
		int destY = start + junkY + (getHeight() - junkY - realSize - MARGIN * 3) / 2;
		Color backup=gc.getForeground();
		Color white=new Color(null, 0Xff,0Xff,0Xff);
		//绘制半透明背景
		gc.setForeground(white);
		gc.setAlpha(55);
		gc.fillOval(destX, destY, realSize, realSize);
		gc.setForeground(backup);
		gc.setAlpha(255);
		gc.drawImage(img, 0, 0, imgW, imgH, destX, destY, realSize, realSize);
	}



	@Override
	protected void drawContentR(GC gc, int start, int width) {
		super.drawContentR(gc, start, width);
		int junkY = nameHeight + LINE_SPACE_HEIGHT * 2 + LINE_SPACE_HEIGHT + MARGIN;
		int imgW = img.getImageData().width;
		int imgH = img.getImageData().height;
		int realSize = Math.min(maxWidth, getHeight() - junkY) / 2;
		
		int destX = width - (HEAD_IMG_HEIGHT + LINE_SPACE_HEIGHT * 5 + maxWidth + MyList.BAR_WIDTH) + (maxWidth - realSize) / 2;
		int destY = start + junkY + (getHeight() - junkY - realSize - MARGIN * 3) / 2;
		Color backup=gc.getForeground();
		Color white=new Color(null, 0Xff,0Xff,0Xff);
		//绘制半透明背景
		gc.setForeground(white);
		gc.setAlpha(55);
		gc.fillOval(destX, destY, realSize, realSize);
		gc.setForeground(backup);
		gc.setAlpha(255);
		gc.drawImage(img, 0, 0, imgW, imgH, destX, destY, realSize, realSize);
	}



	@Override
	protected void saveContent(MouseEvent e, Menu m) {
		// TODO Auto-generated method stub
		super.saveContent(e, m);
	}



	@Override
	protected void onDoubleClick(MouseEvent e) {
		if(log.msgType == 43) {
			Point point = new Point(e.x, e.y);
			for(Rectangle rect : imgs.keySet()) {
				if(rect.contains(point)) {
					final FloatWindow fw = FloatWindow.getInstance();
					fw.init();
					fw.setSize(100, 150);
					final VideoComp vv = new VideoComp(fw.shell);
					fw.add(vv);
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							SongLocation sl = null;
							if(null == video) {
								sl = WeChatUtil.loadHttpVideo(log.msgid, vv);
								video = new File("temp",System.currentTimeMillis() + ".mp4");
								getLog().relatedPath = video.getAbsolutePath();
								HTTPUrlCallbackMedia media = new HTTPUrlCallbackMedia(sl,video);
								vv.play(media);
//								video = WeChatUtil.loadVideo(log.msgid, vv);
							} else {
								vv.play(video);
							}
							
						}
					}).start();
					fw.setTimeOut(-1L);
					SWTTools.centerWindow(fw.shell);
					SWTTools.enableTrag(vv);
					fw.open(-1, -1);
					
					return;
				}
			}
		}
	}

}
