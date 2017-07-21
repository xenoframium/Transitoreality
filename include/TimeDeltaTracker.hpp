#ifndef TimeDeltaTracker_hpp
#define TimeDeltaTracker_hpp

namespace transitoreality {
    class TimeDelta {
        double timeElapsed = 0;
        const double updateInterval;
        
    public:
        TimeDelta(double updateInterval) : updateInterval(updateInterval) {}
        
        void increase(double delta);
        bool shouldUpdate() const;
        void resetTime();
    };
}

#endif /* TimeDelta_hpp */
