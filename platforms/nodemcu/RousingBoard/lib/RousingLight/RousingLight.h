#ifndef RousingLight_H
#define RousingLight_H
enum LightType {
    LightTypeAnalog = 0,
    LightTypeDigital,
};

struct RousingLightFeatures
{
	LightType type;
    int supportedColors; //Bit order is RGBW
};

struct LightState
{
    bool on = false;
    int color;
    int brightness;
};

class RousingLight
{
    protected:
    RousingLightFeatures capability;
    LightState mState;
    public:
    virtual void setup() = 0;
    virtual void update() = 0;
    virtual RousingLightFeatures getCapabilities() = 0;
    virtual LightState getCurrentState() = 0;
    virtual void setCurrentState(LightState state) = 0;
};
#endif