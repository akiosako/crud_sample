package raisetech.crudsample.repository;

import org.apache.ibatis.annotations.*;
import raisetech.crudsample.entity.Message;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MsgMapper {
  @Select("select * from message")
  List<Message> findAll();

  @Select("select * from message where id = #{id}")
  Optional<Message> findById(int id);

  @Insert("insert into message(msg) values(#{msg})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  Message createMsg(String msg);

  @Update("update message set msg = #{msg} where id = #{id}")
  void updateMsg(int id, String msg);

  @Delete("delete from message where id = #{id}")
  void deleteMsg(int id);

}

