package raisetech.crudsample.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.crudsample.entity.Message;
import raisetech.crudsample.repository.MsgMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class MsgServiceImplTest {

  @InjectMocks
  MsgServiceImpl msgServiceImpl;

  @Mock
  MsgMapper msgMapper;

//  @Test
//  void findAll() {
//  }

  @Test
  public void 存在するメッセージのidが指定された時にメッセージが返されること() {
    doReturn(Optional.of(new Message(1, "Hello"))).when(msgMapper).findById(1);

    Message actual = msgServiceImpl.findById(1);
    assertThat(actual).isEqualTo(new Message(1, "Hello"));
  }

//  @Test
//  void createMsg() {
//  }
//
//  @Test
//  void updateMsg() {
//  }
//
//  @Test
//  void updateMsg2() {
//  }
//
//  @Test
//  void deleteMsg() {
//  }
}