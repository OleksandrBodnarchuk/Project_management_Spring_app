package com.javawwa25.app.springboot.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectDto {

	@NotNull
    @NotEmpty
    private String name;
	@NotNull
    @NotEmpty
	private String info;
}
