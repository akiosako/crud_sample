package raisetech.crudsample.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;
import raisetech.crudsample.entity.Message;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MsgMapperTest {

  @Autowired
  MsgMapper msgMapper;

  @Test
  @DataSet(value = "datasets/全てのメッセージが返されること/message.yml")
  @Transactional
  public void 全てのメッセージが返されること() {
    List<Message> msg = msgMapper.findAll();
    assertThat(msg)
            .hasSize(3)
            .contains(
                    new Message(1, "Hello"),
                    new Message(2, "Bye"),
                    new Message(3, "Hi")
            );
  }
  @Test
  @DataSet(value = )
  @Transactional
  public void DBに登録がないとき空の配列が返されること() {
    List<Message> blankMsg = msgMapper.findAll();
    assertThat(blankMsg).hasSize(0);
  }
}
