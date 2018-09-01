
package services;

import java.util.List;

import domain.entity.negocio.ProcessoCondominial;

public interface ProcessoCondService {
	public Boolean atualizarProcessoCondominial(List<ProcessoCondominial> processos, String oidImovel);
}
