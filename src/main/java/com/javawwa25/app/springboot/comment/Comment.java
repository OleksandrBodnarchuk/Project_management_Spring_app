package com.javawwa25.app.springboot.comment;

import com.javawwa25.app.springboot.user.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task_comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
	
	private long taskId;
	private long previousCommentId;
	private String comment;

	
	@Override
	public String toString() {
		return "Comment [taskId=" + taskId + ", previousCommentId=" + previousCommentId + ", comment="
				+ comment + "]";
	}
	
}
