package com.javawwa25.app.springboot.web.dto;

import java.time.LocalDate;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectDto {
	@Nullable
	private long id;
	@NotNull
    @NotEmpty
    private String name;
	@NotNull
    @NotEmpty
	private String info;
	@Nullable
	private LocalDate createdAt;
}
