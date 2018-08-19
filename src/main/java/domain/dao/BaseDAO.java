package domain.dao;

import javax.persistence.EntityManager;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import org.hibernate.Session;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import domain.entity.BaseEntity;
import domain.exception.DAOException;
import domain.repository.Repository;

/**
 * Implementação das definições de persistencia utilizando tipos <b>genéricos</b>
 */
public abstract class BaseDAO<E extends BaseEntity> implements Repository<E>{	
	private Logger LOGGER = Logger.getLogger(getClass().getName());
	
	private EntityManager em;
	
	protected Class<E> clazz;
	
	protected EntityManager getEntityManager() {
		return em;
	}
	
	@PersistenceContext
	protected void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@PostConstruct
	public void postConstruct() {
		clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected Session getSession() {
		return (Session) getEntityManager().getDelegate();
	}
	

	public Optional<E> buscarPorID(String oid) {
		return Optional.ofNullable(getEntityManager().find(clazz, oid));		
	}
	

	public List<E> buscarTodos() {
		return getEntityManager().createQuery("from " + clazz.getName()).getResultList();
	}

	public E persist(E entity) throws DAOException{
		try {
			getEntityManager().persist(entity);
			getEntityManager().flush();
			return entity;
		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "Não foi possível persistir a entidade.\n", exception);
			throw new DAOException("Não foi possível persistir a entidade.".concat(exception.getMessage()));
		}
	}
	
	public E merge(E entity) throws DAOException{
		try {
			E persistedEntity = getEntityManager().merge(entity);
			getEntityManager().flush();
			return persistedEntity;
		} catch (PersistenceException exception) {
			LOGGER.log(Level.SEVERE, "Não foi possível realizar o merge na entidade.\n", exception);
			throw new DAOException("Não foi possível realizar o merge na entidade.".concat(exception.getMessage()));
		}
	}
	
	public void atualizar(E entity) {
		getEntityManager().refresh(entity);
	}
	

	public E salvar(E entity) throws DAOException{
		try {
			E persistedEntity = entity;
			
			if (entity.getID() == null || entity.getID().isEmpty()) {
				getEntityManager().persist(entity);
			} else {
				persistedEntity = getEntityManager().merge(entity);
			}
			getEntityManager().flush();
			return persistedEntity;
		} catch (PersistenceException exception) {
			LOGGER.log(Level.WARNING, "Não foi possível salvar a entidade.\n", exception);
			throw new DAOException("Não foi possível salvar a entidade.".concat(exception.getMessage()));
		}
	}
	
	public void deletar(E entity) throws DAOException {
		try {
			getEntityManager().remove(entity);
			getEntityManager().flush();
		} catch (PersistenceException exception) {
			LOGGER.log(Level.WARNING, "Não foi possível deletar a entidade.\n", exception);
			throw new DAOException("Não foi possível deletar a entidade.".concat(exception.getMessage()));
		}
	}
	
	
}
