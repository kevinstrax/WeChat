package com.xk.ui.swt.vlc;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;

public interface ICallbackImagePainter {

	public void setControl(Canvas c);
	
	public void redraw(GC g, ImageData id, Integer rotate);
	
}
