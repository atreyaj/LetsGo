package tie.hackathon.travelguide.destinations.description;

/**
 * Created by atreya on 18/06/17.
 */
public interface FinalCityInfoView {
    void onPause();
    void onResume();
    void onStart();
    void onStop();
    void showProgress();
    void dismissProgress();
    void parseResult(String description, String iconUrl, String temp, String humidity, String weatherInfo,
                     String lat, String lon);
}
