package services.impl;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import domain.entity.negocio.Imovel;
import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.Locatario;
import domain.entity.negocio.Pessoa;
import domain.entity.negocio.Proprietario;
import domain.repository.ImovelComercialRepository;
import domain.repository.ImovelResidencialRepository;
import domain.repository.LocatarioRepository;
import domain.repository.ProcessoCondominialRepository;
import domain.repository.ProprietarioRepository;
import resource.dto.ImovelComercialDTO;
import resource.dto.ImovelResidencialDTO;
import services.ImovelService;
import services.LocatarioService;
import services.ProprietarioService;

@Stateless 
public class ImovelServiceImpl implements ImovelService{
	private Logger LOGGER = Logger.getLogger(getClass().getName());
	@Inject
	private ImovelResidencialRepository imovelResidencialRepository;
	
	@Inject
	private ImovelComercialRepository imovelComercialRepository;
	
	@Inject
	private ProprietarioRepository proprietarioRepository;
	
	@Inject
	private LocatarioRepository locatarioRepository;
	
	@Inject
	private ProprietarioService proprietarioService;

	@Inject
	private LocatarioService locatarioService;
	
	@Inject
	private ProcessoCondominialRepository processoRepository;
	
	public Boolean cadastrarImovelResidencial(ImovelResidencialDTO imovelDTO) {
		
		List<ImovelResidencial> imoveis = imovelResidencialRepository
				.buscarTodos().stream()
					.filter(residencia -> 
						residencia.getNumeroImovel().equals(imovelDTO.getNumeroImovel())
					).collect(Collectors.toList());
		
		if(!imoveis.isEmpty()) 
			return false;
		
		ImovelResidencial novoImovel = new ImovelResidencial();
		
		novoImovel.setNumeroImovel(imovelDTO.getNumeroImovel());
		novoImovel.setRgi(imovelDTO.getRgi());
		novoImovel.setTrocouBarbara(imovelDTO.getTrocouBarbara());
		novoImovel.setPossuiAnimalEstimacao(imovelDTO.getPossuiAnimalEstimacao());
		novoImovel.setCobrancaBoleto(imovelDTO.getCobrancaBoleto());
		novoImovel.setTrocouColuna(imovelDTO.getTrocouColuna());
		novoImovel.setMoradores(imovelDTO.getMoradores());
		novoImovel.setProcessos(imovelDTO.getProcessos());
		novoImovel.setContatoEmergencia(imovelDTO.getContatoEmergencia());
		novoImovel.setAcordo(imovelDTO.getAcordo());
		novoImovel.setNomeRgi(imovelDTO.getNomeRgi());
		
		if(imovelDTO.getProprietario() != null) {
			Optional<Proprietario> proprietario = proprietarioService.cadastrarProprietario(imovelDTO.getProprietario(), novoImovel);
			if(proprietario.isPresent()) {
				novoImovel.setDonoImovel(proprietario.get());
			}
		}
		if(imovelDTO.getLocador()!=null) {
			Optional<Locatario> locatario = locatarioService.cadastrarLocatario(imovelDTO.getLocador(), novoImovel);
			if(locatario.isPresent()) {
				novoImovel.setLocatario(locatario.get());
				locatario.get().getImoveisAlugados().add(novoImovel);
			}
		}
		
		try {
			imovelResidencialRepository.salvar(novoImovel);
			return true;
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Não foi possível salvar o novo imóvel residencial");
			return false;
		}
			
	}

	public Boolean cadastrarImovelComercial(ImovelComercialDTO imovel) {

		List<ImovelComercial> imoveis = imovelComercialRepository
				.buscarTodos().stream().filter(comercio -> 
						(comercio.getNumeroImovel().equals(imovel.getNumeroImovel()) && comercio.iseSobreloja() && imovel.geteSobreloja()) ||
						(comercio.getNumeroImovel().equals(imovel.getNumeroImovel()) && !comercio.iseSobreloja() && !imovel.geteSobreloja())
					).collect(Collectors.toList());
		
		if(!imoveis.isEmpty())
			return false;
		ImovelComercial comercio = imovel.build();
		
		if(imovel.getProprietario() != null) {
			Optional<Proprietario> proprietario = proprietarioService.cadastrarProprietario(imovel.getProprietario(),comercio);
			if(proprietario.isPresent()) {
				comercio.setDonoImovel(proprietario.get());
			}
		}
		if(imovel.getLocador()!=null) {
			Optional<Locatario> locatario = locatarioService.cadastrarLocatario(imovel.getLocador(),comercio);
			if(locatario.isPresent()) {
				comercio.setLocatario(locatario.get());
			}
		}
		try {
			imovelComercialRepository.salvar(comercio);
			return true;
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Não foi possível salvar o novo imóvel comercial");
			return false;
		}
	}

	public List<ImovelResidencial> buscarTodosImoveisResidenciais() {
		return imovelResidencialRepository.buscarTodos();
	}

	public List<ImovelComercial> buscarTodosImoveisComerciais() {
		return imovelComercialRepository.buscarTodos();
	}
	
	public List<ImovelResidencial> buscarImovelResidencialPorRGI(String rgi){
		List<ImovelResidencial> imoveis = imovelResidencialRepository.buscarTodos()
													.stream().filter(imovel -> imovel.getRgi().contains(rgi))
															.sorted(Comparator.comparing(ImovelResidencial :: getNumeroImovel))
																	.collect(Collectors.toList());
		return imoveis;
	}
	
	public List<ImovelResidencial> buscarImovelResidencialPorNumero(String numero){
		List<ImovelResidencial> imoveis = imovelResidencialRepository.buscarTodos()
				.stream().filter(imovel -> imovel.getNumeroImovel().toString().contains(numero))
								.sorted(Comparator.comparing(ImovelResidencial :: getNumeroImovel))
										.collect(Collectors.toList());
		return imoveis;
	}
	
	public List<ImovelResidencial> buscarImovelResidencialPorNomeLocatario(String nome){
		List<ImovelResidencial> imoveis = 
				imovelResidencialRepository.buscarTodos().stream()
												.sorted(Comparator.comparing(ImovelResidencial :: getNumeroImovel))
														.collect(Collectors.toList());
		List<ImovelResidencial> temp = new ArrayList<ImovelResidencial>();
		imoveis.forEach(imovel -> {
			if (imovel.getLocatario()!=null) {
				if(imovel.getLocatario().getNome()!=null && !imovel.getLocatario()
																	.getNome().isEmpty()) {
					if(imovel.getLocatario().getNome().toLowerCase().contains(nome.toLowerCase())) {
						temp.add(imovel);
					}
				}
			}
		});
		return temp;
	}
	
	public List<ImovelComercial> buscarImovelComercialPorRGI(String rgi){
		List<ImovelComercial> imoveis = imovelComercialRepository.buscarTodos()
													.stream().filter(imovel -> imovel.getRgi().contains(rgi))
															.sorted(Comparator.comparing(ImovelComercial :: getNumeroImovel))
																	.collect(Collectors.toList());
		return imoveis;
	}
	
	public List<ImovelComercial> buscarImovelComercialPorNumero(String numero){
		List<ImovelComercial> imoveis = imovelComercialRepository.buscarTodos()
				.stream().filter(imovel -> imovel.getNumeroImovel().toString().contains(numero))
						.sorted(Comparator.comparing(ImovelComercial :: getNumeroImovel))
								.collect(Collectors.toList());
		return imoveis;
	}
	
	public List<ImovelComercial> buscarImovelComercialPorNomeLocatario(String nome){
		List<ImovelComercial> imoveis = 
				imovelComercialRepository.buscarTodos().stream()
					.sorted(Comparator.comparing(ImovelComercial :: getNumeroImovel))
							.collect(Collectors.toList());
		List<ImovelComercial> temp = new ArrayList<ImovelComercial>();
		imoveis.forEach(imovel -> {
			if (imovel.getLocatario()!=null) {
				if(imovel.getLocatario().getNome()!=null && !imovel.getLocatario()
																	.getNome().isEmpty()) {
					if(imovel.getLocatario().getNome().toLowerCase().contains(nome.toLowerCase())) {
						temp.add(imovel);
					}
				}
			}
		});
		return temp;
	}
	
	public Boolean atualizarImovelComercial(ImovelComercialDTO imovel) {
		Pessoa emergencia = null;
		
		List<ImovelComercial> imoveis = imovelComercialRepository
				.buscarTodos().stream().filter(comercio -> 
						(comercio.getNumeroImovel().equals(imovel.getNumeroImovel()))
					).collect(Collectors.toList());
		
		if(imoveis.isEmpty())
			return false;
		imoveis.get(0).setNomeRgi(imovel.getNomeRgi());
		imoveis.get(0).setCobrancaBoleto(imovel.getCobrancaBoleto());
		imoveis.get(0).setNomeLoja(imovel.getNomeLoja());
		imoveis.get(0).setTrocouColuna(imovel.getTrocouColuna());
		imoveis.get(0).setContatoEmergencia(imovel.getContatoEmergencia());
		imoveis.get(0).seteSobreloja(imovel.geteSobreloja());
		imoveis.get(0).setProcessos(imovel.getProcessos());
		imoveis.get(0).setRgi(imovel.getRgi());
		imoveis.get(0).setTipoLoja(imovel.getTipoLoja());
		imoveis.get(0).setTrocouBarbara(imovel.getTrocouBarbara());
		imoveis.get(0).setAcordo(imovel.getAcordo());
		
		//FIXME: GAMBE QUE SÓ DEUS SABE O PORQUE
		emergencia = imoveis.get(0).getContatoEmergencia();
		imoveis.get(0).setContatoEmergencia(null);
		
		Proprietario prop = imoveis.get(0).getDonoImovel();
		Locatario loc = imoveis.get(0).getLocatario();
		
		if(prop == null) {
			if(imovel.getProprietario() !=null) {
				Optional<Proprietario> proprietario = proprietarioService.cadastrarProprietario(imovel.getProprietario(), imoveis.get(0));
				if(proprietario.isPresent()) {
					imoveis.get(0).setDonoImovel(proprietario.get());
				}
			}
		}else {
			if(imovel.getProprietario() !=null ) {
				if(imovel.getProprietario().getCpf()!=null && !imovel.getProprietario().getCpf().isEmpty()) {
					if(imovel.getProprietario().getCpf().equals(prop.getCpf())) {
						imoveis.get(0).getDonoImovel().setCelular(imovel.getProprietario().getCelular());
						imoveis.get(0).getDonoImovel().setEmail(imovel.getProprietario().getEmail());
						imoveis.get(0).getDonoImovel().setEndereco(imovel.getProprietario().getEndereco());
						imoveis.get(0).getDonoImovel().setNome(imovel.getProprietario().getNome());
						imoveis.get(0).getDonoImovel().setTelefone(imovel.getProprietario().getTelefone());
						imoveis.get(0).getDonoImovel().setPossuidor(imovel.getProprietario().getPossuidor());
					}else {
						prop = removeImovelDoProprietario(imoveis);
						proprietarioRepository.salvar(imoveis.get(0).getDonoImovel());
						Optional<Proprietario> proprietario = proprietarioService.cadastrarProprietario(imovel.getProprietario(), imoveis.get(0));
						if(proprietario.isPresent()) {
							imoveis.get(0).setDonoImovel(proprietario.get());
						}
					}
				}else {
					prop = removeImovelDoProprietario(imoveis);
					proprietarioRepository.salvar(imoveis.get(0).getDonoImovel());
					imoveis.get(0).setDonoImovel(null);
				}
			}else {
				prop = removeImovelDoProprietario(imoveis);
				proprietarioRepository.salvar(imoveis.get(0).getDonoImovel());
				imoveis.get(0).setDonoImovel(null);
			}
		}
		
		if(loc == null) {
			if( imovel.getLocador()!=null) {
				Optional<Locatario> locatario = locatarioService.cadastrarLocatario(imovel.getLocador(), imoveis.get(0));
				if(locatario.isPresent()) {
					imoveis.get(0).setLocatario(locatario.get());
				}
			}
		}else {
			if(imovel.getLocador() !=null ) {
				if(imovel.getLocador().getCpf()!=null && !imovel.getLocador().getCpf().isEmpty()) {
					if(imovel.getLocador().getCpf().equals(loc.getCpf())) {
						imoveis.get(0).getLocatario().setCelular(imovel.getLocador().getCelular());
						imoveis.get(0).getLocatario().setEmail(imovel.getLocador().getEmail());
						imoveis.get(0).getLocatario().setNome(imovel.getLocador().getNome());
						imoveis.get(0).getLocatario().setTelefone(imovel.getLocador().getTelefone());
					}else {
						loc = removeImovelDoLocador(imoveis);
						locatarioRepository.salvar(imoveis.get(0).getLocatario());
						Optional<Locatario> locatario = locatarioService.cadastrarLocatario(imovel.getLocador(), imoveis.get(0));
						if(locatario.isPresent()) {
							imoveis.get(0).setLocatario(locatario.get());
						}
					}
				}else {
					loc = removeImovelDoLocador(imoveis);
					locatarioRepository.salvar(loc);
					imoveis.get(0).setLocatario(null);
				}
			}else {
				loc = removeImovelDoLocador(imoveis);
				locatarioRepository.salvar(loc);
				imoveis.get(0).setLocatario(null);
			}
		}
		try {
			imovelComercialRepository.salvar(imoveis.get(0));
			
			//FIXME: continuação da gambiarra divina
			if(emergencia != null) {
				imoveis.get(0).setContatoEmergencia(emergencia);
				imovelComercialRepository.salvar(imoveis.get(0));
			}
			if(prop !=null && prop.getImoveis().size() == 0) {
				proprietarioRepository.deletar(prop);
			}
			if(loc != null && loc.getImoveisAlugados().size() == 0) {
				locatarioRepository.deletar(loc);
			}
			return true;
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Não foi possível atualizar o novo imóvel comercial",e);
			return false;
		}
	}
	
	private Locatario removeImovelDoLocador(List<ImovelComercial> imoveis) {
		Imovel remover = null;
		for (Imovel imovel : imoveis.get(0).getLocatario().getImoveisAlugados()) {
			if(imovel.getNumeroImovel().equals(imoveis.get(0).getNumeroImovel())){
				remover = imovel;
			}else {
				remover =null;
			}
		}
		if(remover!=null) {
			imoveis.get(0).getLocatario().getImoveisAlugados().remove(remover);
		}
		return imoveis.get(0).getLocatario();
	}

	private Proprietario removeImovelDoProprietario(List<ImovelComercial> imoveis) {
		Imovel remover = null;
		for (Imovel imovel : imoveis.get(0).getDonoImovel().getImoveis()) {
			if(imovel.getNumeroImovel().equals(imoveis.get(0).getNumeroImovel())){
				remover = imovel;
			}else {
				remover =null;
			}
		}
		if(remover!=null) {
			imoveis.get(0).getDonoImovel().getImoveis().remove(remover);
		}
		return imoveis.get(0).getDonoImovel();
	}

	public Boolean atualizarImovelResidencial(ImovelResidencialDTO imovelDTO) {
		Pessoa emergencia = null;
		List<ImovelResidencial> imoveis = imovelResidencialRepository
				.buscarTodos().stream()//
					.filter(residencia -> // 
						(residencia.getNumeroImovel().equals(imovelDTO.getNumeroImovel()))//
					).collect(Collectors.toList());//
		
		if(imoveis.isEmpty()) 
			return false;
		
		imoveis.get(0).setNomeRgi(imovelDTO.getNomeRgi());
		imoveis.get(0).setRgi(imovelDTO.getRgi());
		imoveis.get(0).setTrocouBarbara(imovelDTO.getTrocouBarbara());
		imoveis.get(0).setPossuiAnimalEstimacao(imovelDTO.getPossuiAnimalEstimacao());
		imoveis.get(0).setTrocouColuna(imovelDTO.getTrocouColuna());
		
		imoveis.get(0).setCobrancaBoleto(imovelDTO.getCobrancaBoleto());
		imoveis.get(0).setMoradores(imovelDTO.getMoradores());
		imoveis.get(0).setProcessos(imovelDTO.getProcessos());
		imoveis.get(0).setContatoEmergencia(imovelDTO.getContatoEmergencia());
		imoveis.get(0).setAcordo(imovelDTO.getAcordo());
		
		//FIXME: GAMBE QUE SÓ DEUS SABE O PORQUE
		emergencia = imoveis.get(0).getContatoEmergencia();
		imoveis.get(0).setContatoEmergencia(null);
		
		Proprietario prop = imoveis.get(0).getDonoImovel();
		Locatario loc = imoveis.get(0).getLocatario();
		
		if(prop == null) {
			if(imovelDTO.getProprietario()!=null) {
				Optional<Proprietario> proprietario = proprietarioService.cadastrarProprietario(imovelDTO.getProprietario(), imoveis.get(0));
				if(proprietario.isPresent()) {
					imoveis.get(0).setDonoImovel(proprietario.get());
				}
			}
		}else {
			if(imovelDTO.getProprietario() !=null ) {
				if(imovelDTO.getProprietario().getCpf()!=null && !imovelDTO.getProprietario().getCpf().isEmpty()) {
					if(imovelDTO.getProprietario().getCpf().equals(prop.getCpf())) {
						imoveis.get(0).getDonoImovel().setCelular(imovelDTO.getProprietario().getCelular());
						imoveis.get(0).getDonoImovel().setEmail(imovelDTO.getProprietario().getEmail());
						imoveis.get(0).getDonoImovel().setEndereco(imovelDTO.getProprietario().getEndereco());
						imoveis.get(0).getDonoImovel().setNome(imovelDTO.getProprietario().getNome());
						imoveis.get(0).getDonoImovel().setTelefone(imovelDTO.getProprietario().getTelefone());
						imoveis.get(0).getDonoImovel().setPossuidor(imovelDTO.getProprietario().getPossuidor());
					}else {
						prop = removeImovelDoProprietarioResidencial(imoveis);
						proprietarioRepository.salvar(imoveis.get(0).getDonoImovel());
						Optional<Proprietario> proprietario = proprietarioService.cadastrarProprietario(imovelDTO.getProprietario(), imoveis.get(0));
						if(proprietario.isPresent()) {
							imoveis.get(0).setDonoImovel(proprietario.get());
						}
					}
				}else {
					prop = removeImovelDoProprietarioResidencial(imoveis);
					proprietarioRepository.salvar(imoveis.get(0).getDonoImovel());
					imoveis.get(0).setDonoImovel(null);
				}
			}else {
				prop = removeImovelDoProprietarioResidencial(imoveis);
				proprietarioRepository.salvar(imoveis.get(0).getDonoImovel());
				imoveis.get(0).setDonoImovel(null);
			}
		}
		
		if(loc == null) {
			if(imovelDTO.getLocador()!=null) {
				Optional<Locatario> locatario = locatarioService.cadastrarLocatario(imovelDTO.getLocador(), imoveis.get(0));
				if(locatario.isPresent()) {
					imoveis.get(0).setLocatario(locatario.get());
				}
			}
		}else {
			if(imovelDTO.getLocador() !=null ) {
				if(imovelDTO.getLocador().getCpf()!=null && !imovelDTO.getLocador().getCpf().isEmpty()) {
					if(imovelDTO.getLocador().getCpf().equals(loc.getCpf())) {
						imoveis.get(0).getLocatario().setCelular(imovelDTO.getLocador().getCelular());
						imoveis.get(0).getLocatario().setEmail(imovelDTO.getLocador().getEmail());
						imoveis.get(0).getLocatario().setNome(imovelDTO.getLocador().getNome());
						imoveis.get(0).getLocatario().setTelefone(imovelDTO.getLocador().getTelefone());
					}else {
						loc = removeImovelDoLocadorResidencial(imoveis);
						locatarioRepository.salvar(imoveis.get(0).getLocatario());
						Optional<Locatario> locatario = locatarioService.cadastrarLocatario(imovelDTO.getLocador(), imoveis.get(0));
						if(locatario.isPresent()) {
							imoveis.get(0).setLocatario(locatario.get());
						}
					}
				}else {
					loc = removeImovelDoLocadorResidencial(imoveis);
					locatarioRepository.salvar(imoveis.get(0).getLocatario());
					imoveis.get(0).setLocatario(null);
				}
			}else {
				loc = removeImovelDoLocadorResidencial(imoveis);
				locatarioRepository.salvar(imoveis.get(0).getLocatario());
				imoveis.get(0).setLocatario(null);
			}
		}
		
		try {
			if(emergencia != null) {
				imoveis.get(0).setContatoEmergencia(emergencia);
				imovelResidencialRepository.salvar(imoveis.get(0));
			}
			if(prop != null && prop.getImoveis().size() == 0) {
				proprietarioRepository.deletar(prop);
			}
			if(loc != null && loc.getImoveisAlugados().size() == 0) {
				locatarioRepository.deletar(loc);
			}
			return true;
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Não foi possível salvar o novo imóvel residencial",e);
			return false;
		}
			
	}
	
	private Locatario removeImovelDoLocadorResidencial(List<ImovelResidencial> imoveis) {
		Imovel remover = null;
		for (Imovel imovel : imoveis.get(0).getLocatario().getImoveisAlugados()) {
			if(imovel.getNumeroImovel().equals(imoveis.get(0).getNumeroImovel())){
				remover = imovel;
			}else {
				remover =null;
			}
		}
		if(remover!=null) {
			imoveis.get(0).getLocatario().getImoveisAlugados().remove(remover);
		}
		return imoveis.get(0).getLocatario();
	}

	private Proprietario removeImovelDoProprietarioResidencial(List<ImovelResidencial> imoveis) {
		Imovel remover = null;
		for (Imovel imovel : imoveis.get(0).getDonoImovel().getImoveis()) {
			if(imovel.getNumeroImovel().equals(imoveis.get(0).getNumeroImovel())){
				remover = imovel;
			}else {
				remover =null;
			}
		}
		if(remover!=null) {
			imoveis.get(0).getDonoImovel().getImoveis().remove(remover);
		}
		return imoveis.get(0).getDonoImovel();
	}

	public Optional<ImovelResidencial> recuperarImovelResidencialPorNumero(String numero){
		List<ImovelResidencial> imoveis = imovelResidencialRepository.buscarTodos()
				.stream().filter(imovel -> imovel.getNumeroImovel().equals(numero)).collect(Collectors.toList());
		
		return Optional.ofNullable(imoveis.get(0));
	}
	
	public Optional<ImovelComercial> recuperarImovelComercialPorNumero(String numero){
		List<ImovelComercial> imoveis = imovelComercialRepository.buscarTodos()
				.stream().filter(imovel -> imovel.getNumeroImovel().equals(numero)).collect(Collectors.toList());
		if(imoveis.isEmpty()) return Optional.empty();
		return Optional.ofNullable(imoveis.get(0));
	}
	
	public void removerImovelResidencial(String numero) {
		List<ImovelResidencial> imoveis = imovelResidencialRepository.buscarTodos()
				.stream().filter(imovel -> imovel.getNumeroImovel().equalsIgnoreCase(numero)).collect(Collectors.toList());
		if(!imoveis.isEmpty()) {
			ImovelResidencial imovel = imoveis.get(0);
			Proprietario prop = imovel.getDonoImovel();
			Locatario loc = imovel.getLocatario();
			if(imovel.getProcessos() !=null) {
				imovel.getProcessos().forEach(processo -> processoRepository.deletar(processo));
			}
			if(imovel.getDonoImovel()!= null) {
				prop = removeImovelDoProprietarioResidencial(imoveis);
				proprietarioRepository.salvar(imoveis.get(0).getDonoImovel());
				imoveis.get(0).setDonoImovel(null);
			}
			if(imovel.getLocatario()!=null) {
				loc = removeImovelDoLocadorResidencial(imoveis);
				locatarioRepository.salvar(imoveis.get(0).getLocatario());
				imoveis.get(0).setLocatario(null);
			}
			try {
				imovelResidencialRepository.salvar(imoveis.get(0));
				imovelResidencialRepository.deletar(imoveis.get(0));
				if(prop != null && prop.getImoveis().size() == 0) {
					proprietarioRepository.deletar(prop);
				}
				if(loc != null && loc.getImoveisAlugados().size() == 0) {
					locatarioRepository.deletar(loc);
				}
			}catch(Exception e) {
				LOGGER.log(Level.SEVERE, "Não foi possível deletar o imóvel residencial",e);			}
		}
	}
	
	public void removerImovelComercial(String numero) {
		List<ImovelComercial> imoveis = imovelComercialRepository.buscarTodos()
				.stream().filter(imovel -> imovel.getNumeroImovel().equalsIgnoreCase(numero)).collect(Collectors.toList());
		if(!imoveis.isEmpty()) {
			ImovelComercial imovel = imoveis.get(0);
			Proprietario prop = imovel.getDonoImovel();
			Locatario loc = imovel.getLocatario();
			if(imovel.getProcessos() !=null) {
				imovel.getProcessos().forEach(processo -> processoRepository.deletar(processo));
			}
			if(imovel.getDonoImovel()!= null) {
				prop = removeImovelDoProprietario(imoveis);
				proprietarioRepository.salvar(imoveis.get(0).getDonoImovel());
				imoveis.get(0).setDonoImovel(null);
			}
			if(imovel.getLocatario()!=null) {
				loc = removeImovelDoLocador(imoveis);
				locatarioRepository.salvar(imoveis.get(0).getLocatario());
				imoveis.get(0).setLocatario(null);
			}
			try {
				imovelComercialRepository.salvar(imoveis.get(0));
				imovelComercialRepository.deletar(imoveis.get(0));
				if(prop != null && prop.getImoveis().size() == 0) {
					proprietarioRepository.deletar(prop);
				}
				if(loc != null && loc.getImoveisAlugados().size() == 0) {
					locatarioRepository.deletar(loc);
				}
			}catch(Exception e) {
				LOGGER.log(Level.SEVERE, "Não foi possível deletar o imóvel residencial",e);			}
		}
	}
}

