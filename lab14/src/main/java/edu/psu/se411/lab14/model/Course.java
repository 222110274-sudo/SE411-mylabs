package edu.psu.se411.lab14.model;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record Course(
		@Id
		@PositiveOrZero(message = "id cannot be negative")
		Long id,

		@NotBlank(message = "code is required")
		String code,

		@NotNull(message = "name is required")
		@Size(min = 2, message = "name must be at least 2 characters")
		String name,

		String description,

		@Min(value = 1, message = "credits must be between 1 and 10")
		@Max(value = 10, message = "credits must be between 1 and 10")
		Integer credits
		) {
}
