package controllers;

import api.ReceiptResponse;
import dao.TagDao;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/tags/{tag}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagController {
    final TagDao tags;
    final ReceiptDao receipts;

    public TagController(TagDao tags, ReceiptDao receipts) {
        this.tags = tags;
        this.receipts = receipts;
    }

    @PUT
    public void toggleTag(@NotNull @PathParam("tag") String tagName, @NotNull Integer id)  {
        // if receipt doesn't exist
        if (!receipts.exists(id)) {
            throw new WebApplicationException("receipt id does not exist", Response.Status.NOT_FOUND);
        }

        // if receipt exists, check if tag exits.. if so, untag; if not, tag.
        if (tags.exists(tagName, id)) {
            tags.delete(tagName, id);
        } else {
            tags.insert(tagName,id);
        }

    }

    @GET
    public List<ReceiptResponse> getTags(@NotNull @PathParam("tag") String tagName) {
        System.out.println(tagName);
        // search through the tag table to identify all receiptID with same tagName
        List<Integer> receiptIDs = tags.getReceiptIdByTagName(tagName);

        // search through the receipt table to retrieve all the receipt with certain tag id
        List<ReceiptsRecord> ReceiptRecords = new ArrayList<ReceiptsRecord>();
        for (int id: receiptIDs) {
            ReceiptRecords.add(receipts.getReceiptFromID(id));
        }

        return ReceiptRecords.stream().map(ReceiptResponse::new).collect(toList());
    }

}
