package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.metadata.ClimateService;
import models.metadata.Workflow;
import models.metadata.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import util.APICall;
import util.APICall.ResponseType;
import views.html.climate.*;


public class UserProfileController extends Controller {
	
	public static Result getUserProfile(Long userID) {
		return ok(userProfile.render(User.one(userID)));
	}
	
	// public static Result home(int page) {
	// 	return ok(forum.render(Workflow.page(page), page, Workflow.getNumPage()));
	// }
	
	// public static Result workflow(long id) {
	// 	return ok(workflow.render(Workflow.one(id), null));
	// }
	
	// public static Result addWorkflow() {
	// 	return ok(addWorkflow.render(workflowForm));
	// }
	
	// public static Result newWorkflow() {
	// 	Form<Workflow> dc = workflowForm.bindFromRequest();
	// 	ObjectNode jsonData = Json.newObject();
	// 	try {

	// 		String originalName = dc.field("Name").value();
	// 		String newName = originalName.replace(' ', '-');

	// 		if (newName != null && !newName.isEmpty()) {
	// 			jsonData.put("name", newName);
	// 		}
	// 		jsonData.put("purpose", dc.field("Purpose").value());
	// 		jsonData.put("versionNo", dc.field("Version").value());
	// 		jsonData.put("rootWorkflowId", dc.field("Root_Service").value());
	// 		jsonData.put("userSet", dc.field("User_Set").value());
	// 		jsonData.put("climateServiceSet", dc.field("ClimateService_Set").value());

	// 		JsonNode response = Workflow.create(jsonData);
	// 		Application.flashMsg(response);
	// 	} catch (IllegalStateException e) {
	// 		e.printStackTrace();
	// 		Application.flashMsg(APICall
	// 				.createResponse(ResponseType.CONVERSIONERROR));
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 		Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
	// 	}
	// 	return redirect("/workflow/new/workflow");
	// }
}
