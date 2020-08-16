package ca.uqam.info.inf600g.services;
import java.util.Set;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import ca.uqam.info.inf600g.data.AidantCollection;
import ca.uqam.info.inf600g.model.Aidant;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@Path("/Aidant")
@Tag(name="Aidant Management")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AidantService {

    @GET
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Aidant.class))),
            description = "List of all available Aidants")
    public Set<Aidant> getAllAidants(){
        return AidantCollection.getAccess().getAllAidant();
    }

    @GET
    @Path("/{id}")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Aidant.class))),
            description = "Les informations d'un seul aidant.")
    public Aidant getUnAidant(@PathParam("id") String id){
        return AidantCollection.getAccess().getUnAidant(id);
    }
}
