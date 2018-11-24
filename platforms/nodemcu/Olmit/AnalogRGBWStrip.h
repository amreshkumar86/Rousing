#include "AnalogStrip.h"
class AnalogRGBWStrip : public AnalogStrip {
	int _rPin, _gPin, _bPin, _wPin;
    public:
    AnalogRGBWStrip(int rPin,int gPin,int bPin,int wPin);
    void setup();
    void update();
    RousingLightFeatures getCapabilities();
    LightState getCurrentState();
    void setCurrentState(LightState state);
};
