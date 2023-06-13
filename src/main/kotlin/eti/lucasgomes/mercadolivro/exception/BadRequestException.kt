package eti.lucasgomes.mercadolivro.exception

class BadRequestException(val error: Error) : Exception()