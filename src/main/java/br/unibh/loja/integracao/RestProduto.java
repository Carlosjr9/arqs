package br.unibh.loja.integracao;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import loja.Produto;
import br.unibh.loja.negocio.ServicoProduto;
import io.swagger.annotations.Api;

@Api
@Path("Produto")
public class RestProduto {
	@Inject
	private ServicoProduto sc;

	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Produto> helloworld() throws Exception {
		return sc.findAll();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Produto hello(@PathParam("id") final Long id) throws Exception {
		return sc.find(id);
	}
}