package com.ycandyz.master.controller.quartz;

import com.ycandyz.master.entities.mall.MallAfterSales;
import com.ycandyz.master.service.mall.impl.MaillOrderServiceImpl;
import com.ycandyz.master.service.mall.impl.MallAfterSalesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 售后定时任务
 * @author SanGang
 * @since 2020-11-02
 * @version 2.0
 */

@Slf4j
@Component
@EnableScheduling
public class MallAfterSalesQuartz extends BaseQuartz{

	@Autowired
	private MallAfterSalesServiceImpl mallAfterSalesService;
	
	/**
	 * <p>
	 * @Description 售后退货有效期,更改售后状态,30分钟执行一次
	 * </p>
	 *
	 * @author SanGang
	 * @since 2020-11-02
	 * @version 2.0
	 */
	@Scheduled(cron="0 0/01 * * * ?")
	public void getVideoCountryGroup() {
		log.info("===========售后定时任务==============");
		mallAfterSalesService.processSubStatus();
	}
	
}
