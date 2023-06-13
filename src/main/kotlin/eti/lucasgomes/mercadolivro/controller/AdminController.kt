package eti.lucasgomes.mercadolivro.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("admin")
class AdminController() {

    @GetMapping("/report")
    fun getReport() = "This is a report that only admins can see."
}