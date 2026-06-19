package com.qualibits.qualeval;

import com.qualibits.qualeval.exec.ExpressionParser;

public class AppSetting {

    private final static AppSetting APP_SETTING = new AppSetting();
    private final static ScreenSettings SCREEN_SETTINGS = new ScreenSettings();

    private ExpressionParser expressionParser;

    public static AppSetting getSettings() {
        return APP_SETTING;
    }

    public ScreenSettings getScreenSettings() {
        return SCREEN_SETTINGS;
    }

    public void setParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    public ExpressionParser getParser() {
        return expressionParser;
    }

    public static class ScreenSettings {

        private boolean useVariableName;
        private boolean shouldTurnOffNormalizationCompletely, shouldTurnOffNormalizationForDisplay;

        private ScreenSettings() {
            useVariableName = false;
            shouldTurnOffNormalizationForDisplay = false;
            shouldTurnOffNormalizationCompletely = false;
        }

        public boolean shouldUseVariableName() {
            return useVariableName;
        }

        public void setUseVariableName(boolean useVariableName) {
            this.useVariableName = useVariableName;
        }

        public void setTurnOffNormalizationCompletely(boolean shouldTurnOff) {
            this.shouldTurnOffNormalizationCompletely = shouldTurnOff;
        }

        public void setTurnOffNormalizationForDisplay(boolean shouldTurnOff) {
            this.shouldTurnOffNormalizationForDisplay = shouldTurnOff;
        }
    }
}
