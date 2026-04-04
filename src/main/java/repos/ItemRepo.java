package repos;

import model.Item;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ItemRepo implements PanacheRepository<Item> {

    public List<Item> findAllItems() {
        return listAll();
    }

    public Optional<Item> findItemById(Long id) {
        return findByIdOptional(id);
    }

    //SEARCH METHS

    public List<Item> searchByTitle(String title) {
        return find("lower(title) like lower(?1)", "%" + title + "%").list();
    }

    public List<Item> searchByCategory(String category) {
        return find("lower(category) like lower(?1)", "%" + category + "%").list();
    }

    public List<Item> searchByManufacturer(String manufacturer) {
        return find("lower(manufacturer) like lower(?1)", "%" + manufacturer + "%").list();
    }

    //SORTING METHS


    public void saveItem(Item item) {
        persist(item);
    }

    public boolean deleteItemById(Long id) {
        return deleteById(id);
    }
}