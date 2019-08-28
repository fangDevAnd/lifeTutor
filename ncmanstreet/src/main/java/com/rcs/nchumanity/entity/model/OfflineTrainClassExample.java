package com.rcs.nchumanity.entity.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OfflineTrainClassExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OfflineTrainClassExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andClassIdIsNull() {
            addCriterion("class_id is null");
            return (Criteria) this;
        }

        public Criteria andClassIdIsNotNull() {
            addCriterion("class_id is not null");
            return (Criteria) this;
        }

        public Criteria andClassIdEqualTo(Integer value) {
            addCriterion("class_id =", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotEqualTo(Integer value) {
            addCriterion("class_id <>", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThan(Integer value) {
            addCriterion("class_id >", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("class_id >=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThan(Integer value) {
            addCriterion("class_id <", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdLessThanOrEqualTo(Integer value) {
            addCriterion("class_id <=", value, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdIn(List<Integer> values) {
            addCriterion("class_id in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotIn(List<Integer> values) {
            addCriterion("class_id not in", values, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdBetween(Integer value1, Integer value2) {
            addCriterion("class_id between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andClassIdNotBetween(Integer value1, Integer value2) {
            addCriterion("class_id not between", value1, value2, "classId");
            return (Criteria) this;
        }

        public Criteria andCourseNoIsNull() {
            addCriterion("course_no is null");
            return (Criteria) this;
        }

        public Criteria andCourseNoIsNotNull() {
            addCriterion("course_no is not null");
            return (Criteria) this;
        }

        public Criteria andCourseNoEqualTo(Integer value) {
            addCriterion("course_no =", value, "courseNo");
            return (Criteria) this;
        }

        public Criteria andCourseNoNotEqualTo(Integer value) {
            addCriterion("course_no <>", value, "courseNo");
            return (Criteria) this;
        }

        public Criteria andCourseNoGreaterThan(Integer value) {
            addCriterion("course_no >", value, "courseNo");
            return (Criteria) this;
        }

        public Criteria andCourseNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("course_no >=", value, "courseNo");
            return (Criteria) this;
        }

        public Criteria andCourseNoLessThan(Integer value) {
            addCriterion("course_no <", value, "courseNo");
            return (Criteria) this;
        }

        public Criteria andCourseNoLessThanOrEqualTo(Integer value) {
            addCriterion("course_no <=", value, "courseNo");
            return (Criteria) this;
        }

        public Criteria andCourseNoIn(List<Integer> values) {
            addCriterion("course_no in", values, "courseNo");
            return (Criteria) this;
        }

        public Criteria andCourseNoNotIn(List<Integer> values) {
            addCriterion("course_no not in", values, "courseNo");
            return (Criteria) this;
        }

        public Criteria andCourseNoBetween(Integer value1, Integer value2) {
            addCriterion("course_no between", value1, value2, "courseNo");
            return (Criteria) this;
        }

        public Criteria andCourseNoNotBetween(Integer value1, Integer value2) {
            addCriterion("course_no not between", value1, value2, "courseNo");
            return (Criteria) this;
        }

        public Criteria andClassNameIsNull() {
            addCriterion("class_name is null");
            return (Criteria) this;
        }

        public Criteria andClassNameIsNotNull() {
            addCriterion("class_name is not null");
            return (Criteria) this;
        }

        public Criteria andClassNameEqualTo(String value) {
            addCriterion("class_name =", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotEqualTo(String value) {
            addCriterion("class_name <>", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameGreaterThan(String value) {
            addCriterion("class_name >", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameGreaterThanOrEqualTo(String value) {
            addCriterion("class_name >=", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLessThan(String value) {
            addCriterion("class_name <", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLessThanOrEqualTo(String value) {
            addCriterion("class_name <=", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLike(String value) {
            addCriterion("class_name like", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotLike(String value) {
            addCriterion("class_name not like", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameIn(List<String> values) {
            addCriterion("class_name in", values, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotIn(List<String> values) {
            addCriterion("class_name not in", values, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameBetween(String value1, String value2) {
            addCriterion("class_name between", value1, value2, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotBetween(String value1, String value2) {
            addCriterion("class_name not between", value1, value2, "className");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andTrainerIdIsNull() {
            addCriterion("trainer_id is null");
            return (Criteria) this;
        }

        public Criteria andTrainerIdIsNotNull() {
            addCriterion("trainer_id is not null");
            return (Criteria) this;
        }

        public Criteria andTrainerIdEqualTo(Integer value) {
            addCriterion("trainer_id =", value, "trainerId");
            return (Criteria) this;
        }

        public Criteria andTrainerIdNotEqualTo(Integer value) {
            addCriterion("trainer_id <>", value, "trainerId");
            return (Criteria) this;
        }

        public Criteria andTrainerIdGreaterThan(Integer value) {
            addCriterion("trainer_id >", value, "trainerId");
            return (Criteria) this;
        }

        public Criteria andTrainerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("trainer_id >=", value, "trainerId");
            return (Criteria) this;
        }

        public Criteria andTrainerIdLessThan(Integer value) {
            addCriterion("trainer_id <", value, "trainerId");
            return (Criteria) this;
        }

        public Criteria andTrainerIdLessThanOrEqualTo(Integer value) {
            addCriterion("trainer_id <=", value, "trainerId");
            return (Criteria) this;
        }

        public Criteria andTrainerIdIn(List<Integer> values) {
            addCriterion("trainer_id in", values, "trainerId");
            return (Criteria) this;
        }

        public Criteria andTrainerIdNotIn(List<Integer> values) {
            addCriterion("trainer_id not in", values, "trainerId");
            return (Criteria) this;
        }

        public Criteria andTrainerIdBetween(Integer value1, Integer value2) {
            addCriterion("trainer_id between", value1, value2, "trainerId");
            return (Criteria) this;
        }

        public Criteria andTrainerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("trainer_id not between", value1, value2, "trainerId");
            return (Criteria) this;
        }

        public Criteria andIntroIsNull() {
            addCriterion("intro is null");
            return (Criteria) this;
        }

        public Criteria andIntroIsNotNull() {
            addCriterion("intro is not null");
            return (Criteria) this;
        }

        public Criteria andIntroEqualTo(String value) {
            addCriterion("intro =", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroNotEqualTo(String value) {
            addCriterion("intro <>", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroGreaterThan(String value) {
            addCriterion("intro >", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroGreaterThanOrEqualTo(String value) {
            addCriterion("intro >=", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroLessThan(String value) {
            addCriterion("intro <", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroLessThanOrEqualTo(String value) {
            addCriterion("intro <=", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroLike(String value) {
            addCriterion("intro like", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroNotLike(String value) {
            addCriterion("intro not like", value, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroIn(List<String> values) {
            addCriterion("intro in", values, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroNotIn(List<String> values) {
            addCriterion("intro not in", values, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroBetween(String value1, String value2) {
            addCriterion("intro between", value1, value2, "intro");
            return (Criteria) this;
        }

        public Criteria andIntroNotBetween(String value1, String value2) {
            addCriterion("intro not between", value1, value2, "intro");
            return (Criteria) this;
        }

        public Criteria andImgUrlIsNull() {
            addCriterion("img_url is null");
            return (Criteria) this;
        }

        public Criteria andImgUrlIsNotNull() {
            addCriterion("img_url is not null");
            return (Criteria) this;
        }

        public Criteria andImgUrlEqualTo(String value) {
            addCriterion("img_url =", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotEqualTo(String value) {
            addCriterion("img_url <>", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlGreaterThan(String value) {
            addCriterion("img_url >", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("img_url >=", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLessThan(String value) {
            addCriterion("img_url <", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLessThanOrEqualTo(String value) {
            addCriterion("img_url <=", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlLike(String value) {
            addCriterion("img_url like", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotLike(String value) {
            addCriterion("img_url not like", value, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlIn(List<String> values) {
            addCriterion("img_url in", values, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotIn(List<String> values) {
            addCriterion("img_url not in", values, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlBetween(String value1, String value2) {
            addCriterion("img_url between", value1, value2, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andImgUrlNotBetween(String value1, String value2) {
            addCriterion("img_url not between", value1, value2, "imgUrl");
            return (Criteria) this;
        }

        public Criteria andPointIdIsNull() {
            addCriterion("point_id is null");
            return (Criteria) this;
        }

        public Criteria andPointIdIsNotNull() {
            addCriterion("point_id is not null");
            return (Criteria) this;
        }

        public Criteria andPointIdEqualTo(Integer value) {
            addCriterion("point_id =", value, "pointId");
            return (Criteria) this;
        }

        public Criteria andPointIdNotEqualTo(Integer value) {
            addCriterion("point_id <>", value, "pointId");
            return (Criteria) this;
        }

        public Criteria andPointIdGreaterThan(Integer value) {
            addCriterion("point_id >", value, "pointId");
            return (Criteria) this;
        }

        public Criteria andPointIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("point_id >=", value, "pointId");
            return (Criteria) this;
        }

        public Criteria andPointIdLessThan(Integer value) {
            addCriterion("point_id <", value, "pointId");
            return (Criteria) this;
        }

        public Criteria andPointIdLessThanOrEqualTo(Integer value) {
            addCriterion("point_id <=", value, "pointId");
            return (Criteria) this;
        }

        public Criteria andPointIdIn(List<Integer> values) {
            addCriterion("point_id in", values, "pointId");
            return (Criteria) this;
        }

        public Criteria andPointIdNotIn(List<Integer> values) {
            addCriterion("point_id not in", values, "pointId");
            return (Criteria) this;
        }

        public Criteria andPointIdBetween(Integer value1, Integer value2) {
            addCriterion("point_id between", value1, value2, "pointId");
            return (Criteria) this;
        }

        public Criteria andPointIdNotBetween(Integer value1, Integer value2) {
            addCriterion("point_id not between", value1, value2, "pointId");
            return (Criteria) this;
        }

        public Criteria andIsVrIsNull() {
            addCriterion("is_vr is null");
            return (Criteria) this;
        }

        public Criteria andIsVrIsNotNull() {
            addCriterion("is_vr is not null");
            return (Criteria) this;
        }

        public Criteria andIsVrEqualTo(Boolean value) {
            addCriterion("is_vr =", value, "isVr");
            return (Criteria) this;
        }

        public Criteria andIsVrNotEqualTo(Boolean value) {
            addCriterion("is_vr <>", value, "isVr");
            return (Criteria) this;
        }

        public Criteria andIsVrGreaterThan(Boolean value) {
            addCriterion("is_vr >", value, "isVr");
            return (Criteria) this;
        }

        public Criteria andIsVrGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_vr >=", value, "isVr");
            return (Criteria) this;
        }

        public Criteria andIsVrLessThan(Boolean value) {
            addCriterion("is_vr <", value, "isVr");
            return (Criteria) this;
        }

        public Criteria andIsVrLessThanOrEqualTo(Boolean value) {
            addCriterion("is_vr <=", value, "isVr");
            return (Criteria) this;
        }

        public Criteria andIsVrIn(List<Boolean> values) {
            addCriterion("is_vr in", values, "isVr");
            return (Criteria) this;
        }

        public Criteria andIsVrNotIn(List<Boolean> values) {
            addCriterion("is_vr not in", values, "isVr");
            return (Criteria) this;
        }

        public Criteria andIsVrBetween(Boolean value1, Boolean value2) {
            addCriterion("is_vr between", value1, value2, "isVr");
            return (Criteria) this;
        }

        public Criteria andIsVrNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_vr not between", value1, value2, "isVr");
            return (Criteria) this;
        }

        public Criteria andCurrentNumIsNull() {
            addCriterion("current_num is null");
            return (Criteria) this;
        }

        public Criteria andCurrentNumIsNotNull() {
            addCriterion("current_num is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentNumEqualTo(Integer value) {
            addCriterion("current_num =", value, "currentNum");
            return (Criteria) this;
        }

        public Criteria andCurrentNumNotEqualTo(Integer value) {
            addCriterion("current_num <>", value, "currentNum");
            return (Criteria) this;
        }

        public Criteria andCurrentNumGreaterThan(Integer value) {
            addCriterion("current_num >", value, "currentNum");
            return (Criteria) this;
        }

        public Criteria andCurrentNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_num >=", value, "currentNum");
            return (Criteria) this;
        }

        public Criteria andCurrentNumLessThan(Integer value) {
            addCriterion("current_num <", value, "currentNum");
            return (Criteria) this;
        }

        public Criteria andCurrentNumLessThanOrEqualTo(Integer value) {
            addCriterion("current_num <=", value, "currentNum");
            return (Criteria) this;
        }

        public Criteria andCurrentNumIn(List<Integer> values) {
            addCriterion("current_num in", values, "currentNum");
            return (Criteria) this;
        }

        public Criteria andCurrentNumNotIn(List<Integer> values) {
            addCriterion("current_num not in", values, "currentNum");
            return (Criteria) this;
        }

        public Criteria andCurrentNumBetween(Integer value1, Integer value2) {
            addCriterion("current_num between", value1, value2, "currentNum");
            return (Criteria) this;
        }

        public Criteria andCurrentNumNotBetween(Integer value1, Integer value2) {
            addCriterion("current_num not between", value1, value2, "currentNum");
            return (Criteria) this;
        }

        public Criteria andMaxNumIsNull() {
            addCriterion("max_num is null");
            return (Criteria) this;
        }

        public Criteria andMaxNumIsNotNull() {
            addCriterion("max_num is not null");
            return (Criteria) this;
        }

        public Criteria andMaxNumEqualTo(Integer value) {
            addCriterion("max_num =", value, "maxNum");
            return (Criteria) this;
        }

        public Criteria andMaxNumNotEqualTo(Integer value) {
            addCriterion("max_num <>", value, "maxNum");
            return (Criteria) this;
        }

        public Criteria andMaxNumGreaterThan(Integer value) {
            addCriterion("max_num >", value, "maxNum");
            return (Criteria) this;
        }

        public Criteria andMaxNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_num >=", value, "maxNum");
            return (Criteria) this;
        }

        public Criteria andMaxNumLessThan(Integer value) {
            addCriterion("max_num <", value, "maxNum");
            return (Criteria) this;
        }

        public Criteria andMaxNumLessThanOrEqualTo(Integer value) {
            addCriterion("max_num <=", value, "maxNum");
            return (Criteria) this;
        }

        public Criteria andMaxNumIn(List<Integer> values) {
            addCriterion("max_num in", values, "maxNum");
            return (Criteria) this;
        }

        public Criteria andMaxNumNotIn(List<Integer> values) {
            addCriterion("max_num not in", values, "maxNum");
            return (Criteria) this;
        }

        public Criteria andMaxNumBetween(Integer value1, Integer value2) {
            addCriterion("max_num between", value1, value2, "maxNum");
            return (Criteria) this;
        }

        public Criteria andMaxNumNotBetween(Integer value1, Integer value2) {
            addCriterion("max_num not between", value1, value2, "maxNum");
            return (Criteria) this;
        }

        public Criteria andSpecialTextIsNull() {
            addCriterion("special_text is null");
            return (Criteria) this;
        }

        public Criteria andSpecialTextIsNotNull() {
            addCriterion("special_text is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialTextEqualTo(String value) {
            addCriterion("special_text =", value, "specialText");
            return (Criteria) this;
        }

        public Criteria andSpecialTextNotEqualTo(String value) {
            addCriterion("special_text <>", value, "specialText");
            return (Criteria) this;
        }

        public Criteria andSpecialTextGreaterThan(String value) {
            addCriterion("special_text >", value, "specialText");
            return (Criteria) this;
        }

        public Criteria andSpecialTextGreaterThanOrEqualTo(String value) {
            addCriterion("special_text >=", value, "specialText");
            return (Criteria) this;
        }

        public Criteria andSpecialTextLessThan(String value) {
            addCriterion("special_text <", value, "specialText");
            return (Criteria) this;
        }

        public Criteria andSpecialTextLessThanOrEqualTo(String value) {
            addCriterion("special_text <=", value, "specialText");
            return (Criteria) this;
        }

        public Criteria andSpecialTextLike(String value) {
            addCriterion("special_text like", value, "specialText");
            return (Criteria) this;
        }

        public Criteria andSpecialTextNotLike(String value) {
            addCriterion("special_text not like", value, "specialText");
            return (Criteria) this;
        }

        public Criteria andSpecialTextIn(List<String> values) {
            addCriterion("special_text in", values, "specialText");
            return (Criteria) this;
        }

        public Criteria andSpecialTextNotIn(List<String> values) {
            addCriterion("special_text not in", values, "specialText");
            return (Criteria) this;
        }

        public Criteria andSpecialTextBetween(String value1, String value2) {
            addCriterion("special_text between", value1, value2, "specialText");
            return (Criteria) this;
        }

        public Criteria andSpecialTextNotBetween(String value1, String value2) {
            addCriterion("special_text not between", value1, value2, "specialText");
            return (Criteria) this;
        }

        public Criteria andBackupTextIsNull() {
            addCriterion("backup_text is null");
            return (Criteria) this;
        }

        public Criteria andBackupTextIsNotNull() {
            addCriterion("backup_text is not null");
            return (Criteria) this;
        }

        public Criteria andBackupTextEqualTo(String value) {
            addCriterion("backup_text =", value, "backupText");
            return (Criteria) this;
        }

        public Criteria andBackupTextNotEqualTo(String value) {
            addCriterion("backup_text <>", value, "backupText");
            return (Criteria) this;
        }

        public Criteria andBackupTextGreaterThan(String value) {
            addCriterion("backup_text >", value, "backupText");
            return (Criteria) this;
        }

        public Criteria andBackupTextGreaterThanOrEqualTo(String value) {
            addCriterion("backup_text >=", value, "backupText");
            return (Criteria) this;
        }

        public Criteria andBackupTextLessThan(String value) {
            addCriterion("backup_text <", value, "backupText");
            return (Criteria) this;
        }

        public Criteria andBackupTextLessThanOrEqualTo(String value) {
            addCriterion("backup_text <=", value, "backupText");
            return (Criteria) this;
        }

        public Criteria andBackupTextLike(String value) {
            addCriterion("backup_text like", value, "backupText");
            return (Criteria) this;
        }

        public Criteria andBackupTextNotLike(String value) {
            addCriterion("backup_text not like", value, "backupText");
            return (Criteria) this;
        }

        public Criteria andBackupTextIn(List<String> values) {
            addCriterion("backup_text in", values, "backupText");
            return (Criteria) this;
        }

        public Criteria andBackupTextNotIn(List<String> values) {
            addCriterion("backup_text not in", values, "backupText");
            return (Criteria) this;
        }

        public Criteria andBackupTextBetween(String value1, String value2) {
            addCriterion("backup_text between", value1, value2, "backupText");
            return (Criteria) this;
        }

        public Criteria andBackupTextNotBetween(String value1, String value2) {
            addCriterion("backup_text not between", value1, value2, "backupText");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(Boolean value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(Boolean value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(Boolean value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(Boolean value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(Boolean value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<Boolean> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<Boolean> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}