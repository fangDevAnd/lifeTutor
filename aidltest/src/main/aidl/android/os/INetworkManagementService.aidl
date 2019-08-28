// INetworkManagementService.aidl
package android.os;

// Declare any non-default types here with import statements

interface INetworkManagementService {

  void setFirewallUidChainRule(int uid, int networkType, boolean allow); //zrx add
  void clearFirewallChain(String chain);   //zrx add

}
