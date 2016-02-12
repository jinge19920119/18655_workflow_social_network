package models.metadata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

import util.APICall;
import util.Constants;

@Entity
public class Comment {
    
    private static final String GET_COMMENT_BY_WORKFLOWID = Constants.NEW_BACKEND+"comment/getbyworkflowid/";
    private static final String ADD_COMMENT_CALL = Constants.NEW_BACKEND+"comment/add";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long userid;
    private String username;
    private long replytoid;
    private String replytoname;
    private long workflowid;
    //@Column(columnDefinition="TEXT")
    private String comment;
    private Date date;

    public Comment() {
    }

    public Comment(String username, long userid,
        long replytoid, String replytoname, long workflowid, String comment, Date date) {
        super();
        this.username = username;
        this.setUserid(userid);
        this.replytoid = replytoid;
        this.workflowid = workflowid;
        this.comment = comment;
        this.date = date;
        this.replytoname = replytoname;
    }

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getReplytoid() {
        return replytoid;
    }

    public void setReplytoid(long replytoid) {
        this.replytoid = replytoid;
    }

    public String getReplytoname() {
        return replytoname;
    }

    public void setReplytoname(String replytoname) {
        this.replytoname = replytoname;
    }

    public long getWorkflowid() {
        return workflowid;
    }

    public void setWorkflowid(long workflowid) {
        this.workflowid = workflowid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Comment";
    }
    
    public static List<Comment> getComment(long id) {
        List<Comment> comments = new ArrayList<Comment>();
        JsonNode commentsNode = APICall.callAPI(GET_COMMENT_BY_WORKFLOWID + id);
        if (commentsNode == null || !commentsNode.isArray()) {
            System.out.println("received no comments");
            return comments;
        }
        for (int i = 0; i < commentsNode.size(); i++) {
            JsonNode json = commentsNode.path(i);
            Comment comment = new Comment();
            comment = new Gson().fromJson(json.toString() , Comment.class);
            comments.add(comment);
        }
        return comments;
    }
    
    public static void addComment(JsonNode jsonData) {
        APICall.postAPI(ADD_COMMENT_CALL, jsonData);
    }
}
