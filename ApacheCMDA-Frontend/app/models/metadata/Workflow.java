package models.metadata;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import play.libs.Json;
import util.APICall;
import util.Constants;

public class Workflow {

	private static final int PAGESIZE = 10;//There is a hardcoded pagesize in backend routes file
	private static final String GET_ALL_WORKFLOW_CALL = Constants.NEW_BACKEND+"workflow/getAllWorkflows/json";
	private static final String GET_PAGE_WORKFLOW_CALL = Constants.NEW_BACKEND+"workflow/getPageWorkflows/json";
	private static final String GET_NUM_ENTRY = Constants.NEW_BACKEND+"workflow/getNumEntry";
	private static final String GET_ONE_WORKFLOW_CALL = Constants.NEW_BACKEND+"workflow/getOneWorkflow/id/";
	private static final String ADD_WORKFLOW_CALL = Constants.NEW_BACKEND+"workflow/newWorkflow";
    private static final String MARK_ANSWER = Constants.NEW_BACKEND+"workflow/markAnswer";
    private static final String GET_POPULAR_WORKFLOW = Constants.NEW_BACKEND+ "workflow/getPopularWorkflows/json";
	private long id;
	private String name;
	private String purpose;
	private String author;
	private int authorId;
	private String input;
	private String output;
	private int popularity;
	private byte[] image;
	private String contributors;
	private String linksInstructions;
	private String createTime;
	private String versionNo;
	private List<String> datasetList;
	private List<String> otherWorkflowsList;
	private List<String> climateServiceSetList;
	private int isQuestion;
	private int answerId;
	
	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public int getIsQuestion() {
		return isQuestion;
	}

	public void setIsQuestion(int isQuestion) {
		this.isQuestion = isQuestion;
	}

	public List<String> getClimateServiceSetList() {
		return climateServiceSetList;
	}

	public void setClimateServiceSetList(List<String> climateServiceSetStr) {
		this.climateServiceSetList = new ArrayList<String>();
		this.climateServiceSetList.addAll(climateServiceSetStr);
	}
	
	public List<String> getDatasetList() {
		return datasetList;
	}

	public void setDatasetList(List<String> datasetList) {
		this.datasetList = new ArrayList<String>();
		this.datasetList.addAll(datasetList);
	}

	public List<String> getOtherWorkflowsList() {
		return otherWorkflowsList;
	}

	public void setOtherWorkflowsList(List<String> otherWorkflowsList) {
		this.otherWorkflowsList = new ArrayList<String>();
		this.otherWorkflowsList.addAll(otherWorkflowsList);
	}
	
	public void setPopularity(int popularity){
		this.popularity= popularity;
	}
	
	public int getPopularity(){
		return this.popularity;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getContributors() {
		return contributors;
	}

	public void setContributors(String contributors) {
		this.contributors = contributors;
	}

	public String getLinksInstructions() {
		return linksInstructions;
	}

	public void setLinksInstructions(String linksInstructions) {
		this.linksInstructions = linksInstructions;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public static List<Workflow> all() {
		List<Workflow> workflows = new ArrayList<Workflow>();
		JsonNode workflowsNode = APICall.callAPI(GET_ALL_WORKFLOW_CALL);
		if (workflowsNode == null || workflowsNode.has("error")
				|| !workflowsNode.isArray()) {
			return workflows;
		}
		
		for (int i = 0; i < workflowsNode.size(); i++) {
			JsonNode json = workflowsNode.path(i);
			Workflow newWorkflow = new Workflow();
			newWorkflow.setId(json.path("id").asLong());
			newWorkflow.setName(json.get("name").asText());
			newWorkflow.setPurpose(json.path("purpose").asText());
			
			newWorkflow.setCreateTime(json.path("createTime").asText());
			newWorkflow.setVersionNo(json.path("versionNo").asText());
			workflows.add(newWorkflow);
		}
		return workflows;
	}
	
	public static List<Workflow> popular(){
		List<Workflow> workflows = new ArrayList<Workflow>();
		JsonNode workflowsNode = APICall.callAPI(GET_POPULAR_WORKFLOW);
		if (workflowsNode == null || workflowsNode.has("error")
				|| !workflowsNode.isArray()) {
			return workflows;
		}
		for (int i = 0; i < workflowsNode.size(); i++) {
			JsonNode json = workflowsNode.path(i);
			Workflow newWorkflow = new Workflow();
			newWorkflow.setId(json.path("id").asLong());
			newWorkflow.setName(json.get("name").asText());
			newWorkflow.setPurpose(json.path("purpose").asText());
			newWorkflow.setAuthor(json.path("author").asText());
			newWorkflow.setCreateTime(json.path("createTime").asText());
			newWorkflow.setVersionNo(json.path("versionNo").asText());
			workflows.add(newWorkflow);
		}
		return workflows;
	}
	
	public static List<Workflow> page(int page) {
		List<Workflow> workflows = new ArrayList<Workflow>();
		JsonNode workflowsNode;
		workflowsNode = APICall.callAPIParameter(GET_PAGE_WORKFLOW_CALL,"page",String.valueOf(page));
		if (workflowsNode == null || workflowsNode.has("error")
				|| !workflowsNode.isArray()) {
			return workflows;
		}
		
		for (int i = 0; i < workflowsNode.size(); i++) {
			JsonNode json = workflowsNode.path(i);
			Workflow newWorkflow = new Workflow();
//			newWorkflow.setId(json.path("id").asLong());
//			newWorkflow.setName(json.get("name").asText());
//			newWorkflow.setPurpose(json.path("purpose").asText());
//			newWorkflow.setAuthor(json.path("author").asText());
//			newWorkflow.setAuthorId(json.path("authorId").asInt());
//			newWorkflow.setCreateTime(json.path("createTime").asText());
//			newWorkflow.setVersionNo(json.path("versionNo").asText());
//			newWorkflow.setIsQuestion(json.path("isQuestion").asInt());
			newWorkflow = new Gson().fromJson(json.toString(), Workflow.class);
			workflows.add(newWorkflow);
		}
		return workflows;
	}
	
	public static Workflow one(long id) {
		JsonNode json;
		json = APICall.callAPI(GET_ONE_WORKFLOW_CALL+String.valueOf(id));
		if (json == null || json.has("error")
				|| json.isArray()) {
			return null;
		}
//		Workflow newWorkflow = new Workflow();
//		newWorkflow.setId(json.path("id").asText());
//		newWorkflow.setName(json.get("name").asText());
//		newWorkflow.setPurpose(json.path("purpose").asText());
//		newWorkflow.setInput(json.get("input").asText());
//		newWorkflow.setOutput(json.get("output").asText());
//		newWorkflow.setContributors(json.get("contributors").asText());
//		newWorkflow.setLinksInstructions(json.get("linksInstructions").asText());
//		newWorkflow.setCreateTime(json.path("createTime").asText());
//		newWorkflow.setVersionNo(json.path("versionNo").asText());
		Workflow newWorkflow = null;
		newWorkflow = new Gson().fromJson(json.toString(), Workflow.class);
		
		List<String> list = new ArrayList<String>();
		for (int i= 0; i < json.path("climateServiceSet").size();i++)
			list.add(json.path("climateServiceSet").get(i).path("name").asText());
		newWorkflow.setClimateServiceSetList(list);

		String datasets = json.path("dataset").asText();
		list = new ArrayList<String>();
		for (String set : datasets.split(";"))
			list.add(set);
		newWorkflow.setDatasetList(list);
		
		String otherWorkflows = json.path("otherWorkflows").asText();
		list = new ArrayList<String>();
		for (String workflow:otherWorkflows.split(";"))
			list.add(workflow);
		newWorkflow.setOtherWorkflowsList(list);
		return newWorkflow; // newWorkflow
	}
	
	public static JsonNode create(JsonNode jsonData) {
		return APICall.postAPI(ADD_WORKFLOW_CALL, jsonData);
	}
	
	public static int getNumPage() {
		JsonNode node = APICall.callAPI(GET_NUM_ENTRY);
		int numPage = (int) Math.ceil((double)node.path("numEntry").asLong() / PAGESIZE);
		return numPage;
	}

	public static void markAnswer(int workflowId, int commentId) {
		ObjectNode jsonData = Json.newObject(); 
        jsonData.put("workflowId", workflowId);
        jsonData.put("commentId" , commentId);
        
        System.out.println(jsonData.toString());
        APICall.postAPI(Workflow.MARK_ANSWER, jsonData);
	}
	
}
