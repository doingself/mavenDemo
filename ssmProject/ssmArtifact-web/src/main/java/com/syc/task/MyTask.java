package com.syc.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyTask {

    @Scheduled(fixedDelay = 5000)
    //@Scheduled 可以作为一个触发源添加到一个方法中
    //以一个固定延迟时间5秒钟调用一次执行
    //这个周期是以上一个调用任务的##完成时间##为基准，在上一个任务完成之后，5s后再次执行
    public void taskByFixedDelay() {
        System.out.println("taskByFixedDelay 当前时间" + new Date().getTime());
    }


    @Scheduled(cron = "0 34 13 * * ?")
    //如果你需要在特定的时间执行，就需要用到cron 了
    //这里是在每天的13点30分执行一次
    public void taskByCron() {
        /*
        * 一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。
      1 秒（0~59）
      2 分钟（0~59）
      3 小时（0~23）
      4 天（0~31）
      5 月（0~11）
      6 星期（1~7 1为SUN-依次为SUN，MON，TUE，WED，THU，FRI，SAT）
      7.年份（1970－2099）

      其中每个元素可以是一个值(如6),一个连续区间(9-12),一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符*。

       0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
       0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
       0 0 12 ? * WED 表示每个星期三中午12点
       0 0 12 * * ? 每天中午12点触发
       0 15 10 ? * * 每天上午10:15触发
      */
        System.out.println("taskByCron 当前时间" + new Date().getTime());
    }
}
