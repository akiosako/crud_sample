package raisetech.crud_sample.service;

import raisetech.crud_sample.entity.Message;
import raisetech.crud_sample.from.MsgForm;
import raisetech.crud_sample.from.UpdateForm;

import java.util.List;

public interface MsgService {
    List<Message> findAll();

    Message findById(int id);

    int createMsg(MsgForm msgForm);

    void updateMsg(UpdateForm updateForm);

    void deleteMsg(int id);
}
