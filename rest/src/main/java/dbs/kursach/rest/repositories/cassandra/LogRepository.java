package dbs.kursach.rest.repositories.cassandra;

import dbs.kursach.rest.models.cassandra.Log;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LogRepository extends CassandraRepository<Log, UUID> {
        public Optional<Log> findById(UUID id);
}