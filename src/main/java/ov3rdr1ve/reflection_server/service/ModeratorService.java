package ov3rdr1ve.reflection_server.service;

import ov3rdr1ve.reflection_server.dto.actions.ReportPostRequest;
import ov3rdr1ve.reflection_server.dto.report.ReportedPostDTO;
import ov3rdr1ve.reflection_server.entity.report.ReportedPost;

import java.util.List;

public interface ModeratorService {
    public ReportedPostDTO convertRepPostToDto(ReportedPost reportedPost);
    public ReportedPost convertRepPostToEntity(ReportedPostDTO reportedPostDTO);

    public List<ReportedPostDTO> getAllReportedPosts();
    public ReportedPostDTO reportPost(ReportPostRequest request);

}
