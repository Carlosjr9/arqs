package br.unibh.loja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import loja.Cliente;
import loja.Categoria;
import loja.Produto;
import br.unibh.loja.util.Resources;
import br.unibh.loja.negocio.*;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteServicoCliente {
	@Deployment
	public static Archive<?> createTestArchive() {
		// Cria o pacote que vai ser instalado no Wildfly para realizacao dos testes
		return ShrinkWrap.create(WebArchive.class, "testeloja.war")
				.addClasses(Cliente.class, Produto.class, Categoria.class, Resources.class, DAO.class, ServicoCliente.class)
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	// Realiza as injecoes com CDI
	@Inject
	private Logger log;
	@Inject
	private ServicoCliente sc;

	@Test
	public void teste01_inserirSemErro() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Cliente o = new Cliente(1L, "Clientee1", "cliénte", "123", "perfil1", "26.232-88", "3-1399998888", "Clientee", new Date(), new Date());
		sc.insert(o);
		Cliente aux = (Cliente) sc.findByName("Clientee1").get(0);
		assertNotNull(aux);
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	@Test
	public void teste02_inserirComErro() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			Cliente o = new Cliente(1L, "Clientee1", "cliénte", "123", "perfil1", "26.232-88", "3-1399998888", "Clientee", new Date(), new Date());
			sc.insert(o);
		} catch (Exception e) {
			assertTrue(checkString(e, "Caracteres permitidos: letras, espaços, ponto e aspas simples"));
		}
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	@Test
	public void teste03_atualizar() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Cliente o = (Cliente) sc.findByName("Clientee1").get(0);
		o.setNome("Clientee1 modificado");
		sc.update(o);
		Cliente aux = (Cliente) sc.findByName("Clientee1 modificado").get(0);
		assertNotNull(aux);
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	@Test
	public void teste04_excluir() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Cliente o = (Cliente) sc.findByName("Belo Horizonte").get(0);
		sc.delete(o);
		assertEquals(0, sc.findByName("Belo Horizonte modificado").size());
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private boolean checkString(Throwable e, String str) {
		if (e.getMessage().contains(str)) {
			return true;
		} else if (e.getCause() != null) {
			return checkString(e.getCause(), str);
		}
		return false;
	}
}
