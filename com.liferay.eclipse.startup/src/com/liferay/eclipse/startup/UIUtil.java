package com.liferay.eclipse.startup;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class UIUtil {

	public static Shell getActiveShell() {
		Shell[] retval = new Shell[1];

		Display display = Display.getDefault();

		display.syncExec(
			new Runnable() {

				public void run() {
					Display display = Display.getDefault();

					retval[0] = display.getActiveShell();
				}

			});

		return retval[0];
	}

	public static void async(Runnable runnable) {
		if (runnable != null) {
			try {
				Display display = Display.getDefault();

				display.asyncExec(runnable);
			}
			catch (Throwable t) {

				// ignore

			}
		}
	}
}
