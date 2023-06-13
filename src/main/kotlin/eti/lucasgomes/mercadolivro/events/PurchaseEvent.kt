package eti.lucasgomes.mercadolivro.events

import eti.lucasgomes.mercadolivro.model.PurchaseModel
import org.springframework.context.ApplicationEvent

class PurchaseEvent(
        source: Any,
        val purchaseModel: PurchaseModel
): ApplicationEvent(source)