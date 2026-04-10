package repos;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import model.Review;

@ApplicationScoped
public class ReviewRepo implements PanacheRepository<Review> {
}