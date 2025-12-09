package com.paylink.user.infrastructure.persistance;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
	    name = "contacts",
	    uniqueConstraints = {
	        @UniqueConstraint(
	            name = "unique_contact",
	            columnNames = {"user_id", "contact_user_id"} //Esto hace que no puedas repetir un mismo contacto en tu lista
	        )
	    },
	    indexes = { //Esto sirve para agilizar las busquedas SQL en vez de hacer una relacion JPA normal
	        @Index(name = "idx_user_id", columnList = "user_id"),
	        @Index(name = "idx_contact_user_id", columnList = "contact_user_id")
	    }
	)
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;
	private Long contactUserId;
	private LocalDateTime addedAt;
}
