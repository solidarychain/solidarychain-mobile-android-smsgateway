package network.solidary.smsgateway;

import android.app.Application;

public class App extends Application {
  public void onCreate() {
    super.onCreate();
  }

  private SettingsActivity mSettingsActivity = null;

  public SettingsActivity getCurrentActivity(){
    return mSettingsActivity;
  }
  public void setCurrentActivity(SettingsActivity mCurrentActivity){
    this.mSettingsActivity = mCurrentActivity;
  }
}