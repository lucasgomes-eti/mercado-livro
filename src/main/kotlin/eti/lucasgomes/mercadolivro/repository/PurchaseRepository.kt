package eti.lucasgomes.mercadolivro.repository

import eti.lucasgomes.mercadolivro.model.PurchaseModel
import org.springframework.data.repository.CrudRepository

interface PurchaseRepository : CrudRepository<PurchaseModel, Int>
