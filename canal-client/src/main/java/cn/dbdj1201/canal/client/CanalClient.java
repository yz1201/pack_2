package cn.dbdj1201.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dbdj1201
 * @Date: 2020-09-09 10:02
 */
@Component
public class CanalClient {

    //执行的sql队列
    private Queue<String> SQL_QUEUE = new ConcurrentLinkedQueue<>();

    @Resource
    private DataSource dataSource;


    //客户端监听服务端情况 canal 入库方法
    public void run() {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("47.94.4.93", 11111),
                "example",
                "", "");
        int batchSize = 1000;
        try {
            connector.connect();
            connector.subscribe("db_canal\\..*");
            connector.rollback();

            try {
                while (true) {
                    Message message = connector.getWithoutAck(batchSize);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        TimeUnit.SECONDS.sleep(1);
                    } else {
                        dataHandle(message.getEntries());
                    }
                    connector.ack(batchId);

                    //当队列里面堆积的sql大于一定数值的时候就模拟执行
                    if (SQL_QUEUE.size() >= 1) {
                        executeQueueSql();
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private void executeQueueSql() {

    }

    private void dataHandle(List<CanalEntry.Entry> entries) throws InvalidProtocolBufferException {
        for (CanalEntry.Entry entry : entries) {
            if (CanalEntry.EntryType.ROWDATA == entry.getEntryType()) {
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                CanalEntry.EventType eventType = rowChange.getEventType();
                if (eventType == CanalEntry.EventType.DELETE) {
                    saveDeleteSql(entry);
                } else if (eventType == CanalEntry.EventType.UPDATE) {
                    saveUpdateSql(entry);
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    saveInsertSql(entry);
                }
            }
        }
    }

    private void saveInsertSql(CanalEntry.Entry entry) {

    }

    private void saveUpdateSql(CanalEntry.Entry entry) {

    }

    private void saveDeleteSql(CanalEntry.Entry entry) {
    }


}
