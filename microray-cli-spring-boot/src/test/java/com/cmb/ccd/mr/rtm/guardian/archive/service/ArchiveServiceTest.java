/*
 * @Company: China Merchants Bank
 * @Copyright: Copyright 2015 China Merchants Bank. All rights reserved.
 */
package com.cmb.ccd.mr.rtm.guardian.archive.service;

import com.cmb.ccd.mr.rtm.guardian.archive.dao.ArchiveDetailRepository;
import com.cmb.ccd.mr.rtm.guardian.archive.dao.ArchiveRepository;
import com.cmb.ccd.mr.rtm.guardian.archive.entity.Archive;
import com.cmb.ccd.mr.rtm.guardian.archive.entity.ArchiveStatus;
import com.cmb.ccd.mr.rtm.guardian.archive.form.ReviewForm;
import com.cmb.ccd.mr.rtm.guardian.user.entity.User;
import com.cmb.ccd.mr.rtm.guardian.workflow.dao.WorkflowRepository;
import com.cmb.ccd.mr.rtm.guardian.workflow.entity.Workflow;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("junit")
public class ArchiveServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ArchiveService archiveService;
    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    private ArchiveDetailRepository archiveDetailRepository;
    @Autowired
    private WorkflowRepository workflowRepository;

    @Test
    public void testLoadArchive() throws URISyntaxException, SQLException {
        String path = new File(this.getClass().getResource("").toURI()).getAbsolutePath();
        int size = archiveService.loadArchive(path + "/../sample", User.system());
        Assert.assertEquals(1, size);
        long size1 = archiveDetailRepository.count();
        Assert.assertEquals(20, size1);
    }

    @Test
    public void projectManagerReview() {
        Archive archive = new Archive("pub_f_crd_trx_real_20171013", User.system());
        archive.setSize(100L);
        archiveRepository.save(archive);
        ReviewForm reviewForm = new ReviewForm();
        reviewForm.setAccept(true);
        reviewForm.setArchiveId(archive.getId());
        reviewForm.setOpUserId("674441");
        reviewForm.setOpUserName("张盛雄");
        reviewForm.setRemark("remark");
        reviewForm.setReviewerId("674441");
        reviewForm.setReviewerName("张盛雄");
        archiveService.projectManagerReview(reviewForm);
        List<Workflow> workflowList = workflowRepository.findByArchiveId(archive.getId());
        Assert.assertEquals(Workflow.OpType.审核, workflowList.get(0).getOpType());
        Assert.assertEquals(ArchiveStatus.待放行, archive.getStatus());
    }

    @Test
    public void superiorReview() {
        Archive archive = new Archive("pub_f_crd_trx_real_20171013", User.system());
        archive.setSize(100L);
        archiveRepository.save(archive);
        ReviewForm reviewForm = new ReviewForm();
        reviewForm.setAccept(true);
        reviewForm.setArchiveId(archive.getId());
        reviewForm.setOpUserId("674441");
        reviewForm.setOpUserName("张盛雄");
        reviewForm.setRemark("remark");
        archiveService.superiorReview(reviewForm);
        List<Workflow> workflowList = workflowRepository.findByArchiveId(archive.getId());
        Assert.assertEquals(Workflow.OpType.放行, workflowList.get(0).getOpType());
        Assert.assertEquals(ArchiveStatus.放行通过, archive.getStatus());
    }

}
