package raisetech.crud_sample.repository;

import org.apache.ibatis.annotations.*;
import raisetech.crud_sample.entity.Message;
import raisetech.crud_sample.from.MsgForm;
import raisetech.crud_sample.from.UpdateForm;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MsgMapper{
    @Select("select * from message")
    List<Message> findAll();

    @Select("select * from message where id = #{id}")
    Optional<Message> findById(int id);

    @Insert("insert into message(msg) values(#{msg})")
    @Options(useGeneratedKeys = true,keyProperty = "id", keyColumn = "id")
    void createMsg(MsgForm msgForm);

    @Update("update message set msg = #{msg} where id = #{id}")
    void updateMsg(UpdateForm updateForm);

    @Delete("delete from message where id = #{id}")
    void deleteMsg(int id);

}

