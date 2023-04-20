package midterm.commerce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import midterm.commerce.api.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {}
