package raisetech.crudsample.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {
  private int id;
  @NotEmpty
  @Size(min = 1, max = 20, message = "メッセージは{min}文字～{max}文字の間で登録できます。")
  private String msg;

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Message) {
      Message other = (Message) obj;
      return other.msg.equals(this.msg) && other.id == this.id;
    }
    return false;
  }

  public Message() {

  }
}
