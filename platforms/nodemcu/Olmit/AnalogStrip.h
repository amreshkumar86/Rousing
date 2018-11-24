#ifndef AnalogStrip_H
#define AnalogStrip_H
#include "RousingLight.h"
class AnalogStrip : public RousingLight {
	int _outPin;
    public:
    AnalogStrip(int outPin);
    void setup();
    void update();
    RousingLightFeatures getCapabilities();
    LightState getCurrentState();
    void setCurrentState(LightState state);
};
#endif
