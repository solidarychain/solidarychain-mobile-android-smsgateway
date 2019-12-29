package network.solidary.smsgateway.request;

import lombok.Data;

@Data
public class SmsRequest {
  private String from;
  private String to;
  private String message;
}
