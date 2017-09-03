package dao;

import api.ReceiptResponse;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;


public class TagDao {
    DSLContext dsl;

    public TagDao(Configuration jooqConfig) {this.dsl = DSL.using(jooqConfig);}

    public void insert(String tagName, Integer receiptID) {
        dsl
                .insertInto(TAGS, TAGS.NAME, TAGS.RECEIPTID)
                .values(tagName,receiptID)
                .execute();


    }

    public void delete(String tagName, Integer receiptID) {

        dsl.delete(TAGS)
                .where(TAGS.RECEIPTID.eq(receiptID))
                .and(TAGS.NAME.eq(tagName))
                .execute();

    }
    public boolean exists(String tagName, Integer receiptID) {

        List<Integer> result = dsl.selectFrom(TAGS)
                .where(TAGS.NAME.eq(tagName))
                .and(TAGS.RECEIPTID.eq(receiptID))
                .fetch(TAGS.ID);

        return (result.size()>0);

    }

    public List<Integer> getReceiptIdByTagName(String tagName) {
        return dsl.selectFrom(TAGS)
                .where(TAGS.NAME.eq(tagName))
                .fetch(TAGS.RECEIPTID);
    }

    public List<TagsRecord> getAllTags() {
        return dsl.selectFrom(TAGS).fetch();
    }

}