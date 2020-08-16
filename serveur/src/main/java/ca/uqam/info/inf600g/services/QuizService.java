package ca.uqam.info.inf600g.services;
import java.io.*;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.uqam.info.inf600g.data.PersonalQuizCollection;
import ca.uqam.info.inf600g.model.Question;
import ca.uqam.info.inf600g.model.Quiz;
import ca.uqam.info.inf600g.data.QuizCollectives;
import ca.uqam.info.inf600g.model.QuizPersonnel;
import com.sun.jersey.multipart.FormDataParam;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@Path("/quizz")
@Tag(name="Quiz Management")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuizService {

    @GET
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Quiz.class))),
            description = "List of all available Quiz")
    public ArrayList<Quiz> getAllQuiz(){return QuizCollectives.getAccess().getCollectiveQuizs();
    }
    @GET
    @Path("/personal")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Quiz.class))),
            description = "List of all available personal Quiz")
    public ArrayList<QuizPersonnel> getPersonalQuiz(){return PersonalQuizCollection.getAccess().getPersonalQuizs();
    }
    @GET
    @Path("/personal/{resident_name}")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Quiz.class))),
            description = "Get the list of the resident personal Quiz")
    public ArrayList<QuizPersonnel> getPersonalQuiz(@PathParam("resident_name") String resident_name){return PersonalQuizCollection.getAccess().getQuizByName(resident_name);
    }


    @GET
    @Path("/{id}")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Quiz.class))),
            description = "A specific Quiz")
    public Quiz getQuizById(@PathParam("id") String id){
        if(Quiz.getAccess().containsKey(id)){
            return QuizCollectives.getAccess().getQuizById( Integer.parseInt(id));
        }else{
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

    }

    @POST
    @Path("/ajouterQuiz/{id}")
    @Consumes("application/json;charset=UTF-8")
    public void ajouterQuestionPersonelle(QuizPersonnel quiz, @PathParam("id") String id){
        //System.out.println(question.getQuestionSentence() + " " + question.getChoixUn() + " " + question.getMedia());
        //PersonalQuizCollection.getAccess().ajouterQuestionPersonnel(Integer.parseInt(id), question);
        quiz.id = PersonalQuizCollection.getAccess().getPersonalQuizs().size();
        PersonalQuizCollection.getAccess().ajouterQuizPersonnel(Integer.parseInt(id),quiz);
    }

    @POST
    @Path("/uploadAudio/{Nom}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String ajouterAudio(@FormDataParam("file") InputStream uploadedInputStream,
                               @PathParam("Nom") String nom){
        String UPLOAD_FOLDER = "D:\\Users\\btaou\\Documents\\test\\projet-discord-squad\\serveur\\static\\";
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

    }

    private void createFolderIfNotExists(String dirName)
            throws SecurityException {
        File theDir = new File(dirName);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
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
}
