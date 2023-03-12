package raisetech.crudsample.service;

import raisetech.crudsample.entity.Message;

import java.util.List;

public interface MsgService {
  List<Message> findAll();

  Message findById(int id);

  int createMsg(String msg);

  void updateMsg(int id, String msg);

  void deleteMsg(int id);
}

