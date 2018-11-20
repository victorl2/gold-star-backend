package assemblers;

import java.util.ArrayList;
import java.util.List;

import domain.entity.negocio.ImovelResidencial;
import resource.dto.ImovelResidencialDTO;

public class ImovelResidencialAssembler {
	
	public List<ImovelResidencial> build(List<ImovelResidencialDTO> imoveisDTO){
		List<ImovelResidencial> imoveis = new ArrayList<ImovelResidencial>();
		imoveisDTO.forEach(imovel -> imoveis.add(build(imovel)));
		return imoveis;
	}
	
	public ImovelResidencial build(ImovelResidencialDTO imovelDTO) {
		ImovelResidencial imovel = new ImovelResidencial();
		imovel.setNomeRgi(imovelDTO.getNomeRgi());
		imovel.setContatoEmergencia(imovelDTO.getContatoEmergencia());
		imovel.setMoradores(imovelDTO.getMoradores());
		imovel.setPossuiAnimalEstimacao(imovelDTO.getPossuiAnimalEstimacao());
		imovel.setNumeroImovel(imovelDTO.getNumeroImovel());
		imovel.setTrocouBarbara(imovelDTO.getTrocouBarbara());
		imovel.setRgi(imovelDTO.getRgi());
		imovel.setProcessos(imovelDTO.getProcessos());
		return imovel;
	}
}
