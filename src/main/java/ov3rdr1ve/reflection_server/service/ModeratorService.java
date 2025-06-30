package ov3rdr1ve.reflection_server.service;

import ov3rdr1ve.reflection_server.dto.actions.BanUserRequest;
import ov3rdr1ve.reflection_server.dto.actions.ReportCommentRequest;
import ov3rdr1ve.reflection_server.dto.actions.ReportPostRequest;
import ov3rdr1ve.reflection_server.dto.actions.ReportUserRequest;
import ov3rdr1ve.reflection_server.dto.report.ReportedCommentDTO;
import ov3rdr1ve.reflection_server.dto.report.ReportedPostDTO;
import ov3rdr1ve.reflection_server.dto.report.ReportedUserDTO;
import ov3rdr1ve.reflection_server.dto.user.UserDTO;
import ov3rdr1ve.reflection_server.entity.report.ReportedComment;
import ov3rdr1ve.reflection_server.entity.report.ReportedPost;
import ov3rdr1ve.reflection_server.entity.report.ReportedUser;

import java.util.List;

public interface ModeratorService {
    public ReportedPostDTO convertRepPostToDto(ReportedPost reportedPost);
    public ReportedPost convertRepPostToEntity(ReportedPostDTO reportedPostDTO);
    public ReportedUserDTO convertRepUserToDto(ReportedUser reportedUser);
    public ReportedUser convertRepUserToEntity(ReportedUserDTO reportedUserDTO);
    public ReportedCommentDTO convertRepCommentToDto(ReportedComment reportedComment);
    public ReportedComment convertRepCommentToEntity(ReportedCommentDTO reportedCommentDTO);

    public List<ReportedPostDTO> getAllReportedPosts();
    public ReportedPostDTO reportPost(ReportPostRequest request);
    
    public List<ReportedUserDTO> getAllReportedUsers();
    public ReportedUserDTO reportUser(ReportUserRequest request);

    public List<ReportedCommentDTO> getAllReportedComments();
    public ReportedCommentDTO reportComment(ReportCommentRequest request);

    public boolean banUser(BanUserRequest request);


}
