package com.isa.projekcije.repository;

import com.isa.projekcije.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Friendship findById(Long id);

    Friendship findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<Friendship> findBySenderIdOrReceiverId(Long senderId, long receiverId);

    List<Friendship> findBySenderId(Long senderId);

    List<Friendship> findByReceiverId(Long receiverId);
}
