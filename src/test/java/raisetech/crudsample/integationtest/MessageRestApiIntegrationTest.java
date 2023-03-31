package raisetech.crudsample.integationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MessageRestApiIntegrationTest {
  @Autowired
  MockMvc mockMvc;

  @Test
  @DataSet(value = "datasets/it_全てのメッセージが取得できること/message.yml")
  @Transactional
  void 全てのメッセージが取得できること() throws Exception {
    String responce = mockMvc.perform(MockMvcRequestBuilders.get("/msg"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("[" +
            " {" +
            " \"id\": 1," +
            " \"msg\": \"Hello\"" +
            " }," +
            " {" +
            " \"id\": 2," +
            " \"msg\": \"Bye\"" +
            " }," +
            " {" +
            " \"id\": 3," +
            " \"msg\": \"Hi\"" +
            " }" +
            "]", responce, JSONCompareMode.STRICT
    );
  }

  @Test
  @DataSet(value = "datasets/it_メッセージが存在しないとき200が返されること/message.yml")
  @Transactional
  void メッセージが存在しないとき200が返されること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/msg"))
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @DataSet(value = "datasets/it_指定されたidのメッセージが存在するときメッセージが返されること/message.yml")
  @Transactional
  void 指定されたidのメッセージが存在するときメッセージが返されること() throws Exception {
    String responce = mockMvc.perform(MockMvcRequestBuilders.get("/msg/3"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals(" {" +
            " \"id\": 3," +
            " \"msg\": \"Hi\"" +
            " }", responce, JSONCompareMode.STRICT
    );
  }

  @Test
  @DataSet(value = "datasets/it_指定されたidのメッセージが存在しないとき例外がスローされること/message.yml")
  @Transactional
  void it_指定されたidのメッセージが存在しないとき例外がスローされること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/msg/3"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andReturn().getResponse().getErrorMessage();
  }
}
