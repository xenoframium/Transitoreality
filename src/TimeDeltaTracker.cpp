#include "TimeDeltaTracker.hpp"

using namespace transitoreality;

void TimeDelta::increase(double delta) {
    timeElapsed += delta;
}

bool TimeDelta::shouldUpdate() const {
    return timeElapsed >= updateInterval;
}

void TimeDelta::resetTime() {
    timeElapsed = 0;
}
