package ca.uqam.info.inf600g.services;

import ca.uqam.info.inf600g.data.ResidentCollection;
import ca.uqam.info.inf600g.model.FileManager;
import ca.uqam.info.inf600g.model.Resident;
//import com.sun.jersey.multipart.FormDataParam;


import ca.uqam.info.inf600g.model.Resultat;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Set;


@Path("/residents")
@Tag(name="Resident Management")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResidentService {
    private static final String UPLOAD_FOLDER = "/Users/vorolf/Documents/Course/SessionLive/logicielAdapte/application/projet-discord-squad/serveur/static/";

    @GET
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resident.class))),
            description = "List of all Resident")
    public Set<Resident> getAllresident(){
        return ResidentCollection.getAccess().getAllResident();
    }

    @PUT
    @Path("/reponse/{user_id}/{Quiz_id}/{Question_id}/{answer}")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resident.class))),
            description = "Sauvegarde d'une reponse dans la structure de donnee resident")


        public Set<Resident> setAnswer(@PathParam("user_id") String user_id,@PathParam("Quiz_id") String Quiz_id,@PathParam("Question_id") String Question_id,@PathParam("answer") String answer){
        boolean contains =  ResidentCollection.getAccess().containsKey(user_id);
        if (contains){
            ResidentCollection.getAccess().addAnswerToQuiz(user_id,Quiz_id,Question_id,answer);
            return ResidentCollection.getAccess().getAllResident();
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Path("/tremblements/{user_id}/{avg}/{temps}")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resident.class))),
            description = "donnees concernants les tremblements d'un resident")


    public Set<Resident> addTremblingData(@PathParam("user_id") String user_id,@PathParam("avg") String avg,@PathParam("temps") String temps){
        boolean contains =  ResidentCollection.getAccess().containsKey(user_id);
        if (contains){
            ResidentCollection.getAccess().addTremblingData(user_id,avg,temps);
            return ResidentCollection.getAccess().getAllResident();
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Path("/miniGame_Score/{user_id}/{score}")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resident.class))),
            description = "met a jour le score du mini jeu d'un resident")


    public Resident updateUserScore(@PathParam("user_id") String user_id,@PathParam("score") int score){
        boolean contains =  ResidentCollection.getAccess().containsKey(user_id);
        if (contains){
            ResidentCollection.getAccess().updateUserGameScore(user_id,score);
            return ResidentCollection.getAccess().getOneResident(user_id);
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Path("/miniGame_Score/{user_id}")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resident.class))),
            description = "retourne le score du mini jeu du resident")


    public int getUserScore(@PathParam("user_id") String user_id){
        boolean contains =  ResidentCollection.getAccess().containsKey(user_id);
        if (contains){
            return ResidentCollection.getAccess().getUserGameScore(user_id);
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Path("/bonnereponse/{id}")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resident.class))),
            description = "Ajout d'une bonne Réponse a un Résident.")
    public Set<Resident> ajouterBonneReponse(@PathParam("id") String id){
        System.out.println(id);
        boolean contains =  ResidentCollection.getAccess().containsKey(id);
        if (contains){
            ResidentCollection.getAccess().ajouterBonneReponse(id);
            return ResidentCollection.getAccess().getAllResident();
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @PUT
    @Path("/mauvaisereponse/{id}")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resident.class))),
            description = "Ajout d'une mauvaise réponse a un Résident.")
    public Set<Resident> ajouterMauvaiseReponse(@PathParam("id") String id){
        System.out.println(id);
        boolean contains =  ResidentCollection.getAccess().containsKey(id);
        if (contains){
            ResidentCollection.getAccess().ajouterMauvaiseReponse(id);
            return ResidentCollection.getAccess().getAllResident();
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @GET
    @Path("/{id}")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resident.class))),
            description = "Retourne un résident spécifié par l'id.")
    public Resident getOneResident(@PathParam("id") String id){
        if (ResidentCollection.getAccess().containsKey(id)) {
            return ResidentCollection.getAccess().getOneResident(id);
        }else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Path("/adaptationAlzheimer/{id}")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resident.class))),
            description = "Retourne l'état de l'adaption de l'alzheimer qui cache les mauvaises réponses.")
    public boolean getAdaptationAlzheimer(@PathParam("id") String id){
        boolean contains =  ResidentCollection.getAccess().containsKey(id);
        if (contains){
            return ResidentCollection.getAccess().getOneResident(id).getAdaptationAlzheimer();
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Path("/adaptationAlzheimer/{id}")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resident.class))),
            description = "Mdofier l'activation de l'adaptation des mauvaises réponses qui disparaissent. ")
    public void modifierAdaptationAlzheimer(@PathParam("id") String id){
        boolean contains =  ResidentCollection.getAccess().containsKey(id);
        if (contains){
            ResidentCollection.getAccess().ajouterBonneReponse(id);
            ResidentCollection.getAccess().getOneResident(id).modifierAdaptionAlzheimer();
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }





    @PUT
    @Path("/adaptationTailleTexte/{id}")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resident.class))),
            description = "Mdofier l'activation de l'adaptation de la taille du texte des questions.")
    public void modifierAdaptationTexte(@PathParam("id") String id){
        boolean contains =  ResidentCollection.getAccess().containsKey(id);
        if (contains){
            ResidentCollection.getAccess().ajouterBonneReponse(id);
            ResidentCollection.getAccess().getOneResident(id).modifierAdaptationTexte();
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Path("/adaptationTailleTexte/{id}")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Resident.class))),
            description = "Retourne l'état de l'adaptation de la taille du texte des questions.")
    public boolean getAdaptationTexte(@PathParam("id") String id){
        boolean contains =  ResidentCollection.getAccess().containsKey(id);
        if (contains){
            System.out.println("LE BOOLEAN DE ADAPTATION TEXTE:" + id + " "+ ResidentCollection.getAccess().getOneResident(id).getAdaptationTexte());
            return ResidentCollection.getAccess().getOneResident(id).getAdaptationTexte();
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
/*
    @POST
    @Path("/uploadAudio/{Nom}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String ajouterAudio(@FormDataParam("file") InputStream uploadedInputStream,
                               @PathParam("Nom") String nom){
        System.out.println("ICI DANS EL UPLOAD");
        if (uploadedInputStream == null || nom == null){
            System.out.println("Invalid form data");
            return Response.status(400).entity("Invalid form data").build().toString();}
        try {
            createFolderIfNotExists(UPLOAD_FOLDER);
        } catch (SecurityException se) {
            System.out.println("Can not create destination folder on server");
            return Response.status(500)
                    .entity("Can not create destination folder on server")
                    .build().toString();
        }
        String uploadedFileLocation = UPLOAD_FOLDER + nom;
        try {
            saveToFile(uploadedInputStream, uploadedFileLocation);
        } catch (IOException e) {
            System.out.println("Can not save file");
            return Response.status(500).entity("Can not save file").build().toString();
        }
        System.out.println("File saved to " + uploadedFileLocation);
        return Response.status(200)
                .entity("File saved to " + uploadedFileLocation).build().toString();

    }*/

    @POST
    @Path("/uploadImg")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImgFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("Resident_name") String Resident_name,
            @FormDataParam("PersonalQuiz_id") String PersonalQuiz_id,
            @FormDataParam("Question_id") String Question_id) {
        System.out.println("uploading img");

        System.out.println(Resident_name);
        System.out.println(Question_id);

        String fileLocation = UPLOAD_FOLDER +"/"+Resident_name+"/"+"PersonalQuiz"+PersonalQuiz_id+"/Question"+Question_id+"/"+fileDetail.getFileName();
        File directory = new File(UPLOAD_FOLDER +"/"+Resident_name+"/"+"PersonalQuiz"+PersonalQuiz_id+"/Question"+Question_id+"/");

        FileManager manager=new FileManager();
        return Response.status(200).entity( manager.writeToFile(directory,fileLocation,uploadedInputStream)).build();
    }

    private void saveToFile(InputStream inStream, String target)
            throws IOException {
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[100024];
        out = new FileOutputStream(new File(target));
        while ((read = inStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    }


    private void createFolderIfNotExists(String dirName)
            throws SecurityException {
        File theDir = new File(dirName);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }

    @PUT
    @Path("/ajouteMauvaiseReponsePersonnel/{id}")
    public void ajouterMauvaiseReponsePersonnel(@PathParam("id") String id){
        ResidentCollection.getAccess().ajouterMauvaiseReponseQuizPersonnel(id);
    }

    @PUT
    @Path("/ajouteBonneReponsePersonnel/{id}")
    public void ajouterBonneReponsePersonnel(@PathParam("id") String id){
        ResidentCollection.getAccess().ajouterBonneReponseQuizPersonnel(id);
    }

   @GET
   @Path("/resultatPersonelle/{id}")
   public Resultat getResultat(@PathParam("id") String id){
        System.out.println(ResidentCollection.getAccess().getOneResident(id));
        return ResidentCollection.getAccess().getOneResident(id).getResultatPersonnel();
   }


}
