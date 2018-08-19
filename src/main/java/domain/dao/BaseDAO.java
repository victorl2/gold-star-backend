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
import domain.repository.Repository;


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

	public E persist(E entity) {
		try {
			getEntityManager().persist(entity);
			getEntityManager().flush();
			return entity;
		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "N�o foi poss�vel persistir a entidade.\n", exception);
			return null;
		}
	}
	
	public E merge(E entity) {
		try {
			E persistedEntity = getEntityManager().merge(entity);
			getEntityManager().flush();
			return persistedEntity;
		} catch (PersistenceException exception) {
			LOGGER.log(Level.SEVERE, "N�o foi poss�vel realizar o merge na entidade.\n", exception);
			return null;
		}
	}
	
	public void refresh(E entity) {
		getEntityManager().refresh(entity);
	}
	

	public E salvar(E entity) {
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
			LOGGER.log(Level.SEVERE, "N�o foi poss�vel salvar a entidade.\n", exception);
			return null;
		}
	}
	
	public void deletar(E entity) {
		try {
			getEntityManager().remove(entity);
			getEntityManager().flush();
		} catch (PersistenceException exception) {
			LOGGER.log(Level.SEVERE, "N�o foi poss�vel deletar a entidade.\n", exception);
		}
	}
	
	
}
