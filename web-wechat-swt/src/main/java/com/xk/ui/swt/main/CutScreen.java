package com.xk.ui.swt.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import com.xk.common.uiLib.ICallback;
import com.xk.ui.swt.core.utils.SWTTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class CutScreen implements ICallback {
	
	private static boolean active = false;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
	protected Shell shell;
	private Display display;
	public Image img;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CutScreen window = new CutScreen();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Open the window.
	 */
	public void open() {
		if(active) {
			return ;
		}
		active = true;
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		active = false;
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		Image temps = null;
		Rectangle rect = display.getBounds();
		try {
			Robot rb = new Robot();
			BufferedImage bi = rb.createScreenCapture(new java.awt.Rectangle(0, 0, rect.width, rect.height));
			temps = SWTTools.AWTImg2SWTImg(bi, "");
		} catch (AWTException e) {
			temps = new Image(display, rect.width, rect.height);  
	        GC gc = new GC(display);  
	        gc.copyArea(temps, 0, 0);  
	        gc.dispose();  
		}  
		
		shell = new Shell(SWT.FILL);
		shell.setBounds(-1, -1, rect.width + 2, rect.height + 2);
		shell.setText("SWT Application");
		SWTTools.topWindow(shell);
        ScreenCanvas ts = new ScreenCanvas(shell, temps, this);
		ts.setBounds(rect);
//		SWTTools.enableTrag(ts);
	}

	@Override
	public Object callback(Object obj) {
		img = (Image) obj;
		if(null != img) {
			Clipboard board = new Clipboard(null);
			board.setContents(new ImageData[]{img.getImageData()}, new Transfer[]{ImageTransfer.getInstance()});
			board.dispose();
		}
		shell.dispose();
		return img;
	}

}
