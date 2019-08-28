package com.rcs.nchumanity.entity.complexModel;

import com.rcs.nchumanity.entity.model.EmergencyInfo;
import com.rcs.nchumanity.entity.model.OfflineExamRecord;
import com.rcs.nchumanity.entity.model.OfflineTrainClass;
import com.rcs.nchumanity.entity.model.OnlineCourseInfo;
import com.rcs.nchumanity.entity.model.OnlineCourseInfoExample;
import com.rcs.nchumanity.entity.model.OnlineExamQuestion;
import com.rcs.nchumanity.entity.model.SpecificInfo;
import com.rcs.nchumanity.entity.model.SpecificInfoClassification;
import com.rcs.nchumanity.entity.model.SpecificPicture;
import com.rcs.nchumanity.entity.model.TrainPointInfo;
import com.rcs.nchumanity.entity.model.UserOfflineExamInfo;
import com.rcs.nchumanity.entity.model.UserOfflineStudyRecord;
import com.rcs.nchumanity.entity.model.UserOnlineStudyRecord;
import com.rcs.nchumanity.entity.model.sys.TrainOrgInfo;
import com.rcs.nchumanity.entity.model.train.CourseClassification;
import com.rcs.nchumanity.entity.modelInter.SpecificInfoWithLocation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 这个表存放的是复合的数据的模型数据
 * 用来实现当一个表无法实现数据展示的情况下，进行的多表相连
 */
public class ComplexModelSet {

    /**
     * 关联的是 特定信息记录表和 特定信息分类表 specific_info_classification
     * 由于关系是一对多
     */
    public static class M_speinf_speinfCla {

        public String title;

        public List<SpecificInfo> specificInfos;

        public M_speinf_speinfCla(String title, List<SpecificInfo> specificInfos) {
            this.title = title;
            this.specificInfos = specificInfos;
        }
    }


    /**
     * 关联的是 特定信息记录(带有位置信息的model)和 特定信息分类表 specific_info_classification
     * * 由于关系是一对多
     */
    public static class M_speinf_speinfClaLoca {
        public SpecificInfoClassification specificInfoClassification;
        public List<SpecificInfoWithLocation> infoWithLocations;

        public M_speinf_speinfClaLoca(SpecificInfoClassification specificInfoClassification, List<SpecificInfoWithLocation> infoWithLocations) {
            this.specificInfoClassification = specificInfoClassification;
            this.infoWithLocations = infoWithLocations;
        }
    }


    /**
     * 关联的是 在线课程分类信息 和 在线课程内容信息表
     * curse_classification 和  online_course_classification
     * 关系为
     */
    public static class M_onlCour_courCla {
        public CourseClassification classification;
        public List<OnlineCourseInfo> onlineCourseInfos;

        public M_onlCour_courCla(CourseClassification classification, List<OnlineCourseInfo> onlineCourseInfos) {
            this.classification = classification;
            this.onlineCourseInfos = onlineCourseInfos;
        }
    }


    /**
     * 课程映射多条学习记录
     */
    public static class M_couClas_usOnStuRec {

//        public CourseClassification classification;
//
//        public List<UserOnlineStudyRecord> records;
//
//        public M_couClas_usOnStuRec(CourseClassification classification, List<UserOnlineStudyRecord> records) {
//            this.classification = classification;
//            this.records = records;
//        }


        public int courseNo;

        public String courseName;
        public boolean isStudied;

        public M_couClas_usOnStuRec(int courseNo, String courseName, boolean isStudied) {
            this.courseNo = courseNo;
            this.courseName = courseName;
            this.isStudied = isStudied;
        }
    }


    /**
     * 首页数据的信息封装
     * <p>
     * 包括了3-5个banner列表 存放在@{{@link com.rcs.nchumanity.entity.model.SpecificPicture}}表中
     * 包括了特定信息分类 查询的是父类为<span>培训相关</span>和<span>捐款捐献</span>  存放在@{{@link SpecificInfoClassification}}表中
     * 包括了 特定信息分类，以及信息分类对应的特定信息记录表（1--->多） 查询的是<span>认识红十字</span>和<span>资讯</span>的各3条数据
     * <p>
     * 更多的参数设置，请参考
     */
    public static class M_IndexPageInfo {

        /**
         * 存放的是Banner的img的包装
         */
        public List<SpecificPicture> banners;
        /**
         * 存放的是主页的各个分类的数据
         * 类中的remark属性存放的是图片的url
         * <p>
         * 需要的元素字段 1.typeId，title ,remark
         */
        public List<SpecificInfoClassification> modules;

        /**
         * 存放的是特定信息分类，以及分类的相关 特定信息记录 加载所有数据
         */
        public List<M_speinf_speinfCla> news;
    }


    /**
     * 在线视频课程以及对应的试题的相关的内容
     */
    public static class M__speinf_speinfCla_onLiExamQues {
//        public M_speinf_speinfCla m_speinf_speinfCla;
//        public List<OnlineExamQuestion> examQuestions;

        public int courseNo;

        public String title;

        public String videoUrl;

        public String imgUrl;

        public String writing;

        public String remark;

        public M__speinf_speinfCla_onLiExamQues(int courseNo, String title, String videoUrl, String imgUrl, String writing, String remark, List<Question> questionList) {
            this.courseNo = courseNo;
            this.title = title;
            this.videoUrl = videoUrl;
            this.imgUrl = imgUrl;
            this.writing = writing;
            this.remark = remark;
            this.questionList = questionList;
        }

        public M__speinf_speinfCla_onLiExamQues() {
        }

        public List<Question> questionList;
    }

    public static class Question {
        public String question;
        public String options;
        public String answer;
        public int id;

        public Question(String question, String options, String answer) {
            this.question = question;
            this.options = options;
            this.answer = answer;
        }

        public Question(String question, String options, String answer, int id) {
            this.question = question;
            this.options = options;
            this.answer = answer;
            this.id = id;
        }
    }


//    /**
//     * 培训点----->1:1 ------>地区信息
//     * 培训点及其地区信息
//     */
//    public static class M_traPoi_areaInf {
//
//        public TrainPointInfo pointInfo;
//
//        public AreaInfo areaInfo;
//
//    }


    /**
     * 线下培训班----->1:1----->培训点及其地区信息
     * <p>
     * 培训班对应的培训点以及对应的地区信息
     */
//    public static class M_offliTraClas_M_traPoi_areaInf {
//
//        public OfflineTrainClass trainClass;
//
//        public M_traPoi_areaInf traPoi_areaInf;
//
//    }


    /**
     * 培训地点对应的培训机构的信息
     */
    public static class M_traPoi_traOrgInfo {

        public TrainPointInfo pointInfo;

        public TrainOrgInfo trainOrgInfo;
    }


    /**
     * 线下培训班 对应的培训地点对应的地区信息以及 培训地点对应的培训机构的信息
     */
    public static class M_offliTraClas_M_traPoi_areaInf_M_traPoi_traOrgInfo {

        public OfflineTrainClass trainClass;

//        public M_traPoi_areaInf traPoi_areaInf;

        public M_traPoi_traOrgInfo traPoi_traOrgInfo;


        /**
         *    OfflineTrainClass 线下培训班
         *
         *  UserOfflineExamInfo 用户线下考核信息表
         *
         *
         *  emergencyInfo 急救求救
         *
         */


    }


    public static class M_traiCla_areaInf {

        public int classId;

        public String starTime;
        public String position;

        public String maxNum;

        public String leftNum;

        public String org;

        public int vrAttr;

        public boolean vrClass;

        public String area;

        public String trainer;

        public M_traiCla_areaInf(int classId, String starTime, String position, String maxNum, String leftNum, String org, int vrAttr, boolean vrClass, String area) {
            this.classId = classId;
            this.starTime = starTime;
            this.position = position;
            this.maxNum = maxNum;
            this.leftNum = leftNum;
            this.org = org;
            this.vrAttr = vrAttr;
            this.vrClass = vrClass;
            this.area = area;
        }

        public M_traiCla_areaInf() {
        }
    }

    /**
     * 课程分类详情
     */
    public static class ClassDetail {

        public int classId;
        public String className;
        public Date startTime;
        public Date endTime;
        public String trainer;
        public String intro;
        public String imgUrl;
        public String position;
        public String vrAttr;
        public boolean vrClass;
        public int currentNum;
        public int maxNum;
        public String org;

        public ClassDetail(int classId, String className, Date startTime, Date endTime, String trainer, String intro, String imgUrl, String position, String vrAttr, boolean vrClass, int currentNum, int maxNum, String org) {
            this.classId = classId;
            this.className = className;
            this.startTime = startTime;
            this.endTime = endTime;
            this.org = org;
            this.trainer = trainer;
            this.intro = intro;
            this.imgUrl = imgUrl;
            this.position = position;
            this.vrAttr = vrAttr;
            this.vrClass = vrClass;
            this.currentNum = currentNum;
            this.maxNum = maxNum;
        }

        public ClassDetail() {
        }
    }

    public static class PostInfo implements Serializable {

        public String name;
        public String mobilephone;
        public String address;
        public String expressNo;
        public String expressCompany;
        public String expressStatus;  //0 代表没有发出 1，代表发出

        public PostInfo(String name, String mobilephone, String address, String expressNo, String expressCompany, String expressStatus) {
            this.name = name;
            this.mobilephone = mobilephone;
            this.address = address;
            this.expressNo = expressNo;
            this.expressCompany = expressCompany;
            this.expressStatus = expressStatus;
        }

        public PostInfo() {
        }
    }
}
