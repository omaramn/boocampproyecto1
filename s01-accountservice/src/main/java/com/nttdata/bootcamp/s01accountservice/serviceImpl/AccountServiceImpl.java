package com.nttdata.bootcamp.s01accountservice.serviceImpl;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.s01accountservice.document.AccountDocument;
import com.nttdata.bootcamp.s01accountservice.exception.AccountCreationException;
import com.nttdata.bootcamp.s01accountservice.feingClient.ClienteFeignClient;
import com.nttdata.bootcamp.s01accountservice.mapper.AccountMapper;
import com.nttdata.bootcamp.s01accountservice.model.AccountCreateInput;
import com.nttdata.bootcamp.s01accountservice.model.AccountDetails;
import com.nttdata.bootcamp.s01accountservice.model.ClientDTO;
import com.nttdata.bootcamp.s01accountservice.model.ClientDTO.TypeEnum;
import com.nttdata.bootcamp.s01accountservice.repository.AccountMongoRepository;
import com.nttdata.bootcamp.s01accountservice.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	ClienteFeignClient clienteFeignClient;

	@Autowired
	AccountMongoRepository accountMongoRepository;

	@Override
	public AccountDetails accountsAccountIdGet(String accountId) {
		return AccountMapper.mapDocumentToDto(accountMongoRepository.findById(new ObjectId(accountId)));
	}

	@Override
	public List<AccountDetails> accountsByListPost(List<String> requestBody) {
		return accountMongoRepository
				.findByOwnerClientsContains(requestBody.stream().map(ObjectId::new).collect(Collectors.toList()));
	}

	@Override
	public AccountDetails accountsPost(AccountCreateInput accountCreateInput) {
		AccountDocument savedAccount = null;
		List<ClientDTO> dtoList = clienteFeignClient.bulkRetrieveClients(accountCreateInput.getOwnerClients());

		Predicate<ClientDTO> isPersonalClient = (client) -> client.getType().equals(TypeEnum.PERSONAL);
		Predicate<ClientDTO> isEmpresarialClient = (client) -> client.getType().equals(TypeEnum.EMPRESARIAL);

		Predicate<AccountCreateInput> isAhorroAccount = (input) -> input.getType()
				.equals(com.nttdata.bootcamp.s01accountservice.model.AccountCreateInput.TypeEnum.AHORRO);
		Predicate<AccountCreateInput> isCorrienteAccount = (input) -> input.getType()
				.equals(com.nttdata.bootcamp.s01accountservice.model.AccountCreateInput.TypeEnum.CORRIENTE);
		Predicate<AccountCreateInput> isPlazoFijoAccount = (input) -> input.getType()
				.equals(com.nttdata.bootcamp.s01accountservice.model.AccountCreateInput.TypeEnum.PLAZOFIJO);

		List<ClientDTO> personalClients = dtoList.stream().filter(isPersonalClient::test).collect(Collectors.toList());

		List<ClientDTO> empresarialClients = dtoList.stream().filter(isEmpresarialClient::test)
				.collect(Collectors.toList());

		if (!personalClients.isEmpty()) {
			boolean accountExists = personalClients.stream().anyMatch(personalClient -> {
				List<AccountDetails> accountsOfType = accountMongoRepository
						.findByOwnerClientsContainsAndType(new ObjectId(personalClient.getId()),
								accountCreateInput.getType())
						.stream().map(AccountMapper::mapDocumentToDto).collect(Collectors.toList());
				return !accountsOfType.isEmpty();
			});
			if (accountExists) {
				throw new AccountCreationException("El cliente ya tiene una cuenta de este tipo.");
			}

			savedAccount = accountMongoRepository.save(AccountMapper.mapCreateInputToDocument(accountCreateInput));
		}

		if (!empresarialClients.isEmpty()) {

			if (isAhorroAccount.test(accountCreateInput) || isPlazoFijoAccount.test(accountCreateInput)) {
				throw new AccountCreationException(
						"Un cliente empresarial no puede tener una cuenta de ahorro o de plazo fijo.");
			}

			if (accountCreateInput.getSignClients().size() >= 4) {
				throw new AccountCreationException(
						"Una cuenta empresarial no puede tener más de 4 firmantes autorizados.");
			}

			if (!empresarialClients.isEmpty() && isCorrienteAccount.test(accountCreateInput)) {
				List<AccountDetails> existingCorrienteAccounts = accountMongoRepository
						.findByOwnerClientsContainsAndType(new ObjectId(empresarialClients.get(0).getId()),
								AccountCreateInput.TypeEnum.CORRIENTE)
						.stream().map(AccountMapper::mapDocumentToDto).collect(Collectors.toList());
				if (!existingCorrienteAccounts.isEmpty()) {
					throw new AccountCreationException("Un cliente empresarial ya tiene una cuenta corriente.");
				}
			}

			savedAccount = accountMongoRepository.save(AccountMapper.mapCreateInputToDocument(accountCreateInput));
		}

		return AccountMapper.mapDocumentToDto(savedAccount);
	}

	@Override
	public AccountDetails accountsAccountIdAddSignersPost(String accountId, List<String> requestBody) {
		AccountDocument savedAccount = null;
		Predicate<Set<String>> moreThan4Elements = (set) -> set.size() >= 4;
		AccountDocument accountDocument = accountMongoRepository.findById(new ObjectId(accountId));

		if (accountDocument == null) {
			throw new AccountCreationException("La cuenta solicitada no existe");
		}

		List<String> signClientIds = accountDocument.getSignClients();
		Set<String> uniqueIds = Stream.concat(signClientIds.stream(), requestBody.stream()).collect(Collectors.toSet());

		if (moreThan4Elements.test(uniqueIds)) {
			throw new AccountCreationException("Una cuenta empresarial no puede tener más de 4 firmantes autorizados.");
		} else {
			List<String> signersToAdd = uniqueIds.stream().collect(Collectors.toList());
			accountDocument.setSignClients(signersToAdd);
			savedAccount = accountMongoRepository.save(accountDocument);
		}

		return AccountMapper.mapDocumentToDto(savedAccount);
	}

}
