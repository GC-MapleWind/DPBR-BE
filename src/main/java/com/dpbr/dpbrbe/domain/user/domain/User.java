package com.dpbr.dpbrbe.domain.user.domain;

import com.dpbr.dpbrbe.domain.shared.BaseTimeEntity;
import com.dpbr.dpbrbe.domain.shared.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotNull
	@Column(length = 100)
	private String name;

    @Column
    private String refreshToken;

	@NotNull
	@Column(length = 50)
	private String email;

	@NotNull
	@Column
	@Enumerated(EnumType.STRING)
	private Role role;

    @Column
    private String ocid;

	@Builder
	private User(String email, String name, Role role) {
		this.email = email;
		this.name = name;
		this.role = role;
	}

	public static User create(String email, String name) {
		return User.builder()
			.email(email)
			.name(name)
			.role(Role.USER)
			.build();
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void updateOcid(String ocid) {
		this.ocid = ocid;
	}
}

