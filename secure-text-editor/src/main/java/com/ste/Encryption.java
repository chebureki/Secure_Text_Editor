package com.ste;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/encrypt")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class Encryption {
    @POST
    public String encryptText(String plainText) {
        // Implement your encryption logic here
        String encryptedText = plainText+="hahaha!";
        return encryptedText;
    }
}
