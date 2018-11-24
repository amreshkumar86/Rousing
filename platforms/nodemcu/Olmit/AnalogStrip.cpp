#include "AnalogStrip.h"

AnalogStrip::AnalogStrip(int outPin) : RousingLight() { 
    AnalogStrip::capability.type = LightTypeAnalog;
    _outPin = outPin;
}

void AnalogStrip::setup() {
	pinMode(_outPin, OUTPUT);
	//Should read for saved values and set it up
}

void AnalogStrip::update() {

}

RousingLightFeatures AnalogStrip::getCapabilities() {
    return RousingLight::capability;
}

LightState AnalogStrip::getCurrentState() {
    return RousingLight::mState;
}

void AnalogStrip::setCurrentState(LightState state) {
    RousingLight::mState = state;
}
