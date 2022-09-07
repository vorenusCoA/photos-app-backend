package com.example.photos.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PHOTOS")
@Getter
@Setter
@NoArgsConstructor
public class Photo {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@NotNull
	private String name;

	@Lob
	@NotNull
	private byte[] data;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable = false)
    private User user;

	public Photo(@NotNull String name,@NotNull byte[] data,@NotNull User user) {
		this.name = name;
		this.data = data;
		this.user = user;
	}

}
