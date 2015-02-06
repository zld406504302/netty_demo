package cn.lida.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:15-1-23
 * Email:zhanglida@huoqiu.cn
 */
public class TimeCostStat extends Thread {
    Logger logger = LoggerFactory.getLogger(TimeCostStat.class);

    private long sendStartNaTime;
    private long sendEndNaTime;

    private long receiveStartNaTime;
    private long receiveEndNaTime;

    private boolean done;

    //单挑消息大小
    private int singleMsgSize;

    //目标发送消息数量
    private int totalMsgCount;

    //实际发送条数
    private AtomicInteger sendMsgCount = new AtomicInteger(0);

    //实际接受条数
    private AtomicInteger receiveMsgCount = new AtomicInteger(0);


    //几秒钟统计一下数据
    private int statIntervalSecond = 1000;

    static TimeCostStat instance = new TimeCostStat();

    public static TimeCostStat getInstance() {
        return instance;
    }

    @Override
    public void run() {

        if (0 == totalMsgCount)
            throw new IllegalArgumentException("请输入消息总条数");


        this.sendStartNaTime = System.nanoTime();
        this.receiveStartNaTime = System.nanoTime();
        while (!isDone()) {
            try {
                Thread.sleep(statIntervalSecond);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (totalMsgCount != sendMsgCount.get() || sendEndNaTime == 0)
                sendEndNaTime = System.nanoTime();

            if (totalMsgCount != receiveMsgCount.get() || receiveEndNaTime == 0)
                receiveEndNaTime = System.nanoTime();

            System.out.println(new StatInfo(sendMsgCount.get(), receiveMsgCount.get(), (sendEndNaTime - sendStartNaTime), (receiveEndNaTime - receiveStartNaTime)).toString());
        }

        System.out.println("complete:" + new StatInfo(sendMsgCount.get(), receiveMsgCount.get(), (sendEndNaTime - sendStartNaTime), (receiveEndNaTime - receiveStartNaTime)).toString());

    }

    private class StatInfo {
        private int sendMsgCount;
        private int receiveMsgCount;
        private long sendCostNaTime;
        private long receiveCostNaTime ;

        public StatInfo(int sendMsgCount, int receiveMsgCount, long sendCostNaTime , long receiveCostNaTime) {
            this.sendMsgCount = sendMsgCount;
            this.receiveMsgCount = receiveMsgCount;
            this.sendCostNaTime = sendCostNaTime;
            this.receiveCostNaTime = receiveCostNaTime;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("********************************").append("\n");
            builder.append("单条消息大小:").append(singleMsgSize).append("kb").append("\n");
            builder.append("目标发送总数:").append(totalMsgCount).append("\n");
            builder.append("实际发送总数:").append(sendMsgCount).append("\n");
            builder.append("实际接收总数:").append(receiveMsgCount).append("\n");
            builder.append("实际发送大小:").append(sendMsgCount * singleMsgSize).append("\n");
            builder.append("实际接收大小:").append(receiveMsgCount * singleMsgSize).append("\n");
            long sendCostSTime = sendCostNaTime / 1000000000;
            long receiveCostSTime = receiveCostNaTime/1000000000;
            builder.append("发送耗时:").append(sendCostSTime).append("秒").append("\n");
            builder.append("接收耗时:").append(receiveCostSTime).append("秒").append("\n");
            builder.append("发送tps:").append(sendMsgCount / sendCostSTime).append("\n");
            builder.append("接收tps:").append(receiveMsgCount / receiveCostSTime).append("\n");
            builder.append("********************************").append("\n");
            return builder.toString();
        }
    }


    public TimeCostStat setSingleMsgSize(int singleMsgSize) {
        this.singleMsgSize = singleMsgSize;
        return this;
    }

    public TimeCostStat setTotalMsgCount(int totalMsgCount) {
        this.totalMsgCount = totalMsgCount;
        return this;
    }

    public void incrSendCount() {
        sendMsgCount.incrementAndGet();
    }

    public void incrReceiveCount() {
        receiveMsgCount.incrementAndGet();
    }

    private boolean isDone() {
        return (receiveMsgCount.get() == sendMsgCount.get()) && (totalMsgCount == sendMsgCount.get());
    }

    public TimeCostStat setStatIntervalSecond(int statIntervalSecond) {
        this.statIntervalSecond = statIntervalSecond;
        return this;
    }
}
