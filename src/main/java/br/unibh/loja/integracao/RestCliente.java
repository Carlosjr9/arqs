package br.unibh.loja.integracao;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import loja.Cliente;
import br.unibh.loja.negocio.ServicoCliente;
import io.swagger.annotations.Api;

@Api
@Path("Produto")
public class RestCliente{
	@Inject
	private ServicoCliente sc;

	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cliente> helloworld() throws Exception {
		return sc.findAll();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Cliente hello(@PathParam("id") final Long id) throws Exception {
		return sc.find(id);
	}
}