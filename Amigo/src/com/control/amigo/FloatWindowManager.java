package com.control.amigo;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.example.amigo.R;

public class FloatWindowManager {

	/**
	 * 撠瘚桃�iew�����
	 */
	private static FloatWindowSmallView smallWindow;

	/**
	 * 憭扳瘚桃�iew�����
	 */
	private static FloatWindowBigView bigWindow;

	/**
	 * 撠瘚桃�iew���
	 */
	private static LayoutParams smallWindowParams;

	/**
	 * 憭扳瘚桃�iew���
	 */
	private static LayoutParams bigWindowParams;

	/**
	 * �鈭��撅��溶���宏��瘚桃��
	 */
	private static WindowManager mWindowManager;

	/**
	 * �鈭���������
	 */
	private static ActivityManager mActivityManager;

	/**
	 * ��遣銝�銝芸�瘚桃�����蔭銝箏����銝剝雿蔭��
	 * 
	 * @param context
	 *            敹◆銝箏�蝔��ontext.
	 */
	public static void createSmallWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		if (smallWindow == null) {
			smallWindow = new FloatWindowSmallView(context);
			if (smallWindowParams == null) {
				smallWindowParams = new LayoutParams();
				smallWindowParams.type = LayoutParams.TYPE_PHONE;
				smallWindowParams.format = PixelFormat.RGBA_8888;
				smallWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
						| LayoutParams.FLAG_NOT_FOCUSABLE;
				smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				smallWindowParams.width = FloatWindowSmallView.viewWidth;
				smallWindowParams.height = FloatWindowSmallView.viewHeight;
				smallWindowParams.x = screenWidth;
				smallWindowParams.y = screenHeight / 2;
			}
			smallWindow.setParams(smallWindowParams);
			windowManager.addView(smallWindow, smallWindowParams);
		}
	}

	/**
	 * 撠�瘚桃�����宏���
	 * 
	 * @param context
	 *            敹◆銝箏�蝔��ontext.
	 */
	public static void removeSmallWindow(Context context) {
		if (smallWindow != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(smallWindow);
			smallWindow = null;
		}
	}

	/**
	 * ��遣銝�銝芸之�瘚桃���蔭銝箏��迤銝剝��
	 * 
	 * @param context
	 *            敹◆銝箏�蝔��ontext.
	 */
	public static void createBigWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		if (bigWindow == null) {
			bigWindow = new FloatWindowBigView(context);
			if (bigWindowParams == null) {
				bigWindowParams = new LayoutParams();
				bigWindowParams.x = screenWidth / 2 - FloatWindowBigView.viewWidth / 2;
				bigWindowParams.y = screenHeight / 2 - FloatWindowBigView.viewHeight / 2;
				bigWindowParams.type = LayoutParams.TYPE_PHONE;
				bigWindowParams.format = PixelFormat.RGBA_8888;
				bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				bigWindowParams.width = FloatWindowBigView.viewWidth;
				bigWindowParams.height = FloatWindowBigView.viewHeight;
			}
			windowManager.addView(bigWindow, bigWindowParams);
		}
	}

	/**
	 * 撠之�瘚桃�����宏���
	 * 
	 * @param context
	 *            敹◆銝箏�蝔��ontext.
	 */
	public static void removeBigWindow(Context context) {
		if (bigWindow != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(bigWindow);
			bigWindow = null;
		}
	}

	/**
	 * ��撠瘚桃��extView銝��嚗蝷箏��蝙��������
	 * 
	 * @param context
	 *            �隡摨蝔������
	 */
	public static void updateAmigoInfo() {
		if (bigWindow != null) {
//			TextView percentView = (TextView) smallWindow.findViewById(R.id.percent);
//			percentView.setText(getUsedPercentValue(context));
			TextView motorView = (TextView) bigWindow.findViewById(R.id.motorStatus);
			TextView batteryView = (TextView) bigWindow.findViewById(R.id.batteryStatus);
			TextView positionView = (TextView) bigWindow.findViewById(R.id.position);
			TextView sonar1View = (TextView) bigWindow.findViewById(R.id.sonar1);
			TextView sonar2View = (TextView) bigWindow.findViewById(R.id.sonar2);
			TextView sonar3View = (TextView) bigWindow.findViewById(R.id.sonar3);
			TextView sonar4View = (TextView) bigWindow.findViewById(R.id.sonar4);
			TextView sonar5View = (TextView) bigWindow.findViewById(R.id.sonar5);
			TextView sonar6View = (TextView) bigWindow.findViewById(R.id.sonar6);
			TextView sonar7View = (TextView) bigWindow.findViewById(R.id.sonar7);
			TextView sonar8View = (TextView) bigWindow.findViewById(R.id.sonar8);
			
			motorView.setText(BluetoothService.Amigo.getMotor());
			batteryView.setText(BluetoothService.Amigo.getThetaPos());
			positionView.setText(BluetoothService.Amigo.getPosition());
			sonar1View.setText(BluetoothService.Amigo.getSonar1());
			sonar2View.setText(BluetoothService.Amigo.getSonar2());
			sonar3View.setText(BluetoothService.Amigo.getSonar3());
			sonar4View.setText(BluetoothService.Amigo.getSonar4());
			sonar5View.setText(BluetoothService.Amigo.getSonar5());
			sonar6View.setText(BluetoothService.Amigo.getSonar6());
			sonar7View.setText(BluetoothService.Amigo.getSonar7());
			sonar8View.setText(BluetoothService.Amigo.getSonar8());
			
		}
	}

	/**
	 * ����瘚桃��(��撠瘚桃��之�瘚桃��)�蝷箏撅����
	 * 
	 * @return ��瘚桃�蝷箏獢銝��rue嚗瓷������alse��
	 */
	public static boolean isWindowShowing() {
		return smallWindow != null || bigWindow != null;
	}

	/**
	 * 憒�indowManager餈��遣嚗��遣銝�銝芣��indowManager餈��������歇��遣��indowManager��
	 * 
	 * @param context
	 *            敹◆銝箏�蝔��ontext.
	 * @return WindowManager�����鈭��撅��溶���宏��瘚桃���
	 */
	private static WindowManager getWindowManager(Context context) {
		if (mWindowManager == null) {
			mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return mWindowManager;
	}

	/**
	 * 憒�ctivityManager餈��遣嚗��遣銝�銝芣��ctivityManager餈��������歇��遣��ctivityManager��
	 * 
	 * @param context
	 *            �隡摨蝔������
	 * @return ActivityManager�����鈭����������
	 */
	private static ActivityManager getActivityManager(Context context) {
		if (mActivityManager == null) {
			mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		}
		return mActivityManager;
	}

	/**
	 * 霈∠�歇雿輻��������僎餈���
	 * 
	 * @param context
	 *            �隡摨蝔������
	 * @return 撌脖蝙���������誑摮泵銝脣耦撘����
	 */
//	public static String getUsedPercentValue(Context context) {
//		String dir = "/proc/meminfo";
//		try {
//			FileReader fr = new FileReader(dir);
//			BufferedReader br = new BufferedReader(fr, 2048);
//			String memoryLine = br.readLine();
//			String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
//			br.close();
//			long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
//			long availableSize = getAvailableMemory(context) / 1024;
//			int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
//			return percent + "%";
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return "�瘚桃��";
//	}

	/**
	 * �������������隞亙��蛹�����
	 * 
	 * @param context
	 *            �隡摨蝔������
	 * @return 敶�������
	 */
	private static long getAvailableMemory(Context context) {
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		getActivityManager(context).getMemoryInfo(mi);
		return mi.availMem;
	}

}
