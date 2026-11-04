package Lab13.psu.edu.lab13.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Product(
	@Id
	@Positive(message = "Product id must be positive") Integer id,
	@NotBlank(message = "Product name is required") String name,
	@NotBlank(message = "Product description is required") String description,
	@NotNull(message = "Product price is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than zero") BigDecimal price
) {}