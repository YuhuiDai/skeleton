package api;

import org.jooq.Configuration;
import org.h2.jdbcx.JdbcConnectionPool;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;
import java.math.BigDecimal;

import org.jooq.impl.DSL;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;


public class DaoTest {

    @Test
    public void testAddReceipt() {
        org.jooq.Configuration jooqConfig =new DefaultConfiguration();
        jooqConfig.set(SQLDialect.MYSQL);
        jooqConfig.set(JdbcConnectionPool.create("jdbc:h2:mem:test;MODE=MySQL;INIT=RUNSCRIPT from 'classpath:schema.sql'", "sa", "sa"));
        ReceiptDao receiptDao = new ReceiptDao(jooqConfig);
        receiptDao.insert("tory", BigDecimal.valueOf(11.2));
        receiptDao.insert("burch", BigDecimal.valueOf(33.4));
        List<ReceiptsRecord> receipts = receiptDao.getAllReceipts();

        Assert.assertEquals(receipts.size(), 2);
        Assert.assertEquals(receipts.get(0).getMerchant(), "tory");
        Assert.assertEquals(receipts.get(1).getMerchant(), "burch");
    }
}

