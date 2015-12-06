package repository;

import org.springframework.data.repository.CrudRepository;

import entity.SqlConfig;

public interface SqlConfigRepository extends CrudRepository<SqlConfig, Long> {

}