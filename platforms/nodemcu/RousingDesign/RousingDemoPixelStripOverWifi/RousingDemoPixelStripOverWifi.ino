#include <ESP8266WiFi.h>
#include <Adafruit_NeoPixel.h>
#include <WebSocketsServer.h>
#include <WiFiUdp.h>

//Define Constants
//Wifi Access Point Config
const char *ssid = "RousingNeopixel";
const char *password = "rousingdemo";
unsigned int localPort = 2390;
WiFiUDP Udp;
char packetBuffer[255];
char ipAddBuff[255];
//Neopixel Strip Config
#define PIN 2
#define NUM_LEDS 60
#define BRIGHTNESS 255

//Webserver Config
#define WebSocketPort 81

//Global Vars
WebSocketsServer webSocket = WebSocketsServer(81);
Adafruit_NeoPixel strip = Adafruit_NeoPixel(NUM_LEDS, PIN, NEO_GRBW + NEO_KHZ800);
int r = 0, g = 0, b = 0, w = 0, s = 100, e = 0;
//Setup WiFi Access Point
void setupWifi() {
//  WiFi.softAP(ssid, password);
//  IPAddress myIP = WiFi.softAPIP();
//  Serial.println("AP IP address: ");
//  Serial.println(myIP);
  WiFi.mode(WIFI_STA);
  WiFi.begin("RousingDesign", "RousingInternet");
//  WiFi.begin("BANGERS", "B@NGERS543@!");
  pinMode(LED_BUILTIN, OUTPUT);
  while (WiFi.status() != WL_CONNECTED) {
//    digitalWrite(LED_BUILTIN, LOW);
Serial.print(".");  
    delay(250);
//    digitalWrite(LED_BUILTIN, HIGH);
  }

  digitalWrite(LED_BUILTIN, LOW);

  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  IPAddress ip = WiFi.localIP();
  Serial.println(ip);
  sprintf(ipAddBuff, "ws://%d.%d.%d.%d:81", ip[0], ip[1], ip[2], ip[3] );
  Udp.begin(localPort);
  delay(500);
}

void handleUDP()
{
  // if there's data available, read a packet
  int packetSize = Udp.parsePacket();
  if (packetSize) {
    Serial.print("Received packet of size ");
    Serial.println(packetSize);
    Serial.print("From ");
    IPAddress remoteIp = Udp.remoteIP();
    Serial.print(remoteIp);
    Serial.print(", port ");
    Serial.println(Udp.remotePort());

    // read the packet into packetBufffer
    int len = Udp.read(packetBuffer, 255);
    if (len > 0) {
      packetBuffer[len] = 0;
    }
    Serial.println("Contents:");
    Serial.println(packetBuffer);

    Serial.print("Sending: ");
    Serial.print(ipAddBuff);
    Serial.println(" to:");
    Serial.println(remoteIp);
    // send a reply, to the IP address and port that sent us the packet we received
    Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
    Udp.write(ipAddBuff);
    Udp.endPacket();
  }
}

//Web Socket Handling
void parsePayload(uint8_t* payload) {
  e = 0;
  if(payload[0] == 'c') {
    //Color
    char *str = (char *) &payload[1];
    const char delim[2] = ",";
    char *token;
    int colors[4],i=0;
    
    /* get the first token */
    token = strtok(str, delim);
    
    /* walk through other tokens */
    while( token != NULL ) {
      colors[i++] = atoi(token);
      token = strtok(NULL, delim);
    }
    r = colors[0];
    g = colors[1];
    b = colors[2];
    w = colors[3];
    if(r > 255) r = 255;
    if(g > 255) g = 255;
    if(b > 255) b = 255;
    if(w > 255) w = 255;
    if(r < 0) r = 0;
    if(g < 0) r = 0;
    if(b < 0) r = 0;
    if(w < 0) r = 0;
    setStripColor(getColorValue(r,g,b,w));
  }
  else if(payload[0] == 'b') {
    //Brightness
    int sat = atoi((char*)&payload[1]);
    if(sat > 100) {
      sat = 100;
    }
    else if(sat < 0) {
      sat = 0;
    }
    setStripBrightness(sat);
  }
  else if(payload[0] == 'p') {
    //Power
    int isOn = atoi((char*)&payload[1]);
    handlePower(isOn > 0);
  }
  else if(payload[0] == 'e') {
    //Power
    e = atoi((char*)&payload[1]);
  }
}

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
            parsePayload(payload);
            break;
    }

}

void handlePower(bool isOn) {
    if(isOn == true) {
      r = g = b = 0; w = 255;
      setStripBrightness(100);
    }
    else {
      r = g = b = w = 0;
      setStripBrightness(0);
    }
}

//Setup Pixel Strip
void setupPixelStrip() {
  strip.setBrightness(BRIGHTNESS);
  strip.begin();
  strip.show(); // Initialize all pixels to 'off'
  doPixelBootCheck();
}

void colorWipe(uint32_t c, uint8_t wait) {
  for(uint16_t i=0; i<strip.numPixels(); i++) {
    strip.setPixelColor(i, c);
    strip.show();
    delay(wait);
  }
}

void setStripColor(uint32_t c) {
  for(uint16_t i=0; i<strip.numPixels(); i++) {
    strip.setPixelColor(i, c);
  }
  strip.show();
}

void setStripBrightness(int sVal){
  if(sVal < 0) sVal = 0;
  if(sVal > 100) sVal = 100;
  s = sVal;
  setStripColor(getColorValue(r,g,b,w));
}

//Pixel Strip Boot Check
void doPixelBootCheck() {
  colorWipe(getColorValue(255,0,0,0), 10); // Red
  colorWipe(getColorValue(0,255,0,0), 10); // Green
  colorWipe(getColorValue(0,0,255,0), 10); // Blue
  colorWipe(strip.Color(0, 0, 0, 255), 10); // White
  delay(500);
  r = g = b = w = 0;
  setStripBrightness(0);//Turn off
}

uint32_t getColorValue(int rVal,int gVal,int bVal, int wVal) {  
  return strip.Color((rVal*s)/100,(gVal*s)/100,(bVal*s)/100,(wVal*s)/100);
}

byte neopix_gamma[] = {
    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
    0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  1,  1,  1,
    1,  1,  1,  1,  1,  1,  1,  1,  1,  2,  2,  2,  2,  2,  2,  2,
    2,  3,  3,  3,  3,  3,  3,  3,  4,  4,  4,  4,  4,  5,  5,  5,
    5,  6,  6,  6,  6,  7,  7,  7,  7,  8,  8,  8,  9,  9,  9, 10,
   10, 10, 11, 11, 11, 12, 12, 13, 13, 13, 14, 14, 15, 15, 16, 16,
   17, 17, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 24, 24, 25,
   25, 26, 27, 27, 28, 29, 29, 30, 31, 32, 32, 33, 34, 35, 35, 36,
   37, 38, 39, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 50,
   51, 52, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 66, 67, 68,
   69, 70, 72, 73, 74, 75, 77, 78, 79, 81, 82, 83, 85, 86, 87, 89,
   90, 92, 93, 95, 96, 98, 99,101,102,104,105,107,109,110,112,114,
  115,117,119,120,122,124,126,127,129,131,133,135,137,138,140,142,
  144,146,148,150,152,154,156,158,160,162,164,167,169,171,173,175,
  177,180,182,184,186,189,191,193,196,198,200,203,205,208,210,213,
  215,218,220,223,225,228,231,233,236,239,241,244,247,249,252,255 };
  
void effect1() {
  //Red Chase
  colorWipe(strip.Color(255, 0, 0), 10);
  colorWipe(getColorValue(0,0,0,0),10);
  delay(50);
}

void effect2() {
  //Blue Chase
  colorWipe(strip.Color(0, 0, 255), 10);
  colorWipe(getColorValue(0,0,0,0),10);
  delay(500);
}

void effect3() {
  //Green Chase
  colorWipe(strip.Color(0, 255, 0), 10);
  colorWipe(getColorValue(0,0,0,0),10);
  delay(500);
}

void effect4() {
  //Pulse White
  uint8_t wait = 50;
  for(int j = 0; j < 256 ; j++){
      for(uint16_t i=0; i<strip.numPixels(); i++) {
          strip.setPixelColor(i, strip.Color(0,0,0, neopix_gamma[j] ) );
        }
        delay(wait);
        strip.show();
      }

  for(int j = 255; j >= 0 ; j--){
      for(uint16_t i=0; i<strip.numPixels(); i++) {
          strip.setPixelColor(i, strip.Color(0,0,0, neopix_gamma[j] ) );
        }
        delay(wait);
        strip.show();
      }  
}

void effect5() {
  rainbowFade2White(3,3,1);
}
uint8_t red(uint32_t c) {
  return (c >> 16);
}
uint8_t green(uint32_t c) {
  return (c >> 8);
}
uint8_t blue(uint32_t c) {
  return (c);
}

void rainbowFade2White(uint8_t wait, int rainbowLoops, int whiteLoops) {
  float fadeMax = 100.0;
  int fadeVal = 0;
  uint32_t wheelVal;
  int redVal, greenVal, blueVal;

  for(int k = 0 ; k < rainbowLoops ; k ++){
    
    for(int j=0; j<256; j++) { // 5 cycles of all colors on wheel

      for(int i=0; i< strip.numPixels(); i++) {

        wheelVal = Wheel(((i * 256 / strip.numPixels()) + j) & 255);

        redVal = red(wheelVal) * float(fadeVal/fadeMax);
        greenVal = green(wheelVal) * float(fadeVal/fadeMax);
        blueVal = blue(wheelVal) * float(fadeVal/fadeMax);

        strip.setPixelColor( i, strip.Color( redVal, greenVal, blueVal ) );

      }

      //First loop, fade in!
      if(k == 0 && fadeVal < fadeMax-1) {
          fadeVal++;
      }

      //Last loop, fade out!
      else if(k == rainbowLoops - 1 && j > 255 - fadeMax ){
          fadeVal--;
      }

        strip.show();
        delay(wait);
    }
  
  }



  delay(50);


  for(int k = 0 ; k < whiteLoops ; k ++){

    for(int j = 0; j < 256 ; j++){

        for(uint16_t i=0; i < strip.numPixels(); i++) {
            strip.setPixelColor(i, strip.Color(0,0,0, neopix_gamma[j] ) );
          }
          strip.show();
        }

        delay(1000);
    for(int j = 255; j >= 0 ; j--){

        for(uint16_t i=0; i < strip.numPixels(); i++) {
            strip.setPixelColor(i, strip.Color(0,0,0, neopix_gamma[j] ) );
          }
          strip.show();
        }
  }

  delay(50);


}

void effect6() {
  rainbow(5);
}

uint32_t Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85) {
    return strip.Color(255 - WheelPos * 3, 0, WheelPos * 3,0);
  }
  if(WheelPos < 170) {
    WheelPos -= 85;
    return strip.Color(0, WheelPos * 3, 255 - WheelPos * 3,0);
  }
  WheelPos -= 170;
  return strip.Color(WheelPos * 3, 255 - WheelPos * 3, 0,0);
}

void rainbow(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256; j++) {
    for(i=0; i<strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel((i+j) & 255));
    }
    strip.show();
    delay(wait);
  }
}

// Slightly different, this makes the rainbow equally distributed throughout
void rainbowCycle(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256 * 5; j++) { // 5 cycles of all colors on wheel
    for(i=0; i< strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel(((i * 256 / strip.numPixels()) + j) & 255));
    }
    strip.show();
    delay(wait);
  }
}

void setup() {
 delay(1000);
 Serial.begin(115200);
 Serial.println();
 Serial.print("Configuring access point...");
 setupWifi();
// setupPixelStrip();
// webSocket.begin();
// webSocket.onEvent(webSocketEvent);
}

void loop() {
//  webSocket.loop();
  handleUDP();
  if(e > 0) {
    switch(e) {
      case 1:
      effect1();
      break;
      case 2:
      effect2();
      break;
      case 3:
      effect3();
      break;
      case 4:
      effect5();
      break;
      case 5:
      effect6();
      break;
//      case 6:
//      effect6();
      break;
      default:
      break;
    }
  }
}
