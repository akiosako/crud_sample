package raisetech.crudsample.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {
  private int id;
  @NotNull
  @Size(min = 1, max = 30, message = "メッセージは1文字～30文字の間で登録できます。")
  private String msg;

  public Message() {
  }
}

