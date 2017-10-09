package xenoframium.transitoreality.gfx;

interface  DimensionChangeCallback {
    void onDimensionChange(float width, float height);
}

/**
 * Created by chrisjung on 9/10/17.
 */
interface DynamicComponent {
    float getHeight();
    float getWidth();
    float getZValue();
    void setZValue(float zValue);
    void subscribeUpdateCallback(DimensionChangeCallback callback);
    void unsubscribeUpdateCallback(DimensionChangeCallback callback);
}
