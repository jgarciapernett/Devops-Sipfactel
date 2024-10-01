package co.com.periferia.alfa.core.services;

import co.com.periferia.alfa.core.dto.NotaDebitoCreditoDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface INotaDebitoCreditoService extends IServicio<NotaDebitoCreditoDTO, Integer>{

	NotaDebitoCreditoDTO buscarPorNotaDebitoCredito(String numdoc) throws ExcepcionSegAlfa;


}
