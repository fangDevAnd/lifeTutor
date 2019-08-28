package com.lyc.bluetooth;


/**
 * 设备事件
 * 封装的是当搜索到蓝牙可连接对象是
 * 将 BluetoothDevice 的
 * <p>
 * DeviceEvent event = new DeviceEvent(device.getName(),
 * device.getAddress());
 */
public class DeviceEvent {

    private String mac;
    private String name;


    public String getMac() {
        return mac;
    }


    public void setMac(String mac) {
        this.mac = mac;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public DeviceEvent(String name, String mac) {

        this.mac = mac;
        this.name = name;
        // TODO Auto-generated constructor stub
    }
}
