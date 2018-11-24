#include "RousingWifiManager.h"
#include <DNSServer.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>
//for LED status
#include <Ticker.h>
#include <ESP8266WebServer.h>
#include <ESP8266SSDP.h>
//ESP8266WebServer HTTP(80);
Ticker ticker;
RousingLight _connectedLight;
String deviceInfoJSON = "";
String RousingWifiManager::getDeviceInfo() {
  char *str = "";
  sprintf(str,"%s{\"type\":\"%s\",", str,_connectedLight.capability.type == 0 ? "analog":"digital");
  sprintf(str,"%s\"colorSupport\":{",str);
  sprintf(str,"%s\"r\":%s,",str,_connectedLight.capability.supportedColors.r == 1 ? "true" : "false");
  sprintf(str,"%s\"g\":%s,",str,_connectedLight.capability.supportedColors.g == 1 ? "true" : "false");
  sprintf(str,"%s\"b\":%s,",str,_connectedLight.capability.supportedColors.b == 1 ? "true" : "false");
  sprintf(str,"%s\"w\":%s",str,_connectedLight.capability.supportedColors.w == 1 ? "true" : "false");
  sprintf(str,"%s}}",str);
  return String(str);
}


void tick()
{
	//toggle state
	int state = digitalRead(BUILTIN_LED);  // get the current state of GPIO1 pin
	digitalWrite(BUILTIN_LED, !state);     // set pin to the opposite state
}

//gets called when WiFiManager enters configuration mode
void configModeCallback (WiFiManager *myWiFiManager) {
	Serial.println("Entered config mode");
	Serial.println(WiFi.softAPIP());
	//if you used auto generated SSID, print it
	Serial.println(myWiFiManager->getConfigPortalSSID());
	//entered config mode, make led toggle faster
	ticker.attach(0.2, tick);
}

RousingWifiManager::RousingWifiManager() {
	 
}

void RousingWifiManager::setup(RousingLight connectedLight) {
  _connectedLight = connectedLight;
  deviceInfoJSON = getDeviceInfo();
  //set led pin as output
  pinMode(BUILTIN_LED, OUTPUT);
  // start ticker with 0.5 because we start in AP mode and try to connect
  ticker.attach(0.6, tick);

  //WiFiManager
  //Local intialization. Once its business is done, there is no need to keep it around
  WiFiManager wifiManager;
  //reset settings - for testing
  //wifiManager.resetSettings();

  //set callback that gets called when connecting to previous WiFi fails, and enters Access Point mode
  wifiManager.setAPCallback(configModeCallback);

  //fetches ssid and pass and tries to connect
  //if it does not connect it starts an access point with the specified name
  //here  "AutoConnectAP"
  //and goes into a blocking loop awaiting configuration
  if (!wifiManager.autoConnect("Rousing")) {
    Serial.println("failed to connect and hit timeout");
    //reset and try again, or maybe put it to deep sleep
    ESP.reset();
    delay(1000);
  }

  //if you get here you have connected to the WiFi
  ticker.detach();
  //keep LED on
  digitalWrite(BUILTIN_LED, LOW);
  _ip = WiFi.localIP();
//  HTTP.on("/index.html", HTTP_GET, [](){
//      HTTP.send(200, "text/plain",  "Hello World!!");
//  });
//  HTTP.on("/deviceInfo.json", HTTP_GET, [](){
//    HTTP.send(200, "application/json", deviceInfoJSON);
//  });
//  HTTP.on("/description.xml", HTTP_GET, [](){
//    SSDP.schema(HTTP.client());
//  });
//  HTTP.begin();
//
//  Serial.printf("Starting SSDP...\n");
//  SSDP.setSchemaURL("description.xml");
//  SSDP.setDeviceType("urn:schemas-upnp-org:service:RousingLight:1");
//  SSDP.setHTTPPort(80);
//  SSDP.setName("Rousing NeoPixel Strip");
//  SSDP.setSerialNumber("0000000002");
//  SSDP.setURL("index.html");
//  SSDP.setModelName("Rousing NeoPixel Strip 2018");
//  SSDP.setModelNumber("RNP00001");
//  SSDP.setModelURL("deviceInfo.json");
//  SSDP.begin();
}

IPAddress RousingWifiManager::getConnectedIP() {
  return _ip;
}

void RousingWifiManager::loop() {
//	HTTP.handleClient();
}
