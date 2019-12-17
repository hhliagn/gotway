package com.gotway.gotway.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TopExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TopExample() {
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

        public Criteria andMemIdIsNull() {
            addCriterion("mem_id is null");
            return (Criteria) this;
        }

        public Criteria andMemIdIsNotNull() {
            addCriterion("mem_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemIdEqualTo(Integer value) {
            addCriterion("mem_id =", value, "memId");
            return (Criteria) this;
        }

        public Criteria andMemIdNotEqualTo(Integer value) {
            addCriterion("mem_id <>", value, "memId");
            return (Criteria) this;
        }

        public Criteria andMemIdGreaterThan(Integer value) {
            addCriterion("mem_id >", value, "memId");
            return (Criteria) this;
        }

        public Criteria andMemIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("mem_id >=", value, "memId");
            return (Criteria) this;
        }

        public Criteria andMemIdLessThan(Integer value) {
            addCriterion("mem_id <", value, "memId");
            return (Criteria) this;
        }

        public Criteria andMemIdLessThanOrEqualTo(Integer value) {
            addCriterion("mem_id <=", value, "memId");
            return (Criteria) this;
        }

        public Criteria andMemIdIn(List<Integer> values) {
            addCriterion("mem_id in", values, "memId");
            return (Criteria) this;
        }

        public Criteria andMemIdNotIn(List<Integer> values) {
            addCriterion("mem_id not in", values, "memId");
            return (Criteria) this;
        }

        public Criteria andMemIdBetween(Integer value1, Integer value2) {
            addCriterion("mem_id between", value1, value2, "memId");
            return (Criteria) this;
        }

        public Criteria andMemIdNotBetween(Integer value1, Integer value2) {
            addCriterion("mem_id not between", value1, value2, "memId");
            return (Criteria) this;
        }

        public Criteria andSumMileageIsNull() {
            addCriterion("sum_mileage is null");
            return (Criteria) this;
        }

        public Criteria andSumMileageIsNotNull() {
            addCriterion("sum_mileage is not null");
            return (Criteria) this;
        }

        public Criteria andSumMileageEqualTo(Double value) {
            addCriterion("sum_mileage =", value, "sumMileage");
            return (Criteria) this;
        }

        public Criteria andSumMileageNotEqualTo(Double value) {
            addCriterion("sum_mileage <>", value, "sumMileage");
            return (Criteria) this;
        }

        public Criteria andSumMileageGreaterThan(Double value) {
            addCriterion("sum_mileage >", value, "sumMileage");
            return (Criteria) this;
        }

        public Criteria andSumMileageGreaterThanOrEqualTo(Double value) {
            addCriterion("sum_mileage >=", value, "sumMileage");
            return (Criteria) this;
        }

        public Criteria andSumMileageLessThan(Double value) {
            addCriterion("sum_mileage <", value, "sumMileage");
            return (Criteria) this;
        }

        public Criteria andSumMileageLessThanOrEqualTo(Double value) {
            addCriterion("sum_mileage <=", value, "sumMileage");
            return (Criteria) this;
        }

        public Criteria andSumMileageIn(List<Double> values) {
            addCriterion("sum_mileage in", values, "sumMileage");
            return (Criteria) this;
        }

        public Criteria andSumMileageNotIn(List<Double> values) {
            addCriterion("sum_mileage not in", values, "sumMileage");
            return (Criteria) this;
        }

        public Criteria andSumMileageBetween(Double value1, Double value2) {
            addCriterion("sum_mileage between", value1, value2, "sumMileage");
            return (Criteria) this;
        }

        public Criteria andSumMileageNotBetween(Double value1, Double value2) {
            addCriterion("sum_mileage not between", value1, value2, "sumMileage");
            return (Criteria) this;
        }

        public Criteria andTodayMileageIsNull() {
            addCriterion("today_mileage is null");
            return (Criteria) this;
        }

        public Criteria andTodayMileageIsNotNull() {
            addCriterion("today_mileage is not null");
            return (Criteria) this;
        }

        public Criteria andTodayMileageEqualTo(Double value) {
            addCriterion("today_mileage =", value, "todayMileage");
            return (Criteria) this;
        }

        public Criteria andTodayMileageNotEqualTo(Double value) {
            addCriterion("today_mileage <>", value, "todayMileage");
            return (Criteria) this;
        }

        public Criteria andTodayMileageGreaterThan(Double value) {
            addCriterion("today_mileage >", value, "todayMileage");
            return (Criteria) this;
        }

        public Criteria andTodayMileageGreaterThanOrEqualTo(Double value) {
            addCriterion("today_mileage >=", value, "todayMileage");
            return (Criteria) this;
        }

        public Criteria andTodayMileageLessThan(Double value) {
            addCriterion("today_mileage <", value, "todayMileage");
            return (Criteria) this;
        }

        public Criteria andTodayMileageLessThanOrEqualTo(Double value) {
            addCriterion("today_mileage <=", value, "todayMileage");
            return (Criteria) this;
        }

        public Criteria andTodayMileageIn(List<Double> values) {
            addCriterion("today_mileage in", values, "todayMileage");
            return (Criteria) this;
        }

        public Criteria andTodayMileageNotIn(List<Double> values) {
            addCriterion("today_mileage not in", values, "todayMileage");
            return (Criteria) this;
        }

        public Criteria andTodayMileageBetween(Double value1, Double value2) {
            addCriterion("today_mileage between", value1, value2, "todayMileage");
            return (Criteria) this;
        }

        public Criteria andTodayMileageNotBetween(Double value1, Double value2) {
            addCriterion("today_mileage not between", value1, value2, "todayMileage");
            return (Criteria) this;
        }

        public Criteria andWeekMileageIsNull() {
            addCriterion("week_mileage is null");
            return (Criteria) this;
        }

        public Criteria andWeekMileageIsNotNull() {
            addCriterion("week_mileage is not null");
            return (Criteria) this;
        }

        public Criteria andWeekMileageEqualTo(Double value) {
            addCriterion("week_mileage =", value, "weekMileage");
            return (Criteria) this;
        }

        public Criteria andWeekMileageNotEqualTo(Double value) {
            addCriterion("week_mileage <>", value, "weekMileage");
            return (Criteria) this;
        }

        public Criteria andWeekMileageGreaterThan(Double value) {
            addCriterion("week_mileage >", value, "weekMileage");
            return (Criteria) this;
        }

        public Criteria andWeekMileageGreaterThanOrEqualTo(Double value) {
            addCriterion("week_mileage >=", value, "weekMileage");
            return (Criteria) this;
        }

        public Criteria andWeekMileageLessThan(Double value) {
            addCriterion("week_mileage <", value, "weekMileage");
            return (Criteria) this;
        }

        public Criteria andWeekMileageLessThanOrEqualTo(Double value) {
            addCriterion("week_mileage <=", value, "weekMileage");
            return (Criteria) this;
        }

        public Criteria andWeekMileageIn(List<Double> values) {
            addCriterion("week_mileage in", values, "weekMileage");
            return (Criteria) this;
        }

        public Criteria andWeekMileageNotIn(List<Double> values) {
            addCriterion("week_mileage not in", values, "weekMileage");
            return (Criteria) this;
        }

        public Criteria andWeekMileageBetween(Double value1, Double value2) {
            addCriterion("week_mileage between", value1, value2, "weekMileage");
            return (Criteria) this;
        }

        public Criteria andWeekMileageNotBetween(Double value1, Double value2) {
            addCriterion("week_mileage not between", value1, value2, "weekMileage");
            return (Criteria) this;
        }

        public Criteria andMonthMileageIsNull() {
            addCriterion("month_mileage is null");
            return (Criteria) this;
        }

        public Criteria andMonthMileageIsNotNull() {
            addCriterion("month_mileage is not null");
            return (Criteria) this;
        }

        public Criteria andMonthMileageEqualTo(Double value) {
            addCriterion("month_mileage =", value, "monthMileage");
            return (Criteria) this;
        }

        public Criteria andMonthMileageNotEqualTo(Double value) {
            addCriterion("month_mileage <>", value, "monthMileage");
            return (Criteria) this;
        }

        public Criteria andMonthMileageGreaterThan(Double value) {
            addCriterion("month_mileage >", value, "monthMileage");
            return (Criteria) this;
        }

        public Criteria andMonthMileageGreaterThanOrEqualTo(Double value) {
            addCriterion("month_mileage >=", value, "monthMileage");
            return (Criteria) this;
        }

        public Criteria andMonthMileageLessThan(Double value) {
            addCriterion("month_mileage <", value, "monthMileage");
            return (Criteria) this;
        }

        public Criteria andMonthMileageLessThanOrEqualTo(Double value) {
            addCriterion("month_mileage <=", value, "monthMileage");
            return (Criteria) this;
        }

        public Criteria andMonthMileageIn(List<Double> values) {
            addCriterion("month_mileage in", values, "monthMileage");
            return (Criteria) this;
        }

        public Criteria andMonthMileageNotIn(List<Double> values) {
            addCriterion("month_mileage not in", values, "monthMileage");
            return (Criteria) this;
        }

        public Criteria andMonthMileageBetween(Double value1, Double value2) {
            addCriterion("month_mileage between", value1, value2, "monthMileage");
            return (Criteria) this;
        }

        public Criteria andMonthMileageNotBetween(Double value1, Double value2) {
            addCriterion("month_mileage not between", value1, value2, "monthMileage");
            return (Criteria) this;
        }

        public Criteria andSupportCountIsNull() {
            addCriterion("support_count is null");
            return (Criteria) this;
        }

        public Criteria andSupportCountIsNotNull() {
            addCriterion("support_count is not null");
            return (Criteria) this;
        }

        public Criteria andSupportCountEqualTo(Integer value) {
            addCriterion("support_count =", value, "supportCount");
            return (Criteria) this;
        }

        public Criteria andSupportCountNotEqualTo(Integer value) {
            addCriterion("support_count <>", value, "supportCount");
            return (Criteria) this;
        }

        public Criteria andSupportCountGreaterThan(Integer value) {
            addCriterion("support_count >", value, "supportCount");
            return (Criteria) this;
        }

        public Criteria andSupportCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("support_count >=", value, "supportCount");
            return (Criteria) this;
        }

        public Criteria andSupportCountLessThan(Integer value) {
            addCriterion("support_count <", value, "supportCount");
            return (Criteria) this;
        }

        public Criteria andSupportCountLessThanOrEqualTo(Integer value) {
            addCriterion("support_count <=", value, "supportCount");
            return (Criteria) this;
        }

        public Criteria andSupportCountIn(List<Integer> values) {
            addCriterion("support_count in", values, "supportCount");
            return (Criteria) this;
        }

        public Criteria andSupportCountNotIn(List<Integer> values) {
            addCriterion("support_count not in", values, "supportCount");
            return (Criteria) this;
        }

        public Criteria andSupportCountBetween(Integer value1, Integer value2) {
            addCriterion("support_count between", value1, value2, "supportCount");
            return (Criteria) this;
        }

        public Criteria andSupportCountNotBetween(Integer value1, Integer value2) {
            addCriterion("support_count not between", value1, value2, "supportCount");
            return (Criteria) this;
        }

        public Criteria andTopicCountIsNull() {
            addCriterion("topic_count is null");
            return (Criteria) this;
        }

        public Criteria andTopicCountIsNotNull() {
            addCriterion("topic_count is not null");
            return (Criteria) this;
        }

        public Criteria andTopicCountEqualTo(Integer value) {
            addCriterion("topic_count =", value, "topicCount");
            return (Criteria) this;
        }

        public Criteria andTopicCountNotEqualTo(Integer value) {
            addCriterion("topic_count <>", value, "topicCount");
            return (Criteria) this;
        }

        public Criteria andTopicCountGreaterThan(Integer value) {
            addCriterion("topic_count >", value, "topicCount");
            return (Criteria) this;
        }

        public Criteria andTopicCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("topic_count >=", value, "topicCount");
            return (Criteria) this;
        }

        public Criteria andTopicCountLessThan(Integer value) {
            addCriterion("topic_count <", value, "topicCount");
            return (Criteria) this;
        }

        public Criteria andTopicCountLessThanOrEqualTo(Integer value) {
            addCriterion("topic_count <=", value, "topicCount");
            return (Criteria) this;
        }

        public Criteria andTopicCountIn(List<Integer> values) {
            addCriterion("topic_count in", values, "topicCount");
            return (Criteria) this;
        }

        public Criteria andTopicCountNotIn(List<Integer> values) {
            addCriterion("topic_count not in", values, "topicCount");
            return (Criteria) this;
        }

        public Criteria andTopicCountBetween(Integer value1, Integer value2) {
            addCriterion("topic_count between", value1, value2, "topicCount");
            return (Criteria) this;
        }

        public Criteria andTopicCountNotBetween(Integer value1, Integer value2) {
            addCriterion("topic_count not between", value1, value2, "topicCount");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Integer value) {
            addCriterion("level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Integer value) {
            addCriterion("level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Integer value) {
            addCriterion("level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Integer value) {
            addCriterion("level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Integer value) {
            addCriterion("level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Integer> values) {
            addCriterion("level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Integer> values) {
            addCriterion("level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Integer value1, Integer value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("level not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
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