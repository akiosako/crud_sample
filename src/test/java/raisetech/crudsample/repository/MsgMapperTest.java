package raisetech.crudsample.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;
import raisetech.crudsample.entity.Message;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
  @DataSet(value = "datasets/空の配列が返されること/message.yml")
  @Transactional
  public void DBに登録がないとき空の配列が返されること() {
    List<Message> blankMsg = msgMapper.findAll();
    assertThat(blankMsg).isEmpty();
  }

  @Test
  @DataSet(value = "datasets/指定されたidのメッセージが返されること/message.yml")
  @Transactional
  public void 指定されたidのメッセージが返されること() {
    Optional<Message> msg = msgMapper.findById(1);
    assertThat(msg).isEqualTo(Optional.of(new Message(1, "Hello")));
  }

  @Test
  @DataSet(value = "datasets/メッセージが登録されること/set_message.yml")
  @ExpectedDataSet(value = "datasets/メッセージが登録されること/expected_message.yml", ignoreCols = "id")
  @Transactional
  public void メッセージが登録されること() {
    msgMapper.createMsg(new Message(1, "Hello"));
  }

  @Test
  @DataSet(value = "datasets/メッセージが更新されること/set_message.yml")
  @ExpectedDataSet(value = "datasets/メッセージが更新されること/expected_message.yml")
  @Transactional
  public void 指定されたidのメッセージが存在する時メッセージが更新されること() {
    msgMapper.updateMsg(1, "Bye");
  }

  @Test
  @DataSet(value = "datasets/メッセージが削除されること/set_message.yml")
  @ExpectedDataSet(value = "datasets/メッセージが削除されること/expected_message.yml")
  @Transactional
  public void 指定されたidが存在する時メッセージが削除されること() {
    msgMapper.deleteMsg(2);
  }
}



