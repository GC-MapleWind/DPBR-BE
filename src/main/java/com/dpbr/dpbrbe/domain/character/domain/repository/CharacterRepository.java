package com.dpbr.dpbrbe.domain.character.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpbr.dpbrbe.domain.character.domain.Character;

public interface CharacterRepository extends JpaRepository<Character, String> {

	Optional<Character> findByName(String name);

	List<Character> findAllByOrderByCombatPowerDesc();

	List<Character> findAllByOrderByLevelDesc();

	List<Character> findAllByOrderByUnionLevelDesc();
}
