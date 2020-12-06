package com.takeaway.challenge.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Naveen Kumashi
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements Serializable {
	private static final long serialVersionUID = -4040908041200864357L;

	@JsonIgnore
	@Id
	@Column(name = "id_employee", columnDefinition = "BINARY(16)")	
	@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;
	
	@NotBlank(message = "Email must not be blank")
	@Column(name = "email", unique = true)
	private String email;
	
	@NotBlank(message = "First name must not be blank")	
	@Column(name = "firstName")
	private String firstName;
	
	@NotBlank(message = "Last name must not be blank")
	@Column(name = "lastName")
	private String lastName;
	
	@NotNull(message = "Date of birth must not be null or invalid")
	@Column(name = "dob")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;
	
	@NotNull(message = "Department must not be null or invalid")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_department", referencedColumnName = "id_department", nullable = false)	
	private Department department;
	
	public Employee(
		String email,
		String firstName,
		String lastName,
		LocalDate dob,
		Department department
	) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.department = department;
	}
	
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        if (!(o instanceof Employee )) {
        	return false;
        }
        return id != null && id.equals(((Employee) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "Employee details: First name: " + this.firstName + ", Last name: " + this.lastName;
    }
}
