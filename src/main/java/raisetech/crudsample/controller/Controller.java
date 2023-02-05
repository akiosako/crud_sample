package raisetech.crudsample.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raisetech.crudsample.entity.Message;
import raisetech.crudsample.form.UpdateForm;
import raisetech.crudsample.service.MsgService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msg")
public class Controller {
  private final MsgService msgService;

  public Controller(MsgService msgService) {
    this.msgService = msgService;
  }

  @GetMapping
  public List<Message> findAll() {
    return msgService.findAll();
  }

  @GetMapping("/{id}")//idが存在しない場合は404を返す
  public Message findById(@PathVariable int id) {
    return msgService.findById(id);
  }

//  @PostMapping //30文字を超える場合はエラーメッセージを返す
//  public ResponseEntity<Map<String, String>> createMsg(@RequestBody MsgForm msgForm, UriComponentsBuilder uriComponentsBuilder) {
//    Message createdMsg = msgService.createMsg(msgForm.getMsg());
//    URI url = uriComponentsBuilder
//            .path("/msg/{id}")
//            .buildAndExpand(createdMsg.getId())
//            .toUri();
//    return ResponseEntity.created(url).body(Map.of(
//            "id",createdMsg.getId(),
//            "created message", ,
//            "message", "message successfully created"));
//  }

  @PatchMapping("/{id}")//idが存在しない場合は404を返す
  public Message updateMsg(@PathVariable int id, @RequestBody UpdateForm updateForm) {
    return msgService.updateMsg(id, updateForm.getMsg());
  }

  @PatchMapping("/sample/{id}")
  public ResponseEntity updateMsg2(@PathVariable int id, @RequestBody UpdateForm updateForm) {
    msgService.updateMsg2(id, updateForm.getMsg());
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")//idが存在しない場合は404を返す
  public ResponseEntity<Map<String, String>> deleteMsg(@PathVariable int id) {
    Message deletedMessage = msgService.findById(id);
    msgService.deleteMsg(id);
    return ResponseEntity.ok(Map.of(
            "id", String.valueOf(id),
            "delete message", deletedMessage.getMsg(),
            "message", "message successfully deleted"));
  }

  @ExceptionHandler(value = ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNoResourceFound(ResourceNotFoundException e,
                                                                   HttpServletRequest request) {
    Map<String, String> body = Map.of(
            "timestamp", ZonedDateTime.now().toString(),
            "Status", String.valueOf(HttpStatus.NOT_FOUND.value()),
            "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
            "message", e.getMessage(),
            "path", request.getRequestURI());
    return new ResponseEntity(body, HttpStatus.NOT_FOUND);
  }
}


