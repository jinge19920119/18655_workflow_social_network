package models;

import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Named
@Singleton
public interface CommentRepository extends CrudRepository<Comment, Long> {
	List<Comment> findByWorkflowid(long workflowid);
}

