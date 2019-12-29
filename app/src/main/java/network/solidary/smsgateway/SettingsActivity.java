package network.solidary.smsgateway;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

  private static final int SMS_PERMISSION_CODE = 1;
  protected App mApp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.settings_activity);
    getSupportFragmentManager()
      .beginTransaction()
      .replace(R.id.settings, new SettingsFragment())
      .commit();
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // init mApp
    mApp = (App) this.getApplicationContext();

    // request permissions
    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
      Log.d("permission", "permission denied to SEND_SMS - requesting it");
      String[] permissions = {Manifest.permission.SEND_SMS};
      requestPermissions(permissions, SMS_PERMISSION_CODE);
    }

    // start service
    Intent intent = new Intent(this, RestService.class);
    startService(intent);
  }

  protected void onResume() {
    super.onResume();
    mApp.setCurrentActivity(this);
  }

  protected void onPause() {
    clearReferences();
    super.onPause();
  }

  protected void onDestroy() {
    clearReferences();
    super.onDestroy();
  }

  public static class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
  }

  private void clearReferences() {
    Activity currActivity = mApp.getCurrentActivity();
    if (this.equals(currActivity))
      mApp.setCurrentActivity(null);
  }

  /**
   * Check if we have SMS permission
   */
  public boolean isSmsPermissionGranted() {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
  }
}