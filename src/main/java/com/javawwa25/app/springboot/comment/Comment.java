package com.javawwa25.app.springboot.comment;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Document("task_comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	
	@Id
    private String id;
	
	private long taskId;
	private long previousCommentId;
	private String comment;

	public Comment(long taskId, long previousCommentId, String comment) {
		this.taskId = taskId;
		this.previousCommentId = previousCommentId;
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", taskId=" + taskId + ", previousCommentId=" + previousCommentId + ", comment="
				+ comment + "]";
	}
	
}
