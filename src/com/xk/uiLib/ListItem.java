package com.xk.uiLib;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.GC;


public abstract class ListItem implements Comparable<ListItem>{
	
	private MyList<ListItem> parent;
	private Long weight = -1L;
	protected boolean selected=false;
	protected boolean focused=false;
	
	
	
	@Override
	public int compareTo(ListItem o) {
		if(weight < 0) {
			return 1;
		}
		return weight.compareTo(o.weight);
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}
	
	/**
	 * 用途：获取此单元格实际高度
	 * @date 2017年1月4日
	 * @return 此单元格实际高度
	 */
	public abstract int getHeight();
	
	/**
	 * 用途：绘制此单元格方法
	 * @date 2017年1月3日
	 * @param gc 画笔
	 * @param start 单元格起始绘制点，y轴
	 * @param width list宽度
	 * @param index 单元格位置
	 */
	public abstract void draw(GC gc,int start,int width,int index);
	
	/**
	 * item被点击
	 * @param e 点击事件
	 * @param itemHeight 当前itemY位移，包含本身高度
	 * @param index 相对父组件Y坐标
	 * @param type 单击选中还是双击选中，或者单击获得焦点
	 * @return
	 */
	public abstract boolean oncliek(MouseEvent e, int itemHeight, int index, int type);
	
	void focus(){
		focused=true;
	}
	
	public void select(){
		selected=true;
		if(null!=parent){
			parent.selected = this;
		}
	}
	
	void unFocus(){
		focused=false;
	}
	
	public void unSelect(){
		selected=false;
	}

	public MyList<ListItem> getParent() {
		return parent;
	}

	public void setParent(MyList<ListItem> parent) {
		this.parent = parent;
	}
}
