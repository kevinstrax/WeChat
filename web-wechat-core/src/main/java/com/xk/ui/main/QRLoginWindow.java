package com.xk.ui.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import com.xk.WebWechatClient;
import com.xk.core.IMEvent;
import com.xk.core.IMEventListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.xk.bean.WeChatSign;
import com.xk.utils.Constant;
import com.xk.utils.HTTPUtil;
import com.xk.utils.SWTTools;
import com.xk.utils.XMLUtils;


/**
 * 用途：二维码扫描登陆，程序入口
 *
 * @author xiaokui
 * @date 2017年1月5日
 */
public class QRLoginWindow {

    private static final Log LOG = LogFactory.getLog(QRLoginWindow.class);

	protected Shell shell;
	private Label qrImage;
	private Label tips;
	private Timer timer;
	private static WebWechatClient client = null;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {

	    client = new WebWechatClient();
	    client.init();

		try {
			QRLoginWindow window = new QRLoginWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		final Color back = SWTResourceManager.getColor(245, 245, 245);
		final Color red = SWTResourceManager.getColor(SWT.COLOR_RED);
		shell = new Shell(SWT.FILL);
		shell.setBackground(back);
		shell.setSize(280, 400);
		shell.setText("微信");
		shell.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent paramDisposeEvent) {
				System.exit(0);
			}
		});
		
		SWTTools.centerWindow(shell);
		SWTTools.enableTrag(shell);
		
		final CLabel  closeL = new CLabel (shell, SWT.NONE);
		closeL.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_HAND));
		closeL.setBackground(back);
		closeL.setAlignment(SWT.CENTER);
		closeL.setBounds(235, 0, 45, 35);
		closeL.setText("\nX");
		closeL.setToolTipText("关闭");
		closeL.addMouseTrackListener(new MouseTrackListener() {
			
			@Override
			public void mouseHover(MouseEvent arg0) {
			}
			
			@Override
			public void mouseExit(MouseEvent arg0) {
				closeL.setBackground(back);
				
			}
			
			@Override
			public void mouseEnter(MouseEvent arg0) {
				closeL.setBackground(red);
				
			}
		});
		closeL.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				if(null != timer) {
					timer.cancel();
				}
				shell.dispose();
			}
			
		});
		
		Label nameL = new Label(shell, SWT.NONE);
		nameL.setBackground(back);
		nameL.setBounds(20, 18, 29, 17);
		nameL.setText("微信");
		
		//二维码
		qrImage = new Label(shell, SWT.NONE);
		qrImage.setBounds(48, 80, 186, 186);
		qrImage.setBackground(back);
		
		//提示
		tips = new Label(shell, SWT.NONE);
		tips.setForeground(SWTResourceManager.getColor(112, 128, 144));
		tips.setBackground(back);
		tips.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		tips.setAlignment(SWT.CENTER);
		tips.setBounds(0, 308, 280, 17);
		tips.setText("请使用微信扫一扫以登录");
		loadQRImage();
		loopGetState();
		SWTTools.topWindow(shell);
	}
	
	/**
	 * 用途：获取登录二维码
	 * @date 2016年12月13日
	 */
	private void loadQRImage() {
        Image img = null;
        try {
            InputStream in = client.getQrCode(null).get();
            if (Objects.isNull(in)) {
                client.destroy();
                LOG.error("[Error] fail to getQrCode, client exit.");
                return;
            }
            img = new Image(null, in);
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("[Error] fail to getQrCode, ", e);
            client.destroy();
            return;
        }
        Image dest = SWTTools.scaleImage(img.getImageData(), 186, 186);
        qrImage.setImage(dest);
        img.dispose();
	}
	
	/**
	 * 用途：获取当前状态
	 * @date 2016年12月13日
	 */
	private void loopGetState() {
	    client.checkQrCode(event -> {
           switch (event.getType()) {
               case EVT_OK:
               case EVT_WAITING_SCAN:
                   break;
               case EVT_WAITING_CONFIRM:
                   Display.getDefault().asyncExec(() -> tips.setText("请在手机上确认登录！"));
                   break;
               case EVT_WAITING_LOGIN:
                   Display.getDefault().asyncExec(() -> tips.setText("正在登录..."));
                   break;
               case EVT_LOGIN_SUCCESS:
                   Display.getDefault().asyncExec(() -> {
                       shell.setVisible(false);
                       MainWindow main = MainWindow.getInstance();
                       main.open();
                   });
                   break;
               default:
           }
        });
	}
}
