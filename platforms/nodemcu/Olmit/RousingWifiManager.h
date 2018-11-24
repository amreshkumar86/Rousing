#ifndef RousingWifiManager_H
#define RousingWifiManager_H
#include <ESP8266WiFi.h>
#include "RousingLight.h"
class RousingWifiManager
{
	IPAddress _ip;
  private:
  String getDeviceInfo();
public:
	RousingWifiManager();
    void setup(RousingLight connectedLight);
    IPAddress getConnectedIP();
    void loop();
};

#endif
