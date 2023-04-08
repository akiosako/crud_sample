package raisetech.crudsample.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

    JSONAssert.assertEquals("""
            [
                {
                    "id": 1,
                    "msg": "Hello"
                },
                {
                    "id": 2,
                    "msg": "Bye"
                },
                {
                    "id": 3,
                    "msg": "Hi"
                }
            ]
            """, responce, JSONCompareMode.STRICT
    );
  }

  @Test
  @DataSet(value = "datasets/it_メッセージが存在しないとき空の配列と200が返されること/message.yml")
  @Transactional
  void メッセージが存在しないとき空の空の配列と200が返されること() throws Exception {
    String responce = mockMvc.perform(MockMvcRequestBuilders.get("/msg"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
            []
            """, responce, JSONCompareMode.STRICT);
  }

  @Test
  @DataSet(value = "datasets/it_指定されたidのメッセージが存在するときメッセージが返されること/message.yml")
  @Transactional
  void 指定されたidのメッセージが存在するときメッセージが返されること() throws Exception {
    String responce = mockMvc.perform(MockMvcRequestBuilders.get("/msg/{id}", 3))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
            {
                    "id": 3,
                    "msg": "Hi"
                }
            """, responce, JSONCompareMode.STRICT
    );
  }

  @Test
  @DataSet(value = "datasets/it_指定されたidのメッセージが存在しないとき例外がスローされること/message.yml")
  @Transactional
  void it_指定されたidのメッセージが存在しないとき例外がスローされること() throws Exception {
    String responce = mockMvc.perform(MockMvcRequestBuilders.get("/msg/{id}", 999))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
                              {
                                  "error": "Not Found",
                                  "Status": "404",
                                  "path": "/msg/999",
                                  "message": "resource not found",
                                  "timestamp": "2023-04-02T10:50:29.604145900+09:00[Asia/Tokyo]"
                              }
                    """, responce,
            new CustomComparator(JSONCompareMode.LENIENT,
                    new Customization("timestamp", (o1, o2) -> true)));
  }

  @Test
  @DataSet(value = "datasets/it_メッセージが登録されること/set_message.yml")
  @ExpectedDataSet(value = "datasets/it_メッセージが登録されること/expected_message.yml", ignoreCols = "id")
  @Transactional
  void it_メッセージが登録されること() throws Exception {
    String result = mockMvc.perform(MockMvcRequestBuilders.post("/msg")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("""
                            {
                                    "id": 1,
                                    "msg": "Hello"
                                }
                            """))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
                    {
                        "created message": "Hello",
                        "id": "1",
                        "message": "message created successfully"
                    }
                    """, result,
            new CustomComparator(JSONCompareMode.LENIENT,
                    new Customization("id", (o1, o2) -> true)));
  }

  @Test
  @Transactional
  void it_21文字以上のメッセージがリクエストされたとき登録せずエラーメッセージが返されること() throws Exception {
    String response = mockMvc.perform(MockMvcRequestBuilders.post("/msg")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("""
                            {
                                "msg":"111111111111111111111111111111111111111"
                            }
                            """))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
                    {
                        "message": "21文字以上のメッセージ、空文字、nullは許容されません。",
                        "timestamp": "2023-04-07T16:35:09.423044300+09:00[Asia/Tokyo]",
                        "error": "Bad Request",
                        "Status": "400",
                        "path": "/msg"
                    }
                    """, response,
            new CustomComparator(JSONCompareMode.LENIENT,
                    new Customization("timestamp", (o1, o2) -> true)));
  }

  @Test
  @Transactional
  void it_空文字のメッセージがリクエストされたとき登録せずエラーメッセージが返されること() throws Exception {
    String response = mockMvc.perform(MockMvcRequestBuilders.post("/msg")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("""
                            {
                                "msg":""
                            }
                            """))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
                    {
                        "message": "21文字以上のメッセージ、空文字、nullは許容されません。",
                        "timestamp": "2023-04-07T16:35:09.423044300+09:00[Asia/Tokyo]",
                        "error": "Bad Request",
                        "Status": "400",
                        "path": "/msg"
                    }
                    """, response,
            new CustomComparator(JSONCompareMode.LENIENT,
                    new Customization("timestamp", (o1, o2) -> true)));
  }

  @Test
  @Transactional
  void it_nullがリクエストされたとき登録せずエラーメッセージが返されること() throws Exception {
    String response = mockMvc.perform(MockMvcRequestBuilders.post("/msg")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("""
                            {
                                "msg":null
                            }
                            """))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
                    {
                        "message": "21文字以上のメッセージ、空文字、nullは許容されません。",
                        "timestamp": "2023-04-07T16:35:09.423044300+09:00[Asia/Tokyo]",
                        "error": "Bad Request",
                        "Status": "400",
                        "path": "/msg"
                    }
                    """, response,
            new CustomComparator(JSONCompareMode.LENIENT,
                    new Customization("timestamp", (o1, o2) -> true)));
  }

  @Test
  @DataSet(value = "datasets/it_指定されたidのメッセージが存在するときメッセージが更新されること/message.yml")
  @Transactional
  void it_指定されたidのメッセージが存在するときメッセージが更新されること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/msg/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("""
                            {
                                "msg":"Bye"
                            }
                            """))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  @DataSet(value = "datasets/it_指定されたidのメッセージが存在しないとき例外がスローされること/message.yml")
  @Transactional
  void it_指定されたidのメッセージが存在しないときメッセージを更新せず例外がスローされること() throws Exception {
    String responce = mockMvc.perform(MockMvcRequestBuilders.patch("/msg/{id}", 999)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("""
                            {
                                "msg":"Bye"
                            }
                            """))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
                              {
                                  "error": "Not Found",
                                  "Status": "404",
                                  "path": "/msg/999",
                                  "message": "resource not found",
                                  "timestamp": "2023-04-02T10:50:29.604145900+09:00[Asia/Tokyo]"
                              }
                    """, responce,
            new CustomComparator(JSONCompareMode.LENIENT,
                    new Customization("timestamp", (o1, o2) -> true)));
  }

  @Test
  @Transactional
  void it_21文字以上のメッセージがリクエストされたとき更新せずエラーメッセージが返されること() throws Exception {
    String response = mockMvc.perform(MockMvcRequestBuilders.patch("/msg/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("""
                            {
                                "msg":"111111111111111111111111111111111111111"
                            }
                            """))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
                                {
                                    "error": "Bad Request",
                                    "timestamp": "2023-04-07T20:00:54.970235+09:00[Asia/Tokyo]",
                                    "message": "21文字以上のメッセージ、空文字、nullは許容されません。",
                                    "path": "/msg/1",
                                    "Status": "400"
                                }
                    """, response,
            new CustomComparator(JSONCompareMode.LENIENT,
                    new Customization("timestamp", ((o1, o2) -> true))));
  }

  @Test
  @Transactional
  void nullがリクエストされたとき更新せずエラーメッセージが返されること() throws Exception {
    String response = mockMvc.perform(MockMvcRequestBuilders.patch("/msg/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("""
                            {
                                "msg":null
                            }
                            """))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
                                {
                                    "error": "Bad Request",
                                    "timestamp": "2023-04-07T20:00:54.970235+09:00[Asia/Tokyo]",
                                    "message": "21文字以上のメッセージ、空文字、nullは許容されません。",
                                    "path": "/msg/1",
                                    "Status": "400"
                                }
                    """, response,
            new CustomComparator(JSONCompareMode.LENIENT,
                    new Customization("timestamp", ((o1, o2) -> true))));
  }

  @Test
  @Transactional
  void it_空文字がリクエストされたとき更新せずエラーメッセージが返されること() throws Exception {
    String response = mockMvc.perform(MockMvcRequestBuilders.patch("/msg/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("""
                            {
                                "msg":""
                            }
                            """))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
                                {
                                    "error": "Bad Request",
                                    "timestamp": "2023-04-07T20:00:54.970235+09:00[Asia/Tokyo]",
                                    "message": "21文字以上のメッセージ、空文字、nullは許容されません。",
                                    "path": "/msg/1",
                                    "Status": "400"
                                }
                    """, response,
            new CustomComparator(JSONCompareMode.LENIENT,
                    new Customization("timestamp", ((o1, o2) -> true))));
  }


  @Test
  @DataSet("datasets/it_指定されたidのメッセージが存在するとき削除されること/message.yml")
  @Transactional
  void it_指定されたidのメッセージが存在するとき削除されること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/msg/{id}", 1))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  @DataSet(value = "datasets/it_指定されたidのメッセージが存在しないとき例外がスローされること/message.yml")
  @Transactional
  void it_指定されたidのメッセージが存在しないとき削除せず例外がスローされること() throws Exception {
    String responce = mockMvc.perform(MockMvcRequestBuilders.delete("/msg/{id}", 999))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    JSONAssert.assertEquals("""
                              {
                                  "error": "Not Found",
                                  "Status": "404",
                                  "path": "/msg/999",
                                  "message": "resource not found",
                                  "timestamp": "2023-04-02T10:50:29.604145900+09:00[Asia/Tokyo]"
                              }
                    """, responce,
            new CustomComparator(JSONCompareMode.LENIENT,
                    new Customization("timestamp", (o1, o2) -> true)));
  }
}
