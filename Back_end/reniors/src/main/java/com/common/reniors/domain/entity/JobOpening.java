package com.common.reniors.domain.entity;

import com.common.reniors.domain.entity.Type.LastEdu;
import com.common.reniors.domain.entity.Type.Employment;
import com.common.reniors.domain.entity.Type.IsFinish;
import com.common.reniors.domain.entity.category.Gugun;
import com.common.reniors.domain.entity.category.JobChildCategory;
import com.common.reniors.dto.jobOpening.JobOpeningCreateRequest;
import com.common.reniors.dto.jobOpening.JobOpeningUpdateRequest;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString
public class JobOpening{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_opening_id")
    private Long id;

    @NotNull
//    @CreatedDate
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date createdDate;

    @NotNull
//    @CreatedDate
    @DateTimeFormat(pattern = "YYYY-MM-DD")

    private Date finishedDate;

    @NotNull
    private int numberPeople;

    @NotNull
    private int minCareer;

    @NotNull
    @Column(length = 100)
    private String title;

    @NotNull
    @Column(length = 2000)
    private String contents;

    @Column(length = 100)
    private String contentsImgName;

    @Column(length = 100)
    private String contentsImgPath;

    private int minSalary;

    @Column(length = 100)
    private String jobPosition;

    private int workingDay;
    private IsFinish isFinish;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Employment employment;

    @Enumerated(EnumType.STRING)
    private LastEdu lastEdu;

    //공고 - 회사 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_child_category_id")
    private JobChildCategory jobChildCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gugun_id")
    private Gugun gugun;

    @OneToMany(mappedBy = "jobOpening")
    private List<Apply> applies = new ArrayList<>();

    @OneToMany(mappedBy = "jobOpening")
    private List<Eval> evals = new ArrayList<>();

    @OneToMany(mappedBy = "jobOpening")
    private List<Room> rooms = new ArrayList<>();

    //공고 - 고용형태 연관관계
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "type_employment_id")
//    private TypeEmployment typeEmployment;

    //최종학력, 직무소분류, 구군 받아오기


    public JobOpening(JobOpeningCreateRequest jobOpeningCreateRequest, Company company,Gugun gugun, JobChildCategory jobChildCategory) {
        this.createdDate = jobOpeningCreateRequest.getCreatedDate();
        this.finishedDate = jobOpeningCreateRequest.getFinishedDate();
        this.numberPeople = jobOpeningCreateRequest.getNumberPeople();
        this.minCareer = jobOpeningCreateRequest.getMinCareer();
        this.title = jobOpeningCreateRequest.getTitle();
        this.contents = jobOpeningCreateRequest.getContents();
        this.contentsImgName = jobOpeningCreateRequest.getContentsImgName();
        this.contentsImgPath = jobOpeningCreateRequest.getContentsImgPath();
        this.minSalary = jobOpeningCreateRequest.getMinSalary();
        this.jobPosition = jobOpeningCreateRequest.getJobPosition();
        this.workingDay = jobOpeningCreateRequest.getWorkingDay();
        this.employment = jobOpeningCreateRequest.getEmployment();
        this.lastEdu=jobOpeningCreateRequest.getLastEdu();
        this.isFinish = IsFinish.F;
        this.company = company;
        this.gugun = gugun;
        this.jobChildCategory = jobChildCategory;

    }
    public void update(JobOpeningUpdateRequest jobOpeningUpdateRequest, Gugun gugun, JobChildCategory jobChildCategory){
        this.numberPeople = jobOpeningUpdateRequest.getNumberPeople();
        this.minCareer = jobOpeningUpdateRequest.getMinCareer();
        this.title = jobOpeningUpdateRequest.getTitle();
        this.contents = jobOpeningUpdateRequest.getContents();
        this.contentsImgName = jobOpeningUpdateRequest.getContentsImgName();
        this.contentsImgPath = jobOpeningUpdateRequest.getContentsImgPath();
        this.minSalary = jobOpeningUpdateRequest.getMinSalary();
        this.jobPosition = jobOpeningUpdateRequest.getJobPosition();
        this.workingDay = jobOpeningUpdateRequest.getWorkingDay();
        this.employment = jobOpeningUpdateRequest.getEmployment();
        this.lastEdu=jobOpeningUpdateRequest.getLastEdu();
        this.gugun = gugun;
        this.jobChildCategory = jobChildCategory;
    }

    public void finish(){
        this.isFinish = IsFinish.T;
    }

}
