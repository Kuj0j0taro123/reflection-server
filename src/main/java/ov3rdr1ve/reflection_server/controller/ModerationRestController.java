package ov3rdr1ve.reflection_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ov3rdr1ve.reflection_server.dto.actions.BanUserRequest;
import ov3rdr1ve.reflection_server.dto.actions.ReportCommentRequest;
import ov3rdr1ve.reflection_server.dto.actions.ReportPostRequest;
import ov3rdr1ve.reflection_server.dto.actions.ReportUserRequest;
import ov3rdr1ve.reflection_server.dto.report.ReportedCommentDTO;
import ov3rdr1ve.reflection_server.dto.report.ReportedPostDTO;
import ov3rdr1ve.reflection_server.dto.report.ReportedUserDTO;
import ov3rdr1ve.reflection_server.service.ModeratorService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ModerationRestController {

    private final ModeratorService moderatorService;

    public ModerationRestController(ModeratorService moderatorService){
        this.moderatorService = moderatorService;
    }

    @GetMapping("/moderator/posts")
    public List<ReportedPostDTO> getAllReportedPosts(){
        return moderatorService.getAllReportedPosts();
    }

    @PostMapping("/report/post")
    public ResponseEntity<?> reportPost(@RequestBody ReportPostRequest req){
        //todo
        return new ResponseEntity<>(moderatorService.reportPost(req), HttpStatus.OK);
    }

    @GetMapping("/moderator/users")
    public List<ReportedUserDTO> getAllReportedUsers(){
        return moderatorService.getAllReportedUsers();
    }

    @PostMapping("/report/user")
    public ResponseEntity<?> reportUser(@RequestBody ReportUserRequest req){
        return new ResponseEntity<>(moderatorService.reportUser(req), HttpStatus.OK);
    }

    @GetMapping("/moderator/comments")
    public List<ReportedCommentDTO> getAllReportedComments(){
        return moderatorService.getAllReportedComments();
    }

    @PostMapping("/report/comment")
    public ResponseEntity<?> reportComment(@RequestBody ReportCommentRequest req){
        return new ResponseEntity<>(moderatorService.reportComment(req), HttpStatus.OK);
    }

    @PostMapping("/moderator/ban")
    public ResponseEntity<?> banUser(@RequestBody BanUserRequest req){
        return new ResponseEntity<>(moderatorService.banUser(req), HttpStatus.OK);
    }

    @PostMapping("/moderator/unban")
    public ResponseEntity<?> unbanUser(@RequestBody BanUserRequest req){
        return new ResponseEntity<>(moderatorService.unbanUser(req), HttpStatus.OK);
    }

}
