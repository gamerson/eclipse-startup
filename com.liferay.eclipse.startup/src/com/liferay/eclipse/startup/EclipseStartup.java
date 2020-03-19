package com.liferay.eclipse.startup;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;

public class EclipseStartup extends AbstractUIPlugin implements IStartup {

	private static EclipseStartup _plugin;

	public static final String FIRST_LAUNCH_COMPLETE = "FIRST_LAUNCH_COMPLETE";

	public static final String PLUGIN_ID = "com.liferay.eclipse.startup";

	private static boolean _isFirstLaunch() {
		IScopeContext[] scopes = {ConfigurationScope.INSTANCE};

		IPreferencesService preferencesService = Platform.getPreferencesService();

		return !preferencesService.getBoolean(PLUGIN_ID, FIRST_LAUNCH_COMPLETE, false, scopes);
	}

	public static EclipseStartup getDefault() {
		return _plugin;
	}

	private void _doStuffToEclipse() {

	}

	private void _doStuffInUI() {
		UIUtil.async(() -> MessageDialog.openConfirm(UIUtil.getActiveShell(), "Hello Eclipse!", "This is executed just once."));
	}

	private void _firstLaunchComplete() {
		IEclipsePreferences eclipseInstallationPreferences = _getEclipseInstallationPreferences();

		eclipseInstallationPreferences.putBoolean(FIRST_LAUNCH_COMPLETE, true);

		try {
			eclipseInstallationPreferences.flush();
		}
		catch (BackingStoreException e) {
		}
	}

	public IEclipsePreferences _getEclipseInstallationPreferences() {
		return ConfigurationScope.INSTANCE.getNode(PLUGIN_ID);
	}

	@Override
	public void earlyStartup() {
		if (_isFirstLaunch()) {
			_doStuffToEclipse();
			_doStuffInUI();

			_firstLaunchComplete();
		}
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);

		_plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		_plugin = null;

		super.stop(context);
	}
}