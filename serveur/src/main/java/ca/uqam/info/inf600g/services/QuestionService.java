package ca.uqam.info.inf600g.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.uqam.info.inf600g.model.Quiz;
import ca.uqam.info.inf600g.model.Question;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/questions")
@Tag(name="Question Management")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuestionService {

//	@GET
//	@ApiResponse(
//            responseCode = "200",
//            content = @Content(mediaType = "application/json",
//                    array = @ArraySchema(schema = @Schema(implementation = Question.class))),
//            description = "List of all available Questions")
//	public QuestionCollection getAllQuestions(){
//		return Quiz.getAccess().getAllQuestions();
//	}

	@GET
	@Path("/{id}")
	@ApiResponse(
			responseCode = "200",
			content = @Content(mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = Question.class))),
			description = "List of all available Questions")
	public Question getOnequestion(@PathParam("id") String id){
		if(Quiz.getAccess().containsKey(id)){
			return Quiz.getAccess().getOneQuestion(Integer.parseInt(id));
		}else{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

	}


}
