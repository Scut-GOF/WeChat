package gof.scut.common.utils;

/**
 * Created by zm on 2015/3/31.
 */

public class Log {
	private static boolean LOG_SHOW = true;//show Log
	private static boolean DEBUG = false;

	public static void e(String TAG, String message) {
		if (!LOG_SHOW) {
			return;
		}

		if (DEBUG) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length < 4) {
				android.util.Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				android.util.Log.e(className + "." + methodName + "():" + lineNumber, message);
			}
		} else {
			android.util.Log.e(TAG, message);
		}
	}

	public static void e(String TAG, String message, Throwable tr) {
		if (!LOG_SHOW) {
			return;
		}

		if (DEBUG) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length < 4) {
				android.util.Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				android.util.Log.e(className + "." + methodName + "():" + lineNumber, message, tr);
			}
		} else {
			android.util.Log.e(TAG, message, tr);
		}
	}

	public static void d(String TAG, String message) {
		if (!LOG_SHOW) {
			return;
		}

		if (DEBUG) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length < 3) {
				android.util.Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				android.util.Log.d(className + "." + methodName + "():" + lineNumber, message);
			}
		} else {
			android.util.Log.d(TAG, message);
		}
	}

	public static void i(String TAG, String message) {
		if (!LOG_SHOW) {
			return;
		}

		if (DEBUG) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length < 3) {
				android.util.Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				android.util.Log.i(className + "." + methodName + "():" + lineNumber, message);
			}
		} else {
			android.util.Log.i(TAG, message);
		}
	}

	public static void w(String TAG, String message) {
		if (!LOG_SHOW) {
			return;
		}

		if (DEBUG) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length < 3) {
				android.util.Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				android.util.Log.w(className + "." + methodName + "():" + lineNumber, message);
			}
		} else {
			android.util.Log.w(TAG, message);
		}
	}

	public static void w(String TAG, String message, Throwable tr) {
		if (!LOG_SHOW) {
			return;
		}

		if (DEBUG) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length < 3) {
				android.util.Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				android.util.Log.w(className + "." + methodName + "():" + lineNumber, message, tr);
			}
		} else {
			android.util.Log.w(TAG, message, tr);
		}
	}

	public static void v(String TAG, String message) {
		if (!LOG_SHOW) {
			return;
		}

		if (DEBUG) {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			if (elements.length < 3) {
				android.util.Log.e(TAG, "Stack to shallow");
			} else {
				String fullClassName = elements[3].getClassName();
				String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
				String methodName = elements[3].getMethodName();
				int lineNumber = elements[3].getLineNumber();
				android.util.Log.v(className + "." + methodName + "():" + lineNumber, message);
			}
		} else {
			android.util.Log.v(TAG, message);
		}
	}
}

