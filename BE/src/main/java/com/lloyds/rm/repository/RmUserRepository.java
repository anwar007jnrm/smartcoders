package com.lloyds.rm.repository;

import com.lloyds.rm.entity.RmUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RmUserRepository extends JpaRepository<RmUser, Long> {
    Optional<RmUser> findByRmidAndPassword(String rmid, String password);

}
