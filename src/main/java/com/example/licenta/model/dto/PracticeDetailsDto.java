package com.example.licenta.model.dto;

import com.example.licenta.model.GlobalDetails;
import com.example.licenta.model.Task;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PracticeDetailsDto {
	private GlobalDetails globalDetails;
	private List<Task> tasks;
	private Long executedHours;
	private Long remainingHours;
	private String coordonator;
}
