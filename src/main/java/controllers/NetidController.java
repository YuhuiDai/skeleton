package controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;

@Path("/netid")

public class NetidController {

    @GET
    public String getNetId() {
        return "yd229";
    }
}
