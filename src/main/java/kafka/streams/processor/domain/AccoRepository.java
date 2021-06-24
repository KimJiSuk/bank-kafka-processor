package kafka.streams.processor.domain;

import kafka.streams.processor.entity.Acco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccoRepository extends JpaRepository<Acco, String> {
}
