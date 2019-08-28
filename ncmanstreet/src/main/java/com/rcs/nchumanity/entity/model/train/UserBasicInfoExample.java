package com.rcs.nchumanity.entity.model.train;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserBasicInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserBasicInfoExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(String value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(String value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(String value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(String value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(String value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(String value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLike(String value) {
            addCriterion("gender like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotLike(String value) {
            addCriterion("gender not like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<String> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<String> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(String value1, String value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(String value1, String value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andEmployerIsNull() {
            addCriterion("employer is null");
            return (Criteria) this;
        }

        public Criteria andEmployerIsNotNull() {
            addCriterion("employer is not null");
            return (Criteria) this;
        }

        public Criteria andEmployerEqualTo(String value) {
            addCriterion("employer =", value, "employer");
            return (Criteria) this;
        }

        public Criteria andEmployerNotEqualTo(String value) {
            addCriterion("employer <>", value, "employer");
            return (Criteria) this;
        }

        public Criteria andEmployerGreaterThan(String value) {
            addCriterion("employer >", value, "employer");
            return (Criteria) this;
        }

        public Criteria andEmployerGreaterThanOrEqualTo(String value) {
            addCriterion("employer >=", value, "employer");
            return (Criteria) this;
        }

        public Criteria andEmployerLessThan(String value) {
            addCriterion("employer <", value, "employer");
            return (Criteria) this;
        }

        public Criteria andEmployerLessThanOrEqualTo(String value) {
            addCriterion("employer <=", value, "employer");
            return (Criteria) this;
        }

        public Criteria andEmployerLike(String value) {
            addCriterion("employer like", value, "employer");
            return (Criteria) this;
        }

        public Criteria andEmployerNotLike(String value) {
            addCriterion("employer not like", value, "employer");
            return (Criteria) this;
        }

        public Criteria andEmployerIn(List<String> values) {
            addCriterion("employer in", values, "employer");
            return (Criteria) this;
        }

        public Criteria andEmployerNotIn(List<String> values) {
            addCriterion("employer not in", values, "employer");
            return (Criteria) this;
        }

        public Criteria andEmployerBetween(String value1, String value2) {
            addCriterion("employer between", value1, value2, "employer");
            return (Criteria) this;
        }

        public Criteria andEmployerNotBetween(String value1, String value2) {
            addCriterion("employer not between", value1, value2, "employer");
            return (Criteria) this;
        }

        public Criteria andIdNumberIsNull() {
            addCriterion("id_number is null");
            return (Criteria) this;
        }

        public Criteria andIdNumberIsNotNull() {
            addCriterion("id_number is not null");
            return (Criteria) this;
        }

        public Criteria andIdNumberEqualTo(String value) {
            addCriterion("id_number =", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberNotEqualTo(String value) {
            addCriterion("id_number <>", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberGreaterThan(String value) {
            addCriterion("id_number >", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberGreaterThanOrEqualTo(String value) {
            addCriterion("id_number >=", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberLessThan(String value) {
            addCriterion("id_number <", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberLessThanOrEqualTo(String value) {
            addCriterion("id_number <=", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberLike(String value) {
            addCriterion("id_number like", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberNotLike(String value) {
            addCriterion("id_number not like", value, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberIn(List<String> values) {
            addCriterion("id_number in", values, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberNotIn(List<String> values) {
            addCriterion("id_number not in", values, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberBetween(String value1, String value2) {
            addCriterion("id_number between", value1, value2, "idNumber");
            return (Criteria) this;
        }

        public Criteria andIdNumberNotBetween(String value1, String value2) {
            addCriterion("id_number not between", value1, value2, "idNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNoIsNull() {
            addCriterion("license_no is null");
            return (Criteria) this;
        }

        public Criteria andLicenseNoIsNotNull() {
            addCriterion("license_no is not null");
            return (Criteria) this;
        }

        public Criteria andLicenseNoEqualTo(String value) {
            addCriterion("license_no =", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoNotEqualTo(String value) {
            addCriterion("license_no <>", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoGreaterThan(String value) {
            addCriterion("license_no >", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoGreaterThanOrEqualTo(String value) {
            addCriterion("license_no >=", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoLessThan(String value) {
            addCriterion("license_no <", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoLessThanOrEqualTo(String value) {
            addCriterion("license_no <=", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoLike(String value) {
            addCriterion("license_no like", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoNotLike(String value) {
            addCriterion("license_no not like", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoIn(List<String> values) {
            addCriterion("license_no in", values, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoNotIn(List<String> values) {
            addCriterion("license_no not in", values, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoBetween(String value1, String value2) {
            addCriterion("license_no between", value1, value2, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoNotBetween(String value1, String value2) {
            addCriterion("license_no not between", value1, value2, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlIsNull() {
            addCriterion("photo_url is null");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlIsNotNull() {
            addCriterion("photo_url is not null");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlEqualTo(String value) {
            addCriterion("photo_url =", value, "photoUrl");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlNotEqualTo(String value) {
            addCriterion("photo_url <>", value, "photoUrl");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlGreaterThan(String value) {
            addCriterion("photo_url >", value, "photoUrl");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlGreaterThanOrEqualTo(String value) {
            addCriterion("photo_url >=", value, "photoUrl");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlLessThan(String value) {
            addCriterion("photo_url <", value, "photoUrl");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlLessThanOrEqualTo(String value) {
            addCriterion("photo_url <=", value, "photoUrl");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlLike(String value) {
            addCriterion("photo_url like", value, "photoUrl");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlNotLike(String value) {
            addCriterion("photo_url not like", value, "photoUrl");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlIn(List<String> values) {
            addCriterion("photo_url in", values, "photoUrl");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlNotIn(List<String> values) {
            addCriterion("photo_url not in", values, "photoUrl");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlBetween(String value1, String value2) {
            addCriterion("photo_url between", value1, value2, "photoUrl");
            return (Criteria) this;
        }

        public Criteria andPhotoUrlNotBetween(String value1, String value2) {
            addCriterion("photo_url not between", value1, value2, "photoUrl");
            return (Criteria) this;
        }

        public Criteria andTrainStatusIsNull() {
            addCriterion("train_status is null");
            return (Criteria) this;
        }

        public Criteria andTrainStatusIsNotNull() {
            addCriterion("train_status is not null");
            return (Criteria) this;
        }

        public Criteria andTrainStatusEqualTo(Integer value) {
            addCriterion("train_status =", value, "trainStatus");
            return (Criteria) this;
        }

        public Criteria andTrainStatusNotEqualTo(Integer value) {
            addCriterion("train_status <>", value, "trainStatus");
            return (Criteria) this;
        }

        public Criteria andTrainStatusGreaterThan(Integer value) {
            addCriterion("train_status >", value, "trainStatus");
            return (Criteria) this;
        }

        public Criteria andTrainStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("train_status >=", value, "trainStatus");
            return (Criteria) this;
        }

        public Criteria andTrainStatusLessThan(Integer value) {
            addCriterion("train_status <", value, "trainStatus");
            return (Criteria) this;
        }

        public Criteria andTrainStatusLessThanOrEqualTo(Integer value) {
            addCriterion("train_status <=", value, "trainStatus");
            return (Criteria) this;
        }

        public Criteria andTrainStatusIn(List<Integer> values) {
            addCriterion("train_status in", values, "trainStatus");
            return (Criteria) this;
        }

        public Criteria andTrainStatusNotIn(List<Integer> values) {
            addCriterion("train_status not in", values, "trainStatus");
            return (Criteria) this;
        }

        public Criteria andTrainStatusBetween(Integer value1, Integer value2) {
            addCriterion("train_status between", value1, value2, "trainStatus");
            return (Criteria) this;
        }

        public Criteria andTrainStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("train_status not between", value1, value2, "trainStatus");
            return (Criteria) this;
        }

        public Criteria andOverdueTimeIsNull() {
            addCriterion("overdue_time is null");
            return (Criteria) this;
        }

        public Criteria andOverdueTimeIsNotNull() {
            addCriterion("overdue_time is not null");
            return (Criteria) this;
        }

        public Criteria andOverdueTimeEqualTo(Date value) {
            addCriterion("overdue_time =", value, "overdueTime");
            return (Criteria) this;
        }

        public Criteria andOverdueTimeNotEqualTo(Date value) {
            addCriterion("overdue_time <>", value, "overdueTime");
            return (Criteria) this;
        }

        public Criteria andOverdueTimeGreaterThan(Date value) {
            addCriterion("overdue_time >", value, "overdueTime");
            return (Criteria) this;
        }

        public Criteria andOverdueTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("overdue_time >=", value, "overdueTime");
            return (Criteria) this;
        }

        public Criteria andOverdueTimeLessThan(Date value) {
            addCriterion("overdue_time <", value, "overdueTime");
            return (Criteria) this;
        }

        public Criteria andOverdueTimeLessThanOrEqualTo(Date value) {
            addCriterion("overdue_time <=", value, "overdueTime");
            return (Criteria) this;
        }

        public Criteria andOverdueTimeIn(List<Date> values) {
            addCriterion("overdue_time in", values, "overdueTime");
            return (Criteria) this;
        }

        public Criteria andOverdueTimeNotIn(List<Date> values) {
            addCriterion("overdue_time not in", values, "overdueTime");
            return (Criteria) this;
        }

        public Criteria andOverdueTimeBetween(Date value1, Date value2) {
            addCriterion("overdue_time between", value1, value2, "overdueTime");
            return (Criteria) this;
        }

        public Criteria andOverdueTimeNotBetween(Date value1, Date value2) {
            addCriterion("overdue_time not between", value1, value2, "overdueTime");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationIsNull() {
            addCriterion("degree_of_education is null");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationIsNotNull() {
            addCriterion("degree_of_education is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationEqualTo(String value) {
            addCriterion("degree_of_education =", value, "degreeOfEducation");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationNotEqualTo(String value) {
            addCriterion("degree_of_education <>", value, "degreeOfEducation");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationGreaterThan(String value) {
            addCriterion("degree_of_education >", value, "degreeOfEducation");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationGreaterThanOrEqualTo(String value) {
            addCriterion("degree_of_education >=", value, "degreeOfEducation");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationLessThan(String value) {
            addCriterion("degree_of_education <", value, "degreeOfEducation");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationLessThanOrEqualTo(String value) {
            addCriterion("degree_of_education <=", value, "degreeOfEducation");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationLike(String value) {
            addCriterion("degree_of_education like", value, "degreeOfEducation");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationNotLike(String value) {
            addCriterion("degree_of_education not like", value, "degreeOfEducation");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationIn(List<String> values) {
            addCriterion("degree_of_education in", values, "degreeOfEducation");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationNotIn(List<String> values) {
            addCriterion("degree_of_education not in", values, "degreeOfEducation");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationBetween(String value1, String value2) {
            addCriterion("degree_of_education between", value1, value2, "degreeOfEducation");
            return (Criteria) this;
        }

        public Criteria andDegreeOfEducationNotBetween(String value1, String value2) {
            addCriterion("degree_of_education not between", value1, value2, "degreeOfEducation");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingIsNull() {
            addCriterion("years_of_working is null");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingIsNotNull() {
            addCriterion("years_of_working is not null");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingEqualTo(String value) {
            addCriterion("years_of_working =", value, "yearsOfWorking");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingNotEqualTo(String value) {
            addCriterion("years_of_working <>", value, "yearsOfWorking");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingGreaterThan(String value) {
            addCriterion("years_of_working >", value, "yearsOfWorking");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingGreaterThanOrEqualTo(String value) {
            addCriterion("years_of_working >=", value, "yearsOfWorking");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingLessThan(String value) {
            addCriterion("years_of_working <", value, "yearsOfWorking");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingLessThanOrEqualTo(String value) {
            addCriterion("years_of_working <=", value, "yearsOfWorking");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingLike(String value) {
            addCriterion("years_of_working like", value, "yearsOfWorking");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingNotLike(String value) {
            addCriterion("years_of_working not like", value, "yearsOfWorking");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingIn(List<String> values) {
            addCriterion("years_of_working in", values, "yearsOfWorking");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingNotIn(List<String> values) {
            addCriterion("years_of_working not in", values, "yearsOfWorking");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingBetween(String value1, String value2) {
            addCriterion("years_of_working between", value1, value2, "yearsOfWorking");
            return (Criteria) this;
        }

        public Criteria andYearsOfWorkingNotBetween(String value1, String value2) {
            addCriterion("years_of_working not between", value1, value2, "yearsOfWorking");
            return (Criteria) this;
        }

        public Criteria andSignDateIsNull() {
            addCriterion("sign_date is null");
            return (Criteria) this;
        }

        public Criteria andSignDateIsNotNull() {
            addCriterion("sign_date is not null");
            return (Criteria) this;
        }

        public Criteria andSignDateEqualTo(String value) {
            addCriterion("sign_date =", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateNotEqualTo(String value) {
            addCriterion("sign_date <>", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateGreaterThan(String value) {
            addCriterion("sign_date >", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateGreaterThanOrEqualTo(String value) {
            addCriterion("sign_date >=", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateLessThan(String value) {
            addCriterion("sign_date <", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateLessThanOrEqualTo(String value) {
            addCriterion("sign_date <=", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateLike(String value) {
            addCriterion("sign_date like", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateNotLike(String value) {
            addCriterion("sign_date not like", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateIn(List<String> values) {
            addCriterion("sign_date in", values, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateNotIn(List<String> values) {
            addCriterion("sign_date not in", values, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateBetween(String value1, String value2) {
            addCriterion("sign_date between", value1, value2, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateNotBetween(String value1, String value2) {
            addCriterion("sign_date not between", value1, value2, "signDate");
            return (Criteria) this;
        }

        public Criteria andDutyIsNull() {
            addCriterion("duty is null");
            return (Criteria) this;
        }

        public Criteria andDutyIsNotNull() {
            addCriterion("duty is not null");
            return (Criteria) this;
        }

        public Criteria andDutyEqualTo(String value) {
            addCriterion("duty =", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyNotEqualTo(String value) {
            addCriterion("duty <>", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyGreaterThan(String value) {
            addCriterion("duty >", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyGreaterThanOrEqualTo(String value) {
            addCriterion("duty >=", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyLessThan(String value) {
            addCriterion("duty <", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyLessThanOrEqualTo(String value) {
            addCriterion("duty <=", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyLike(String value) {
            addCriterion("duty like", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyNotLike(String value) {
            addCriterion("duty not like", value, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyIn(List<String> values) {
            addCriterion("duty in", values, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyNotIn(List<String> values) {
            addCriterion("duty not in", values, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyBetween(String value1, String value2) {
            addCriterion("duty between", value1, value2, "duty");
            return (Criteria) this;
        }

        public Criteria andDutyNotBetween(String value1, String value2) {
            addCriterion("duty not between", value1, value2, "duty");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNull() {
            addCriterion("area_id is null");
            return (Criteria) this;
        }

        public Criteria andAreaIdIsNotNull() {
            addCriterion("area_id is not null");
            return (Criteria) this;
        }

        public Criteria andAreaIdEqualTo(Integer value) {
            addCriterion("area_id =", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotEqualTo(Integer value) {
            addCriterion("area_id <>", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThan(Integer value) {
            addCriterion("area_id >", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("area_id >=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThan(Integer value) {
            addCriterion("area_id <", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdLessThanOrEqualTo(Integer value) {
            addCriterion("area_id <=", value, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdIn(List<Integer> values) {
            addCriterion("area_id in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotIn(List<Integer> values) {
            addCriterion("area_id not in", values, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdBetween(Integer value1, Integer value2) {
            addCriterion("area_id between", value1, value2, "areaId");
            return (Criteria) this;
        }

        public Criteria andAreaIdNotBetween(Integer value1, Integer value2) {
            addCriterion("area_id not between", value1, value2, "areaId");
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