package com.cassiolucianodasilva.teste_solutios.utils

class CpfCnpjMask {

    fun cpfCnpj(cpf: String): String {
        var maskCpf = ""
        var mask = ""

        if(cpf.count() == 11) mask = "###.###.###-##"
        else if(cpf.count() == 14) mask = "##.###.###/####-##"

        var i = 0
        for (m in mask.toCharArray()) {
            if (m != '#') {
                maskCpf += m
                continue
            }
            try {
                maskCpf += cpf[i]
            } catch (e: Exception) {
                break
            }
            i++
        }
        return maskCpf
    }




}