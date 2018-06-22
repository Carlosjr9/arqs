package br.unibh.loja.negocio;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import loja.Cliente;

@Stateless
@LocalBean
public class ServicoCliente {

	@Inject
	EntityManager em;
	
	@Inject
	private Logger log;
	
	public Cliente insert(Cliente c) throws Exception {
		log.info("Persistindo "+c);
		em.persist(c);
		return c;
	}
	
	public Cliente update(Cliente c) throws Exception {
		log.info("Atualizando "+c);
		return em.merge(c);
	}
	
	public void delete(Cliente c) throws Exception {
		log.info("Removendo "+c);
		Object o = em.merge(c);
		em.remove(o);
	}
	
	public Cliente find(Long k) throws Exception {
		log.info("Encontrando pela chave "+k);
		return em.find(Cliente.class, k);
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> findAll() throws Exception {
		log.info("Encontrando todos os objetos");
		return em.createQuery("from Client").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> findByName(String name) throws Exception {
		log.info("Encontrando o "+name);
		return em.createNamedQuery("Cliente.findByName")
		.setParameter("nome", "%"+name+"%").getResultList();
	}

}