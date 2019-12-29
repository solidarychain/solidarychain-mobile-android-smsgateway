package network.solidary.smsgateway.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HelloResponse {
  private String message;
  private String from;
  private String to;
  private String result;
  private LocalDateTime dateTime;

  public HelloResponse(String message, String from, String to, String result) {
    this.message = message;
    this.from = from;
    this.to = to;
    this.result = result;
    this.dateTime = LocalDateTime.now();
  }
}
