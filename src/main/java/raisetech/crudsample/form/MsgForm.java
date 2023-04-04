package raisetech.crudsample.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MsgForm {
  @NotEmpty
  @Size(min = 1, max = 20, message = "メッセージは{min}文字～{max}文字の間で登録できます。")
  private String msg;
}
