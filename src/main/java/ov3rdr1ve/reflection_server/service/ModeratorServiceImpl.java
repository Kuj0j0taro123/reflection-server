package ov3rdr1ve.reflection_server.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ov3rdr1ve.reflection_server.dto.actions.ReportPostRequest;
import ov3rdr1ve.reflection_server.dto.report.ReportedPostDTO;
import ov3rdr1ve.reflection_server.entity.Post;
import ov3rdr1ve.reflection_server.entity.User;
import ov3rdr1ve.reflection_server.entity.report.ReportedPost;
import ov3rdr1ve.reflection_server.repository.PostRepository;
import ov3rdr1ve.reflection_server.repository.ReportedPostRepository;
import ov3rdr1ve.reflection_server.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModeratorServiceImpl implements ModeratorService {

    private final ReportedPostRepository reportedPostRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public ModeratorServiceImpl(ReportedPostRepository reportedPostRepository,
                                UserRepository userRepository,
                                PostRepository postRepository) {
        this.reportedPostRepository = reportedPostRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
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
}
