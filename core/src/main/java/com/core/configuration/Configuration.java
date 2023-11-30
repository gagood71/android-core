package com.core.configuration;

import com.core.R;

public class Configuration {
    public static final class Color {
        public static final int NORMAL_MAIN_COLOR = R.color.normal_main_color;

        public static final int NORMAL_BUTTON_UNABLE = R.drawable.normal_button_unable;
        public static final int NORMAL_BUTTON_ENABLE = R.drawable.normal_button_enable;
        public static final int NORMAL_BUTTON_FONT_COLOR = R.color.normal_button_font_color;

        public static final int NORMAL_DARK_FONT_COLOR = R.color.normal_dark_font_color;
        public static final int NORMAL_LIGHT_FONT_COLOR = R.color.normal_light_font_color;

        public static final int NORMAL_BOTTOM_LINE_COLOR = R.color.normal_bottom_line_color;
        public static final int NORMAL_WARNING_COLOR = R.color.normal_warning_color;
    }

    public static final class String {
        public static final int 系統 = R.string.系統;
        public static final int 系統初始化 = R.string.系統初始化;
        public static final int 系統設定 = R.string.系統設定;
        public static final int 系統提示 = R.string.系統提示;
        public static final int 系統訊息 = R.string.系統訊息;
        public static final int 系統記錄 = R.string.系統記錄;

        public static final int 確認 = R.string.確認;
        public static final int 取消 = R.string.取消;
        public static final int 警告 = R.string.警告;
        public static final int 連接 = R.string.連接;
        public static final int 連接中 = R.string.連接中;
        public static final int 連接成功 = R.string.連接成功;
        public static final int 連接失敗 = R.string.連接失敗;
    }

    public static final class Time {
        public static final long TIME_0 = 0;
        public static final long TIME_500 = 500;
        public static final long TIME_1000 = 1000;
        public static final long TIME_10000 = 10000;
        public static final long TIME_20000 = 20000;
        public static final long TIME_60000 = 60000;
    }

    public static final class Permission {
        public static final int ACCESS_COARSE_LOCATION_PERMISSION = 100;
        public static final int BLUETOOTH_PERMISSION = 101;
        public static final int BLUETOOTH_PERMISSIONS = 102;
    }
}
