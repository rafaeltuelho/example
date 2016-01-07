package estacionamento.persistence;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import estacionamento.entity.User;
import java.util.List;

/**
 *
 * @author 70744416353
 */
@PersistenceController
public class UserDAO extends JPACrud<User, Long> {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param cpf
     * @return
     */
    public User loadByCPF(String cpf) {
        String jpql = "SELECT u from " + this.getBeanClass().getSimpleName() + " u where u.cpf = :cpf";

        TypedQuery<User> query = getEntityManager().createQuery(jpql, User.class);
        query.setParameter("cpf", cpf);

        User result;
        try {
            result = query.getSingleResult();
        } catch (NoResultException cause) {
            result = null;
        }

        return result;
    }

    /**
     *
     * @param cpf
     * @return
     */
    public boolean existeCadastroParaCPF(String cpf) {
        String jpql = "SELECT u from " + this.getBeanClass().getSimpleName() + " u where u.cpf = :cpf";
        TypedQuery<User> query = getEntityManager().createQuery(jpql, User.class);
        query.setParameter("cpf", cpf);
        return !query.getResultList().isEmpty();
    }

    /**
     *
     * @return
     */
    public Long count() {
        return (Long) getEntityManager().createQuery("select COUNT(u) from User u").getSingleResult();
    }

    /**
     *
     * @param field
     * @param order
     * @param init
     * @param qtde
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<User> list(String field, String order, int init, int qtde) {
        return getEntityManager().createQuery("select u from User u ORDER BY " + field + " " + order).setFirstResult(init).setMaxResults(qtde).getResultList();
    }

    /**
     *
     * @param name
     * @return
     */
    public List<User> pesquisar(String name) {
        String jpql = "SELECT u FROM User u WHERE upper(u.name) like upper('%" + name.replaceAll(" ", "%") + "%')";
        return super.findByJPQL(jpql);
    }

}