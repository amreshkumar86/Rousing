#include "AnalogRGBWStrip.h"

AnalogRGBWStrip::AnalogRGBWStrip(int rPin,int gPin,int bPin,int wPin): AnalogStrip(-1) { 
    capability.type = LightTypeAnalog;
    struct colorBits mySupportedColors;
    mySupportedColors.r = 1;
    mySupportedColors.g = 1;
    mySupportedColors.b = 1;
    mySupportedColors.w = 1;
    capability.supportedColors = mySupportedColors;
    _rPin = rPin;
    _gPin = gPin;
    _bPin = bPin;
    _wPin = wPin;
}

void AnalogRGBWStrip::setup() {
    //Should read for saved values and set it up
    pinMode(_rPin, OUTPUT);
    pinMode(_gPin, OUTPUT);
    pinMode(_bPin, OUTPUT);
    pinMode(_wPin, OUTPUT);
}

void AnalogRGBWStrip::update() {

}

RousingLightFeatures AnalogRGBWStrip::getCapabilities() {
    return RousingLight::capability;
}

LightState AnalogRGBWStrip::getCurrentState() {
    return RousingLight::mState;
}

void AnalogRGBWStrip::setCurrentState(LightState state) {
    RousingLight::mState = state;
}
