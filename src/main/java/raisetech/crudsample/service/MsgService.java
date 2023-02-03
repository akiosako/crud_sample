package raisetech.crudsample.service;

import raisetech.crudsample.entity.Message;

import java.util.List;

public interface MsgService {
  List<Message> findAll();

  Message findById(int id);

  Message createMsg(String msg);

  Message updateMsg(int id, String msg);

  void deleteMsg(int id);
}
