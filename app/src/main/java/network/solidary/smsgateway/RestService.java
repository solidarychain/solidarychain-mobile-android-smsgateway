package network.solidary.smsgateway;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import io.javalin.Javalin;
import network.solidary.smsgateway.request.SmsRequest;
import network.solidary.smsgateway.response.HelloResponse;

import static android.content.ContentValues.TAG;

public class RestService extends Service {

  public static boolean isRunning = false;
  private SettingsActivity mSettingsActivity;

  public RestService() {
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Javalin app = Javalin.create().start(7000);
    app.get("/", ctx -> {
        HelloResponse mHelloResponse = new HelloResponse("hello", "kapa", "pelo", "ok");
        // ctx.result("Hello World")
        ctx.json(mHelloResponse);
      }
    );
    app.post("/sms", ctx -> {
      Log.i(TAG, ctx.body());
      SmsRequest smsRequest = ctx.bodyAsClass(SmsRequest.class);
      Util.sendSmsRequest(getApplication().getApplicationContext(), smsRequest);
      Log.i(TAG, String.format("sent message: %s", smsRequest.getMessage()));
    });

    isRunning = true;
    return START_STICKY;
  }

  //@Override
  //public IBinder onBind(Intent intent) {
  //  // TODO: Return the communication channel to the service.
  //  throw new UnsupportedOperationException("Not yet implemented");
  //}
}
