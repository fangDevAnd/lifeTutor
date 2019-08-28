package com.lyc.bluetooth;

import java.util.UUID;

/**
 * 蓝牙相关的配置类
 */
public class BluetoothToolConfig {

    public static final String ACTION_START_DISCOVERY = "ACTION_START_DISCOVERY";
    public static final String ACTION_SELECTED_DEVICE = "ACTION_SELECTED_DEVICE";
    public static final String DEVICE = "DEVICE";

    //00001101-0000-1000-8000-00805F9B34FB
    //8ce255c0-200a-11e0-ac64-0800200c9a66
    /**
     * 此处，必须使用Android的SSP（协议栈默认）的UUID：
     *
     * 00001101-0000-1000-8000-00805F9B34FB
     *
     * 才能正常和外部的，也是SSP串口的蓝牙设备去连接。
     *
     *
     */
    public static final UUID PRIVATE_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static final int MESSAGE_CONNECT_ERROR = 0x00000003; //消息连接错误
    public static final int MESSAGE_CONNECT_SUCCESS = 0x00000002; // 连接成功
    public static final int MESSAGE_READ_OBJECT = 0x00000004; // 读取
    public static final int MESSAGE_READ_BYTE = 0x00000005;
    public static final String ACTION_CONNECT_ERROR = "ACTION_CONNECT_ERROR"; // 连接错误
    public static final String ACTION_CONNECT_SUCCESS = "ACTION_CONNECT_SUCCESS"; // 连接成功
    public static final String ACTION_DATA_TO_GAME = "ACTION_DATA_TO_GAME";

    public static final String DATA = "DATA";
    public static final String ACTION_DATA_TO_SERVICE = "ACTION_DATA_TO_SERVICE"; //

    public static final String ACTION_FOUND_DEVICE = "ACTION_FOUND_DEVICE";

    public static final String ACTION_NOT_FOUND_SERVER = "ACTION_NOT_FOUND_DEVICE"; //

    public static final String ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE";
    public static final String ACTION_START_SERVER = "ACTION_STARRT_SERVER";

}
