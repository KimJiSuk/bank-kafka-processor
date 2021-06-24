package kafka.streams.processor.domain;

import kafka.streams.processor.entity.Tran;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranRepository extends JpaRepository<Tran, String> {
}
