#ifndef RousingLight_H
#define RousingLight_H
#import <Arduino.h>
enum LightType {
    LightTypeAnalog = 0,
    LightTypeDigital,
};

struct colorBits {
  unsigned int r = 0;
  unsigned int g = 0;
  unsigned int b = 0;
  unsigned int w = 0;
};

struct RousingLightFeatures
{
	  LightType type;
    struct colorBits supportedColors; //Bit order is RGBW
};

struct LightState
{
    bool on = false;
    struct colorBits currentColor;
    int brightness;
};

class RousingLight
{
    protected:
    
    public:
    RousingLightFeatures capability;
    LightState mState;
    RousingLight();
    void setup();
    void update();
    RousingLightFeatures getCapabilities();
    LightState getCurrentState();
    void setCurrentState(LightState state);
};
#endif
