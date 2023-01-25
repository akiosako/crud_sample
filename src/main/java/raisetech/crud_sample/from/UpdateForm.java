package raisetech.crud_sample.from;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateForm {
    private int id;
    @NotNull
    @Size(min = 1,max = 30 ,message = "メッセージは1文字～30文字の間で登録できます。")
    private String msg;
}
