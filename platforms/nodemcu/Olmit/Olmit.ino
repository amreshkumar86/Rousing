#include "RousingStrips.h"
#include "RousingWifiManager.h"
#include "WebSocketManager.h"
#include <WebSocketsServer.h>
WebSocketsServer webSocket = WebSocketsServer(81);
RousingWifiManager wifiManager;
WebSocketManager wsManager;

void webSocketEvent(uint8_t num, WStype_t type, uint8_t * payload, size_t length) {

    switch(type) {
        case WStype_DISCONNECTED:
            Serial.printf("[%u] Disconnected!\n", num);
            break;
        case WStype_CONNECTED:
            {
                IPAddress ip = webSocket.remoteIP(num);
                Serial.printf("[%u] Connected from %d.%d.%d.%d url: %s\n", num, ip[0], ip[1], ip[2], ip[3], payload);
        
        // send message to client
        webSocket.sendTXT(num, "Connected");
            }
            break;
        case WStype_TEXT:
            {Serial.printf("[%u] get Text: %s\n", num, payload);

            // send message to client
            // webSocket.sendTXT(num, "message here");

            // send data to all connected clients
            // webSocket.broadcastTXT("message here");
            char *msg = "";
            sprintf(msg,"%s", payload);
            webSocket.sendTXT(num,msg);}
            break;
        case WStype_BIN:
            Serial.printf("[%u] get binary length: %u\n", num, length);
            hexdump(payload, length);

            // send message to client
            // webSocket.sendBIN(num, payload, length);
            break;
    }

}

void setup()
{
  
  Serial.begin(115200);
  RousingLight onboardLight = detectOnboardLight();
  wifiManager.setup(onboardLight);
  IPAddress connectedIP = wifiManager.getConnectedIP();
  Serial.println(connectedIP);
  webSocket.begin();
    webSocket.onEvent(webSocketEvent);
    Serial.println("Started");
//  wsManager.setup(onboardLight);
}

void loop()
{
  webSocket.loop();
//  wifiManager.loop();
}

RousingLight detectOnboardLight()
{
	//DO-D4 should have light pins
	//D5 HIGH = NeoPixel, Output Pin must be D4
	//D6 HIGH = Analog Single Color, Output Pin must be D0
	//D7 HIGH = Analog RGB, Output Pin must be D0,D1,D2
	//D8 HIGH = Analog RGBW, Output Pin must be D0,D1,D2,D3
//	int dFiveInput = digitalRead(D5);
//	int dSixInput = digitalRead(D6);
//	int dSevenInput = digitalRead(D7);
//	int dEightInput = digitalRead(D8);
  RousingLight light;
//	if(dFiveInput == HIGH) {
//		//Light Type NeoPixel
//	}
//	else if(dSixInput == HIGH) {
//		//Light Type Analog SingleColor
//    light = AnalogStrip(D0);
//	}
//	else if(dSevenInput == HIGH) {
//    //Light Type Analog RGB
//    light = AnalogRGBStrip(D0,D1,D2);
//	}
//	else if(dEightInput == HIGH) {
//		//Light Type Analog RGBW
//		light = AnalogRGBWStrip(D0,D1,D2,D4);
//	}
//  returning AnalogRGBW for now
  light = AnalogRGBWStrip(D0,D1,D2,D4);
	return light;
}
