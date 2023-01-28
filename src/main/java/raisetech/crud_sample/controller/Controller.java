package raisetech.crud_sample.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import raisetech.crud_sample.entity.Message;
import raisetech.crud_sample.from.MsgForm;
import raisetech.crud_sample.from.UpdateForm;
import raisetech.crud_sample.service.MsgService;

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
    public ResponseEntity<Map<String, String>> createMsg(@RequestBody @Valid MsgForm msgForm, UriComponentsBuilder uriComponentsBuilder) {
        String newId = String.valueOf(msgService.createMsg(msgForm));
        URI url = uriComponentsBuilder.fromUriString("http://localhost:8080")
                .path("/msg/{id}")
                .buildAndExpand(newId)
                .toUri();
        return ResponseEntity.created(url).body(Map.of("message", "message successfully created"));
    }

    @PatchMapping("/{id}")//idが存在しない場合は404を返す
    public ResponseEntity<String> updateMsg(@PathVariable int id, @RequestBody UpdateForm updateForm) {
        msgService.findById(id);
        msgService.updateMsg(updateForm);
        return ResponseEntity.ok("message successfully updated");
    }

    @DeleteMapping("/{id}")//idが存在しない場合は404を返す
    public ResponseEntity<String> deleteMsg(@PathVariable int id) {
        msgService.findById(id);
        msgService.deleteMsg(id);
        return ResponseEntity.ok("message successfully deleted");
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


