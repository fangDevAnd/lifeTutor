package com.android.server;


import android.os.INetworkManagementService;

import java.lang.reflect.Constructor;

public class NetworkManagementService extends INetworkManagementService.Stub {


    /**
     * mConnector = new NativeDaemonConnector(
     * new NetdCallbackReceiver(), socket, 10, NETD_TAG, 160, wl,
     * FgThread.get().getLooper());
     * <p>
     * 上面的代码是原程序编写的，我们同样需要得到这个实例
     */


    public void setFirewallUidChainRule(int uid, int networkType, boolean allow) {
        //enforceSystemUid();
        final String MOBILE = "mobile";
        final String WIFI = "wifi";
        final String rule = allow ? "allow" : "deny";
        final String chain = (networkType == 1) ? WIFI : MOBILE;

        try {
//            mConnector.execute("firewall", "set_uid_fw_rule", uid, chain, rule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @internal Configure firewall rule by uid and chain
     * @hide
     */
    public void clearFirewallChain(String chain) { //enforceSystemUid();


        try {
//            mConnector.execute("firewall", "clear_fw_chain", chain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } //add by zrx

}
