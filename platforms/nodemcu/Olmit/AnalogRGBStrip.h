#include "AnalogStrip.h"
class AnalogRGBStrip : public AnalogStrip {
	int _rPin, _gPin, _bPin;
    public:
    AnalogRGBStrip(int rPin,int gPin,int bPin);
    void setup();
    void update();
    RousingLightFeatures getCapabilities();
    LightState getCurrentState();
    void setCurrentState(LightState state);
};
