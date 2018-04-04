#include <RousingLight.h>
class NeoPixelStrip : public RousingLight {
  public:
    NeoPixelStrip();
    virtual void setup();
    virtual void update();
    virtual RousingLightFeatures getCapabilities();
    virtual LightState getCurrentState();
    virtual void setCurrentState(LightState state);
};