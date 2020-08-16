package ca.uqam.info.inf600g.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sun.research.ws.wadl.Response;

import java.io.Serializable;
// Permet d'ignorer les champs qu'on ne spécifie pas, tel que le média pour les questions normales non personnalisés.
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question implements Serializable, Cloneable {
	@JsonProperty("id")
	public int id;
	@JsonProperty("questionSentence")
	private String questionSentence;
	@JsonProperty("choixUn")
	private String choixUn;
	@JsonProperty("choixDeux")
	private String choixDeux;
	@JsonProperty("choixTrois")
	private String choixTrois;
	@JsonProperty("reponse")
	private String reponse;
	@JsonProperty("media")
	private String media;
	/*@JsonProperty("answer")
	public int answer;*/


	public Question(int id, String questionSentence, String choix_un, String choix_deux, String choix_trois, String bonne_reponse) {
		this.id = id;
		this.questionSentence = questionSentence;
		this.choixUn = choix_un;
		this.choixDeux = choix_deux;
		this.choixTrois = choix_trois;
		this.reponse = bonne_reponse;
		//this.answer = answer;
		this.media = null;
	}

	public Question(int id, String questionSentence, String choix_un, String choix_deux, String choix_trois, String bonne_reponse, String media) {
		this.id = id;
		this.questionSentence = questionSentence;
		this.choixUn = choix_un;
		this.choixDeux = choix_deux;
		this.choixTrois = choix_trois;
		this.reponse = bonne_reponse;
		this.media = media;
		//this.answer = answer;
	}
	public Question(){}


	public boolean VerifierReponse(String reponse) {
		if (reponse.isEmpty()) {
			return false;
		}
		if (reponse.equals(reponse)) {
			return true;
		}else {
			return false;
		}
	}

	public String getLabel() {
		return Integer.toString(this.id);
	}

	public void SetId(String id){
		this.id = Integer.parseInt(id);
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public String getReponse() {
		return this.reponse;
	}

	public String getQuestionSentence() {
		return this.questionSentence;
	}

	public void setQuestionSentence(String questionSentence){
		this.questionSentence = questionSentence;
	}

	public String getChoixUn() {
		return this.choixUn;
	}

	public void setchoixUn(String choixUn){
		this.choixUn = choixUn;
	}

	public String getChoixDeux() {
		return this.choixDeux;
	}
	public void setchoixDeux(String choixDeux){
		this.choixDeux = choixDeux;
	}
	public String getChoixTrois() {
		return this.choixTrois;
	}
	public void setChoixTrois(String choixTrois){
		this.choixTrois = choixTrois;
	}

	public String getMedia() {
		return this.media;
	}
	public void setMedia(String media){
		this.media = media;
	}
}
