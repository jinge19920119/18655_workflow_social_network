package controllers;

import java.io.File;
import java.nio.file.Files;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.metadata.Workflow;
import models.metadata.Comment;
import play.data.Form;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import util.APICall;
import util.APICall.ResponseType;
import views.html.climate.*;

public class WorkflowController extends Controller {

	final static Form<Workflow> workflowForm = Form.form(Workflow.class);

	public static Result home(int page) {
		return ok(forum.render(Workflow.page(page), page, Workflow.getNumPage()));
	}

	public static Result workflow(long id) {
		return ok(workflow.render(Workflow.one(id), Comment.getComment(id)));
	}

	public static Result addWorkflow() {
		return ok(addWorkflow.render(workflowForm));
	}
	
	public static Result popular(){
		return ok(views.html.climate.popular.render(Workflow.popular()));
	}

	public static Result newWorkflow() {
		Form<Workflow> dc = workflowForm.bindFromRequest();
		ObjectNode jsonData = Json.newObject();
		try {
			jsonData.put("author", session("userName"));
			jsonData.put("authorId", session("id"));
			jsonData.put("isQuestion", dc.field("isQuestion").value());
			jsonData.put("name", dc.field("Name").value());
			jsonData.put("purpose", dc.field("Purpose").value());
			jsonData.put("input", dc.field("Input").value());
			jsonData.put("output", dc.field("Output").value());
			jsonData.put("contributors", dc.field("Contributors").value());
			jsonData.put("linksInstructions", dc.field("Links_and_Instructions").value());
			jsonData.put("versionNo", dc.field("Version").value());
			jsonData.put("dataset", dc.field("Dataset").value());
			jsonData.put("otherWorkflows", dc.field("OtherWorkflows").value());
			jsonData.put("userSet", dc.field("User_Set").value());
			jsonData.put("climateServiceSet", dc.field("ClimateService_Set").value());

	        MultipartFormData body = request().body().asMultipartFormData();
	        File image = body.getFile("Image").getFile();
			byte[] imageBytes = Files.readAllBytes(image.toPath());
	        jsonData.put("image", new String(Base64.encodeBase64(imageBytes)));
			JsonNode response = Workflow.create(jsonData);
			Application.flashMsg(response);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			Application.flashMsg(APICall
					.createResponse(ResponseType.CONVERSIONERROR));
		} catch (Exception e) {
			e.printStackTrace();
			Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
		}
		return redirect("/workflow/new/workflow");
	}
	
    public static Result getImage(long id) {
        Workflow tmp = Workflow.one(id);
        return ok(tmp.getImage()).as("image/png");
    }

    public static Result newComment() {
	    DynamicForm df = Form.form().bindFromRequest();
	    ObjectNode jsonData = Json.newObject();
	    try {
	        jsonData.put("username", df.get("username"));
	        jsonData.put("userid" , df.get("userid"));
	        jsonData.put("replytoid" , df.get("replytoid"));
	        jsonData.put("workflowid" , df.get("workflowid"));
	        jsonData.put("replytoname", df.get("replytoname"));
	        System.out.println("workflow id to add: "+ df.get("workflowid"));
	        jsonData.put("comment" , df.get("comment"));
	        Comment.addComment(jsonData);
	    } catch (IllegalStateException e) {
	        e.printStackTrace();
	        Application.flashMsg(APICall
                    .createResponse(ResponseType.CONVERSIONERROR));
	    } catch (Exception e) {
            e.printStackTrace();
            Application.flashMsg(APICall.createResponse(ResponseType.UNKNOWN));
        }
	    return redirect("/workflow/" + df.get("workflowid"));
	}
    
    public static Result markAnswer(int workflowId, int commentId) {
        Workflow.markAnswer(workflowId, commentId);
        return redirect("/workflow/" + workflowId);
    }
}
