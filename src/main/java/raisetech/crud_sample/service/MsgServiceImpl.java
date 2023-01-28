package raisetech.crud_sample.service;

import org.springframework.stereotype.Service;
import raisetech.crud_sample.controller.ResourceNotFoundException;
import raisetech.crud_sample.entity.Message;
import raisetech.crud_sample.from.MsgForm;
import raisetech.crud_sample.from.UpdateForm;
import raisetech.crud_sample.repository.MsgMapper;

import java.util.List;

@Service
public class MsgServiceImpl implements MsgService {
    private final MsgMapper msgMapper;

    public MsgServiceImpl(MsgMapper msgMapper) {
        this.msgMapper = msgMapper;
    }

    @Override
    public List<Message> findAll() {
        return msgMapper.findAll();
    }

    @Override
    public Message findById(int id) {
        return this.msgMapper.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }

    @Override
    public int createMsg(MsgForm msgForm) {
        msgMapper.createMsg(msgForm);
        return msgForm.getId();
    }

    @Override
    public void updateMsg(UpdateForm updateForm) {
        msgMapper.updateMsg(updateForm);

    }

    @Override
    public void deleteMsg(int id) {
        msgMapper.deleteMsg(id);
    }


}
