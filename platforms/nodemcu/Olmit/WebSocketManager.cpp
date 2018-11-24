#include "WebSocketManager.h"
#include <ESP8266WebServer.h>

#define WebSocketPort 81

//Web Socket Handling
//WebSocketsServer *webSocketServer;

void WebSocketManager::webSocketEvent(uint8_t num, WStype_t type, uint8_t * payload, size_t length) {
Serial.printf("WSEvent...\n");
    switch(type) {
        case WStype_DISCONNECTED:
            {
              Serial.printf("[%u] Disconnected!\n", num);
            }
            break;
        case WStype_CONNECTED:
            {
//                IPAddress ip = webSocketServer->remoteIP(num);
//                Serial.printf("[%u] Connected from %d.%d.%d.%d url: %s\n", num, ip[0], ip[1], ip[2], ip[3], payload);
            }
            break;
        case WStype_TEXT:
            {
              char *msg = "";
              sprintf(msg,"%s", payload);
//              webSocketServer->sendTXT(num,msg);
            }
            break;
        case WStype_ERROR:
            Serial.printf("Error");
            break;
    }

}


WebSocketManager::WebSocketManager() {
  
}

void WebSocketManager::setup(RousingLight connectedLight) {
//  WebSocketsServer wss = WebSocketsServer(81);
//  webSocketServer = &wss;
//Serial.printf("Setup...\n");
//  wss.begin();
//  wss.onEvent(webSocketEvent);
//  Serial.printf("Started...\n");
}

void WebSocketManager::loop() {
//  (*(webSocketServer)).loop();
//  webSocketServer->loop();
}
