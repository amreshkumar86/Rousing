#include "AnalogRGBStrip.h"

AnalogRGBStrip::AnalogRGBStrip(int rPin,int gPin,int bPin): AnalogStrip(-1) { 
    RousingLight::capability.type = LightTypeAnalog;
    struct colorBits mySupportedColors;
    mySupportedColors.r = 1;
    mySupportedColors.g = 1;
    mySupportedColors.b = 1;
    RousingLight::capability.supportedColors = mySupportedColors;
    _rPin = rPin;
    _gPin = gPin;
    _bPin = bPin;
}

void AnalogRGBStrip::setup() {
	pinMode(_rPin, OUTPUT);
	pinMode(_gPin, OUTPUT);
	pinMode(_bPin, OUTPUT);
}

void AnalogRGBStrip::update() {

}

RousingLightFeatures AnalogRGBStrip::getCapabilities() {
    return RousingLight::capability;
}

LightState AnalogRGBStrip::getCurrentState() {
    return RousingLight::mState;
}

void AnalogRGBStrip::setCurrentState(LightState state) {
    RousingLight::mState = state;
}
