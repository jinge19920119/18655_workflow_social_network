package controllers;

import java.util.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import models.ClimateService;
import models.ClimateServiceRepository;
import models.User;
import models.UserRepository;
import models.Workflow;
import models.WorkflowRepository;
import models.WorkflowSortByPopularity;
import play.mvc.Controller;
import play.mvc.Result;
import org.apache.commons.codec.binary.Base64;

@Named
@Singleton
public class WorkflowController extends Controller{

	private final WorkflowRepository workflowRepository;
	private final ClimateServiceRepository climateServiceRepository;
	private final UserRepository userRepository;

	// We are using constructor injection to receive a repository to support our
	// desire for immutability.
	@Inject
	public WorkflowController(final WorkflowRepository workflowRepository, ClimateServiceRepository climateServiceRepository,
			UserRepository userRepository) {
		this.workflowRepository = workflowRepository;
		this.climateServiceRepository = climateServiceRepository;
		this.userRepository = userRepository;
	}

	public Result getAllWorkflows(String format) {
		Iterable<Workflow> workflowIterable = workflowRepository.findAll();
		List<Workflow> workflowList = new ArrayList<Workflow>();
		for (Workflow workflow : workflowIterable) {
			workflowList.add(workflow);
		}
		String result = new String();
		if (format.equals("json")) {
			result = new Gson().toJson(workflowList);
		}
		return ok(result);
	}
	
	public Result getPopularWorkflows(String format) {
		Iterable<Workflow> workflowIterable = workflowRepository.findAll();
		List<Workflow> workflowList = new ArrayList<Workflow>();
		for (Workflow workflow : workflowIterable) {
			workflowList.add(workflow);
		}
		String result = new String();
		if (workflowList.size()==0){
			return ok(result);
		}
		Collections.sort(workflowList, new WorkflowSortByPopularity());
		List<Workflow> topList= new ArrayList<>();
		for(int i=0;i<3;i++){//get top three
			topList.add(workflowList.get(i));
		}
		result= new Gson().toJson(topList);
		return ok(result);
	}

	public Result getPageWorkflows(String format, int page, int size) {

		int pageStartFrom1 = page-1;
		Page<Workflow> workflowPage = workflowRepository.findAll(new PageRequest(pageStartFrom1, size));

		List<Workflow> workflowList = workflowPage.getContent();
		List<Workflow> newList= new ArrayList<>();
		
		int num= workflowList.size();
		int top=0;
		if(num!=0){
			for(int i=0;i<num;i++){
				if(workflowList.get(i).getPopularity()>workflowList.get(top).getPopularity())
					top=i;
			}
		}
		newList.add(workflowList.get(top));
		for(int i=0;i<num;i++){
			if(i!=top)
				newList.add(workflowList.get(i));
		}
		String workflows = null;
		if (format.equals("json")) {
			workflows = new Gson().toJson(newList);
		}

		return ok(workflows);
	}

	public Result getOneWorkflow(String format, long id) {
		List<Workflow> result = workflowRepository.findById(id);

		String workflow = null;
		if (format.equals("json")) {
			Workflow flow = result.get(0);
			flow.addPopularity();
			workflowRepository.save(flow);
			workflow = new Gson().toJson(flow);
		}

		return ok(workflow);
	}
	
	public Result getWorkflowByAuthorId(int id) {
		List<Workflow> result = workflowRepository.findByAuthorId(id);
		String workflow = new Gson().toJson(result);
		return ok(workflow);
	}

	public Result getNumEntry() {
		JsonObject rtn = new JsonObject();
		rtn.addProperty("numEntry", workflowRepository.count());
		return ok(rtn.toString());
	}

	public Result addWorkflow() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			System.out.println("Workflow not created, expecting Json data");
			return badRequest("Workflow not created, expecting Json data");
		}

		// Parse JSON file
		String author = json.path("author").asText();
		int authorId = json.path("authorId").asInt();
		String name = json.path("name").asText();
		String purpose = json.path("purpose").asText();
		Date createTime = new Date();
		String imageStr = json.path("image").asText();
		byte[] image = Base64.decodeBase64(imageStr);
		String input = json.path("input").asText();
		String output = json.path("output").asText();
		String contributors = json.path("contributors").asText();
		String linksInstructions = json.path("linksInstructions").asText();
		String versionNo = json.path("versionNo").asText();
		String dataset = json.path("dataset").asText();
		String otherWorkflows = json.path("otherWorkflows").asText();
	    String userIdSet = json.path("userSet").asText();
	    String climateServiceIdSet = json.path("climateServiceSet").asText();
	    int isQuestion = json.path("isQuestion").asInt();
	    int answerId = 0; //json.path("answerId").asInt();
	    
	    List<String> userIdList = Arrays.asList(userIdSet.split("\\s*,\\s*"));
	    List<String> climateServiceIdList = Arrays.asList(climateServiceIdSet.split("\\s*,\\s*"));

	    List<User> userSetList = new ArrayList<User>();
	    List<ClimateService> climateServiceSetList = new ArrayList<ClimateService>();

	    for (String userId : userIdList) {
	    	userSetList.add(userRepository.findOne(Long.parseLong(userId)));
	    }
	    for (String climateServiceId : climateServiceIdList) {
	    	climateServiceSetList.add(climateServiceRepository.findOne(Long.parseLong(climateServiceId)));
	    }

		try {
			if (workflowRepository.findByName(name).size()>0) {
				System.out.println("Workflow exist in database " + name);
				return badRequest("Workflow exist in database");
			}

			Workflow workflow = new Workflow(author, authorId, name, purpose, input, output, image, contributors, linksInstructions, createTime,
					versionNo, dataset, otherWorkflows, userSetList, climateServiceSetList,isQuestion, answerId) ;
			workflowRepository.save(workflow);
			System.out.println("Workflow saved: " + workflow.getId());
			return created(new Gson().toJson(workflow.getId()));
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			System.out.println("Workflow not saved: " + name);
			return badRequest("Workflow not saved: " + name);
		}
	}

	public Result deleteWorkflowById(long id) {
		Workflow workflow = workflowRepository.findOne(id);
		if (workflow == null) {
			System.out.println("Workflow not found with id: " + id);
			return notFound("Workflow not found with id: " + id);
		}

		workflowRepository.delete(workflow);
		System.out.println("Workflow is deleted: " + id);
		return ok("Workflow is deleted: " + id);
	}
	
	public Result markAnswer(){
		JsonNode json = request().body().asJson();
		if (json == null) {
			System.out.println("Workflow not created, expecting Json data");
			return badRequest("Workflow not created, expecting Json data");
		}
		
		long workflowId = json.path("workflowId").asLong();
		int commentId = json.path("commentId").asInt();
		Workflow w = workflowRepository.findOne(workflowId);
		if (w.getAnswerId() != commentId)
			w.setAnswerId(commentId);
		else
			w.setAnswerId(0);
		workflowRepository.save(w);
		return ok("Mark answer success");
	}
}
