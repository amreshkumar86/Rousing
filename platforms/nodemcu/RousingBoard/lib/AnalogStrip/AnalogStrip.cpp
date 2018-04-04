#include <AnalogStrip.h>

AnalogStrip::AnalogStrip() { 
    capability.type = LightTypeAnalog;
}

void AnalogStrip::setup() {

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