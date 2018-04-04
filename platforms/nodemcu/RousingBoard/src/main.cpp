#include <Arduino.h>
#include <NeoPixelStrip.h>
#include <AnalogStrip.h>
void setup() {
    // put your setup code here, to run once:
    AnalogStrip aStrip;
    NeoPixelStrip nStrip;
    printf("aStrip Type:%d",aStrip.getCapabilities().type);
    printf("nStrip Type:%d",nStrip.getCapabilities().type);
}

void loop() {
    // put your main code here, to run repeatedly:
}