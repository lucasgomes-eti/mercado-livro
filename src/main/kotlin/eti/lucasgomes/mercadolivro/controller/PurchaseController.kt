package eti.lucasgomes.mercadolivro.controller

import eti.lucasgomes.mercadolivro.controller.mapper.PurchaseMapper
import eti.lucasgomes.mercadolivro.controller.request.PostPurchaseRequest
import eti.lucasgomes.mercadolivro.service.PurchaseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("purchases")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody @Valid request: PostPurchaseRequest) {
        purchaseService.create(purchaseMapper.toModel(request))
    }
}