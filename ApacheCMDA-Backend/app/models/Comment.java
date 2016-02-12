package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String username;
	private long userid;
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
		this.userid = userid;
		this.username = username;
		this.replytoid = replytoid;
		this.workflowid = workflowid;
		this.comment = comment;
		this.date = date;
        this.replytoname = replytoname;
	}

    public long getId() {
        return id;
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

    @Override
    public String toString() {
    	return "Comment";
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }
}