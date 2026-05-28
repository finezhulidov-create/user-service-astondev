package com.zhulidov.user_service_astondev.dao;

import com.zhulidov.user_service_astondev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
