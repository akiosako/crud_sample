package raisetech.crudsample.service;

import org.springframework.stereotype.Service;
import raisetech.crudsample.controller.ResourceNotFoundException;
import raisetech.crudsample.entity.Message;
import raisetech.crudsample.repository.MsgMapper;

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
  public Message createMsg(String msg) {
    return msgMapper.createMsg(msg);
  }

  @Override
  public void updateMsg(int id, String msg) {
    msgMapper.updateMsg(id, msg);
  }

  @Override
  public void deleteMsg(int id) {
    msgMapper.deleteMsg(id);

  }


}
