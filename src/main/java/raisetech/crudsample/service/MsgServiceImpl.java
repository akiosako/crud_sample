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
  public int createMsg(String msg) {
    Message message = new Message();
    message.setMsg(msg);
    this.msgMapper.createMsg(message);
    return message.getId();
  }

  @Override
  public void updateMsg(int id, String msg) {
    this.msgMapper.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found"));

    this.msgMapper.updateMsg(id, msg);
  }

  @Override
  public void deleteMsg(int id) {
    this.msgMapper.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found"));

    this.msgMapper.deleteMsg(id);

  }


}
