package com.qualibits.qualeval;

public class AppSetting {

    private final static AppSetting APP_SETTING = new AppSetting();
    private final static ScreenSettings SCREEN_SETTINGS = new ScreenSettings();

    public static AppSetting getSettings() {
        return APP_SETTING;
    }

    public ScreenSettings getScreenSettings() {
        return SCREEN_SETTINGS;
    }

    public static class ScreenSettings {

        private boolean useVariableName;

        private ScreenSettings() {
            useVariableName = false;
        }

        public boolean shouldUseVariableName() {
            return useVariableName;
        }

        public void setUseVariableName(boolean useVariableName) {
            this.useVariableName = useVariableName;
        }
    }
}
