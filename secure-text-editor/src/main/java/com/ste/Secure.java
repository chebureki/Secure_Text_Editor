package com.ste;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/secure")
public class Secure {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String secure() {
        return "this is secure";
    }
}
