package ca.uqam.info.inf600g.services;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/greeter")
@Tag(name="Greeter resource")
@Produces(MediaType.APPLICATION_JSON)
public class HelloService {

    @GET
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = String.class))),
            description = "Returns greetings")
    public Set<String> getAllAccounts() {
        return new HashSet<String>(Arrays.asList(new String[]{
          "Hello, world!",
          "Hello, seb!"
        }));
    }
}
