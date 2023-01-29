package raisetech.crudsample.service;

import raisetech.crudsample.entity.Message;
import raisetech.crudsample.from.MsgForm;
import raisetech.crudsample.from.UpdateForm;

import java.util.List;

public interface MsgService {
    List<Message> findAll();

    Message findById(int id);

    int createMsg(MsgForm msgForm);

    void updateMsg(UpdateForm updateForm);

    void deleteMsg(int id);
}
