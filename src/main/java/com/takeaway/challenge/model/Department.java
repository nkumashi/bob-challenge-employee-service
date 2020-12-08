package com.takeaway.challenge.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

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
@Table(name = "department")
public class Department implements Serializable {
	private static final long serialVersionUID = 5175998636055429389L;

	@Id
	@Column(name = "id_department")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Department name must not be blank")
	@Column(name = "department_name")
	private String name = "";
	
	public Department(String name) {
		this.name = name;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        if (!(o instanceof Department )) {
        	return false;
        }
        return id != null && id.equals(((Department) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "Department details: Department name: " + this.name;
    }
}
