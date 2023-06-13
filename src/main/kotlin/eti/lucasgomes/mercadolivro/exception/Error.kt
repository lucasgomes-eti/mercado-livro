package eti.lucasgomes.mercadolivro.exception


sealed class Error(val code: String, val message: String) {
    object Global {
        object InvalidEntity : Error("ML-001", "Invalid entity")

        const val EMAIL_TAKEN_MESSAGE = "Email already taken"

        object EmailTaken : Error("ML-002", EMAIL_TAKEN_MESSAGE)
    }

    object Auth {
        object Unauthorized : Error("ML-101", "Authentication failed")
        object AccessDenied : Error("ML-102", "Access denied for the requested resource")
    }

    object Book {
        data class NotFound(val id: Int) : Error("ML-201", "Book with id: $id wasn't found")
        data class InvalidStatus(val status: String) : Error("ML-202", "Can't update book with status: $status")

        const val BOOK_UNAVAILABLE_FOR_PURCHASE_MESSAGE = "One of the books in the purchase is unavailable"

        object BookUnavailableForPurchase : Error("ML-203", BOOK_UNAVAILABLE_FOR_PURCHASE_MESSAGE)
    }

    object Customer {
        data class NotFound(val id: Int) : Error("ML-301", "Customer with id: $id wasn't found")
    }
}