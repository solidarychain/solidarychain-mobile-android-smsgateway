package network.solidary.smsgateway;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import network.solidary.smsgateway.request.SmsRequest;

public class Util {

  private static final int MAX_SMS_CARACTERS = 140;

  public static void sendSmsRequest(Context context, SmsRequest smsRequest) {
    try {
      //SmsManager smsManager = SmsManager.getDefault();
      //smsManager.sendTextMessage(smsRequest.getTo(), smsRequest.getFrom(), smsRequest.getMessage(), null, null);
      sendSms(context, smsRequest.getTo(), smsRequest.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void sendSms(Context context, String phoneNumber, String content) {
    if (StringUtils.isEmpty(content)) return;
    PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
    SmsManager smsManager = SmsManager.getDefault();
    if (content.length() >= MAX_SMS_CARACTERS) {
      List<String> ms = smsManager.divideMessage(content);
      for (String str : ms) {
        smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
      }
    } else {
      smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
    }
  }
}
