package com.gotway.gotway.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gotway.gotway.mapper.*;

import com.gotway.gotway.pojo.PointAssist;
import com.gotway.gotway.pojo.PointAssistExample;
import com.gotway.gotway.pojo.Top;
import com.gotway.gotway.pojo.TopExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



/**
 *  任务调度
 */
@Component
public class SysScheduler {

	@Autowired
	private TopMapper topMapper;
	@Autowired
	private PointAssistMapper pointAssistMapper;
	@Autowired
	private DrivingRecordMapper drivingRecordMapper;
	// 日期格式
	DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


	// 每天0时1分执行
	@Scheduled(cron="0 1 0 * * ? ")
	void everyday() {
        System.out.println("任务调度1开始执行>>>>>>>>>>>>>>>>>>>");
		//1,日里程归零
		try {
			int maxnum =1;
			int num = 1;
			int size = 200;
			Top top = new Top();
			top.setTodayMileage(0D);
			while(num<=maxnum){
				Page<Object> page = PageHelper.startPage(num, size);
				TopExample example = new TopExample();
				List<Top> tops = topMapper.selectByExample(example);
				if(maxnum==1)maxnum= Long.valueOf(page.getTotal()/size +1 ).intValue();

				List<Integer> ids  = new ArrayList<Integer>();
				if(tops!=null && tops.size()>0)for (Top t:tops) {
					ids.add(t.getId());
				}
				example.createCriteria().andIdIn(ids);
				topMapper.updateByExampleSelective(top,example);
				num++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("任务调度1出现异常>>>>>>>>>>>>>>>>>>>");
		}
        System.out.println("任务调度2开始执行>>>>>>>>>>>>>>>>>>>");
		//2,清除积分辅助表当天前的数据
		try {
			PointAssistExample example = new PointAssistExample();
			example.createCriteria().andCreateDateNotEqualTo(new Date()).andStickIdIsNull();
			pointAssistMapper.deleteByExample(example);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("任务调度2出现异常>>>>>>>>>>>>>>>>>>>");
		}
	}

	// 每周星期天0时1分执行
	@Scheduled(cron="0 1 0 ? * SUN ")
	void everyWeek() {
        System.out.println("任务调度3开始执行>>>>>>>>>>>>>>>>>>>");
		//3,上周里程重新统计
		try {
			int maxnum =1;
			int num = 1;
			int size = 200;

			while(num<=maxnum) {
				Page<Object> page = PageHelper.startPage(num, size);
				List<Map<String,Object>> list = drivingRecordMapper.selectForStatisticsLastWeek();
				if(maxnum==1)maxnum= Long.valueOf(page.getTotal()/size +1 ).intValue();
				if(list!=null && list.size()>0) for (Map<String,Object> m:list) {
					Top top = new Top();
					top.setWeekMileage(Double.valueOf(m.get("sumMileage").toString()));
					TopExample example = new TopExample();
					example.createCriteria().andMemIdEqualTo(Integer.valueOf(m.get("user_id").toString()));
					topMapper.updateByExampleSelective(top,example);
				}
				num++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("任务调度3出现异常>>>>>>>>>>>>>>>>>>>");
		}
	}

	// 每月一号0时1分执行
	@Scheduled(cron="0 1 0 1 * ?")
	void everyMonth() {
        System.out.println("任务调度4开始执行>>>>>>>>>>>>>>>>>>>");
		//1,上月里程重新统计
		try {
			int maxnum =1;
			int num = 1;
			int size = 200;

			while(num<=maxnum) {
				Page<Object> page = PageHelper.startPage(num, size);
				List<Map<String,Object>> list = drivingRecordMapper.selectForStatisticsLastMonth();
				if(maxnum==1)maxnum= Long.valueOf(page.getTotal()/size +1 ).intValue();
				if(list!=null && list.size()>0) for (Map<String,Object> m:list) {
					Top top = new Top();
					top.setMonthMileage(Double.valueOf(m.get("sumMileage").toString()));
					TopExample example = new TopExample();
					example.createCriteria().andMemIdEqualTo(Integer.valueOf(m.get("user_id").toString()));
					topMapper.updateByExampleSelective(top,example);
				}
				num++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("任务调度4出现异常>>>>>>>>>>>>>>>>>>>");
		}
	}
	// 每天0时0分处理一次
	@Scheduled(cron="0 0 0 * * ?")
	void check() {
		System.out.println("任务调度5开始执行>>>>>>>>>>>>>>>>>>>");
		//,校正一些数据
		try {
			//校正评论数
			//校正用户总里程


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("任务调度5出现异常>>>>>>>>>>>>>>>>>>>");
		}
	}
	
}