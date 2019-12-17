package com.gotway.gotway.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("account is null");
            return (Criteria) this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("account is not null");
            return (Criteria) this;
        }

        public Criteria andAccountEqualTo(String value) {
            addCriterion("account =", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotEqualTo(String value) {
            addCriterion("account <>", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThan(String value) {
            addCriterion("account >", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(String value) {
            addCriterion("account >=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThan(String value) {
            addCriterion("account <", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLessThanOrEqualTo(String value) {
            addCriterion("account <=", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountLike(String value) {
            addCriterion("account like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotLike(String value) {
            addCriterion("account not like", value, "account");
            return (Criteria) this;
        }

        public Criteria andAccountIn(List<String> values) {
            addCriterion("account in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotIn(List<String> values) {
            addCriterion("account not in", values, "account");
            return (Criteria) this;
        }

        public Criteria andAccountBetween(String value1, String value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andAccountNotBetween(String value1, String value2) {
            addCriterion("account not between", value1, value2, "account");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNull() {
            addCriterion("nickname is null");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNotNull() {
            addCriterion("nickname is not null");
            return (Criteria) this;
        }

        public Criteria andNicknameEqualTo(String value) {
            addCriterion("nickname =", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotEqualTo(String value) {
            addCriterion("nickname <>", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThan(String value) {
            addCriterion("nickname >", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("nickname >=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThan(String value) {
            addCriterion("nickname <", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThanOrEqualTo(String value) {
            addCriterion("nickname <=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLike(String value) {
            addCriterion("nickname like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotLike(String value) {
            addCriterion("nickname not like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameIn(List<String> values) {
            addCriterion("nickname in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotIn(List<String> values) {
            addCriterion("nickname not in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameBetween(String value1, String value2) {
            addCriterion("nickname between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotBetween(String value1, String value2) {
            addCriterion("nickname not between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andRealNameIsNull() {
            addCriterion("real_name is null");
            return (Criteria) this;
        }

        public Criteria andRealNameIsNotNull() {
            addCriterion("real_name is not null");
            return (Criteria) this;
        }

        public Criteria andRealNameEqualTo(String value) {
            addCriterion("real_name =", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotEqualTo(String value) {
            addCriterion("real_name <>", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameGreaterThan(String value) {
            addCriterion("real_name >", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameGreaterThanOrEqualTo(String value) {
            addCriterion("real_name >=", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLessThan(String value) {
            addCriterion("real_name <", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLessThanOrEqualTo(String value) {
            addCriterion("real_name <=", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLike(String value) {
            addCriterion("real_name like", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotLike(String value) {
            addCriterion("real_name not like", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameIn(List<String> values) {
            addCriterion("real_name in", values, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotIn(List<String> values) {
            addCriterion("real_name not in", values, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameBetween(String value1, String value2) {
            addCriterion("real_name between", value1, value2, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotBetween(String value1, String value2) {
            addCriterion("real_name not between", value1, value2, "realName");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNull() {
            addCriterion("birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(Date value) {
            addCriterionForJDBCDate("birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(Date value) {
            addCriterionForJDBCDate("birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(Date value) {
            addCriterionForJDBCDate("birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<Date> values) {
            addCriterionForJDBCDate("birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<Date> values) {
            addCriterionForJDBCDate("birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneTagIsNull() {
            addCriterion("phone_tag is null");
            return (Criteria) this;
        }

        public Criteria andPhoneTagIsNotNull() {
            addCriterion("phone_tag is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneTagEqualTo(Integer value) {
            addCriterion("phone_tag =", value, "phoneTag");
            return (Criteria) this;
        }

        public Criteria andPhoneTagNotEqualTo(Integer value) {
            addCriterion("phone_tag <>", value, "phoneTag");
            return (Criteria) this;
        }

        public Criteria andPhoneTagGreaterThan(Integer value) {
            addCriterion("phone_tag >", value, "phoneTag");
            return (Criteria) this;
        }

        public Criteria andPhoneTagGreaterThanOrEqualTo(Integer value) {
            addCriterion("phone_tag >=", value, "phoneTag");
            return (Criteria) this;
        }

        public Criteria andPhoneTagLessThan(Integer value) {
            addCriterion("phone_tag <", value, "phoneTag");
            return (Criteria) this;
        }

        public Criteria andPhoneTagLessThanOrEqualTo(Integer value) {
            addCriterion("phone_tag <=", value, "phoneTag");
            return (Criteria) this;
        }

        public Criteria andPhoneTagIn(List<Integer> values) {
            addCriterion("phone_tag in", values, "phoneTag");
            return (Criteria) this;
        }

        public Criteria andPhoneTagNotIn(List<Integer> values) {
            addCriterion("phone_tag not in", values, "phoneTag");
            return (Criteria) this;
        }

        public Criteria andPhoneTagBetween(Integer value1, Integer value2) {
            addCriterion("phone_tag between", value1, value2, "phoneTag");
            return (Criteria) this;
        }

        public Criteria andPhoneTagNotBetween(Integer value1, Integer value2) {
            addCriterion("phone_tag not between", value1, value2, "phoneTag");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailTagIsNull() {
            addCriterion("email_tag is null");
            return (Criteria) this;
        }

        public Criteria andEmailTagIsNotNull() {
            addCriterion("email_tag is not null");
            return (Criteria) this;
        }

        public Criteria andEmailTagEqualTo(Integer value) {
            addCriterion("email_tag =", value, "emailTag");
            return (Criteria) this;
        }

        public Criteria andEmailTagNotEqualTo(Integer value) {
            addCriterion("email_tag <>", value, "emailTag");
            return (Criteria) this;
        }

        public Criteria andEmailTagGreaterThan(Integer value) {
            addCriterion("email_tag >", value, "emailTag");
            return (Criteria) this;
        }

        public Criteria andEmailTagGreaterThanOrEqualTo(Integer value) {
            addCriterion("email_tag >=", value, "emailTag");
            return (Criteria) this;
        }

        public Criteria andEmailTagLessThan(Integer value) {
            addCriterion("email_tag <", value, "emailTag");
            return (Criteria) this;
        }

        public Criteria andEmailTagLessThanOrEqualTo(Integer value) {
            addCriterion("email_tag <=", value, "emailTag");
            return (Criteria) this;
        }

        public Criteria andEmailTagIn(List<Integer> values) {
            addCriterion("email_tag in", values, "emailTag");
            return (Criteria) this;
        }

        public Criteria andEmailTagNotIn(List<Integer> values) {
            addCriterion("email_tag not in", values, "emailTag");
            return (Criteria) this;
        }

        public Criteria andEmailTagBetween(Integer value1, Integer value2) {
            addCriterion("email_tag between", value1, value2, "emailTag");
            return (Criteria) this;
        }

        public Criteria andEmailTagNotBetween(Integer value1, Integer value2) {
            addCriterion("email_tag not between", value1, value2, "emailTag");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andHeadImgIsNull() {
            addCriterion("head_img is null");
            return (Criteria) this;
        }

        public Criteria andHeadImgIsNotNull() {
            addCriterion("head_img is not null");
            return (Criteria) this;
        }

        public Criteria andHeadImgEqualTo(String value) {
            addCriterion("head_img =", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgNotEqualTo(String value) {
            addCriterion("head_img <>", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgGreaterThan(String value) {
            addCriterion("head_img >", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgGreaterThanOrEqualTo(String value) {
            addCriterion("head_img >=", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgLessThan(String value) {
            addCriterion("head_img <", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgLessThanOrEqualTo(String value) {
            addCriterion("head_img <=", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgLike(String value) {
            addCriterion("head_img like", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgNotLike(String value) {
            addCriterion("head_img not like", value, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgIn(List<String> values) {
            addCriterion("head_img in", values, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgNotIn(List<String> values) {
            addCriterion("head_img not in", values, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgBetween(String value1, String value2) {
            addCriterion("head_img between", value1, value2, "headImg");
            return (Criteria) this;
        }

        public Criteria andHeadImgNotBetween(String value1, String value2) {
            addCriterion("head_img not between", value1, value2, "headImg");
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

        public Criteria andGenderEqualTo(Integer value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(Integer value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(Integer value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Integer value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(Integer value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(Integer value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<Integer> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<Integer> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(Integer value1, Integer value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(Integer value1, Integer value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(Double value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(Double value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(Double value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(Double value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(Double value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(Double value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<Double> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<Double> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(Double value1, Double value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(Double value1, Double value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignIsNull() {
            addCriterion("individuality_sign is null");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignIsNotNull() {
            addCriterion("individuality_sign is not null");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignEqualTo(String value) {
            addCriterion("individuality_sign =", value, "individualitySign");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignNotEqualTo(String value) {
            addCriterion("individuality_sign <>", value, "individualitySign");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignGreaterThan(String value) {
            addCriterion("individuality_sign >", value, "individualitySign");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignGreaterThanOrEqualTo(String value) {
            addCriterion("individuality_sign >=", value, "individualitySign");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignLessThan(String value) {
            addCriterion("individuality_sign <", value, "individualitySign");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignLessThanOrEqualTo(String value) {
            addCriterion("individuality_sign <=", value, "individualitySign");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignLike(String value) {
            addCriterion("individuality_sign like", value, "individualitySign");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignNotLike(String value) {
            addCriterion("individuality_sign not like", value, "individualitySign");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignIn(List<String> values) {
            addCriterion("individuality_sign in", values, "individualitySign");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignNotIn(List<String> values) {
            addCriterion("individuality_sign not in", values, "individualitySign");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignBetween(String value1, String value2) {
            addCriterion("individuality_sign between", value1, value2, "individualitySign");
            return (Criteria) this;
        }

        public Criteria andIndividualitySignNotBetween(String value1, String value2) {
            addCriterion("individuality_sign not between", value1, value2, "individualitySign");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("user_type is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("user_type is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(Integer value) {
            addCriterion("user_type =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(Integer value) {
            addCriterion("user_type <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(Integer value) {
            addCriterion("user_type >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_type >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(Integer value) {
            addCriterion("user_type <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(Integer value) {
            addCriterion("user_type <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<Integer> values) {
            addCriterion("user_type in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<Integer> values) {
            addCriterion("user_type not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(Integer value1, Integer value2) {
            addCriterion("user_type between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("user_type not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andRoleTagIsNull() {
            addCriterion("role_tag is null");
            return (Criteria) this;
        }

        public Criteria andRoleTagIsNotNull() {
            addCriterion("role_tag is not null");
            return (Criteria) this;
        }

        public Criteria andRoleTagEqualTo(Integer value) {
            addCriterion("role_tag =", value, "roleTag");
            return (Criteria) this;
        }

        public Criteria andRoleTagNotEqualTo(Integer value) {
            addCriterion("role_tag <>", value, "roleTag");
            return (Criteria) this;
        }

        public Criteria andRoleTagGreaterThan(Integer value) {
            addCriterion("role_tag >", value, "roleTag");
            return (Criteria) this;
        }

        public Criteria andRoleTagGreaterThanOrEqualTo(Integer value) {
            addCriterion("role_tag >=", value, "roleTag");
            return (Criteria) this;
        }

        public Criteria andRoleTagLessThan(Integer value) {
            addCriterion("role_tag <", value, "roleTag");
            return (Criteria) this;
        }

        public Criteria andRoleTagLessThanOrEqualTo(Integer value) {
            addCriterion("role_tag <=", value, "roleTag");
            return (Criteria) this;
        }

        public Criteria andRoleTagIn(List<Integer> values) {
            addCriterion("role_tag in", values, "roleTag");
            return (Criteria) this;
        }

        public Criteria andRoleTagNotIn(List<Integer> values) {
            addCriterion("role_tag not in", values, "roleTag");
            return (Criteria) this;
        }

        public Criteria andRoleTagBetween(Integer value1, Integer value2) {
            addCriterion("role_tag between", value1, value2, "roleTag");
            return (Criteria) this;
        }

        public Criteria andRoleTagNotBetween(Integer value1, Integer value2) {
            addCriterion("role_tag not between", value1, value2, "roleTag");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andSystemModelIsNull() {
            addCriterion("system_model is null");
            return (Criteria) this;
        }

        public Criteria andSystemModelIsNotNull() {
            addCriterion("system_model is not null");
            return (Criteria) this;
        }

        public Criteria andSystemModelEqualTo(String value) {
            addCriterion("system_model =", value, "systemModel");
            return (Criteria) this;
        }

        public Criteria andSystemModelNotEqualTo(String value) {
            addCriterion("system_model <>", value, "systemModel");
            return (Criteria) this;
        }

        public Criteria andSystemModelGreaterThan(String value) {
            addCriterion("system_model >", value, "systemModel");
            return (Criteria) this;
        }

        public Criteria andSystemModelGreaterThanOrEqualTo(String value) {
            addCriterion("system_model >=", value, "systemModel");
            return (Criteria) this;
        }

        public Criteria andSystemModelLessThan(String value) {
            addCriterion("system_model <", value, "systemModel");
            return (Criteria) this;
        }

        public Criteria andSystemModelLessThanOrEqualTo(String value) {
            addCriterion("system_model <=", value, "systemModel");
            return (Criteria) this;
        }

        public Criteria andSystemModelLike(String value) {
            addCriterion("system_model like", value, "systemModel");
            return (Criteria) this;
        }

        public Criteria andSystemModelNotLike(String value) {
            addCriterion("system_model not like", value, "systemModel");
            return (Criteria) this;
        }

        public Criteria andSystemModelIn(List<String> values) {
            addCriterion("system_model in", values, "systemModel");
            return (Criteria) this;
        }

        public Criteria andSystemModelNotIn(List<String> values) {
            addCriterion("system_model not in", values, "systemModel");
            return (Criteria) this;
        }

        public Criteria andSystemModelBetween(String value1, String value2) {
            addCriterion("system_model between", value1, value2, "systemModel");
            return (Criteria) this;
        }

        public Criteria andSystemModelNotBetween(String value1, String value2) {
            addCriterion("system_model not between", value1, value2, "systemModel");
            return (Criteria) this;
        }

        public Criteria andIntegralLevelIsNull() {
            addCriterion("integral_level is null");
            return (Criteria) this;
        }

        public Criteria andIntegralLevelIsNotNull() {
            addCriterion("integral_level is not null");
            return (Criteria) this;
        }

        public Criteria andIntegralLevelEqualTo(Integer value) {
            addCriterion("integral_level =", value, "integralLevel");
            return (Criteria) this;
        }

        public Criteria andIntegralLevelNotEqualTo(Integer value) {
            addCriterion("integral_level <>", value, "integralLevel");
            return (Criteria) this;
        }

        public Criteria andIntegralLevelGreaterThan(Integer value) {
            addCriterion("integral_level >", value, "integralLevel");
            return (Criteria) this;
        }

        public Criteria andIntegralLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("integral_level >=", value, "integralLevel");
            return (Criteria) this;
        }

        public Criteria andIntegralLevelLessThan(Integer value) {
            addCriterion("integral_level <", value, "integralLevel");
            return (Criteria) this;
        }

        public Criteria andIntegralLevelLessThanOrEqualTo(Integer value) {
            addCriterion("integral_level <=", value, "integralLevel");
            return (Criteria) this;
        }

        public Criteria andIntegralLevelIn(List<Integer> values) {
            addCriterion("integral_level in", values, "integralLevel");
            return (Criteria) this;
        }

        public Criteria andIntegralLevelNotIn(List<Integer> values) {
            addCriterion("integral_level not in", values, "integralLevel");
            return (Criteria) this;
        }

        public Criteria andIntegralLevelBetween(Integer value1, Integer value2) {
            addCriterion("integral_level between", value1, value2, "integralLevel");
            return (Criteria) this;
        }

        public Criteria andIntegralLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("integral_level not between", value1, value2, "integralLevel");
            return (Criteria) this;
        }

        public Criteria andIntegralQtyIsNull() {
            addCriterion("integral_qty is null");
            return (Criteria) this;
        }

        public Criteria andIntegralQtyIsNotNull() {
            addCriterion("integral_qty is not null");
            return (Criteria) this;
        }

        public Criteria andIntegralQtyEqualTo(Integer value) {
            addCriterion("integral_qty =", value, "integralQty");
            return (Criteria) this;
        }

        public Criteria andIntegralQtyNotEqualTo(Integer value) {
            addCriterion("integral_qty <>", value, "integralQty");
            return (Criteria) this;
        }

        public Criteria andIntegralQtyGreaterThan(Integer value) {
            addCriterion("integral_qty >", value, "integralQty");
            return (Criteria) this;
        }

        public Criteria andIntegralQtyGreaterThanOrEqualTo(Integer value) {
            addCriterion("integral_qty >=", value, "integralQty");
            return (Criteria) this;
        }

        public Criteria andIntegralQtyLessThan(Integer value) {
            addCriterion("integral_qty <", value, "integralQty");
            return (Criteria) this;
        }

        public Criteria andIntegralQtyLessThanOrEqualTo(Integer value) {
            addCriterion("integral_qty <=", value, "integralQty");
            return (Criteria) this;
        }

        public Criteria andIntegralQtyIn(List<Integer> values) {
            addCriterion("integral_qty in", values, "integralQty");
            return (Criteria) this;
        }

        public Criteria andIntegralQtyNotIn(List<Integer> values) {
            addCriterion("integral_qty not in", values, "integralQty");
            return (Criteria) this;
        }

        public Criteria andIntegralQtyBetween(Integer value1, Integer value2) {
            addCriterion("integral_qty between", value1, value2, "integralQty");
            return (Criteria) this;
        }

        public Criteria andIntegralQtyNotBetween(Integer value1, Integer value2) {
            addCriterion("integral_qty not between", value1, value2, "integralQty");
            return (Criteria) this;
        }

        public Criteria andMileageTotalIsNull() {
            addCriterion("mileage_total is null");
            return (Criteria) this;
        }

        public Criteria andMileageTotalIsNotNull() {
            addCriterion("mileage_total is not null");
            return (Criteria) this;
        }

        public Criteria andMileageTotalEqualTo(Double value) {
            addCriterion("mileage_total =", value, "mileageTotal");
            return (Criteria) this;
        }

        public Criteria andMileageTotalNotEqualTo(Double value) {
            addCriterion("mileage_total <>", value, "mileageTotal");
            return (Criteria) this;
        }

        public Criteria andMileageTotalGreaterThan(Double value) {
            addCriterion("mileage_total >", value, "mileageTotal");
            return (Criteria) this;
        }

        public Criteria andMileageTotalGreaterThanOrEqualTo(Double value) {
            addCriterion("mileage_total >=", value, "mileageTotal");
            return (Criteria) this;
        }

        public Criteria andMileageTotalLessThan(Double value) {
            addCriterion("mileage_total <", value, "mileageTotal");
            return (Criteria) this;
        }

        public Criteria andMileageTotalLessThanOrEqualTo(Double value) {
            addCriterion("mileage_total <=", value, "mileageTotal");
            return (Criteria) this;
        }

        public Criteria andMileageTotalIn(List<Double> values) {
            addCriterion("mileage_total in", values, "mileageTotal");
            return (Criteria) this;
        }

        public Criteria andMileageTotalNotIn(List<Double> values) {
            addCriterion("mileage_total not in", values, "mileageTotal");
            return (Criteria) this;
        }

        public Criteria andMileageTotalBetween(Double value1, Double value2) {
            addCriterion("mileage_total between", value1, value2, "mileageTotal");
            return (Criteria) this;
        }

        public Criteria andMileageTotalNotBetween(Double value1, Double value2) {
            addCriterion("mileage_total not between", value1, value2, "mileageTotal");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(Double value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(Double value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(Double value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(Double value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(Double value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<Double> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<Double> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(Double value1, Double value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(Double value1, Double value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(Double value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(Double value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(Double value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(Double value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(Double value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<Double> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<Double> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(Double value1, Double value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(Double value1, Double value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLocationTagIsNull() {
            addCriterion("location_tag is null");
            return (Criteria) this;
        }

        public Criteria andLocationTagIsNotNull() {
            addCriterion("location_tag is not null");
            return (Criteria) this;
        }

        public Criteria andLocationTagEqualTo(Integer value) {
            addCriterion("location_tag =", value, "locationTag");
            return (Criteria) this;
        }

        public Criteria andLocationTagNotEqualTo(Integer value) {
            addCriterion("location_tag <>", value, "locationTag");
            return (Criteria) this;
        }

        public Criteria andLocationTagGreaterThan(Integer value) {
            addCriterion("location_tag >", value, "locationTag");
            return (Criteria) this;
        }

        public Criteria andLocationTagGreaterThanOrEqualTo(Integer value) {
            addCriterion("location_tag >=", value, "locationTag");
            return (Criteria) this;
        }

        public Criteria andLocationTagLessThan(Integer value) {
            addCriterion("location_tag <", value, "locationTag");
            return (Criteria) this;
        }

        public Criteria andLocationTagLessThanOrEqualTo(Integer value) {
            addCriterion("location_tag <=", value, "locationTag");
            return (Criteria) this;
        }

        public Criteria andLocationTagIn(List<Integer> values) {
            addCriterion("location_tag in", values, "locationTag");
            return (Criteria) this;
        }

        public Criteria andLocationTagNotIn(List<Integer> values) {
            addCriterion("location_tag not in", values, "locationTag");
            return (Criteria) this;
        }

        public Criteria andLocationTagBetween(Integer value1, Integer value2) {
            addCriterion("location_tag between", value1, value2, "locationTag");
            return (Criteria) this;
        }

        public Criteria andLocationTagNotBetween(Integer value1, Integer value2) {
            addCriterion("location_tag not between", value1, value2, "locationTag");
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