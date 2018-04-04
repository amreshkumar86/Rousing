#include <Adafruit_NeoPixel.h>
#include <NeoPixelStrip.h>

NeoPixelStrip::NeoPixelStrip() {
    capability.type = LightTypeDigital;
}

void NeoPixelStrip::setup() {

}

void NeoPixelStrip::update() {

}

RousingLightFeatures NeoPixelStrip::getCapabilities() {
    return RousingLight::capability;
}

LightState NeoPixelStrip::getCurrentState() {
    return RousingLight::mState;
}

void NeoPixelStrip::setCurrentState(LightState state) {
    RousingLight::mState = state;
}