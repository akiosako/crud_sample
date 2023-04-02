package raisetech.crudsample.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MsgForm {
  @NotNull
  @Size(min = 1, max = 20, message = "メッセージは1文字～20文字の間で登録できます。")
  private String msg;
}
