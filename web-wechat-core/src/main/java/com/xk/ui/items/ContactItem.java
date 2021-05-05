package com.xk.ui.items;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import com.xk.bean.ContactsStruct;
import com.xk.bean.StringNode;
import com.xk.uiLib.ICallback;
import com.xk.uiLib.ListItem;
import com.xk.uiLib.MyList;
import com.xk.utils.ImageCache;
import com.xk.utils.ImojCache;

/**
 * 用途：联系人单元格
 *
 * @author xiaokui
 * @date 2017年1月3日
 */
public class ContactItem extends ListItem {

	private ContactsStruct data;//联系人详细数据
	private static Image headDefault=SWTResourceManager.getImage(ContactItem.class, "/images/head.png");
	private boolean dir;//true表示是依据首字母分割的单元格
//	private String name;
	private List<StringNode> name;
	
	public ContactItem(ContactsStruct data, boolean dir, String name) {
		super();
		this.data = data;
		this.dir = dir;
		this.name = ImojCache.computeNode(name);
		//异步加载头像，不然卡很久
		ImageCache.asyncLoadPicture(data, new ICallback<Object>() {
			
			@Override
			public Object callback(Object obj) {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						MyList<ListItem> parent = getParent();
						if(null != parent) {
							parent.flush();
						}
						
					}
					
				});
				return null;
			}
		});
	}

	@Override
	public int getHeight() {
		return dir ? 20 : 60;
	}

	@Override
	public void draw(GC gc, int start, int width, int index) {
		if(selected || focused) {
			int alf=gc.getAlpha();
			Color bk = gc.getBackground();
			gc.setBackground(SWTResourceManager.getColor(136, 136, 136));
			gc.setAlpha(selected ? 155 : 65);
			gc.fillRectangle(0, start, width-MyList.BAR_WIDTH, getHeight());
			gc.setAlpha(alf);
			gc.setBackground(bk);
		}
		Font old = gc.getFont();
		Font font=SWTResourceManager.getFont("宋体", 10, SWT.NORMAL);
		gc.setFont(font);
		if(dir) {
			//绘制ABCDEFG.....
			int alf=gc.getAlpha();
			Color bk = gc.getBackground();
			gc.setBackground(SWTResourceManager.getColor(136, 136, 136));
			gc.setAlpha(155);
			gc.fillRectangle(0, start, width-MyList.BAR_WIDTH, getHeight());
			gc.setAlpha(alf);
			gc.setBackground(bk);
			Color fo = gc.getForeground();
			gc.setForeground(SWTResourceManager.getColor(112, 128, 144));
			Path path = new Path(null);
			float offset = 15f;
			for(StringNode node : name) {
				path.addString(node.base, offset, start + 5, font);
				offset += gc.stringExtent(node.base).x + StringNode.SPACE;
			}
			gc.drawPath(path);
			path.dispose();
			gc.setForeground(fo);
		}else {
			//绘制联系人名称，头像
			Image showHead = (null == data.head || data.head.isDisposed()) ? headDefault : data.head;
			gc.drawImage(showHead, 0, 0, showHead.getImageData().width, showHead.getImageData().height, 10, start + 5, 50, 50);
			Path path = new Path(null);
			float offset = 15f + 60f;
			Image icons = SWTResourceManager.getImage(ContactItem.class, "/images/icons.png");
			for(StringNode node : name) {
				if(node.type == 0) {
					path.addString(node.base, offset, start + 5, font);
					offset += gc.textExtent(node.base, StringNode.DRAW_FLAGS).x + StringNode.SPACE;
				}else {
					Point po = ImojCache.computeLoc(node.base);
					if(null != po) {
						gc.drawImage(icons, 0, po.y, 20, 20, (int)offset, start + 5, 20, 20);
					}
					offset += 20 + StringNode.SPACE;
				}
			}
			gc.drawPath(path);
		}
		gc.setFont(old);
	}

	@Override
	public boolean oncliek(MouseEvent e, int itemHeight, int index, int type) {
		return !dir;
	}

	public ContactsStruct getData() {
		return data;
	}

}
