#ifndef WebSocketManager_H
#define WebSocketManager_H
#include "RousingLight.h"
#include <WebSocketsServer.h>
class WebSocketManager
{
  private:
public:
  WebSocketManager();
  void setup(RousingLight connectedLight);
  void loop();
  void webSocketEvent(uint8_t num, WStype_t type, uint8_t * payload, size_t length);
};
#endif
