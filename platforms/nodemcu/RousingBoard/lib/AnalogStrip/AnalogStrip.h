#include <RousingLight.h>
class AnalogStrip : public RousingLight {
    public:
    AnalogStrip();
    virtual void setup();
    virtual void update();
    virtual RousingLightFeatures getCapabilities();
    virtual LightState getCurrentState();
    virtual void setCurrentState(LightState state);
};