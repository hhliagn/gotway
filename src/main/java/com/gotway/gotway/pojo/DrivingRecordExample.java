package com.gotway.gotway.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrivingRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DrivingRecordExample() {
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

        public Criteria andDeviceNameIsNull() {
            addCriterion("device_name is null");
            return (Criteria) this;
        }

        public Criteria andDeviceNameIsNotNull() {
            addCriterion("device_name is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceNameEqualTo(String value) {
            addCriterion("device_name =", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameNotEqualTo(String value) {
            addCriterion("device_name <>", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameGreaterThan(String value) {
            addCriterion("device_name >", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameGreaterThanOrEqualTo(String value) {
            addCriterion("device_name >=", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameLessThan(String value) {
            addCriterion("device_name <", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameLessThanOrEqualTo(String value) {
            addCriterion("device_name <=", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameLike(String value) {
            addCriterion("device_name like", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameNotLike(String value) {
            addCriterion("device_name not like", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameIn(List<String> values) {
            addCriterion("device_name in", values, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameNotIn(List<String> values) {
            addCriterion("device_name not in", values, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameBetween(String value1, String value2) {
            addCriterion("device_name between", value1, value2, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameNotBetween(String value1, String value2) {
            addCriterion("device_name not between", value1, value2, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceMacIsNull() {
            addCriterion("device_mac is null");
            return (Criteria) this;
        }

        public Criteria andDeviceMacIsNotNull() {
            addCriterion("device_mac is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceMacEqualTo(String value) {
            addCriterion("device_mac =", value, "deviceMac");
            return (Criteria) this;
        }

        public Criteria andDeviceMacNotEqualTo(String value) {
            addCriterion("device_mac <>", value, "deviceMac");
            return (Criteria) this;
        }

        public Criteria andDeviceMacGreaterThan(String value) {
            addCriterion("device_mac >", value, "deviceMac");
            return (Criteria) this;
        }

        public Criteria andDeviceMacGreaterThanOrEqualTo(String value) {
            addCriterion("device_mac >=", value, "deviceMac");
            return (Criteria) this;
        }

        public Criteria andDeviceMacLessThan(String value) {
            addCriterion("device_mac <", value, "deviceMac");
            return (Criteria) this;
        }

        public Criteria andDeviceMacLessThanOrEqualTo(String value) {
            addCriterion("device_mac <=", value, "deviceMac");
            return (Criteria) this;
        }

        public Criteria andDeviceMacLike(String value) {
            addCriterion("device_mac like", value, "deviceMac");
            return (Criteria) this;
        }

        public Criteria andDeviceMacNotLike(String value) {
            addCriterion("device_mac not like", value, "deviceMac");
            return (Criteria) this;
        }

        public Criteria andDeviceMacIn(List<String> values) {
            addCriterion("device_mac in", values, "deviceMac");
            return (Criteria) this;
        }

        public Criteria andDeviceMacNotIn(List<String> values) {
            addCriterion("device_mac not in", values, "deviceMac");
            return (Criteria) this;
        }

        public Criteria andDeviceMacBetween(String value1, String value2) {
            addCriterion("device_mac between", value1, value2, "deviceMac");
            return (Criteria) this;
        }

        public Criteria andDeviceMacNotBetween(String value1, String value2) {
            addCriterion("device_mac not between", value1, value2, "deviceMac");
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

        public Criteria andMileageIsNull() {
            addCriterion("mileage is null");
            return (Criteria) this;
        }

        public Criteria andMileageIsNotNull() {
            addCriterion("mileage is not null");
            return (Criteria) this;
        }

        public Criteria andMileageEqualTo(Double value) {
            addCriterion("mileage =", value, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageNotEqualTo(Double value) {
            addCriterion("mileage <>", value, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageGreaterThan(Double value) {
            addCriterion("mileage >", value, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageGreaterThanOrEqualTo(Double value) {
            addCriterion("mileage >=", value, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageLessThan(Double value) {
            addCriterion("mileage <", value, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageLessThanOrEqualTo(Double value) {
            addCriterion("mileage <=", value, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageIn(List<Double> values) {
            addCriterion("mileage in", values, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageNotIn(List<Double> values) {
            addCriterion("mileage not in", values, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageBetween(Double value1, Double value2) {
            addCriterion("mileage between", value1, value2, "mileage");
            return (Criteria) this;
        }

        public Criteria andMileageNotBetween(Double value1, Double value2) {
            addCriterion("mileage not between", value1, value2, "mileage");
            return (Criteria) this;
        }

        public Criteria andDurationIsNull() {
            addCriterion("duration is null");
            return (Criteria) this;
        }

        public Criteria andDurationIsNotNull() {
            addCriterion("duration is not null");
            return (Criteria) this;
        }

        public Criteria andDurationEqualTo(Long value) {
            addCriterion("duration =", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotEqualTo(Long value) {
            addCriterion("duration <>", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThan(Long value) {
            addCriterion("duration >", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThanOrEqualTo(Long value) {
            addCriterion("duration >=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThan(Long value) {
            addCriterion("duration <", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThanOrEqualTo(Long value) {
            addCriterion("duration <=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationIn(List<Long> values) {
            addCriterion("duration in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotIn(List<Long> values) {
            addCriterion("duration not in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationBetween(Long value1, Long value2) {
            addCriterion("duration between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotBetween(Long value1, Long value2) {
            addCriterion("duration not between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andElectricityIsNull() {
            addCriterion("electricity is null");
            return (Criteria) this;
        }

        public Criteria andElectricityIsNotNull() {
            addCriterion("electricity is not null");
            return (Criteria) this;
        }

        public Criteria andElectricityEqualTo(Integer value) {
            addCriterion("electricity =", value, "electricity");
            return (Criteria) this;
        }

        public Criteria andElectricityNotEqualTo(Integer value) {
            addCriterion("electricity <>", value, "electricity");
            return (Criteria) this;
        }

        public Criteria andElectricityGreaterThan(Integer value) {
            addCriterion("electricity >", value, "electricity");
            return (Criteria) this;
        }

        public Criteria andElectricityGreaterThanOrEqualTo(Integer value) {
            addCriterion("electricity >=", value, "electricity");
            return (Criteria) this;
        }

        public Criteria andElectricityLessThan(Integer value) {
            addCriterion("electricity <", value, "electricity");
            return (Criteria) this;
        }

        public Criteria andElectricityLessThanOrEqualTo(Integer value) {
            addCriterion("electricity <=", value, "electricity");
            return (Criteria) this;
        }

        public Criteria andElectricityIn(List<Integer> values) {
            addCriterion("electricity in", values, "electricity");
            return (Criteria) this;
        }

        public Criteria andElectricityNotIn(List<Integer> values) {
            addCriterion("electricity not in", values, "electricity");
            return (Criteria) this;
        }

        public Criteria andElectricityBetween(Integer value1, Integer value2) {
            addCriterion("electricity between", value1, value2, "electricity");
            return (Criteria) this;
        }

        public Criteria andElectricityNotBetween(Integer value1, Integer value2) {
            addCriterion("electricity not between", value1, value2, "electricity");
            return (Criteria) this;
        }

        public Criteria andHighestSpeedIsNull() {
            addCriterion("highest_speed is null");
            return (Criteria) this;
        }

        public Criteria andHighestSpeedIsNotNull() {
            addCriterion("highest_speed is not null");
            return (Criteria) this;
        }

        public Criteria andHighestSpeedEqualTo(Double value) {
            addCriterion("highest_speed =", value, "highestSpeed");
            return (Criteria) this;
        }

        public Criteria andHighestSpeedNotEqualTo(Double value) {
            addCriterion("highest_speed <>", value, "highestSpeed");
            return (Criteria) this;
        }

        public Criteria andHighestSpeedGreaterThan(Double value) {
            addCriterion("highest_speed >", value, "highestSpeed");
            return (Criteria) this;
        }

        public Criteria andHighestSpeedGreaterThanOrEqualTo(Double value) {
            addCriterion("highest_speed >=", value, "highestSpeed");
            return (Criteria) this;
        }

        public Criteria andHighestSpeedLessThan(Double value) {
            addCriterion("highest_speed <", value, "highestSpeed");
            return (Criteria) this;
        }

        public Criteria andHighestSpeedLessThanOrEqualTo(Double value) {
            addCriterion("highest_speed <=", value, "highestSpeed");
            return (Criteria) this;
        }

        public Criteria andHighestSpeedIn(List<Double> values) {
            addCriterion("highest_speed in", values, "highestSpeed");
            return (Criteria) this;
        }

        public Criteria andHighestSpeedNotIn(List<Double> values) {
            addCriterion("highest_speed not in", values, "highestSpeed");
            return (Criteria) this;
        }

        public Criteria andHighestSpeedBetween(Double value1, Double value2) {
            addCriterion("highest_speed between", value1, value2, "highestSpeed");
            return (Criteria) this;
        }

        public Criteria andHighestSpeedNotBetween(Double value1, Double value2) {
            addCriterion("highest_speed not between", value1, value2, "highestSpeed");
            return (Criteria) this;
        }

        public Criteria andAveSpeedIsNull() {
            addCriterion("ave_speed is null");
            return (Criteria) this;
        }

        public Criteria andAveSpeedIsNotNull() {
            addCriterion("ave_speed is not null");
            return (Criteria) this;
        }

        public Criteria andAveSpeedEqualTo(Double value) {
            addCriterion("ave_speed =", value, "aveSpeed");
            return (Criteria) this;
        }

        public Criteria andAveSpeedNotEqualTo(Double value) {
            addCriterion("ave_speed <>", value, "aveSpeed");
            return (Criteria) this;
        }

        public Criteria andAveSpeedGreaterThan(Double value) {
            addCriterion("ave_speed >", value, "aveSpeed");
            return (Criteria) this;
        }

        public Criteria andAveSpeedGreaterThanOrEqualTo(Double value) {
            addCriterion("ave_speed >=", value, "aveSpeed");
            return (Criteria) this;
        }

        public Criteria andAveSpeedLessThan(Double value) {
            addCriterion("ave_speed <", value, "aveSpeed");
            return (Criteria) this;
        }

        public Criteria andAveSpeedLessThanOrEqualTo(Double value) {
            addCriterion("ave_speed <=", value, "aveSpeed");
            return (Criteria) this;
        }

        public Criteria andAveSpeedIn(List<Double> values) {
            addCriterion("ave_speed in", values, "aveSpeed");
            return (Criteria) this;
        }

        public Criteria andAveSpeedNotIn(List<Double> values) {
            addCriterion("ave_speed not in", values, "aveSpeed");
            return (Criteria) this;
        }

        public Criteria andAveSpeedBetween(Double value1, Double value2) {
            addCriterion("ave_speed between", value1, value2, "aveSpeed");
            return (Criteria) this;
        }

        public Criteria andAveSpeedNotBetween(Double value1, Double value2) {
            addCriterion("ave_speed not between", value1, value2, "aveSpeed");
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