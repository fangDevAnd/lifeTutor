package com.rcs.nchumanity.entity.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OfflineExamRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OfflineExamRecordExample() {
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

        public Criteria andExamIdIsNull() {
            addCriterion("exam_id is null");
            return (Criteria) this;
        }

        public Criteria andExamIdIsNotNull() {
            addCriterion("exam_id is not null");
            return (Criteria) this;
        }

        public Criteria andExamIdEqualTo(Integer value) {
            addCriterion("exam_id =", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotEqualTo(Integer value) {
            addCriterion("exam_id <>", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdGreaterThan(Integer value) {
            addCriterion("exam_id >", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("exam_id >=", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdLessThan(Integer value) {
            addCriterion("exam_id <", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdLessThanOrEqualTo(Integer value) {
            addCriterion("exam_id <=", value, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdIn(List<Integer> values) {
            addCriterion("exam_id in", values, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotIn(List<Integer> values) {
            addCriterion("exam_id not in", values, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdBetween(Integer value1, Integer value2) {
            addCriterion("exam_id between", value1, value2, "examId");
            return (Criteria) this;
        }

        public Criteria andExamIdNotBetween(Integer value1, Integer value2) {
            addCriterion("exam_id not between", value1, value2, "examId");
            return (Criteria) this;
        }

        public Criteria andBindNoIsNull() {
            addCriterion("bind_no is null");
            return (Criteria) this;
        }

        public Criteria andBindNoIsNotNull() {
            addCriterion("bind_no is not null");
            return (Criteria) this;
        }

        public Criteria andBindNoEqualTo(String value) {
            addCriterion("bind_no =", value, "bindNo");
            return (Criteria) this;
        }

        public Criteria andBindNoNotEqualTo(String value) {
            addCriterion("bind_no <>", value, "bindNo");
            return (Criteria) this;
        }

        public Criteria andBindNoGreaterThan(String value) {
            addCriterion("bind_no >", value, "bindNo");
            return (Criteria) this;
        }

        public Criteria andBindNoGreaterThanOrEqualTo(String value) {
            addCriterion("bind_no >=", value, "bindNo");
            return (Criteria) this;
        }

        public Criteria andBindNoLessThan(String value) {
            addCriterion("bind_no <", value, "bindNo");
            return (Criteria) this;
        }

        public Criteria andBindNoLessThanOrEqualTo(String value) {
            addCriterion("bind_no <=", value, "bindNo");
            return (Criteria) this;
        }

        public Criteria andBindNoLike(String value) {
            addCriterion("bind_no like", value, "bindNo");
            return (Criteria) this;
        }

        public Criteria andBindNoNotLike(String value) {
            addCriterion("bind_no not like", value, "bindNo");
            return (Criteria) this;
        }

        public Criteria andBindNoIn(List<String> values) {
            addCriterion("bind_no in", values, "bindNo");
            return (Criteria) this;
        }

        public Criteria andBindNoNotIn(List<String> values) {
            addCriterion("bind_no not in", values, "bindNo");
            return (Criteria) this;
        }

        public Criteria andBindNoBetween(String value1, String value2) {
            addCriterion("bind_no between", value1, value2, "bindNo");
            return (Criteria) this;
        }

        public Criteria andBindNoNotBetween(String value1, String value2) {
            addCriterion("bind_no not between", value1, value2, "bindNo");
            return (Criteria) this;
        }

        public Criteria andExamTimeIsNull() {
            addCriterion("exam_time is null");
            return (Criteria) this;
        }

        public Criteria andExamTimeIsNotNull() {
            addCriterion("exam_time is not null");
            return (Criteria) this;
        }

        public Criteria andExamTimeEqualTo(Date value) {
            addCriterion("exam_time =", value, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeNotEqualTo(Date value) {
            addCriterion("exam_time <>", value, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeGreaterThan(Date value) {
            addCriterion("exam_time >", value, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("exam_time >=", value, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeLessThan(Date value) {
            addCriterion("exam_time <", value, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeLessThanOrEqualTo(Date value) {
            addCriterion("exam_time <=", value, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeIn(List<Date> values) {
            addCriterion("exam_time in", values, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeNotIn(List<Date> values) {
            addCriterion("exam_time not in", values, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeBetween(Date value1, Date value2) {
            addCriterion("exam_time between", value1, value2, "examTime");
            return (Criteria) this;
        }

        public Criteria andExamTimeNotBetween(Date value1, Date value2) {
            addCriterion("exam_time not between", value1, value2, "examTime");
            return (Criteria) this;
        }

        public Criteria andVrdataIdIsNull() {
            addCriterion("vrdata_id is null");
            return (Criteria) this;
        }

        public Criteria andVrdataIdIsNotNull() {
            addCriterion("vrdata_id is not null");
            return (Criteria) this;
        }

        public Criteria andVrdataIdEqualTo(String value) {
            addCriterion("vrdata_id =", value, "vrdataId");
            return (Criteria) this;
        }

        public Criteria andVrdataIdNotEqualTo(String value) {
            addCriterion("vrdata_id <>", value, "vrdataId");
            return (Criteria) this;
        }

        public Criteria andVrdataIdGreaterThan(String value) {
            addCriterion("vrdata_id >", value, "vrdataId");
            return (Criteria) this;
        }

        public Criteria andVrdataIdGreaterThanOrEqualTo(String value) {
            addCriterion("vrdata_id >=", value, "vrdataId");
            return (Criteria) this;
        }

        public Criteria andVrdataIdLessThan(String value) {
            addCriterion("vrdata_id <", value, "vrdataId");
            return (Criteria) this;
        }

        public Criteria andVrdataIdLessThanOrEqualTo(String value) {
            addCriterion("vrdata_id <=", value, "vrdataId");
            return (Criteria) this;
        }

        public Criteria andVrdataIdLike(String value) {
            addCriterion("vrdata_id like", value, "vrdataId");
            return (Criteria) this;
        }

        public Criteria andVrdataIdNotLike(String value) {
            addCriterion("vrdata_id not like", value, "vrdataId");
            return (Criteria) this;
        }

        public Criteria andVrdataIdIn(List<String> values) {
            addCriterion("vrdata_id in", values, "vrdataId");
            return (Criteria) this;
        }

        public Criteria andVrdataIdNotIn(List<String> values) {
            addCriterion("vrdata_id not in", values, "vrdataId");
            return (Criteria) this;
        }

        public Criteria andVrdataIdBetween(String value1, String value2) {
            addCriterion("vrdata_id between", value1, value2, "vrdataId");
            return (Criteria) this;
        }

        public Criteria andVrdataIdNotBetween(String value1, String value2) {
            addCriterion("vrdata_id not between", value1, value2, "vrdataId");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(Integer value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Integer value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Integer value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Integer value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Integer value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Integer> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Integer> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Integer value1, Integer value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andPassRateIsNull() {
            addCriterion("pass_rate is null");
            return (Criteria) this;
        }

        public Criteria andPassRateIsNotNull() {
            addCriterion("pass_rate is not null");
            return (Criteria) this;
        }

        public Criteria andPassRateEqualTo(Float value) {
            addCriterion("pass_rate =", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateNotEqualTo(Float value) {
            addCriterion("pass_rate <>", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateGreaterThan(Float value) {
            addCriterion("pass_rate >", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateGreaterThanOrEqualTo(Float value) {
            addCriterion("pass_rate >=", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateLessThan(Float value) {
            addCriterion("pass_rate <", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateLessThanOrEqualTo(Float value) {
            addCriterion("pass_rate <=", value, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateIn(List<Float> values) {
            addCriterion("pass_rate in", values, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateNotIn(List<Float> values) {
            addCriterion("pass_rate not in", values, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateBetween(Float value1, Float value2) {
            addCriterion("pass_rate between", value1, value2, "passRate");
            return (Criteria) this;
        }

        public Criteria andPassRateNotBetween(Float value1, Float value2) {
            addCriterion("pass_rate not between", value1, value2, "passRate");
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