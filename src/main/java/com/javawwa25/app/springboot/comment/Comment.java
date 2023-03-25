package com.javawwa25.app.springboot.comment;

import com.javawwa25.app.springboot.task.Task;
import com.javawwa25.app.springboot.user.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment extends BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
    private Task task;
	private String comment;
	private Long authorId;
	private Long recipientId;
	
}
