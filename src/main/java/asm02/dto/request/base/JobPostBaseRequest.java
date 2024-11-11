package asm02.dto.request.base;

import asm02.entity.eJobType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
@SuperBuilder
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public abstract class JobPostBaseRequest {
    @NotNull(message = "{label.title} {validation.not-null}")
    @Size(min = 5, max = 255, message = "{label.title} {validation.size}")
    String title;
    @NotNull(message = "{label.job-description} {validation.not-null}")
    @Size(min = 5, max = 65535, message = "{label.job-description} {validation.size}")
    String description;
    @NotNull(message = "{label.yoe} {validation.not-null}")
    @Size(min = 1, max = 255, message = "{label.yoe} {validation.size}")
    String yoe;
    @NotNull(message = "{label.job-vacancies} {validation.not-null}")
    @Min(value = 1 , message = "{label.job-vacancies} {validation.min}")
    @Max(value = 255, message = "{label.job-vacancies} {validation.max}")
    Integer vacancies;
    @NotNull(message = "{label.address} {validation.not-null}")
    @Size(min = 3, max = 255, message = "{label.address} {validation.size}")
    String address;
    @NotNull(message = "{label.application-deadline} {validation.not-null}")
    @Future
    Date deadline;
    @NotNull(message = "{label.salary-range} {validation.not-null}")
    @Size(min = 3, max = 255, message = "{label.salary-range} {validation.size}")
    String salaryRange;
    @NotNull
    Long category;
    @NotNull
    eJobType type=eJobType.FULL_TIME;
}
