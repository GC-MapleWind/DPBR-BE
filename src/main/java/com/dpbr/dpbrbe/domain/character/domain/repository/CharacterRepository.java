package com.dpbr.dpbrbe.domain.character.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpbr.dpbrbe.domain.character.domain.Character;

public interface CharacterRepository extends JpaRepository<Character, String> {
}
