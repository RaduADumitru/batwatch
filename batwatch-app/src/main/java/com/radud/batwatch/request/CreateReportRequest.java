package com.radud.batwatch.request;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateReportRequest(
        @Schema(description = "Title of the report", example = "Found bat in garden")
        @NotBlank(message = "Title must not be blank")
        String title,

        @Schema(description = "Description of the report with added details", example = "Saw a bat in my garden that could not fly. Could you please help?")
        @NotBlank(message = "Description must not be blank")
        String description,

        @ArraySchema(
                schema = @Schema(implementation = CreateLocationRequest.class),
                arraySchema = @Schema(
                        description = "List of relevant geographic locations for the report"
                )
        )
        @NotEmpty(message = "At least one location must be provided for the report")
        List<@Valid CreateLocationRequest> locations) {
}
