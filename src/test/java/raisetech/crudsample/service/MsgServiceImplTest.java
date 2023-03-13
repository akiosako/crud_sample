package raisetech.crudsample.service;

import org.apache.tomcat.util.net.TLSClientHelloExtractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.support.ManagedList;
import raisetech.crudsample.controller.ResourceNotFoundException;
import raisetech.crudsample.entity.Message;
import raisetech.crudsample.repository.MsgMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MsgServiceImplTest {

  @InjectMocks
  MsgServiceImpl msgServiceImpl;

  @Mock
  MsgMapper msgMapper;

  @Test
  public void findAllですべてのメッセージが返されること() {
    doReturn(List.of(new Message(1, "Hello"), new Message(2, "Bye"), new Message(3, "Hi"))).when(msgMapper).findAll();

    List<Message> actual = msgServiceImpl.findAll();
    assertThat(actual).isEqualTo(List.of(new Message(1, "Hello"), new Message(2, "Bye"), new Message(3, "Hi")));
    verify(msgMapper).findAll();
  }

  @Test
  public void 存在するメッセージのidが指定された時にメッセージが返されること() {
    doReturn(Optional.of(new Message(1, "Hello"))).when(msgMapper).findById(1);

    Message actual = msgServiceImpl.findById(1);
    assertThat(actual).isEqualTo(new Message(1, "Hello"));
    verify(msgMapper).findById(1);
  }

  @Test
  public void 存在しないidを指定した時ResourceNotFoundExceptionがスローされること() {
    when(msgMapper.findById(999)).thenThrow(ResourceNotFoundException.class);

    assertThrows(ResourceNotFoundException.class, () -> msgServiceImpl.findById(999));
    verify(msgMapper).findById(999);
  }

  @Test
  public void メッセージが登録され自動採番されたidを返すこと() {
    doNothing().when(msgMapper).createMsg(new Message(0, "Hello"));

    int newId = msgServiceImpl.createMsg("Hello");
    assertThat(newId).isEqualTo(new Message(0, "Hello").getId());
  }

  @Test
  public void 指定されたidが存在する時メッセージが更新されること() {
    doReturn(Optional.of(new Message(1, "Hello"))).when(msgMapper).findById(1);
    verify(msgMapper).findById(1);

    doNothing().when(msgMapper).updateMsg(1, "Hello");
    try {
      msgServiceImpl.updateMsg(1, "Hello");
    } catch (ResourceNotFoundException e) {
      fail(e.getMessage());
    }
    verify(msgMapper).updateMsg(1, "Hello");
  }

  @Test
  public void 存在しないidが指定された時例外がスローされること() {
    doReturn(Optional.empty()).when(msgMapper).findById(999);

    assertThrows(ResourceNotFoundException.class, () -> msgServiceImpl.updateMsg(999, "Hello"));
    verify(msgMapper, times(1)).findById(999);
  }

  @Test
  public void 指定されたidが存在するときメッセージが削除されること() {
    doReturn(Optional.of(new Message(1, "Hello"))).when(msgMapper).findById(1);

    doNothing().when(msgMapper).deleteMsg(1);
    try {
      msgServiceImpl.deleteMsg(1);
    } catch (ResourceNotFoundException e) {
      fail(e.getMessage());
    }
    verify(msgMapper, times(1)).findById(1);
    verify(msgMapper).deleteMsg(1);
  }

  @Test
  public void 指定されたidが存在しない時例外がスローされること() {
    doReturn(Optional.empty()).when(msgMapper).findById(999);

    assertThrows(ResourceNotFoundException.class, () -> msgServiceImpl.deleteMsg(999));
    verify(msgMapper, times(1)).findById(999);
  }
}
