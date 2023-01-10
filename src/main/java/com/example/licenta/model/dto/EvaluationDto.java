package com.example.licenta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDto {
	private String evaluationCriteria;
	private Double normalGrade;
	private Double reTakeGrade;
}
