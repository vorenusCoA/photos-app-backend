package com.example.photos.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	@EqualsAndHashCode.Include
	private UUID id;

	@NotNull
	@Column(unique = true)
	private String email;

	@NotNull
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USERS_ROLES",
			   joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			   inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

	public User(@NotNull String email, @NotNull String password) {
		this.email = email;
		this.password = password;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}
	
}
