package com.example.licenta.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "studentDetails")
public class StudentDetails {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long executedHours;
	private Double normalGrade;
	private Double reTakeGrade;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
