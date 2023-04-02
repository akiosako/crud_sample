package raisetech.crudsample.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import raisetech.crudsample.entity.Message;
import raisetech.crudsample.form.MsgForm;
import raisetech.crudsample.form.UpdateForm;
import raisetech.crudsample.service.MsgService;

import java.net.URI;
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

  @PostMapping //30文字を超える場合はエラーメッセージを返す
  public ResponseEntity<Map<String, String>> createMsg(@Valid @RequestBody MsgForm msgForm, UriComponentsBuilder uriComponentsBuilder) {
    int newId = this.msgService.createMsg(msgForm.getMsg());
    URI uri = uriComponentsBuilder
            .path("/msg/{id}")
            .buildAndExpand(newId)
            .toUri();
    return ResponseEntity.created(uri).body(Map.of(
            "id", String.valueOf(newId),
            "created message", msgForm.getMsg(),
            "message", "message created successfully"
    ));
  }

  @PatchMapping("/{id}")
  public ResponseEntity updateMsg(@PathVariable int id, @RequestBody UpdateForm updateForm) {
    msgService.updateMsg(id, updateForm.getMsg());
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")//idが存在しない場合は404を返す
  public ResponseEntity deleteMsg(@PathVariable int id) {
    msgService.deleteMsg(id);
    return ResponseEntity.noContent().build();
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


