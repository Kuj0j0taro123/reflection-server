package ov3rdr1ve.reflection_server.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ov3rdr1ve.reflection_server.dto.actions.BanUserRequest;
import ov3rdr1ve.reflection_server.dto.actions.ReportCommentRequest;
import ov3rdr1ve.reflection_server.dto.actions.ReportPostRequest;
import ov3rdr1ve.reflection_server.dto.actions.ReportUserRequest;
import ov3rdr1ve.reflection_server.dto.report.ReportedCommentDTO;
import ov3rdr1ve.reflection_server.dto.report.ReportedPostDTO;
import ov3rdr1ve.reflection_server.dto.report.ReportedUserDTO;
import ov3rdr1ve.reflection_server.dto.user.UserDTO;
import ov3rdr1ve.reflection_server.entity.Comment;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.entity.report.ReportedComment;
import ov3rdr1ve.reflection_server.entity.report.ReportedPost;
import ov3rdr1ve.reflection_server.entity.report.ReportedUser;
import ov3rdr1ve.reflection_server.repository.*;
import ov3rdr1ve.reflection_server.security.HttpSessionTracker;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModeratorServiceImpl implements ModeratorService {

    private final HttpSessionTracker sessionTracker;
    private final ReportedPostRepository reportedPostRepository;
    private final PostRepository postRepository;
    private final ReportedUserRepository reportedUserRepository;
    private final UserRepository userRepository;
    private final ReportedCommentRepository reportedCommentRepository;
    private final CommentRepository commentRepository;


    public ModeratorServiceImpl(ReportedPostRepository reportedPostRepository,
                                PostRepository postRepository,
                                ReportedUserRepository reportedUserRepository,
                                UserRepository userRepository,
                                ReportedCommentRepository reportedCommentRepository,
                                CommentRepository commentRepository,
                                HttpSessionTracker sessionTracker
                                ) {
        this.reportedPostRepository = reportedPostRepository;
        this.postRepository = postRepository;
        this.reportedUserRepository = reportedUserRepository;
        this.userRepository = userRepository;
        this.reportedCommentRepository = reportedCommentRepository;
        this.commentRepository = commentRepository;
        this.sessionTracker = sessionTracker;
    }

    @Override
    public ReportedPostDTO convertRepPostToDto(ReportedPost reportedPost) {
        ReportedPostDTO reportedPostDTO = new ReportedPostDTO();
        reportedPostDTO.setId(reportedPost.getId());
        reportedPostDTO.setSubmitterId(reportedPost.getSubmitter().getId());
        reportedPostDTO.setPostId(reportedPost.getReportedPost().getId());
        reportedPostDTO.setReason(reportedPost.getReason());
        reportedPostDTO.setReportedOn(reportedPost.getCreatedOn());
        return reportedPostDTO;
    }

    @Override
    public ReportedPost convertRepPostToEntity(ReportedPostDTO reportedPostDTO) {
        return reportedPostRepository.findById(reportedPostDTO.getId()).orElseThrow();
    }

    @Override
    public ReportedUserDTO convertRepUserToDto(ReportedUser reportedUser) {
        ReportedUserDTO reportedUserDTO = new ReportedUserDTO();
        reportedUserDTO.setId(reportedUser.getId());
        reportedUserDTO.setSubmitterId(reportedUser.getSubmitter().getId());
        reportedUserDTO.setUserId(reportedUser.getReportedUser().getId());
        reportedUserDTO.setReason(reportedUser.getReason());
        reportedUserDTO.setReportedOn(reportedUser.getCreatedOn());
        return reportedUserDTO;
    }

    @Override
    public ReportedUser convertRepUserToEntity(ReportedUserDTO reportedUserDTO) {
        return reportedUserRepository.findById(reportedUserDTO.getId()).orElseThrow();
    }

    @Override
    public ReportedCommentDTO convertRepCommentToDto(ReportedComment reportedComment) {
        ReportedCommentDTO reportedCommentDTO = new ReportedCommentDTO();
        reportedCommentDTO.setId(reportedComment.getId());
        reportedCommentDTO.setSubmitterId(reportedComment.getSubmitter().getId());
        reportedCommentDTO.setCommentId(reportedComment.getReportedComment().getId());
        reportedCommentDTO.setReason(reportedComment.getReason());
        reportedCommentDTO.setReportedOn(reportedComment.getCreatedOn());
        return reportedCommentDTO;
    }

    @Override
    public ReportedComment convertRepCommentToEntity(ReportedCommentDTO reportedCommentDTO) {
        return reportedCommentRepository.findById(reportedCommentDTO.getId()).orElseThrow();
    }

    @Override
    public List<ReportedPostDTO> getAllReportedPosts() {
        List<ReportedPost> results = reportedPostRepository.findAll();

        ArrayList<ReportedPostDTO> reportedPosts = new ArrayList<>();

        for (ReportedPost result : results)
            reportedPosts.add(convertRepPostToDto(result));

        return reportedPosts;
    }

    @Override
    @Transactional
    public ReportedPostDTO reportPost(ReportPostRequest request) {
        User submitter = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        Post post = postRepository.findById(request.getPostId()).orElseThrow();

        ReportedPost report = new ReportedPost();
        report.setReportedPost(post);
        report.setSubmitter(submitter);
        report.setReason(request.getReason()); //todo: make sure reason isn't empty
        report = reportedPostRepository.save(report);
        return convertRepPostToDto(report);
    }

    @Override
    public List<ReportedUserDTO> getAllReportedUsers() {
        List<ReportedUser> results = reportedUserRepository.findAll();

        ArrayList<ReportedUserDTO> reportedUsers = new ArrayList<>();

        for (ReportedUser result : results){
            reportedUsers.add(convertRepUserToDto(result));
        }

        return reportedUsers;
    }

    @Override
    @Transactional
    public ReportedUserDTO reportUser(ReportUserRequest request) {
        User submitter = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        User userGettingReported = userRepository.findById(request.getUserId()).orElseThrow();

        ReportedUser report = new ReportedUser();
        report.setReportedUser(userGettingReported);
        report.setSubmitter(submitter);
        report.setReason(request.getReason());
        report = reportedUserRepository.save(report);
        return convertRepUserToDto(report);
    }

    @Override
    public List<ReportedCommentDTO> getAllReportedComments() {
        List<ReportedComment> results = reportedCommentRepository.findAll();

        ArrayList<ReportedCommentDTO> reportedComments = new ArrayList<>();

        for (ReportedComment result : results){
            reportedComments.add(convertRepCommentToDto(result));
        }
        return reportedComments;
    }

    @Override
    public ReportedCommentDTO reportComment(ReportCommentRequest request) {
        User submitter = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
        Comment commentGettingReported = commentRepository.findById(request.getCommentId()).orElseThrow();

        ReportedComment report = new ReportedComment();
        report.setReportedComment(commentGettingReported);
        report.setSubmitter(submitter);
        report.setReason(request.getReason());
        report = reportedCommentRepository.save(report);
        return convertRepCommentToDto(report);
    }

    @Override
    @Transactional
    public boolean banUser(BanUserRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        // cant ban a mod
        if (user.getRoles().contains("MODERATOR"))
            return false;

        user.setBanned(true);

        userRepository.save(user);

        // 2) immediately kill all their sessions
        for (HttpSession session : sessionTracker.getSessions(user.getUsername())) {
            try {
                session.invalidate();
            } catch (IllegalStateException ignored) {
                // session may already be invalidated
            }
        }

        return true;
    }

}
